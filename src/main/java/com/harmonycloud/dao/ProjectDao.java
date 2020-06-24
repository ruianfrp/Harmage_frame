package com.harmonycloud.dao;

import com.harmonycloud.bean.project.Project;
import com.harmonycloud.bean.project.ProjectListView;
import com.harmonycloud.bean.project.ProjectStatusBean;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao {
    List<ProjectListView> listProject();

    List<ProjectListView> listOverViewProject();

    int updateByPrimaryKeySelective(Project project);

    Integer findCustomerIdByProjId(Long ProjId);

    List<ProjectListView> listPmProject(String employeeGh);

    Long findProjIdByprojName(String ProjName);

    List<Integer> selectProjectIdInMilestone();

    List<String> selectMilestoneStatus(@Param("projectId") int projectId);

    ProjectListView selectProjectById(Integer id);

    List<ProjectStatusBean> getStopProjectDay(@Param("status") String status);

    int updateStopStatusForProject(@Param("projectId") int projectId,@Param("status") String status);

    List<Integer> getProjectId();

    Project getProject(@Param("projectName") String ProjectName);
}