package com.harmonycloud.view;

import java.sql.Date;
import java.util.List;

public class ProjEndApplyDetailView {

    private Long id;
    private Long fkProjId;
    private String projName;
    private String applyName;
    private String projSaleman;
    private Date projectEndTime;
    private Date actualEndTime;
    private Date createTime;
    private String projEndRemark;
    private String endApplyStatus;
    private String applyApprovalName;
    private String projEndMeeting;
    private Date meetingTime;
    private List<ProjectFileView> projectFileViews;
//    private file[] files;


    public List<ProjectFileView> getProjectFileViews() {
        return projectFileViews;
    }

    public void setProjectFileViews(List<ProjectFileView> projectFileViews) {
        this.projectFileViews = projectFileViews;
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

    public String getProjSaleman() {
        return projSaleman;
    }

    public void setProjSaleman(String projSaleman) {
        this.projSaleman = projSaleman;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProjEndRemark() {
        return projEndRemark;
    }

    public void setProjEndRemark(String projEndRemark) {
        this.projEndRemark = projEndRemark;
    }

    public String getEndApplyStatus() {
        return endApplyStatus;
    }

    public void setEndApplyStatus(String endApplyStatus) {
        this.endApplyStatus = endApplyStatus;
    }

    public String getApplyApprovalName() {
        return applyApprovalName;
    }

    public void setApplyApprovalName(String applyApprovalName) {
        this.applyApprovalName = applyApprovalName;
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
}
