package com.harmonycloud.view;

public class ProjectDelayRankingView {

    private Integer fkProjectId;
    private String projectName;
    private String milestonePhase;
    private Integer delayTime;

    public ProjectDelayRankingView() {
    }

    public ProjectDelayRankingView(Integer fkProjectId, String projectName, String milestonePhase, Integer delayTime) {
        this.fkProjectId = fkProjectId;
        this.projectName = projectName;
        this.milestonePhase = milestonePhase;
        this.delayTime = delayTime;
    }

    public Integer getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Integer fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMilestonePhase() {
        return milestonePhase;
    }

    public void setMilestonePhase(String milestonePhase) {
        this.milestonePhase = milestonePhase;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
    }
}
