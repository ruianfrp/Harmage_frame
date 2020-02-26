package com.harmonycloud.controller;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.Threads.SynchroThread;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.config.Constant;
import com.harmonycloud.entity.MemberApply;
import com.harmonycloud.service.EmployeeService;
import com.harmonycloud.service.LoginInfoService;
import com.harmonycloud.service.MemberApplyService;
import com.harmonycloud.service.ProjectService;
import com.harmonycloud.util.SendMessageUtil;
import com.harmonycloud.view.MatchEmployeeView;
import com.harmonycloud.view.MemberAgreeApplyList;
import com.harmonycloud.view.MemberApplyDetailView;
import com.harmonycloud.view.MemberApplyListView;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.harmonycloud.config.URLConstant.URL_GET_TOKKEN;
import static com.harmonycloud.config.URLConstant.URL_SEND_WORK_MESSAGE;
import static com.harmonycloud.util.JsonWebToken.VerifyCode;

/**
 * @author ：frp
 * @date ：Created in 2019/9/6 14:34
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="申请项目成员controller",tags={"申请项目成员操作接口"})
@RequestMapping("/memberApply")
public class MemberApplyController {

    @Autowired
    LoginInfoService loginInfoService;

    @Autowired
    MemberApplyService memberApplyService;

    @Autowired
    ProjectService projectService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 返回申请项目成员列表
     *
     * @return message
     * @throws Exception
     */
    @PostMapping("/listMemberApply")
    @ApiOperation(value = "返回申请项目成员列表")
    public Message listMemberApply(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String startTimeSort = (String) map.get("startTimeSort");
        String statusSort = (String) map.get("statusSort");
        Integer pageNum = (Integer) map.get("pageNum");
        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum,10);
        List<MemberApplyListView> list = memberApplyService.listMemberApply(startTimeSort,statusSort);
        PageInfo<MemberApplyListView> pageInfo = new PageInfo<>(list);
        if (pageInfo.getTotal()>0) {
            for (MemberApplyListView memberApplyListView : list) {
                List<String> listAllName = memberApplyService.listSkillName(memberApplyListView.getId());
                memberApplyListView.setSkillAllName(listAllName);
            }
            Integer applyNum = loginInfoService.selectApplyWait();
            Integer leaveNum = loginInfoService.selectLeaveWait();
            Integer memberNum = applyNum + leaveNum;
            log.info("申请项目成员列表返回成功");
            data.put("pageInfo", pageInfo);
            data.put("applyNum", applyNum);
            data.put("memberNum", memberNum);
            res.message.setMessage(200, "申请项目成员列表返回成功", data);
        } else {
            log.error("返回错误，成员信息数据为空");
            res.message.setMessage(400, "返回错误，成员信息数据为空");
        }
        return res.message;
    }

    @PostMapping("/insertMemberApply")
    @ApiOperation(value = "PM发起人员申请")
    public Message insertMemberApply(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = res.user.getId();
        MemberApply memberApply = new MemberApply();
        Integer projId = (Integer) map.get("projectId");
        String memberRecommendGh = (String) map.get("memberRecommendGh");
        memberApply.setFkProjectId(projId);
        memberApply.setFkEmployeeGh(employeeGh);
        memberApply.setMemberApplicationReason((String) map.get("memberApplicationReason"));
        memberApply.setMemberJoinType((String) map.get("memberJoinType"));
        memberApply.setMemberStartTime((String) map.get("memberStartTime"));
        memberApply.setMemberJoinSup((String) map.get("memberJoinSup"));
        memberApply.setMemberRecommendGh(memberRecommendGh);
        memberApply.setMemberApplicationStatus("待审批");
        memberApply.setMemberEndTime((String) map.get("memberEndTime"));
        memberApplyService.insertMemberApply(memberApply);

        String memberJoinSup = memberApply.getMemberJoinSup();
        String memberJoinType = memberApply.getMemberJoinType();
        String memberStartTime = memberApply.getMemberStartTime();
        Long id = memberApplyService.selectMemberApplyId(projId, employeeGh, memberRecommendGh,
                memberJoinSup, memberJoinType, memberStartTime);
        log.info("申请成员提交成功(不含技能)");

        List<Integer> listSkill = (List<Integer>) map.get("skillId");
        List<Integer> listSkillTest = (List<Integer>) map.get("skillTestId");
        if (listSkill.size() > 0) {
            for (short i = 0; i < listSkill.size(); i++) {
                Integer applySkillId = listSkill.get(i);
                memberApplyService.insertIntoApplySkill(id, applySkillId);
            }
            log.info("申请成员提交成功(带普通技能)");
        }
        if (listSkillTest.size() > 0) {
            for (short i = 0; i < listSkillTest.size(); i++) {
                Integer applySkillTestId = listSkillTest.get(i);
                memberApplyService.insertIntoApplySkillTest(id, applySkillTestId);
            }
            log.info("申请成员提交成功(带考核技能)");
        }
        log.info("申请成员提交成功");
        res.message.setMessage(200, "申请成员提交成功");

        //发送钉钉消息通知
        SendMessageUtil.PMOMessageSend("您有新的人员申请待审批，请尽快完成审批！\n(*^v^*)点击详情前往Harmage");
        return res.message;
    }

    /**
     * 根据申请id返回申请项目成员信息  匹配人员
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/selectById")
    @ApiOperation(value = "根据申请id返回申请项目成员信息")
    public Message selectById(@RequestBody @ApiParam(name = "id", value = "申请id") Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer id = (Integer) map.get("id");
        Map<String, Object> data = new HashMap<>();
        MemberApplyDetailView memberApplyDetailView = memberApplyService.selectById(id);
        if (memberApplyDetailView != null) {
            List<String> listAllName = memberApplyService.listSkillName(id);
//            List<String> listTestName = memberApplyService.listSkillTestName(id);
//            listAllName.removeAll(listTestName);
//            listAllName.addAll(listTestName);
            memberApplyDetailView.setSkillAllName(listAllName);
            log.info(id + "申请项目成员信息返回成功");

            if (memberApplyDetailView.getApplyStatus().equals("待审批")) {
                List<Integer> skillAllId = memberApplyService.getSkillId(id);
//                List<Integer> skillTestAllId = memberApplyService.getSkillTestId(id);
                List<MatchEmployeeView> selectSkillMatchEmployee = new ArrayList<>();
//                List<MatchEmployeeView> selectSkillTestMatchEmployee = new ArrayList<>();

                //获取普通技能匹配的员工
                if (skillAllId.size()>0) {
                    for (Integer skillId : skillAllId) {
                        selectSkillMatchEmployee.addAll(memberApplyService.selectSkillMatchEmployee(skillId));
                    }
                    if (selectSkillMatchEmployee.size() > 0) {
                        log.info("匹配人员信息返回成功");
                    } else {
                        log.warn("无匹配人员");
                    }
                } else {
                    log.warn("普通技能匹配人员为空");
                }

                //获取考核技能匹配的员工
//                if (!skillTestAllId.isEmpty()) {
//                    Integer skillTestId = skillTestAllId.get(0);
//                    selectSkillTestMatchEmployee = memberApplyService.selectSkillTestMatchEmployee(skillTestId);
//                    for (short i = 1; i < skillTestAllId.size(); i++) {
//                        skillTestId = skillTestAllId.get(i);
//                        selectSkillTestMatchEmployee.addAll(memberApplyService.selectSkillTestMatchEmployee(skillTestId));
//                    }
//                    if (selectSkillTestMatchEmployee.size() > 0) {
//                        log.info("匹配人员信息返回成功");
//                    } else {
//                        log.warn("无匹配人员");
//                    }
//                } else {
//                    log.error("考核技能匹配人员为空");
//                }

                //获取当前项目组小于2个的员工
                List<MatchEmployeeView> listLess = memberApplyService.projectLessThanTwo();

                //获取时间匹配的员工
                List<MatchEmployeeView> listMatchStartTime = memberApplyService.matchStartTime(id);
//                List<MatchEmployeeView> listMatchEndTime = memberApplyService.matchEndTime(id);

                listLess.retainAll(selectSkillMatchEmployee);
//                listLess.addAll(selectSkillTestMatchEmployee);
                listLess.addAll(listMatchStartTime);
//                listLess.addAll(listMatchEndTime);
                List<MatchEmployeeView> listAllMatch = listLess;
                List<MatchEmployeeView> list = new ArrayList<>();
                for (int i = 0; i < listLess.size(); i++) {
                    for (int j = listLess.size() - 1; j > i; j--) {
                        if (listLess.get(i).getEmployeeGh().equals(listLess.get(j).getEmployeeGh())) {
                            listLess.remove(j);
                        }
                    }
                }
                for (short i = 0; i < listLess.size(); i++) {
                    if (listLess.get(i).getEmployeeGh() != null) {
                        list.add(listLess.get(i));
                    }
                }
                for (MatchEmployeeView temp : list) {
                    temp.setSkillInfo(employeeService.listEmployeeSkill(temp.getEmployeeGh()));
                    temp.setSkillTestInfo(employeeService.listEmployeeTestSkill(temp.getEmployeeGh()));
                    log.info("匹配人员技能返回成功");
                    if (temp.getProjCount() == 0) {
                        temp.setGrade(Collections.frequency(listAllMatch, temp) + 3);
                    } else if (temp.getProjCount() == 1) {
                        temp.setGrade(Collections.frequency(listAllMatch, temp) + 2);
                    } else {
                        temp.setGrade(Collections.frequency(listAllMatch, temp) + 1);
                    }
                }
                MatchEmployeeView recommendEmployee = memberApplyService.selectRecommendEmployee(id);
                if (recommendEmployee.getEmployeeGh() != null) {
                    recommendEmployee.setSkillInfo(employeeService.listEmployeeSkill(recommendEmployee.getEmployeeGh()));
                    recommendEmployee.setSkillTestInfo(employeeService.listEmployeeTestSkill(recommendEmployee.getEmployeeGh()));
                    data.put("recommend", recommendEmployee);
                    log.info("建议人员信息返回成功");
                } else {
                    data.put("recommend", null);
                    log.info("无建议人员");
                }
                data.put("match", list);
                data.put("list", memberApplyDetailView);
                res.message.setMessage(200, id + "申请项目成员信息返回成功", data);
            } else if (memberApplyDetailView.getApplyStatus().equals("审批通过") || memberApplyDetailView.getApplyStatus().equals("PMO添加")) {
                MatchEmployeeView distributionEmployee = memberApplyService.selectDistributionEmployee(id);
                if (distributionEmployee.getEmployeeGh() != null) {
                    distributionEmployee.setSkillInfo(employeeService.listEmployeeSkill(distributionEmployee.getEmployeeGh()));
                    distributionEmployee.setSkillTestInfo(employeeService.listEmployeeTestSkill(distributionEmployee.getEmployeeGh()));
                    data.put("distribution", distributionEmployee);
                    log.info("已分配人员信息返回成功");
                } else {
                    data.put("distribution", null);
                    log.error("已分配人员信息返回失败");
                }
                data.put("list", memberApplyDetailView);
                res.message.setMessage(200, id + "申请项目成员信息返回成功", data);
            }
        } else {
            log.error("返回错误，成员信息数据为空");
            res.message.setMessage(400, "返回错误，信息为空");
        }
        return res.message;
    }

    /**
     * 返回Pm申请项目成员列表
     *
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/listPmMemberApply")
    @ApiOperation(value = "返回Pm申请项目成员列表")
    public Message listPmMemberApply(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String startTimeSort = (String) map.get("startTimeSort");
        String statusSort = (String) map.get("statusSort");
        Integer pageNum = (Integer) map.get("pageNum");
        String employeeGh = res.user.getId();
        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum,10);
        List<MemberApplyListView> list = memberApplyService.listPmMemberApply(employeeGh,startTimeSort,statusSort);
        PageInfo<MemberApplyListView> pageInfo = new PageInfo<>(list);
        if (pageInfo.getTotal()>0) {
            for (MemberApplyListView memberApplyListView : list) {
                List<String> listAllName = memberApplyService.listSkillName(memberApplyListView.getId());
                memberApplyListView.setSkillAllName(listAllName);
            }
            log.info("Pm申请项目成员列表返回成功");
            data.put("pageInfo", pageInfo);
            res.message.setMessage(200, "Pm申请项目成员列表返回成功", data);
        } else {
            log.error("返回错误，成员信息数据为空");
            res.message.setMessage(400, "返回错误，成员信息数据为空");
        }
        return res.message;
    }

    /**
     * 人员申请审批
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/updateMemberApply")
    @ApiOperation(value = "审批申请接口")
    public Message updateMemberApply(@RequestBody @ApiParam(name = "id\nmemberApplicationStatus\nemployeeGh\nmemberStartTime\nmemberEndTime", value = "成员申请id\n审批状态\n加入的员工工号\n开始时间\n结束时间") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String applyApprovalGh = res.user.getId();
        String memberApplicationStatus = (String) map.get("memberApplicationStatus");
        Integer id = (Integer) map.get("id");
        String employeeGh = (String) map.get("employeeGh");//分配成员工号
        String memberStartTime = (String) map.get("memberStartTime");
        String memberEndTime = (String) map.get("memberEndTime");
        Integer result = memberApplyService.updateMemberApply(employeeGh, applyApprovalGh, memberStartTime, memberEndTime, memberApplicationStatus, id);
        if (result > 0) {
            log.info("审批信息修改成功");
            if(memberApplicationStatus.equals("审批通过")){
                String applicantPM = memberApplyService.selectApplicantPM(id);
                Integer agreeType = 1;
                String pmGh = applicantPM;
                List<String> ownerPM = memberApplyService.selectOwnerPM(id);
                memberApplyService.insertIntoMemberApplyAgree(id,agreeType,pmGh);
                if(ownerPM.size()>0){
                    for (String fkEmployeeGh : ownerPM){
                        agreeType = 2;
                        pmGh = fkEmployeeGh;
                        memberApplyService.insertIntoMemberApplyAgree(id,agreeType,pmGh);
                    }
                }
                ownerPM.add(applicantPM);
                List<String> allUserId = new ArrayList<>();
                for (String list : ownerPM) {
                    String aa = memberApplyService.selectUserIdByGh(list);
                    if (aa != null) {
                        allUserId.add(aa);
                    }
                }
                if (allUserId.size() > 0) {
                    String allPM = Joiner.on(",").join(allUserId);
//                //发送钉钉消息通知
                SendMessageUtil.PMMessageSend(allPM);
                }
            }
            res.message.setMessage(200,"成员申请审批信息修改成功");
        } else {
            log.error("审批信息修改失败");
            res.message.setMessage(403, "成员申请审批信息修改失败");
        }
        return res.message;
    }

    /**
     * 申请成员加入PM的确认列表
     *
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/listApplicantAgreeApply")
    @ApiOperation(value = "申请成员加入PM的确认列表")
    public Message listApplicantAgreeApply(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer pageNum = (Integer) map.get("pageNum");
        String sort = (String) map.get("sort");
        Map<String, Object> data = new HashMap<>();
        String employeeGh = res.user.getId();
        PageHelper.startPage(pageNum,10);
        List<MemberAgreeApplyList> applicantList = memberApplyService.listApplicantAgreeApply(employeeGh,sort);
        PageInfo<MemberAgreeApplyList> pageInfo = new PageInfo<>(applicantList);
        if (pageInfo.getTotal()>0) {
            for (MemberAgreeApplyList memberAgreeApply : applicantList) {
                String lastProjectName = memberApplyService.selectOwnerProjectName(memberAgreeApply.getId());
                memberAgreeApply.setLastProjectName(lastProjectName);
            }
            Integer applicantAgreeNum = loginInfoService.selectApplicantAgreeWait(employeeGh);
            Integer ownerAgreeNum = loginInfoService.selectOwnerAgreeWait(employeeGh);
            Integer memberNum = applicantAgreeNum + ownerAgreeNum;
            log.info("成员申请PM 确认列表返回成功");
            data.put("pageInfo", pageInfo);
            data.put("applicantAgreeNum", applicantAgreeNum);
            data.put("memberNum", memberNum);
            res.message.setMessage(200, "成员申请PM 确认列表返回成功", data);
        } else {
            log.error("成员申请PM 确认列表返回为空");
            res.message.setMessage(400, "成员申请PM 确认列表返回为空");
        }
        return res.message;
    }

    /**
     * 申请成员原先PM的确认列表
     *
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/listOwnerAgreeApply")
    @ApiOperation(value = "申请成员原先PM的确认列表")
    public Message listOwnerAgreeApply(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer pageNum = (Integer) map.get("pageNum");
        String sort = (String) map.get("sort");
        Map<String, Object> data = new HashMap<>();
        String employeeGh = res.user.getId();
        PageHelper.startPage(pageNum,10);
        List<MemberAgreeApplyList> ownerList = memberApplyService.listOwnerAgreeApply(employeeGh,sort);
        PageInfo<MemberAgreeApplyList> pageInfo = new PageInfo<>(ownerList);
        if (pageInfo.getTotal()>0) {
            Integer ownerAgreeNum = loginInfoService.selectOwnerAgreeWait(employeeGh);
            Integer applicantAgreeNum = loginInfoService.selectApplicantAgreeWait(employeeGh);
            Integer memberNum = applicantAgreeNum + ownerAgreeNum;
            log.info("成员调出PM 确认列表返回成功");
            data.put("pageInfo", pageInfo);
            data.put("ownerAgreeNum", ownerAgreeNum);
            data.put("memberNum", memberNum);
            res.message.setMessage(200, "成员调出PM 确认列表返回成功", data);
        } else {
            log.error("成员调出PM 确认列表返回为空");
            res.message.setMessage(400, "成员调出PM 确认列表返回为空");
        }
        return res.message;
    }

    /**
     * 加入项目组PM的确认
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/applicantAgree")
    @ApiOperation(value = "加入项目组PM的确认", notes = "ifAgree 1：同意、2：不同意 ")
    public Message applicantAgree(@RequestBody @ApiParam(name = "ifAgree\nid", value = "确认信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer id = (Integer) map.get("id");
        Integer ifAgree = (Integer) map.get("ifAgree");
        Integer result = memberApplyService.applicantAgree(ifAgree, id);
        Integer result2 = memberApplyService.applicantAgree2(ifAgree, id);
        if (result > 0 && result2 > 0) {
            log.info("进入项目组PM确认完成");
            if (ifAgree == 1) {
                MemberApply memberApply = memberApplyService.selectIfOwnerIsAgree(id);
                if (memberApply != null) {
                    log.info("申请信息返回成功");
                    List<Integer> selectIfHaveProj2 = memberApplyService.selectIfHaveProj2(id);
                    if ((memberApply.getOwnerIsAgree() == 1 && memberApply.getIsDone() == 0) || (memberApply.getOwnerIsAgree() == 0 && selectIfHaveProj2.size() == 0 && memberApply.getIsDone() == 0)) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String nowTime = df.format(new Date());
                        Date nowDate = df.parse(nowTime);
                        Date startTime = df.parse(memberApply.getMemberStartTime());
                        if (nowDate.before(startTime)) {
                            log.info("加入时间到了进组");
                        } else {
                            log.info("立即开始进组");
                            String memberJoinType = memberApply.getMemberJoinType();
                            String employeeGh = memberApply.getDistributionGh();
                            Integer projectId = memberApply.getFkProjectId();
                            String memberStartTime = memberApply.getMemberStartTime();
                            String memberEndTime = memberApply.getMemberEndTime();
                            String memberJoinSup = memberApply.getMemberJoinSup();
                            if (memberJoinType.equals("完全转组")) {
                                memberApplyService.updateQuitMember(employeeGh);
                                Integer r = memberApplyService.insertMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
                                if (r > 0) {
                                    log.info("完全转组组员添加成功");
                                    Integer result1 = memberApplyService.doneMemberApply(id);
                                    if (result1 > 0) {
                                        log.info("完全转组组员申请已处理");
                                    } else {
                                        log.error("完全转组组员申请处理失败");
                                    }
                                } else {
                                    log.error("完全转组组员添加失败");
                                }
                            } else {
                                Integer r1 = memberApplyService.insertMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
                                if (r1 > 0) {
                                    log.info(memberJoinType + "组员添加成功");
                                    Integer result1 = memberApplyService.doneMemberApply(id);
                                    if (result1 > 0) {
                                        log.info(memberJoinType + "组员申请已处理");
                                    } else {
                                        log.error(memberJoinType + "组员申请处理失败");
                                    }
                                } else {
                                    log.error(memberJoinType + "组员添加失败");
                                }
                            }
                        }
                    }
                } else {
                    log.error("申请信息返回失败");
                }
            }
            res.message.setMessage(200, "进入项目组PM确认完成");
        } else {
            log.error("数据修改失败");
            res.message.setMessage(403, "数据修改失败");
        }
        return res.message;
    }

    /**
     * 原先项目组PM的确认
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/ownerAgree")
    @ApiOperation(value = "原先项目组PM的确认", notes = "ifAgree 1：同意、2：不同意 ")
    public Message ownerAgree(@RequestBody @ApiParam(name = "ifAgree\nid", value = "确认信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = res.user.getId();
        Integer id = (Integer) map.get("id");
        Integer ifAgree = (Integer) map.get("ifAgree");
        Integer result = memberApplyService.ownerAgree2(ifAgree, id, employeeGh);
        if (result > 0) {
            log.info("原先项目组PM确认完成");
            ifAgree = 0;
            List<Integer> ifOwnerAgree = memberApplyService.selectIfOwnerAgree(id);
            if(ifOwnerAgree.size()>0){
                for (Integer i : ifOwnerAgree){
                    if(i==1){
                        ifAgree=1;
                    }else if(i==2){
                        ifAgree=2;
                        break;
                    }else {
                        ifAgree=0;
                        break;
                    }
                }
            }
            if (ifAgree == 1) {
                Integer result3 = memberApplyService.ownerAgree(ifAgree,id);
                if(result3 > 0){
                    log.info("申请列表原先PM同意修改成功");
                    MemberApply memberApply = memberApplyService.selectIfOwnerIsAgree(id);
                    if (memberApply != null) {
                        log.info("申请信息返回成功");
                        if (memberApply.getApplicantisAgree() == 1 && memberApply.getIsDone() == 0) {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String nowTime = df.format(new Date());
                            Date nowDate = df.parse(nowTime);
                            Date startTime = df.parse(memberApply.getMemberStartTime());
                            if (nowDate.before(startTime)) {
                                log.info("加入时间到了进组");
                            } else {
                                log.info("立即开始进组");
                                String memberJoinType = memberApply.getMemberJoinType();
                                employeeGh = memberApply.getDistributionGh();
                                Integer projectId = memberApply.getFkProjectId();
                                String memberStartTime = memberApply.getMemberStartTime();
                                String memberEndTime = memberApply.getMemberEndTime();
                                String memberJoinSup = memberApply.getMemberJoinSup();
                                if (memberJoinType.equals("完全转组")) {
                                    memberApplyService.updateQuitMember(employeeGh);
                                    Integer r = memberApplyService.insertMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
                                    if (r > 0) {
                                        log.info("完全转组组员添加成功");
                                        Integer result1 = memberApplyService.doneMemberApply(id);
                                        if (result1 > 0) {
                                            log.info("完全转组组员申请已处理");
                                        } else {
                                            log.error("完全转组组员申请处理失败");
                                        }
                                    } else {
                                        log.error("完全转组组员添加失败");
                                    }
                                } else {
                                    Integer r1 = memberApplyService.insertMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
                                    if (r1 > 0) {
                                        log.info(memberJoinType + "组员添加成功");
                                        Integer result1 = memberApplyService.doneMemberApply(id);
                                        if (result1 > 0) {
                                            log.info(memberJoinType + "组员申请已处理");
                                        } else {
                                            log.error(memberJoinType + "组员申请处理失败");
                                        }
                                    } else {
                                        log.error(memberJoinType + "组员添加失败");
                                    }
                                }
                            }
                        }
                    } else {
                        log.error("申请信息返回失败");
                    }
                }else {
                    log.error("申请列表原先PM同意修改失败");
                }
            }else if(ifAgree == 2){
                Integer result3 = memberApplyService.ownerAgree(ifAgree,id);
                if(result3 > 0) {
                    log.info("申请列表原先PM同意修改成功");
                }else {
                    log.error("申请列表原先PM同意修改失败");
                }
            }
            res.message.setMessage(200, "原先项目组PM确认完成");
        } else {
            log.error("数据修改失败");
            res.message.setMessage(403, "数据修改失败");
        }
        return res.message;
    }
}
