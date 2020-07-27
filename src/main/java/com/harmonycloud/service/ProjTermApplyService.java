package com.harmonycloud.service;

/**
 * Created by SunRuiBin on 2020/7/27.
 */

public interface ProjTermApplyService {
    Integer insertProjEndApply(Integer fkProjectId, String projEndTime, String applyApprovalGh, String projEndRemark,
                               String projEndMeeting, String meetingTime);
}
