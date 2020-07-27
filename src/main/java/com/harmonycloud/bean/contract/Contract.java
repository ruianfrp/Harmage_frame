package com.harmonycloud.bean.contract;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Contract {
    private Integer id;
    private Integer fkCustomerId;
    private String customerName;
    private Integer fkProjectId;
    private String projName;
    private String contractNo;
    private String contractName;
    private double contractMoney;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractDate;
    private String fkEmployeeGh;
    private String employeeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkCustomerId() {
        return fkCustomerId;
    }

    public void setFkCustomerId(Integer fkCustomerId) {
        this.fkCustomerId = fkCustomerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Integer fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
