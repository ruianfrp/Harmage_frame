package com.harmonycloud.controller;


import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.member.Member;
import com.harmonycloud.bean.member.MemberView;
import com.harmonycloud.service.MemberService;
import com.harmonycloud.util.SyncInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.harmonycloud.util.JsonWebToken.VerifyCode;
/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="项目成员controller",tags={"项目成员操作接口"})
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 根据项目编号返回成员列表
     *
     * @param projectId 项目id
     * @return res.message
     */
    @GetMapping("/listMember")
    @ApiOperation(value = "返回成员列表")
    public Message listMember(@ApiParam(name = "projectId", value = "项目Id", required = true) Long projectId) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<MemberView> list = memberService.listMember(projectId);
        if (list != null) {
            log.info("成员信息返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，成员信息数据为空");
            res.message.setMessage(200, "数据为空");
        }
        return res.message;
    }

    /**
     * 返回历史成员列表
     *
     * @param projectId 项目id
     * @return res.message
     */
    @GetMapping("/listAllMember")
    @ApiOperation(value = "返回历史成员列表")
    public Message listAllMember(@ApiParam(name = "projectId", value = "项目Id", required = true) Long projectId) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<MemberView> list = memberService.listAllMember(projectId);
        if (list.size() > 0) {
            log.info("历史成员返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "历史成员返回成功", data);
        } else {
            log.warn("无历史成员");
            res.message.setMessage(200, "无历史成员");
        }
        return res.message;
    }

    /**
     * 修改项目成员信息
     *
     * @param memberView
     * @return message
     */
    @PostMapping("/updateMember")
    @ApiOperation(value = "修改项目成员信息")
    public Message updateMember(@RequestBody MemberView memberView) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Member member = new Member();
        member.setId(memberView.getId());
        member.setMemberJob(memberView.getMemberJob());
        member.setMemberSup(memberView.getMemberSup());
        member.setMemberFunc(memberView.getMemberFunc());
        member.setMemberType(memberView.getMemberType());
        member.setMemberBonus(memberView.getMemberBonus());
        member.setMemberJobBand(memberView.getMemberJobBand());
        member.setMemberRemarks(memberView.getMemberRemarks());
        member.setEstimateStartTime(memberView.getEstimateStartTime());
        member.setEstimateEndTime(memberView.getEstimateEndTime());
        member.setRealEndTime(memberView.getRealEndTime());
        member.setRealStartTime(memberView.getRealStartTime());

        int result = memberService.updateByPrimaryKeySelective(member);
        if (result > 0) {
            log.info("成员信息修改成功");
            res.message.setMessage(200, "数据返回成功");
        } else {
            log.error("返回错误，成员信息修改失败");
            res.message.setMessage(403, "数据修改失败");
        }
        return res.message;
    }

    /**
     * 增加项目成员
     *
     * @param member
     * @return message
     */
    @PostMapping("/insertMember")
    @ApiOperation(value = "增加项目成员")
    public Message insertMember(@RequestBody Member member) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowString = dateFormat.format(date);
        Date now = dateFormat.parse(nowString);
        if (now.before(member.getEstimateStartTime())) {
            member.setIsQuit(3);
            String applyApprovalGh = res.user.getId();
            Integer fkProjectId = member.getFkProjectId();
            String fkEmployeeGh = memberService.findPmByProjId(fkProjectId);
            if (fkEmployeeGh == null) {
                log.error("PM工号为空");
                res.message.setMessage(400, "PM工号为空");
                return res.message;
            }
            String memberRecommendGh = member.getFkEmployeeGh();
            String distributionGh = member.getFkEmployeeGh();
            String memberJoinSup = member.getMemberSup();
            String memberJoinType = member.getMemberType();
            Date memberStartTime = member.getEstimateStartTime();
            Date memberEndTime = member.getEstimateEndTime();
            Integer result = memberService.insertIntoMemberApply(fkProjectId, fkEmployeeGh, memberRecommendGh, applyApprovalGh,
                    distributionGh, memberJoinSup, memberJoinType, memberStartTime, memberEndTime);
            if (result > 0) {
                log.info("PMO添加成员成功");
            } else {
                log.error("PMO添加成员失败");
                res.message.setMessage(403, "PMO添加成员失败");
                return res.message;
            }
        } else {
            member.setIsQuit(0);
            member.setRealStartTime(now);
        }
        int result = memberService.insertSelective(member);
        if (result > 0) {
            log.info("成员信息添加成功");
            res.message.setMessage(200, "成员信息添加成功");
        } else {
            log.error("返回错误，成员信息修改成功");
            res.message.setMessage(400, "数据修改失败");
        }
        return res.message;
    }

    /**
     * PMO调离项目成员
     *
     * @param map
     * @return message
     */
    @PostMapping("/memberLeave")
    @ApiOperation(value = "更新项目成员")
    public Message memberLeave(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String leaveTime = (String) map.get("memberLeaveTime");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date());
        Date leaveDate = df.parse(leaveTime);
        Date nowDate = df.parse(nowTime);
        if (nowDate.before(leaveDate)) {
            Integer projectId = (Integer) map.get("projectId");
            String employeeGh = memberService.selectPmGh(projectId);
            String memberLeaveGh = (String) map.get("memberLeaveGh");
            String leaveApprovalGh = res.user.getId();
            String memberLeaveRemark = (String) map.get("memberLeaveRemark");
            Integer result = memberService.insertPMOMemberLeave(projectId, employeeGh, memberLeaveGh, leaveApprovalGh, leaveDate, memberLeaveRemark);
            if (result > 0) {
                log.info("PMO调离人员成功");
                res.message.setMessage(200, "PMO调离人员成功");
            } else {
                log.error("PMO调离人员失败");
                res.message.setMessage(403, "PMO调离人员成功");
            }
        } else {
            int memberId = (int) map.get("memberId");
            int result = memberService.updateMember(memberId);
            if (result > 0) {
                log.info("成员信息修改成功");
                res.message.setMessage(200, "PMO调离人员成功");
            } else {
                log.error("返回错误，成员信息修改成功");
                res.message.setMessage(403, "PMO调离人员成功");
            }
        }
        return res.message;
    }

    /**
     * 获取memberJob的列表
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/listMemberJob")
    @ApiOperation("获取memberJob的列表")
    public Message listMemberJob(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer projectId = (Integer) map.get("projectId");
        List<String> list = memberService.listMemberJob(projectId);
        if (list.size() > 0) {
            log.info("memberJob列表返回成功");
            data.put("list", list);
            res.message.setMessage(200, "memberJob列表返回成功", data);
        } else {
            log.warn("memberJob列表返回失败");
            res.message.setMessage(200, "memberJob列表返回失败");
        }
        return res.message;
    }





}
