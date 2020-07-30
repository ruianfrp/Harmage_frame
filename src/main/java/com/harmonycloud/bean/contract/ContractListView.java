package com.harmonycloud.bean.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ContractListView {
    private Integer id;  //id
    private Integer fkCustomerId;  //客户id
    private String customerName;  //客户名称
    private Integer fkProjectId;  //项目id
    private String projLine;  //项目产品线（类型）
    private String projSubstate;  //项目状态
    private String projName;  //项目名称
    private String contractNo;  //合同编号
    private String contractName;  //合同名称
    private double contractMoney;  //合同金额
    private double received;  //已收款
    private double uncollected;  //未收款
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date contractDate;  //合同签订时间
    private String fkEmployeeGh;  //合同签署人工号
    private String employeeName;  //合同签署人姓名
    private String contractStage;  //下一阶段
    private String contractProportion;  //比例
    private double contractAmount;  //金额
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date contractTime;  //预计开票时间
    private String contractRemark;  //备注
    private ContractFileView contractFile;  //合同文件信息
    private Date createTime;  //合同录入时间
    private Date updateTime;  //合同修改时间

    public ContractFileView getContractFile() {
        return contractFile;
    }

    public void setContractFile(ContractFileView contractFile) {
        this.contractFile = contractFile;
    }

    public double getReceived() {
        return received;
    }

    public void setReceived(double received) {
        this.received = received;
    }

    public double getUncollected() {
        return uncollected;
    }

    public void setUncollected(double uncollected) {
        this.uncollected = uncollected;
    }

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

    public String getProjLine() {
        return projLine;
    }

    public void setProjLine(String projLine) {
        this.projLine = projLine;
    }

    public String getProjSubstate() {
        return projSubstate;
    }

    public void setProjSubstate(String projSubstate) {
        this.projSubstate = projSubstate;
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
