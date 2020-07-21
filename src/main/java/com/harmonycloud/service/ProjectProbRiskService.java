package com.harmonycloud.service;

import com.harmonycloud.bean.project.ProjectProblem;
import com.harmonycloud.bean.project.ProjectRisk;

import java.util.List;

public interface ProjectProbRiskService {
    List<ProjectProblem> listProjectProblemById(Integer projectId);

    List<ProjectRisk> listProjectRiskById(Integer projectId);

    Integer insertProjectProblem(ProjectProblem projectProblem);

    Integer insertProjectRisk(ProjectRisk projectRisk);

    Integer updateProjectProblem(ProjectProblem projectProblem);

    Integer updateProjectRisk(ProjectRisk projectRisk);

    Integer deleteProjectProblem(Integer problemId);

    Integer deleteProjectRisk(Integer riskId);
}
