package com.harmonycloud.bean.account;

import java.util.Date;

public class Authority {
    private Long id;
    private String authorityName;
    private String authorityValue;
    private Date createTime;
    private Date updateTime;

    public Authority(){ }

    public Authority(Long id, String authorityName, String authorityValue, Date createTime, Date updateTime) {
        this.id = id;
        this.authorityName = authorityName;
        this.authorityValue = authorityValue;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityValue() {
        return authorityValue;
    }

    public void setAuthorityValue(String authorityValue) {
        this.authorityValue = authorityValue;
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
