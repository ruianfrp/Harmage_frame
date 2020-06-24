package com.harmonycloud.service.imp;

import com.harmonycloud.bean.skill.*;
import com.harmonycloud.dao.SkillDao;
import com.harmonycloud.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
public class SkillServiceImp implements SkillService {


    @Autowired
    SkillDao skillDao;

    @Override
    public List<SkillListView> selectSkillDirct(){
        return skillDao.selectSkillDirct();
    }

    @Override
    public List<SkillListView2> listSkill(String skillDirct){return skillDao.listSkill(skillDirct);}

    @Override
    public void insertSkill(String skillDirct, String skillName){
        skillDao.insertSkill(skillDirct, skillName);
    }

    @Override
    public void deleteSkill(String skillDirct, String skillName){
        skillDao.deleteSkill(skillDirct, skillName);
    }

    @Override
    public void updateSkill(String skillDirct, String skillName, Date updateTime, Long id){
        skillDao.updateSkill(skillDirct, skillName, updateTime, id);
    }

    @Override
    public Long selectId(String skillDirct, String skillName){
        return skillDao.selectId(skillDirct, skillName);
    }

    @Override
    public List<TestSkillListView> listTestSkill(){
        return skillDao.listTestSkill();
    }

    @Override
    public void insertTestSkill(String skillTestName, String skillTestLv){
        skillDao.insertTestSkill(skillTestName, skillTestLv);
    }

    @Override
    public void deleteTestSkill(String skillTestName){
        skillDao.deleteTestSkill(skillTestName);
    }

    @Override
    public void updateTestSkill(String skillTestName, String skillTestLv, Date updateTime, Long skillTestId){
        skillDao.updateTestSkill(skillTestName, skillTestLv, updateTime, skillTestId);
    }

    @Override
    public Long selectTestSkillId(String skillTestName, String skillTestLv){
        return skillDao.selectTestSkillId(skillTestName, skillTestLv);
    }

    @Override
    public List<TestSkillListView> selectByTestId(String skillTestName){
        return skillDao.selectByTestId(skillTestName);
    }
    //
    @Override
    public List<String> selectAllSkillTestName(){
        return skillDao.selectAllSkillTestName();
    }
    //
    @Override
    public SkillTestNameAndLevelAndPassNum selectSkillTestNameAndLevelAndPassNum(String skillTestName, String skillTestLevel) {
        SkillTestNameAndLevelAndPassNum skillTestNameAndLevelAndPassNum = skillDao.selectSkillTestNameAndLevelAndPassNum(skillTestName, skillTestLevel);
        if (skillTestNameAndLevelAndPassNum.getPassNum() == 0) {
            skillTestNameAndLevelAndPassNum.setSkillTestName(skillTestName);
            skillTestNameAndLevelAndPassNum.setSkillTestLv(skillTestLevel);
        }
        return skillTestNameAndLevelAndPassNum;
    }

    @Override
    public Long selectSkillTestPassNum(String skillTestName, String skillTestLevel) { return skillDao.selectSkillTestPassNum(skillTestName, skillTestLevel); };

    public List<SkillView> selectSkillView() {
        List<SkillView> views = skillDao.selectSkillAndCount();
        int count = 0;
        for (SkillView s : views){
            s.setInfo(skillDao.selectSkillNameAndCount(s.getSkillDirct()));
        }
        return views;
    }

}
