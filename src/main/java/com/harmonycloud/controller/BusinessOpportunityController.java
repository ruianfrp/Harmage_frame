package com.harmonycloud.controller;

import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.customer.BusinessOpportunity;
import com.harmonycloud.service.BusinessOpportunityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

@RestController
@RequestMapping("bizOpp")
@Slf4j
@Api(value = "业务商机Controller",tags = {"业务商机接口"})
public class BusinessOpportunityController {
    @Autowired
    BusinessOpportunityService businessOpportunityService;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/insertBizOpp")
    @ResponseBody
    @ApiOperation(value = "新增商机")
    public Map insertBizOpp(@RequestBody @ApiParam(name="传入商机对象",value = "非必填项不必显式给出null") BusinessOpportunity businessOpportunity){
/*        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }*/
        Map<String,Object> map = new HashMap<>();
        Integer result = businessOpportunityService.insertBizOpp(businessOpportunity);
        if(result>0){
            map.put("data",businessOpportunity);
        }else{
            map.put("error","插入失败！");
        }
        return map;
    }

}
