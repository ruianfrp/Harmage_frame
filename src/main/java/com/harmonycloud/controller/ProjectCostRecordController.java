package com.harmonycloud.controller;

import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.project.ProjectCostRecord;
import com.harmonycloud.bean.project.ProjectFileView;
import com.harmonycloud.service.ProjectCostRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

@RestController
@Slf4j
public class ProjectCostRecordController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    ProjectCostRecordService projectCostRecordService;


    /*
    * 添加项目成本记录,并更新到project目前成本中proj_current_cost
    * @return message
    * */
    @PostMapping("/insertProjectCostRecord")
    @ApiOperation(value = "增加项目成本记录")
    @ResponseBody
    public ProjectCostRecord insertProjectCostRecord(@RequestBody Map map){
/*        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));*/
/*        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }*/
        ProjectCostRecord projectCostRecord = new ProjectCostRecord();
        double amount = Double.parseDouble(map.get("amount").toString());
        Integer projectId = Integer.parseInt(map.get("fk_project_id").toString());
        projectCostRecord.setAmount(amount);
        projectCostRecord.setFkProjectId(projectId);
        projectCostRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        Integer result = projectCostRecordService.insertCostRecordDataBase(projectCostRecord);
        result = projectCostRecordService.updateProjectCurrentCost(projectId,amount);
/*        if (result > 0) {
            log.info("新增项目成本成功");
            //res.message.setMessage(200, "新增项目成本成功");
        } else {
            log.error("新增项目成本失败");
            //res.message.setMessage(403, "新增项目成本失败");
        }*/

        return projectCostRecord;
    }

    /*
    * 查询项目成本记录
     */
    @PostMapping("/selectProjectCostRecord")
    @ApiOperation(value="查询项目成本记录")
    @ResponseBody
    public Map selectProjectCostRecord(@RequestBody Map map){
        //VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        Integer projectId = Integer.parseInt(map.get("projectId").toString());

        Map<String, Object> data = new HashMap<>();
        List<ProjectCostRecord> listProjectCostRecord = projectCostRecordService.selectProjectCostRecord(projectId);
        String projectName = projectCostRecordService.selectProjectName(projectId);

        if (listProjectCostRecord.size() > 0) {
            log.info("项目成本列表返回成功");
            data.put("list", listProjectCostRecord);
            data.put("total", listProjectCostRecord.size());
            data.put("projectName",projectName);
            //res.message.setMessage(200, "项目成本列表返回成功", data);
        } else {
            log.warn("项目成本返回为空");
            //res.message.setMessage(400, "项目成本返回为空");
        }
        //return res.message;
        return data;
    }
}
