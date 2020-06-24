package com.harmonycloud.bean.member;

import java.sql.Date;

public class MemberLeave {

    private Long id;
    private Long fkProjectId;
    private String fkEmployeeGh;
    private String memberLeaveGh;
    private String leaveApprovalGh;
    private String memberSkillEvaluation;
    private Date memberLeaveTime;
    private String memberLeaveRemark;
    private String memberLeaveStatus;
    private Date createTime;
    private Date updateTime;

    public MemberLeave() {
    }

    public MemberLeave(Long id, Long fkProjectId, String fkEmployeeGh, String memberLeaveGh, String leaveApprovalGh, String memberSkillEvaluation, Date memberLeaveTime, String memberLeaveRemark, String memberLeaveStatus, Date createTime, Date updateTime) {
        this.id = id;
        this.fkProjectId = fkProjectId;
        this.fkEmployeeGh = fkEmployeeGh;
        this.memberLeaveGh = memberLeaveGh;
        this.leaveApprovalGh = leaveApprovalGh;
        this.memberSkillEvaluation = memberSkillEvaluation;
        this.memberLeaveTime = memberLeaveTime;
        this.memberLeaveRemark = memberLeaveRemark;
        this.memberLeaveStatus = memberLeaveStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Long fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh;
    }

    public String getMemberLeaveGh() {
        return memberLeaveGh;
    }

    public void setMemberLeaveGh(String memberLeaveGh) {
        this.memberLeaveGh = memberLeaveGh;
    }

    public String getMemberSkillEvaluation() {
        return memberSkillEvaluation;
    }

    public void setMemberSkillEvaluation(String memberSkillEvaluation) {
        this.memberSkillEvaluation = memberSkillEvaluation;
    }

    public Date getMemberLeaveTime() {
        return memberLeaveTime;
    }

    public void setMemberLeaveTime(Date memberLeaveTime) {
        this.memberLeaveTime = memberLeaveTime;
    }

    public String getMemberLeaveRemark() {
        return memberLeaveRemark;
    }

    public void setMemberLeaveRemark(String memberLeaveRemark) {
        this.memberLeaveRemark = memberLeaveRemark;
    }

    public String getMemberLeaveStatus() {
        return memberLeaveStatus;
    }

    public void setMemberLeaveStatus(String memberLeaveStatus) {
        this.memberLeaveStatus = memberLeaveStatus;
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

    public String getLeaveApprovalGh() {
        return leaveApprovalGh;
    }

    public void setLeaveApprovalGh(String leaveApprovalGh) {
        this.leaveApprovalGh = leaveApprovalGh;
    }
}
