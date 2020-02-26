package com.harmonycloud.entity;

import java.util.Date;

public class Dictionary {
    private Long id;

    private String dictKey;

    private String dictValue;

    private Date createTime;

    private Date updateTime;

    public Dictionary(Long id, String dictKey, String dictValue, Date createTime, Date updateTime) {
        this.id = id;
        this.dictKey = dictKey;
        this.dictValue = dictValue;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Dictionary() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey == null ? null : dictKey.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
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