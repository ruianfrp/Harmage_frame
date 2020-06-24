package com.harmonycloud.service.imp;

import com.harmonycloud.bean.execl.WorkingReportExcel;
import com.harmonycloud.dao.WorkingReportDao;
import com.harmonycloud.service.WorkingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingReportImp implements WorkingReportService {

    @Autowired
    WorkingReportDao workingReportDao;

    @Override
    public List<WorkingReportExcel> getWorkingTimeReport() {
        return workingReportDao.getWorkingTimeReport();
    }

    @Override
    public int insertWorkingReport(List<WorkingReportExcel> workingReportExcelList) {
        return workingReportDao.insertWorkingReport(workingReportExcelList);
    }

    @Override
    public List<WorkingReportExcel> getTotalWorkingTimeReport(String startTime, String endTime) {
        return workingReportDao.getTotalWorkingTimeReport(startTime, endTime);
    }
}
