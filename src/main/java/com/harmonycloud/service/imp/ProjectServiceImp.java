package com.harmonycloud.service.imp;


import com.harmonycloud.bean.project.Project;
import com.harmonycloud.bean.project.ProjectListView;
import com.harmonycloud.bean.project.ProjectStatusBean;
import com.harmonycloud.dao.ProjectDao;
import com.harmonycloud.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
public class ProjectServiceImp implements ProjectService {


    @Autowired
    ProjectDao projectDao;

    @Override
    public List<ProjectListView> listProject() {
        return projectDao.listProject();
    }

    @Override
    public List<ProjectListView> listOverViewProject(){
        return projectDao.listOverViewProject();
    }

    @Override
    public int updateByPrimaryKeySelective(Project project){
        return projectDao.updateByPrimaryKeySelective(project);
    }

    @Override
    public Integer findCustomerIdByProjId(Long ProjId) {
        return projectDao.findCustomerIdByProjId(ProjId);
    }

    @Override
    public List<ProjectListView> listPmProject(String employeeGh) {
        return projectDao.listPmProject(employeeGh);
    }

    @Override
    public Long findProjIdByprojName(String ProjName) {
        return projectDao.findProjIdByprojName(ProjName);
    }

    @Override
    public List<Integer> selectProjectIdInMilestone(){
        return projectDao.selectProjectIdInMilestone();
    }

    @Override
    public List<String> selectMilestoneStatus(Integer projectId){
        return projectDao.selectMilestoneStatus(projectId);
    }

    @Override
    public ProjectListView selectProjectById(Integer id) {
        return projectDao.selectProjectById(id);
    }

    @Override
    public List<ProjectStatusBean> getStopProjectDay(String status) {
        return projectDao.getStopProjectDay(status);
    }

    @Override
    public int updateStopStatusForProject(int projectId, String status) {
        return projectDao.updateStopStatusForProject(projectId, status);
    }

    @Override
    public List<Integer> getProjectId() {
        return projectDao.getProjectId();
    }

    @Override
    public Project getProject(String projectName) {
        return projectDao.getProject(projectName);
    }
}
