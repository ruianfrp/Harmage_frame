package com.harmonycloud.controller;


import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.skill.SkillListView;
import com.harmonycloud.bean.skill.SkillTestNameAndLevelAndPassNum;
import com.harmonycloud.bean.skill.SkillView;
import com.harmonycloud.bean.skill.TestSkillListView;
import com.harmonycloud.service.SkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

/**
 * @author ：frp
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="技能controller",tags={"技能操作接口"})
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    SkillService skillService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 返回普通技能列表
     *
     * @return message
     * @throws Exception
     */
    @GetMapping("/listSkill")
    @ApiOperation(value = "返回普通技能列表")
    public Message listSkill() throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<SkillListView> listSkill = skillService.selectSkillDirct();
        if (listSkill != null) {
            for (SkillListView skillListView : listSkill) {
                skillListView.setSkillInfo(skillService.listSkill(skillListView.getSkillDirct()));
            }
            log.info("Skill信息返回成功");
            data.put("list", listSkill);
            data.put("total", listSkill.size());
            res.message.setMessage(200, "Skill信息返回成功", data);
        } else {
            log.error("返回错误，Skill数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 返回考核技能列表
     *
     * @return message
     * @throws Exception
     */
    @GetMapping("/listTestSkill")
    @ApiOperation(value = "返回考核技能列表")
    public Message<TestSkillListView> listTestSkill() throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<TestSkillListView> listTestSkill = skillService.listTestSkill();
        if (listTestSkill != null) {
            log.info("TestSkill信息返回成功");
            data.put("list", listTestSkill);
            data.put("total", listTestSkill.size());
            res.message.setMessage(200, "TestSkill信息返回成功", data);
        } else {
            log.error("返回错误，TestSkill数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 新增普通技能
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @PostMapping("/insertSkill")
    @ApiOperation(value = "新增普通技能信息")
    public Message insertSkill(@RequestBody @ApiParam(name = "skillDirct\nskillName", value = "普通技能信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String skillDirct = (String) map.get("skillDirct");
        String skillName = (String) map.get("skillName");
        Long id = skillService.selectId(skillDirct, skillName);
        if (id == null) {
            skillService.insertSkill(skillDirct, skillName);
            log.info("普通技能信息增加成功");
            res.message.setMessage(200, "普通技能信息增加成功");
        } else {
            log.error("已存在此普通技能，无法增加");
            res.message.setMessage(400, "已存在此普通技能，无法增加");
        }
        return res.message;
    }

    /**
     * 新增考核技能信息
     *
     * @param testSkillListView
     * @return message
     * @throws Exception
     */
    @PostMapping("/insertTestSkill")
    @ApiOperation(value = "新增考核技能信息")
    public Message insertTestSkill(@RequestBody @ApiParam(name = "skillTestName\nskillTestLv", value = "考核技能信息list") List<TestSkillListView> testSkillListView) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        if (testSkillListView == null) {
            log.error("返回数据为空，无法增加");
            res.message.setMessage(400, "返回数据为空，无法增加");
        } else {
            for (short i = 0; i < testSkillListView.size(); i++) {
                Long skillTestId = skillService.selectTestSkillId(testSkillListView.get(i).getSkillTestName(), testSkillListView.get(i).getSkillTestLv());
                if (skillTestId == null) {
                    skillService.insertTestSkill(testSkillListView.get(i).getSkillTestName(), testSkillListView.get(i).getSkillTestLv());
                    log.info("考核技能信息增加成功");
                    res.message.setMessage(200, "考核技能信息增加成功");
                } else {
                    log.error("已存在此考核技能，无法增加");
                    res.message.setMessage(400, "已存在此考核技能，无法增加");
                }
            }
        }
        return res.message;
    }

    /**
     * 删除普通技能
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @DeleteMapping("/deleteSkill")
    @ApiOperation(value = "删除普通技能信息")
    public Message deleteSkill(@RequestBody @ApiParam(name = "skillDirct\nskillName", value = "普通技能信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String skillDirct = (String) map.get("skillDirct");
        String skillName = (String) map.get("skillName");
        Long id = skillService.selectId(skillDirct, skillName);
        if (id == null) {
            log.error("此普通技能不存在，无法删除");
            res.message.setMessage(400, "此普通技能不存在，无法删除");
        } else {
            skillService.deleteSkill(skillDirct, skillName);
            log.info("普通技能删除成功");
            res.message.setMessage(200, "普通技能删除成功");
        }
        return res.message;
    }

    /**
     * 删除考核技能信息
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @DeleteMapping("/deleteTestSkill")
    @ApiOperation(value = "删除考核技能信息")
    public Message deleteTestSkill(@RequestBody @ApiParam(name = "skillTestName", value = "考核技能名称") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        if (map == null) {
            log.error("返回数据为空，无法删除");
            res.message.setMessage(400, "返回数据为空，无法删除");
        } else {

            String skillTestName = (String) map.get("skillTestName");
            List<TestSkillListView> listTestSkill = skillService.selectByTestId(skillTestName);
            if (listTestSkill == null) {
                log.error("此考核技能不存在，无法删除");
                res.message.setMessage(400, "此考核技能不存在，无法删除");
            } else {
                skillService.deleteTestSkill(skillTestName);
                log.info("考核技能删除成功");
                res.message.setMessage(200, "考核技能删除成功");
            }

        }
        return res.message;
    }

    /**
     * 修改普通技能
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @PostMapping("/updateSkill")
    @ApiOperation(value = "修改普通技能信息", notes = "skillDirct、skillName 可为空 updateTime、id 不能为空")
    public Message updateSkill(@RequestBody @ApiParam(name = "skillDirct\nskillName\nupdateTime\nid", value = "普通技能信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Date updateTime = (Date) map.get("updateTime");
        Long id = (Long) map.get("id");
        if (id == null) {
            log.error("普通技能id不得为空");
            res.message.setMessage(400, "普通技能id不得为空");
        } else if (updateTime == null) {
            log.error("修改时间不得为空");
            res.message.setMessage(400, "修改时间不得为空");
        } else {
            skillService.updateSkill((String) map.get("skillDirct"), (String) map.get("skillName"), updateTime, id);
            log.info("普通技能信息修改成功");
            res.message.setMessage(200, "普通技能信息修改成功");
        }
        return res.message;
    }

    /**
     * 修改考核技能信息
     *
     * @param map
     * @return message
     * @throws Exception
     */
    @PostMapping("/updateTestSkill")
    @ApiOperation(value = "修改考核技能信息", notes = "skillTestName、skillTestLv 可为空 updateTime、skillTestId 不能为空")
    public Message updateTestSkill(@RequestBody @ApiParam(name = "skillTestName\nskillTestLv\nupdateTime\nskillTestId", value = "考核技能信息") Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Date updateTime = (Date) map.get("updateTime");
        Long skillTestId = (Long) map.get("skillTestId");
        if (skillTestId == null) {
            log.error("考核技能id不得为空");
            res.message.setMessage(400, "考核技能id不得为空");
        } else if (updateTime == null) {
            log.error("修改时间不得为空");
            res.message.setMessage(400, "修改时间不得为空");
        } else {
            skillService.updateTestSkill((String) map.get("skillTestName"), (String) map.get("skillTestLv"), updateTime, skillTestId);
            log.info("考核技能信息修改成功");
            res.message.setMessage(200, "考核技能信息修改成功");
        }
        return res.message;
    }

    /**
     * 返回员工通过考核的人数
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/employeePassSkillTestNum")
    @ApiOperation(value = "返回通过测试技能的人数以及对应各个技能的人数")
    public Message employeePassSkillTestNum() throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String[] skillTestLevel = {"初级", "中级", "高级"};
        List<String> allSkillTestName = skillService.selectAllSkillTestName();
        List<SkillTestNameAndLevelAndPassNum> subData = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        long sumPassNum = 0;
        long everyNum = 0;
        long everyPassNum = 0;
        if (allSkillTestName != null) {
            for (String skillName : allSkillTestName) {
                SkillTestNameAndLevelAndPassNum nameList = new SkillTestNameAndLevelAndPassNum();
                List<SkillTestNameAndLevelAndPassNum> lvAndNumDatas = new ArrayList<>();
                everyPassNum = 0;
                for (String skillLevel : skillTestLevel) {
                    SkillTestNameAndLevelAndPassNum lvAndNum = new SkillTestNameAndLevelAndPassNum();
                    everyNum = skillService.selectSkillTestPassNum(skillName, skillLevel);
                    lvAndNum.setSkillTestName(skillName);
                    lvAndNum.setSkillTestLv(skillLevel);
                    lvAndNum.setPassNum(everyNum);
                    everyPassNum += everyNum;
                    sumPassNum += everyNum;
                    lvAndNumDatas.add(lvAndNum);
                }

                nameList.setSkillTestName(skillName);
                nameList.setPassNum(everyPassNum);
                nameList.setSkillTestNameAndLevelAndPassNums(lvAndNumDatas);
                subData.add(nameList);
            }

            log.info("skill信息返回成功");
            data.put("sumNum", sumPassNum);
            data.put("list", subData);
            res.message.setMessage(200, "Skill信息返回成功", data);
        } else {
            log.error("返回错误，Skill数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }

    /**
     * 获取技能图
     *
     * @return message
     * @throws Exception
     */
    @GetMapping("/selectSkillView")
    @ApiOperation(value = "返回技能图")
    public Message selectSkillView() throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        Map<String, Object> data = new HashMap<>();
        List<SkillView> skillViews = skillService.selectSkillView();
        if (skillViews != null) {
            log.info("Skill信息返回成功");
            data.put("list", skillViews);
            int total = 0;
            for (SkillView s : skillViews) {
                total = total + s.getSkillDirctCount();
            }
            data.put("total", total);
            res.message.setMessage(200, "Skill信息返回成功", data);
        } else {
            log.error("返回错误，Skill数据为空");
            res.message.setMessage(400, "数据为空");
        }
        return res.message;
    }
}
