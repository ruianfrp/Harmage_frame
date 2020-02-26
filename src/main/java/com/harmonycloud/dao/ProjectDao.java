package com.harmonycloud.dao;

import com.harmonycloud.entity.Project;
import com.harmonycloud.view.ProjectListView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao {
    List<ProjectListView> listProject();

    int updateByPrimaryKeySelective(Project project);

    Integer findCustomerIdByProjId(Long ProjId);

    List<ProjectListView> listPmProject(String employeeGh);

    Long findProjIdByprojName(String ProjName);

    List<Integer> selectProjectIdInMilestone();

    List<String> selectMilestoneStatus(Integer projectId);

    ProjectListView selectProjectById(Integer id);
}