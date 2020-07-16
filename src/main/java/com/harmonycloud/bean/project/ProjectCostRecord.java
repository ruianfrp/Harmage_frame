package com.harmonycloud.bean.project;

import java.util.Date;

public class ProjectCostRecord {
    private long id;
    private long fkProjectId;
    private double amount;
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(long fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
