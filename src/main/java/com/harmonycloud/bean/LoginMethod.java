package com.harmonycloud.bean;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.harmonycloud.bean.account.LoginInfoView;
import com.harmonycloud.config.DingConstant;
import com.harmonycloud.controller.LoginController;
import com.harmonycloud.util.DesEncryptUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.harmonycloud.config.URLConstant.*;
import static com.harmonycloud.util.JsonWebToken.CreateToken;

@Slf4j
public class LoginMethod {

    public LoginMethod(){
    }

    public static void main(String[] args) {
        try {
            // 获取连接
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://47.92.161.179:31036/harmage?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                    "root", "Ab@123456");
            // 定义sql语句
            String Sql = "select * from employee";
            // 执行sql语句
            Statement stt = conn.createStatement();
            // 返回结果集
            ResultSet rs = stt.executeQuery(Sql);
            if(rs.next()){
                System.out.println(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LoginInfoView getDataFromSql(String unionid_dingding) throws ApiException, SQLException {
        String req = null;
        //根据用户union_id搜索user_id(员工工号)
        DingTalkClient client = new DefaultDingTalkClient(URL_GET_USERID_BY_UNIONID);
        OapiUserGetUseridByUnionidRequest request = new OapiUserGetUseridByUnionidRequest();
        request.setUnionid(unionid_dingding);
        request.setHttpMethod("GET");
        OapiUserGetUseridByUnionidResponse response = client.execute(request, getToken());

        DingTalkClient clientUser = new DefaultDingTalkClient(URL_GET_USER_INFO);
        OapiUserGetRequest requestUser = new OapiUserGetRequest();
        requestUser.setUserid(response.getUserid());
        requestUser.setHttpMethod("GET");
        OapiUserGetResponse responseUser = clientUser.execute(requestUser, getToken());

        String employeeGh = responseUser.getJobnumber();
        System.out.println(employeeGh);//年份+原本的工号？？？
        Connection con = DriverManager.getConnection("jdbc:mysql://47.92.161.179:31036/harmage?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                "root", "Ab@123456");
        String sql = "select GROUP_CONCAT(a.authority_value SEPARATOR ',') as authority_value from user as u left join authority as a on u.user_authority_id=a.id where fk_employee_gh=?;";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, employeeGh);
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            req = LoginController.selectRole(rs.getString(1));
        }
        rs.close();
        st.close();

        String name = null;
        String sql2 = "select employee_name from employee where employee_gh=?;";
        PreparedStatement st2 = con.prepareStatement(sql2);
        st2.setString(1, employeeGh);
        ResultSet rs2 = st2.executeQuery();
        if(rs2.next()){
            name = rs2.getString(1);
        }
        rs2.close();
        st2.close();
        String sql3 = "select dingding_user_id from employee where employee_gh=?;";
        PreparedStatement st3 = con.prepareStatement(sql3);
        st3.setString(1,employeeGh);
        ResultSet rs3 = st3.executeQuery();
        if(rs3.next()){
            if(rs3.getString(1)==null){
                String sql4 = "update employee set dingding_user_id=? where employee_gh=?;";
                PreparedStatement st4 = con.prepareStatement(sql4);
                st4.setString(1,response.getUserid());
                st4.setString(2,employeeGh);
                int rs4 = st4.executeUpdate();
                if(rs4!=0){
                    log.info(employeeGh + " 钉钉user_id添加成功");
                }else {
                    log.error(employeeGh + " 钉钉user_id添加失败");
                }
                st4.close();
            }
        }
        rs3.close();
        st3.close();
        String sql5 = "insert into admin(account,fk_employee_gh,password) select ?,?,? from dual where not EXISTS (select account from admin where account=?);";
        PreparedStatement st5 = con.prepareStatement(sql5);
        st5.setString(1,responseUser.getMobile());
        DesEncryptUtil u = new DesEncryptUtil();
        String encString = u.getEncString("Ab123456");
        st5.setString(2,responseUser.getJobnumber());
        st5.setString(3,encString);
        st5.setString(4,responseUser.getMobile());
        int rs5 = st5.executeUpdate();
        if(rs5 > 0){
            log.info("成功添加账号密码");
        }
        st5.close();
        con.close();
        if(name != null && req != null){
            return new LoginInfoView(employeeGh,name,req,unionid_dingding);
        }else {
            return new LoginInfoView(employeeGh,null,"No Person",unionid_dingding);
        }
    }


    private static Boolean verifyAdminInfo(String account, String password){
        return true;
//        try {
//            String key = DESCoderBean.initKey("harmonycloud");
//            List<AdminLoginView> adminlist = loginInfoService.adminLogin(account);
//            if(adminlist.get(0).getName() == null){
//                log.error("Account not found");
//            }
//            else{
//                byte[] s = DESCoderBean.decryptBASE64(adminlist.get(0).getPassword());
//                byte[] outputData = DESCoderBean.decrypt(s, key);
//                String outputStr = new String(outputData);
//                if(password.equals(outputStr)){
//                    return true;
//                }
//                else {
//                    log.error("Password wrong");
//                    return false;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;

    }

    public Message login(String requestAuthCode) throws ApiException, SQLException {
        Message res= new Message();
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_GET_USER_INFO_BYCODE);
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(requestAuthCode);
        Map<String,Object> data =new HashMap<>();
        OapiSnsGetuserinfoBycodeResponse response;
        try {
            response = client.execute(req,DingConstant.Login_OUT_APP_ID,DingConstant.Login_OUT_APP_SECRET);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        if(response.isSuccess()==false){
            log.error("Login authentication failed");
            res.setMessage(403,"Login authentication failed！");
        }
        else{
            log.info("Login authentication successed！");
            ArrayList<LoginInfoView> user= new ArrayList<>();
            LoginInfoView information= getDataFromSql(response.getUserInfo().getUnionid());
            user.add(information);
            data.put("list",user);
            data.put("total",1);
            String token="";
            try{
                token= CreateToken(information);
            }
            catch (Exception e){
                log.error("Token creating was failed!");
            }

            data.put("token",token);
            res.setMessage(200,"Login authentication successed！",data);
        }
        return res;
    }

    public static Message adminlogin(String req, String name, String Gh){
        String username = "admin";
        String password = "admin";

        Message res= new Message();
        Map<String,Object> data =new HashMap<>();
        if(verifyAdminInfo(username,password)==false){
            log.error("Admin Login failed！");
            res.setMessage(403,"Admin Login failed！");
        }
        else{
            res.setCode(200);
            res.setMsg("Admin Login successed！");
            log.info("Admin Login successed！");
            ArrayList<LoginInfoView> user= new ArrayList<>();
            if(req==null){
                LoginInfoView information=new LoginInfoView("0",username,"Admin");
                user.add(information);
                data.put("list",user);
                data.put("total",1);
                String token="";
                try{
                    token= CreateToken(information);
                }
                catch (Exception e){
                    log.error("Token creating was failed!");
                }
                data.put("token",token);
                res.setMessage(200,req + " Login successed！",data);
            } else{
                username = name;
                LoginInfoView information=new LoginInfoView(Gh,username,req);
                user.add(information);
                data.put("list",user);
                data.put("total",1);
                String token="";
                try{
                    token= CreateToken(information);
                }
                catch (Exception e){
                    log.error("Token creating was failed!");
                }
                data.put("token",token);
                res.setMessage(200,req + " Login successed！",data);
            }
        }
        return res;
    }

    /**
     * 获取token
     */
    private static String getToken (){
        DefaultDingTalkClient client = new
                DefaultDingTalkClient(URL_GET_TOKKEN);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(DingConstant.APP_KEY);
        request.setAppsecret(DingConstant.APP_SECRET);
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

}
