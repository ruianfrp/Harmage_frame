package com.harmonycloud.bean.employee;

import java.util.Date;

public class EmployeeSkill {
    private Long id;

    private Long fkSkillId;

    private String fkEmployeeGh;

    private String employeeSkillLevel;

    private Date createTime;

    private Date updateTime;

    public EmployeeSkill(Long id, Long fkSkillId, String fkEmployeeGh, String employeeSkillLevel, Date createTime, Date updateTime) {
        this.id = id;
        this.fkSkillId = fkSkillId;
        this.fkEmployeeGh = fkEmployeeGh;
        this.employeeSkillLevel = employeeSkillLevel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public EmployeeSkill() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkSkillId() {
        return fkSkillId;
    }

    public void setFkSkillId(Long fkSkillId) {
        this.fkSkillId = fkSkillId;
    }

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh == null ? null : fkEmployeeGh.trim();
    }

    public String getEmployeeSkillLevel() {
        return employeeSkillLevel;
    }

    public void setEmployeeSkillLevel(String employeeSkillLevel) {
        this.employeeSkillLevel = employeeSkillLevel == null ? null : employeeSkillLevel.trim();
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