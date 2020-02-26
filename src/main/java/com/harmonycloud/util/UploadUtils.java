package com.harmonycloud.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UploadUtils {

    /**
     * 得到真实文件名
     *
     * @param fileName
     * @return
     */
    public static String subFileName(String fileName) {
        // 查找最后一个 \ (文件分隔符)位置
        int index = fileName.lastIndexOf(File.separator);
        if (index == -1) {
            // 没有分隔符为真实名称
            return fileName;
        } else {
            return fileName.substring(index + 1);
        }
    }

    /**
     * 获得随机UUID文件名
     *
     * @param fileName
     * @return
     */
    public static String generateRandonFileName(String fileName) {
        // 首相获得扩展名，然后生成一个UUID码作为名称，然后加上扩展名
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = dateFormat.format(date);
        return fileName.substring(0,fileName.lastIndexOf(".")) + dateTime;
//        String ext = fileName.substring(fileName.lastIndexOf("."));
//        return UUID.randomUUID().toString() + ext;
    }

    public static String generateRandonFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获得hashcode 生成二级目录
     *
     * @param uuidFileName
     * @return
     */
    public static String generateRandomDir(String uuidFileName) {
        int hashCode = uuidFileName.hashCode();// 得到它的hashcode编码
        // 一级目录
        int d1 = hashCode & 0xf;
        // 二级目录
        int d2 = (hashCode >> 4) & 0xf;
        return "/" + d1 + "/" + d2;
    }

    public static void main(String[] args) {
        System.out.println(generateRandonFileName());
    }
}