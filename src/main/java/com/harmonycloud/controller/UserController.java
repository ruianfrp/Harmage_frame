package com.harmonycloud.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.account.Authority;
import com.harmonycloud.bean.account.UserListView;
import com.harmonycloud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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
@Api(value="用户controller",tags={"用户操作接口"})
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/selectAuthority")
    @ApiOperation(value = "获取权限信息")
    public Message selectAuthority() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<Authority> list = userService.selectAuthority();
        if (list != null) {
            log.info("权限信息返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "权限信息返回成功", data);
        } else {
            log.error("权限信息返回为空");
            res.message.setMessage(400, "权限信息返回为空");
        }
        return res.message;
    }

    /**
     * User列表获取
     *
     * @return message
     */
    @PostMapping("/listUser")
    @ApiOperation(value = "获取用户信息")
    public Message listUser(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer pageNum = (Integer) map.get("pageNum");
        String selectEmployeeGh = (String) map.get("selectEmployeeGh");
        String selectEmployeeName = (String) map.get("selectEmployeeName");
        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum,10);
        List<UserListView> list = userService.listUser(selectEmployeeGh,selectEmployeeName);
        PageInfo<UserListView> pageInfo = new PageInfo<>(list);
        if (pageInfo.getTotal()>0) {
            for (UserListView userListView : list) {
                String authority = LoginController.selectRole(userListView.getAuthorityValue());
                userListView.setAuthorityValue(authority);
            }
            data.put("pageInfo", pageInfo);
            log.info("数据返回成功");
            res.message.setMessage(200, "数据返回成功", data);
        } else {
            log.error("返回错误，User数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 用户权限的实时修改
     *
     * @param map
     * @return message
     */
    @PostMapping("/update_authority")
    @ApiOperation(value = "修改用户权限")
    public Message updateAuthority(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String authority = (String) map.get("authority");
        String employeeGh = (String) map.get("employeeGh");
        String salesIndustry = (String) map.get("salesIndustry");
        if (authority != null && employeeGh != null) {
            userService.deleteUser(employeeGh);
            List<String> result = Arrays.asList(authority.split(";"));
            for (String str : result) {
                userService.insertUser(employeeGh, str, salesIndustry);
            }
            log.info("用户权限修改成功");
            res.message.setMessage(200, "用户权限修改成功");
        } else {
            log.error("用户权限修改失败，数据为空");
            res.message.setMessage(400, "用户权限修改失败，数据为空");
        }
        return res.message;
    }

    /**
     * 用户列表的删除
     *
     * @param map
     * @return message
     */
    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "用户列表的删除")
    public Message deleteUser(@RequestBody @ApiParam(name = "employeeGh", value = "用户工号", required = true) Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = (String) map.get("employeeGh");
        if (employeeGh != null) {
            userService.deleteUser(employeeGh);
            log.info("用户删除成功");
            res.message.setMessage(200, "用户删除成功");
        } else {
            log.error("用户删除失败，无数据返回");
            res.message.setMessage(400, "用户删除失败，无数据返回");
        }
        return res.message;
    }

    /**
     * 用户列表的添加
     *
     * @param userListViews
     * @return message
     */
    @PostMapping("/insertUser")
    @ApiOperation(value = "用户列表的添加")
    public Message insertUser(@RequestBody @ApiParam(name = "employeeGh\nuserName\nemployeeJob\nprojName\nauthority\n" +
            "updateTime\ndingtalkStuffid", value = "用户信息\n除了employeeGh,authority其余为null", required = true) List<UserListView> userListViews) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        for (short i = 0; i < userListViews.size(); i++) {
            if (userListViews.get(i).getEmployeeGh() != null) {
                String name = userService.selectByfkEmployeeGhInEmployee(userListViews.get(i).getEmployeeGh());
                if (name != null) {
                    List<String> result = Arrays.asList(userListViews.get(i).getAuthority().split(","));
                    for (String str : result) {
                        userService.insertUser(userListViews.get(i).getEmployeeGh(), str, userListViews.get(i).getSalesIndustry());
                    }
                    log.info("用户添加成功");
                    res.message.setMessage(200, "用户添加成功");
                } else {
                    log.error("员工工号不存在，无法进行用户添加");
                    res.message.setMessage(400, "员工工号不存在，无法进行用户添加");
                }
            } else if (userListViews.get(i).getEmployeeGh() == null) {
                log.error("工号无返回，添加失败");
                res.message.setMessage(400, "工号无返回，添加失败");
            }
        }
        return res.message;
    }
}
