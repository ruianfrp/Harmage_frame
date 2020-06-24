package com.harmonycloud.util.ScheduleTask;

import com.harmonycloud.bean.execl.WorkingReportExcel;
import com.harmonycloud.service.WorkingReportService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Component
@EnableScheduling   //开启定时任务
@EnableAsync        //开启多线程
@Slf4j
public class workingReportTask {

    @Autowired
    WorkingReportService workingReportService;

    @Async
    @Scheduled(cron = "0 00 00 * * ?")
    public void insertWorkingHours() {
        List<WorkingReportExcel> workingReportExcelList = workingReportService.getWorkingTimeReport();
        workingReportExcelList.stream().forEach(a -> {
            System.out.println(a.getEmployeeId() + ":::" + a.getEmployeeName() + ":::" + a.getJoinTime());
        });
        Map<Integer, Integer> projectCount = new HashMap<>();
        workingReportExcelList.stream().forEach(workingReportExcel -> {
            Integer employeeId = workingReportExcel.getEmployeeId();
            Long leaveTime = ObjectUtils.isEmpty(workingReportExcel.getLeaveTime()) ? System.currentTimeMillis() : workingReportExcel.getLeaveTime().getTime();
            Long joinTime = ObjectUtils.isEmpty(workingReportExcel.getJoinTime()) ? System.currentTimeMillis() : workingReportExcel.getJoinTime().getTime();
            if (projectCount.containsKey(employeeId) && joinTime <= System.currentTimeMillis() && leaveTime >= System.currentTimeMillis()) {
                projectCount.put(employeeId, projectCount.get(employeeId) + 1);
            } else {
                projectCount.put(employeeId, 1);
            }
        });
        for(int i = 0; i < workingReportExcelList.size(); i++){
            WorkingReportExcel workingReportExcel = workingReportExcelList.get(i);
            Long leaveTime = ObjectUtils.isEmpty(workingReportExcel.getLeaveTime()) ? System.currentTimeMillis() : workingReportExcel.getLeaveTime().getTime();
            Long joinTime = ObjectUtils.isEmpty(workingReportExcel.getJoinTime()) ? System.currentTimeMillis() : workingReportExcel.getJoinTime().getTime();
            if(joinTime <= System.currentTimeMillis() && leaveTime >= System.currentTimeMillis()) {
                workingReportExcel.setWorkingHours(8D / projectCount.get(workingReportExcel.getEmployeeId()));
            } else {
                workingReportExcelList.remove(i);
                i--;
            }
        }
        workingReportService.insertWorkingReport(workingReportExcelList);
    }
}
