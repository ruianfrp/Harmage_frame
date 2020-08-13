package com.harmonycloud.service.imp;

import com.harmonycloud.bean.project.ProjectCostRecord;
import com.harmonycloud.dao.ProjectCostRecordDao;
import com.harmonycloud.service.ProjectCostRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectCostRecordServiceImp implements ProjectCostRecordService {
    @Autowired
    ProjectCostRecordDao projectCostRecordDao;

    @Override
    public Integer insertCostRecordDataBase(ProjectCostRecord projectCostRecord) {
        return projectCostRecordDao.insertCostRecordDataBase(projectCostRecord);
    }

    @Override
    public int updateProjectCurrentCost(Integer projectId,double money) {
        return projectCostRecordDao.updateProjectCurrentCost(projectId,money);
    }

    @Override
    public List<ProjectCostRecord> selectProjectCostRecord(Integer projectId) {
        return projectCostRecordDao.selectProjectCostRecord(projectId);
    }

    @Override
    public String selectProjectName(Integer projectId) {
        return projectCostRecordDao.selectProjectName(projectId);
    }

    @Override
    public Integer selectProjectId(String projectNameInExcel) {
        return projectCostRecordDao.selectProjectId(projectNameInExcel);
    }
}
