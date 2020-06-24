package com.harmonycloud.util.ScheduleTask;

import com.harmonycloud.bean.enumeration.ProjectMilestoneStatus;
import com.harmonycloud.service.MilestoneService;
import com.harmonycloud.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@EnableScheduling   //开启定时任务
@EnableAsync        //开启多线程
@Slf4j
public class ProjectScheduleTask {

    @Autowired
    ProjectService projectService;

    @Autowired
    MilestoneService milestoneService;

    @Async
    @Scheduled(cron = "0 30 14 * * ?")
    public void projectChangeStatus() {
        List<Integer> listProjectId = projectService.getProjectId();
        if (listProjectId.size() > 0) {
            log.info("里程碑返回项目id成功");
            for (short i = 0; i < listProjectId.size(); i++) {
                Integer project_id = listProjectId.get(i);
                System.out.println(project_id);
                List<String> listStatus = projectService.selectMilestoneStatus(project_id);
                listStatus.stream().forEach(a -> System.out.println(a));
                String flag = new String();
                if (listStatus.size() > 0) {
                    if (listStatus.contains("延期中") || listStatus.contains("严重延期")) {
                        System.out.println(ProjectMilestoneStatus.getProjectMilestoneStatus("严重延期").getProjectStatus());
                        milestoneService.updateProjectStatus(ProjectMilestoneStatus.getProjectMilestoneStatus("严重延期").getProjectStatus(), ProjectMilestoneStatus.getProjectMilestoneStatus("严重延期").getProjectSubState(), project_id);
                    } else if (listStatus.contains("顺利进行")) {
                        System.out.println(ProjectMilestoneStatus.getProjectMilestoneStatus("顺利进行").getProjectStatus());
                        milestoneService.updateProjectStatus(ProjectMilestoneStatus.getProjectMilestoneStatus("顺利进行").getProjectStatus(), ProjectMilestoneStatus.getProjectMilestoneStatus("顺利进行").getProjectSubState(), project_id);
                    } else {
                        System.out.println("完成");
                        milestoneService.updateProjectStatus("完成", "完成", project_id);
                    }
                } else {
                    log.error("项目 " + project_id + " 里程碑状态返回为空,项目未开始");
                    System.out.println("未開始");
                    milestoneService.updateProjectStatus(ProjectMilestoneStatus.SMOOTHLY.getProjectStatus(), ProjectMilestoneStatus.SMOOTHLY.getProjectSubState(), project_id);
                }
//                if(listStatus.size()>0){
//                    log.info("项目 " + project_id + " 里程碑状态返回成功");
//                    for(short j = 0;j<listStatus.size();j++){
//                        if (listStatus.get(j).equals("顺利进行") && flag.equals("")) {
//                            flag = "正常进行";
//                        }
//                        if (listStatus.get(j).equals("延期中") && ((flag.equals("")) || flag.equals("正常进行"))) {
//                            flag = "延期中";
//                        }
//                        if (listStatus.get(j).equals("严重延期") && ((flag.equals("")) || flag.equals("正常进行") || flag.equals("延期中"))) {
//                            flag = "严重延期";
//                        }
//                    }
//                    milestoneService.updateProjectStatus(flag, project_id);
//                }else {
//                    log.error("项目 " + project_id + " 里程碑状态返回为空");
//                }
            }
        } else {
            log.error("里程碑返回项目id为空");
        }
    }
}
