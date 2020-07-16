package com.harmonycloud.service;

import com.harmonycloud.bean.contract.ContractFileView;
import com.harmonycloud.bean.project.ProjectFileView;
import com.harmonycloud.bean.project.ProjectStatusBean;

import java.util.List;
import java.util.Set;

public interface ProjectFileService {

    String selectProjectName(Integer id);

    List<ProjectFileView> listAllProjectFile(Integer projectId);

    ProjectFileView selectByFileId(Integer fileId);

    ProjectFileView selectByInfo(Integer id, String newName, String oldName);

    Integer insertFile(Integer id, String fileType, String fileProjStage, String fileMilestoneName, String oldName, String newName, String fileSavePath, String fileUrl);

    Integer deleteFile(Integer fileId);

    Set<String> selectNewName(Integer projectId);

    List<ProjectStatusBean> getStopProjectDay(String status);

    int updateStopStatusForProject(int projectId, String status);

    Integer insertContractFile(ContractFileView contractFileView);

    ContractFileView selectContractFileById(Integer id);
}
