package com.harmonycloud.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {

    Key key;

    public DESUtil(String str) {
        getKey(str);//生成密匙
    }

    /**
     * 根据参数生成KEY
     */
    public void getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            _generator.init(new SecureRandom(strKey.getBytes()));
            this.key = _generator.generateKey();
            _generator = null;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    //文件file进行加密并保存目标文件destFile中
    public void encrypt(MultipartFile file, String destFile) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("harmonyc".getBytes("utf-8"), "DES"));
        InputStream is = file.getInputStream();
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[4096];
        int r;
        while ((r = cis.read(buffer)) != -1) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    //文件file进行加密并保存目标文件destFile中
    public void encrypt(String file, String destFile) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("harmonyc".getBytes("utf-8"), "DES"));
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[4096];
        int r;
        while ((r = cis.read(buffer)) != -1) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    //文件采用DES算法解密文件
    public void decrypt(String file, String dest) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec("harmonyc".getBytes("utf-8"), "DES"));
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[4096];
        int r;
        while ((r = is.read(buffer)) != -1) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();
    }

    public static void main(String[] args) throws Exception {
        DESUtil td = new DESUtil("harmonycloud");
        System.out.println(td.key);
        td.encrypt("C:/Users/hc/Desktop/新建文本文档.txt", "C:/Users/hc/Desktop/新建文本文档r.txt"); //加密
        td.decrypt("C:/Users/hc/Desktop/新建文本文档r.txt", "C:/Users/hc/Desktop/新建文本文档rr.txt"); //解密

    }
}