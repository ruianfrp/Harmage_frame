package com.harmonycloud.service;

import com.harmonycloud.bean.milestone.Milestone;
import com.harmonycloud.bean.milestone.MilestoneStatusView;
import com.harmonycloud.bean.milestone.MilestoneWeekPlan;
import com.harmonycloud.bean.project.ProjectDelayRankingView;

import java.util.List;
import java.util.Set;

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

    Set<String> selectStatus(String project_id);

    void updateProjectStatus(String projectStatus, String projectSubState, Integer project_id);

    void startMilestone(Integer index,Integer fkProjectId,String startTime,String milestoneStatus);

    int saveMilestoneAndWeekPlanToLog(Integer fkProjectId, String milestone, String milestoneWeekPlan, String operation);

    Milestone selectMilestone(Integer index, Integer fkProjectId);

    void endMilestone(Integer index, Integer fkProjectId, String endTime,String milestoneStatus,String milestoneRemark);

    List<MilestoneStatusView> selectMilestoneInfo();

    Integer updateScheduleStatus(String flag,Integer milestoneIndex,Integer fkProjectId);

    List<ProjectDelayRankingView> projectDelayRanking();
}