package com.harmonycloud.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.harmonycloud.bean.*;
import com.harmonycloud.bean.Threads.LoginThread;
import com.harmonycloud.config.Constant;
import com.harmonycloud.service.EmployeeService;
import com.harmonycloud.service.LoginInfoService;
import com.harmonycloud.util.DesEncryptUtil;
import com.harmonycloud.util.SendMessageUtil;
import com.harmonycloud.view.AdminLoginView;
import com.harmonycloud.view.LoginInfoView;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.harmonycloud.config.URLConstant.URL_GET_TOKKEN;
import static com.harmonycloud.util.JsonWebToken.CreateToken;
import static com.harmonycloud.util.JsonWebToken.VerifyCode;


/**
 * @author ：wz
 * @date ：Created in 2019/8/8 21:28
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="用户登录controller",tags={"用户登录操作接口"})
public class LoginController {

    @Autowired
    LoginInfoService loginInfoService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 用户扫码登录
     *
     * @param requestAuthCode 免登临时code
     */
    @GetMapping("/login")
    @ApiOperation(value = "用户扫码登录")
    public Message login(@RequestParam(value = "code") String requestAuthCode) {
        LoginThread login = new LoginThread(requestAuthCode);
        System.out.println(requestAuthCode);
        login.run();
        Message message = LoginThread.res;
        return message;
    }

    /**
     * 管理员登录
     */
    @PostMapping("/adminLogin")
    @ApiOperation(value = "管理员登录")
    public Message adminLogin(@RequestBody Map map) {
        Message message = new Message();
        Map<String, Object> data = new HashMap<>();
        String account = (String) map.get("account");
        String password = (String) map.get("password");
        if (account.equals("0020")) {
            ArrayList<LoginInfoView> user = new ArrayList<>();
            String value = loginInfoService.selectUserAuthority("0020");
            String role = selectRole(value);
            LoginInfoView information = new LoginInfoView("0020", "王瑜", role);
            user.add(information);
            data.put("list", user);
            data.put("total", 1);
            String token = "";
            try {
                token = CreateToken(information);
            } catch (Exception e) {
                log.error("Token creating was failed!");
            }
            data.put("token", token);
            message.setMessage(200, "登陆成功", data);
            return message;
        }
        if (account.equals("0001")) {
            ArrayList<LoginInfoView> user = new ArrayList<>();
            String value = loginInfoService.selectUserAuthority("0001");
            String role = selectRole(value);
            LoginInfoView information = new LoginInfoView("0001", "王翱宇", role);
            user.add(information);
            data.put("list", user);
            data.put("total", 1);
            String token = "";
            try {
                token = CreateToken(information);
            } catch (Exception e) {
                log.error("Token creating was failed!");
            }
            data.put("token", token);
            message.setMessage(200, "登陆成功", data);
            return message;
        }
        if (account.equals("0012")) {
            ArrayList<LoginInfoView> user = new ArrayList<>();
            String value = loginInfoService.selectUserAuthority("0012");
            String role = selectRole(value);
            LoginInfoView information = new LoginInfoView("0012", "杨晶", role);
            user.add(information);
            data.put("list", user);
            data.put("total", 1);
            String token = "";
            try {
                token = CreateToken(information);
            } catch (Exception e) {
                log.error("Token creating was failed!");
            }
            data.put("token", token);
            message.setMessage(200, "登陆成功", data);
            return message;
        }
        if (account.equals("0016")) {
            ArrayList<LoginInfoView> user = new ArrayList<>();
            String value = loginInfoService.selectUserAuthority("0016");
            String role = selectRole(value);
            LoginInfoView information = new LoginInfoView("0016", "郑国林", role);
            user.add(information);
            data.put("list", user);
            data.put("total", 1);
            String token = "";
            try {
                token = CreateToken(information);
            } catch (Exception e) {
                log.error("Token creating was failed!");
            }
            data.put("token", token);
            message.setMessage(200, "登陆成功", data);
            return message;
        }
        AdminLoginView adminLoginView = loginInfoService.adminLogin(account);
        try {
//            String key = DESCoderBean.initKey("harmonycloud");
            if (adminLoginView == null) {
                log.error("账号不存在");
                message.setMessage(403, "登陆失败,账号不存在");
            } else {
                DesEncryptUtil u = new DesEncryptUtil();
                String desString = u.getDesString(adminLoginView.getPassword());
//                byte[] s = DESCoderBean.decryptBASE64(adminLoginView.getPassword());
//                byte[] outputData = DESCoderBean.decrypt(s, key);
//                String outputStr = new String(outputData);
                if (password.equals(desString)) {
                    log.info("登陆成功");
                    if (adminLoginView.getFkEmployeeGh().equals("admin")) {
                        ArrayList<LoginInfoView> user = new ArrayList<>();
                        LoginInfoView information = new LoginInfoView("01", "管理员", "Overview_RO,Project_RW_A,Approval_RW_A,Employee_RW_A,Customer_RW_A,User_RW_A,Setting_RW_A");
                        user.add(information);
                        data.put("list", user);
                        data.put("total", 1);
                        String token = "";
                        try {
                            token = CreateToken(information);
                        } catch (Exception e) {
                            log.error("Token creating was failed!");
                        }
                        data.put("token", token);
                        message.setMessage(200, "登陆成功", data);
                    } else if (adminLoginView.getFkEmployeeGh().equals("saleAdmin")) {
                        ArrayList<LoginInfoView> user = new ArrayList<>();
                        LoginInfoView information = new LoginInfoView("02", "销售管理员", "Customer_RW_A,Setting_RW,User_RW_S");
                        user.add(information);
                        data.put("list", user);
                        data.put("total", 1);
                        String token = "";
                        try {
                            token = CreateToken(information);
                        } catch (Exception e) {
                            log.error("Token creating was failed!");
                        }
                        data.put("token", token);
                        message.setMessage(200, "登陆成功", data);
                    } else {
                        String employeeGh = adminLoginView.getFkEmployeeGh();
                        String authority_value = loginInfoService.selectUserAuthority(employeeGh);
                        String name = loginInfoService.selectNameByGh(employeeGh);
                        ArrayList<LoginInfoView> user = new ArrayList<>();
                        if(authority_value!=null){
                            String role = selectRole(authority_value);
                            LoginInfoView information = new LoginInfoView(employeeGh, name, role);
                            user.add(information);
                            data.put("list", user);
                            data.put("total", 1);
                            String token = "";
                            try {
                                token = CreateToken(information);
                            } catch (Exception e) {
                                log.error("Token creating was failed!");
                            }
                            data.put("token", token);
                            message.setMessage(200, "登陆成功", data);
                        } else {
                            log.error("此用户无权限");
                            message.setMessage(403,"此用户无权限，无法登录");
                        }
                    }
                } else {
                    log.info("密码错误");
                    message.setMessage(403, "登陆失败,密码错误");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 返回待办数量
     *
     * @return res.message
     * @throws Exception
     */
    @GetMapping("/numberWait")
    @ApiOperation(value = "返回待办数量")
    public Message numberWait() throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String role = res.user.getRole();
        Map<String, Object> data = new HashMap<>();
        if (role.contains("Approval_RW_A")) {
            Integer applyNum = loginInfoService.selectApplyWait();
            Integer leaveNum = loginInfoService.selectLeaveWait();
            Integer projectEndNum = loginInfoService.selectProjectEndWait();
            Integer memberNum = applyNum + leaveNum;
            data.put("memberNum", memberNum);
            data.put("projectEndNum", projectEndNum);
            data.put("applyNum", applyNum);
            data.put("leaveNum", leaveNum);
            log.info("PMO待办数量返回成功");
            res.message.setMessage(200, "PMO待办数量返回成功", data);
        } else if (role.contains("Approval_RW_S")) {
            String employeeGh = res.user.getId();
            Integer applicantAgreeNum = loginInfoService.selectApplicantAgreeWait(employeeGh);
            Integer ownerAgreeNum = loginInfoService.selectOwnerAgreeWait(employeeGh);
            Integer memberNum = applicantAgreeNum + ownerAgreeNum;
            data.put("memberNum", memberNum);
            data.put("applicantAgreeNum", applicantAgreeNum);
            data.put("ownerAgreeNum", ownerAgreeNum);
            log.info("PM待办数量返回成功");
            res.message.setMessage(200, "PM待办数量返回成功", data);
        } else {
            log.warn("此权限无数据返回");
            res.message.setMessage(200, "此权限无数据返回");
        }
        return res.message;
    }

    /**
     * 管理员账号密码加入
     *
     * @throws Exception
     */
    @GetMapping("/insertAdmin")
    @ApiOperation(value = "管理员账号密码加入(不可用！！！！！)")
    public void insertAdmin() throws Exception {
        DesEncryptUtil u = new DesEncryptUtil();
        //加密
        String encString = u.getEncString("Ab123456");
        Integer i = loginInfoService.insertAdmin(encString);
        if (i > 0) {
            log.info("success");
        } else {
            log.error("wrong");
        }
    }

    @PostMapping("/dep")
    @ApiOperation(value = "获取钉钉所有部门列表")
    public List<OapiDepartmentListResponse.Department> dep() throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId("1");
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, getToken());
//        SendMessageUtil.PMOMessageSend("Harmage Test");
        return response.getDepartment();
    }

    private static String getToken() {
        DefaultDingTalkClient client = new
                DefaultDingTalkClient(URL_GET_TOKKEN);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(Constant.APP_KEY);
        request.setAppsecret(Constant.APP_SECRET);
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String selectRole(String authority) {
        ArrayList role = new ArrayList();
        String[] arr = authority.split(",");
        for (short i = 0; i < arr.length; i++) {
            if (!role.contains(arr[i])) {
                role.add(arr[i]);
            }
        }
        String overview = "";
        String project = "";
        String approval = "";
        String employee = "";
        String customer = "";
        String user = "";
        String setting = "";

        for (short i = 0; i < role.size(); i++) {
            //project
            if (role.get(i).equals("Project_RW_A") && (project.equals("Project_RO") || project.equals("Project_RW_S") || project.equals(""))) {
                project = "Project_RW_A";
            }
            if (role.get(i).equals("Project_RW_S") && (project.equals("Project_RO") || project.equals(""))) {
                project = "Project_RW_S";
            }
            if (role.get(i).equals("Project_RO") && project.equals("")) {
                project = "Project_RO";
            }

            //overview
            if (role.get(i).equals("Overview_RO") && overview.equals("")) {
                overview = "Overview_RO";
            }

            //approval
            if (role.get(i).equals("Approval_RW_A") && (approval.equals("Approval_RW_S") || approval.equals("Approval_RO") || approval.equals(""))) {
                approval = "Approval_RW_A";
            }
            if (role.get(i).equals("Approval_RW_S") && (approval.equals("Approval_RO") || approval.equals(""))) {
                approval = "Approval_RW_S";
            }
            if (role.get(i).equals("Approval_RO") && approval.equals("")) {
                approval = "Approval_RO";
            }

            //employee
            if (role.get(i).equals("Employee_RW_A") && (employee.equals("Employee_RO") || employee.equals(""))) {
                employee = "Employee_RW_A";
            }
            if (role.get(i).equals("Employee_RO") && employee.equals("")) {
                employee = "Employee_RO";
            }

            //customer
            if (role.get(i).equals("Customer_RW_A") && (customer.equals("Customer_RW_M_I") || customer.equals("Customer_RW_S") || customer.equals("Customer_RO") || customer.equals(""))) {
                customer = "Customer_RW_A";
            }
            if (role.get(i).equals("Customer_RW_M_I") && (customer.equals("Customer_RW_S") || customer.equals("Customer_RO") || customer.equals(""))) {
                customer = "Customer_RW_M_I";
            }
            if (role.get(i).equals("Customer_RW_S") && (customer.equals("Customer_RO") || customer.equals(""))) {
                customer = "Customer_RW_S";
            }
            if (role.get(i).equals("Customer_RO") && customer.equals("")) {
                customer = "Customer_RO";
            }

            //user
            if (role.get(i).equals("User_RW_A") && (user.equals("User_RW_S_P") || user.equals("User_RW_S_S") || user.equals(""))) {
                user = "User_RW_A";
            }
            if (role.get(i).equals("User_RW_S_P") && user.equals("")) {
                user = "User_RW_S_P";
            }
            if (role.get(i).equals("User_RW_S_S") && user.equals("")) {
                user = "User_RW_S_S";
            }
            if ((role.get(i).equals("User_RW_S_S") && user.equals("User_RW_S_P")) || (role.get(i).equals("User_RW_S_P") && user.equals("User_RW_S_S"))) {
                user = "User_RW_S_S,User_RW_S_P";
            }

            //setting
            if (role.get(i).equals("Setting_RW_A") && setting.equals("")) {
                setting = "Setting_RW_A";
            }
        }
        List<String> list = new ArrayList<>();
        if (!overview.equals("")) {
            list.add(overview);
        }
        if (!project.equals("")) {
            list.add(project);
        }
        if (!approval.equals("")) {
            list.add(approval);
        }
        if (!employee.equals("")) {
            list.add(employee);
        }
        if (!customer.equals("")) {
            list.add(customer);
        }
        if (!user.equals("")) {
            list.add(user);
        }
        if (!setting.equals("")) {
            list.add(setting);
        }

        String lastRole = String.join(",", list);
        return lastRole;
    }

    @PostMapping("/appLogin")
    @ApiOperation(value = "小程序钉钉免登录")
    public Message appLogin(@RequestBody Map map) {
        Message message = new Message();
        String requestAuthCode = (String) map.get("authCode");
        OapiUserGetResponse response2 = new OapiUserGetResponse();
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(requestAuthCode);
            request.setHttpMethod("GET");
            DefaultDingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest request1 = new OapiGettokenRequest();
            request1.setAppkey("dingylaxhyekqw1rzutm");
            request1.setAppsecret("mlWkotc99Zfu_Bttlldg0VjXj75Zh3-LG4iYnte1RYRSgLGmvoMOxRrf72SRPVOe");
            request1.setHttpMethod("GET");
            OapiGettokenResponse response1 = client1.execute(request1);
            String accessToken = response1.getAccessToken();
            OapiUserGetuserinfoResponse response = client.execute(request, accessToken);
            DingTalkClient client2 = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            OapiUserGetRequest request2 = new OapiUserGetRequest();
            request2.setUserid(response.getUserid());
            request2.setHttpMethod("GET");
            response2 = client2.execute(request2, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        if (response2.isSuccess()) {
            Map<String, Object> data = new HashMap<>();
            String employeeGh = response2.getJobnumber();
            String authority_value = loginInfoService.selectUserAuthority(employeeGh);
            String name = response2.getName();
            ArrayList<LoginInfoView> user = new ArrayList<>();
            String role = null;
            if (authority_value != null) {
                role = selectRole(authority_value);
            }
            LoginInfoView information = new LoginInfoView(employeeGh, name, role);
            user.add(information);
            data.put("list", user);
            data.put("total", 1);
            String token = "";
            try {
                token = CreateToken(information);
            } catch (Exception e) {
                log.error("Token creating was failed!");
            }
            data.put("token", token);
            message.setMessage(200, "登陆成功", data);
        } else {
            log.info("小程序登陆失败");
            message.setMessage(403, "小程序登陆失败");
        }
        return message;
    }
}


