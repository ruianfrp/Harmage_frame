package com.harmonycloud.controller;


import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.project.ProjectPreSaleApply;
import com.harmonycloud.service.ProjectPreSaleApplyService;
import com.harmonycloud.util.SendMessageUtil;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

@RestController
@Api(value = "售前立项审批Controller",tags = {"售前立项审批操作接口"})
@RequestMapping("projPreSaleApply")
@Slf4j
public class ProjectPreSaleApplyController {
    @Autowired
    ProjectPreSaleApplyService projectPreSaleApplyService;

    private static final String TEST_USER_ID = "15940213473275452";

    @PostMapping("/insertProjSaleApply")
    @ApiOperation(value = "发起售前立项",notes = "projBudget,expectStartTime,assCustomer,ass_businessOpp,projLine," +
            "personRqmt,schedule可为null")
    public Map insertProjPreSaleApply(@RequestBody  ProjectPreSaleApply projectPreSaleApply) throws ApiException {
        /*        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }*/

        Map<String,Object> map = new HashMap<>();
        /**
         * 根据项目信息构造markdown格式字符串
         * */
        String markdownContent = "## 售前立项审批\n" +
                "#### 项目名称: "+projectPreSaleApply.getProjName()+"\n" +
                "#### 项目类型："+projectPreSaleApply.getProjType()+"\n" +
                "#### 项目负责人："+projectPreSaleApply.getProjManager()+"\n" +
                "#### 售前经理："+projectPreSaleApply.getPreSaleManager()+"\n" +
                "#### 项目概况："+projectPreSaleApply.getProjOverview();
        Integer result = projectPreSaleApplyService.insertProjectPreSaleApply(projectPreSaleApply);
        String projectId = projectPreSaleApply.getFkProjectId().toString();
        if(result>0){
            log.info("成功发起售前立项");
            map = SendMessageUtil.approvalMsgSend(markdownContent,projectId,TEST_USER_ID);
            map.put("intoDB","ok");
        }else{
            map.put("intoDB","error");
        }

        return map;
    }

    @GetMapping("/notifyMessage")
    @ApiOperation(value="审批状态信息返回",notes = "参数信息包含在头部,status 0：不通过 1：通过")
    @ResponseBody
    public Map updateProjPreSaleApply(@RequestParam("status") int status,@RequestParam("projectId") Long projectId) throws ApiException {
        Map<String,Object> map = new HashMap<>();
        map.put("status_code",status);
        Integer result = projectPreSaleApplyService.updateStates(projectId,status);
        if(result>0){
            map.put("msg","审批状态更新成功");
            if(status==0){
                map.put("status","成功驳回");
                SendMessageUtil.textMsgSend("您成功驳回一条Harmage审批",TEST_USER_ID);
            }else{
                map.put("status","成功通过");
                SendMessageUtil.textMsgSend("您成功通过一条Harmage审批",TEST_USER_ID);
            }
        }else map.put("msg","审批状态更新失败，请联系管理员！");
        return map;
    }
}
