package com.harmonycloud.controller;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.contract.ContractFileView;
import com.harmonycloud.bean.project.ProjectFileView;
import com.harmonycloud.config.FtpConfig;
import com.harmonycloud.service.ProjectFileService;
import com.harmonycloud.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

/**
 * @author ：frp
 * @date ：Created in 2019/7/20 12:13
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="文件controller",tags={"文件操作接口"})
@RequestMapping("/file/ftp")
public class FileController {

    @Value("${frame.ip}")
    private String frame;


    @Autowired
    ProjectFileService projectFileService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 项目文件上传
     *
     * @param files             文件
     * @param id                项目id
     * @param fileMilestoneName 里程碑或阶段名称
     * @param fileProjStage     项目阶段名称
     * @return res.message
     * @throws IOException
     */
    @PostMapping("/uploadFiles")
    @ApiOperation(value = "项目文件上传")
    public Message uploadFile(@RequestParam("file") MultipartFile[] files,
                              @RequestParam("projectId") Integer id,
                              @RequestParam("fileMilestoneName") String fileMilestoneName,
                              @RequestParam("fileProjStage") String fileProjStage) throws IOException {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
        FtpConfig ftpConfig = new FtpConfig();
        Map<String, Object> data = new HashMap<>();
        List<ProjectFileView> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String oldName = file.getOriginalFilename();// 获取文件原来的名字
            String fileType = oldName.substring(oldName.lastIndexOf("."));
            String newName = UploadUtils.generateRandonFileName(oldName) + " " + fileType;// 通过工具类产生新文件名称，防止重名
//            String projectName = projectFileService.selectProjectName(id);
            String SavePath = "/" + id + "/" + fileProjStage;//以项目id为目录
            if (fileMilestoneName != null) {
                SavePath = SavePath + "/" + fileMilestoneName;
            }
            String HttpPath = FtpUtil.UploadByConfig(ftpConfig, newName, SavePath, file.getInputStream());
            if (HttpPath != null) {
                log.info("项目文件上传成功");
                // 添加到数据库
                String fileUrl = "ftp://ftpuser:ftpuser@10.1.11.97:21/upload" + SavePath + "/" + newName;
                Integer result = projectFileService.insertFile(id, fileType, fileProjStage, fileMilestoneName, oldName, newName, SavePath, fileUrl);
                if (result > 0) {
                    log.info("数据库文件信息添加成功");
                    //返回添加的文件信息
                    ProjectFileView projectFileView = projectFileService.selectByInfo(id, newName, oldName);
                    if (projectFileView != null) {
                        log.info("上传文件信息返回成功");
                        list.add(projectFileView);
                    } else {
                        log.error("上传文件信息返回失败");
                        res.message.setMessage(403, "上传文件信息返回失败");
                    }
                } else {
                    log.error("数据库文件信息添加失败");
                    res.message.setMessage(403, "数据库文件信息添加失败");
                }
            } else {
                log.error("项目文件上传失败");
                res.message.setMessage(403, "项目文件上传失败");
            }
        }
        if (list.size() > 0) {
            data.put("list", list);
            res.message.setMessage(200, "文件上传成功", data);
        } else {
            log.warn("没有上传文件");
            res.message.setMessage(400, "没有上传文件");
        }
        return res.message;
    }

    /**
     * 总项目文件展示列表
     *
     * @param map projectId 项目id
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/listFiles")
    @ApiOperation(value = "总文件展示列表")
    public Message listAllFile(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
        Integer projectId = (Integer) map.get("projectId");
        Map<String, Object> data = new HashMap<>();
        List<ProjectFileView> list = projectFileService.listAllProjectFile(projectId);
        if (list.size() > 0) {
            log.info("项目文件列表返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "项目文件列表返回成功", data);
        } else {
            log.warn("项目文件返回为空");
            res.message.setMessage(400, "项目文件返回为空");
        }
        return res.message;
    }

    /**
     * 项目文件删除
     *
     * @param map fileId 文件id
     * @return res.message
     * @throws IOException
     */
    @PostMapping("/deleteFiles")
    @ApiOperation(value = "项目文件删除")
    public Message deleteFiles(@RequestBody Map map) throws IOException {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
        FtpConfig ftpConfig = new FtpConfig();
        Integer fileId = (Integer) map.get("fileId");
        ProjectFileView projectFileView = projectFileService.selectByFileId(fileId);
        String remotePath = ftpConfig.getFTP_BASEPATH() + projectFileView.getFileSavePath();
        String fileName = projectFileView.getFileNewName();
        boolean flag = FtpUtil.DeleteByConfig(ftpConfig, remotePath, fileName);
        if (flag) {
            log.info("项目文件删除成功");
            Integer result = projectFileService.deleteFile(fileId);
            if (result > 0) {
                log.info("数据库文件信息删除成功");
                res.message.setMessage(200, "项目文件删除成功");
            } else {
                log.error("数据库文件信息删除失败");
                res.message.setMessage(403, "数据库文件信息删除失败");
            }
        } else {
            log.info("项目文件删除失败");
            res.message.setMessage(403, "项目文件删除失败");
        }
        return res.message;
    }

    /**
     * 取消上传文件
     *
     * @param list
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/uploadCancel")
    @ApiOperation(value = "取消上传文件")
    public Message uploadCancel(@RequestBody List<ProjectFileView> list) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
//        if (res.message.getCode() == 401) {
//            log.error("Authorization参数校验失败");
//            return res.message;
//        }
        FtpConfig ftpConfig = new FtpConfig();
        if (list.size() > 0) {
            for (short i = 0; i < list.size(); i++) {
                Integer fileId = list.get(i).getFileId();
                ProjectFileView projectFileView = projectFileService.selectByFileId(fileId);
                String remotePath = ftpConfig.getFTP_BASEPATH() + projectFileView.getFileSavePath();
                String fileName = projectFileView.getFileNewName();
                boolean flag = FtpUtil.DeleteByConfig(ftpConfig, remotePath, fileName);
                if (flag) {
                    log.info("项目文件删除成功");
                    Integer result = projectFileService.deleteFile(fileId);
                    if (result > 0) {
                        log.info("数据库文件信息删除成功");
                    } else {
                        log.error("数据库文件信息删除失败");
                    }
                } else {
                    log.info("项目文件删除失败");
                }
            }
            log.info("取消成功，已删除文件");
            res.message.setMessage(200, "取消成功，已删除文件");
        } else {
            log.warn("取消成功，无可删除文件");
            res.message.setMessage(200, "取消成功，已删除文件");
        }
        return res.message;
    }

    /**
     * 打包项目所有文件
     *
     * @param map
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/packageFile")
    @ApiOperation(value = "打包项目所有文件")
    public Message zip(@RequestBody Map map) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        Map<String, Object> data = new HashMap<>();
        Integer projectId = (Integer) map.get("projectId");
        String sourceFilePath = "/var/upload/" + projectId;
        String tarFilePath = "/var/upload/" + projectId + ".zip";
        boolean result = ZipUtils.toZip(sourceFilePath, tarFilePath, true);
        if (result) {
            log.info("文件打包成功");
            String url = frame + "/" + projectId + ".zip";
            data.put("url", url);
            res.message.setMessage(200, "文件压缩成功", data);
        } else {
            res.message.setMessage(403, "文件压缩失败");
        }
        return res.message;
    }

    /**
     * 通过挂载直接访问url文件地址
     *
     * @param files
     * @param id
     * @param fileMilestoneName
     * @param fileProjStage
     * @return
     * @throws IOException
     */
    @PostMapping("/simpleUploadFile")
    @ApiOperation(value = "文件本地上传")
    public Message simpleUpload(@RequestParam("file") MultipartFile[] files,
                                @RequestParam("projectId") Integer id,
                                @RequestParam("fileMilestoneName") String fileMilestoneName,
                                @RequestParam("fileProjStage") String fileProjStage) throws IOException {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        Map<String, Object> data = new HashMap<>();
        List<ProjectFileView> list = new ArrayList<>();
        String dataServerDestDir = "/var/upload";
        for (MultipartFile file : files) {
            String oldName = file.getOriginalFilename();// 获取文件原来的名字
            String fileType = oldName.substring(oldName.lastIndexOf("."));
//            log.info(oldName);
            String newName = UploadUtils.generateRandonFileName(oldName) + fileType;// 通过工具类产生新文件名称，防止重名
//            String projectName = projectFileService.selectProjectName(id);
            String SavePath = "/" + id + "/" + fileProjStage;//以项目id为目录
            if (fileMilestoneName != null) {
                SavePath = SavePath + "/" + fileMilestoneName;
            }
            File dest = new File(dataServerDestDir + SavePath);
            dest.setWritable(true, false);
            if (!dest.exists()) { //判断文件目录是否存在
                dest.mkdirs();
            }
            InputStream fis = file.getInputStream();
            OutputStream output = new FileOutputStream(dataServerDestDir + SavePath + "/" + newName);
            int i;
            byte[] b = new byte[4096];
            while ((i = fis.read(b)) != -1) {
                output.write(b, 0, i);
            }
            log.info("项目文件上传成功");
            output.close();
            fis.close();
            // 添加到数据库
            String fileUrl = frame + SavePath + "/" + newName;
            Integer result = projectFileService.insertFile(id, fileType, fileProjStage, fileMilestoneName, oldName, newName, SavePath, fileUrl);
            if (result > 0) {
                log.info("数据库文件信息添加成功");
                //返回添加的文件信息
                ProjectFileView projectFileView = projectFileService.selectByInfo(id, newName, oldName);
                if (projectFileView != null) {
                    log.info("上传文件信息返回成功");
                    list.add(projectFileView);
                } else {
                    log.error("上传文件信息返回失败");
                    res.message.setMessage(400, "上传文件信息返回失败");
                }
            } else {
                log.error("数据库文件信息添加失败");
                res.message.setMessage(400, "数据库文件信息添加失败");
            }
        }
        if (list.size() > 0) {
            data.put("list", list);
            res.message.setMessage(200, "文件上传成功", data);
        } else {
            log.warn("没有上传文件");
            res.message.setMessage(400, "没有上传文件");
        }
        return res.message;
    }

    @PostMapping("/simpleDeleteFile")
    @ApiOperation(value = "文件删除")
    public Message simpleDelete(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        Integer fileId = (Integer) map.get("fileId");
        ProjectFileView projectFileView = projectFileService.selectByFileId(fileId);
        if (projectFileView != null) {
            boolean flag = false;
            String filePath = "/var/upload" + projectFileView.getFileSavePath() + "/" + projectFileView.getFileNewName();
            File file = new File(filePath);
            if (!file.exists()) {
                flag = false;
            }
            try {
                flag = file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag) {
                log.info("项目文件删除成功");
                Integer result = projectFileService.deleteFile(fileId);
                if (result > 0) {
                    log.info("数据库文件信息删除成功");
                    res.message.setMessage(200, "项目文件删除成功");
                } else {
                    log.error("数据库文件信息删除失败");
                    res.message.setMessage(403, "数据库文件信息删除失败");
                }
            } else {
                log.info("项目文件删除失败");
                res.message.setMessage(403, "项目文件删除失败");
            }
        }
        return res.message;
    }

    @PostMapping("/simpleUploadCancel")
    @ApiOperation(value = "取消上传文件")
    public Message simpleUploadCancel(@RequestBody List<ProjectFileView> list) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (list.size() > 0) {
            for (short i = 0; i < list.size(); i++) {
                Integer fileId = list.get(i).getFileId();
                ProjectFileView projectFileView = projectFileService.selectByFileId(fileId);
                if (projectFileView != null) {
                    boolean flag = false;
                    String filePath = "/var/upload" + projectFileView.getFileSavePath() + "/" + projectFileView.getFileNewName();
                    File file = new File(filePath);
                    if (!file.exists()) {
                        flag = false;
                    }
                    try {
                        flag = file.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (flag) {
                        log.info("项目文件删除成功");
                        Integer result = projectFileService.deleteFile(fileId);
                        if (result > 0) {
                            log.info("数据库文件信息删除成功");
                        } else {
                            log.error("数据库文件信息删除失败");
                        }
                    } else {
                        log.info("项目文件删除失败");
                    }
                }
            }
            log.info("取消成功，已删除文件");
            res.message.setMessage(200, "取消成功，已删除文件");
        } else {
            log.warn("取消成功，无可删除文件");
            res.message.setMessage(200, "取消成功，已删除文件");
        }
        return res.message;
    }

    /**
     * 合同文件加密上传
     *
     * @param files      文件
     * @param customerId 客户id
     * @param projectId  项目id
     * @return res.message
     * @throws Exception
     */
    @PostMapping("/encryptUploadFile")
    @ApiOperation(value = "文件加密上传")
    public Message encryptUploadFile(@RequestParam("file") MultipartFile[] files,
                                     @RequestParam("customerId") Integer customerId,
                                     @RequestParam("projectId") Integer projectId) throws Exception {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        Map<String, Object> data = new HashMap<>();
        List<ContractFileView> list = new ArrayList<>();
        String dataServerDestDir = "/var/upload/ContractFile";
        for (MultipartFile file : files) {
            ContractFileView contractFileView = new ContractFileView();
            contractFileView.setFkCustomerId(customerId);
            contractFileView.setFkProjectId(projectId);
            String oldName = file.getOriginalFilename();// 获取文件原来的名字
            contractFileView.setFileOldName(oldName);
            String fileType = oldName.substring(oldName.lastIndexOf("."));
            String newName = UploadUtils.generateRandonFileName(oldName) + fileType;// 通过工具类产生新文件名称，防止重名
            contractFileView.setFileNewName(newName);
            String SavePath = "/" + customerId + "/" + projectId;
            File dest = new File(dataServerDestDir + SavePath);
            dest.setWritable(true, false);
            if (!dest.exists()) { //判断文件目录是否存在
                dest.mkdirs();
            }
            String path = dataServerDestDir + SavePath + "/" + newName;
            contractFileView.setContractPath(path);
            DESUtil td = new DESUtil("harmonycloud");
            td.encrypt(file, path);
            String fileUrl = frame + SavePath + "/" + newName;
            contractFileView.setContractUrl(fileUrl);
            Integer result = projectFileService.insertContractFile(contractFileView);
            if (result > 0) {
                log.info("合同文件信息添加成功");
                list.add(contractFileView);
            } else {
                log.error("合同文件信息添加失败");
                res.message.setMessage(400, "合同文件信息添加失败");
            }
            log.info("项目文件上传成功");
        }
        if (list.size() > 0) {
            data.put("list", list);
            res.message.setMessage(200, "文件上传成功", data);
        } else {
            log.warn("没有上传文件");
            res.message.setMessage(400, "没有上传文件");
        }
        return res.message;
    }
}
