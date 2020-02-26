package com.harmonycloud.entity;

import java.util.Date;

public class User {
    private Long id;

    private String fkEmployeeGh;

    private String dingtalkStuffid;

    private String userAuthority;

    private Date createTime;

    private Date updateTime;

    public User(Long id, String fkEmployeeGh, String dingtalkStuffid, String userAuthority, Date createTime, Date updateTime) {
        this.id = id;
        this.fkEmployeeGh = fkEmployeeGh;
        this.dingtalkStuffid = dingtalkStuffid;
        this.userAuthority = userAuthority;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh == null ? null : fkEmployeeGh.trim();
    }

    public String getDingtalkStuffid() {
        return dingtalkStuffid;
    }

    public void setDingtalkStuffid(String dingtalkStuffid) {
        this.dingtalkStuffid = dingtalkStuffid == null ? null : dingtalkStuffid.trim();
    }

    public String getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(String userAuthority) {
        this.userAuthority = userAuthority == null ? null : userAuthority.trim();
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