package com.harmonycloud.service.imp;


import com.harmonycloud.bean.employee.MatchEmployeeView;
import com.harmonycloud.bean.member.MemberAgreeApplyList;
import com.harmonycloud.bean.member.MemberApply;
import com.harmonycloud.bean.member.MemberApplyDetailView;
import com.harmonycloud.bean.member.MemberApplyListView;
import com.harmonycloud.dao.MemberApplyDao;
import com.harmonycloud.service.MemberApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：star
 * @date ：Created in 2019/9/6 14:29
 */
@Service
public class MemberApplyServiceImp implements MemberApplyService {

    @Autowired
    MemberApplyDao memberApplyDao;

    @Override
    public List<MemberApplyListView> listMemberApply(String startTimeSort, String statusSort) {
        return memberApplyDao.listMemberApply(startTimeSort, statusSort);
    }

    @Override
    public List<String> listSkillName(Integer id) {
        return memberApplyDao.listSkillName(id);
    }

    @Override
    public List<String> listSkillTestName(Integer id) {
        return memberApplyDao.listSkillTestName(id);
    }

    @Override
    public MemberApplyDetailView selectById(Integer id) {
        return memberApplyDao.selectById(id);
    }

    @Override
    public void insertMemberApply(MemberApply memberApply) {
        memberApplyDao.insertMemberApply(memberApply);
    }

    @Override
    public Long selectMemberApplyId(Integer projId, String employeeGh, String memberRecommendGh,
                                    String memberJoinSup, String memberJoinType, String memberStartTime) {
        return memberApplyDao.selectMemberApplyId(projId, employeeGh, memberRecommendGh, memberJoinSup, memberJoinType, memberStartTime);
    }

    @Override
    public List<MemberApplyListView> listPmMemberApply(String employeeGh,String startTimeSort,String statusSort) {
        return memberApplyDao.listPmMemberApply(employeeGh,startTimeSort,statusSort);
    }

    @Override
    public void insertIntoApplySkill(Long id, Integer applySkillId) {
        memberApplyDao.insertIntoApplySkill(id, applySkillId);
    }

    @Override
    public void insertIntoApplySkillTest(Long id, Integer applySkillTestId) {
        memberApplyDao.insertIntoApplySkillTest(id, applySkillTestId);
    }

    @Override
    public Integer updateMemberApply(String employeeGh, String applyApprovalGh, String memberStartTime, String memberEndTime, String memberApplicationStatus, Integer id) {
        return memberApplyDao.updateMemberApply(employeeGh, applyApprovalGh, memberStartTime, memberEndTime, memberApplicationStatus, id);
    }

    @Override
    public Integer updateCompleteMember(String memberStartTime, String employeeGh) {
        return memberApplyDao.updateCompleteMember(memberStartTime, employeeGh);
    }

    @Override
    public Integer joinMemberByApply(String employeeGh, Integer projectId, String memberStartTime, String memberEndTime, String memberJoinSup, String memberJoinType) {
        return memberApplyDao.joinMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
    }

    @Override
    public List<Integer> getSkillId(Integer id) {
        return memberApplyDao.getSkillId(id);
    }

    @Override
    public List<MatchEmployeeView> selectSkillMatchEmployee(Integer skillId) {
        return memberApplyDao.selectSkillMatchEmployee(skillId);
    }

    @Override
    public List<Integer> getSkillTestId(Integer id) {
        return memberApplyDao.getSkillTestId(id);
    }

    @Override
    public List<MatchEmployeeView> selectSkillTestMatchEmployee(Integer skillTestId) {
        return memberApplyDao.selectSkillTestMatchEmployee(skillTestId);
    }

    @Override
    public Integer easyMemberApply() {
        return memberApplyDao.easyMemberApply();
    }

    @Override
    public Integer memberQuit() {
        return memberApplyDao.memberQuit();
    }

    @Override
    public List<MatchEmployeeView> projectLessThanTwo() {
        return memberApplyDao.projectLessThanTwo();
    }

    @Override
    public List<MatchEmployeeView> matchStartTime(Integer id) {
        return memberApplyDao.matchStartTime(id);
    }

    @Override
    public List<MatchEmployeeView> matchEndTime(Integer id) {
        return memberApplyDao.matchEndTime(id);
    }

    @Override
    public List<MemberAgreeApplyList> listApplicantAgreeApply(String employeeGh, String sort) {
        return memberApplyDao.listApplicantAgreeApply(employeeGh,sort);
    }

    @Override
    public List<MemberAgreeApplyList> listOwnerAgreeApply(String employeeGh,String sort) {
        return memberApplyDao.listOwnerAgreeApply(employeeGh,sort);
    }

    @Override
    public Integer applicantAgree(Integer ifAgree, Integer id) {
        return memberApplyDao.applicantAgree(ifAgree, id);
    }

    @Override
    public Integer applicantAgree2(Integer ifAgree, Integer id){
        return memberApplyDao.applicantAgree2(ifAgree, id);
    }

    @Override
    public Integer ownerAgree(Integer ifAgree, Integer id) {
        return memberApplyDao.ownerAgree(ifAgree, id);
    }

    @Override
    public Integer ownerAgree2(Integer ifAgree, Integer id, String employeeGh){
        return memberApplyDao.ownerAgree2(ifAgree, id, employeeGh);
    }

    @Override
    public List<MemberApply> selectAllAgreeMemberApply() {
        return memberApplyDao.selectAllAgreeMemberApply();
    }

    @Override
    public Integer doneMemberApply(Integer id) {
        return memberApplyDao.doneMemberApply(id);
    }

    @Override
    public MatchEmployeeView selectRecommendEmployee(Integer id){
        return memberApplyDao.selectRecommendEmployee(id);
    }

    @Override
    public MatchEmployeeView selectDistributionEmployee(Integer id){
        return memberApplyDao.selectDistributionEmployee(id);
    }

    @Override
    public String selectOwnerProjectName(Integer id){
        return memberApplyDao.selectOwnerProjectName(id);
    }

    @Override
    public String selectApplicantPM(Integer id){
        return memberApplyDao.selectApplicantPM(id);
    }

    @Override
    public List<String> selectOwnerPM(Integer id){
        return memberApplyDao.selectOwnerPM(id);
    }

    @Override
    public String selectUserIdByGh(String list){
        return memberApplyDao.selectUserIdByGh(list);
    }

    @Override
    public List<MemberApply> selectDistributionGh(){
        return memberApplyDao.selectDistributionGh();
    }

    @Override
    public List<Integer> selectIfHaveProj(String fkEmployeeGh){
        return memberApplyDao.selectIfHaveProj(fkEmployeeGh);
    }

    @Override
    public MemberApply selectIfOwnerIsAgree(Integer id){
        return memberApplyDao.selectIfOwnerIsAgree(id);
    }

    @Override
    public List<Integer> selectIfHaveProj2(Integer id){
        return memberApplyDao.selectIfHaveProj2(id);
    }

    @Override
    public Integer updateQuitMember(String employeeGh){
        return memberApplyDao.updateQuitMember(employeeGh);
    }

    @Override
    public Integer insertMemberByApply(String employeeGh, Integer projectId, String memberStartTime, String memberEndTime, String memberJoinSup, String memberJoinType){
        return memberApplyDao.insertMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
    }

    @Override
    public Integer insertIntoMemberApplyAgree(Integer id,Integer agreeType,String pmGh){
        return memberApplyDao.insertIntoMemberApplyAgree(id, agreeType, pmGh);
    }

    @Override
    public List<Integer> selectIfOwnerAgree(Integer id){
        return memberApplyDao.selectIfOwnerAgree(id);
    }
}

