package com.harmonycloud.bean.execl;

public class EmployeeReportExecl {

    public EmployeeReportExecl() {}

    public EmployeeReportExecl(Integer employeeId,String employeeName,Integer projectId,String projectName,Integer inputDays, Integer totalDays) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.inputDays = inputDays;
        this.totalDays = totalDays;
    }

    @ExcelColumn(value = "工号", col = 1)
    private Integer employeeId;
    @ExcelColumn(value = "员工姓名", col = 2)
    private String employeeName;
    @ExcelColumn(value = "项目ID", col = 3)
    private Integer projectId;
    @ExcelColumn(value = "项目名称", col = 4)
    private String projectName;
    @ExcelColumn(value = "本月投入时间",col = 5)
    private Integer inputDays;
    @ExcelColumn(value = "本月总时间", col = 6)
    private Integer totalDays;

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

    public Integer getInputDays() {
        return inputDays;
    }

    public void setInputDays(Integer inputDays) {
        this.inputDays = inputDays;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }
}
