package com.harmonycloud.dao;

import com.harmonycloud.bean.contract.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractDao {

    List<ContractListView> listContract(String employeeGh);

    List<ContractListView> listAllContract();

    List<ContractListView> selectContractByIndustry(String sales);

    List<ContractReceivedView> selectReceived();

    ContractFileView selectContractFile(Integer id);

    ContractFileView selectContractFileByProjId(Integer projId);

    List<ContractStep> listContractStep(Integer projId);

    ContractFileView selectContractStepFile(Integer id, String fileType);

    Integer insertContract(Contract contract);

    Integer insertContractStep(List<ContractStep> contractSteps);

    Integer deleteContract(Integer id);

    Integer deleteContractStep(Integer id);

    Integer updateContract(Contract contract);

    Integer updateContractStep(ContractStep contractStep);

    Integer updateFile(String uploadFileType,Integer contractStepId);

    List<Integer> listPaymentDone(Integer contractStepId);

    Integer finashProj(Integer contractStepId);

    List<ExcelContractView> selectAllContract();

    List<ExcelContractStepView> selectAllContractStep(Integer contractId);

}