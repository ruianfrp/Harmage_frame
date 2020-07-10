package com.harmonycloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.dingtalk.oapi.lib.aes.Utils;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.config.Constant;
import com.harmonycloud.config.DingConstant;
import com.harmonycloud.util.ding.DingUtils;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.harmonycloud.config.URLConstant.URL_GET_TOKKEN;
import static com.harmonycloud.util.JsonWebToken.VerifyCode;

/**
 * ISV 小程序回调信息处理
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="员工controller",tags={"员工操作接口"})
public class CallbackController {


    @Autowired
    private HttpServletRequest request;

    private Logger bizLogger = LoggerFactory.getLogger("BIZ_CALLBACKCONTROLLER");
    private Logger mainLogger = LoggerFactory.getLogger(CallbackController.class);
    private static String accessToken = null;

    /**
     * 创建应用，验证回调URL创建有效事件（第一次保存回调URL之前）
     */
    private static final String EVENT_CHECK_CREATE_SUITE_URL = "check_create_suite_url";

    /**
     * 创建应用，验证回调URL变更有效事件（第一次保存回调URL之后）
     */
    private static final String EVENT_CHECK_UPADTE_SUITE_URL = "check_update_suite_url";

    /**
     * suite_ticket推送事件
     */
    private static final String EVENT_SUITE_TICKET = "suite_ticket";

    /**
     * 企业授权开通应用事件
     */
    private static final String EVENT_TMP_AUTH_CODE = "tmp_auth_code";

    @RequestMapping(value = "dingCallback", method = RequestMethod.POST)
    public Object dingCallback(
            @RequestParam(value = "signature") String signature,
            @RequestParam(value = "timestamp") Long timestamp,
            @RequestParam(value = "nonce") String nonce,
            @RequestBody(required = false) JSONObject json
    ) {
        String params = " signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;
        try {
            bizLogger.info("begin callback:" + params);
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(Constant.TOKEN, Constant.ENCODING_AES_KEY, Constant.SUITE_KEY);

            // 从post请求的body中获取回调信息的加密数据进行解密处理
            String encrypt = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp.toString(), nonce, encrypt);
            JSONObject callBackContent = JSON.parseObject(plainText);

            // 根据回调事件类型做不同的业务处理
            String eventType = callBackContent.getString("EventType");
            if (EVENT_CHECK_CREATE_SUITE_URL.equals(eventType)) {
                bizLogger.info("验证新创建的回调URL有效性: " + plainText);
            } else if (EVENT_CHECK_UPADTE_SUITE_URL.equals(eventType)) {
                bizLogger.info("验证更新回调URL有效性: " + plainText);
            } else if (EVENT_SUITE_TICKET.equals(eventType)) {
                // suite_ticket用于用签名形式生成accessToken(访问钉钉服务端的凭证)，需要保存到应用的db。
                // 钉钉会定期向本callback url推送suite_ticket新值用以提升安全性。
                // 应用在获取到新的时值时，保存db成功后，返回给钉钉success加密串（如本demo的return）
                bizLogger.info("应用suite_ticket数据推送: " + plainText);
            } else if (EVENT_TMP_AUTH_CODE.equals(eventType)) {
                // 本事件应用应该异步进行授权开通企业的初始化，目的是尽最大努力快速返回给钉钉服务端。用以提升企业管理员开通应用体验
                // 即使本接口没有收到数据或者收到事件后处理初始化失败都可以后续再用户试用应用时从前端获取到corpId并拉取授权企业信息，进而初始化开通及企业。
                bizLogger.info("企业授权开通应用事件: " + plainText);
            } else {
                // 其他类型事件处理
            }

            // 返回success的加密信息表示回调处理成功
            return dingTalkEncryptor.getEncryptedMap("success", timestamp, nonce);
        } catch (Exception e) {
            //失败的情况，应用的开发者应该通过告警感知，并干预修复
            mainLogger.error("process callback fail." + params, e);
            return "fail";
        }
    }

    //注册回调
    public static void main(String[] args) {
        DingUtils.getToken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
        request.setUrl("http://47.92.161.179/callback");
        request.setAesKey(Constant.ENCODING_AES_KEY);
        request.setToken(Constant.TOKEN);
        request.setCallBackTag(Arrays.asList("bpms_instance_change", "bpms_task_change"));
        try {
            OapiCallBackRegisterCallBackResponse response = client.execute(request, accessToken);
            if(response.isSuccess()){
                System.out.println("注册回调成功！");
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    //测试
    @GetMapping("/aaa")
    public Message aaa(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
        Map<String, Object> data = new HashMap<>();
        List<OapiProcessinstanceGetResponse> qqq = new ArrayList<>();
        List<String> instanceList = DingUtils.getInstanceList();
        for(String s:instanceList){
            qqq.add(DingUtils.getDeliverTheProject(s));
        }
        data.put("qqq",qqq);
        res.message.setMessage(200,"aaaaaaaa",data);
        return res.message;
    }
}
