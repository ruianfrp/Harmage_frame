package com.harmonycloud.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/19 17:16
 */
public class MemberView {

    private Long id;

    private String fkEmployeeGh;

    private String memberName;

    private String memberJob;   //角色

    private String memberSup;   //support

    private String memberType;

    private String memberFunc;

    private BigDecimal memberBonus;

    private String memberJobBand;

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

    private Byte isQuit;

    private String memberRemarks;

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Byte getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Byte isQuit) {
        this.isQuit = isQuit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
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

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh;
    }

    public String getMemberJob() {
        return memberJob;
    }

    public void setMemberJob(String memberJob) {
        this.memberJob = memberJob;
    }

    public String getMemberSup() {
        return memberSup;
    }

    public void setMemberSup(String memberSup) {
        this.memberSup = memberSup;
    }

    public String getMemberFunc() {
        return memberFunc;
    }

    public void setMemberFunc(String memberFunc) {
        this.memberFunc = memberFunc;
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
        this.memberJobBand = memberJobBand;
    }

    public String getMemberRemarks() {
        return memberRemarks;
    }

    public void setMemberRemarks(String memberRemarks) {
        this.memberRemarks = memberRemarks;
    }
}
