package com.harmonycloud.util.ExportDataToProject;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
    private static Connection conn;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://10.1.11.89/harmage?useUnicode=true&characterEncoding=UTF-8","root","123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        DBUtils.conn = conn;
    }
}