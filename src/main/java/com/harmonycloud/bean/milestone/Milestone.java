package com.harmonycloud.bean.milestone;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Milestone {
    private Long milestoneIndex;
    private Long fkProjectId;
    private String milestonePhase;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date milestonePrestaTime;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date milestonePreendTime;

    @JSONField(format="yyyy-MM-dd")
    private Date milestoneNewPrestaTime;

    @JSONField(format="yyyy-MM-dd")
    private Date milestoneNewPreendTime;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date milestoneStartTime;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date milestoneEndTime;
    private String milestoneStatus;
    private String milestoneRemark;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date createTime;
    /*@JsonFormat(pattern = "yyyy-MM-dd")*/
    @JSONField(format="yyyy-MM-dd")
    private Date updateTime;

    public Milestone(Long milestoneIndex, Long fkProjectId, String milestonePhase, Date milestonePrestaTime, Date milestonePreendTime, Date milestoneNewPrestaTime, Date milestoneNewPreendTime, Date milestoneStartTime, Date milestoneEndTime, String milestoneStatus, String milestoneRemark, Date createTime, Date updateTime) {
        this.milestoneIndex = milestoneIndex;
        this.fkProjectId = fkProjectId;
        this.milestonePhase = milestonePhase;
        this.milestonePrestaTime = milestonePrestaTime;
        this.milestonePreendTime = milestonePreendTime;
        this.milestoneNewPrestaTime = milestoneNewPrestaTime;
        this.milestoneNewPreendTime = milestoneNewPreendTime;
        this.milestoneStartTime = milestoneStartTime;
        this.milestoneEndTime = milestoneEndTime;
        this.milestoneStatus = milestoneStatus;
        this.milestoneRemark = milestoneRemark;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Milestone() {
        super();
    }

    public Date getMilestoneNewPrestaTime() {
        return milestoneNewPrestaTime;
    }

    public void setMilestoneNewPrestaTime(Date milestoneNewPrestaTime) {
        this.milestoneNewPrestaTime = milestoneNewPrestaTime;
    }

    public Date getMilestoneNewPreendTime() {
        return milestoneNewPreendTime;
    }

    public void setMilestoneNewPreendTime(Date milestoneNewPreendTime) {
        this.milestoneNewPreendTime = milestoneNewPreendTime;
    }

    public Long getMilestoneIndex() {
        return milestoneIndex;
    }

    public void setMilestoneIndex(Long milestoneIndex) {
        this.milestoneIndex = milestoneIndex;
    }

    public Long getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Long fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getMilestonePhase() {
        return milestonePhase;
    }

    public void setMilestonePhase(String milestonePhase) {
        this.milestonePhase = milestonePhase == null ? null : milestonePhase.trim();
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

    public Date getMilestoneStartTime() {
        return milestoneStartTime;
    }

    public void setMilestoneStartTime(Date milestoneStartTime) {
        this.milestoneStartTime = milestoneStartTime;
    }

    public Date getMilestoneEndTime() {
        return milestoneEndTime;
    }

    public void setMilestoneEndTime(Date milestoneEndTime) {
        this.milestoneEndTime = milestoneEndTime;
    }

    public String getMilestoneStatus() {
        return milestoneStatus;
    }


    public void setMilestoneStatus(String milestoneStatus) {
        this.milestoneStatus = milestoneStatus == null ? null : milestoneStatus.trim();
    }

    public String getMilestoneRemark() {
        return milestoneRemark;
    }

    public void setMilestoneRemark(String milestoneRemark) {
        this.milestoneRemark = milestoneRemark;
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