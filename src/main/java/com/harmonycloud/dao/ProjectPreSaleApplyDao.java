package com.harmonycloud.dao;

import com.harmonycloud.bean.project.ProjectPreSaleApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPreSaleApplyDao {

    Integer insertProjectPreSaleApply(ProjectPreSaleApply projectPreSaleApply);

    Integer updateProjectPreSaleApply(ProjectPreSaleApply projectPreSaleApply);

    Integer updateStatus(@Param("id") Long id,@Param("status") int status);

    List<ProjectPreSaleApply> listProjectPreSaleApply();
}
