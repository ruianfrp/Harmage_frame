package com.harmonycloud.bean.project;

public class ProjectProbRiskState {
    private Integer projectId;
    private String projName;
    private String description;
    private Integer probRiskCount;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProbRiskCount() {
        return probRiskCount;
    }

    public void setProbRiskCount(Integer probRiskCount) {
        this.probRiskCount = probRiskCount;
    }

}
