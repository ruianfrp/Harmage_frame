package com.harmonycloud.view;

import java.sql.Date;

public class EmployeeExperienceView {
    private String employeeGh;
    private String employeeName;
    private String projName;
    private String memberJob;
    private String memberSup;
    private String memberFunc;
    private String projPlace;
    private Date memberStartTime;
    private Date memberEndTime;

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

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getMemberJob() {
        return memberJob;
    }

    public void setMemberJob(String memberJob) {
        this.memberJob = memberJob;
    }

    public String getMemberSup() {
        return memberSup;
    }

    public void setMemberSup(String memberSup) {
        this.memberSup = memberSup;
    }

    public String getMemberFunc() {
        return memberFunc;
    }

    public void setMemberFunc(String memberFunc) {
        this.memberFunc = memberFunc;
    }

    public String getProjPlace() {
        return projPlace;
    }

    public void setProjPlace(String projPlace) {
        this.projPlace = projPlace;
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
}
