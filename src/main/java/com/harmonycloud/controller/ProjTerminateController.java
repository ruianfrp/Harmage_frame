package com.harmonycloud.controller;

import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.project.ProjectEndMsgView;
import com.harmonycloud.service.ProjEndApplyService;
import com.harmonycloud.service.ProjectFileService;
import com.harmonycloud.util.SendMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

/**
 * Created by SunRuiBin on 2020/7/27.
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="终止申请controller",tags={"终止申请操作接口"})
@RequestMapping("/projectTerminateApply")
public class ProjTerminateController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    ProjEndApplyService projTermApplyService;



    @Autowired
    ProjEndApplyService projEndApplyService;



    @Autowired
    ProjectFileService projectFileService;


    /**
     * PM发起终止申请
     *
     * @param map
     * @return res.message
     * @throws Exception
     */

    @PostMapping("/insertProjTerminateApply")
    @ApiOperation(value = "PM发起终止申请", notes = "fkProjectId、projEndTime、projEndMeeting 必填，其余可为空：null")
    public Message insertProjEndApply(@RequestBody @ApiParam(name = "fkProjectId\nprojEndTime\napplyApprovalGh\n" +
            "projEndRemark", value = "终止申请信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer fkProjectId = (Integer) map.get("fkProjectId");
        String projEndTime = (String) map.get("projEndTime");
        String projEndRemark = (String) map.get("projEndRemark");
        String projEndMeeting = (String) map.get("projEndMeeting");
        String meetingTime = (String) map.get("meetingTime");
        String projEndType = "已申请";
        if (projEndMeeting.equals("是")) {
            String hadMeetingTime = projEndApplyService.selectMeetingTime(fkProjectId);
            if (hadMeetingTime != null) {
                meetingTime = hadMeetingTime;
                Integer result = projEndApplyService.insertProjEndApply(fkProjectId, projEndTime, null, projEndRemark, projEndMeeting, meetingTime);
                if (result > 0) {
                    log.info("PM发起终止申请成功");
                    res.message.setMessage(200, "PM发起终止申请成功");
                } else {
                    log.error("PM发起终止申请失败");
                    res.message.setMessage(403, "PM发起终止申请失败");
                    return res.message;
                }
            } else {
                Integer result = projEndApplyService.insertProjEndApply(fkProjectId, projEndTime, null, projEndRemark, projEndMeeting, meetingTime);
                if (result > 0) {
                    log.info("PM发起终止申请成功");
                    res.message.setMessage(200, "PM发起终止申请成功");
                } else {
                    log.error("发起终止申请失败");
                    res.message.setMessage(403, "发起终止申请失败");
                    return res.message;
                }
            }
            ProjectEndMsgView projectEndMsgView = projEndApplyService.selectProjEndMsgInfo(fkProjectId);
            if (projectEndMsgView != null) {
                projectEndMsgView.setRealEndTime(projEndTime);
                projectEndMsgView.setRemark(projEndRemark);
                Integer fileNum = projEndApplyService.selectFileNum(fkProjectId);
                projectEndMsgView.setFileNum(fileNum);
                //发送钉钉消息通知(终止通知)
                SendMessageUtil.projectEndMessageSend(projectEndMsgView);
            }
        } else {
            Integer result = projEndApplyService.insertProjEndApply(fkProjectId, projEndTime, null, projEndRemark, projEndMeeting, meetingTime);
            if (result > 0) {
                log.info("PM发起终止申请成功");
                res.message.setMessage(200, "PM发起终止申请成功");
            } else {
                log.error("发起终止申请失败");
                res.message.setMessage(403, "PM发起终止申请失败");
                return res.message;
            }
            ProjectEndMsgView projectEndMsgView = projEndApplyService.selectProjEndMsgInfo(fkProjectId);
            if (projectEndMsgView != null) {
                projectEndMsgView.setMeetingTime(meetingTime);
                Integer fileNum = projEndApplyService.selectFileNum(fkProjectId);
                projectEndMsgView.setFileNum(fileNum);
                //发送钉钉消息通知(终止会议通知)
                SendMessageUtil.projectEndMeetingMsgSend(projectEndMsgView);
            }
        }
        Integer result1 = projEndApplyService.updateProjectEndTypeByProjectId(projEndType, fkProjectId);
        if (result1 > 0) {
            log.info("项目终止状态修改成功");
        } else {
            log.error("项目终止状态修改失败");
        }
        return res.message;
    }
}
