package com.harmonycloud.dao;

import com.harmonycloud.bean.execl.WorkingReportExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingReportDao {
    List<WorkingReportExcel> getWorkingTimeReport();

    int insertWorkingReport(List<WorkingReportExcel> workingReportExcelList);

    List<WorkingReportExcel> getTotalWorkingTimeReport(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
