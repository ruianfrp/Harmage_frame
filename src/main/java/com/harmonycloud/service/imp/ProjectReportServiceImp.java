package com.harmonycloud.service.imp;

import com.harmonycloud.bean.report.ProjectReport;
import com.harmonycloud.dao.ProjectReportDao;
import com.harmonycloud.service.ProjectReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectReportServiceImp implements ProjectReportService {

    @Autowired
    ProjectReportDao projectReportDao;

    @Override
    public int insertReportDataBase(List<ProjectReport> projectReportList) {
        return  projectReportDao.insertReportDataBase(projectReportList);
    }

    @Override
    public ArrayList<ProjectReport> getProjectReport(String projectName) {
        return projectReportDao.getProjectReport(projectName);
    }
}
