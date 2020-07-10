package com.harmonycloud.config;

/**
 * 项目中的常量定义类
 */
public class Constant {
    /**
     * 应用的SuiteKey，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String SUITE_KEY="ding0b83f8854ac7f23b35c2f4657eb6378f";

    /**
     * 应用的SuiteSecret，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String SUITE_SECRET="";

    /**
     * 回调URL签名用。应用的签名Token, 登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String TOKEN = "123456";

    /**
     * 回调URL加解密用。应用的"数据加密密钥"，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String ENCODING_AES_KEY = "WiT6zbYwXVrUhmlBG5SZnUOlJvKSmyAxCOfa81l3Sra";

    public static void main(String[] args) {
        String string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 43; i++) {
            int index = (int) Math.floor(Math.random() * string.length());
            sb.append(string.charAt(index));
        }
        System.out.println(sb.toString());
    }

}
