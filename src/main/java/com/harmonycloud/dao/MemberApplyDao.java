package com.harmonycloud.dao;

import com.harmonycloud.bean.employee.MatchEmployeeView;
import com.harmonycloud.bean.member.MemberAgreeApplyList;
import com.harmonycloud.bean.member.MemberApply;
import com.harmonycloud.bean.member.MemberApplyDetailView;
import com.harmonycloud.bean.member.MemberApplyListView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberApplyDao {

    List<MemberApplyListView> listMemberApply(String startTimeSort, String statusSort);

    List<String> listSkillName(Integer id);

    List<String> listSkillTestName(Integer id);

    MemberApplyDetailView selectById(Integer id);

    void insertMemberApply(MemberApply memberApply);

    Long selectMemberApplyId(Integer projId, String employeeGh, String memberRecommendGh,
                             String memberJoinSup, String memberJoinType, String memberStartTime);

    List<MemberApplyListView> listPmMemberApply(String employeeGh,String startTimeSort,String statusSort);

    void insertIntoApplySkill(Long id, Integer applySkillId);

    void insertIntoApplySkillTest(Long id, Integer applySkillTestId);

    Integer updateMemberApply(String employeeGh, String applyApprovalGh, String memberStartTime, String memberEndTime, String memberApplicationStatus, Integer id);

    Integer updateCompleteMember(String memberStartTime, String employeeGh);

    Integer joinMemberByApply(String employeeGh, Integer projectId, String memberStartTime, String memberEndTime, String memberJoinSup, String memberJoinType);

    List<Integer> getSkillId(Integer id);

    List<MatchEmployeeView> selectSkillMatchEmployee(Integer skillId);

    List<Integer> getSkillTestId(Integer id);

    List<MatchEmployeeView> selectSkillTestMatchEmployee(Integer skillTestId);

    Integer easyMemberApply();

    Integer memberQuit();

    List<MatchEmployeeView> projectLessThanTwo();

    List<MatchEmployeeView> matchStartTime(Integer id);

    List<MatchEmployeeView> matchEndTime(Integer id);

    List<MemberAgreeApplyList> listApplicantAgreeApply(String employeeGh, String sort);

    List<MemberAgreeApplyList> listOwnerAgreeApply(String employeeGh,String sort);

    Integer applicantAgree(Integer ifAgree, Integer id);

    Integer applicantAgree2(Integer ifAgree, Integer id);

    Integer ownerAgree(Integer ifAgree, Integer id);

    Integer ownerAgree2(Integer ifAgree, Integer id, String employeeGh);

    List<MemberApply> selectAllAgreeMemberApply();

    Integer doneMemberApply(Integer id);

    MatchEmployeeView selectRecommendEmployee(Integer id);

    MatchEmployeeView selectDistributionEmployee(Integer id);

    String selectOwnerProjectName(Integer id);

    String selectApplicantPM(Integer id);

    List<String> selectOwnerPM(Integer id);

    String selectUserIdByGh(String list);

    List<MemberApply> selectDistributionGh();

    List<Integer> selectIfHaveProj(String fkEmployeeGh);

    MemberApply selectIfOwnerIsAgree(Integer id);

    List<Integer> selectIfHaveProj2(Integer id);

    Integer updateQuitMember(String employeeGh);

    Integer insertMemberByApply(String employeeGh, Integer projectId, String memberStartTime, String memberEndTime, String memberJoinSup, String memberJoinType);

    Integer insertIntoMemberApplyAgree(Integer id,Integer agreeType,String pmGh);

    List<Integer> selectIfOwnerAgree(Integer id);
}
