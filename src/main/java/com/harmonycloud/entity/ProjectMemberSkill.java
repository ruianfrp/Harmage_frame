package com.harmonycloud.entity;

import java.util.Date;

public class ProjectMemberSkill {
    private Long id;

    private Long fkMemberId;

    private Long fkSkillId;

    private String memberSkillLevel;

    private Date createTime;

    private Date updateTime;

    public ProjectMemberSkill(Long id, Long fkMemberId, Long fkSkillId, String memberSkillLevel, Date createTime, Date updateTime) {
        this.id = id;
        this.fkMemberId = fkMemberId;
        this.fkSkillId = fkSkillId;
        this.memberSkillLevel = memberSkillLevel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ProjectMemberSkill() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkMemberId() {
        return fkMemberId;
    }

    public void setFkMemberId(Long fkMemberId) {
        this.fkMemberId = fkMemberId;
    }

    public Long getFkSkillId() {
        return fkSkillId;
    }

    public void setFkSkillId(Long fkSkillId) {
        this.fkSkillId = fkSkillId;
    }

    public String getMemberSkillLevel() {
        return memberSkillLevel;
    }

    public void setMemberSkillLevel(String memberSkillLevel) {
        this.memberSkillLevel = memberSkillLevel == null ? null : memberSkillLevel.trim();
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
}