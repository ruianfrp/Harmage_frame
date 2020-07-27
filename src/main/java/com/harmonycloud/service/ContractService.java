package com.harmonycloud.service;

import com.harmonycloud.bean.contract.Contract;
import com.harmonycloud.bean.contract.ContractListView;
import com.harmonycloud.bean.contract.ContractReceivedView;
import com.harmonycloud.bean.contract.ContractStep;

import java.util.List;

public interface ContractService {
    List<ContractListView> listContract(String employeeGh);

    List<ContractReceivedView> selectReceived();

    List<ContractListView> listAllContract();

    List<ContractListView> selectContractByIndustry(String sales);

    List<ContractStep> listContractStep(Integer projId);

    Integer insertContract(Contract contract);

    Integer insertContractStep(List<ContractStep> contractSteps);

    Integer deleteContract(Integer id);

    Integer deleteContractStep(Integer id);

    Integer updateContract(Contract contract);

    Integer updateContractStep(ContractStep contractStep);

    Integer updateFile(Integer contractId,Integer contractStepId,Integer updateType);
}