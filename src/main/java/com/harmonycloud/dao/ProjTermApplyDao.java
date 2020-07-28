package com.harmonycloud.dao;

import com.harmonycloud.bean.project.ProjEndApplyDetailView;
import com.harmonycloud.bean.project.ProjEndApplyListView;
import com.harmonycloud.bean.project.ProjectEndMsgView;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by SunRuiBin on 2020/7/27.
 */
@Repository
public interface ProjTermApplyDao {

    Integer insertProjEndApply(Integer fkProjectId, String projEndTime, String applyApprovalGh, String projEndRemark,
                               String projEndMeeting, String meetingTime);
    List<ProjEndApplyListView> listPMProjEndApply(String projPMGh);

    List<ProjEndApplyListView> listPMOProjEndApply();

    ProjEndApplyDetailView projEndApplyDetail(int id);

    int updateProjEndApply(String applyStatus, String applyApprovalGh, int id);


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
