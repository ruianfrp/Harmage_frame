package com.harmonycloud.bean.Threads;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserGetOrgUserCountRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetOrgUserCountResponse;
import com.harmonycloud.config.DingConstant;
import com.harmonycloud.util.SyncInfo;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

import static com.harmonycloud.config.URLConstant.URL_GET_COUNT_USER;
import static com.harmonycloud.config.URLConstant.URL_GET_TOKKEN;

/**
 * @author ：frp
 * @date ：Created in 2019/8/26 17:12
 * 同步策略  同步线程
 */

@Slf4j
public class SynchroThread extends Thread {
    public SynchroThread() {
    }

    @Override
    public void run() {
//        try {
//            Connection con = DriverManager.getConnection("jdbc:mysql://47.92.161.179:31036/harmage?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
//                    "root", "Ab@123456");
//            String sql = "select count(*) from employee where is_quit=0;";
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            if (rs.next()) {
//                Long num = Long.valueOf(rs.getInt(1));
//                log.info("系统中员工数为： " + (num-2));
//                Long countUser = getCountUser();
//                log.info("钉钉中员工数为： " + countUser);
//                if((num-2) >= countUser){
//                    log.info("员工信息已为最新，无需同步");
//                }
//                else {
                    SyncInfo.LoadAllUserInfo();
                    log.info("同步成功");
//                }
//            }
//            rs.close();
//            st.close();
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    private static Long getCountUser(){
        DingTalkClient client = new DefaultDingTalkClient(URL_GET_COUNT_USER);
        OapiUserGetOrgUserCountRequest request = new OapiUserGetOrgUserCountRequest();
        request.setOnlyActive(1L);
        request.setHttpMethod("GET");
        try {
            OapiUserGetOrgUserCountResponse response = client.execute(request, getToken());
            Long count = response.getCount();
            return count;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

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