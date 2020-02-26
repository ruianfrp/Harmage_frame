package com.harmonycloud.util.ExportDataToUser;

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
            new ExportExcel(fileOut, "customer_document_record", Object.class, "document_record");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}