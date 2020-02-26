package com.harmonycloud.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.entity.Milestone;
import com.harmonycloud.entity.MilestoneWeekPlan;
import com.harmonycloud.service.MilestoneService;
import com.harmonycloud.view.MilestoneView;
import com.harmonycloud.view.ProjectDelayRankingView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;
import static java.util.Comparator.comparing;

/**
 * @author ：wz
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="里程碑controller",tags={"里程碑操作接口"})
@RequestMapping("/milestone")
public class MilestoneController {
	@Autowired
	MilestoneService milestoneService;

	@Autowired
	private HttpServletRequest request;
/*	private List<Milestone> getMilestoneFromSql(String project_id) {
		List<Milestone> list = milestoneService.selectMilestone(project_id);
		log.info(project_id + " select success!");
		return list;
	}

	private List<MilestoneWeekPlan> getMilestoneWeekPlanFromSql(String project_id) {
		List<MilestoneWeekPlan> list = milestoneService.selectMilestoneWeekPlan(project_id);
		log.info(project_id + " select success!");
		return list;
	}*/

	/*	*//**
	 * 将更改前的里程碑信息保存到日志中
	 * @param project_id 里程碑所属项目id
	 * @param operation  对里程碑执行的操作，添加到里程碑日志表中的msbl_operation字段,可为"UPDATE"或者"DELETE"
	 *//*
	private Boolean saveMilestoneAndWeekPlanToLog(String project_id, String operation) {
		List<Milestone> milestoneList = getMilestoneFromSql(project_id);
		List<MilestoneWeekPlan> weekPlanList = getMilestoneWeekPlanFromSql(project_id);
		String milestone = JSONArray.toJSONString(milestoneList);
		String milestoneWeekPlan = JSONArray.toJSONString(weekPlanList);
		milestoneService.saveMilestoneAndWeekPlanToLog(project_id, milestone, milestoneWeekPlan, operation);
		log.info("milestone Log Save success!");
		return true;
	}*/

	/**
	 * 删掉特定项目的所有里程碑
	 *
	 * @param project_id 待删除的里程碑所属项目id
	 */
/*	private Boolean deleteProjectMilestone(String project_id) {
		milestoneService.deleteMilestone(project_id);
		log.info("milestone of project "+project_id + " delete success!");
		return true;
	}*/

	/**
	 * 添加项目里程碑信息到数据库
	 * @param project_id 里程碑所属项目id
	 * @param milestones 待添加的里程碑信息
	 * @param createTime 里程碑创建时间
	 */
/*	private Boolean addProjectMilestoneAndWeekPlan(String project_id, List<MilestoneView> milestones, Date createTime, Date updateTime) {
		List<MilestoneWeekPlan> milestoneWeekPlans=new ArrayList<>();
		for (MilestoneView i : milestones
		) {
			milestoneService.addMilestone(i.getMilestoneIndex().toString(),project_id, i.getMilestonePhase(),
					i.getMilestonePrestaTime(), i.getMilestonePreendTime(),
					i.getMilestoneStartTime(), i.getMilestoneEndTime(),
					i.getMilestoneStatus(), createTime, updateTime);
			if(!i.getWeekPlans().isEmpty())milestoneWeekPlans.addAll(i.getWeekPlans());
		}
		if(!milestoneWeekPlans.isEmpty())addMilestoneWeekPlan(project_id,milestoneWeekPlans);
		return true;
	}*/

	/**
	 * 添加里程碑周计划信息到数据库
	 */
/*	private Boolean addMilestoneWeekPlan(String project_id, List<MilestoneWeekPlan> milestoneWeekPlans) {
		for (MilestoneWeekPlan i : milestoneWeekPlans
		) {
			milestoneService.addMilestoneWeekPlan(project_id, i.getFkMilestoneIndex().toString(),
					i.getTimePoint(), i.getWeekPlanDescribe(), i.getWeekPlanStatus());
		}
		return true;
	}*/

	/**
	 * 将里程碑列表与周计划列表整合入MilestoneView类中
	 */
	private List<MilestoneView> CreateMilestoneViews(List<Milestone> milestones, List<MilestoneWeekPlan> weekPlans) {
		if (milestones == null) return null;
		List<MilestoneView> viewList = new ArrayList<>();
		if (weekPlans == null || weekPlans.isEmpty()) {//如果周计划为空,直接将每一个Milestone包装到milestoneView中
			for (Milestone milestone : milestones) {
				viewList.add(new MilestoneView(milestone));
			}
		} else {//否则对于每一个里程碑,从所有周计划中找到属于该里程碑的周计划,形成列表,将其加入到milestoneView类中的weekPlans字段
			MilestoneView view = null;
			List<MilestoneWeekPlan> weekPlanList = null;
			for (Milestone milestone : milestones
			) {
				view = new MilestoneView(milestone);//将当前Milestone包装到milestoneView类中中
				weekPlanList = new ArrayList<>();
				Iterator<MilestoneWeekPlan> it = weekPlans.iterator();
				while (it.hasNext()) {
					MilestoneWeekPlan weekPlan = it.next();
					if (weekPlan.getFkMilestoneIndex().equals(milestone.getMilestoneIndex())) {
						weekPlanList.add(weekPlan);//将属于当前milestone的周计划收集到列表
						it.remove();
					}
				}
				view.setWeekPlans(weekPlanList.isEmpty() ? null : weekPlanList);
				viewList.add(view);
			}
		}
		return viewList;
	}

	/**
	 * 查找并返还当前项目的里程碑列表
	 */
	@PostMapping(value = "/list")
	@ApiOperation(value = "当前项目的里程碑列表")
	public Message list(@RequestBody @ApiParam(name = "project_id", value = "项目id", required = true) Map<String, Object> map) {
		String project_id = ((Integer) map.get("projectId")).toString();
		VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
		if (res.message.getCode() == 401) {
			log.error("Authorization参数校验失败");
			return res.message;
		}
		Map<String, Object> data = new HashMap<>();
		List<Milestone> milestones;
		if (true) {//判断当前登录者身份，目前所有身份都允许通过
			log.info("身份校验成功");
			milestones = milestoneService.selectMilestones(project_id);
		} else {
			log.error("身份校验失败");
			res.message.setMessage(401, "身份校验失败", null);
			return res.message;
		}
		if (milestones != null) {
			log.info("里程碑信息返回成功");
			data.put("list", milestones);
			data.put("total", milestones.size());
			res.message.setMessage(200, "里程碑信息返回成功", data);
		} else {
			res.message.setMessage(400, "数据为空", null);
			log.error("里程碑信息返回失败");
		}
		return res.message;
	}


	/**
	 * 编辑特定项目的里程碑信息
	 */
	@PostMapping(value = "/edit")
	@ApiOperation(value = "编辑里程碑信息")
	public Message edit(@RequestBody @ApiParam(name = "data\nprojectId\noperation", value = "里程碑信息", required = true) Map<String, Object> map) throws Exception {
		VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
		if (res.message.getCode() == 401) {
			log.error("Authorization参数校验失败");
			return res.message;
		}
		if (true) {//判断当前登录者身份，目前所有身份都允许通过
			log.info("身份校验成功");
			JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
			String dateString = JSON.toJSONStringWithDateFormat(map.get("data"), "yyyy-MM-dd HH:mm:ss");
			log.info("get ObjString success");
			List<Milestone> milestones = JSONObject.parseObject(dateString, new TypeReference<List<Milestone>>() {
			});
			log.info("finally success");
			String project_id = ((Integer) map.get("projectId")).toString();
			String operation = (String) map.get("operation");//对里程碑执行的操作，可为"ADD"、"UPDATE"或者"DELETE"
				/*if (operation.equals("ADD")) {
				isSuccess = addProjectMilestoneAndWeekPlan(project_id, milestoneViews, updateTime,updateTime);
				info = isSuccess ? "里程碑信息添加成功" : "里程碑信息添加失败";
			} else if (operation.equals("UPDATE")) {
				//获取里程碑创建时间
				Date createTime = sdf.parse((String)map.get("createTime"));
				isSuccess = (saveMilestoneAndWeekPlanToLog(project_id, operation) &&
						deleteProjectMilestone(project_id) &&
						addProjectMilestoneAndWeekPlan(project_id, milestoneViews, createTime,updateTime));
				info = isSuccess ? "里程碑信息修改成功" : "里程碑信息修改失败";

			} else if (operation.equals("DELETE")) {
				isSuccess = (saveMilestoneAndWeekPlanToLog(project_id, operation) &&
						deleteProjectMilestone(project_id));
				info = isSuccess ? "里程碑信息删除成功" : "里程碑信息删除失败";
			}*/
//			try {
			String flag = "正常进行";
			int projectId = Integer.parseInt(project_id);
			if (operation.equals("ADD")) {
				milestoneService.addMilestoneAndWeekPlan(milestones);
				log.info("里程碑信息添加成功");
				res.message.setMessage(200, "里程碑信息添加成功", null);
			} else if (operation.equals("UPDATE")) {
				milestoneService.updateMilestone(project_id, milestones);
				log.info("里程碑信息修改成功");
				res.message.setMessage(200, "里程碑信息修改成功", null);
				List<String> listStatus = milestoneService.selectStatus(project_id);
				for (short i = 0; i < listStatus.size(); i++) {
					if (listStatus.get(i).equals("延期中") && flag.equals("正常进行")) {
						flag = "延期中";
					}
					if (listStatus.get(i).equals("严重延期") && ((flag.equals("正常进行")) || flag.equals("延期中"))) {
						flag = "严重延期";
					}
				}
				if (flag != null) {
					milestoneService.updateProjectStatus(flag, projectId);
				}
			} else if (operation.equals("DELETE")) {
				milestoneService.backupAndDeleteMilestone(project_id, operation);
				log.info("里程碑信息删除成功");
				res.message.setMessage(200, "里程碑信息删除成功", null);
			}
//			} catch (Exception e) {
//				log.info("操作失败");
//				e.printStackTrace();
////				res.message.setMessage(403, "操作失败", null);
//			}
		} else {
			log.error("身份校验失败");
			res.message.setMessage(401, "身份校验失败", null);
		}
		return res.message;
	}

	/**
	 * 开启里程碑
	 *
	 * @param map
	 * @return res.message
	 * @throws Exception
	 */
	@PostMapping("/startMilestone")
	@ApiOperation(value = "开启里程碑")
	public Message startMilestone(@RequestBody @ApiParam(name = "index\nprojectId\nstartTime\nmilestoneStatus", value = "里程碑信息", required = true) Map<String, Object> map) throws Exception {
		VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
		if (res.message.getCode() == 401) {
			log.error("Authorization参数校验失败");
			return res.message;
		}
		Integer index = (Integer) map.get("index");
		Integer projectId = (Integer) map.get("projectId");
		String startTime = (String) map.get("startTime");
		String milestoneStatus = (String) map.get("milestoneStatus");
		//1.查询所开始的里程碑
		Milestone milestone = findMilestoneByIndexAndPId(index, projectId);
		List<Milestone> milestones = new ArrayList<>();
		milestones.add(milestone);
		String m = JSONArray.toJSONString(milestones);
		//2.记录日志
		int result = milestoneService.saveMilestoneAndWeekPlanToLog(projectId, m, "", "UPDATE");
		//3.开始里程碑,修改状态
		milestoneService.startMilestone(index, projectId, startTime, milestoneStatus);
		String flag = "正常进行";
		List<String> listStatus = milestoneService.selectStatus(projectId.toString());
		for (short i = 0; i < listStatus.size(); i++) {
			if (listStatus.get(i).equals("延期中") && flag.equals("正常进行")) {
				flag = "延期中";
			}
			if (listStatus.get(i).equals("严重延期") && ((flag.equals("正常进行")) || flag.equals("延期中"))) {
				flag = "严重延期";
			}
		}
		if (flag != null) {
			milestoneService.updateProjectStatus(flag, projectId);
		}

		//4.返回数据
		if (result > 0) {
			log.info("操作成功");
			res.message.setMessage(200, "里程碑开始成功");
		} else {
			log.error("操作失败");
			res.message.setMessage(400, "数据修改失败");
		}
		return res.message;
	}

	//查找指定的里程碑的方法
	Milestone findMilestoneByIndexAndPId(Integer index, Integer projectId) {
		Milestone milestone = milestoneService.selectMilestone(index, projectId);
		return milestone;
	}

	@PostMapping("/endMilestone")
	@ApiOperation(value = "结束里程碑")
	public Message endMilestone(@RequestBody @ApiParam(name = "index\nprojectId\nendTime\nmilestoneStatus\nmilestoneRemark", value = "里程碑信息", required = true) Map<String, Object> map) throws Exception {
		VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
		if (res.message.getCode() == 401) {
			log.error("Authorization参数校验失败");
			return res.message;
		}
		Integer index = (Integer) map.get("index");
		Integer projectId = (Integer) map.get("projectId");
		String endTime = (String) map.get("endTime");
		String milestoneStatus = (String) map.get("milestoneStatus");
		String milestoneRemark = (String) map.get("milestoneRemark");
		//1.查询所结束的里程碑
		Milestone milestone = findMilestoneByIndexAndPId(index, projectId);
		List<Milestone> milestones = new ArrayList<>();
		milestones.add(milestone);
		String m = JSONArray.toJSONString(milestones);
		//2.记录日志
		int result = milestoneService.saveMilestoneAndWeekPlanToLog(projectId, m, "", "UPDATE");
		//3.结束里程碑,修改状态,添加备注
		milestoneService.endMilestone(index, projectId, endTime, milestoneStatus, milestoneRemark);
		String flag = "正常进行";
		List<String> listStatus = milestoneService.selectStatus(projectId.toString());
		for (short i = 0; i < listStatus.size(); i++) {
			if (listStatus.get(i).equals("延期中") && flag.equals("正常进行")) {
				flag = "延期中";
			}
			if (listStatus.get(i).equals("严重延期") && ((flag.equals("正常进行")) || flag.equals("延期中"))) {
				flag = "严重延期";
			}
		}
		if (flag != null) {
			milestoneService.updateProjectStatus(flag, projectId);
		}
		//4.返回响应
		if (result > 0) {
			log.info("操作成功");
			res.message.setMessage(200, "结束里程碑成功");
		} else {
			log.error("操作失败");
			res.message.setMessage(400, "数据修改失败");
		}
		return res.message;

	}

	/**
	 * 返回项目延期排行榜
	 *
	 * @return message
	 * @throws Exception
	 */
	@GetMapping("/projectDelayRanking")
	@ApiOperation(value = "返回项目延期排行榜")
	public Message projectDelayRanking() throws Exception {
		VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
		if (res.message.getCode() == 401) {
			log.error("Authorization参数校验失败");
			return res.message;
		}
		Map<String, Object> data = new HashMap<>();
		List<ProjectDelayRankingView> list = milestoneService.projectDelayRanking();
		list = list.stream().sorted(comparing(ProjectDelayRankingView::getDelayTime).reversed()).collect(Collectors.toList());
		if (list != null) {
			log.info("项目延期排行榜返回成功");
			data.put("list", list);
			data.put("total", list.size());
			res.message.setMessage(200, "项目延期排行榜返回成功", data);
		} else {
			log.error("返回错误");
			res.message.setMessage(400, "返回错误");
		}
		return res.message;
	}
}
