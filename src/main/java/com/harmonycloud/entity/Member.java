package com.harmonycloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Member {
    private Long id;

    private String fkEmployeeGh;

    private Integer fkProjectId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date estimateStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date realStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date estimateEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date realEndTime;

    private String memberGroup;

    private String memberJob;

    private String memberSup;

    private String memberType;

    private String memberFunc;

    private BigDecimal memberBonus;

    private String memberJobBand;

    private Integer isQuit;

    private Date createTime;

    private Date updateTime;

    private String memberRemarks;

    public Member(Long id, String fkEmployeeGh, Integer fkProjectId, Date estimateStartTime, Date realStartTime, Date estimateEndTime, Date realEndTime, String memberGroup, String memberJob, String memberSup, String memberType, String memberFunc, BigDecimal memberBonus, String memberJobBand, Integer isQuit, Date createTime, Date updateTime, String memberRemarks) {
        this.id = id;
        this.fkEmployeeGh = fkEmployeeGh;
        this.fkProjectId = fkProjectId;
        this.estimateStartTime = estimateStartTime;
        this.realStartTime = realStartTime;
        this.estimateEndTime = estimateEndTime;
        this.realEndTime = realEndTime;
        this.memberGroup = memberGroup;
        this.memberJob = memberJob;
        this.memberSup = memberSup;
        this.memberType = memberType;
        this.memberFunc = memberFunc;
        this.memberBonus = memberBonus;
        this.memberJobBand = memberJobBand;
        this.isQuit = isQuit;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.memberRemarks = memberRemarks;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Member() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh == null ? null : fkEmployeeGh.trim();
    }

    public Integer getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Integer fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public Date getEstimateStartTime() {
        return estimateStartTime;
    }

    public void setEstimateStartTime(Date estimateStartTime) {
        this.estimateStartTime = estimateStartTime;
    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Date getEstimateEndTime() {
        return estimateEndTime;
    }

    public void setEstimateEndTime(Date estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(String memberGroup) {
        this.memberGroup = memberGroup == null ? null : memberGroup.trim();
    }

    public String getMemberJob() {
        return memberJob;
    }

    public void setMemberJob(String memberJob) {
        this.memberJob = memberJob == null ? null : memberJob.trim();
    }

    public String getMemberSup() {
        return memberSup;
    }

    public void setMemberSup(String memberSup) {
        this.memberSup = memberSup == null ? null : memberSup.trim();
    }

    public String getMemberFunc() {
        return memberFunc;
    }

    public void setMemberFunc(String memberFunc) {
        this.memberFunc = memberFunc == null ? null : memberFunc.trim();
    }

    public BigDecimal getMemberBonus() {
        return memberBonus;
    }

    public void setMemberBonus(BigDecimal memberBonus) {
        this.memberBonus = memberBonus;
    }

    public String getMemberJobBand() {
        return memberJobBand;
    }

    public void setMemberJobBand(String memberJobBand) {
        this.memberJobBand = memberJobBand == null ? null : memberJobBand.trim();
    }

    public Integer getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Integer isQuit) {
        this.isQuit = isQuit;
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

    public String getMemberRemarks() {
        return memberRemarks;
    }

    public void setMemberRemarks(String memberRemarks) {
        this.memberRemarks = memberRemarks;
    }
}