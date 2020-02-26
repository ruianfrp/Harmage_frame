package com.harmonycloud.util.ScheduleTask;

import com.harmonycloud.entity.MemberApply;
import com.harmonycloud.service.EmployeeService;
import com.harmonycloud.service.MemberApplyService;
import com.harmonycloud.service.MemberLeaveService;
import com.harmonycloud.service.MemberService;
import com.harmonycloud.view.MemberLeaveListView;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
@EnableScheduling   //开启定时任务
@EnableAsync        //开启多线程
@Slf4j
public class MemberScheduleTask {

    @Autowired
    MemberLeaveService memberLeaveService;

    @Autowired
    MemberApplyService memberApplyService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    MemberService memberService;

    /**
     * 员工调离
     *
     * @throws Exception
     */
    @Async
    @Scheduled(cron = "0 0 1 * * ?")//每天1点执行
    public void memberLeave() {
        List<MemberLeaveListView> list = memberLeaveService.selectIfLeave();
        if (list.size() > 0) {
            for (short i = 0; i < list.size(); i++) {
                String memberRemarks = list.get(i).getMemberLeaveRemark();
                java.sql.Date realEndTime = list.get(i).getMemberLeaveTime();
                String employeeGh = list.get(i).getMemberLeaveGh();
                Integer id = list.get(i).getId();
                Integer projectId = list.get(i).getProjectId();
                Integer result = memberLeaveService.leaveMember(memberRemarks, realEndTime, employeeGh, projectId);
                if (result > 0) {
                    log.info(employeeGh + " 员工调离成功");
                    Integer result1 = memberLeaveService.updateMemberIsLeave(id);
                    if (result1 > 0) {
                        log.info(id + " 调离申请已执行");
                    } else {
                        log.error(id + " 调离申请未执行");
                        break;
                    }
                } else {
                    log.error(employeeGh + " 员工调离失败");
                    break;
                }
            }
            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 成员调离完成");
        } else {
            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 没有需要调离的成员");
        }
    }

    /**
     * 员工进组
     *
     * @throws Exception
     */
    @Async
    @Scheduled(cron = "0 0 1 * * ?")//每天1点执行
    public void memberApply() {
        Integer result = memberApplyService.easyMemberApply();
        Integer result1 = memberApplyService.memberQuit();
        if (result == 0 && result1 == 0) {
            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 没有需要进组的成员");
        } else if (result > 0 && result1 == 0) {
            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 成员进组成功（无完全转组成员）");
        } else if (result > 0 && result1 > 0) {
            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 成员进组成功（包含完全转组成员）");
        } else {
            log.warn("完全转组员工进组异常");
        }
    }

    @Async
    @Scheduled(cron = "0 20 0 * * ?")
    public void memberApplyAddWithoutProj(){
        List<MemberApply> list = memberApplyService.selectDistributionGh();
        if(list!=null){
            for(MemberApply memberApply:list){
                String fkEmployeeGh = memberApply.getDistributionGh();
                List<Integer> memberId = memberApplyService.selectIfHaveProj(fkEmployeeGh);
                if(memberId.size()==0){
                    Integer id = Math.toIntExact(memberApply.getId());
                    String memberJoinType = memberApply.getMemberJoinType();
                    String employeeGh = memberApply.getDistributionGh();
                    Integer projectId = Math.toIntExact(memberApply.getFkProjectId());
                    String memberStartTime = memberApply.getMemberStartTime();
                    String memberEndTime = memberApply.getMemberEndTime();
                    String memberJoinSup = memberApply.getMemberJoinSup();
                    Integer r = memberApplyService.joinMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
                    if (r > 0) {
                        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 组员添加成功");
                        Integer result = memberApplyService.doneMemberApply(id);
                        if (result > 0) {
                            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 组员申请已处理");
                        } else {
                            log.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 组员申请处理失败");
                        }
                    } else {
                        log.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 组员添加失败");
                    }
                }
            }
        }else {
            log.warn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 没有需要进组的无项目组成员");
        }
    }

    @Async
    @Scheduled(cron = "0 30 0 * * ?")
    public void memberApplyAdd(){
        List<MemberApply> list = memberApplyService.selectAllAgreeMemberApply();
        if (list.size() > 0) {
            for (short i = 0; i < list.size(); i++) {
                Integer id = Math.toIntExact(list.get(i).getId());
                String memberJoinType = list.get(i).getMemberJoinType();
                String employeeGh = list.get(i).getDistributionGh();
                Integer projectId = Math.toIntExact(list.get(i).getFkProjectId());
                String memberStartTime = list.get(i).getMemberStartTime();
                String memberEndTime = list.get(i).getMemberEndTime();
                String memberJoinSup = list.get(i).getMemberJoinSup();
                if (memberJoinType.equals("完全转组")) {
                    memberApplyService.updateCompleteMember(memberStartTime, employeeGh);
                    Integer r = memberApplyService.joinMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
                    if (r > 0) {
                        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 完全转组组员添加成功");
                        Integer result = memberApplyService.doneMemberApply(id);
                        if (result > 0) {
                            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 完全转组组员申请已处理");
                        } else {
                            log.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 完全转组组员申请处理失败");
                        }
                    } else {
                        log.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 完全转组组员添加失败");
                    }
                } else {
                    Integer r1 = memberApplyService.joinMemberByApply(employeeGh, projectId, memberStartTime, memberEndTime, memberJoinSup, memberJoinType);
                    if (r1 > 0) {
                        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + memberJoinType + "组员添加成功");
                        Integer result = memberApplyService.doneMemberApply(id);
                        if (result > 0) {
                            log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + memberJoinType + "组员申请已处理");
                        } else {
                            log.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + memberJoinType + "组员申请处理失败");
                        }
                    } else {
                        log.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + memberJoinType + "组员添加失败");
                    }
                }
            }
        } else {
            log.warn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 暂无需要添加的组员");
        }
    }

    @Async
    @Scheduled(cron = "0 30 1 * * ?")
    public void memberQuit(){
        List<String> list = employeeService.selectEmployeeQuit();
        if(list!=null){
            for(String employeeGh : list){
                Integer result = memberService.updateMemberQuit(employeeGh);
                if(result>0){
                    log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 离职员工离组成功");
                } else {
                    log.warn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 离职员工已经离组");
                }
            }
        }
    }
}
