package com.harmonycloud.dao;

import com.harmonycloud.entity.Milestone;
import com.harmonycloud.entity.MilestoneWeekPlan;
import com.harmonycloud.view.MilestoneStatusView;
import com.harmonycloud.view.ProjectDelayRankingView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilestoneDao {
    List<Milestone> selectMilestones(String project_id);

    List<MilestoneWeekPlan> selectMilestoneWeekPlan(String project_id);

    int deleteMilestone(String project_id);

    int addMilestone(String milestone_index, String fk_project_id, String milestone_phase,
                     String milestone_presta_time, String milestone_preend_time, String milestone_new_presta_time, String milestone_new_preend_time, String milestone_start_time,
                     String milestone_end_time, String milestone_status, String create_time, String update_time);

    int addMilestoneWeekPlan(String fk_project_id, String fk_milestone_index, String time_point, String week_plan_describe, String week_plan_status);

    void milestoneBatchInsert(List<Milestone> list);

    void weekPlanBatchInsert(List<MilestoneWeekPlan> list);

    List<String> selectStatus(String project_id);

    void updateProjectStatus(String flag, Integer project_id);

    void startMilestone(Integer index, Integer fkProjectId, String startTime,String milestoneStatus);

    int saveMilestoneAndWeekPlanToLog(Integer fkProjectId, String milestone, String operation);

    Milestone selectMilestone(Integer index, Integer projectId);

    void endMilestone(Integer index, Integer fkProjectId, String endTime, String milestoneStatus, String milestoneRemark);

    List<MilestoneStatusView> selectMilestoneInfo();

    Integer updateScheduleStatus(String flag, Integer milestoneIndex, Integer fkProjectId);

    List<ProjectDelayRankingView> projectDelayRanking();
}