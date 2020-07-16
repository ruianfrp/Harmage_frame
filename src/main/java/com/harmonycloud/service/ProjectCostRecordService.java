package com.harmonycloud.service;

import com.harmonycloud.bean.project.ProjectCostRecord;

import java.util.List;

public interface ProjectCostRecordService {
    Integer insertCostRecordDataBase(ProjectCostRecord projectCostRecord);

    int updateProjectCurrentCost(Integer projectId,double amount);

    List<ProjectCostRecord> selectProjectCostRecord(Integer projectId);

    String selectProjectName(Integer projectId);
}
