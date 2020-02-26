package com.harmonycloud.imp;

import com.harmonycloud.dao.UserDao;
import com.harmonycloud.entity.Authority;
import com.harmonycloud.service.UserService;
import com.harmonycloud.view.UserListView;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<UserListView> listUser(String selectEmployeeGh,String selectEmployeeName) {
        return userDao.listUser(selectEmployeeGh, selectEmployeeName);
    }

    @Override
    public void deleteUser(String employeeGh){
        userDao.deleteUser(employeeGh);
    }

    @Override
    public String selectByfkEmployeeGhInEmployee(String fkEmployeeGh){
        return userDao.selectByfkEmployeeGhInEmployee(fkEmployeeGh);
    }

    @Override
    public void insertUser(String employeeGh,String authority,String salesIndustry){
        userDao.insertUser(employeeGh, authority,salesIndustry);
    }

    @Override
    public Integer selectIdByEmployeeGh(String employeeGh){
        return userDao.selectIdByEmployeeGh(employeeGh);
    }

    @Override
    public List<Authority> selectAuthority(){
        return userDao.selectAuthority();
    }

}
