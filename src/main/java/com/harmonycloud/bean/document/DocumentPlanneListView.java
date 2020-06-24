package com.harmonycloud.bean.document;

import java.math.BigDecimal;
import java.util.Date;

public class DocumentPlanneListView {
    private Integer id;
    private Integer customerId;
    private String customerName;
    private String competitor;
    private String customerCommunicate;
    private String nextPlan;
    private String ifHaveproj;
    private String prodInvolved;
    private String projSituation;
    private String nextWork;
    private BigDecimal budget;
    private BigDecimal offer;
    private String graspDegree;
    private String decisionChain;
    private String customerStatus;
    private String cooperationChannel;
    private String employeeName;
    private Date createTime;
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

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public String getCustomerCommunicate() {
        return customerCommunicate;
    }

    public void setCustomerCommunicate(String customerCommunicate) {
        this.customerCommunicate = customerCommunicate;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public String getIfHaveproj() {
        return ifHaveproj;
    }

    public void setIfHaveproj(String ifHaveproj) {
        this.ifHaveproj = ifHaveproj;
    }

    public String getProdInvolved() {
        return prodInvolved;
    }

    public void setProdInvolved(String prodInvolved) {
        this.prodInvolved = prodInvolved;
    }

    public String getProjSituation() {
        return projSituation;
    }

    public void setProjSituation(String projSituation) {
        this.projSituation = projSituation;
    }

    public String getNextWork() {
        return nextWork;
    }

    public void setNextWork(String nextWork) {
        this.nextWork = nextWork;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getOffer() {
        return offer;
    }

    public void setOffer(BigDecimal offer) {
        this.offer = offer;
    }

    public String getGraspDegree() {
        return graspDegree;
    }

    public void setGraspDegree(String graspDegree) {
        this.graspDegree = graspDegree;
    }

    public String getDecisionChain() {
        return decisionChain;
    }

    public void setDecisionChain(String decisionChain) {
        this.decisionChain = decisionChain;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCooperationChannel() {
        return cooperationChannel;
    }

    public void setCooperationChannel(String cooperationChannel) {
        this.cooperationChannel = cooperationChannel;
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
