package com.harmonycloud.service.imp;

import com.harmonycloud.dao.AdminDao;
import com.harmonycloud.bean.account.Admin;
import com.harmonycloud.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Override
    public int insert(Admin admin) {
        return adminDao.insert(admin);
    }
}