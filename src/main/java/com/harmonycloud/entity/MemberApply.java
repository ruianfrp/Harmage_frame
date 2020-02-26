package com.harmonycloud.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class MemberApply {

    private Long id;
    private Integer fkProjectId;
    private String fkEmployeeGh;
    private Long fkSkillId;
    private String memberRecommendGh;
    private String applyApprovalGh;
    private String distributionGh;
    private String memberApplicationReason;
    private String memberJoinSup;
    private String memberJoinType;
    private String memberStartTime;
    private String memberEndTime;
    private String memberApplicationStatus;
    private Integer ownerIsAgree;
    private Integer applicantisAgree;
    private Integer isDone;
    private Date createTime;
    private Date updateTime;

    public MemberApply() {
    }

    public MemberApply(Long id, Integer fkProjectId, String fkEmployeeGh, Long fkSkillId, String memberRecommendGh, String applyApprovalGh, String distributionGh, String memberApplicationReason, String memberJoinSup, String memberJoinType, String memberStartTime, String memberEndTime, String memberApplicationStatus, Integer ownerIsAgree, Integer applicantisAgree, Integer isDone, Date createTime, Date updateTime) {
        this.id = id;
        this.fkProjectId = fkProjectId;
        this.fkEmployeeGh = fkEmployeeGh;
        this.fkSkillId = fkSkillId;
        this.memberRecommendGh = memberRecommendGh;
        this.applyApprovalGh = applyApprovalGh;
        this.distributionGh = distributionGh;
        this.memberApplicationReason = memberApplicationReason;
        this.memberJoinSup = memberJoinSup;
        this.memberJoinType = memberJoinType;
        this.memberStartTime = memberStartTime;
        this.memberEndTime = memberEndTime;
        this.memberApplicationStatus = memberApplicationStatus;
        this.ownerIsAgree = ownerIsAgree;
        this.applicantisAgree = applicantisAgree;
        this.isDone = isDone;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getIsDone() {
        return isDone;
    }

    public void setIsDone(Integer isDone) {
        this.isDone = isDone;
    }

    public String getDistributionGh() {
        return distributionGh;
    }

    public void setDistributionGh(String distributionGh) {
        this.distributionGh = distributionGh;
    }

    public Integer getOwnerIsAgree() {
        return ownerIsAgree;
    }

    public void setOwnerIsAgree(Integer ownerIsAgree) {
        this.ownerIsAgree = ownerIsAgree;
    }

    public Integer getApplicantisAgree() {
        return applicantisAgree;
    }

    public void setApplicantisAgree(Integer applicantisAgree) {
        this.applicantisAgree = applicantisAgree;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Integer fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh;
    }

    public Long getFkSkillId() {
        return fkSkillId;
    }

    public void setFkSkillId(Long fkSkillId) {
        this.fkSkillId = fkSkillId;
    }

    public String getMemberRecommendGh() {
        return memberRecommendGh;
    }

    public void setMemberRecommendGh(String memberRecommendGh) {
        this.memberRecommendGh = memberRecommendGh;
    }

    public String getMemberApplicationReason() {
        return memberApplicationReason;
    }

    public void setMemberApplicationReason(String memberApplicationReason) {
        this.memberApplicationReason = memberApplicationReason;
    }

    public String getMemberJoinSup() {
        return memberJoinSup;
    }

    public void setMemberJoinSup(String memberJoinSup) {
        this.memberJoinSup = memberJoinSup;
    }

    public String getMemberJoinType() {
        return memberJoinType;
    }

    public void setMemberJoinType(String memberJoinType) {
        this.memberJoinType = memberJoinType;
    }

    public String getMemberStartTime() {
        return memberStartTime;
    }

    public void setMemberStartTime(String memberStartTime) {
        this.memberStartTime = memberStartTime;
    }

    public String getMemberEndTime() {
        return memberEndTime;
    }

    public void setMemberEndTime(String memberEndTime) {
        this.memberEndTime = memberEndTime;
    }

    public String getMemberApplicationStatus() {
        return memberApplicationStatus;
    }

    public void setMemberApplicationStatus(String memberApplicationStatus) {
        this.memberApplicationStatus = memberApplicationStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getApplyApprovalGh() {
        return applyApprovalGh;
    }

    public void setApplyApprovalGh(String applyApprovalGh) {
        this.applyApprovalGh = applyApprovalGh;
    }
}
