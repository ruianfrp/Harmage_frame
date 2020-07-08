package com.harmonycloud.imp;


import com.alibaba.fastjson.JSONArray;
import com.harmonycloud.dao.MilestoneDao;
import com.harmonycloud.entity.Milestone;
import com.harmonycloud.entity.MilestoneWeekPlan;
import com.harmonycloud.service.MilestoneService;
import com.harmonycloud.view.MilestoneStatusView;
import com.harmonycloud.view.MilestoneView;
import com.harmonycloud.view.ProjectDelayRankingView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
@Slf4j
public class MilestoneServiceImp implements MilestoneService {


    @Autowired
    MilestoneDao milestoneDao;

    @Override
    public List<Milestone> selectMilestones(String project_id) {
        return milestoneDao.selectMilestones(project_id);
    }

    @Override
    public List<MilestoneWeekPlan> selectMilestoneWeekPlan(String project_id) {
        return milestoneDao.selectMilestoneWeekPlan(project_id);
    }

    @Override
    public List<String> selectStatus(String project_id) {
        return milestoneDao.selectStatus(project_id);
    }

    @Override
    public void updateProjectStatus(String flag, Integer project_id) {
        milestoneDao.updateProjectStatus(flag, project_id);
    }

    /**
     * 更新特定项目的里程碑信息
     *
     * @param project_id 项目id
     * @param milestones 待加入数据库的里程碑信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateMilestone(String project_id, List<Milestone> milestones) {
        backupAndDeleteMilestone(project_id, "UPDATE");
        addMilestoneAndWeekPlan(milestones);
        log.info("Milestone " + project_id + " update success!");
    }

    /**
     * 备份并删除特定项目的所有里程碑
     *
     * @param project_id 项目id
     * @param operation  删除里程碑的目的,若仅是为了删除里程碑则为"DELETE",若是为了更新里程碑则为"UPDATE"
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void backupAndDeleteMilestone(String project_id, String operation) {
        List<Milestone> milestoneList = milestoneDao.selectMilestones(project_id);
        log.info("Milestone select success!");
//        List<MilestoneWeekPlan> weekPlanList = milestoneDao.selectMilestoneWeekPlan(project_id);
//        log.info("Week plan list select success!");
        String milestone = JSONArray.toJSONString(milestoneList);
//        String milestoneWeekPlan = JSONArray.toJSONString(weekPlanList);
        milestoneDao.saveMilestoneAndWeekPlanToLog(Integer.parseInt(project_id), milestone,operation);
        log.info("log saved success!");
        milestoneDao.deleteMilestone(project_id);
        log.info("Milestone " + project_id + " delete success!");
    }

    /**
     * 添加项目里程碑及其日志信息到数据库
     *
     * @param milestones 待添加的里程碑信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addMilestoneAndWeekPlan(List<Milestone> milestones) {
//        List<MilestoneWeekPlan> milestoneWeekPlans = new ArrayList<>();
        milestoneDao.milestoneBatchInsert(milestones);
        log.info("里程碑信息批量导入成功!");
    }


/*    private void saveMilestoneAndWeekPlanToLog(String project_id,String operation) {
        List<Milestone> milestoneList = selectMilestone(project_id);
        log.info("Milestone select success!");
        List<MilestoneWeekPlan> weekPlanList = selectMilestoneWeekPlan(project_id);
        log.info("Week plan list select success!");
        String milestone = JSONArray.toJSONString(milestoneList);
        String milestoneWeekPlan = JSONArray.toJSONString(weekPlanList);
        milestoneDao.saveMilestoneAndWeekPlanToLog(project_id,milestone,milestoneWeekPlan,operation);
        log.info("log saved success!");
    }*/

    /**
     * 单条里程碑信息导入
     */
    private void addMilestone(String milestone_index, String fk_project_id, String milestone_phase,
                              Date milestone_presta_time, Date milestone_preend_time,Date milestone_new_presta_time, Date milestone_new_preend_time,
                              Date milestone_start_time, Date milestone_end_time, String milestone_status,
                              Date create_time, Date update_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String milestonePrestaTime = milestone_presta_time == null ? null : sdf.format(milestone_presta_time);
        String milestonePreendTime = milestone_preend_time == null ? null : sdf.format(milestone_preend_time);
        String milestoneNewPrestaTime = milestone_new_presta_time == null ? null : sdf.format(milestone_new_presta_time);
        String milestoneNewPreendTime = milestone_new_preend_time == null ? null : sdf.format(milestone_new_preend_time);
        String milestoneStartTime = milestone_start_time == null ? null : sdf.format(milestone_start_time);
        String milestoneEndTime = milestone_end_time == null ? null : sdf.format(milestone_end_time);
        String createTime = create_time == null ? null : sdf.format(create_time);
        String updateTime = update_time == null ? null : sdf.format(update_time);
        milestoneDao.addMilestone(milestone_index, fk_project_id, milestone_phase,
                milestonePrestaTime, milestonePreendTime,milestoneNewPrestaTime,milestoneNewPreendTime, milestoneStartTime,
                milestoneEndTime, milestone_status, createTime, updateTime);
    }

    /**
     * 单条周计划信息导入
     */
    private void addMilestoneWeekPlan(String fk_project_id, String fk_milestone_index, Date time_point, String week_plan_describe, String week_plan_status) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timePoint = time_point == null ? null : sdf.format(time_point);
        milestoneDao.addMilestoneWeekPlan(fk_project_id, fk_milestone_index, timePoint, week_plan_describe, week_plan_status);
    }


    //开始里程碑
    @Override
    public void startMilestone(Integer index, Integer fkProjectId, String startTime,String milestoneStatus) {
        milestoneDao.startMilestone(index,fkProjectId,startTime,milestoneStatus);
    }

    //记录日志
    @Override
    public int saveMilestoneAndWeekPlanToLog(Integer fkProjectId, String milestone, String milestoneWeekPlan, String operation) {
        return milestoneDao.saveMilestoneAndWeekPlanToLog(fkProjectId,milestone,operation);
    }

    @Override
    public Milestone selectMilestone(Integer index, Integer fkProjectId) {
        return milestoneDao.selectMilestone(index,fkProjectId);
    }

    @Override
    public void endMilestone(Integer index, Integer fkProjectId, String endTime, String milestoneStatus, String milestoneRemark) {
        milestoneDao.endMilestone(index,fkProjectId,endTime,milestoneStatus,milestoneRemark);
    }

    @Override
    public List<MilestoneStatusView> selectMilestoneInfo(){
        return milestoneDao.selectMilestoneInfo();
    }

    @Override
    public Integer updateScheduleStatus(String flag,Integer milestoneIndex,Integer fkProjectId){
        return milestoneDao.updateScheduleStatus(flag, milestoneIndex, fkProjectId);
    }

    @Override
    public List<ProjectDelayRankingView> projectDelayRanking() {
        return milestoneDao.projectDelayRanking();
    }

}