package com.harmonycloud.controller;

import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.apply.StopAndRestartApplyBean;
import com.harmonycloud.bean.enumeration.ProjectMilestoneStatus;
import com.harmonycloud.bean.enumeration.ProjectStatus;
import com.harmonycloud.service.MilestoneService;
import com.harmonycloud.service.ProjectService;
import com.harmonycloud.service.ProjectStatusCacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "审批controller", tags = {"暂停和重启审批操作接口"})
@RequestMapping("/approval")
public class SuspendedAndRestartController {

    @Autowired
    ProjectService projectService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    ProjectStatusCacheService projectStatusCacheService;

    /**
     * 获取暂停/启动申请列表
     */
    @PostMapping("/getApply")
    @ApiOperation(value = "获取暂停/启动申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "type", value = "获取类型（0：未审批；1审批通过；2：驳回；3：全部）", required = true),
            @ApiImplicitParam(paramType = "query", name = "action", value = "获取类型（STOP表示暂停，RESTART表示重启）", required = true)
    })
    public Message getApply(String action, Integer type) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Message message = new Message();
        int[] typerArray = (type == 3 ? new int[]{0, 1, 2} : new int[]{type});
        List<Integer> typeArrayList = Arrays.stream(typerArray).boxed().collect(Collectors.toList());
        List<StopAndRestartApplyBean> stopAndRestartApplyBeans = projectStatusCacheService.getProjectApply(action, typeArrayList);
        message.setMessage(200, "列表数据获取成功");
        message.setData(stopAndRestartApplyBeans);
        return message;
    }

    /**
     * 通过暂停申请
     */
    @PostMapping("/agreeToStopTheProject")
    @ApiOperation(value = "通过暂停申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "暂停项目ID", required = true)
    })
    public Message agreeToStopTheProject(Integer projectId) {
//        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
        Message message = new Message();
        int updateProjectStatusFlagCount = projectStatusCacheService.updateProjectStatusFlag(projectId, ProjectStatus.STOP.getStatus(), 1);
        if (updateProjectStatusFlagCount > 0) {
            projectService.updateStopStatusForProject(projectId, ProjectStatus.STOP.getStatus());
            message.setMessage(200, "暂停申请成功");
        }
        return message;
    }

    /**
     * 通过重启申请
     */
    @PostMapping("/agreeToRestartTheProject")
    @ApiOperation(value = "通过重启申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "重启项目ID", required = true)
    })
    public Message agreeToRestartTheProject(Integer projectId) {
//        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
        int updateProjectStatusFlagCount = projectStatusCacheService.updateProjectStatusFlag(projectId, ProjectStatus.RESTART.getStatus(), 1);
        if (updateProjectStatusFlagCount > 0) {
            projectService.updateStopStatusForProject(projectId, ProjectStatus.RESTART.getStatus());
            List<String> listStatus = projectService.selectMilestoneStatus(projectId);
            if (listStatus.size() > 0) {
                if (listStatus.contains("延期中") || listStatus.contains("严重延期")) {
                    milestoneService.updateProjectStatus(ProjectMilestoneStatus.getProjectMilestoneStatus("严重延期").getProjectStatus(), ProjectMilestoneStatus.getProjectMilestoneStatus("严重延期").getProjectSubState(), projectId);
                } else if (listStatus.contains("顺利进行")) {
                    milestoneService.updateProjectStatus(ProjectMilestoneStatus.getProjectMilestoneStatus("顺利进行").getProjectStatus(), ProjectMilestoneStatus.getProjectMilestoneStatus("顺利进行").getProjectSubState(), projectId);
                } else {
                    milestoneService.updateProjectStatus("完成", "完成", projectId);
                }
            } else {
                log.error("项目 " + projectId + " 里程碑状态返回为空,项目未开始");
                milestoneService.updateProjectStatus(ProjectMilestoneStatus.SMOOTHLY.getProjectStatus(),ProjectMilestoneStatus.SMOOTHLY.getProjectSubState() , projectId);
            }
        }
//        res.message.setMessage(200, "通过重启申请");
//        return res.message;
        Message message = new Message();
        message.setMessage(200, "通过重启");
        return message;
    }

    /**
     * 项目驳回
     */
    @PostMapping("/rejectedApply")
    @ApiOperation(value = "项目驳回")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "驳回项目ID", required = true),
            @ApiImplicitParam(paramType = "query", name = "action", value = "获取类型（STOP表示暂停，RESTART表示重启）", required = true)
    })
    public Message rejectedApply(Integer projectId, String action) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        int updateProjectStatusFlagCount = projectStatusCacheService.updateProjectStatusFlag(projectId, action, 2);
        if(updateProjectStatusFlagCount > 0) { res.message.setMessage(200, "驳回申请成功"); }else{ res.message.setMessage(400, "驳回申请失败");}
        return res.message;
    }
}
