package com.harmonycloud.bean.project;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class ProjectPreSaleApply {
    private Long id;
    private Long fkProjectId;
    private String projName;
    private String projType;
    private String channel;
    private String projManager;
    private String preSaleManager;
    private String industry;
    private double projBudget;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date expectStartTime;
    private String assCustomer;
    private String assBusinessOpp;
    private String projLine;
    private String projOverview;
    private String personRqmt;
    private String schedule;
    private int status;
}
