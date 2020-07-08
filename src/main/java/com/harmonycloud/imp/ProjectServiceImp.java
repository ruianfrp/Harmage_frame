package com.harmonycloud.imp;


import com.harmonycloud.dao.ProjectDao;
import com.harmonycloud.entity.Project;
import com.harmonycloud.service.ProjectService;
import com.harmonycloud.view.ProjectListView;
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
}
