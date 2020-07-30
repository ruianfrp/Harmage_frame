package com.harmonycloud.bean.contract;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ContractFileView {
    private Integer id;  //id
    private Integer fkContractId;  //合同id  必填
    private Integer fkContractStepId;  //合同阶段id  非必填
    private String fileType;  //合同类型（验收报告、回款证明）  非必填
    private String fileOldName;  //合同文件名称
    private String fileNewName;  //重命名后的名字
    private String contractPath;  //合同文件路径
    private String contractUrl;  //合同文件Url（加密后无用）
    private Date createTime;
    private Date updateTime;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

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
