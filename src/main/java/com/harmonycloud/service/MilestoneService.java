package com.harmonycloud.service;

import com.harmonycloud.entity.Milestone;
import com.harmonycloud.entity.MilestoneWeekPlan;
import com.harmonycloud.view.MilestoneStatusView;
import com.harmonycloud.view.MilestoneView;
import com.harmonycloud.view.ProjectDelayRankingView;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
public interface MilestoneService {
    List<Milestone> selectMilestones(String project_id);

    List<MilestoneWeekPlan> selectMilestoneWeekPlan(String project_id);

    void updateMilestone(String project_id, List<Milestone> milestones);

    void backupAndDeleteMilestone(String project_id, String operation);

    void addMilestoneAndWeekPlan(List<Milestone> milestones);

    List<String> selectStatus(String project_id);

    void updateProjectStatus(String flag, Integer project_id);

    void startMilestone(Integer index,Integer fkProjectId,String startTime,String milestoneStatus);

    int saveMilestoneAndWeekPlanToLog(Integer fkProjectId, String milestone, String milestoneWeekPlan, String operation);

    Milestone selectMilestone(Integer index, Integer fkProjectId);

    void endMilestone(Integer index, Integer fkProjectId, String endTime,String milestoneStatus,String milestoneRemark);

    List<MilestoneStatusView> selectMilestoneInfo();

    Integer updateScheduleStatus(String flag,Integer milestoneIndex,Integer fkProjectId);

    List<ProjectDelayRankingView> projectDelayRanking();
}