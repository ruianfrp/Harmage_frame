package com.harmonycloud.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="skill对象",description="技能对象skill")
public class SkillListView {
    @ApiModelProperty(value="技能类别")
    private String skillDirct;
    @ApiModelProperty(value="技能名称")
    private List<SkillListView2> skillInfo;

    public String getSkillDirct() {
        return skillDirct;
    }

    public void setSkillDirct(String skillDirct) {
        this.skillDirct = skillDirct;
    }

    public List<SkillListView2> getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(List<SkillListView2> skillInfo) {
        this.skillInfo = skillInfo;
    }
}
