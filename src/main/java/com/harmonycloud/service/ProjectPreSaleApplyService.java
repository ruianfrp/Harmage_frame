package com.harmonycloud.service;

import com.harmonycloud.bean.project.ProjectPreSaleApply;

import java.util.List;

public interface ProjectPreSaleApplyService {
    Integer insertProjectPreSaleApply(ProjectPreSaleApply projectPreSaleApply);

    Integer updateProjectPreSaleApply(ProjectPreSaleApply projectPreSaleApply);

    Integer updateStates(Long id,int status);

    List<ProjectPreSaleApply> listProjectPreSaleApply();
}
