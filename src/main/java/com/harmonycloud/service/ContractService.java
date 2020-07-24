package com.harmonycloud.service;

import com.harmonycloud.bean.contract.Contract;
import com.harmonycloud.bean.contract.ContractListView;
import com.harmonycloud.bean.contract.ContractReceivedView;

import java.util.List;

public interface ContractService {
    List<ContractListView> listContract(String employeeGh);

    List<ContractReceivedView> selectReceived();

    List<ContractListView> listAllContract();

    List<ContractListView> selectContractByIndustry(String sales);

    Integer insertContract(Contract contract);

    Integer deleteContract(Integer id);

    Integer updateContract(Contract contract);
}