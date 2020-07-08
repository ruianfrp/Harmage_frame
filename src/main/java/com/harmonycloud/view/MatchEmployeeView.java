package com.harmonycloud.view;

import java.util.List;

public class MatchEmployeeView {
    private String employeeGh;
    private String employeeName;
    private String employeeDep;
    private String projName;
    private String employeeJob;
    private String employeeType;
    private String employeeWorkplace;
    private List<EmployeeSkillView> skillInfo;
    private List<TestSkillListView> skillTestInfo;
    private Integer grade;
    private Integer projCount;
    private String memberType;


    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Integer getProjCount() {
        return projCount;
    }

    public void setProjCount(Integer projCount) {
        this.projCount = projCount;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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

    public List<EmployeeSkillView> getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(List<EmployeeSkillView> skillInfo) {
        this.skillInfo = skillInfo;
    }

    public List<TestSkillListView> getSkillTestInfo() {
        return skillTestInfo;
    }

    public void setSkillTestInfo(List<TestSkillListView> skillTestInfo) {
        this.skillTestInfo = skillTestInfo;
    }
}
