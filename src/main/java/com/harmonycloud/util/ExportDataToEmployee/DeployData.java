package com.harmonycloud.util.ExportDataToEmployee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DeployData {
    private File fileOut;

    public void excute(String filepath) {
        fileOut = new File(filepath);
        this.deployUserInfoData();
    }

    public void excuteFile(File file){
        fileOut = file;
        this.deployUserInfoData();
    }

    public void deployUserInfoData() {
        try {
            new ExportExcel(fileOut, "customer_salesman", Object.class, "Sheet5");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}