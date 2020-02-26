package com.harmonycloud.view;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/8 14:32
 */
public class ProjectListView {

    private Long id;
    private String projName;
    private String customerName;
    private String projPlace;
    private String projPmName;
    private String projSaleman;
    private Date projPrestaTime;
    private Date projPreendTime;
    private Date projStartTime;
    private Date projEndTime;
    private String projNumOfPeople;  //人数 不可修改
    private String projStatus;  //状态
    private String projType;
    private String projLine;
    private String customerProvince;
    private String customerCity;
    private String customerPlace;
    private BigDecimal projMoney;
    private BigDecimal projAllCost;
    private BigDecimal projCurrentCost;
    private String projCooperationModel;
    private String projDevModel;
    private String projDescription;
    private Double projWarningLine;  //预警线
    private Double projRealWarningLine;  //实时预警线
    private String projEndType;
    private Integer projPeriod;
    private Integer projLastPeriod;

    public String getCustomerProvince() {
        return customerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public Integer getProjLastPeriod() {
        return projLastPeriod;
    }

    public void setProjLastPeriod(Integer projLastPeriod) {
        this.projLastPeriod = projLastPeriod;
    }

    public BigDecimal getProjAllCost() {
        return projAllCost;
    }

    public void setProjAllCost(BigDecimal projAllCost) {
        this.projAllCost = projAllCost;
    }

    public Double getProjRealWarningLine() {
        return projRealWarningLine;
    }

    public void setProjRealWarningLine(Double projRealWarningLine) {
        this.projRealWarningLine = projRealWarningLine;
    }

    public Integer getProjPeriod() {
        return projPeriod;
    }

    public void setProjPeriod(Integer projPeriod) {
        this.projPeriod = projPeriod;
    }

    public String getProjEndType() {
        return projEndType;
    }

    public void setProjEndType(String projEndType) {
        this.projEndType = projEndType;
    }

    public Date getProjEndTime() {
        return projEndTime;
    }

    public void setProjEndTime(Date projEndTime) {
        this.projEndTime = projEndTime;
    }

    public String getCustomerPlace() {
        return customerPlace;
    }

    public void setCustomerPlace(String customerPlace) {
        this.customerPlace = customerPlace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjPlace() {
        return projPlace;
    }

    public void setProjPlace(String projPlace) {
        this.projPlace = projPlace;
    }

    public String getProjPmName() {
        return projPmName;
    }

    public void setProjPmName(String projPmName) {
        this.projPmName = projPmName;
    }

    public String getProjSaleman() {
        return projSaleman;
    }

    public void setProjSaleman(String projSaleman) {
        this.projSaleman = projSaleman;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
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

    public String getProjNumOfPeople() {
        return projNumOfPeople;
    }

    public void setProjNumOfPeople(String projNumOfPeople) {
        this.projNumOfPeople = projNumOfPeople;
    }

    public String getProjStatus() {
        return projStatus;
    }

    public void setProjStatus(String projStatus) {
        this.projStatus = projStatus;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public String getProjLine() {
        return projLine;
    }

    public void setProjLine(String projLine) {
        this.projLine = projLine;
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

    public Double getProjWarningLine() {
        return projWarningLine;
    }

    public void setProjWarningLine(Double projWarningLine) {
        this.projWarningLine = projWarningLine;
    }
}
