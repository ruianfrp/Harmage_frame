package com.harmonycloud.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.Threads.SynchroThread;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.employee.EmployeeCountView;
import com.harmonycloud.bean.employee.EmployeeListView;
import com.harmonycloud.bean.employee.EmployeeNumView;
import com.harmonycloud.bean.employee.EmployeeSkillView;
import com.harmonycloud.bean.skill.TestSkillListView;
import com.harmonycloud.config.DingConstant;
import com.harmonycloud.service.EmployeeService;
import com.harmonycloud.util.SyncInfo;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

/**
 * @author ：frp
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="员工controller",tags={"员工操作接口"})
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 员工详情 级联删除
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @DeleteMapping("/deleteEmployee")
    @ApiOperation(value = "员工删除", notes = "删除后所有外键相关自动删除")
    public Message deleteEmployee(@RequestBody @ApiParam(name = "employeeGh", value = "员工工号") Map map) throws Exception {
//        System.out.println(request);
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = (String) map.get("employeeGh");
        if (employeeGh != null) {
            employeeService.deleteEmployee(employeeGh);
            log.info("Sucess to delete " + employeeGh + " from employee");
            log.info(employeeGh + "删除成功");
            res.message.setMessage(200, employeeGh + "删除成功");
        } else {
            log.error("返回参数为空，无法删除");
            res.message.setMessage(400, "返回参数为空，无法删除");
        }
        return res.message;
    }

    /**
     * 按照给定的数据修改员工信息
     *
     * @param employeeListView
     * @return message
     * @throws Exception
     */
    @PostMapping("/updateEmployee")
    @ApiOperation(value = "修改员工信息", notes = "传入字段可以为空，空值传为null")
    public Message updateEmployee(@RequestBody @ApiParam(name = "employeeGh\nemployeeName\nemployeeSex\nemployeeDep\n" +
            "employeeJob\nemployeeType\nemployeeWorkplace\ncreateTime\nisQuit\nupdateTime\nskillInfo\nskillTestInfo", value = "员工信息") List<EmployeeListView> employeeListView) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = employeeListView.get(0).getEmployeeGh();
        String employeeName = employeeListView.get(0).getEmployeeName();
        String employeeSex = employeeListView.get(0).getEmployeeSex();
        String employeeDep = employeeListView.get(0).getEmployeeDep();
        String employeeJob = employeeListView.get(0).getEmployeeJob();
        String employeeType = employeeListView.get(0).getEmployeeType();
        String employeeWorkplace = employeeListView.get(0).getEmployeeWorkplace();
        Date createTime = employeeListView.get(0).getCreateTime();
        Integer isQuit = employeeListView.get(0).getIsQuit();
        Date updateTime = employeeListView.get(0).getUpdateTime();
        employeeService.updateEmployee(employeeName, employeeDep, employeeJob, employeeWorkplace, isQuit,
                createTime, updateTime, employeeSex, employeeType, employeeGh);
        List<EmployeeSkillView> skillInfo = employeeListView.get(0).getSkillInfo();
        List<TestSkillListView> skillTestInfo = employeeListView.get(0).getSkillTestInfo();
        if (skillInfo != null) {
            employeeService.deleteEmployeeSkill(employeeGh);
            for (short i = 0; i < skillInfo.size(); i++) {
                employeeService.insertEmployeeSkill(employeeGh, skillInfo.get(i).getId());
            }
            log.info("数据修改成功（包含普通技能）");
            res.message.setMessage(200, "数据修改成功（包含普通技能）");
        }
        if (skillTestInfo != null) {
            employeeService.deleteEmployeeSkillTest(employeeGh);
            for (short i = 0; i < skillTestInfo.size(); i++) {
                employeeService.insertEmployeeSkillTest(employeeGh, skillTestInfo.get(i).getSkillTestId());
            }
            log.info("数据修改成功（包含考核技能）");
            res.message.setMessage(200, "数据修改成功（包含考核技能）");
        }
        if (skillInfo == null && skillTestInfo == null) {
            log.info("数据修改成功");
            res.message.setMessage(200, "数据修改成功");
        }
        return res.message;
    }

    /**
     * 返回所有员工的信息（分页）
     *
     * @return message
     * @throws Exception
     */
    @PostMapping("/listEmployee")
    @ApiOperation(value = "返回员工信息")
    public Message listEmployee(@RequestBody Map map){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer pageNum = (Integer) map.get("pageNum");
        String selectEmployeeGh = (String) map.get("selectEmployeeGh");
        String selectEmployeeName = (String) map.get("selectEmployeeName");
        String selectSex = (String) map.get("selectSex");
        String selectType = (String) map.get("selectType");
        String sort = (String) map.get("sort");
        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum,10);
        List<EmployeeListView> list = employeeService.listEmployee(selectEmployeeGh,selectSex,selectType,selectEmployeeName,sort);
        PageInfo<EmployeeListView> pageInfo = new PageInfo<>(list);
        if (list.size()>0) {
            List<EmployeeSkillView> list1 = employeeService.selectEmployeeSkill();
            List<TestSkillListView> list2 = employeeService.selectEmployeeTestSkill();
            for (EmployeeListView employeeListView : list) {
                List<EmployeeSkillView> skillViewList = new ArrayList<>();
                List<TestSkillListView> testSkillViewList = new ArrayList<>();
                for(EmployeeSkillView skillView:list1){
                    if(employeeListView.getEmployeeGh().equals(skillView.getFkEmployeeGh())){
                        skillViewList.add(skillView);
                    }
                }
                for(TestSkillListView testSkillView:list2){
                    if(employeeListView.getEmployeeGh().equals(testSkillView.getFkEmployeeGh())){
                        testSkillViewList.add(testSkillView);
                    }
                }
                employeeListView.setSkillInfo(skillViewList);
                employeeListView.setSkillTestInfo(testSkillViewList);
            }
            log.info("Employee信息返回成功");
            data.put("pageInfo", pageInfo);
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，Employee数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 获取所有员工列表（导出）
     *
     * @return message
     * @throws Exception
     */
    @GetMapping("/listAllEmployee")
    @ApiOperation(value = "获取所有员工列表")
    public Message listAllEmployee(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<EmployeeListView> list = employeeService.listAllEmployee();
        if (list.size()>0) {
            List<EmployeeSkillView> list1 = employeeService.selectEmployeeSkill();
            List<TestSkillListView> list2 = employeeService.selectEmployeeTestSkill();
            for (EmployeeListView employeeListView : list) {
                List<EmployeeSkillView> skillViewList = new ArrayList<>();
                List<TestSkillListView> testSkillViewList = new ArrayList<>();
                for(EmployeeSkillView skillView:list1){
                    if(employeeListView.getEmployeeGh().equals(skillView.getFkEmployeeGh())){
                        skillViewList.add(skillView);
                    }
                }
                for(TestSkillListView testSkillView:list2){
                    if(employeeListView.getEmployeeGh().equals(testSkillView.getFkEmployeeGh())){
                        testSkillViewList.add(testSkillView);
                    }
                }
                employeeListView.setSkillInfo(skillViewList);
                employeeListView.setSkillTestInfo(testSkillViewList);
            }
            log.info("获取所有员工列表成功");
            data.put("list", list);
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，Employee数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 按照员工工号查看员工详情
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @PostMapping("/selectByEmployeeGh")
    @ApiOperation(value = "按照员工工号查看员工详情")
    public Message selectByEmployeeGh(@RequestBody @ApiParam(name = "employeeGh", value = "员工工号") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<EmployeeListView> listEmployee = employeeService.selectByEmployeeGh((String) map.get("employeeGh"));
        if (listEmployee != null) {
            for (EmployeeListView employeeListView : listEmployee) {
                employeeListView.setSkillInfo(employeeService.listEmployeeSkill(employeeListView.getEmployeeGh()));
                employeeListView.setSkillTestInfo(employeeService.listEmployeeTestSkill(employeeListView.getEmployeeGh()));
                employeeListView.setEmployeeExperience(employeeService.listEmployeeExperience(employeeListView.getEmployeeGh()));
            }
            data.put("list", listEmployee);
            log.info("员工信息返回成功");
            res.message.setMessage(200, "员工信息返回成功", data);
        } else {
            log.error("返回错误,员工信息返回为空");
            res.message.setMessage(400, "返回错误,员工信息返回为空");
        }
        return res.message;
    }

    /**
     * 员工列表的添加
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @PostMapping("/insertEmployee")
    @ApiOperation(value = "添加员工")
    public Message insertEmployee(@RequestBody @ApiParam(name = "employeeGh\nemployeeName\nemployeeDep\n" +
            "employeeJob\nemployeeWorkplace\nemployeeSex\nemployeeType", value = "员工信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String name = employeeService.selectEmployeeNameByEmployeeGh((String) map.get("employeeGh"));
        if (map.get("employeeGh") != null && map.get("employeeName") != null && map.get("employeeDep") != null && map.get("employeeJob") != null &&
                map.get("employeeWorkplace") != null && map.get("employeeSex") != null && map.get("employeeType") != null && name == null) {
            employeeService.insertEmployee((String) map.get("employeeGh"), (String) map.get("employeeName"), (String) map.get("employeeDep"),
                    (String) map.get("employeeJob"), (String) map.get("employeeWorkplace"), (String) map.get("employeeSex"), (String) map.get("employeeType"));
            log.info("员工添加成功");
            res.message.setMessage(200, "员工添加成功");
        } else if (name != null) {
            log.error("工号为 " + map.get("employeeGh") + " 的员工已存在，无法进行重复添加");
            res.message.setMessage(403, "工号为 " + map.get("employeeGh") + " 的员工已存在，无法进行重复添加");
        } else {
            log.error("返回数据不完整，添加员工失败");
            res.message.setMessage(400, "返回数据不完整，添加员工失败");
        }
        return res.message;
    }

    /**
     * 根据员工工号查找员工姓名
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @PostMapping("/findByEmployeeGh")
    @ApiOperation(value = "根据员工工号查找员工姓名")
    public Message findByEmployeeGh(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = (String) map.get("employeeGh");
        Map<String, Object> data = new HashMap<>();
        String employeeName = employeeService.findByEmployeeGh(employeeGh);
        if (employeeName != null) {
            data.put("employeeName", employeeName);
            log.info("员工信息返回成功");
            res.message.setMessage(200, "员工信息返回成功", data);
        } else {
            log.error("返回错误,员工信息返回为空");
            res.message.setMessage(400, "返回错误,员工信息返回为空");
        }
        return res.message;
    }

    /**
     * 根据姓名查找员工工号
     *
     * @param employeeName
     * @return message
     * @throws Exception
     */
    @PostMapping("/findByEmployeeName")
    @ApiOperation(value = "根据姓名查找员工工号")
    public Message findByEmployeeName(@ApiParam(name = "employeeName", value = "员工姓名") String employeeName) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        String employeeGH = employeeService.findByEmployeeName(employeeName);
        if (employeeGH != null) {
            data.put("employeeGH", employeeGH);
            log.info("员工信息返回成功");
            res.message.setMessage(200, "员工信息返回成功", data);
        } else {
            log.error("返回错误,员工工号返回为空");
            res.message.setMessage(400, "返回错误,员工工号返回为空");
        }
        return res.message;
    }

    /**
     * 同步按钮触发同步事件
     *
     * @return message
     */
    @GetMapping("/synchronization")
    @ApiOperation(value = "员工信息同步", notes = "同步时会自动添加用户")
    public Message synchronization(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
        SynchroThread synchro = new SynchroThread();
        synchro.run();
        res.message.setMessage(200, "同步成功");
        return res.message;
    }

    /**
     * 按照员工姓名查看员工详情
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @PostMapping("/selectByEmployeeName")
    @ApiOperation(value = "按照员工姓名查看员工详情")
    public Message selectByEmployeeName(@RequestBody @ApiParam(name = "employeeName", value = "员工姓名") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<EmployeeListView> listEmployee = employeeService.selectByEmployeeName((String) map.get("employeeName"));
        if (listEmployee != null) {
            for (EmployeeListView listSkill : listEmployee) {
                listSkill.setSkillInfo(employeeService.listEmployeeSkill(listSkill.getEmployeeGh()));
            }
            data.put("list", listEmployee);
            log.info("员工信息返回成功");
            res.message.setMessage(200, "员工信息返回成功", data);
        } else {
            log.error("返回错误,员工信息返回为空");
            res.message.setMessage(400, "返回错误,员工信息返回为空");
        }
        return res.message;
    }

    /**
     * 获取员工人数变化折线图数据
     *
     * @return res.message
     * @throws Exception
     */
    @GetMapping("/getData")
    @ApiOperation(value = "获取员工人数变化折线图数据")
    public Message getData() throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<EmployeeCountView> list = new ArrayList<>();
        for (Integer num = 0; num < 6; num++) {
            EmployeeCountView employeeCountView = employeeService.MonthInsertPerson(num);
            Integer endCountPerson = employeeService.MonthEndPerson(num);
            Integer quitNumPerson = employeeService.MonthQuitPerson(num);
            Integer beginCountPerson = employeeService.MonthStartPerson(num);
            employeeCountView.setEndCountPerson((endCountPerson-2));
            employeeCountView.setQuitNumPerson(quitNumPerson);
            employeeCountView.setBeginCountPerson((beginCountPerson-2));
            employeeCountView.setLastNumMonth(num + 1);
            list.add(num, employeeCountView);
        }
        if (list.size() > 0) {
            data.put("list", list);
            data.put("total", list.size());
            log.info("按月返回数据成功");
            res.message.setMessage(200, "按月返回数据成功", data);
        } else {
            log.error("按月返回数据为空");
            res.message.setMessage(400, "按月返回数据为空");
        }
        return res.message;
    }

    /**
     * 获取所有的员工姓名及工号
     * @return
     */
    @GetMapping("/selectAllEmployee")
    @ApiOperation(value = "获取所有的员工工号及姓名")
    public Message selectAllEmployee(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<EmployeeListView> list = employeeService.selectAllEmployee();
        if(list.size()>0){
            log.info("全部员工返回成功");
            data.put("list",list);
            data.put("total",list.size());
            res.message.setMessage(200,"全部员工返回成功",data);
        }else {
            log.error("全部员工返回为空");
            res.message.setMessage(400,"全部员工返回为空");
        }
        return res.message;
    }

    /**
     * 按照类型返回员工的数量（全职、兼职、实习）
     * @return
     */
    @GetMapping("/selectEmployeeNum")
    @ApiOperation(value = "返回员工人数")
    public Message selectEmployeeNum(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<EmployeeNumView> list = employeeService.selectEmployeeNum();
        if(list.size()>0){
            log.info("员工数量返回成功");
            data.put("list",list);
            data.put("total",list.size());
            res.message.setMessage(200,"员工数量返回成功",data);
        }else {
            log.error("员工数量返回为空");
            res.message.setMessage(400,"员工数量返回为空");
        }
        return res.message;
    }
}
