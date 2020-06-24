package com.harmonycloud.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.member.MemberLeaveListView;
import com.harmonycloud.config.DingConstant;
import com.harmonycloud.service.EmployeeService;
import com.harmonycloud.service.LoginInfoService;
import com.harmonycloud.service.MemberLeaveService;
import com.harmonycloud.service.MemberService;
import com.harmonycloud.util.SendMessageUtil;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.harmonycloud.config.URLConstant.URL_GET_TOKKEN;
import static com.harmonycloud.config.URLConstant.URL_SEND_WORK_MESSAGE;
import static com.harmonycloud.util.JsonWebToken.VerifyCode;

@CrossOrigin
@RestController
@Slf4j
@Api(value="调离项目成员controller",tags={"申请调离项目成员操作接口"})
@RequestMapping("/memberLeave")
public class MemberLeaveController {

    @Autowired
    LoginInfoService loginInfoService;

    @Autowired
    MemberLeaveService memberLeaveService;

    @Autowired
    MemberService memberService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 返回申请调离项目成员列表
     *
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/listMemberLeave")
    @ApiOperation(value = "返回申请调离项目成员列表")
    public Message listMemberLeave(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer pageNum = (Integer) map.get("pageNum");
        String sort = (String) map.get("sort");
        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum,10);
        List<MemberLeaveListView> list = memberLeaveService.listMemberLeave(sort);
        PageInfo<MemberLeaveListView> pageInfo = new PageInfo<>(list);
        if (pageInfo.getTotal() > 0) {
            for (MemberLeaveListView memberLeaveListView : list) {
                memberLeaveListView.setSkillInfo(employeeService.listEmployeeSkill(memberLeaveListView.getEmployeeGh()));
                String employeeGh = memberLeaveListView.getEmployeeGh();
                String countProjName = memberLeaveService.countProjName(employeeGh);
                if (countProjName != null) {
                    memberLeaveListView.setCountProjName(countProjName);
                } else {
                    memberLeaveListView.setCountProjName(null);
                }
            }
            Integer leaveNum = loginInfoService.selectLeaveWait();
            log.info("调离申请列表返回成功");
            data.put("pageInfo", pageInfo);
            data.put("leaveNum", leaveNum);
            res.message.setMessage(200, "调离申请列表返回成功", data);
        } else {
            log.error("返回错误，调离申请数据为空");
            res.message.setMessage(400, "返回错误，调离申请数据为空");
        }
        return res.message;
    }

    /**
     * 返回Pm申请调离项目成员列表
     *
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/listPmMemberLeave")
    @ApiOperation(value = "返回Pm申请调离项目成员列表")
    public Message listPmMemberLeave(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        String employeeGh = res.user.getId();
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer pageNum = (Integer) map.get("pageNum");
        String sort = (String) map.get("sort");
        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum,10);
        List<MemberLeaveListView> list = memberLeaveService.listPmMemberLeave(employeeGh,sort);
        PageInfo<MemberLeaveListView> pageInfo = new PageInfo<>(list);
        if (pageInfo.getTotal()>0) {
            for (MemberLeaveListView memberLeaveListView : list) {
                memberLeaveListView.setSkillInfo(employeeService.listEmployeeSkill(memberLeaveListView.getEmployeeGh()));
            }
            log.info("Pm调离申请列表返回成功");
            data.put("pageInfo", pageInfo);
            res.message.setMessage(200, "Pm调离申请列表返回成功", data);
        } else {
            log.error("返回错误，调离申请数据为空");
            res.message.setMessage(400, "返回错误，调离申请数据为空");
        }
        return res.message;
    }

    /**
     * PMO审批调离申请
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/updateMemberLeave")
    @ApiOperation(value = "PMO审批调离申请")
    public Message updateMemberLeave(@RequestBody @ApiParam(name = "id\nmemberLeaveStatus\nprojectId\nestimateStartTime\nestimateEndTime\n" +
            "memberJob\nmemberSup\nmemberType\nmemberFunc", value = "调离申请信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = 0;
        Integer result1 = 0;
        String leaveApprovalGh = res.user.getId();
        Integer projectId = (Integer) map.get("projectId");
        if (map.get("memberLeaveStatus").equals("审批通过")) {
            memberLeaveService.updateMemberLeave(leaveApprovalGh, (String) map.get("memberLeaveStatus"), projectId, (Integer) map.get("id"));
            result = 1;
            if (projectId != null && map.get("memberJob") != null && map.get("estimateStartTime") != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowTime = df.format(new Date());
                Date nowDate = df.parse(nowTime);
                String startTime = map.get("estimateStartTime") + " 00:00:00";
                Date estimateStartTime = df.parse(startTime);
                if(nowDate.before(estimateStartTime)){
                    log.info("加入时间到了进组");
                    memberLeaveService.distributeProject((Integer) map.get("id"), projectId, (String) map.get("estimateStartTime"), (String) map.get("estimateEndTime"),
                            (String) map.get("memberJob"), (String) map.get("memberSup"), (String) map.get("memberType"), (String) map.get("memberFunc"));
                    result1 = 1;
                }else {
                    log.info("立即开始进组");
                    memberLeaveService.insertMemberByLeaveApply((Integer) map.get("id"), projectId, (String) map.get("estimateStartTime"), (String) map.get("estimateEndTime"),
                            (String) map.get("memberJob"), (String) map.get("memberSup"), (String) map.get("memberType"), (String) map.get("memberFunc"));
                    result1 = 1;
                }
                Integer fkProjectId = projectId;
                String fkEmployeeGh = memberService.findPmByProjId(fkProjectId);
                if(fkEmployeeGh==null){
                    log.error("PM工号为空");
                    res.message.setMessage(400,"PM工号为空");
                    return res.message;
                }
                String memberRecommendGh = memberLeaveService.findMemberLeaveGh((Integer) map.get("id"));
                String distributionGh = memberLeaveService.findMemberLeaveGh((Integer) map.get("id"));
                String applyApprovalGh = res.user.getId();
                String memberJoinSup = (String) map.get("memberSup");
                String memberJoinType = (String) map.get("memberType");
                String endTime = (String) map.get("estimateEndTime");
                Date memberEndTime = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date memberStartTime = sf.parse(startTime);
                if(endTime!=null){
                    memberEndTime = sf.parse(endTime);
                }
                memberService.insertIntoMemberApply(fkProjectId,fkEmployeeGh,memberRecommendGh,applyApprovalGh,distributionGh,
                        memberJoinSup,memberJoinType,memberStartTime,memberEndTime);
                log.info("审批通过");
            }
            MemberLeaveListView memberLeaveView = memberLeaveService.selectLeaveTime((Integer) map.get("id"));
            java.sql.Date realEndTime = memberLeaveView.getMemberLeaveTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = df.format(new Date());
            String endTime = df.format(new Date(realEndTime.getTime()));
            Date RealEndTime = df.parse(endTime);
            Date nowDate = df.parse(nowTime);
            if (nowDate.before(RealEndTime)) {
                log.info("离开时间到了进行调离");
            }else {
                log.info("立即开始调离");
                String memberRemarks = memberLeaveView.getMemberLeaveRemark();
                String employeeGh = memberLeaveView.getMemberLeaveGh();
                Integer result2 = memberLeaveService.leaveMember(memberRemarks, realEndTime, employeeGh, memberLeaveView.getProjectId());
                if (result2 > 0) {
                    log.info(employeeGh + " 员工调离成功");
                    Integer result3 = memberLeaveService.updateMemberIsLeave((Integer) map.get("id"));
                    if (result3 > 0) {
                        log.info(map.get("id") + " 调离申请已执行");
                    } else {
                        log.error(map.get("id") + " 调离申请未执行");
                    }
                } else {
                    log.error(employeeGh + " 员工调离失败");
                }
            }
        } else {
            memberLeaveService.updateMemberLeave(leaveApprovalGh, (String) map.get("memberLeaveStatus"), projectId, (Integer) map.get("id"));
            log.info("审批驳回");
        }
        if (result == 1 && result1 == 1) {
            log.info("审批成功，成功安排项目组");
            res.message.setMessage(200, "PMO审批调离申请成功，成功安排项目组");
        } else if (result == 1 && result1 == 0) {
            log.info("修改成功，审批驳回");
            res.message.setMessage(200, "PMO审批调离修改成功，审批驳回");
        } else {
            log.error("操作失败");
            res.message.setMessage(400, "数据修改失败");
        }
        return res.message;
    }

    /**
     * PM发起人员调离申请
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/insertMemberLeave")
    @ApiOperation(value = "PM发起人员调离申请", notes = "fkProjectId、memberLeaveGh、memberLeaveTime 必填，其余可为空：null")
    public Message insertMemberLeave(@RequestBody @ApiParam(name = "fkProjectId\nmemberLeaveGh\nmemberSkillEvaluation\n" +
            "memberApplicationReason\nmemberLeaveTime\nmemberLeaveRemark", value = "调离申请信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer fkProjectId = (Integer) map.get("fkProjectId");
        String fkEmployeeGh = res.user.getId();
        String memberLeaveGh = (String) map.get("memberLeaveGh");
        String memberSkillEvaluation = (String) map.get("memberSkillEvaluation");
        String memberLeaveTime = (String) map.get("memberLeaveTime");
        String memberLeaveRemark = (String) map.get("memberLeaveRemark");

        memberLeaveService.insertMemberLeave(fkProjectId, fkEmployeeGh, memberLeaveGh, memberSkillEvaluation,
                memberLeaveTime, memberLeaveRemark);

        log.info("PM发起人员调离申请成功");
        res.message.setMessage(200, "PM发起人员调离申请成功");

        //发送钉钉消息通知
        SendMessageUtil.PMOMessageSend("您有新的人员调离待审批，请尽快完成审批！\n(*^v^*)点击详情前往Harmage");
        return res.message;
    }

    /**
     * 人员是否已申请调离
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/ifHadMemberLeave")
    @ApiOperation(value = "人员是否已申请调离")
    public Message ifHadMemberLeave(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer projectId = (Integer) map.get("projectId");
        String employeeGh = (String) map.get("employeeGh");
        Integer id = memberLeaveService.ifHadMemberLeave(projectId, employeeGh);
        if (id != null) {
            log.warn("此员工已申请调离");
            data.put("if", false);
            res.message.setMessage(200, "此员工已申请调离，请勿重复申请", data);
        } else {
            log.info("此员工允许申请调离");
            data.put("if", true);
            res.message.setMessage(200, "此员工允许申请调离", data);
        }
        return res.message;
    }
}
