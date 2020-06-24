package com.harmonycloud.bean.document;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DocumentRecordListView {
    private Integer id;
    private Integer customerId;
    private String customerName;
    private Integer contactsId;
    private String contactsName;
    private String documentType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date documentContactTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date documentNextContactTime;
    private String exchangeTheme;
    private String exchangePerson;
    private String exchangeReason;
    private String nextPlan;
    private String detailedContent;
    private String employeeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getContactsId() {
        return contactsId;
    }

    public void setContactsId(Integer contactsId) {
        this.contactsId = contactsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Date getDocumentContactTime() {
        return documentContactTime;
    }

    public void setDocumentContactTime(Date documentContactTime) {
        this.documentContactTime = documentContactTime;
    }

    public Date getDocumentNextContactTime() {
        return documentNextContactTime;
    }

    public void setDocumentNextContactTime(Date documentNextContactTime) {
        this.documentNextContactTime = documentNextContactTime;
    }

    public String getExchangeTheme() {
        return exchangeTheme;
    }

    public void setExchangeTheme(String exchangeTheme) {
        this.exchangeTheme = exchangeTheme;
    }

    public String getExchangePerson() {
        return exchangePerson;
    }

    public void setExchangePerson(String exchangePerson) {
        this.exchangePerson = exchangePerson;
    }

    public String getExchangeReason() {
        return exchangeReason;
    }

    public void setExchangeReason(String exchangeReason) {
        this.exchangeReason = exchangeReason;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public String getDetailedContent() {
        return detailedContent;
    }

    public void setDetailedContent(String detailedContent) {
        this.detailedContent = detailedContent;
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
