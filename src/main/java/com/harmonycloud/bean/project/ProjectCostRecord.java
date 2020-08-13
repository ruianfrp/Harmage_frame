package com.harmonycloud.bean.project;

import java.util.Date;

public class ProjectCostRecord {
    private long id;
    private long fkProjectId;
    private double money;
    private Date importTime;

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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

}
