package com.harmonycloud.controller;

import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.util.ExportDataToMmber.DeployData;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.io.File;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;


/**
 * @author ：frp
 * @date ：Created in 2020/8/3 10:40
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="导入导出controller",tags={"导入导出操作接口"})
public class ImportAndExportController {

    @Autowired
    private HttpServletRequest request;

    /**
     * excel导入
     *
     * @param file       excel文件
     * @param importType 导入文件类型
     * @return res.message
     */
    @PostMapping("/excelImport")
    public Message excelImport(@RequestParam("file") File file,
                               @RequestParam("importType") String importType) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        DeployData deployData = new DeployData();
        deployData.excuteFile(file, importType);
        return res.message;
    }

    /**
     * excel导出
     *
     * @return res.message
     */
    @GetMapping("/excelExport")
    public Message excelExport() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }

        return res.message;
    }
}
