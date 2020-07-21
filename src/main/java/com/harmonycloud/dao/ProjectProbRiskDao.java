package com.harmonycloud.dao;

import com.harmonycloud.bean.project.ProjectProblem;
import com.harmonycloud.bean.project.ProjectRisk;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectProbRiskDao {
    List<ProjectProblem> listProjectProblemById(Integer projectId);

    List<ProjectRisk> listProjectRiskById(Integer projectId);

    Integer insertProjectProblem(ProjectProblem projectProblem);

    Integer insertProjectRisk(ProjectRisk projectRisk);

    Integer updateProjectProblem(ProjectProblem projectProblem);

    Integer updateProjectRisk(ProjectRisk projectRisk);

    Integer deleteProjectProblem(Integer problemId);

    Integer deleteProjectRisk(Integer riskId);

}
