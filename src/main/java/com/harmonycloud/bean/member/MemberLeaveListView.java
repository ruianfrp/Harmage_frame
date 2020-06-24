package com.harmonycloud.bean.member;

import com.harmonycloud.bean.employee.EmployeeSkillView;

import java.sql.Date;
import java.util.List;

public class MemberLeaveListView {
    private Integer id;
    private Integer projectId;
    private String employeeGh;
    private String projName;
    private String leaveApplyName;
    private String memberLeaveGh;
    private String memberLeaveName;
    private Date memberLeaveTime;
    private String leaveStatus;
    private String createTime;
    private String memberSkillEvaluation;
    private String memberLeaveRemark;
    private String distributionProjName;
    private List<EmployeeSkillView> skillInfo;
    private String leaveApprovalName;
    private String countProjName;

    public String getDistributionProjName() {
        return distributionProjName;
    }

    public void setDistributionProjName(String distributionProjName) {
        this.distributionProjName = distributionProjName;
    }

    public String getCountProjName() {
        return countProjName;
    }

    public void setCountProjName(String countProjName) {
        this.countProjName = countProjName;
    }

    public String getLeaveApplyName() {
        return leaveApplyName;
    }

    public void setLeaveApplyName(String leaveApplyName) {
        this.leaveApplyName = leaveApplyName;
    }

    public String getLeaveApprovalName() {
        return leaveApprovalName;
    }

    public void setLeaveApprovalName(String leaveApprovalName) {
        this.leaveApprovalName = leaveApprovalName;
    }

    public String getEmployeeGh() {
        return employeeGh;
    }

    public void setEmployeeGh(String employeeGh) {
        this.employeeGh = employeeGh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getMemberLeaveName() {
        return memberLeaveName;
    }

    public void setMemberLeaveName(String memberLeaveName) {
        this.memberLeaveName = memberLeaveName;
    }

    public List<EmployeeSkillView> getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(List<EmployeeSkillView> skillInfo) {
        this.skillInfo = skillInfo;
    }

    public Date getMemberLeaveTime() {
        return memberLeaveTime;
    }

    public void setMemberLeaveTime(Date memberLeaveTime) {
        this.memberLeaveTime = memberLeaveTime;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMemberSkillEvaluation() {
        return memberSkillEvaluation;
    }

    public void setMemberSkillEvaluation(String memberSkillEvaluation) {
        this.memberSkillEvaluation = memberSkillEvaluation;
    }

    public String getMemberLeaveRemark() {
        return memberLeaveRemark;
    }

    public void setMemberLeaveRemark(String memberLeaveRemark) {
        this.memberLeaveRemark = memberLeaveRemark;
    }

    public String getMemberLeaveGh() {
        return memberLeaveGh;
    }

    public void setMemberLeaveGh(String memberLeaveGh) {
        this.memberLeaveGh = memberLeaveGh;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
