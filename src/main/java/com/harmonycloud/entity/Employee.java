package com.harmonycloud.entity;

import java.util.Date;

public class Employee {
    private String employeeGh;

    private String employeeName;

    private String employeeDep;

    private String employeeJob;

    private String employeeWorkplace;

    private Byte isQuit;

    private Date createTime;

    private Date updateTime;

    private String employeeSex;

    private String employeeType;

    public Employee(String employeeGh, String employeeName, String employeeDep, String employeeJob, String employeeWorkplace, Byte isQuit, Date createTime, Date updateTime, String employeeSex, String employeeType) {
        this.employeeGh = employeeGh;
        this.employeeName = employeeName;
        this.employeeDep = employeeDep;
        this.employeeJob = employeeJob;
        this.employeeWorkplace = employeeWorkplace;
        this.isQuit = isQuit;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.employeeSex = employeeSex;
        this.employeeType = employeeType;
    }

    public Employee() {
        super();
    }

    public String getEmployeeGh() {
        return employeeGh;
    }

    public void setEmployeeGh(String employeeGh) {
        this.employeeGh = employeeGh == null ? null : employeeGh.trim();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public String getEmployeeDep() {
        return employeeDep;
    }

    public void setEmployeeDep(String employeeDep) {
        this.employeeDep = employeeDep == null ? null : employeeDep.trim();
    }

    public String getEmployeeJob() {
        return employeeJob;
    }

    public void setEmployeeJob(String employeeJob) {
        this.employeeJob = employeeJob == null ? null : employeeJob.trim();
    }

    public String getEmployeeWorkplace() {
        return employeeWorkplace;
    }

    public void setEmployeeWorkplace(String employeeWorkplace) {
        this.employeeWorkplace = employeeWorkplace == null ? null : employeeWorkplace.trim();
    }

    public Byte getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Byte isQuit) {
        this.isQuit = isQuit;
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

    public String getEmployeeSex() {
        return employeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        this.employeeSex = employeeSex == null ? null : employeeSex.trim();
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType == null ? null : employeeType.trim();
    }
}