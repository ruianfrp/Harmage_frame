package com.harmonycloud.bean.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harmonycloud.util.date.DateUtils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class  ProjectProbRisk{
    private Long id;
    private Long fkProjectId;
    private String description;
    private String solution;
    private Date estSettleTime;
    private Date actSettleTime;
    private Integer currentState;
    private Date proposedTime;
    private String proposedPerson;
    private String inChargePerson;
    private Integer type;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getEstSettleTime() {
        return estSettleTime;
    }

    public void setEstSettleTime(Date estSettleTime) {
        this.estSettleTime = DateUtils.format(estSettleTime);
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getActSettleTime() {
        return actSettleTime;
    }

    public void setActSettleTime(Date actSettleTime) {
        this.actSettleTime = DateUtils.format(actSettleTime);
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getProposedTime() {
        return proposedTime;
    }

    public void setProposedTime(Date proposedTime) {
        this.proposedTime = DateUtils.format(proposedTime);
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
