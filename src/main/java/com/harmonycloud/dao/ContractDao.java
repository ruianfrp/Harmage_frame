package com.harmonycloud.dao;

import com.harmonycloud.bean.contract.Contract;
import com.harmonycloud.bean.contract.ContractListView;
import com.harmonycloud.bean.contract.ContractReceivedView;
import com.harmonycloud.bean.contract.ContractStep;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractDao {

    List<ContractListView> listContract(String employeeGh);

    List<ContractListView> listAllContract();

    List<ContractListView> selectContractByIndustry(String sales);

    List<ContractReceivedView> selectReceived();

    List<ContractStep> listContractStep(Integer projId);

    Integer insertContract(Contract contract);

    Integer insertContractStep(List<ContractStep> contractSteps);

    Integer deleteContract(Integer id);

    Integer deleteContractStep(Integer id);

    Integer updateContract(Contract contract);

    Integer updateContractStep(ContractStep contractStep);

    Integer updateFile(Integer contractId,Integer contractStepId,Integer updateType);

}