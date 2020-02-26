package com.harmonycloud.view;

import java.sql.Date;
import java.util.List;

public class MemberApplyListView {

    private Integer id;
    private Integer projectId;
    private String projName;
    private List<String> skillAllName;
    private Date memberStartTime;
    private Integer lastTime;
    private String applyStatus;
    private String memberRecommendName;
    private String createTime;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }


    public List<String> getSkillAllName() {
        return skillAllName;
    }

    public void setSkillAllName(List<String> skillAllName) {
        this.skillAllName = skillAllName;
    }

    public Date getMemberStartTime() {
        return memberStartTime;
    }

    public void setMemberStartTime(Date memberStartTime) {
        this.memberStartTime = memberStartTime;
    }

    public Integer getLastTime() {
        return lastTime;
    }

    public void setLastTime(Integer lastTime) {
        this.lastTime = lastTime;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getMemberRecommendName() {
        return memberRecommendName;
    }

    public void setMemberRecommendName(String memberRecommendName) {
        this.memberRecommendName = memberRecommendName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
