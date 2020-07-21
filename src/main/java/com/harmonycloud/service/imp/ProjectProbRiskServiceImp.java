package com.harmonycloud.service.imp;

import com.harmonycloud.bean.project.ProjectProblem;
import com.harmonycloud.bean.project.ProjectRisk;
import com.harmonycloud.dao.ProjectProbRiskDao;
import com.harmonycloud.service.ProjectProbRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectProbRiskServiceImp implements ProjectProbRiskService {
    @Autowired
    ProjectProbRiskDao projectProbRiskDao;

    @Override
    public List<ProjectProblem> listProjectProblemById(Integer projectId) {
        return projectProbRiskDao.listProjectProblemById(projectId);
    }

    @Override
    public List<ProjectRisk> listProjectRiskById(Integer projectId) {
        return projectProbRiskDao.listProjectRiskById(projectId);
    }

    @Override
    public Integer insertProjectProblem(ProjectProblem projectProblem) {
        return projectProbRiskDao.insertProjectProblem(projectProblem);
    }

    @Override
    public Integer insertProjectRisk(ProjectRisk projectRisk) {
        return projectProbRiskDao.insertProjectRisk(projectRisk);
    }

    @Override
    public Integer updateProjectProblem(ProjectProblem projectProblem) {
        return projectProbRiskDao.updateProjectProblem(projectProblem);
    }

    @Override
    public Integer updateProjectRisk(ProjectRisk projectRisk) {
        return projectProbRiskDao.insertProjectRisk(projectRisk);
    }

    @Override
    public Integer deleteProjectProblem(Integer problemId) {
        return projectProbRiskDao.deleteProjectProblem(problemId);
    }

    @Override
    public Integer deleteProjectRisk(Integer riskId) {
        return projectProbRiskDao.deleteProjectRisk(riskId);
    }
}
