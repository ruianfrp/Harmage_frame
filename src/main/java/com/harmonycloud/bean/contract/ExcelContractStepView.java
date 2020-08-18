package com.harmonycloud.bean.contract;

import java.util.Date;

public class ExcelContractStepView {
    private String contractStage;
    private String contractProportion;
    private double contractAmount;
    private String contractStandard;
    private Date contractTime;
    private String contractRemark;
    private String employeeName;
    private Date acceptanceTime;
    private String acceptanceDone;
    private Date invoiceTime;
    private String invoiceDone;
    private Date paymentTime;
    private String paymentDone;

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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(Date acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public String getAcceptanceDone() {
        return acceptanceDone;
    }

    public void setAcceptanceDone(String acceptanceDone) {
        this.acceptanceDone = acceptanceDone;
    }

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getInvoiceDone() {
        return invoiceDone;
    }

    public void setInvoiceDone(String invoiceDone) {
        this.invoiceDone = invoiceDone;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentDone() {
        return paymentDone;
    }

    public void setPaymentDone(String paymentDone) {
        this.paymentDone = paymentDone;
    }
}
