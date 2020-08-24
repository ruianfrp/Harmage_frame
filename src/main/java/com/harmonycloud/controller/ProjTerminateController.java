package com.harmonycloud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.project.ProjEndApplyDetailView;
import com.harmonycloud.bean.project.ProjEndApplyListView;
import com.harmonycloud.bean.project.ProjectEndMsgView;
import com.harmonycloud.bean.project.ProjectFileView;
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
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;
import static org.apache.commons.io.FileUtils.deleteDirectory;

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
    ProjectFileService projectFileService;




    /**
     * 返回PM终止申请列表
     *
     * @return message
     * @throws Exception
     */
    @PostMapping("/listPMProjectEndApply")
    @ApiOperation(value = "返回PM终止申请列表")
    public Message listPMProjEndApply(@RequestBody Integer pageNum) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String projPMGh = res.user.getId();
        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum,10);
        List<ProjEndApplyListView> list = projTermApplyService.listPMProjEndApply(projPMGh);
        PageInfo<ProjEndApplyListView> pageInfo = new PageInfo<>(list);
        if (pageInfo.getTotal()>0) {
            log.info("终止申请列表返回成功");
            data.put("pageInfo", pageInfo);
            res.message.setMessage(200, "终止申请列表返回成功", data);
        } else {
            log.error("返回错误，终止申请列表数据为空");
            res.message.setMessage(400, "返回错误，终止申请列表数据为空");
        }
        return res.message;
    }



    /**
     * 返回PMO终止申请列表
     *
     * @return message
     * @throws Exception
     */
    @PostMapping("/listProjectTerminateApply")
    @ApiOperation(value = "返回PMO终止申请列表")
    public Message listPMOProjEndApply(@RequestBody Integer pageNum) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum,10);
        List<ProjEndApplyListView> list = projTermApplyService.listPMOProjEndApply();
        PageInfo<ProjEndApplyListView> pageInfo = new PageInfo<>(list);
        if (pageInfo.getTotal()>0) {
            log.info("终止申请列表返回成功");
            data.put("pageInfo", pageInfo);
            res.message.setMessage(200, "终止申请列表返回成功", data);
        } else {
            log.error("返回错误，终止申请列表数据为空");
            res.message.setMessage(400, "返回错误，终止申请列表数据为空");
        }
        return res.message;
    }


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
            String hadMeetingTime = projTermApplyService.selectMeetingTime(fkProjectId);
            if (hadMeetingTime != null) {
                meetingTime = hadMeetingTime;
                Integer result = projTermApplyService.insertProjEndApply(fkProjectId, projEndTime, null, projEndRemark, projEndMeeting, meetingTime);
                if (result > 0) {
                    log.info("PM发起终止申请成功");
                    res.message.setMessage(200, "PM发起终止申请成功");
                } else {
                    log.error("PM发起终止申请失败");
                    res.message.setMessage(403, "PM发起终止申请失败");
                    return res.message;
                }
            } else {
                Integer result = projTermApplyService.insertProjEndApply(fkProjectId, projEndTime, null, projEndRemark, projEndMeeting, meetingTime);
                if (result > 0) {
                    log.info("PM发起终止申请成功");
                    res.message.setMessage(200, "PM发起终止申请成功");
                } else {
                    log.error("发起终止申请失败");
                    res.message.setMessage(403, "发起终止申请失败");
                    return res.message;
                }
            }
            ProjectEndMsgView projectEndMsgView = projTermApplyService.selectProjEndMsgInfo(fkProjectId);
            if (projectEndMsgView != null) {
                projectEndMsgView.setRealEndTime(projEndTime);
                projectEndMsgView.setRemark(projEndRemark);
                Integer fileNum = projTermApplyService.selectFileNum(fkProjectId);
                projectEndMsgView.setFileNum(fileNum);
                //发送钉钉消息通知(终止通知)
                SendMessageUtil.projectTerminateMessageSend(projectEndMsgView);
            }
        } else {
            Integer result = projTermApplyService.insertProjEndApply(fkProjectId, projEndTime, null, projEndRemark, projEndMeeting, meetingTime);
            if (result > 0) {
                log.info("PM发起终止申请成功");
                res.message.setMessage(200, "PM发起终止申请成功");
            } else {
                log.error("发起终止申请失败");
                res.message.setMessage(403, "PM发起终止申请失败");
                return res.message;
            }
            ProjectEndMsgView projectEndMsgView = projTermApplyService.selectProjEndMsgInfo(fkProjectId);
            if (projectEndMsgView != null) {
                projectEndMsgView.setMeetingTime(meetingTime);
                Integer fileNum = projTermApplyService.selectFileNum(fkProjectId);
                projectEndMsgView.setFileNum(fileNum);
                //发送钉钉消息通知(终止会议通知)
                SendMessageUtil.projectTerminateMeetingMsgSend(projectEndMsgView);
            }
        }
        Integer result1 = projTermApplyService.updateProjectEndTypeByProjectId(projEndType, fkProjectId);
        if (result1 > 0) {
            log.info("项目终止状态修改成功");
        } else {
            log.error("项目终止状态修改失败");
        }
        return res.message;
    }


    @PostMapping("/projectTerminateApplyDetail")
    @ApiOperation(value = "返回终止申请详情")
    public Message projEndApplyDetail(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        int id = (int) map.get("id");
        ProjEndApplyDetailView detail = projTermApplyService.projEndApplyDetail(id);
        if (detail != null) {
            log.info("终止申请列表返回成功");
            Integer projectId = Math.toIntExact(detail.getFkProjId());
            List<ProjectFileView> list = projectFileService.listAllProjectFile(projectId);
            if (list.size() > 0) {
                log.info("项目文件列表返回成功");
                detail.setProjectFileViews(list);
                data.put("list", detail);
                res.message.setMessage(200, "终止申请列表返回成功(包含文件列表)", data);
            } else {
                data.put("list", detail);
                log.warn("项目文件返回为空");
                res.message.setMessage(200, "终止申请列表返回成功(不包含文件列表)", data);
            }
        } else {
            log.error("返回错误，终止申请详情数据为空");
            res.message.setMessage(400, "返回错误，终止申请详情数据为空");
        }
        return res.message;
    }
    
    /**
     * 审批接口
     *
     * @return message
     * @throws Exception
     */
    @PostMapping("/updateProjectTerminateApply")
    @ApiOperation(value = "审批")
    public Message updateProjEndApply(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String applyApprovalGh = res.user.getId();
        String applyStatus = (String) map.get("applyStatus");
        Integer id = (Integer) map.get("id");
        Integer id1 = id;
        Integer id2 = id;
        String projEndType = "未申请";
        String status;
        int result = projTermApplyService.updateProjEndApply(applyStatus, applyApprovalGh,id);
        if (result > 0) {
            if (applyStatus.equals("审批通过")) {
                Integer fkProjId = projTermApplyService.selectIfAgreeView(id);
                if (fkProjId != null) {
                    log.info("会议申请通过");
                    Integer result2 = projTermApplyService.updateProjectEndTypeById(projEndType, id);
                    if (result2 > 0) {
                        log.info("projEndType修改成功");
                    } else {
                        log.error("projEndType修改失败");
                    }
                } else {
                    String projEndTime = projTermApplyService.selectProjectEndTime(id);
                    String projPreendTime = projTermApplyService.selectProjectPreendTime(id);
                    if (projEndTime != null && projPreendTime != null) {
                        log.info("项目预计完成时间与实际结束时间返回正常");
                        Integer result1 = projTermApplyService.updateProjectStatus("完成", id1, id2);
                        if (result1 > 0) {
                            log.info("项目状态修改成功");
                        } else {
                            log.error("项目状态修改失败");
                        }
                    } else {
                        log.error("项目预计完成时间与实际结束时间返回失败");
                    }
                }
            } else {
                //删除终止申请文件
                String path = projTermApplyService.selectProjectEndFilePath(id);
                if (path != null) {
                    String filePath = "/var/upload" + path;
                    File file = new File(filePath);
                    deleteDirectory(file);
                    log.info("终验报告文件夹删除");
                    Integer result3 = projTermApplyService.deleteProjectEndFiles(id);
                    if (result3 > 0) {
                        log.info("数据库终验报告信息删除成功");
                    } else {
                        log.error("数据库终验报告信息删除失败");
                    }
                } else {
                    log.error("终验报告路径返回失败");
                }
                Integer result3 = projTermApplyService.updateProjectEndTypeById(projEndType, id);
                if (result3 > 0) {
                    log.info("projEndType修改成功");
                } else {
                    log.error("projEndType修改失败");
                }
            }
            log.info("操作成功");
            res.message.setMessage(200, "数据返回成功");
        } else {
            log.error("操作失败");
            res.message.setMessage(403, "数据修改失败");
        }
        return res.message;
    }

}
