package com.harmonycloud.service;

import com.harmonycloud.bean.report.ProjectReport;

import java.util.ArrayList;
import java.util.List;

public interface ProjectReportService {
    int insertReportDataBase(List<ProjectReport> projectReportList);

    ArrayList<ProjectReport> getProjectReport(String projectName);
}
