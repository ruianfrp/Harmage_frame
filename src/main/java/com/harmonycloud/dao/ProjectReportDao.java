package com.harmonycloud.dao;

import com.harmonycloud.bean.report.ProjectReport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProjectReportDao {
    int insertReportDataBase(List<ProjectReport> projectReportList);
    ArrayList<ProjectReport> getProjectReport(String projectName);
}
