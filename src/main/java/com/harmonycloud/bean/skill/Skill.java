package com.harmonycloud.bean.skill;

import java.util.Date;

public class Skill {
    private Long id;

    private String skillDirct;

    private String skillName;

    private Date createTime;

    private Date updateTime;

    public Skill(Long id, String skillDirct, String skillName, Date createTime, Date updateTime) {
        this.id = id;
        this.skillDirct = skillDirct;
        this.skillName = skillName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Skill() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillDirct() {
        return skillDirct;
    }

    public void setSkillDirct(String skillDirct) {
        this.skillDirct = skillDirct == null ? null : skillDirct.trim();
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName == null ? null : skillName.trim();
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