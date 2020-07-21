package com.harmonycloud.controller;


import com.harmonycloud.bean.project.ProjectProblem;
import com.harmonycloud.bean.project.ProjectRisk;
import com.harmonycloud.service.ProjectProbRiskService;
import com.sun.javafx.image.IntPixelGetter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javafx.beans.binding.ObjectExpression;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "项目问题/风险Controller",tags = {"项目问题/风险操作接口"})
@RequestMapping("/projectProbRisk")
public class ProjectProbRiskController {
    @Autowired
    ProjectProbRiskService projectProbRiskService;

    @Autowired
    private HttpServletRequest request;


    /*
    * 根据项目ID返回该项目问题/风险列表
    * @param "projectId"
    * @author xing
    * */
    @PostMapping("/listProjectProblem")
    @ApiOperation(value = "返回项目问题列表",notes = "根据projectId返回列表")
    @ResponseBody
    public Map listProjectProblem(@RequestBody @ApiParam(name = "projectId",value = "项目id",required = true) Map map) {
        Integer projectId = Integer.parseInt(map.get("projectId").toString());
        Map<String, Object> data = new HashMap<>();
        List<ProjectProblem> list = projectProbRiskService.listProjectProblemById(projectId);

        data.put("list", list);
        data.put("total", list.size());

        return data;
    }

    @PostMapping("/listProjectRisk")
    @ApiOperation(value = "返回项目风险列表",notes = "根据projectId返回列表")
    @ResponseBody
    public Map listProjectRisk(@RequestBody @ApiParam(name = "projectId",value="项目Id",required = true) Map map){
        Integer projectId = Integer.parseInt(map.get("projectId").toString());
        Map<String, Object> data = new HashMap<>();
        List<ProjectRisk> list = projectProbRiskService.listProjectRiskById(projectId);

        data.put("list", list);
        data.put("total", list.size());

        return data;
    }

    /*
    * 添加项目问题/风险
    * @author xing
    * */
    @PostMapping("/insertProjectProblem")
    @ApiOperation(value = "添加项目问题")
    @ResponseBody
    public Map insertProjectProblem(@RequestBody ProjectProblem projectProblem){
        Map<String,Object> data = new HashMap<>();
        projectProblem.setProposedTime(new Timestamp(System.currentTimeMillis()));
        Integer result = projectProbRiskService.insertProjectProblem(projectProblem);
        if(result>0) {
            data.put("result","添加成功！");
            data.put("object",projectProblem);
        }
        else data.put("result","添加失败！");

        return data;
    }

    @PostMapping("/insertProjectRisk")
    @ApiOperation(value = "添加项目风险")
    @ResponseBody
    public Map insertProjectRisk(@RequestBody ProjectRisk projectRisk){
        Map<String,Object> data = new HashMap<>();
        Integer result = projectProbRiskService.insertProjectRisk(projectRisk);
        if(result>0) {
            data.put("result","添加成功！");
            data.put("object",projectRisk);
        }
        else data.put("result","添加失败！");
        return data;
    }

    @PostMapping("/updateProjectProblem")
    @ApiOperation(value = "更新项目问题")
    @ResponseBody
    public Map updateProjectProblem(@RequestBody ProjectProblem projectProblem){
        Map<String,Object> data = new HashMap<>();
        Integer result = projectProbRiskService.updateProjectProblem(projectProblem);
        if(result>0){
            data.put("result","更新成功");
            data.put("object",projectProblem);
        }
        else data.put("result","添加失败");
        return data;
    }

    @PostMapping("/updateProjectRisk")
    @ApiOperation(value = "更新项目风险")
    @ResponseBody
    public Map updateProjectRisk(@RequestBody ProjectRisk projectRisk){
        Map<String,Object> data = new HashMap<>();
        Integer result = projectProbRiskService.updateProjectRisk(projectRisk);
        if(result>0){
            data.put("result","更新成功");
            data.put("object",projectRisk);
        }
        else data.put("result","添加失败");
        return data;
    }

    @PostMapping("/deleteProjectProblem")
    @ApiOperation(value = "删除项目问题",notes = "id为项目某一问题标识")
    @ResponseBody
    public Map deleteProjectProblem(@RequestBody @ApiParam(name = "problemId",value = "问题标识") Map map){
        Map<String, Object> data = new HashMap<>();
        Integer result = projectProbRiskService.deleteProjectProblem(Integer.parseInt(map.get("problemId").toString()));
        if(result>0){
            data.put("result","delete success!");
        }
        else data.put("result","delete failed!");
        return data;
    }

    @PostMapping("/deleteProjectRisk")
    @ApiOperation(value = "删除项目风险",notes = "id为项目某一风险标识")
    @ResponseBody
    public Map deleteProjectRisk(@RequestBody @ApiParam(name = "riskId",value = "风险标识") Map map){
        Map<String, Object> data = new HashMap<>();
        Integer result = projectProbRiskService.deleteProjectProblem(Integer.parseInt(map.get("riskId").toString()));
        if(result>0){
            data.put("result","delete success!");
        }
        else data.put("result","delete failed!");
        return data;
    }
}
