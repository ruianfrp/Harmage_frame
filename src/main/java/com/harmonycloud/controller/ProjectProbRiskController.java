package com.harmonycloud.controller;


import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.project.ProjectProbRisk;
import com.harmonycloud.bean.project.ProjectProbRiskState;
import com.harmonycloud.service.ProjectProbRiskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

@RestController
@Api(value = "项目问题/风险Controller",tags = {"项目问题/风险操作接口"})
@RequestMapping("/projectProbRisk")
@Slf4j
public class ProjectProbRiskController {
    @Autowired
    ProjectProbRiskService projectProbRiskService;

    @Autowired
    HttpServletRequest request;


    /*
    * 根据项目ID返回该项目问题/风险列表
    * @param "projectId"
    * @author xing
    * */
    @PostMapping("/listProjectProbRisk")
    @ApiOperation(value = "返回项目风险/问题列表",notes = "根据projectId返回列表")
    @ResponseBody
    public Message listProjectProbRisk(@RequestBody @ApiParam(name = "projectId",value = "项目id",required = true) Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer projectId = Integer.parseInt(map.get("projectId").toString());
        Map<String, Object> data = new HashMap<>();
        List<ProjectProbRisk> list = projectProbRiskService.listProjectProbRiskById(projectId);
        if (list != null) {
            log.info("项目风险/问题列表返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "项目风险/问题列表返回成功", data);
        } else {
            log.error("项目风险/问题列表返回为空");
            res.message.setMessage(400, "项目风险/问题列表返回为空");
        }
       return res.message;
    }


    /*
    * 添加项目问题/风险
    * @author xing
    * */
    @PostMapping("/insertProjectProbRisk")
    @ApiOperation(value = "添加项目风险/问题")
    @ResponseBody
    public Message insertProjectProbRisk(@RequestBody @ApiParam(name="fkProjectId\ndescription\ncurrentState\n" +
            "proposedPerson\ninChargePerson\ntype",value="以上字段必填,其余可不填或填null,提出人负责人" +
            "填工号",required = true) ProjectProbRisk projectProbRisk){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        projectProbRisk.setProposedTime(new Date());
        Integer result = projectProbRiskService.insertProjectProbRisk(projectProbRisk);
        Map<String,Object> data = new HashMap<>();
        if(result>0) {
            data.put("insert data",projectProbRisk);
            if(projectProbRisk.getType()==0) {
                log.info("项目问题添加成功");
                res.message.setMessage(200,"项目问题添加成功",data);
            }else{
                log.info("项目风险添加成功");
                res.message.setMessage(200,"项目风险添加成功",data);
            }
        }else{
            log.error("添加失败！");
            res.message.setMessage(400,"添加失败！");
        }

        return res.message;
    }


    @PostMapping("/updateProjectProbRisk")
    @ApiOperation(value = "更新项目风险/问题",notes = "根据记录id标识来更新")
    public Message updateProjectProbRisk(@RequestBody ProjectProbRisk projectProbRisk){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = projectProbRiskService.updateProjectProbRisk(projectProbRisk);
        Map<String,Object> data = new HashMap<>();
        if(result>0){
            log.info("更新成功！");
            data.put("after update",projectProbRisk);
            res.message.setMessage(200,"更新成功！",data);
        }
        else{
            log.error("更新失败！数据不存在");
            res.message.setMessage(400,"更新失败！数据不存在");
        }
        return res.message;
    }


    @PostMapping("/deleteProjectProbRisk")
    @ApiOperation(value = "删除项目风险/问题",notes = "id为风险/问题标识")
    @ResponseBody
    public Message deleteProjectProbRisk(@RequestBody @ApiParam(name = "id",value = "标识",required = true) Map map){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = projectProbRiskService.deleteProjectProbRisk(Integer.parseInt(map.get("id").toString()));
        if(result>0){
            log.info("删除成功！");
            res.message.setMessage(200,"删除成功！");
        }
        else{
            log.error("删除失败！");
            res.message.setMessage(400,"删除失败!");
        }
        return res.message;
    }

    @GetMapping("/listProjectProbRiskState")
    @ApiOperation(value = "返回项目风险/问题概览",notes = "返回项目id,名称,描述,未处理的问题/风险数量")
    @ResponseBody
    public Message ProjectProbRiskState(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
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
        if(list!=null){
            log.info("项目风险/问题概览返回成功");
            data.put("list",list);
            data.put("total",list.size());
            res.message.setMessage(200,"项目风险/问题概览返回成功",data);
        }else{
            log.error("项目风险/问题概览返回为空");
            res.message.setMessage(400,"项目风险/问题概览列表返回为空");
        }

        return res.message;
    }
}
