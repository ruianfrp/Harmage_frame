package com.harmonycloud.bean.milestone;

import java.util.Date;

public class MilestoneStatusView {
    private Integer milestoneIndex;
    private Integer fkProjectId;
    private Date milestonePrestaTime;
    private Date milestonePreendTime;
    private Integer requireDay;
    private Integer exceedDay;

    public Integer getMilestoneIndex() {
        return milestoneIndex;
    }

    public void setMilestoneIndex(Integer milestoneIndex) {
        this.milestoneIndex = milestoneIndex;
    }

    public Integer getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Integer fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public Date getMilestonePrestaTime() {
        return milestonePrestaTime;
    }

    public void setMilestonePrestaTime(Date milestonePrestaTime) {
        this.milestonePrestaTime = milestonePrestaTime;
    }

    public Date getMilestonePreendTime() {
        return milestonePreendTime;
    }

    public void setMilestonePreendTime(Date milestonePreendTime) {
        this.milestonePreendTime = milestonePreendTime;
    }

    public Integer getRequireDay() {
        return requireDay;
    }

    public void setRequireDay(Integer requireDay) {
        this.requireDay = requireDay;
    }

    public Integer getExceedDay() {
        return exceedDay;
    }

    public void setExceedDay(Integer exceedDay) {
        this.exceedDay = exceedDay;
    }
}
