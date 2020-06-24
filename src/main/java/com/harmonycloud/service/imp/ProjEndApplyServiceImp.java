package com.harmonycloud.service.imp;

import com.harmonycloud.bean.project.ProjEndApplyDetailView;
import com.harmonycloud.bean.project.ProjEndApplyListView;
import com.harmonycloud.bean.project.ProjectEndMsgView;
import com.harmonycloud.dao.ProjEndApplyDao;
import com.harmonycloud.service.ProjEndApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：star
 * @date ：Created in 2019/9/9 10:32
 */
@Service
public class ProjEndApplyServiceImp implements ProjEndApplyService {

    @Autowired
    ProjEndApplyDao projEndApplyDao;

    @Override
    public List<ProjEndApplyListView> listPMProjEndApply(String projPMGh) {
        return projEndApplyDao.listPMProjEndApply(projPMGh);
    }

    @Override
    public List<ProjEndApplyListView> listPMOProjEndApply() {
        return projEndApplyDao.listPMOProjEndApply();
    }

    @Override
    public ProjEndApplyDetailView projEndApplyDetail(int id) {
        return projEndApplyDao.projEndApplyDetail(id);
    }

    @Override
    public int updateProjEndApply(String applyStatus, String applyApprovalGh, int id) {
        return projEndApplyDao.updateProjEndApply(applyStatus, applyApprovalGh, id);
    }

    @Override
    public Integer insertProjEndApply(Integer fkProjectId, String projEndTime, String applyApprovalGh, String projEndRemark,
                                   String projEndMeeting, String meetingTime) {
        return projEndApplyDao.insertProjEndApply(fkProjectId, projEndTime, applyApprovalGh, projEndRemark,
                projEndMeeting, meetingTime);
    }

    @Override
    public String selectProjectEndTime(Integer id) {
        return projEndApplyDao.selectProjectEndTime(id);
    }

    @Override
    public String selectProjectPreendTime(Integer id) {
        return projEndApplyDao.selectProjectPreendTime(id);
    }

    @Override
    public Integer updateProjectStatus(String status, Integer id1, Integer id2) {
        return projEndApplyDao.updateProjectStatus(status, id1, id2);
    }

    @Override
    public Integer updateProjectEndTypeByProjectId(String projEndType, Integer fkProjectId) {
        return projEndApplyDao.updateProjectEndTypeByProjectId(projEndType, fkProjectId);
    }

    @Override
    public Integer updateProjectEndTypeById(String projEndType, Integer id) {
        return projEndApplyDao.updateProjectEndTypeById(projEndType, id);
    }

    @Override
    public Integer selectIfAgreeView(Integer id){
        return projEndApplyDao.selectIfAgreeView(id);
    }

    @Override
    public String selectMeetingTime(Integer fkProjectId){
        return projEndApplyDao.selectMeetingTime(fkProjectId);
    }

    @Override
    public ProjectEndMsgView selectProjEndMsgInfo(Integer fkProjectId){
        return projEndApplyDao.selectProjEndMsgInfo(fkProjectId);
    }

    @Override
    public Integer selectFileNum(Integer fkProjectId){
        return projEndApplyDao.selectFileNum(fkProjectId);
    }

    @Override
    public String selectProjectEndFilePath(Integer id){
        return projEndApplyDao.selectProjectEndFilePath(id);
    }

    @Override
    public Integer deleteProjectEndFiles(Integer id){
        return projEndApplyDao.deleteProjectEndFiles(id);
    }

}
