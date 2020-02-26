package com.harmonycloud.util;

import java.io.*;

import ch.ethz.ssh2.*;
import org.springframework.web.multipart.MultipartFile;

public class SimpleFileUtil {

    private static SimpleFileUtil instance;

    private String ip;

    private int port;

    private String name;

    private String password;

    /**
     * 私有化默认构造函数
     * 实例化对象只能通过getInstance
     */
    private SimpleFileUtil(){

    }

    /**
     * 私有化有参构造函数
     * @param ip 服务器ip
     * @param port 服务器端口 22
     * @param name 登录名
     * @param password 登录密码
     */
    private SimpleFileUtil(String ip,int port,String name,String password){
        this.ip = ip ;
        this.port = port;
        this.name = name;
        this.password = password;
    }

    /**
     * download
     * @param remoteFile 服务器上的文件名
     * @param remoteTargetDirectory 服务器上文件的所在路径
     * @param newPath 下载文件的路径
     */
    public void downloadFile(String remoteFile, String remoteTargetDirectory,String newPath){
        Connection connection = new Connection(ip,port);

        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(name,password);
            if(isAuthenticated){
                SCPClient scpClient = connection.createSCPClient();
                SCPInputStream sis = scpClient.get(remoteTargetDirectory + "/" + remoteFile);
                File f = new File(newPath);
                if(!f.exists()){
                    f.mkdirs();
                }
                File newFile = new File(newPath + remoteFile);
                FileOutputStream fos = new FileOutputStream(newFile);
                byte[] b = new byte[4096];
                int i;
                while ((i = sis.read(b)) != -1){
                    fos.write(b,0, i);
                }
                fos.flush();
                fos.close();
                sis.close();
                connection.close();
                System.out.println("download ok");
            }else{
                System.out.println("连接建立失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  获取服务器上相应文件的流
     * @param remoteFile 文件名
     * @param remoteTargetDirectory 文件路径
     * @return
     * @throws IOException
     */
    public SCPInputStream getStream(String remoteFile, String remoteTargetDirectory) throws IOException {
        Connection connection = new Connection(ip,port);
        connection.connect();
        boolean isAuthenticated = connection.authenticateWithPassword(name,password);
        if(!isAuthenticated){
            System.out.println("连接建立失败");
            return null;
        }
        SCPClient scpClient = connection.createSCPClient();
        return scpClient.get(remoteTargetDirectory + "/" + remoteFile);
    }

    /**
     * 上传文件到服务器
     * @param f 文件对象
     * @param remoteTargetDirectory 上传路径
     * @param mode 默认为null
     */
    public boolean uploadFile(MultipartFile f, String remoteTargetDirectory, String mode, String newName) {
        Connection connection = new Connection(ip,port);

        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(name,password);
            if(!isAuthenticated){
                System.out.println("连接建立失败");
                return false;
            }
            SCPClient scpClient = new SCPClient(connection);
            SCPOutputStream os = scpClient.put(newName,f.getSize(),remoteTargetDirectory,mode);
            byte[] b = new byte[4096];
            InputStream fis = f.getInputStream();
            int i;
            while ((i = fis.read(b)) != -1) {
                os.write(b, 0, i);
            }
            os.flush();
            fis.close();
            os.close();
            connection.close();
            System.out.println("upload ok");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过ch.ethz.ssh2.Session执行命令删除文件
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath){
        Connection connection = new Connection(ip,port);
        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(name,password);
            if(!isAuthenticated){
                System.out.println("连接建立失败");
                return false;
            }
            Session session;
            session = connection.openSession();
            session.execCommand("rm -f " + filePath);
            session.close();
            connection.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 单例模式
     * 懒汉式
     * 线程安全
     * @return
     */
    public static SimpleFileUtil getInstance(){
        if(null == instance){
            synchronized (SimpleFileUtil.class){
                if(null == instance){
                    instance = new SimpleFileUtil();
                }
            }
        }
        return instance;
    }


    public static SimpleFileUtil getInstance(String ip,int port,String name,String password){
        if(null == instance){
            synchronized (SimpleFileUtil.class){
                if(null == instance){
                    instance = new SimpleFileUtil(ip,port,name,password);
                }
            }
        }
        return instance;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}