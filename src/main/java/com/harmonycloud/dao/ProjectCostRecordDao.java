package com.harmonycloud.dao;

import com.harmonycloud.bean.project.Project;
import com.harmonycloud.bean.project.ProjectCostRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectCostRecordDao {
    Integer insertCostRecordDataBase(ProjectCostRecord projectCostRecord);

    int updateProjectCurrentCost(Integer projectId,double amount);

    List<ProjectCostRecord> selectProjectCostRecord(Integer projectId);

    String selectProjectName(Integer projectId);

}
