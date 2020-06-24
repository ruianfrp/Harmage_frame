package com.harmonycloud.bean.skill;

import java.util.List;

public class SkillView {

    String skillDirct;
    int skillDirctCount;
    List<SkillView> info;

    public SkillView() {
    }

    public SkillView(String skillDirct, int skillDirctCount, List<SkillView> info) {
        this.skillDirct = skillDirct;
        this.skillDirctCount = skillDirctCount;
        this.info = info;
    }

    public String getSkillDirct() {
        return skillDirct;
    }

    public void setSkillDirct(String skillDirct) {
        this.skillDirct = skillDirct;
    }

    public int getSkillDirctCount() {
        return skillDirctCount;
    }

    public void setSkillDirctCount(int skillDirctCount) {
        this.skillDirctCount = skillDirctCount;
    }

    public List<SkillView> getInfo() {
        return info;
    }

    public void setInfo(List<SkillView> info) {
        this.info = info;
    }
}
