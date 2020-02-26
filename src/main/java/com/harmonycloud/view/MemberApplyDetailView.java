package com.harmonycloud.view;

import java.sql.Date;
import java.util.List;

public class MemberApplyDetailView {
    private Integer id;
    private Integer projId;
    private String projName;
    private String projPlace;
    private String employeeName;
    private List<String> skillAllName;
    private String memberStartTime;
    private String memberEndTime;
    private String memberJoinSup;
    private String memberJoinType;
    private Integer lastTime;
    private String applyStatus;
    private String memberRecommendName;
    private String memberApplicationReason;
    private Date createTime;
    private List<Integer> skillId;
    private List<Integer> skillTestId;

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getProjPlace() {
        return projPlace;
    }

    public void setProjPlace(String projPlace) {
        this.projPlace = projPlace;
    }

    public List<Integer> getSkillId() {
        return skillId;
    }

    public void setSkillId(List<Integer> skillId) {
        this.skillId = skillId;
    }

    public List<Integer> getSkillTestId() {
        return skillTestId;
    }

    public void setSkillTestId(List<Integer> skillTestId) {
        this.skillTestId = skillTestId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public List<String> getSkillAllName() {
        return skillAllName;
    }

    public void setSkillAllName(List<String> skillAllName) {
        this.skillAllName = skillAllName;
    }

    public String getMemberStartTime() {
        return memberStartTime;
    }

    public void setMemberStartTime(String memberStartTime) {
        this.memberStartTime = memberStartTime;
    }

    public String getMemberEndTime() {
        return memberEndTime;
    }

    public void setMemberEndTime(String memberEndTime) {
        this.memberEndTime = memberEndTime;
    }

    public String getMemberJoinSup() {
        return memberJoinSup;
    }

    public void setMemberJoinSup(String memberJoinSup) {
        this.memberJoinSup = memberJoinSup;
    }

    public String getMemberJoinType() {
        return memberJoinType;
    }

    public void setMemberJoinType(String memberJoinType) {
        this.memberJoinType = memberJoinType;
    }

    public Integer getLastTime() {
        return lastTime;
    }

    public void setLastTime(Integer lastTime) {
        this.lastTime = lastTime;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getMemberRecommendName() {
        return memberRecommendName;
    }

    public void setMemberRecommendName(String memberRecommendName) {
        this.memberRecommendName = memberRecommendName;
    }

    public String getMemberApplicationReason() {
        return memberApplicationReason;
    }

    public void setMemberApplicationReason(String memberApplicationReason) {
        this.memberApplicationReason = memberApplicationReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
