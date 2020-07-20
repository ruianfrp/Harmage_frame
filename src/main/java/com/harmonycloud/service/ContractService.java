package com.harmonycloud.service;

import com.harmonycloud.bean.contract.Contract;

import java.util.List;

public interface ContractService {
    List<Contract> listContract(Integer projectId, Integer customerId);

    Integer insertContract(Contract contract);

    Integer deleteContract(Integer id);

    Integer updateContract(Contract contract);
}