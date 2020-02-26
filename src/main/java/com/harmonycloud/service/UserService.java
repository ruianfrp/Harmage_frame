package com.harmonycloud.service;

import com.harmonycloud.entity.Authority;
import com.harmonycloud.view.UserListView;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
public interface UserService {

    List<UserListView> listUser(String selectEmployeeGh,String selectEmployeeName);

    void deleteUser(String employeeGh);

    String selectByfkEmployeeGhInEmployee(String fkEmployeeGh);

    void insertUser(String employeeGh,String authority, String salesIndustry);

    Integer selectIdByEmployeeGh(String employeeGh);

    List<Authority> selectAuthority();
}
