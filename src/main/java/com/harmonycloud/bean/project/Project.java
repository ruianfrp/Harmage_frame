package com.harmonycloud.bean.project;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Project {
    private Long id;

    private Long fkCustomerId;

    private String projPmGh;

    private String projSaleman;

    private String projName;

    private String projType;

    private String projLine;

    private String projPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projPrestaTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projPreendTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projEndTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projEstablishTime;

    private BigDecimal projMoney;

    private BigDecimal projAllCost;

    private BigDecimal projCurrentCost;

    private String projStatus;

    private String projRemark;

    private Date createTime;

    private Date updateTime;

    private String projCooperationModel;

    private String projDevModel;

    private String projDescription;

    private String projEndType;

    public Project() {
        super();
    }

    public Project(Long id, Long fkCustomerId, String projPmGh, String projSaleman, String projName, String projType, String projLine, String projPlace, Date projPrestaTime, Date projPreendTime, Date projStartTime, Date projEndTime, Date projEstablishTime, BigDecimal projMoney, BigDecimal projAllCost, BigDecimal projCurrentCost, String projStatus, String projRemark, Date createTime, Date updateTime, String projCooperationModel, String projDevModel, String projDescription, String projEndType) {
        this.id = id;
        this.fkCustomerId = fkCustomerId;
        this.projPmGh = projPmGh;
        this.projSaleman = projSaleman;
        this.projName = projName;
        this.projType = projType;
        this.projLine = projLine;
        this.projPlace = projPlace;
        this.projPrestaTime = projPrestaTime;
        this.projPreendTime = projPreendTime;
        this.projStartTime = projStartTime;
        this.projEndTime = projEndTime;
        this.projEstablishTime = projEstablishTime;
        this.projMoney = projMoney;
        this.projAllCost = projAllCost;
        this.projCurrentCost = projCurrentCost;
        this.projStatus = projStatus;
        this.projRemark = projRemark;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.projCooperationModel = projCooperationModel;
        this.projDevModel = projDevModel;
        this.projDescription = projDescription;
        this.projEndType = projEndType;
    }

    public BigDecimal getProjAllCost() {
        return projAllCost;
    }

    public void setProjAllCost(BigDecimal projAllCost) {
        this.projAllCost = projAllCost;
    }

    public String getProjEndType() {
        return projEndType;
    }

    public void setProjEndType(String projEndType) {
        this.projEndType = projEndType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkCustomerId() {
        return fkCustomerId;
    }

    public void setFkCustomerId(Long fkCustomerId) {
        this.fkCustomerId = fkCustomerId;
    }

    public String getProjPmGh() {
        return projPmGh;
    }

    public void setProjPmGh(String projPmName) {
        this.projPmGh = projPmName == null ? null : projPmName.trim();
    }

    public String getProjSaleman() {
        return projSaleman;
    }

    public void setProjSaleman(String projSaleman) {
        this.projSaleman = projSaleman == null ? null : projSaleman.trim();
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName == null ? null : projName.trim();
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType == null ? null : projType.trim();
    }

    public String getProjLine() {
        return projLine;
    }

    public void setProjLine(String projLine) {
        this.projLine = projLine == null ? null : projLine.trim();
    }

    public String getProjPlace() {
        return projPlace;
    }

    public void setProjPlace(String projPlace) {
        this.projPlace = projPlace == null ? null : projPlace.trim();
    }

    public Date getProjPrestaTime() {
        return projPrestaTime;
    }

    public void setProjPrestaTime(Date projPrestaTime) {
        this.projPrestaTime = projPrestaTime;
    }

    public Date getProjPreendTime() {
        return projPreendTime;
    }

    public void setProjPreendTime(Date projPreendTime) {
        this.projPreendTime = projPreendTime;
    }

    public Date getProjStartTime() {
        return projStartTime;
    }

    public void setProjStartTime(Date projStartTime) {
        this.projStartTime = projStartTime;
    }

    public Date getProjEndTime() {
        return projEndTime;
    }

    public void setProjEndTime(Date projEndTime) {
        this.projEndTime = projEndTime;
    }

    public Date getProjEstablishTime() {
        return projEstablishTime;
    }

    public void setProjEstablishTime(Date projEstablishTime) {
        this.projEstablishTime = projEstablishTime;
    }

    public BigDecimal getProjMoney() {
        return projMoney;
    }

    public void setProjMoney(BigDecimal projMoney) {
        this.projMoney = projMoney;
    }

    public BigDecimal getProjCurrentCost() {
        return projCurrentCost;
    }

    public void setProjCurrentCost(BigDecimal projCurrentCost) {
        this.projCurrentCost = projCurrentCost;
    }

    public String getProjStatus() {
        return projStatus;
    }

    public void setProjStatus(String projStatus) {
        this.projStatus = projStatus == null ? null : projStatus.trim();
    }

    public String getProjRemark() {
        return projRemark;
    }

    public void setProjRemark(String projRemark) {
        this.projRemark = projRemark == null ? null : projRemark.trim();
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

    public String getProjCooperationModel() {
        return projCooperationModel;
    }

    public void setProjCooperationModel(String projCooperationModel) {
        this.projCooperationModel = projCooperationModel;
    }

    public String getProjDevModel() {
        return projDevModel;
    }

    public void setProjDevModel(String projDevModel) {
        this.projDevModel = projDevModel;
    }

    public String getProjDescription() {
        return projDescription;
    }

    public void setProjDescription(String projDescription) {
        this.projDescription = projDescription;
    }
}