package com.harmonycloud.bean.enumeration;

public enum ProjectStatus {
    RESTART("RESTART"),STOP("STOP");

    private String status;

    ProjectStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
