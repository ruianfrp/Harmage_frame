package com.harmonycloud.service.imp;

import com.harmonycloud.bean.apply.StopAndRestartApplyBean;
import com.harmonycloud.bean.enumeration.ProjectStatus;
import com.harmonycloud.dao.ProjectStatusCacheDao;
import com.harmonycloud.service.ProjectStatusCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ProjectStatusCacheServiceImp implements ProjectStatusCacheService {

    @Autowired
    ProjectStatusCacheDao projectStatusCacheDao;

    @Override
    public int insertProjectStatusCache(int projectId, String projectName, String status, String stopTimeFrom, String stopTimeTo, String note, int userId, String userName) {
        return projectStatusCacheDao.insertProjectStatusCache(projectId, projectName, status, stopTimeFrom, stopTimeTo, note, userId, userName);
    }

    @Override
    public List<StopAndRestartApplyBean> getProjectApply(String action, List<Integer> flag) {
        return projectStatusCacheDao.getStopProjectApply(action, flag);
    }

    @Override
    public List<StopAndRestartApplyBean> getAllProjectApply(int flag) {
        return null;
    }

    @Override
    public int updateProjectStatusFlag(int projectId, String status, int flag) {
        return projectStatusCacheDao.updateProjectStatusFlag(projectId, status,  flag);
    }

    @Override
    public List<String> getActionByProjectId(int projectId, int flag) {
        return projectStatusCacheDao.getActionByProjectId(projectId, flag);
    }

    public static void main(String[] args) {
        System.out.println(ProjectStatus.STOP.getStatus());
    }
}
