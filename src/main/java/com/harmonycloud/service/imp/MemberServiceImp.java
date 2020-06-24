package com.harmonycloud.service.imp;


import com.harmonycloud.bean.execl.EmployeeReportExecl;
import com.harmonycloud.bean.member.Member;
import com.harmonycloud.bean.member.MemberView;
import com.harmonycloud.dao.MemberDao;
import com.harmonycloud.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
public class MemberServiceImp implements MemberService {


    @Autowired
    MemberDao memberDao;

    @Override
    public List<MemberView> listMember(Long projectId) {
        return memberDao.listMember(projectId);
    }


    @Override
    public int insertSelective(Member member) {
        return memberDao.insertSelective(member);
    }

    @Override
    public int updateByPrimaryKeySelective(Member record) {
        return memberDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateMember(int memberId) {
        return memberDao.updateMember(memberId);
    }

    @Override
    public List<String> listMemberJob(Integer projectId) {
        return memberDao.listMemberJob(projectId);
    }

    @Override
    public Integer insertPMOMemberLeave(Integer projectId, String employeeGh, String memberLeaveGh, String leaveApprovalGh,
                                        Date leaveDate, String memberLeaveRemark) {
        return memberDao.insertPMOMemberLeave(projectId, employeeGh, memberLeaveGh, leaveApprovalGh, leaveDate, memberLeaveRemark);
    }

    @Override
    public String selectPmGh(Integer projectId) {
        return memberDao.selectPmGh(projectId);
    }

    @Override
    public Integer updateMemberQuit(String employeeGh) {
        return memberDao.updateMemberQuit(employeeGh);
    }

    @Override
    public String findPmByProjId(Integer fkProjectId) {
        return memberDao.findPmByProjId(fkProjectId);
    }

    @Override
    public Integer insertIntoMemberApply(Integer fkProjectId, String fkEmployeeGh, String memberRecommendGh, String applyApprovalGh,
                                         String distributionGh, String memberJoinSup, String memberJoinType, Date memberStartTime, Date memberEndTime) {
        return memberDao.insertIntoMemberApply(fkProjectId, fkEmployeeGh, memberRecommendGh, applyApprovalGh, distributionGh, memberJoinSup, memberJoinType, memberStartTime, memberEndTime);
    }

    @Override
    public List<EmployeeReportExecl> getProjectTimeReport() {
        return memberDao.getProjectTimeReport();
    }


}
