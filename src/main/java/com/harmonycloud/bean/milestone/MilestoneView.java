package com.harmonycloud.bean.milestone;


import java.util.List;

public class MilestoneView extends Milestone {
	List<MilestoneWeekPlan> weekPlans;

	public MilestoneView(){super();}

	public MilestoneView(Long milestoneIndex,
	                     Long fkProjectId,
	                     String milestonePhase,
	                     java.util.Date milestonePrestaTime,
	                     java.util.Date milestonePreendTime,
						 java.util.Date milestoneNewPrestaTime,
						 java.util.Date milestoneNewPreendTime,
	                     java.util.Date milestoneStartTime,
	                     java.util.Date milestoneEndTime,
	                     String milestoneStutas,
	                     String milestoneRemark,
	                     java.util.Date createTime,
	                     java.util.Date updateTime,
	                     List<MilestoneWeekPlan> weekPlans) {
		super(milestoneIndex,fkProjectId,milestonePhase,milestonePrestaTime,milestonePreendTime,milestoneNewPrestaTime,milestoneNewPreendTime,milestoneStartTime,
				milestoneEndTime,milestoneStutas,milestoneRemark,createTime,updateTime);
		this.weekPlans=weekPlans;
	}

	public MilestoneView(Long milestoneIndex,
	                     Long fkProjectId,
	                     String milestonePhase,
	                     java.util.Date milestonePrestaTime,
	                     java.util.Date milestonePreendTime,
						 java.util.Date milestoneNewPrestaTime,
						 java.util.Date milestoneNewPreendTime,
	                     java.util.Date milestoneStartTime,
	                     java.util.Date milestoneEndTime,
	                     String milestoneStutas,
	                     String milestoneRemark,
	                     java.util.Date createTime,
	                     java.util.Date updateTime
	                     ){
		this(milestoneIndex,fkProjectId,milestonePhase,milestonePrestaTime,milestonePreendTime,milestoneNewPrestaTime,milestoneNewPreendTime,milestoneStartTime,
				milestoneEndTime,milestoneStutas,milestoneRemark,createTime,updateTime,null);
	}

	public MilestoneView(Milestone it){
		this(it.getMilestoneIndex(),it.getFkProjectId(),it.getMilestonePhase(),it.getMilestonePrestaTime(),
				it.getMilestonePreendTime(),it.getMilestoneNewPrestaTime(),it.getMilestoneNewPreendTime(),it.getMilestoneStartTime(),it.getMilestoneEndTime(),it.getMilestoneStatus(),
				it.getMilestoneRemark(),it.getCreateTime(),it.getUpdateTime());
	}
	public List<MilestoneWeekPlan> getWeekPlans() {
		return weekPlans;
	}

	public void setWeekPlans(List<MilestoneWeekPlan> weekPlans) {
		this.weekPlans = weekPlans;
	}

}
