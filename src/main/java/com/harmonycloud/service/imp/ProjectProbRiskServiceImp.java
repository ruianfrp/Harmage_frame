package com.harmonycloud.service.imp;

import com.harmonycloud.bean.project.ProjectProbRisk;
import com.harmonycloud.bean.project.ProjectProbRiskState;
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
    public List<ProjectProbRisk> listProjectProbRiskById(Integer projectId) {
        return projectProbRiskDao.listProjectProbRiskById(projectId);
    }

    @Override
    public Integer insertProjectProbRisk(ProjectProbRisk projectProbRisk) {
        return projectProbRiskDao.insertProjectProbRisk(projectProbRisk);
    }

    @Override
    public Integer updateProjectProbRisk(ProjectProbRisk projectProbRisk) {
        return projectProbRiskDao.updateProjectProbRisk(projectProbRisk);
    }

    @Override
    public Integer deleteProjectProbRisk(Integer id) {
        return projectProbRiskDao.deleteProjectProbRisk(id);
    }

    @Override
    public List<ProjectProbRiskState> listProjectProbRiskState() {
        return projectProbRiskDao.listProjectProbRiskState();
    }

    @Override
    public String selectProbRiskDescription(Integer projectId) {
        return projectProbRiskDao.selectProbRiskDescription(projectId);
    }

    @Override
    public String selectPersonNameByEmployeeId(String employeeId) {
        return projectProbRiskDao.selectPersonNameByEmployeeId(employeeId);
    }
}
