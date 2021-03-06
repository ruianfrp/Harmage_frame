package com.harmonycloud.dao;

import com.harmonycloud.bean.contract.ContractFileView;
import com.harmonycloud.bean.project.ProjectFileView;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectFileDao {
    String selectProjectName(Integer id);

    List<ProjectFileView> listAllProjectFile(Integer projectId);

    ProjectFileView selectByFileId(Integer fileId);

    ProjectFileView selectByInfo(Integer id, String newName, String oldName);

    Integer insertFile(Integer id, String fileType, String fileProjStage, String fileMilestoneName, String oldName, String newName, String fileSavePath, String fileUrl);

    Integer deleteFile(Integer fileId);

    Set<String> selectNewName(Integer projectId);

    Integer insertContractFile(ContractFileView contractFileView);

    ContractFileView selectContractFileById(Integer id);

}
