package com.harmonycloud.service.imp;

import com.harmonycloud.bean.account.Admin;
import com.harmonycloud.bean.contract.*;
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
    public ContractFileView selectContractFile(Integer id){
        return contractDao.selectContractFile(id);
    }

    @Override
    public ContractFileView selectContractFileByProjId(Integer projId){
        return contractDao.selectContractFileByProjId(projId);
    }

    @Override
    public List<ContractStep> listContractStep(Integer projId){
        return contractDao.listContractStep(projId);
    }

    @Override
    public ContractFileView selectContractStepFile(Integer id, String fileType){
        return contractDao.selectContractStepFile(id, fileType);
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
    public Integer updateFile(String uploadFileType,Integer contractStepId){
        return contractDao.updateFile(uploadFileType, contractStepId);
    }

    @Override
    public List<Integer> listPaymentDone(Integer contractStepId){
        return contractDao.listPaymentDone(contractStepId);
    }

    @Override
    public Integer finashProj(Integer contractStepId){
        return contractDao.finashProj(contractStepId);
    }

    @Override
    public List<ExcelContractView> selectAllContract(){
        return contractDao.selectAllContract();
    }

    @Override
    public List<ExcelContractStepView> selectAllContractStep(Integer contractId){
        return contractDao.selectAllContractStep(contractId);
    }
}