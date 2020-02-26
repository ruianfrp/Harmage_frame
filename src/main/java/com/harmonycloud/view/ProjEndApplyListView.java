package com.harmonycloud.view;

import java.sql.Date;

public class ProjEndApplyListView {

    private Long id;
    private Long fkProjId;
    private String projName;
    private String applyName;
    private Date projectEndTime;
    private Date actualEndTime;
    private String endApplyStatus;
    private String applyApprovalName;
    private String projEndMeeting;
    private Date meetingTime;
    private String createTime;
    private String updateTime;

    public String getApplyApprovalName() {
        return applyApprovalName;
    }

    public void setApplyApprovalName(String applyApprovalName) {
        this.applyApprovalName = applyApprovalName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(Long fkProjId) {
        this.fkProjId = fkProjId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getProjectEndTime() {
        return projectEndTime;
    }

    public void setProjectEndTime(Date projectEndTime) {
        this.projectEndTime = projectEndTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getEndApplyStatus() {
        return endApplyStatus;
    }

    public void setEndApplyStatus(String endApplyStatus) {
        this.endApplyStatus = endApplyStatus;
    }

    public String getProjEndMeeting() {
        return projEndMeeting;
    }

    public void setProjEndMeeting(String projEndMeeting) {
        this.projEndMeeting = projEndMeeting;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
