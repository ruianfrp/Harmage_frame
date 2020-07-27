package com.harmonycloud.bean.contract;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ContractFileView {
    private Integer id;
    private Integer fkContractId;
    private Integer fkContractStepId;
    private String fileOldName;
    private String fileNewName;
    private String contractPath;
    private String contractUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Integer getFkContractId() {
        return fkContractId;
    }

    public void setFkContractId(Integer fkContractId) {
        this.fkContractId = fkContractId;
    }

    public Integer getFkContractStepId() {
        return fkContractStepId;
    }

    public void setFkContractStepId(Integer fkContractStepId) {
        this.fkContractStepId = fkContractStepId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractPath() {
        return contractPath;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
