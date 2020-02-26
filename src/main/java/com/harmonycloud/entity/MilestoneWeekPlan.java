package com.harmonycloud.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MilestoneWeekPlan {
	private Long id;
	private Long fkMilestoneIndex;
	private Long fkProjectId;

	@JSONField(format="yyyy-MM-dd")
	private Date timePoint;

	private String weekPlanDescribe;
	private String weekPlanStatus;

	public MilestoneWeekPlan(
			Long id,
			Long fkMilestoneIndex,
			Long fkProjectId,
			Date timePoint,
			String describe,
			String status) {
		this.id = id;
		this.fkMilestoneIndex = fkMilestoneIndex;
		this.fkProjectId=fkProjectId;
		this.weekPlanDescribe = describe;
		this.weekPlanStatus = status;
		this.timePoint=timePoint;
	}

	public MilestoneWeekPlan() {
		super();
	}

	public Long getFkMilestoneIndex() {
		return fkMilestoneIndex;
	}
	public void setFkMilestoneIndex(Long fkMilestoneIndex) {
		this.fkMilestoneIndex = fkMilestoneIndex;
	}

	public Long getFkProjectId() {
		return fkProjectId;
	}
	public void setFkProjectId(Long fkProjectId) {
		this.fkProjectId = fkProjectId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getWeekPlanDescribe() {
		return weekPlanDescribe;
	}
	public void setWeekPlanDescribe(String weekPlanDescribe) {
		this.weekPlanDescribe = weekPlanDescribe == null ? null : weekPlanDescribe.trim();
	}

	public String getWeekPlanStatus() {
		return weekPlanStatus;
	}
	public void setWeekPlanStatus(String weekPlanStatus) {
		this.weekPlanStatus = weekPlanStatus == null ? null : weekPlanStatus.trim();
	}

	public Date getTimePoint(){return timePoint;}
	public void setTimePoint(Date timePoint){this.timePoint = timePoint;}

}
