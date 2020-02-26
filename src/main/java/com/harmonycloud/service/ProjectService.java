package com.harmonycloud.service;

import com.harmonycloud.entity.Project;
import com.harmonycloud.view.ProjectListView;

import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
public interface ProjectService {
    List<ProjectListView> listProject();

    int updateByPrimaryKeySelective(Project project);

    Integer findCustomerIdByProjId(Long ProjId);

    List<ProjectListView> listPmProject(String employeeGh);

    Long findProjIdByprojName(String ProjName);

    List<Integer> selectProjectIdInMilestone();

    List<String> selectMilestoneStatus(Integer projectId);

    ProjectListView selectProjectById(Integer id);
}
