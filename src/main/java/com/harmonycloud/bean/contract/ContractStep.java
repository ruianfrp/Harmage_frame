package com.harmonycloud.bean.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ContractStep {
    private Integer id;  //合同阶段id
    private Integer fkContractId;  //合同id
    private String contractStage;  //合同阶段名称
    private String contractProportion;  //比例
    private double contractAmount;  //金额
    private String contractStandard;  //验收标准
    private String fkEmployeeGh;  //负责人工号
    private String employeeName;  //负责人姓名
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date contractTime;  //预计开票时间 
    private String contractRemark;  //备注
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date acceptanceTime;  //验收报告上传时间
    private Integer acceptanceDone;  //验收报告是否上传（0未上传，1已上传）
    private ContractFileView acceptanceFile;  //验收报告文件信息
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invoiceTime;  //开票证明上传时间
    private Integer invoiceDone;  //开票证明是否上传（0未上传，1已上传）
    private ContractFileView invoiceFile;  //开票证明文件信息
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date paymentTime;  //回款证明上传时间
    private Integer paymentDone;  //回款证明是否上传（0未上传，1已上传）
    private ContractFileView paymentFile;  //回款证明文件信息
    private Date createTime;  //录入时间
    private Date updateTime;  //修改时间

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public Integer getInvoiceDone() {
        return invoiceDone;
    }

    public void setInvoiceDone(Integer invoiceDone) {
        this.invoiceDone = invoiceDone;
    }

    public ContractFileView getInvoiceFile() {
        return invoiceFile;
    }

    public void setInvoiceFile(ContractFileView invoiceFile) {
        this.invoiceFile = invoiceFile;
    }

    public ContractFileView getAcceptanceFile() {
        return acceptanceFile;
    }

    public void setAcceptanceFile(ContractFileView acceptanceFile) {
        this.acceptanceFile = acceptanceFile;
    }

    public ContractFileView getPaymentFile() {
        return paymentFile;
    }

    public void setPaymentFile(ContractFileView paymentFile) {
        this.paymentFile = paymentFile;
    }

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
