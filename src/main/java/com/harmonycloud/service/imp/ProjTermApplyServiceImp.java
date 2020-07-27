package com.harmonycloud.service.imp;

import com.harmonycloud.dao.ProjEndApplyDao;
import com.harmonycloud.service.ProjTermApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}


