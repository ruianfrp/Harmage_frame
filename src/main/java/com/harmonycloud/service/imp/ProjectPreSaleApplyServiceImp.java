package com.harmonycloud.service.imp;

import com.harmonycloud.bean.project.ProjectPreSaleApply;
import com.harmonycloud.dao.ProjectPreSaleApplyDao;
import com.harmonycloud.service.ProjectPreSaleApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectPreSaleApplyServiceImp implements ProjectPreSaleApplyService {
    @Autowired
    ProjectPreSaleApplyDao projectPreSaleApplyDao;

    @Override
    public Integer insertProjectPreSaleApply(ProjectPreSaleApply projectPreSaleApply) {
        return projectPreSaleApplyDao.insertProjectPreSaleApply(projectPreSaleApply);
    }

    @Override
    public Integer updateProjectPreSaleApply(ProjectPreSaleApply projectPreSaleApply) {
        return projectPreSaleApplyDao.updateProjectPreSaleApply(projectPreSaleApply);
    }

    @Override
    public Integer updateStates(Long id, int status) {
        return projectPreSaleApplyDao.updateStatus(id,status);
    }

    @Override
    public List<ProjectPreSaleApply> listProjectPreSaleApply() {
        return projectPreSaleApplyDao.listProjectPreSaleApply();
    }
}
