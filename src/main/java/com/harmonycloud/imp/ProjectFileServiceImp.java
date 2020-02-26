package com.harmonycloud.imp;

import com.harmonycloud.dao.ProjectFileDao;
import com.harmonycloud.service.ProjectFileService;
import com.harmonycloud.view.ProjectFileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProjectFileServiceImp implements ProjectFileService {
    @Autowired
    ProjectFileDao projectFileDao;

    @Override
    public String selectProjectName(Integer id) {
        return projectFileDao.selectProjectName(id);
    }

    @Override
    public List<ProjectFileView> listAllProjectFile(Integer projectId) {
        return projectFileDao.listAllProjectFile(projectId);
    }

    @Override
    public ProjectFileView selectByFileId(Integer fileId) {
        return projectFileDao.selectByFileId(fileId);
    }

    @Override
    public ProjectFileView selectByInfo(Integer id, String newName, String oldName) {
        return projectFileDao.selectByInfo(id, newName, oldName);
    }

    @Override
    public Integer insertFile(Integer id, String fileType, String fileProjStage, String fileMilestoneName, String oldName, String newName, String fileSavePath, String fileUrl) {
        return projectFileDao.insertFile(id, fileType, fileProjStage, fileMilestoneName, oldName, newName, fileSavePath, fileUrl);
    }

    @Override
    public Integer deleteFile(Integer fileId) {
        return projectFileDao.deleteFile(fileId);
    }

    @Override
    public Set<String> selectNewName(Integer projectId){
        return projectFileDao.selectNewName(projectId);
    }
}
