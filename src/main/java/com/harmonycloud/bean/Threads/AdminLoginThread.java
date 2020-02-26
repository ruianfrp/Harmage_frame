package com.harmonycloud.bean.Threads;

import com.harmonycloud.bean.LoginMethod;
import com.harmonycloud.bean.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AdminLoginThread extends Thread {
    public static Message res;
    public static String req;
    public static String Gh;
    public static String employeeName;

    public AdminLoginThread(String request,String name,String employeeGh){
        req = request;
        employeeName = name;
        Gh = employeeGh;
    }

    @Override
    public void run(){
        res = LoginMethod.adminlogin(req,employeeName,Gh);
    }
}
