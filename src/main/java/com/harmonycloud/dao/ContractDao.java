package com.harmonycloud.dao;

import com.harmonycloud.bean.contract.Contract;
import com.harmonycloud.bean.contract.ContractListView;
import com.harmonycloud.bean.contract.ContractReceivedView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractDao {

    List<ContractListView> listContract(String employeeGh);

    List<ContractListView> listAllContract();

    List<ContractListView> selectContractByIndustry(String sales);

    List<ContractReceivedView> selectReceived();

    Integer insertContract(Contract contract);

    Integer deleteContract(Integer id);

    Integer updateContract(Contract contract);

}