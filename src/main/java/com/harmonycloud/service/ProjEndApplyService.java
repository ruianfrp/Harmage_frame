package com.harmonycloud.service;

import com.harmonycloud.view.ProjEndApplyDetailView;
import com.harmonycloud.view.ProjEndApplyListView;
import com.harmonycloud.view.ProjectEndMsgView;

import java.util.List;

/**
 * @author ：star
 * @date ：Created in 2019/9/9 10:31
 */
public interface ProjEndApplyService {

    List<ProjEndApplyListView> listPMProjEndApply(String projPMGh);

    List<ProjEndApplyListView> listPMOProjEndApply();

    ProjEndApplyDetailView projEndApplyDetail(int id);

    int updateProjEndApply(String applyStatus, String applyApprovalGh, int id);

    Integer insertProjEndApply(Integer fkProjectId, String projEndTime, String applyApprovalGh, String projEndRemark,
                            String projEndMeeting, String meetingTime);

    String selectProjectEndTime(Integer id);

    String selectProjectPreendTime(Integer id);

    Integer updateProjectStatus(String status, Integer id1, Integer id2);

    Integer updateProjectEndTypeByProjectId(String projEndType, Integer fkProjectId);

    Integer updateProjectEndTypeById(String projEndType, Integer id);

    Integer selectIfAgreeView(Integer id);

    String selectMeetingTime(Integer fkProjectId);

    ProjectEndMsgView selectProjEndMsgInfo(Integer fkProjectId);

    Integer selectFileNum(Integer fkProjectId);

    String selectProjectEndFilePath(Integer id);

    Integer deleteProjectEndFiles(Integer id);
}
