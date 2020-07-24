package com.harmonycloud.bean.contract;

import java.util.Date;

public class ContractStep {
    private Integer id;
    private Integer fkContractId;
    private String contractStage;
    private String contractProportion;
    private double contractAmount;
    private String contractStandard;
    private String fkEmployeeGh;
    private String employeeName;
    private Date contractTime;
    private String contractRemark;
    private Date acceptanceTime;
    private Integer acceptanceDone;
    private Date paymentTime;
    private Integer paymentDone;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkContractId() {
        return fkContractId;
    }

    public void setFkContractId(Integer fkContractId) {
        this.fkContractId = fkContractId;
    }

    public String getContractStage() {
        return contractStage;
    }

    public void setContractStage(String contractStage) {
        this.contractStage = contractStage;
    }

    public String getContractProportion() {
        return contractProportion;
    }

    public void setContractProportion(String contractProportion) {
        this.contractProportion = contractProportion;
    }

    public double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractStandard() {
        return contractStandard;
    }

    public void setContractStandard(String contractStandard) {
        this.contractStandard = contractStandard;
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

    public Date getContractTime() {
        return contractTime;
    }

    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }

    public String getContractRemark() {
        return contractRemark;
    }

    public void setContractRemark(String contractRemark) {
        this.contractRemark = contractRemark;
    }

    public Date getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(Date acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public Integer getAcceptanceDone() {
        return acceptanceDone;
    }

    public void setAcceptanceDone(Integer acceptanceDone) {
        this.acceptanceDone = acceptanceDone;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getPaymentDone() {
        return paymentDone;
    }

    public void setPaymentDone(Integer paymentDone) {
        this.paymentDone = paymentDone;
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
