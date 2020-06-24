package com.harmonycloud.bean.project;

public class ProjectFileView {
    private Integer fileId;
    private Integer fkProjectId;
    private String fileOldName;
    private String fileNewName;
    private String fileType;
    private String fileProjStage;
    private String fileMilestoneName;
    private String fileSavePath;
    private String projName;
    private String projLine;
    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileProjStage() {
        return fileProjStage;
    }

    public void setFileProjStage(String fileProjStage) {
        this.fileProjStage = fileProjStage;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjLine() {
        return projLine;
    }

    public void setProjLine(String projLine) {
        this.projLine = projLine;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(Integer fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFileOldName() {
        return fileOldName;
    }

    public void setFileOldName(String fileOldName) {
        this.fileOldName = fileOldName;
    }

    public String getFileNewName() {
        return fileNewName;
    }

    public void setFileNewName(String fileNewName) {
        this.fileNewName = fileNewName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileMilestoneName() {
        return fileMilestoneName;
    }

    public void setFileMilestoneName(String fileMilestoneName) {
        this.fileMilestoneName = fileMilestoneName;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }
}