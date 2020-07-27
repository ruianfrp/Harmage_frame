package com.harmonycloud.dao;

import com.harmonycloud.bean.project.ProjectProbRisk;
import com.harmonycloud.bean.project.ProjectProbRiskState;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectProbRiskDao {
    List<ProjectProbRisk> listProjectProbRiskById(Integer projectId);

    Integer insertProjectProbRisk( ProjectProbRisk projectProbRisk);

    Integer updateProjectProbRisk( ProjectProbRisk projectProbRisk);

    Integer deleteProjectProbRisk(Integer id);

    List<ProjectProbRiskState> listProjectProbRiskState();

    String selectProbRiskDescription(Integer projectId);

    String selectPersonNameByEmployeeId(String employeeId);

}
