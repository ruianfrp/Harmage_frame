package com.harmonycloud.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

public class TestSkillListView {
    private Long skillTestId;
    private String fkEmployeeGh;
    private String skillTestName;
    private String skillTestLv;
    private Date createTime;
    private Date updateTime;

    public String getFkEmployeeGh() {
        return fkEmployeeGh;
    }

    public void setFkEmployeeGh(String fkEmployeeGh) {
        this.fkEmployeeGh = fkEmployeeGh;
    }

    public Long getSkillTestId() {
        return skillTestId;
    }

    public void setSkillTestId(Long skillTestId) {
        this.skillTestId = skillTestId;
    }

    public String getSkillTestName() {
        return skillTestName;
    }

    public void setSkillTestName(String skillTestName) {
        this.skillTestName = skillTestName;
    }

    public String getSkillTestLv() {
        return skillTestLv;
    }

    public void setSkillTestLv(String skillTestLv) {
        this.skillTestLv = skillTestLv;
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
