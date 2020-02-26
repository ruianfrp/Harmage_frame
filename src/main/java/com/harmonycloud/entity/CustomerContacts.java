package com.harmonycloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerContacts {
    private Integer id;
    private Integer fkCustomerId;
    private String contactsName;
    private String contactsSex;
    private Integer contactsPosition;
    private String contactsSpecificPosition;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contactsBirthday;
    private String contactsTel;
    private String contactsWechat;
    private String contactsQq;
    private String contactsEmail;
    private String contactsMsn;
    private String contactsRemark;
    private String contactsAli;
    private Date createTime;
    private Date updateTime;

    public CustomerContacts(){}

    public CustomerContacts(Integer id, Integer fkCustomerId, String contactsName, String contactsSex, Integer contactsPosition, String contactsSpecificPosition, Date contactsBirthday, String contactsTel, String contactsWechat, String contactsQq, String contactsEmail, String contactsMsn, String contactsRemark, String contactsAli, Date createTime, Date updateTime) {
        this.id = id;
        this.fkCustomerId = fkCustomerId;
        this.contactsName = contactsName;
        this.contactsSex = contactsSex;
        this.contactsPosition = contactsPosition;
        this.contactsSpecificPosition = contactsSpecificPosition;
        this.contactsBirthday = contactsBirthday;
        this.contactsTel = contactsTel;
        this.contactsWechat = contactsWechat;
        this.contactsQq = contactsQq;
        this.contactsEmail = contactsEmail;
        this.contactsMsn = contactsMsn;
        this.contactsRemark = contactsRemark;
        this.contactsAli = contactsAli;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

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

    public Integer getContactsPosition() {
        return contactsPosition;
    }

    public void setContactsPosition(Integer contactsPosition) {
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

    public String getContactsWechat() {
        return contactsWechat;
    }

    public void setContactsWechat(String contactsWechat) {
        this.contactsWechat = contactsWechat;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
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
