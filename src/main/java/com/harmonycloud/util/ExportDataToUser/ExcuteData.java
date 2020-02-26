package com.harmonycloud.util.ExportDataToUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExcuteData {
    private PreparedStatement pstmt;
    public boolean ExcuData(String sql) {
        Connection conn = DBUtils.getConn();
        boolean flag=false;
        try {
            pstmt = conn.prepareStatement(sql);
            flag=pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}