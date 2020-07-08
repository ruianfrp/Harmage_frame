package com.harmonycloud.view;

import java.sql.Date;

public class EmployeeSkillView {
    private Long id;
    private String fkEmployeeGh;
    private String skillDirct;
    private String skillName;
    private Date createTime;
    private Date updateTime;

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh;
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
        this.skillDirct = skillDirct;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
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
