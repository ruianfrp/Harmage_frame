package com.harmonycloud.util.ExportDataToMmber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DeployData {
    private File fileOut;

    public void excute(String filepath) {
        fileOut = new File(filepath);
        this.deployUserInfoData("无");
    }

    public void excuteFile(File file, String importType){
        fileOut = file;
        this.deployUserInfoData(importType);
    }

    public void deployUserInfoData(String importType) {
        try {
            if(importType.equals("成员导入")){
                new ExportExcel(fileOut, "member", Object.class, "Sheet1");
            }else if (importType.equals("项目导入")) {
                new ExportExcel(fileOut, "member", Object.class, "Sheet1");
            }else if (importType.equals("客户导入")) {
                new ExportExcel(fileOut, "member", Object.class, "Sheet1");
            }else if (importType.equals("客户跟单记录导入")) {
                new ExportExcel(fileOut, "member", Object.class, "Sheet1");
            }else if (importType.equals("项目成本导入")) {
                new ExportExcel(fileOut, "member", Object.class, "Sheet1");
            }else {
                new ExportExcel(fileOut, "member", Object.class, "Sheet1");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}