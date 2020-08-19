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
     * 获取部门用户
     * */
    public static final String URL_GET_USER_SIMPLE_LIST = "https://oapi.dingtalk.com/user/simplelist";

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

    public static final String URL_GET_ROLE_LIST = "https://oapi.dingtalk.com/topapi/role/list";

    /**
     * 根据角色获取用户名ID信息
    * */
    public static final String URL_GET_ROLE_SIMPLELIST = "https://oapi.dingtalk.com/topapi/role/simplelist";

    public static final String URL_GET_REPORT_LIST = "https://oapi.dingtalk.com/topapi/report/list";

    public static final String URL_GET_PROCESSINSTANCE_LISTIDS = "https://oapi.dingtalk.com/topapi/processinstance/listids";

    public static final String URL_GET_PROCESSINSTANCE = "https://oapi.dingtalk.com/topapi/processinstance/get";
}
