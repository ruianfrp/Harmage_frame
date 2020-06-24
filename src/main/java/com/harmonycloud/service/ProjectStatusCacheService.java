package com.harmonycloud.service;

import com.harmonycloud.bean.apply.StopAndRestartApplyBean;

import java.util.List;

public interface ProjectStatusCacheService {
    int insertProjectStatusCache(int projectId, String projectName, String status, String stopTimeFrom, String stopTimeTo, String note, int userId, String userName);

    List<StopAndRestartApplyBean> getProjectApply(String action, List<Integer> list);

    List<StopAndRestartApplyBean> getAllProjectApply(int flag);

    int updateProjectStatusFlag(int projectId, String status, int flag);

    List<String> getActionByProjectId(int projectId, int flag);
}
