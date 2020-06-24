package com.harmonycloud.bean.execl;

import java.util.Date;

public class WorkingReportExcel {

    private Date workingDate;
    @ExcelColumn(value = "工号", col = 1)
    private Integer employeeId;
    @ExcelColumn(value = "员工姓名", col = 2)
    private String employeeName;
    @ExcelColumn(value = "项目ID", col = 3)
    private Integer projectId;
    @ExcelColumn(value = "项目名称", col = 4)
    private String projectName;
    @ExcelColumn(value = "加入时间", col = 5)
    private Date joinTime;
    @ExcelColumn(value = "离开时间", col = 6)
    private Date leaveTime;
    @ExcelColumn(value = "投入时间", col = 7)
    private Double workingHours;
    @ExcelColumn(value = "在项目时间", col = 9)
    private Integer totalDays;
    @ExcelColumn(value = "投入时间百分比",col = 8)
    private Double timePercentage;


    public Date getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(Date workingDate) {
        this.workingDate = workingDate;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Double getTimePercentage() {
        return timePercentage;
    }

    public void setTimePercentage(Double timePercentage) {
        this.timePercentage = timePercentage;
    }
}
