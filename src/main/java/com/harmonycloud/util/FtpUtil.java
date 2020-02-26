package com.harmonycloud.util;


import java.io.*;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.harmonycloud.config.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class FtpUtil {

    /**
     * 向FTP服务器上传文件
     *
     * @param ftpConfig   由spring管理的FtpConfig配置，可以在使用此方法的类中通过@AutoWared注入该属性
     * @param NewName     文件新名字（不重名）
     * @param SavePath    保存的地址
     * @param inputStream 输入流
     * @return HttpPath
     * @throws IOException
     */
    public static String UploadByConfig(FtpConfig ftpConfig, String NewName, String SavePath,
                                        InputStream inputStream) throws IOException {
        String HttpPath = null;

        boolean flag = uploadFile(ftpConfig.getFTP_ADDRESS(), ftpConfig.getFTP_PORT(), ftpConfig.getFTP_USERNAME(),
                ftpConfig.getFTP_PASSWORD(), ftpConfig.getFTP_BASEPATH(), SavePath, NewName, inputStream);

        if (!flag) {
            log.error("上传失败");
            return HttpPath;
        }
        HttpPath = ftpConfig.getFTP_BASEPATH() + SavePath + "/" + NewName;
        log.info("上传成功===" + HttpPath);
        return HttpPath;
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器hostname
     * @param ftpPort  FTP服务器端口
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, String ftpPort, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        int port = Integer.parseInt(ftpPort);
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            ftp.login(username, password);

            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            // 切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                // 如果目录不存在创建目录
                filePath = new String(filePath.getBytes("UTF-8"), "iso-8859-1");
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir))
                        continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            // 设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();// 允许被动连接--访问远程ftp时需要
            // 上传文件
            filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * ftp下载文件的方法
     *
     * @param ftpConfig  由spring管理的FtpConfig配置，可以在使用此方法的类中通过@AutoWared注入该属性
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return 若上传成功，返回
     * @throws IOException
     */
    public static boolean DowmloadByConfig(FtpConfig ftpConfig, String remotePath, String fileName, String localPath) throws IOException {

        boolean flag = downloadFile(ftpConfig.getFTP_ADDRESS(), Integer.parseInt(ftpConfig.getFTP_PORT()), ftpConfig.getFTP_USERNAME(),
                ftpConfig.getFTP_PASSWORD(), remotePath, fileName, localPath);
        if (!flag) {
            log.error("下载失败");
            return false;
        }
        log.info("下载成功");
        return true;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath, String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            System.out.println("开始下载文件");
            int reply;
            ftp.connect(host, port);
            ftp.login(username, password);
            ftp.setBufferSize(1024);
            ftp.setControlEncoding("UTF-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置文件传输类型为二进制
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equalsIgnoreCase(fileName)) {
                    File localFile = new File(localPath + "/" + fileName);
                    System.out.println(localFile);
                    System.out.println(fileName);
                    if (!localFile.exists()) {
                        FileOutputStream is = new FileOutputStream(localFile);
                        System.out.println(is);
                        result = ftp.retrieveFile(ff.getName(), is);
                        is.close();
                    }
                }
            }
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {

                }
            }
            return result;
        }
    }


    public static boolean DeleteByConfig(FtpConfig ftpConfig, String remotePath, String fileName) throws IOException {

        boolean flag = deleteFile(ftpConfig.getFTP_ADDRESS(), Integer.parseInt(ftpConfig.getFTP_PORT()), ftpConfig.getFTP_USERNAME(),
                ftpConfig.getFTP_PASSWORD(), remotePath, fileName);
        if (!flag) {
            log.error("删除失败");
            return false;
        }
        log.info("删除成功");
        return true;
    }

    public static boolean deleteFile(String host, int ftpPort, String username, String password,
                                     String remotePath, String filename) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, ftpPort);// 连接FTP服务器
            ftp.login(username, password);

            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);
            int flag = ftp.dele(filename);
            if (flag == 250) {
                result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}