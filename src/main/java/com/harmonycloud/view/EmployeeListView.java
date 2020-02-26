package com.harmonycloud.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/8 15:12
 */
public class EmployeeListView {
    private String employeeGh;
    private String employeeName;
    private String employeeSex;
    private String employeeDep;
    private String projName;
    private String employeeJob;
    private String employeeType;
    private String employeeWorkplace;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private Integer isQuit;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    private List<EmployeeSkillView> skillInfo;
    private List<TestSkillListView> skillTestInfo;
    private List<EmployeeExperienceView> employeeExperience;

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

    public String getEmployeeSex() {
        return employeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        this.employeeSex = employeeSex;
    }

    public String getEmployeeDep() {
        return employeeDep;
    }

    public void setEmployeeDep(String employeeDep) {
        this.employeeDep = employeeDep;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getEmployeeJob() {
        return employeeJob;
    }

    public void setEmployeeJob(String employeeJob) {
        this.employeeJob = employeeJob;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeWorkplace() {
        return employeeWorkplace;
    }

    public void setEmployeeWorkplace(String employeeWorkplace) {
        this.employeeWorkplace = employeeWorkplace;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<EmployeeSkillView> getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(List<EmployeeSkillView> skillInfo) {
        this.skillInfo = skillInfo;
    }

    public Integer getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Integer isQuit) {
        this.isQuit = isQuit;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<TestSkillListView> getSkillTestInfo() {
        return skillTestInfo;
    }

    public void setSkillTestInfo(List<TestSkillListView> skillTestInfo) {
        this.skillTestInfo = skillTestInfo;
    }

    public List<EmployeeExperienceView> getEmployeeExperience() {
        return employeeExperience;
    }

    public void setEmployeeExperience(List<EmployeeExperienceView> employeeExperience) {
        this.employeeExperience = employeeExperience;
    }
}
