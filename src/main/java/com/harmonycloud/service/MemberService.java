package com.harmonycloud.service;

import com.harmonycloud.entity.Member;
import com.harmonycloud.view.MemberView;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
public interface MemberService {
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
