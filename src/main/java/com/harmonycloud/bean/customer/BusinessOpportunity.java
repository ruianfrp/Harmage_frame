package com.harmonycloud.bean.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BusinessOpportunity {
     private Long id;
     private String bizOppName;
     private String assCustomer;
     private String assProject;
     private String projLine;
     private String channelSource;
     private String currentState;
     private String competitors;
     private String bizOppOutline;
     private String historyOutline;
     private String difficulty;
     private int probability;
     private int estCost;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
     private Date estBidTime;
     private String bizDevExpert;

     public BusinessOpportunity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizOppName() {
        return bizOppName;
    }

    public void setBizOppName(String bizOppName) {
        this.bizOppName = bizOppName;
    }

    public String getAssCustomer() {
        return assCustomer;
    }

    public void setAssCustomer(String assCustomer) {
        this.assCustomer = assCustomer;
    }

    public String getAssProject() {
        return assProject;
    }

    public void setAssProject(String assProject) {
        this.assProject = assProject;
    }

    public String getProjLine() {
        return projLine;
    }

    public void setProjLine(String projLine) {
        this.projLine = projLine;
    }

    public String getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(String channelSource) {
        this.channelSource = channelSource;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCompetitors() {
        return competitors;
    }

    public void setCompetitors(String competitors) {
        this.competitors = competitors;
    }

    public String getBizOppOutline() {
        return bizOppOutline;
    }

    public void setBizOppOutline(String bizOppOutline) {
        this.bizOppOutline = bizOppOutline;
    }

    public String getHistoryOutline() {
        return historyOutline;
    }

    public void setHistoryOutline(String historyOutline) {
        this.historyOutline = historyOutline;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getEstCost() {
        return estCost;
    }

    public void setEstCost(int estCost) {
        this.estCost = estCost;
    }

    public Date getEstBidTime() {
        return estBidTime;
    }

    public void setEstBidTime(Date estBidTime) {
        this.estBidTime = estBidTime;
    }

    public String getBizDevExpert() {
        return bizDevExpert;
    }

    public void setBizDevExpert(String bizDevExpert) {
        this.bizDevExpert = bizDevExpert;
    }

}
