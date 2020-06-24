package com.harmonycloud.bean.milestone;

import java.util.Date;

public class MilestoneBacklog {
    private Long id;

    private Long fkProjectId;

    private String milestone;

    private String milestoneWeekPlan;
    private String operation;


    private Date ModifyTime;

    public MilestoneBacklog(Long id, Long fkProjectId, String milestone,String milestoneWeekPlan,String operation,Date ModifyTime) {
        this.id = id;
        this.fkProjectId = fkProjectId;
        this.milestone = milestone;
        this.milestoneWeekPlan=milestoneWeekPlan;
        this.operation = operation;
        this.ModifyTime = ModifyTime;
    }

    public MilestoneBacklog() {
        super();
    }

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


    public String getMilestone() {
        return milestone;
    }
    public void setMilestone(String milestone) {
        this.milestone = milestone == null ? null : milestone.trim();
    }


    public String getMilestoneWeekPlan() { return milestoneWeekPlan; }
    public void setMilestoneWeekPlan(String milestoneWeekPlan) {
        this.milestoneWeekPlan = milestoneWeekPlan == null ? null : milestoneWeekPlan.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }


    public Date getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.ModifyTime = modifyTime;
    }
}