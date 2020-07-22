package com.harmonycloud.service;

import com.harmonycloud.bean.project.ProjectProbRisk;
import com.harmonycloud.bean.project.ProjectProbRiskState;

import java.util.List;

public interface ProjectProbRiskService {
    List<ProjectProbRisk> listProjectProbRiskById(Integer projectId);

    Integer insertProjectProbRisk(ProjectProbRisk projectProbRisk);

    Integer updateProjectProbRisk(ProjectProbRisk projectProbRisk);

    Integer deleteProjectProbRisk(Integer id);

    List<ProjectProbRiskState> listProjectProbRiskState();

    String selectProbRiskDescription(Integer projectId);
}
