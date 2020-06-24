package com.harmonycloud.service;


import com.harmonycloud.bean.member.MemberLeaveListView;

import java.sql.Date;
import java.util.List;

public interface MemberLeaveService {

    List<MemberLeaveListView> listMemberLeave(String sort);

    List<MemberLeaveListView> listPmMemberLeave(String employeeGh, String sort);

    int updateMemberLeave(String leaveApprovalGh,String memberLeaveStatus,Integer projectId,int id);

    void insertMemberLeave(Integer fkProjectId,String fkEmployeeGh,String memberLeaveGh,String memberSkillEvaluation,
                           String memberLeaveTime,String memberLeaveRemark);

    List<MemberLeaveListView> selectIfLeave();

    Integer leaveMember(String memberRemarks, Date realEndTime, String employeeGh, Integer projectId);

    Integer updateMemberIsLeave(Integer id);

    Integer distributeProject(Integer id,Integer projectId,String estimateStartTime,String estimateEndTime,String memberJob,String memberSup,String memberType,String memberFunc);

    Integer ifHadMemberLeave(Integer projectId, String employeeGh);

    String countProjName(String employeeGh);

    String findMemberLeaveGh(Integer id);

    MemberLeaveListView selectLeaveTime(Integer id);

    Integer insertMemberByLeaveApply(Integer id, Integer projectId, String estimateStartTime, String estimateEndTime, String memberJob, String memberSup, String memberType, String memberFunc);
}
