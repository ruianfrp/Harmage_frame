package com.harmonycloud.util.ExportDataToProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DeployData {
    private File fileOut;

    public void excute(String filepath) {
        fileOut = new File(filepath);
        this.deployUserInfoData();
    }

    public void deployUserInfoData() {
        try {
            new ExportExcel(fileOut, "project", Object.class, "Sheet1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}