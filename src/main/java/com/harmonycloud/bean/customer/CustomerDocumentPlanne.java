package com.harmonycloud.bean.customer;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerDocumentPlanne {
    private Integer id;
    private Integer fkCustomerId;
    private String competitor;
    private Integer customerCommunicate;
    private String nextPlan;
    private String ifHaveproj;
    private String prodInvolved;
    private String projSituation;
    private String nextWork;
    private BigDecimal budget;
    private BigDecimal offer;
    private String graspDegree;
    private String decisionChain;
    private Integer customerStatus;
    private String cooperationChannel;
    private Date createTime;
    private Date updateTime;

    public CustomerDocumentPlanne(){}

    public CustomerDocumentPlanne(Integer id, Integer fkCustomerId, String competitor, Integer customerCommunicate, String nextPlan, String ifHaveproj, String prodInvolved, String projSituation, String nextWork, BigDecimal budget, BigDecimal offer, String graspDegree, String decisionChain, Integer customerStatus, String cooperationChannel, Date createTime, Date updateTime) {
        this.id = id;
        this.fkCustomerId = fkCustomerId;
        this.competitor = competitor;
        this.customerCommunicate = customerCommunicate;
        this.nextPlan = nextPlan;
        this.ifHaveproj = ifHaveproj;
        this.prodInvolved = prodInvolved;
        this.projSituation = projSituation;
        this.nextWork = nextWork;
        this.budget = budget;
        this.offer = offer;
        this.graspDegree = graspDegree;
        this.decisionChain = decisionChain;
        this.customerStatus = customerStatus;
        this.cooperationChannel = cooperationChannel;
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

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public Integer getCustomerCommunicate() {
        return customerCommunicate;
    }

    public void setCustomerCommunicate(Integer customerCommunicate) {
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

    public Integer getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(Integer customerStatus) {
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
