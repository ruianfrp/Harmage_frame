package com.harmonycloud.dao;

import com.harmonycloud.bean.apply.StopAndRestartApplyBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStatusCacheDao {
    int insertProjectStatusCache(@Param("project_id") int projectId,@Param("project_name") String projectName, @Param("action") String status,@Param("start_time") String stopTimeFrom,@Param("end_time") String stopTimeTo,@Param("note") String note, @Param("user_id") int userId, @Param("user_name") String userName);

    List<StopAndRestartApplyBean> getStopProjectApply(@Param("action") String action, @Param("flag") List<Integer> flag);

    int updateProjectStatusFlag(@Param("projectId") int projectId, @Param("action") String status, @Param("flag") int flag);

    List<String> getActionByProjectId(@Param("projectId") int projectId, @Param("flag") int flag);
}
