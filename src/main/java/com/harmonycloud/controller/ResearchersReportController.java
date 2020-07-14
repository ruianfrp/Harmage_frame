package com.harmonycloud.controller;

import com.alibaba.fastjson.JSON;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.execl.EmployeeReportExecl;
import com.harmonycloud.bean.execl.WorkingReportExcel;
import com.harmonycloud.bean.report.EmployeeReport;
import com.harmonycloud.service.MemberService;
import com.harmonycloud.service.WorkingReportService;
import com.harmonycloud.util.execl.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "人员报表controller", tags = {"人员在项目信息"})
@RequestMapping("/report")
public class ResearchersReportController {

    private static java.text.DecimalFormat decimalFormat  = new java.text.DecimalFormat("#.00");

    @Autowired
    MemberService memberService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    WorkingReportService workingReportService;

    @GetMapping("/getListReport")
    @ApiOperation(value = "返回人员项目信息列表")
    public Message getListReport() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        List<EmployeeReportExecl> data = memberService.getProjectTimeReport();
        int days = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        data.stream().forEach(employeeReport -> {
            if (employeeReport.getInputDays() < 0) {
                employeeReport.setInputDays(0);
            }
            if (employeeReport.getInputDays() > days) {
                employeeReport.setInputDays(days);
            }
        });
        System.out.println(data);
        res.message.setMessage(200, "返回人员项目信息列表成功", data);
        return res.message;
    }

    @GetMapping("/getListReportNew")
    @ApiOperation(value = "返回人员项目信息列表(新：数据库数据获取)")
    public Message getListReportNew(@RequestParam(value = "开始时间") String startTime, @RequestParam(value = "结束时间") String endTime) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        List<WorkingReportExcel> data = workingReportService.getTotalWorkingTimeReport(startTime, endTime);
        Message message = new Message();
        System.out.println(data);
        message.setMessage(200, "返回人员项目信息列表成功", data);
        return message;
    }

    @RequestMapping(value = "/exportExcelNew", method = RequestMethod.GET)
    public void exportExcelNew(HttpServletResponse response, @RequestParam(value = "开始时间") String startTime, @RequestParam(value = "结束时间") String endTime)  throws IOException {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
        }
        List<WorkingReportExcel> data = workingReportService.getTotalWorkingTimeReport(startTime, endTime);
        data.stream().forEach(workingReportExcel -> {
            workingReportExcel.setWorkingHours(Double.valueOf(decimalFormat.format(Double.valueOf(workingReportExcel.getWorkingHours()) / 8)));
            workingReportExcel.setTimePercentage(Double.valueOf(decimalFormat.format(Double.valueOf(workingReportExcel.getWorkingHours()) / workingReportExcel.getTotalDays())));
        });
        ExcelUtils.writeExcel(response, "人员报表", data, WorkingReportExcel.class);
    }


    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response)  throws IOException {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
        }
        List<EmployeeReportExecl> data = memberService.getProjectTimeReport();
        int days = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        data.stream().forEach(employeeReport -> {
            if (employeeReport.getInputDays() < 0) {
                employeeReport.setInputDays(0);
            }
            if (employeeReport.getInputDays() > days) {
                employeeReport.setInputDays(days);
            }
        });
        ExcelUtils.writeExcel(response, "人员报表", data, EmployeeReportExecl.class);
    }

    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
    public void readExcel(MultipartFile file){

    }
}
