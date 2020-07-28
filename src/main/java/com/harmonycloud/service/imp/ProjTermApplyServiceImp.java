package com.harmonycloud.service.imp;

import com.harmonycloud.bean.project.ProjEndApplyDetailView;
import com.harmonycloud.bean.project.ProjEndApplyListView;
import com.harmonycloud.bean.project.ProjectEndMsgView;
import com.harmonycloud.dao.ProjEndApplyDao;
import com.harmonycloud.service.ProjTermApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SunRuiBin on 2020/7/27.
 */
@Service
public class ProjTermApplyServiceImp implements ProjTermApplyService{

    @Autowired
    ProjEndApplyDao projEndApplyDao;


    @Override
    public Integer insertProjEndApply(Integer fkProjectId, String projEndTime, String applyApprovalGh, String projEndRemark,
                               String projEndMeeting, String meetingTime){

        return projEndApplyDao.insertProjEndApply(fkProjectId, projEndTime, applyApprovalGh, projEndRemark,
                projEndMeeting, meetingTime);
    }

    @Override
    public List<ProjEndApplyListView> listPMProjEndApply(String projPMGh) {
        return null;
    }

    @Override
    public List<ProjEndApplyListView> listPMOProjEndApply() {
        return null;
    }

    @Override
    public ProjEndApplyDetailView projEndApplyDetail(int id) {
        return null;
    }

    @Override
    public int updateProjEndApply(String applyStatus, String applyApprovalGh, int id) {
        return 0;
    }

    @Override
    public String selectProjectEndTime(Integer id) {
        return null;
    }

    @Override
    public String selectProjectPreendTime(Integer id) {
        return null;
    }

    @Override
    public Integer updateProjectStatus(String status, Integer id1, Integer id2) {
        return null;
    }

    @Override
    public Integer updateProjectEndTypeByProjectId(String projEndType, Integer fkProjectId) {
        return null;
    }

    @Override
    public Integer updateProjectEndTypeById(String projEndType, Integer id) {
        return null;
    }

    @Override
    public Integer selectIfAgreeView(Integer id) {
        return null;
    }

    @Override
    public String selectMeetingTime(Integer fkProjectId) {
        return null;
    }

    @Override
    public ProjectEndMsgView selectProjEndMsgInfo(Integer fkProjectId) {
        return null;
    }

    @Override
    public Integer selectFileNum(Integer fkProjectId) {
        return null;
    }

    @Override
    public String selectProjectEndFilePath(Integer id) {
        return null;
    }

    @Override
    public Integer deleteProjectEndFiles(Integer id) {
        return null;
    }
}


