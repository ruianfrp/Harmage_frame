package com.harmonycloud.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;



public class DesEncryptUtil {

    private  Key key;

    public DesEncryptUtil() {
        setKey2("harmonycloud");
    }
    /**
     * 根据参数生成KEY
     *
     * @param strKey
     *
     * Windows下正常  Linux 下报错
     */
    public void setKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            _generator.init(new SecureRandom(strKey.getBytes()));
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Linux 系统下正常运行
    public void setKey2(String strKey) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec keySpec = new DESKeySpec(strKey.getBytes("utf-8"));
            this.key = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }
    }

    public static void main(String[] args) {
        DesEncryptUtil u = new DesEncryptUtil();
        //加密
        String encString = u.getEncString("admin");
        //解密
        String desString = u.getDesString(encString);
        System.err.println("encString:"+encString+";desString:"+desString+":admin");
        if (!desString.equals("admin")) {
            System.err.println("admin"+":错误：encString:"+encString+";desString:"+desString);
        }
    }

    /**
     * 加密String明文输入,String密文输出
     *
     * @param strMing
     * @return 加密
     */
    public String getEncString(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        Encoder base64en = Base64.getEncoder();
        try {
            byteMing = strMing.getBytes("UTF-8");
            byteMi = getEncCode(byteMing);
            strMi = base64en.encodeToString(byteMi);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return 解密
     */
    public String getDesString(String strMi) {
        Decoder base64De = Base64.getMimeDecoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decode(strMi);
            byteMing = getDesCode(byteMi);
            strMing = new String(byteMing, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private  byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, this.key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }


}
