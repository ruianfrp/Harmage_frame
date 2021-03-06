package com.harmonycloud.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiOrgListshortcutRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.harmonycloud.bean.project.ProjectEndMsgView;
import com.harmonycloud.bean.project.ProjectPreSaleApply;
import com.harmonycloud.config.DingConstant;
import com.harmonycloud.config.URLConstant;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.harmonycloud.config.URLConstant.URL_GET_TOKKEN;
import static com.harmonycloud.config.URLConstant.URL_SEND_WORK_MESSAGE;

@Slf4j
public class SendMessageUtil {
    public static final String USER_ID_LIST = "15848927472119248";
    public static final Long AGENT_ID = 305862497L;
    public static final String ACTION_URL = "dingtalk://dingtalkclient/page/link?url=http%3a%2f%2f47.92.161.179%3a31800%2flogin&pc_slide=false";
    public static final String BG_COLOR = "FFBBBBBB";
    public static final String OK_URL = "dingtalk://dingtalkclient/page/link?url=http%3a%2f%2f10.1.11.97%3a8080%2fprojPreSaleApply%2fnotifyMessage%3fstatus%3d1%26projectId%3d";
    public static final String VETO_URL = "dingtalk://dingtalkclient/page/link?url=http%3a%2f%2f10.1.11.97%3a8080%2fprojPreSaleApply%2fnotifyMessage%3fstatus%3d0%26projectId%3d";

    public static void PMOMessageSend(String res) throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(USER_ID_LIST);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle("Harmage消息通知");
        msg.getActionCard().setMarkdown(res);
//        msg.getActionCard().setSingleTitle("查看详情");
//        msg.getActionCard().setSingleUrl("http://10.1.11.89/login");
        List<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList> btnJsonLists = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList btnJson1 = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
        btnJson1.setTitle("查看详情");
        btnJson1.setActionUrl(ACTION_URL);
        btnJsonLists.add(0,btnJson1);
        msg.getActionCard().setBtnJsonList(btnJsonLists);
        msg.getActionCard().setBtnOrientation("0");
        msg.setMsgtype("action_card");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getToken());
        if (response.isSuccess()) {
            log.info("钉钉发送工作通知消息成功");
        } else {
            log.error("钉钉发送工作通知消息失败");
        }
    }

    public static void PMMessageSend(String userId) throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(userId);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle("Harmage消息通知");
        msg.getActionCard().setMarkdown("项目组有需要确认状态的员工，请尽快确认！\n(*^v^*)点击详情前往Harmage");
        List<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList> btnJsonLists = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList btnJson1 = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
        btnJson1.setTitle("查看详情");
        btnJson1.setActionUrl(ACTION_URL);
        btnJsonLists.add(btnJson1);
        msg.getActionCard().setBtnOrientation("0");
        msg.getActionCard().setBtnJsonList(btnJsonLists);
        msg.setMsgtype("action_card");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getToken());
        if (response.isSuccess()) {
            log.info("钉钉发送工作通知消息成功");
        } else {
            log.error("钉钉发送工作通知消息失败");
        }
    }

    public static void projectEndMessageSend(ProjectEndMsgView projectEndMsgView) throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);
        OapiMessageCorpconversationAsyncsendV2Request.Form form0 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form1 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form2 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form3 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form4 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form5 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form0.setKey("项目名称：");
        form0.setValue(projectEndMsgView.getProjectName());
        form1.setKey("项目经理：");
        form1.setValue(projectEndMsgView.getPmName());
        form2.setKey("预计结束时间：");
        form2.setValue(projectEndMsgView.getPreEndTime());
        form3.setKey("实际结束时间：");
        form3.setValue(projectEndMsgView.getRealEndTime());
        form4.setKey("申请时间：");
        form4.setValue(projectEndMsgView.getCreatTime());
        form5.setKey("申请理由：");
        form5.setValue(projectEndMsgView.getRemark());
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
        list.add(0,form0);
        list.add(1,form1);
        list.add(2,form2);
        list.add(3,form3);
        list.add(4,form4);
        list.add(5,form5);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(USER_ID_LIST);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().setMessageUrl(ACTION_URL);
        msg.getOa().getHead().setText("结项申请审批");
        msg.getOa().getHead().setBgcolor("BG_COLOR");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setTitle("Harmage消息通知");
        msg.getOa().getBody().setForm(Collections.singletonList(new OapiMessageCorpconversationAsyncsendV2Request.Form()));
        msg.getOa().getBody().setForm(list);
        msg.getOa().getBody().setContent("此项目已完成且已开结项会议，特此申请结项！\n(*^v^*)点击详情前往Harmage");
        msg.getOa().getBody().setFileCount(String.valueOf(projectEndMsgView.getFileNum()));
        msg.getOa().getBody().setAuthor(projectEndMsgView.getPmName());
        msg.setMsgtype("oa");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getToken());
        if (response.isSuccess()) {
            log.info("钉钉发送工作通知消息成功");
        } else {
            log.error("钉钉发送工作通知消息失败");
        }
    }

    public static void projectTerminateMessageSend(ProjectEndMsgView projectEndMsgView) throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);
        OapiMessageCorpconversationAsyncsendV2Request.Form form0 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form1 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form2 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form3 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form4 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form5 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form0.setKey("项目名称：");
        form0.setValue(projectEndMsgView.getProjectName());
        form1.setKey("项目经理：");
        form1.setValue(projectEndMsgView.getPmName());
        form2.setKey("预计结束时间：");
        form2.setValue(projectEndMsgView.getPreEndTime());
        form3.setKey("实际结束时间：");
        form3.setValue(projectEndMsgView.getRealEndTime());
        form4.setKey("申请时间：");
        form4.setValue(projectEndMsgView.getCreatTime());
        form5.setKey("申请理由：");
        form5.setValue(projectEndMsgView.getRemark());
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
        list.add(0,form0);
        list.add(1,form1);
        list.add(2,form2);
        list.add(3,form3);
        list.add(4,form4);
        list.add(5,form5);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(USER_ID_LIST);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().setMessageUrl(ACTION_URL);
        msg.getOa().getHead().setText("终止项目申请审批");
        msg.getOa().getHead().setBgcolor("BG_COLOR");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setTitle("Harmage消息通知");
        msg.getOa().getBody().setForm(Collections.singletonList(new OapiMessageCorpconversationAsyncsendV2Request.Form()));
        msg.getOa().getBody().setForm(list);
        msg.getOa().getBody().setContent("此项目已完成且已开结项会议，特此申请结项！\n(*^v^*)点击详情前往Harmage");
        msg.getOa().getBody().setFileCount(String.valueOf(projectEndMsgView.getFileNum()));
        msg.getOa().getBody().setAuthor(projectEndMsgView.getPmName());
        msg.setMsgtype("oa");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getToken());
        if (response.isSuccess()) {
            log.info("钉钉发送工作通知消息成功");
        } else {
            log.error("钉钉发送工作通知消息失败");
        }
    }

    public static void projectEndMeetingMsgSend(ProjectEndMsgView projectEndMsgView) throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);
        OapiMessageCorpconversationAsyncsendV2Request.Form form0 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form1 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form2 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form3 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form4 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form0.setKey("项目名称：");
        form0.setValue(projectEndMsgView.getProjectName());
        form1.setKey("项目经理：");
        form1.setValue(projectEndMsgView.getPmName());
        form2.setKey("预定会议时间：");
        form2.setValue(projectEndMsgView.getMeetingTime());
        form3.setKey("申请时间：");
        form3.setValue(projectEndMsgView.getCreatTime());
        form4.setKey("申请理由：");
        form4.setValue(projectEndMsgView.getRemark());
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
        list.add(0,form0);
        list.add(1,form1);
        list.add(2,form2);
        list.add(3,form3);
        list.add(4,form4);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(USER_ID_LIST);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().setMessageUrl(ACTION_URL);
        msg.getOa().getHead().setText("结项会议申请审批");
        msg.getOa().getHead().setBgcolor("BG_COLOR");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setTitle("Harmage消息通知");
        msg.getOa().getBody().setForm(Collections.singletonList(new OapiMessageCorpconversationAsyncsendV2Request.Form()));
        msg.getOa().getBody().setForm(list);
        msg.getOa().getBody().setContent("此项目已完成暂未开结项会议，特此申请结项会议时间，请尽快安排实际会议时间！\n(*^v^*)点击详情前往Harmage");
        msg.getOa().getBody().setFileCount(String.valueOf(projectEndMsgView.getFileNum()));
        msg.getOa().getBody().setAuthor(projectEndMsgView.getPmName());
        msg.setMsgtype("oa");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getToken());
        if (response.isSuccess()) {
            log.info("钉钉发送工作通知消息成功");
        } else {
            log.error("钉钉发送工作通知消息失败");
        }
    }

    public static void projectTerminateMeetingMsgSend(ProjectEndMsgView projectEndMsgView) throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);
        OapiMessageCorpconversationAsyncsendV2Request.Form form0 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form1 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form2 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form3 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        OapiMessageCorpconversationAsyncsendV2Request.Form form4 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form0.setKey("项目名称：");
        form0.setValue(projectEndMsgView.getProjectName());
        form1.setKey("项目经理：");
        form1.setValue(projectEndMsgView.getPmName());
        form2.setKey("预定会议时间：");
        form2.setValue(projectEndMsgView.getMeetingTime());
        form3.setKey("申请时间：");
        form3.setValue(projectEndMsgView.getCreatTime());
        form4.setKey("申请理由：");
        form4.setValue(projectEndMsgView.getRemark());
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
        list.add(0,form0);
        list.add(1,form1);
        list.add(2,form2);
        list.add(3,form3);
        list.add(4,form4);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(USER_ID_LIST);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().setMessageUrl(ACTION_URL);
        msg.getOa().getHead().setText("终止项目会议申请审批");
        msg.getOa().getHead().setBgcolor("BG_COLOR");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setTitle("Harmage消息通知");
        msg.getOa().getBody().setForm(Collections.singletonList(new OapiMessageCorpconversationAsyncsendV2Request.Form()));
        msg.getOa().getBody().setForm(list);
        msg.getOa().getBody().setContent("此项目已完成暂未开结项会议，特此申请结项会议时间，请尽快安排实际会议时间！\n(*^v^*)点击详情前往Harmage");
        msg.getOa().getBody().setFileCount(String.valueOf(projectEndMsgView.getFileNum()));
        msg.getOa().getBody().setAuthor(projectEndMsgView.getPmName());
        msg.setMsgtype("oa");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getToken());
        if (response.isSuccess()) {
            log.info("钉钉发送工作通知消息成功");
        } else {
            log.error("钉钉发送工作通知消息失败");
        }
    }

    public static Map projPreSaleApplyMsgSend(ProjectPreSaleApply projectPreSaleApply, String userId) throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(userId);
        /**
         * 构建OA消息体
         * */
        OapiMessageCorpconversationAsyncsendV2Request.Form form0 = new OapiMessageCorpconversationAsyncsendV2Request.Form();

        form0.setKey("项目名称：");
        form0.setValue(projectPreSaleApply.getProjName());

        List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
        list.add(0,form0);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.setMsgtype("oa");
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().setMessageUrl(ACTION_URL);

        msg.getOa().getHead().setBgcolor(BG_COLOR);
        msg.getOa().getHead().setText("售前项目立项审批");

        msg.getOa().getBody().setTitle("Harmage消息通知");
        msg.getOa().getBody().setForm(list);
        msg.getOa().getBody().setContent(projectPreSaleApply.getProjOverview());
        msg.getOa().getBody().setAuthor(projectPreSaleApply.getPreSaleManager());

        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getToken());
        Map<String,Object> map = new HashMap<>();
        if(response.isSuccess()){
            map.put("result",response);
            log.info("dingding send message success");
        }else{
            map.put("result","failed");
            log.info("ding ding send message failed");
        }
        return map;
    }

    public static Map approvalMsgSend(String markdownContent,String projectId,String userId) throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(userId);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("action_card");
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle("Harmage消息通知");
        msg.getActionCard().setMarkdown(markdownContent);
        msg.getActionCard().setBtnOrientation("0");//0：竖直排列 1：横向排列
        List<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList> btnJsonList = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList btnOk = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
        /**
         * URL中包含status id字段
         * pc_slide=false表示在浏览器中打开链接
        * */
        btnOk.setTitle("通过");
        btnOk.setActionUrl(OK_URL+projectId+"&pc_slide=false");
        OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList btnVeto = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
        btnVeto.setTitle("驳回");
        btnVeto.setActionUrl(VETO_URL+projectId+"&pc_slide=false");
        OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList btnDetail = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
        btnDetail.setTitle("查看详情");
        btnDetail.setActionUrl(ACTION_URL);
        btnJsonList.add(btnOk);
        btnJsonList.add(btnVeto);
        btnJsonList.add(btnDetail);
        msg.getActionCard().setBtnJsonList(btnJsonList);

        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,getToken());
        Map<String,Object> map = new HashMap<>();
        map.put("result",response);
        return map;
    }

    public static Map textMsgSend(String content,String userId) throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient(URL_SEND_WORK_MESSAGE);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(AGENT_ID);
        request.setUseridList(userId);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(content);

        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,getToken());
        Map<String,Object> map = new HashMap<>();
        map.put("result",response);
        return map;
    }

    private static String getToken() {
        DefaultDingTalkClient client = new
                DefaultDingTalkClient(URL_GET_TOKKEN);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(DingConstant.APP_KEY);
        request.setAppsecret(DingConstant.APP_SECRET);
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
