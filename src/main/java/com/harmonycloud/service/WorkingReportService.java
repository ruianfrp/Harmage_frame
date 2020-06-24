package com.harmonycloud.service;

import com.harmonycloud.bean.execl.WorkingReportExcel;

import java.util.List;

public interface WorkingReportService {
    List<WorkingReportExcel> getWorkingTimeReport();

    int insertWorkingReport(List<WorkingReportExcel> workingReportExcelList);

    List<WorkingReportExcel> getTotalWorkingTimeReport(String startTime, String endTime);
}
