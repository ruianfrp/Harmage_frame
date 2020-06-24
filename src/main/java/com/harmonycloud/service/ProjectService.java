package com.harmonycloud.service;

import com.harmonycloud.bean.project.Project;
import com.harmonycloud.bean.project.ProjectListView;
import com.harmonycloud.bean.project.ProjectStatusBean;

import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
public interface ProjectService {
    List<ProjectListView> listProject();

    List<ProjectListView> listOverViewProject();

    int updateByPrimaryKeySelective(Project project);

    Integer findCustomerIdByProjId(Long ProjId);

    List<ProjectListView> listPmProject(String employeeGh);

    Long findProjIdByprojName(String ProjName);

    List<Integer> selectProjectIdInMilestone();

    List<String> selectMilestoneStatus(Integer projectId);

    ProjectListView selectProjectById(Integer id);

    List<ProjectStatusBean> getStopProjectDay(String status);

    int updateStopStatusForProject(int projectId, String status);

    List<Integer> getProjectId();

    Project getProject(String projectName);
}
