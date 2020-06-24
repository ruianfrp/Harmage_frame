package com.harmonycloud.controller;

import com.harmonycloud.service.AdminService;
import com.harmonycloud.util.DesEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://47.92.161.179:31036/harmage?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                "root", "Ab@123456");
        String sql5 = "insert into admin(account,fk_employee_gh,password) select ?,?,? from dual where not EXISTS (select account from admin where account=?);";
        PreparedStatement st5 = con.prepareStatement(sql5);
        st5.setString(1, "13588401944");
        DesEncryptUtil u = new DesEncryptUtil();
        String encString = u.getEncString("Ab123456");
        st5.setString(2, "0287");
        st5.setString(3, encString);
        st5.setString(4, "13588401944");
        int rs5 = st5.executeUpdate();
        if (rs5 > 0) {
            log.info("成功添加账号密码");
        }
        st5.close();
        con.close();
    }

}
