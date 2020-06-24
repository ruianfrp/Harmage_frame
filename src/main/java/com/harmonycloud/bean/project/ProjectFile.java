package com.harmonycloud.bean.project;

public class ProjectFile {
    private Integer fileId;
    private Integer fkProjectId;
    private String fileName;
    private String fileType;
    private String fileMilestoneName;
    private String fileSavePath;

    public ProjectFile() {
    }

    public ProjectFile(Integer fileId, Integer fkProjectId, String fileName, String fileType, String fileMilestoneName, String fileSavePath) {
        this.fileId = fileId;
        this.fkProjectId = fkProjectId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileMilestoneName = fileMilestoneName;
        this.fileSavePath = fileSavePath;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
