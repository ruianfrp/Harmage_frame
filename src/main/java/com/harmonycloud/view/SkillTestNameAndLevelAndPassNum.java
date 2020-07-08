package com.harmonycloud.view;

import java.util.List;

public class SkillTestNameAndLevelAndPassNum {
    private String skillTestName;
    private String skillTestLv;
    private Long passNum;
    private List<SkillTestNameAndLevelAndPassNum> skillTestNameAndLevelAndPassNums;

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

    public Long getPassNum() {
        return passNum;
    }

    public void setPassNum(Long passNum) {
        this.passNum = passNum;
    }

    public List<SkillTestNameAndLevelAndPassNum> getSkillTestNameAndLevelAndPassNums() {
        return skillTestNameAndLevelAndPassNums;
    }

    public void setSkillTestNameAndLevelAndPassNums(List<SkillTestNameAndLevelAndPassNum> skillTestNameAndLevelAndPassNums) {
        this.skillTestNameAndLevelAndPassNums = skillTestNameAndLevelAndPassNums;
    }
}
