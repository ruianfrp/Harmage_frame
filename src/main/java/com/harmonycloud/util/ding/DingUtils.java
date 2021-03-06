package com.harmonycloud.util.ding;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.harmonycloud.bean.report.ProjectReport;
import com.harmonycloud.config.DingConstant;
import com.harmonycloud.config.URLConstant;
import com.harmonycloud.service.ProjectReportService;
import com.harmonycloud.util.date.DateUtils;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
//@Component
@Order(value = 1)
public class DingUtils {
    @Autowired
    ProjectReportService projectReportService;

    private static String accessToken = null;
    //暂时不考虑重命名情况
    private static HashMap<String, String> userIdHashMap = new HashMap<>(300);

    public static void main(String[] args) throws ParseException {
        getToken();
//        getUserIdMapByDepartment(getDepartment());
//        List<String> stringList = getInstanceList();
//        stringList.stream().forEach(a -> System.out.println(a));
//        stringList.stream().forEach();
//        List<ProjectReport> projectReportList = insertReportDataBase("项目周报（项目经理填写）");
//        projectReportList.stream().forEach(aa -> {System.out.println(aa.getStartTime() + ":::" + aa.getProjectName() + ":::" + aa.getReport());});
        List<String> instanceList = getInstanceList();
        for (String s : instanceList) {
            System.out.println(getDeliverTheProject(s));
        }
    }

    @PostConstruct
    public void init() {
        getToken();
        DingUtils.getUserIdMapByDepartment(DingUtils.getDepartment());
    }

    /**
     * 初始化的时候生成
     * 根据钉钉开发平台，个人看的时候觉得获取角色类型，然后获取角色类型的人物信息
     * 注入HashMap中较为轻盈（获取角色员工列表返回之后名字和userId）
     */
    public static Set<Long> getRoles() {
        Set<Long> rolesIdSet = new HashSet<>();
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_ROLE_LIST);
            OapiRoleListRequest req = new OapiRoleListRequest();
            OapiRoleListResponse rsp = client.execute(req, accessToken);
            System.out.println(rsp.getBody());
            rsp.getResult().getList().stream().forEach(roleGroup -> {
                roleGroup.getRoles().stream().forEach(role -> rolesIdSet.add(role.getId()));
            });
            System.out.println(rolesIdSet);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rolesIdSet;
    }

    /**
     * 根据角色获取用户名id信息
     *
     * @param roles
     */
    public static void getUserIdMap(Set<Long> roles) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_ROLE_SIMPLELIST);
            OapiRoleSimplelistRequest req = new OapiRoleSimplelistRequest();
            for (Long role : roles) {
                req.setRoleId(role);
                OapiRoleSimplelistResponse rsp = client.execute(req, accessToken);
                rsp.getResult().getList().forEach(member -> userIdHashMap.put(member.getName(), member.getUserid()));
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取公司部门信息
     * @return
     */
    public static Set<Long> getDepartment() {
        Set<Long> departmentSet = new HashSet<>();
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_DEPARTMENT_LIST);
            OapiDepartmentListRequest req = new OapiDepartmentListRequest();
            req.setHttpMethod("GET");
            OapiDepartmentListResponse rsp = client.execute(req, accessToken);
            rsp.getDepartment().stream().forEach(department -> {
                departmentSet.add(department.getId());
                System.out.println(department.getId());
            });
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(departmentSet);
        return departmentSet;
    }

    /**
     * 通过部门获取用户名id信息
     * @param departmentSet
     */
    public static void getUserIdMapByDepartment(Set<Long> departmentSet) {
        int i = 0;
        for (Long departmentId : departmentSet) {
            try {
                System.out.println(i++);
                DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_USER_SIMPLE_LIST);
                OapiUserSimplelistRequest req = new OapiUserSimplelistRequest();
                req.setDepartmentId(departmentId);
                req.setHttpMethod("GET");
                OapiUserSimplelistResponse rsp = client.execute(req, accessToken);
                JSONObject.parseObject(rsp.getBody()).getJSONArray("userlist").stream().forEach(user -> {
                    JSONObject userJSONObject = JSONObject.parseObject(user.toString());
                    userIdHashMap.put(userJSONObject.getString("name"), userJSONObject.getString("userid"));
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取token，现如今场景下，页面访问获取钉钉信息较少，所以不进行token的缓存刷新，之后在此项目和钉钉数据全局同步后建议缓存刷新
     */
    public static void getToken() {
        try {
            //获取accessToken实现免登陆
            DefaultDingTalkClient cli = new DefaultDingTalkClient(URLConstant.URL_GET_TOKKEN);
            OapiGettokenRequest req = new OapiGettokenRequest();
            //后期导入配置文件
            req.setAppkey(DingConstant.APP_KEY);
            req.setAppsecret(DingConstant.APP_SECRET);
            req.setHttpMethod("GET");
            OapiGettokenResponse res = cli.execute(req);
            accessToken = res.getAccessToken();
        } catch (ApiException apiException) {
            //日志
        }
    }

    /**
     * 通过用户名，项目名，报表类型获取报表信息
     *
     * @param userName
     * @param projectName
     * @param reportType
     * @return
     */
    public static JSONObject getProjectReport(String userName, String projectName, String reportType) {
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_REPORT_LIST);
        OapiReportListRequest request = new OapiReportListRequest();
        request.setTemplateName(reportType);
        request.setStartTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * (30 * 6 - 1));
        System.out.println(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30 * 6);
        request.setEndTime(System.currentTimeMillis() + 1000L * 60 * 60 * 24);
        System.out.println(System.currentTimeMillis());
        request.setCursor(0L);
        request.setSize(100L);
        request.setUserid(userIdHashMap.get(userName));
        try {
            OapiReportListResponse response = client.execute(request, accessToken);
            //解码获取的日志json格式文件
//            String jsonArray = response.getResult().getDataList().;
            String logInfo = response.getBody();
            JSONObject jsonObject = JSONObject.parseObject(logInfo);
            JSONObject resultJsonObject = new JSONObject(new LinkedHashMap<>());
            DateUtils.insertUserIdMap(resultJsonObject);
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data_list");
            for (Object jsonReport : jsonArray) {
                JSONObject jsonObject1 = JSONObject.parseObject(jsonReport.toString());
                for (Object object : jsonObject1.getJSONArray("contents")) {
                    JSONObject contentsJSONObject = JSONObject.parseObject(object.toString());
                    if (contentsJSONObject.get("key").equals("项目名称") && projectName.equals(contentsJSONObject.getString("value"))) {
                        String createTime = jsonObject1.getString("create_time");
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(Long.valueOf(createTime));
                        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        String monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                        cal.add(Calendar.DAY_OF_MONTH, 5);
                        String friday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                        resultJsonObject.put(monday + "~" + friday, jsonObject1);
                        break;
                    }
                }
            }
            return resultJsonObject;
        } catch (Exception exception) {
            //
        }
        return new JSONObject();
    }

    /**
     * 获取报表信息，并将信息存入数据库中
     *
     * @param reportType
     * @return
     */
    public static List<ProjectReport> insertReportDataBase(String reportType) {
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_REPORT_LIST);
        OapiReportListRequest request = new OapiReportListRequest();
        request.setTemplateName(reportType);
        request.setStartTime(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7);
        request.setEndTime(System.currentTimeMillis());
        request.setCursor(0L);
        request.setSize(100L);
        try {
            OapiReportListResponse response = client.execute(request, accessToken);
            //解码获取的日志json格式文件
//            String jsonArray = response.getResult().getDataList().;
            String logInfo = response.getBody();
            JSONObject jsonObject = JSONObject.parseObject(logInfo);
            JSONObject resultJsonObject = new JSONObject(new LinkedHashMap<>());
            DateUtils.insertUserIdMap(resultJsonObject);
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data_list");
            List<ProjectReport> projectReportList = new ArrayList<>();
//            ProjectReport projectReport = new ProjectReport();
            for (Object jsonReport : jsonArray) {
                ProjectReport projectReport = new ProjectReport();
                JSONObject jsonObject1 = JSONObject.parseObject(jsonReport.toString());
                for (Object object : jsonObject1.getJSONArray("contents")) {
                    JSONObject contentsJSONObject = JSONObject.parseObject(object.toString());
                    if ("开始时间".equals(contentsJSONObject.getString("key"))) {
                        projectReport.setStartTime(contentsJSONObject.getString("value").split(" ")[0]);
                    }
                    if ("结束时间".equals(contentsJSONObject.getString("key"))) {
                        projectReport.setEndTime(contentsJSONObject.getString("value").split(" ")[0]);
                    }
                    if ("项目名称".equals(contentsJSONObject.getString("key"))) {
                        projectReport.setProjectName(contentsJSONObject.getString("value"));
                    }
                }
                projectReport.setReport(jsonObject1.getJSONArray("contents").toJSONString());
                projectReportList.add(projectReport);
            }
            return projectReportList;
        } catch (Exception exception) {
            //
        }
        return new ArrayList<>();
    }

    public static List<String> getInstanceList() {
        getToken();
        List<String> instanceList = new ArrayList<>();
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_PROCESSINSTANCE_LISTIDS);
            OapiProcessinstanceListRequest req = new OapiProcessinstanceListRequest();
            req.setProcessCode("PROC-F2883F47-184A-48FC-9B20-781CFE9C3F39");
            Date day2 = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String yesterday = simpleDateFormat.format(day2);//获取昨天日期
//            System.out.println(yesterday);
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yesterday + " 00:00:00");
            long yesterdaytamp = date1.getTime();
//            System.out.println(yesterdaytamp);
            req.setStartTime(yesterdaytamp);
            OapiProcessinstanceListResponse rsp = client.execute(req, accessToken);
            if (rsp.isSuccess()) {
                for (int i = 0; i < rsp.getResult().getList().size(); i++) {
                    instanceList.add(String.valueOf(rsp.getResult().getList().get(i)));
                }
            }
        } catch (ApiException | ParseException e) {
            e.printStackTrace();
        }
        return instanceList;
    }

    public static OapiProcessinstanceGetResponse getDeliverTheProject(String instanceId) {
        getToken();
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_PROCESSINSTANCE);
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(instanceId);
            OapiProcessinstanceGetResponse response = client.execute(request, accessToken);
            if (response.isSuccess() && response.getProcessInstance().getResult().equals("agree") && response.getProcessInstance().getStatus().equals("COMPLETED")) {
                log.info("有新的立项申请...");
//                duplicateCharacters(response.getProcessInstance().getFormComponentValues().get(2).getValue());
//                duplicateCharacters(response.getProcessInstance().getFormComponentValues().get(3).getValue());
//                Connection con = DriverManager.getConnection("jdbc:mysql://47.92.161.179:31036/harmage?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
//                        "root", "Ab@123456");
//                String sql2 = "insert into project(proj_PM_gh, proj_Saleman, proj_name, proj_type, proj_line, proj_place," +
//                        "proj_presta_time, proj_preend_time, proj_establish_time, proj_money, proj_all_cost, proj_current_cost, proj_status," +
//                        "proj_remark, create_time, proj_end_type, proj_substate) select (select employee_gh from employee where employee_name=?)," +
//                        "(select employee_gh from employee where employee_name=?),?,?,?,'-',?,?,?,?,0,0,'已立项',?,?,'未申请','正常' from dual" +
//                        "where not EXISTS (select proj_name from project where proj_name=?);";
//                PreparedStatement st5 = con.prepareStatement(sql2);
//                st5.setString(1, "冯锐鹏");
//                st5.setString(2, "冯锐鹏");
//                st5.setString(3, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(4, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(5, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(6, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(7, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(8, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(9, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(10, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(11, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                st5.setString(12, response.getProcessInstance().getFormComponentValues().get(0).getValue());
//                int rs5 = st5.executeUpdate();
//                if (rs5 > 0) {
//                    log.info("成功添加账号密码");
//                }
//                st5.close();
//                con.close();
                return response;
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取字符串中相同值（名字）
    private static void duplicateCharacters(String word) {
        char[] charaters = word.toCharArray();
        Map<Character, Integer> charMap = new HashMap<Character, Integer>();
        for (Character ch : charaters) {
            if (charMap.containsKey(ch)) {
                charMap.put(ch, charMap.get(ch) + 1);
            } else {
                charMap.put(ch, 1);
            }
        }
        Set<Map.Entry<Character, Integer>> entrySet = charMap.entrySet();
        System.out.printf("List of duplicate characters in Strng '%s' %n", word);
        for (Map.Entry<Character, Integer> entry : entrySet) {
            if (entry.getValue() > 1) {
                System.out.printf("%s:%d:%n", entry.getKey(), entry.getValue());
            }
        }
    }
}
