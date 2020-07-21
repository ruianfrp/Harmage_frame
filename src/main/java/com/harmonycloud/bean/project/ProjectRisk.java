package com.harmonycloud.bean.project;

import java.util.Date;

public class ProjectRisk {
    private Long id;
    private Long fkProjectId;
    private String riskDescription;
    private String solution;
    private Date estSettleTime;
    private Date actSettleTime;
    private String currentState;
    private Date proposedTime;
    private String proposedPerson;
    private String inChargePerson;

    public ProjectRisk(Long fkProjectId,String riskDescription,Date proposedTime,String proposedPerson){
        this.fkProjectId=fkProjectId;
        this.riskDescription=riskDescription;
        this.proposedTime=proposedTime;
        this.proposedPerson=proposedPerson;
    }
    public ProjectRisk(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Long fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Date getEstSettleTime() {
        return estSettleTime;
    }

    public void setEstSettleTime(Date estSettleTime) {
        this.estSettleTime = estSettleTime;
    }

    public Date getActSettleTime() {
        return actSettleTime;
    }

    public void setActSettleTime(Date actSettleTime) {
        this.actSettleTime = actSettleTime;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Date getProposedTime() {
        return proposedTime;
    }

    public void setProposedTime(Date proposedTime) {
        this.proposedTime = proposedTime;
    }

    public String getProposedPerson() {
        return proposedPerson;
    }

    public void setProposedPerson(String proposedPerson) {
        this.proposedPerson = proposedPerson;
    }

    public String getInChargePerson() {
        return inChargePerson;
    }

    public void setInChargePerson(String inChargePerson) {
        this.inChargePerson = inChargePerson;
    }
}
