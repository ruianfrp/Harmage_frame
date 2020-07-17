package com.harmonycloud.service.imp;

import com.harmonycloud.bean.account.Admin;
import com.harmonycloud.bean.contract.Contract;
import com.harmonycloud.dao.AdminDao;
import com.harmonycloud.dao.ContractDao;
import com.harmonycloud.service.AdminService;
import com.harmonycloud.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImp implements ContractService {

    @Autowired
    ContractDao contractDao;

    @Override
    public List<Contract> listContract(Integer projectId, Integer customerId){
        return contractDao.listContract(projectId, customerId);
    }
}