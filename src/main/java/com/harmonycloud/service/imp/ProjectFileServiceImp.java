package com.harmonycloud.service.imp;

import com.harmonycloud.bean.project.ProjectFileView;
import com.harmonycloud.bean.project.ProjectStatusBean;
import com.harmonycloud.dao.ProjectDao;
import com.harmonycloud.dao.ProjectFileDao;
import com.harmonycloud.service.ProjectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProjectFileServiceImp implements ProjectFileService {
    @Autowired
    ProjectFileDao projectFileDao;

    @Autowired
    ProjectDao projectDao;

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

    @Override
    public List<ProjectStatusBean> getStopProjectDay(String status) {
        return projectDao.getStopProjectDay(status);
    }

    @Override
    public int updateStopStatusForProject(int projectId, String status) {
        return projectDao.updateStopStatusForProject(projectId, status);
    }
}
