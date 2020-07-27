package com.harmonycloud.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by SunRuiBin on 2020/7/27.
 */
@Repository
public interface ProjTermApplyDao {

    Integer insertProjEndApply(Integer fkProjectId, String projEndTime, String applyApprovalGh, String projEndRemark,
                               String projEndMeeting, String meetingTime);
}
