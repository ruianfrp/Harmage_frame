package com.harmonycloud.bean.customer;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerContactsListView {
    private Integer id;
    private Integer fkCustomerId;
    private String contactsName;
    private String contactsSex;
    private String contactsPosition;
    private String contactsSpecificPosition;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contactsBirthday;
    private String contactsTel;
    private String contactsWechat;
    private String contactsQq;
    private String contactsEmail;
    private String contactsMsn;
    private String contactsRemark;
    private String contactsAli;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkCustomerId() {
        return fkCustomerId;
    }

    public void setFkCustomerId(Integer fkCustomerId) {
        this.fkCustomerId = fkCustomerId;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsSex() {
        return contactsSex;
    }

    public void setContactsSex(String contactsSex) {
        this.contactsSex = contactsSex;
    }

    public String getContactsPosition() {
        return contactsPosition;
    }

    public void setContactsPosition(String contactsPosition) {
        this.contactsPosition = contactsPosition;
    }

    public String getContactsSpecificPosition() {
        return contactsSpecificPosition;
    }

    public void setContactsSpecificPosition(String contactsSpecificPosition) {
        this.contactsSpecificPosition = contactsSpecificPosition;
    }

    public Date getContactsBirthday() {
        return contactsBirthday;
    }

    public void setContactsBirthday(Date contactsBirthday) {
        this.contactsBirthday = contactsBirthday;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public String getContactsWechat() {
        return contactsWechat;
    }

    public void setContactsWechat(String contactsWechat) {
        this.contactsWechat = contactsWechat;
    }

    public String getContactsQq() {
        return contactsQq;
    }

    public void setContactsQq(String contactsQq) {
        this.contactsQq = contactsQq;
    }

    public String getContactsEmail() {
        return contactsEmail;
    }

    public void setContactsEmail(String contactsEmail) {
        this.contactsEmail = contactsEmail;
    }

    public String getContactsMsn() {
        return contactsMsn;
    }

    public void setContactsMsn(String contactsMsn) {
        this.contactsMsn = contactsMsn;
    }

    public String getContactsRemark() {
        return contactsRemark;
    }

    public void setContactsRemark(String contactsRemark) {
        this.contactsRemark = contactsRemark;
    }

    public String getContactsAli() {
        return contactsAli;
    }

    public void setContactsAli(String contactsAli) {
        this.contactsAli = contactsAli;
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
