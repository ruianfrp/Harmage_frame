package com.harmonycloud.controller;


import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.entity.Customer;
import com.harmonycloud.entity.Project;
import com.harmonycloud.service.CustomerService;
import com.harmonycloud.service.ProjectService;
import com.harmonycloud.view.ProjectListView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="项目controller",tags={"项目操作接口"})
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    CustomerService customerService;

    @Autowired
    private HttpServletRequest request;


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
            list = projectService.listProject();
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
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
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
            log.info("Project信息返回成功");
            data.put("list", projectListView);
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，Project数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }
}
