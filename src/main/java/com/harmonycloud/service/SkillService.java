package com.harmonycloud.service;

import com.harmonycloud.bean.skill.*;

import java.sql.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
public interface SkillService {
    List<SkillListView> selectSkillDirct();

    List<SkillListView2> listSkill(String skillDirct);

    void insertSkill(String skillDirct, String skillName);

    void deleteSkill(String skillDirct, String skillName);

    void updateSkill(String skillDirct, String skillName, Date updateTime, Long id);

    Long selectId(String skillDirct, String skillName);

    List<TestSkillListView> listTestSkill();

    void insertTestSkill(String skillTestName, String skillTestLv);

    void deleteTestSkill(String skillTestName);

    void updateTestSkill(String skillTestName, String skillTestLv, Date updateTime, Long skillTestId);

    Long selectTestSkillId(String skillTestName, String skillTestLv);

    List<TestSkillListView> selectByTestId(String skillTestName);
    //
    List<String> selectAllSkillTestName();
    //
    SkillTestNameAndLevelAndPassNum selectSkillTestNameAndLevelAndPassNum(String skillTestName, String skillTestLevel);
    //
    Long selectSkillTestPassNum(String skillTestName, String skillTestLevel);

    List<SkillView> selectSkillView();

}
