package com.harmonycloud.util.ScheduleTask;

import com.harmonycloud.bean.milestone.MilestoneStatusView;
import com.harmonycloud.service.MilestoneService;
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
public class MilestoneScheduleTask {

    @Autowired
    MilestoneService milestoneService;

    @Async
    @Scheduled(cron = "0 0 2 * * ?")
    public void projectChangeStatus(){
        List<MilestoneStatusView> listMilestone = milestoneService.selectMilestoneInfo();
        if(listMilestone.size()>0){
            log.info("里程碑信息获取成功");
            String flag;
            for(short i=0;i<listMilestone.size();i++){
                double exceedDay = listMilestone.get(i).getExceedDay();
                double percentRequireDay = listMilestone.get(i).getRequireDay() * 0.4;
                if(exceedDay>=percentRequireDay){
                    log.info("延期时间超出预计时间40%");
                    flag = "严重延期";
                }else {
                    log.info("延期时间未超出预计时间40%");
                    flag = "延期中";
                }
                Integer result = milestoneService.updateScheduleStatus(flag,listMilestone.get(i).getMilestoneIndex(),listMilestone.get(i).getFkProjectId());
                if(result>0){
                    log.info("里程碑状态修改成功");
                }else {
                    log.error("里程碑状态修改失败");
                }
            }
        }else {
            log.warn("没有需要修改的里程碑状态");
        }
    }
}
