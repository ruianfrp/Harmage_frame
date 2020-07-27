package com.harmonycloud.service.imp;

import com.harmonycloud.bean.account.Admin;
import com.harmonycloud.bean.contract.Contract;
import com.harmonycloud.bean.contract.ContractListView;
import com.harmonycloud.bean.contract.ContractReceivedView;
import com.harmonycloud.bean.contract.ContractStep;
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
    public List<ContractListView> listContract(String employeeGh){
        return contractDao.listContract(employeeGh);
    }

    @Override
    public List<ContractListView> listAllContract(){
        return contractDao.listAllContract();
    }

    @Override
    public List<ContractListView> selectContractByIndustry(String sales){
        return contractDao.selectContractByIndustry(sales);
    }

    @Override
    public List<ContractReceivedView> selectReceived(){
        return contractDao.selectReceived();
    }

    @Override
    public List<ContractStep> listContractStep(Integer projId){
        return contractDao.listContractStep(projId);
    }

    @Override
    public Integer insertContract(Contract contract){
        return contractDao.insertContract(contract);
    }

    @Override
    public Integer insertContractStep(List<ContractStep> contractSteps){
        return contractDao.insertContractStep(contractSteps);
    }

    @Override
    public Integer deleteContract(Integer id){
        return contractDao.deleteContract(id);
    }

    @Override
    public Integer deleteContractStep(Integer id){
        return contractDao.deleteContractStep(id);
    }

    @Override
    public Integer updateContract(Contract contract){
        return contractDao.updateContract(contract);
    }

    @Override
    public Integer updateContractStep(ContractStep contractStep){
        return contractDao.updateContractStep(contractStep);
    }

    @Override
    public Integer updateFile(Integer contractId,Integer contractStepId,Integer updateType){
        return contractDao.updateFile(contractId, contractStepId, updateType);
    }
}