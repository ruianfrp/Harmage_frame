package com.harmonycloud.config;

public class URLConstant {
    /**
     * 钉钉网关gettoken地址
     */
    public static final String URL_GET_TOKKEN = "https://oapi.dingtalk.com/gettoken";

    /**
     *获取部门列表的接口url
     */
    public static final String URL_GET_DEPARTMENT_LIST = "https://oapi.dingtalk.com/department/list";

    /**
     *获取部门内成员列表的接口url
     */
    public static final String URL_GET_USER_LIST = "https://oapi.dingtalk.com/user/list";

    /**
     *根据临时授权码从钉钉获取登录用户信息
     */
    public static final String URL_GET_USER_INFO_BYCODE = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";

    /**
     * 获取企业员工人数
     */
    public static final String URL_GET_COUNT_USER = "https://oapi.dingtalk.com/user/get_org_user_count";

    /**
     * 发送工作通知消息
     */
    public static final String URL_SEND_WORK_MESSAGE = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";

    /**
     * 根据unionid获取userid
     */
    public static final String URL_GET_USERID_BY_UNIONID = "https://oapi.dingtalk.com/user/getUseridByUnionid";

    /**
     * 获取用户详情
     */
    public static final String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/get";
}
