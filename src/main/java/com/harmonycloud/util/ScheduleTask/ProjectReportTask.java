package com.harmonycloud.util.ScheduleTask;

import com.harmonycloud.bean.project.Project;
import com.harmonycloud.bean.report.ProjectReport;
import com.harmonycloud.service.ProjectReportService;
import com.harmonycloud.service.ProjectService;
import com.harmonycloud.util.date.DateUtils;
import com.harmonycloud.util.ding.DingUtils;
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
public class ProjectReportTask {
    @Autowired
    ProjectReportService projectReportService;

    @Autowired
    ProjectService projectService;

    @Async
    @Scheduled(cron = "0 0 23 ? * WED")
    public void insertReportDatabase() {
        DingUtils.getToken();
        List<ProjectReport> projectReportList = DingUtils.insertReportDataBase("项目周报（项目经理填写）");
        projectReportList.stream().forEach(aa -> {System.out.println(aa.getStartTime() + ":::" + aa.getProjectName() + ":::" + aa.getReport());});
        int insertNumber = projectReportService.insertReportDataBase(projectReportList);
        System.out.println(insertNumber);
    }
}
