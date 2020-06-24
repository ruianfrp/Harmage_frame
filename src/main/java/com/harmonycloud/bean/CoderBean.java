package com.harmonycloud.bean;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public abstract class CoderBean {

    /**
     * BASE64解密
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (Base64.getDecoder()).decode(key);
    }

    /**
     * BASE64加密
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (Base64.getEncoder()).encodeToString(key);
    }
}
