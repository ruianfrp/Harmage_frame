package com.harmonycloud.service.imp;

import com.harmonycloud.bean.account.LoginInfoView;
import com.harmonycloud.dao.LoginInfoDao;
import com.harmonycloud.service.LoginInfoService;
import com.harmonycloud.bean.account.AdminLoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginInfoServiceImp implements LoginInfoService {
    @Autowired
    LoginInfoDao loginInfoDao;

    @Override
    public LoginInfoView loginInfo(String unionid_dingding) {
        return loginInfoDao.loginInfo(unionid_dingding);
    }

    @Override
    public AdminLoginView adminLogin(String account) {
        return loginInfoDao.adminLogin(account);
    }

    @Override
    public String selectUserAuthority(String employeeGh){
        return loginInfoDao.selectUserAuthority(employeeGh);
    }

    @Override
    public Integer selectIfPm(String employeeGh){
        return loginInfoDao.selectIfPm(employeeGh);
    }

    @Override
    public Integer selectUserAuthorityNumber(String employeeGh){
        return loginInfoDao.selectUserAuthorityNumber(employeeGh);
    }

    @Override
    public Integer selectIfHaveName(String employeeGh){
        return loginInfoDao.selectIfHaveName(employeeGh);
    }

    @Override
    public String selectNameByGh(String employeeGh){
        return loginInfoDao.selectNameByGh(employeeGh);
    }

    @Override
    public Integer selectApplyWait(){
        return loginInfoDao.selectApplyWait();
    }

    @Override
    public Integer selectLeaveWait(){
        return loginInfoDao.selectLeaveWait();
    }

    @Override
    public Integer selectProjectEndWait(){
        return loginInfoDao.selectProjectEndWait();
    }

    @Override
    public Integer selectApplicantAgreeWait(String employeeGh){
        return loginInfoDao.selectApplicantAgreeWait(employeeGh);
    }

    @Override
    public Integer selectOwnerAgreeWait(String employeeGh){
        return loginInfoDao.selectOwnerAgreeWait(employeeGh);
    }

    @Override
    public Integer insertAdmin(String password){
        return loginInfoDao.insertAdmin(password);
    }
}
