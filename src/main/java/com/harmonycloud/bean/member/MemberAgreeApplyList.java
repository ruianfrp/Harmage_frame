package com.harmonycloud.bean.member;

import java.sql.Date;

public class MemberAgreeApplyList {
    private Integer id;
    private String employeeGh;
    private String employeeName;
    private String ownerProjName;
    private String applicantProjName;
    private String lastProjectName;
    private String applicantProjPlace;
    private String applicantProjPm;
    private String memberJoinType;
    private Date memberStartTime;
    private Date memberEndTime;
    private String memberApplicationStatus;
    private String applyApprovalName;
    private Integer ownerIsAgree;
    private Integer applicantIsAgree;

    public String getLastProjectName() {
        return lastProjectName;
    }

    public void setLastProjectName(String lastProjectName) {
        this.lastProjectName = lastProjectName;
    }

    public String getApplicantProjPlace() {
        return applicantProjPlace;
    }

    public void setApplicantProjPlace(String applicantProjPlace) {
        this.applicantProjPlace = applicantProjPlace;
    }

    public String getApplicantProjPm() {
        return applicantProjPm;
    }

    public void setApplicantProjPm(String applicantProjPm) {
        this.applicantProjPm = applicantProjPm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerIsAgree() {
        return ownerIsAgree;
    }

    public void setOwnerIsAgree(Integer ownerIsAgree) {
        this.ownerIsAgree = ownerIsAgree;
    }

    public Integer getApplicantIsAgree() {
        return applicantIsAgree;
    }

    public void setApplicantIsAgree(Integer applicantIsAgree) {
        this.applicantIsAgree = applicantIsAgree;
    }

    public String getEmployeeGh() {
        return employeeGh;
    }

    public void setEmployeeGh(String employeeGh) {
        this.employeeGh = employeeGh;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getOwnerProjName() {
        return ownerProjName;
    }

    public void setOwnerProjName(String ownerProjName) {
        this.ownerProjName = ownerProjName;
    }

    public String getApplicantProjName() {
        return applicantProjName;
    }

    public void setApplicantProjName(String applicantProjName) {
        this.applicantProjName = applicantProjName;
    }

    public String getMemberJoinType() {
        return memberJoinType;
    }

    public void setMemberJoinType(String memberJoinType) {
        this.memberJoinType = memberJoinType;
    }

    public Date getMemberStartTime() {
        return memberStartTime;
    }

    public void setMemberStartTime(Date memberStartTime) {
        this.memberStartTime = memberStartTime;
    }

    public Date getMemberEndTime() {
        return memberEndTime;
    }

    public void setMemberEndTime(Date memberEndTime) {
        this.memberEndTime = memberEndTime;
    }

    public String getMemberApplicationStatus() {
        return memberApplicationStatus;
    }

    public void setMemberApplicationStatus(String memberApplicationStatus) {
        this.memberApplicationStatus = memberApplicationStatus;
    }

    public String getApplyApprovalName() {
        return applyApprovalName;
    }

    public void setApplyApprovalName(String applyApprovalName) {
        this.applyApprovalName = applyApprovalName;
    }
}
