package com.harmonycloud.dao;

import com.harmonycloud.entity.Member;
import com.harmonycloud.view.MemberView;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MemberDao {
    List<MemberView> listMember(Long projectId);

    int insertSelective(Member member);

    int updateByPrimaryKeySelective(Member record);

    int updateMember(int memberId);

    List<String> listMemberJob(Integer projectId);

    Integer insertPMOMemberLeave(Integer projectId, String employeeGh, String memberLeaveGh, String leaveApprovalGh, Date leaveDate, String memberLeaveRemark);

    String selectPmGh(Integer projectId);

    Integer updateMemberQuit(String employeeGh);

    String findPmByProjId(Integer fkProjectId);

    Integer insertIntoMemberApply(Integer fkProjectId,String fkEmployeeGh,String memberRecommendGh,String applyApprovalGh,
                                  String distributionGh,String memberJoinSup,String memberJoinType,Date memberStartTime,Date memberEndTime);
}