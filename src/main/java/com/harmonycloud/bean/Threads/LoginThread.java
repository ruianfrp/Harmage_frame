package com.harmonycloud.bean.Threads;

import com.harmonycloud.bean.LoginMethod;
import com.harmonycloud.bean.Message;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class LoginThread extends Thread {
    public static String requestAuthCode;
    public static Message res;

    public LoginThread(String request) {
        requestAuthCode = request;
    }

    @Override
    public void run() {
        LoginMethod loginMethod = new LoginMethod();
        try {
            res = loginMethod.login(requestAuthCode);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
