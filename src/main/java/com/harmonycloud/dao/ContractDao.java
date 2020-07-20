package com.harmonycloud.dao;

import com.harmonycloud.bean.contract.Contract;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractDao {

    List<Contract> listContract(Integer projectId, Integer customerId);

    Integer insertContract(Contract contract);

    Integer deleteContract(Integer id);

    Integer updateContract(Contract contract);

}