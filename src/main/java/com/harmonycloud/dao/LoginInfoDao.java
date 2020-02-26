package com.harmonycloud.dao;

import com.harmonycloud.view.AdminLoginView;
import com.harmonycloud.view.LoginInfoView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginInfoDao {
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