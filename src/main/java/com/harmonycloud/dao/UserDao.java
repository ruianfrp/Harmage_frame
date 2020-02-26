package com.harmonycloud.dao;

import com.harmonycloud.entity.Authority;
import com.harmonycloud.view.UserListView;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserDao {
    List<UserListView> listUser(String selectEmployeeGh,String selectEmployeeName);

    void deleteUser(String employeeGh);

    String selectByfkEmployeeGhInEmployee(String fkEmployeeGh);

    void insertUser(String employeeGh, String authority, String salesIndustry);

    Integer selectIdByEmployeeGh(String employeeGh);

    List<Authority> selectAuthority();
}