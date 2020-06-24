package com.harmonycloud.bean.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerDocumentRecord {
    private Integer id;
    private Integer fkCustomerId;
    private Integer fkContactsId;
    private Integer documentType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date documentContactTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date documentNextContactTime;
    private String exchangeTheme;
    private String exchangePerson;
    private Integer exchangeReason;
    private Integer nextPlan;
    private String detailedContent;
    private String salesmanEmployeeGh;
    private Date createTime;
    private Date updateTime;

    public CustomerDocumentRecord(){}

    public CustomerDocumentRecord(Integer id, Integer fkCustomerId, Integer fkContactsId, Integer documentType, Date documentContactTime, Date documentNextContactTime, String exchangeTheme, String exchangePerson, Integer exchangeReason, Integer nextPlan, String detailedContent, String salesmanEmployeeGh, Date createTime, Date updateTime) {
        this.id = id;
        this.fkCustomerId = fkCustomerId;
        this.fkContactsId = fkContactsId;
        this.documentType = documentType;
        this.documentContactTime = documentContactTime;
        this.documentNextContactTime = documentNextContactTime;
        this.exchangeTheme = exchangeTheme;
        this.exchangePerson = exchangePerson;
        this.exchangeReason = exchangeReason;
        this.nextPlan = nextPlan;
        this.detailedContent = detailedContent;
        this.salesmanEmployeeGh = salesmanEmployeeGh;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Integer getFkContactsId() {
        return fkContactsId;
    }

    public void setFkContactsId(Integer fkContactsId) {
        this.fkContactsId = fkContactsId;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
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

    public Integer getExchangeReason() {
        return exchangeReason;
    }

    public void setExchangeReason(Integer exchangeReason) {
        this.exchangeReason = exchangeReason;
    }

    public Integer getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(Integer nextPlan) {
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

    public String getSalesmanEmployeeGh() {
        return salesmanEmployeeGh;
    }

    public void setSalesmanEmployeeGh(String salesmanEmployeeGh) {
        this.salesmanEmployeeGh = salesmanEmployeeGh;
    }
}
