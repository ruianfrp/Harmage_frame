package com.harmonycloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.enumeration.ProjectStatus;
import com.harmonycloud.bean.customer.Customer;
import com.harmonycloud.bean.project.Project;
import com.harmonycloud.bean.project.ProjectListView;
import com.harmonycloud.bean.report.ProjectReport;
import com.harmonycloud.service.CustomerService;
import com.harmonycloud.service.ProjectReportService;
import com.harmonycloud.service.ProjectService;
import com.harmonycloud.service.ProjectStatusCacheService;
import com.harmonycloud.util.date.DateUtils;
import com.harmonycloud.util.ding.DingUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;
import static com.harmonycloud.util.date.DateUtils.simpleDateFormat;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value = "项目controller", tags = {"项目操作接口"})
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    CustomerService customerService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProjectStatusCacheService projectStatusCacheService;

    @Autowired
    private ProjectReportService projectReportService;

    /**
     * 计算项目周期
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public int getPeriod(String startTime, String endTime) {
        int startYear = Integer.parseInt(startTime.split("-")[0]);
        int startMonth = Integer.parseInt(startTime.split("-")[1]);
        int startDay = Integer.parseInt(startTime.split("-")[2]);
        int endYear = Integer.parseInt(endTime.split("-")[0]);
        int endMonth = Integer.parseInt(endTime.split("-")[1]);
        int endDay = Integer.parseInt(endTime.split("-")[2]);
        int period = 0;
        period = (endYear - startYear) * 12 + endMonth - startMonth + ((getDaysByYearMonth(startYear, startMonth) - endDay) > (getDaysByYearMonth(startYear, startMonth) - startDay) ? 0 : 1);
        return period;
    }

    /**
     * 返回某个月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取概览
     *
     * @return res.message
     * @throws Exception
     */
    @GetMapping("/overview")
    @ApiOperation(value = "获取概览")
    public Message overview() throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String authority = res.user.getRole();
        Map<String, Object> data = new HashMap<>();
        List<ProjectListView> list;
        if (!authority.contains("Overview_RO")) {
            list = null;
        } else {
            list = projectService.listOverViewProject();
            if (list != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (ProjectListView projectListView : list) {
                    String projPrestaTime = projectListView.getProjPrestaTime().toString();
                    String projPreendTime = projectListView.getProjPreendTime().toString();
                    String nowTime = sdf.format(new Date());
                    double projMoney = projectListView.getProjMoney().doubleValue() / 10000.0;
                    int projPeriod = getPeriod(projPrestaTime, projPreendTime);
                    int nowPeriod = getPeriod(projPrestaTime, nowTime);
                    double currentCost = projectListView.getProjCurrentCost().doubleValue() / 10000.0;
                    double projAvgMoney = projMoney * 0.5 / projPeriod;
                    double projWarningLine = projMoney * 0.5;
                    double projRealWarningLine = projMoney * 0.5;
                    int lastPeriod = projPeriod - nowPeriod;
                    if (lastPeriod < 0) {
                        lastPeriod = -1;
                    }
                    if (projPeriod > nowPeriod) {
                        projRealWarningLine = projAvgMoney * nowPeriod;
                    }
                    projectListView.setProjAllCost(projectListView.getProjAllCost().divide(new BigDecimal(10000.0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    projectListView.setProjMoney(new BigDecimal(projMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
                    projectListView.setProjCurrentCost(new BigDecimal(currentCost).setScale(2, BigDecimal.ROUND_HALF_UP));
                    projectListView.setProjRealWarningLine(new BigDecimal(projRealWarningLine).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    projectListView.setProjWarningLine(new BigDecimal(projWarningLine).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    projectListView.setProjLastPeriod(lastPeriod);
                }
            }
        }
        if (list != null) {
            List<String> projNameList = new ArrayList<>();
            List<Double> projWarningLineList = new ArrayList<>();
            List<Double> projRealWarningLineList = new ArrayList<>();
            List<BigDecimal> projMoneyList = new ArrayList<>();
            List<BigDecimal> projAllCostList = new ArrayList<>();
            List<BigDecimal> projCurrentCostList = new ArrayList<>();
            List<Integer> projLastPeriodList = new ArrayList<>();
            List<ProjectListView> sortedList = list.stream().sorted(Comparator.comparing(view -> view.getProjRealWarningLine() - view.getProjAllCost().doubleValue())).collect(Collectors.toList());
            for (ProjectListView projectListView : sortedList) {
                projCurrentCostList.add(projectListView.getProjCurrentCost());
                projNameList.add(projectListView.getProjName());
                projWarningLineList.add(projectListView.getProjWarningLine());
                projRealWarningLineList.add(projectListView.getProjRealWarningLine());
                projMoneyList.add(projectListView.getProjMoney());
                projAllCostList.add(projectListView.getProjAllCost());
                projLastPeriodList.add(projectListView.getProjLastPeriod());
            }
            log.info("Project信息返回成功");
            data.put("project", projNameList);
            data.put("warnLine", projWarningLineList);
            data.put("realWarnLine", projRealWarningLineList);
            data.put("allCost", projAllCostList);
            data.put("currentCost", projCurrentCostList);
            data.put("projMoney", projMoneyList);
            data.put("lastPeriod", projLastPeriodList);
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，Project数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 获取概览（按总成本排序）
     *
     * @return res.message
     * @throws Exception
     */
    @GetMapping("/overview2")
    @ApiOperation(value = "获取概览")
    public Message overview2() throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String authority = res.user.getRole();
        Map<String, Object> data = new HashMap<>();
        List<ProjectListView> list;
        if (!authority.contains("Overview_RO")) {
            list = null;
        } else {
            list = projectService.listOverViewProject();
            if (list != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (ProjectListView projectListView : list) {
                    String projPrestaTime = projectListView.getProjPrestaTime().toString();
                    String projPreendTime = projectListView.getProjPreendTime().toString();
                    String nowTime = sdf.format(new Date());
                    double projMoney = projectListView.getProjMoney().doubleValue() / 10000.0;
                    int projPeriod = getPeriod(projPrestaTime, projPreendTime);
                    int nowPeriod = getPeriod(projPrestaTime, nowTime);
                    double currentCost = projectListView.getProjCurrentCost().doubleValue() / 10000.0;
                    double projAvgMoney = projMoney * 0.5 / projPeriod;
                    double projWarningLine = projMoney * 0.5;
                    double projRealWarningLine = projMoney * 0.5;
                    int lastPeriod = projPeriod - nowPeriod;
                    if (lastPeriod < 0) {
                        lastPeriod = -1;
                    }
                    if (projPeriod > nowPeriod) {
                        projRealWarningLine = projAvgMoney * nowPeriod;
                    }
                    projectListView.setProjAllCost(projectListView.getProjAllCost().divide(new BigDecimal(10000.0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    projectListView.setProjMoney(new BigDecimal(projMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
                    projectListView.setProjCurrentCost(new BigDecimal(currentCost).setScale(2, BigDecimal.ROUND_HALF_UP));
                    projectListView.setProjRealWarningLine(new BigDecimal(projRealWarningLine).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    projectListView.setProjWarningLine(new BigDecimal(projWarningLine).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    projectListView.setProjLastPeriod(lastPeriod);
                }
            }
        }
        if (list != null) {
            List<String> projNameList = new ArrayList<>();
            List<Double> projWarningLineList = new ArrayList<>();
            List<Double> projRealWarningLineList = new ArrayList<>();
            List<BigDecimal> projMoneyList = new ArrayList<>();
            List<BigDecimal> projAllCostList = new ArrayList<>();
            List<BigDecimal> projCurrentCostList = new ArrayList<>();
            List<Integer> projLastPeriodList = new ArrayList<>();
            List<ProjectListView> sortedList = list.stream().sorted(Comparator.comparing(view -> view.getProjAllCost().doubleValue())).collect(Collectors.toList());
            for (ProjectListView projectListView : sortedList) {
                projCurrentCostList.add(projectListView.getProjCurrentCost());
                projNameList.add(projectListView.getProjName());
                projWarningLineList.add(projectListView.getProjWarningLine());
                projRealWarningLineList.add(projectListView.getProjRealWarningLine());
                projMoneyList.add(projectListView.getProjMoney());
                projAllCostList.add(projectListView.getProjAllCost());
                projLastPeriodList.add(projectListView.getProjLastPeriod());
            }
            log.info("Project信息返回成功");
            data.put("project", projNameList);
            data.put("warnLine", projWarningLineList);
            data.put("realWarnLine", projRealWarningLineList);
            data.put("allCost", projAllCostList);
            data.put("currentCost", projCurrentCostList);
            data.put("projMoney", projMoneyList);
            data.put("lastPeriod", projLastPeriodList);
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，Project数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 获取项目列表
     *
     * @return message
     */
    @GetMapping("/listProject")
    @ApiOperation(value = "获取项目列表")
    public Message listProject() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String authority = res.user.getRole();
        String employeeGh = res.user.getId();
        Map<String, Object> data = new HashMap<>();
        List<ProjectListView> list;
        if (authority.contains("Project_RW_S")) {
            list = projectService.listPmProject(employeeGh);
            if (list != null) {
                for (ProjectListView projectListView : list) {
                    String projPrestaTime = projectListView.getProjPrestaTime().toString();
                    String projPreendTime = projectListView.getProjPreendTime().toString();
                    int projPeriod = getPeriod(projPrestaTime, projPreendTime);
                    projectListView.setProjPeriod(projPeriod);
                }
            }
        } else {
            list = projectService.listProject();
            if (list != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (ProjectListView projectListView : list) {
                    String projPrestaTime = projectListView.getProjPrestaTime().toString();
                    String projPreendTime = projectListView.getProjPreendTime().toString();
                    String nowTime = sdf.format(new Date());
                    double projMoney = projectListView.getProjMoney().doubleValue();
                    int projPeriod = getPeriod(projPrestaTime, projPreendTime);
                    projectListView.setProjPeriod(projPeriod);
                    int nowPeriod = getPeriod(projPrestaTime, nowTime);
                    double projAvgMoney = projMoney * 1.0 / projPeriod;
                    double projWarningLine = projMoney;
                    if (projPeriod > nowPeriod) {
                        projWarningLine = projAvgMoney * nowPeriod;
                    }
                    projectListView.setProjWarningLine(projWarningLine);
                }
            }
        }
        if (list != null) {
            log.info("Project信息返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，Project数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 修改项目信息
     *
     * @param projectListView
     * @return message
     */
    @PostMapping("/updateProject")
    @ApiOperation(value = "修改项目信息")
    public Message updateProject(@RequestBody ProjectListView projectListView) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }

        Project project = new Project();

        project.setId(projectListView.getId());
        project.setProjType(projectListView.getProjType());
        project.setProjPrestaTime(projectListView.getProjPrestaTime());
        project.setProjPreendTime(projectListView.getProjPreendTime());
        project.setProjStartTime(projectListView.getProjStartTime());
        project.setProjEndTime(projectListView.getProjEndTime());
        project.setProjPlace(projectListView.getProjPlace());
        project.setProjMoney(projectListView.getProjMoney());
        project.setProjAllCost(projectListView.getProjAllCost());
        project.setProjCurrentCost(projectListView.getProjCurrentCost());
        project.setProjCooperationModel(projectListView.getProjCooperationModel());
        project.setProjDevModel(projectListView.getProjDevModel());
        project.setProjDescription(projectListView.getProjDescription());

        if (projectListView.getCustomerPlace() != null) {
            Integer customerId = projectService.findCustomerIdByProjId(projectListView.getId());
            Customer customer = new Customer();
            customer.setId(customerId);
            customer.setCustomerProvince(projectListView.getCustomerProvince());
            customer.setCustomerCity(projectListView.getCustomerCity());
            customer.setCustomerPlace(projectListView.getCustomerPlace());
            customerService.updateByPrimaryKeySelective(customer);
        }
        int result = projectService.updateByPrimaryKeySelective(project);
        if (result > 0) {
            log.info("项目信息修改成功");
            res.message.setMessage(200, "数据返回成功");
        } else {
            log.error("返回错误，项目信息修改失败");
            res.message.setMessage(400, "数据修改失败");
        }
        return res.message;
    }

    /**
     * 根据项目id查询项目
     *
     * @param map
     * @return res.message
     */
    @PostMapping("/selectProjectById")
    @ApiOperation(value = "根据项目id查询项目")
    public Message selectProjectById(@RequestBody @ApiParam(name = "id", value = "项目id") Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer id = (Integer) map.get("id");
        Map<String, ProjectListView> data = new HashMap<>();
        ProjectListView projectListView = projectService.selectProjectById(id);
        if (projectListView != null) {
            String projPrestaTime = projectListView.getProjPrestaTime().toString();
            String projPreendTime = projectListView.getProjPreendTime().toString();
            int projPeriod = getPeriod(projPrestaTime, projPreendTime);
            projectListView.setProjPeriod(projPeriod);
            List<String> projectStatusList = new ArrayList<>();
            projectStatusList = projectStatusCacheService.getActionByProjectId(id, 0);
            System.out.println(projectStatusList);
            projectListView.setStopFlag(getListStopFlag(projectStatusList));
            projectListView.setRestartFlag(getListRestartFlag(projectStatusList));
            log.info("Project信息返回成功");
            data.put("list", projectListView);
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，Project数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

//    /**
//     * PM提交项目暂停
//     */
//    @PostMapping("/stopProject")
//    @ApiOperation(value = "PM提交项目停止信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "projectId", value = "项目ID", required = true),
//            @ApiImplicitParam(paramType="query", name = "projectName", value = "项目名字", required = true),
//            @ApiImplicitParam(paramType="query", name = "stopTimeFrom", value = "项目停止时间", required = true),
//            @ApiImplicitParam(paramType="query", name = "stopTimeTo", value = "项目停止预期中止时间", required = true),
//            @ApiImplicitParam(paramType="query", name = "note", value = "项目停止备注", required = true),
//            @ApiImplicitParam(paramType="query", name = "userId", value = "操作人ID", required = true),
//            @ApiImplicitParam(paramType="query", name = "userName", value = "操作人名字", required = true)
//    })
//    public Message stopProject(Integer projectId, String projectName, String stopTimeFrom, String stopTimeTo, String note, Integer userId, String userName) {
////        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
////        if (res.message.getCode() == 401) {
////            log.error("Authorization参数校验失败");
////            return res.message;
////        }
//        Message message = new Message();
//        int dataCount = projectStatusCacheService.insertProjectStatusCache(projectId, projectName,ProjectStatus.STOP.getStatus(), stopTimeFrom, stopTimeTo, note, userId, userName);
//        if (dataCount == 1) {
//            message.setMessage(200, "PM提交项目停止信息数据成功插入!");
//        }
//        return message;
//    }
//
//    /**
//     * PM提交项目重启
//     */
//    @PostMapping("/restartProject")
//    @ApiOperation(value = "PM提交项目重启")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "projectId", value = "项目ID", required = true),
//            @ApiImplicitParam(paramType="query", name = "projectName", value = "项目名字", required = true),
//            @ApiImplicitParam(paramType="query", name = "stopTimeFrom", value = "项目重启时间", required = true),
//            @ApiImplicitParam(paramType="query", name = "stopTimeTo", value = "项目预期截止时间", required = true),
//            @ApiImplicitParam(paramType="query", name = "note", value = "项目重启备注", required = true),
//            @ApiImplicitParam(paramType="query", name = "userId", value = "操作人ID", required = true),
//            @ApiImplicitParam(paramType="query", name = "userName", value = "操作人名字", required = true)
//    })
//    public Message restartProject(Integer projectId, String projectName, String stopTimeFrom, String stopTimeTo, String note, Integer userId, String userName) {
//        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
//        int dataCount = projectStatusCacheService.insertProjectStatusCache(projectId, projectName,ProjectStatus.RESTART.getStatus(), stopTimeFrom, stopTimeTo, note, userId, userName);
//        if (dataCount == 1) {
//            res.message.setMessage(200, "PM提交项目重启数据成功插入!");
//        }
//        return res.message;
//    }

    /**
     * PM提交项目暂停
     */
    @PostMapping("/stopProject")
    @ApiOperation(value = "PM提交项目停止信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "projectId", value = "项目ID", required = true),
//            @ApiImplicitParam(paramType="query", name = "projectName", value = "项目名字", required = true),
//            @ApiImplicitParam(paramType="query", name = "stopTimeFrom", value = "项目停止时间", required = true),
//            @ApiImplicitParam(paramType="query", name = "stopTimeTo", value = "项目停止预期中止时间", required = true),
//            @ApiImplicitParam(paramType="query", name = "note", value = "项目停止备注", required = true),
//            @ApiImplicitParam(paramType="query", name = "userId", value = "操作人ID", required = true),
//            @ApiImplicitParam(paramType="query", name = "userName", value = "操作人名字", required = true)
//    })
//    public Message stopProject(Integer projectId, String projectName, String stopTimeFrom, String stopTimeTo, String note, Integer userId, String userName) {
//        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
    public Message stopProject(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer projectId = Integer.valueOf(map.get("projectId").toString());
        String projectName = map.get("projectName").toString();
        String stopTimeFrom = map.get("stopTimeFrom").toString();
        String stopTimeTo = map.containsKey("stopTimeTo") ? (ObjectUtils.isEmpty(map.get("stopTimeTo")) ? null : map.get("stopTimeTo").toString()) : null;
        String note = map.get("note").toString();
        Integer userId = Integer.valueOf(map.get("userId").toString());
        String userName = map.get("userName").toString();
        int dataCount = projectStatusCacheService.insertProjectStatusCache(projectId, projectName, ProjectStatus.STOP.getStatus(), stopTimeFrom, stopTimeTo, note, userId, userName);
        if (dataCount == 1) {
            res.message.setMessage(200, "PM提交项目停止信息数据成功插入!");
        }
        return res.message;
    }

    /**
     * PM提交项目重启
     */
    @PostMapping("/restartProject")
    @ApiOperation(value = "PM提交项目重启")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "projectId", value = "项目ID", required = true),
//            @ApiImplicitParam(paramType="query", name = "projectName", value = "项目名字", required = true),
//            @ApiImplicitParam(paramType="query", name = "stopTimeFrom", value = "项目重启时间", required = true),
//            @ApiImplicitParam(paramType="query", name = "stopTimeTo", value = "项目预期截止时间", required = true),
//            @ApiImplicitParam(paramType="query", name = "note", value = "项目重启备注", required = true),
//            @ApiImplicitParam(paramType="query", name = "userId", value = "操作人ID", required = true),
//            @ApiImplicitParam(paramType="query", name = "userName", value = "操作人名字", required = true)
//    })
    public Message restartProject(@RequestBody JSONObject map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer projectId = Integer.valueOf(map.get("projectId").toString());
        String projectName = map.get("projectName").toString();
        String stopTimeFrom = map.get("rebootTimeFrom").toString();
        String stopTimeTo = map.containsKey("rebootTimeTo") ? (ObjectUtils.isEmpty(map.get("rebootTimeTo")) ? null : map.get("rebootTimeTo").toString()) : null;
        String note = map.get("note").toString();
        Integer userId = Integer.valueOf(map.get("userId").toString());
        String userName = map.get("userName").toString();
        int dataCount = projectStatusCacheService.insertProjectStatusCache(projectId, projectName, ProjectStatus.RESTART.getStatus(), stopTimeFrom, stopTimeTo, note, userId, userName);
        if (dataCount == 1) {
            res.message.setMessage(200, "PM提交项目重启数据成功插入!");
        }
        return res.message;
    }

    /**
     *
     */
    @PostMapping("/getProjectReportOld")
    @ApiOperation(value = "获取项目周报")
    public Message getProjectReportOld(@RequestBody Map projectMessage) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String projectName = projectMessage.get("projectName").toString();
        String userName = projectMessage.get("userName").toString();
//        String reportType = projectMessage.get("reportType").toString();
        DingUtils.getToken();
        JSONObject data = DingUtils.getProjectReport(userName, projectName, "项目周报（项目经理填写）");
        res.message.setMessage(200, "获取项目周报成功", data);
        return res.message;
    }

    /**
     *
     */
    @PostMapping("/getProjectReport")
    @ApiOperation(value = "获取项目周报")
    public Message getProjectReport(@RequestParam String projectName) throws ParseException {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        DingUtils.getToken();
        ArrayList<ProjectReport> data = projectReportService.getProjectReport(projectName);
        Project project = projectService.getProject(projectName);
        List<String> projectDate = DateUtils.getProjectDate(project.getProjStartTime(), ObjectUtils.isEmpty(project.getProjEndTime()) ? new Date() : project.getProjEndTime());
        int index = 0;
        JSONObject jsonObject = new JSONObject(new LinkedHashMap<>());
        for(String monday: projectDate) {
            ProjectReport projectReport = null;
            Date startTime = null;
            if(index < data.size()) {
                projectReport = data.get(index);
                startTime = simpleDateFormat.parse(projectReport.getStartTime());
            }
            if(!ObjectUtils.isEmpty(projectReport) && startTime.getTime() >= simpleDateFormat.parse(monday).getTime() && startTime.getTime() < (simpleDateFormat.parse(monday).getTime() + 1000 * 60 * 60 * 24 * 7)) {
                jsonObject.put(projectReport.getStartTime() + "~" + projectReport.getEndTime(), projectReport.getReport());
                index++;
            }else {
                Calendar c_friday = new GregorianCalendar();
                c_friday.setTime(simpleDateFormat.parse(monday));
                System.out.println("时间：" + simpleDateFormat.format(c_friday.getTime().getTime()));
                c_friday.add(Calendar.DAY_OF_MONTH, 4);
                jsonObject.put(monday + "~" + simpleDateFormat.format(c_friday.getTime().getTime()), "There is no data");
            }
        }
        res.message.setMessage(200, "获取项目周报成功", jsonObject);
        return res.message;
    }

    public static boolean getListStopFlag(List<String> typeList) {
        for (String type : typeList) {
            if ("STOP".equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean getListRestartFlag(List<String> typeList) {
        for (String type : typeList) {
            if ("RESTART".equals(type)) {
                return true;
            }
        }
        return false;
    }
}
