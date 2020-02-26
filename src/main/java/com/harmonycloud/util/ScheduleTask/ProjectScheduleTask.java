package com.harmonycloud.util.ScheduleTask;

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
    @Scheduled(cron = "0 0 3 * * ?")
    public void projectChangeStatus() throws Exception {
        List<Integer> listProjectId = projectService.selectProjectIdInMilestone();
        if(listProjectId.size()>0){
            log.info("里程碑返回项目id成功");
            for(short i=0;i<listProjectId.size();i++){
                Integer project_id = listProjectId.get(i);
                List<String> listStatus = projectService.selectMilestoneStatus(project_id);
                String flag = new String();
                if(listStatus.size()>0){
                    log.info("项目 " + project_id + " 里程碑状态返回成功");
                    for(short j = 0;j<listStatus.size();j++){
                        if (listStatus.get(j).equals("顺利进行") && flag.equals("")) {
                            flag = "正常进行";
                        }
                        if (listStatus.get(j).equals("延期中") && ((flag.equals("")) || flag.equals("正常进行"))) {
                            flag = "延期中";
                        }
                        if (listStatus.get(j).equals("严重延期") && ((flag.equals("")) || flag.equals("正常进行") || flag.equals("延期中"))) {
                            flag = "严重延期";
                        }
                    }
                    milestoneService.updateProjectStatus(flag, project_id);
                }else {
                    log.error("项目 " + project_id + " 里程碑状态返回为空");
                }
            }
        }else {
            log.error("里程碑返回项目id为空");
        }
    }
}
