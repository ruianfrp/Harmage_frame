package com.harmonycloud.imp;

import com.harmonycloud.dao.CustomerDao;
import com.harmonycloud.dao.MemberLeaveDao;
import com.harmonycloud.service.MemberLeaveService;
import com.harmonycloud.view.MemberLeaveListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class MemberLeaveServiceImp implements MemberLeaveService {

    @Autowired
    MemberLeaveDao memberLeaveDao;

    @Override
    public List<MemberLeaveListView> listMemberLeave(String sort){
        return memberLeaveDao.listMemberLeave(sort);
    }

    @Override
    public List<MemberLeaveListView> listPmMemberLeave(String employeeGh,String sort){
        return memberLeaveDao.listPmMemberLeave(employeeGh,sort);
    }

    @Override
    public int updateMemberLeave(String leaveApprovalGh, String memberLeaveStatus, Integer projectId, int id) {
        return memberLeaveDao.updateMemberLeave(leaveApprovalGh, memberLeaveStatus, projectId, id);
    }

    @Override
    public void insertMemberLeave(Integer fkProjectId,String fkEmployeeGh,String memberLeaveGh,String memberSkillEvaluation,
                            String memberLeaveTime,String memberLeaveRemark){
        memberLeaveDao.insertMemberLeave(fkProjectId, fkEmployeeGh, memberLeaveGh, memberSkillEvaluation,
                memberLeaveTime, memberLeaveRemark);
    }

    @Override
    public List<MemberLeaveListView> selectIfLeave(){
        return memberLeaveDao.selectIfLeave();
    }

    @Override
    public Integer leaveMember(String memberRemarks, Date realEndTime, String employeeGh, Integer projectId){
        return memberLeaveDao.leaveMember(memberRemarks, realEndTime, employeeGh, projectId);
    }

    @Override
    public Integer updateMemberIsLeave(Integer id){
        return memberLeaveDao.updateMemberIsLeave(id);
    }

    @Override
    public Integer distributeProject(Integer id,Integer projectId,String estimateStartTime,String estimateEndTime,
                                     String memberJob,String memberSup,String memberType,String memberFunc){
        return memberLeaveDao.distributeProject(id, projectId, estimateStartTime, estimateEndTime, memberJob, memberSup, memberType, memberFunc);
    }

    @Override
    public Integer ifHadMemberLeave(Integer projectId, String employeeGh){
        return memberLeaveDao.ifHadMemberLeave(projectId, employeeGh);
    }

    @Override
    public String countProjName(String employeeGh){
        return memberLeaveDao.countProjName(employeeGh);
    }

    @Override
    public String findMemberLeaveGh(Integer id){
        return memberLeaveDao.findMemberLeaveGh(id);
    }

    @Override
    public MemberLeaveListView selectLeaveTime(Integer id){
        return memberLeaveDao.selectLeaveTime(id);
    }

    @Override
    public Integer insertMemberByLeaveApply(Integer id, Integer projectId, String estimateStartTime, String estimateEndTime, String memberJob, String memberSup, String memberType, String memberFunc){
        return memberLeaveDao.insertMemberByLeaveApply(id, projectId, estimateStartTime, estimateEndTime, memberJob, memberSup, memberType, memberFunc);
    }

}
