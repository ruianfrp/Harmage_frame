package com.harmonycloud.controller;


import com.harmonycloud.bean.project.ProjectProbRisk;
import com.harmonycloud.bean.project.ProjectProbRiskState;
import com.harmonycloud.service.ProjectProbRiskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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
    @PostMapping("/listProjectProbRisk")
    @ApiOperation(value = "返回项目风险/问题列表",notes = "根据projectId返回列表")
    @ResponseBody
    public Map listProjectProbRisk(@RequestBody @ApiParam(name = "projectId",value = "项目id",required = true) Map map) {
        Integer projectId = Integer.parseInt(map.get("projectId").toString());
        Map<String, Object> data = new HashMap<>();
        List<ProjectProbRisk> list = projectProbRiskService.listProjectProbRiskById(projectId);

        data.put("list", list);
        data.put("total", list.size());

        return data;
    }


    /*
    * 添加项目问题/风险
    * @author xing
    * */
    @PostMapping("/insertProjectProbRisk")
    @ApiOperation(value = "添加项目风险/问题")
    @ResponseBody
    public Map insertProjectProbRisk(@RequestBody ProjectProbRisk ProjectProbRisk){
        Map<String,Object> data = new HashMap<>();
        ProjectProbRisk.setProposedTime(new Timestamp(System.currentTimeMillis()));
        Integer result = projectProbRiskService.insertProjectProbRisk(ProjectProbRisk);
        if(result>0) {
            data.put("result","添加成功！");
            data.put("object",ProjectProbRisk);
        }
        else data.put("result","添加失败！");

        return data;
    }


    @PostMapping("/updateProjectProbRisk")
    @ApiOperation(value = "更新项目风险/问题",notes = "根据id标识来更新")
    @ResponseBody
    public Map updateProjectProbRisk(@RequestBody ProjectProbRisk ProjectProbRisk){
        Map<String,Object> data = new HashMap<>();
        Integer result = projectProbRiskService.updateProjectProbRisk(ProjectProbRisk);
        if(result>0){
            data.put("result","更新成功");
            data.put("object",ProjectProbRisk);
        }
        else data.put("result","更新失败");
        return data;
    }


    @PostMapping("/deleteProjectProbRisk")
    @ApiOperation(value = "删除项目风险/问题",notes = "id为风险/问题标识")
    @ResponseBody
    public Map deleteProjectProbRisk(@RequestBody @ApiParam(name = "id",value = "标识") Map map){
        Map<String, Object> data = new HashMap<>();
        Integer result = projectProbRiskService.deleteProjectProbRisk(Integer.parseInt(map.get("id").toString()));
        if(result>0){
            data.put("result","delete success!");
        }
        else data.put("result","delete failed!");
        return data;
    }

    @GetMapping("/listProjectProbRiskState")
    @ApiOperation(value = "返回项目风险/问题概览")
    @ResponseBody
    public Map ProjectProbRiskState(){
        Map<String,Object> data = new HashMap<>();
        List<ProjectProbRiskState> list = projectProbRiskService.listProjectProbRiskState();
        /*
        * 设置项目风险/问题概览
        * */
        for(ProjectProbRiskState projectProbRiskState:list){
            Integer projectId = projectProbRiskState.getProjectId();
            String overviewDes = projectProbRiskService.selectProbRiskDescription(projectId);
            projectProbRiskState.setDescription(overviewDes);
        }
        data.put("message","success");
        data.put("total",list.size());
        data.put("overview",list);

        return data;
    }
}
