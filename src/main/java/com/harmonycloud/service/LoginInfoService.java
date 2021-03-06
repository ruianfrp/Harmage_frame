package com.harmonycloud.service;

import com.harmonycloud.bean.account.AdminLoginView;
import com.harmonycloud.bean.account.LoginInfoView;

import java.util.List;

public interface LoginInfoService {
    LoginInfoView loginInfo(String unionid_dingding);

    AdminLoginView adminLogin(String account);

    String selectUserAuthority(String employeeGh);

    Integer selectIfPm(String employeeGh);

    Integer selectUserAuthorityNumber(String employeeGh);

    Integer selectIfHaveName(String employeeGh);

    String selectNameByGh(String employeeGh);

    Integer selectApplyWait();

    Integer selectLeaveWait();

    Integer selectProjectEndWait();

    Integer selectApplicantAgreeWait(String employeeGh);

    Integer selectOwnerAgreeWait(String employeeGh);

    Integer insertAdmin(String password);
}
