package com.harmonycloud.view;

public class EmployeeCountView {
    private Integer lastNumMonth;
    private Integer beginCountPerson;
    private Integer endCountPerson;
    private Integer insertNumPerson;
    private Integer quitNumPerson;
    private String monthStartTime;
    private String monthEndTime;

    public String getMonthStartTime() {
        return monthStartTime;
    }

    public void setMonthStartTime(String monthStartTime) {
        this.monthStartTime = monthStartTime;
    }

    public String getMonthEndTime() {
        return monthEndTime;
    }

    public void setMonthEndTime(String monthEndTime) {
        this.monthEndTime = monthEndTime;
    }

    public Integer getLastNumMonth() {
        return lastNumMonth;
    }

    public void setLastNumMonth(Integer lastNumMonth) {
        this.lastNumMonth = lastNumMonth;
    }

    public Integer getBeginCountPerson() {
        return beginCountPerson;
    }

    public void setBeginCountPerson(Integer beginCountPerson) {
        this.beginCountPerson = beginCountPerson;
    }

    public Integer getEndCountPerson() {
        return endCountPerson;
    }

    public void setEndCountPerson(Integer endCountPerson) {
        this.endCountPerson = endCountPerson;
    }

    public Integer getInsertNumPerson() {
        return insertNumPerson;
    }

    public void setInsertNumPerson(Integer insertNumPerson) {
        this.insertNumPerson = insertNumPerson;
    }

    public Integer getQuitNumPerson() {
        return quitNumPerson;
    }

    public void setQuitNumPerson(Integer quitNumPerson) {
        this.quitNumPerson = quitNumPerson;
    }
}
