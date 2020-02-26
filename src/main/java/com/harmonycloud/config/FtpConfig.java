package com.harmonycloud.config;

public class FtpConfig {

    /**
     * 获取IP地址
     */
    private String FTP_ADDRESS = "10.1.11.89";

    /**
     * 端口号
     */
    private String FTP_PORT = "21";

    /**
     * 用户名
     */
    private String FTP_USERNAME="ftpuser";

    /**
   * 密码
   */
    private String FTP_PASSWORD="ftpuser";

    /**
     * 基本路径
     */
    private String FTP_BASEPATH = "/var/ftp/upload";

    /**
     * 下载地址地基础url，这个是配置的图片服务器的地址,最后访问图片时候，需要用该基础地址    
     */
    private String IMAGE_BASE_URL = "http://10.1.11.89/picture";


    public String getFTP_ADDRESS() {
        return FTP_ADDRESS;
    }

    public void setFTP_ADDRESS(String FTP_ADDRESS) {
        this.FTP_ADDRESS = FTP_ADDRESS;
    }

    public String getFTP_PORT() {
        return FTP_PORT;
    }

    public void setFTP_PORT(String FTP_PORT) {
        this.FTP_PORT = FTP_PORT;
    }

    public String getFTP_BASEPATH() {
        return FTP_BASEPATH;
    }

    public void setFTP_BASEPATH(String FTP_BASEPATH) {
        this.FTP_BASEPATH = FTP_BASEPATH;
    }

    public String getIMAGE_BASE_URL() {
        return IMAGE_BASE_URL;
    }

    public String getFTP_USERNAME() {
        return FTP_USERNAME;
    }

    public void setFTP_USERNAME(String FTP_USERNAME) {
        this.FTP_USERNAME = FTP_USERNAME;
    }

    public String getFTP_PASSWORD() {
        return FTP_PASSWORD;
    }

    public void setFTP_PASSWORD(String FTP_PASSWORD) {
        this.FTP_PASSWORD = FTP_PASSWORD;
    }

    public void setIMAGE_BASE_URL(String IMAGE_BASE_URL) {
        this.IMAGE_BASE_URL = IMAGE_BASE_URL;
    }
}
