package com.harmonycloud.controller;

import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.project.ProjectCostRecord;
import com.harmonycloud.bean.project.ProjectFileView;
import com.harmonycloud.service.ProjectCostRecordService;
import com.taobao.api.internal.toplink.remoting.HandshakingHeadersBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

@RestController
@Slf4j
@Api(value = "项目成本Controller",tags = {"项目成本操作接口"})
@RequestMapping("/projectCost")
public class ProjectCostRecordController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    ProjectCostRecordService projectCostRecordService;

    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";
    private final static int OFFSET = 2;

    /*
    * 查询项目成本记录
     */
    @PostMapping("/selectProjectCostRecord")
    @ApiOperation(value="查询项目成本记录",notes = "根据项目id返回成本记录")
    @ResponseBody
    public Map selectProjectCostRecord(@RequestBody @ApiParam(name="projectId",value = "项目id",required = true)Map map){
        //VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        Integer projectId = Integer.parseInt(map.get("projectId").toString());

        Map<String, Object> data = new HashMap<>();
        List<ProjectCostRecord> listProjectCostRecord = projectCostRecordService.selectProjectCostRecord(projectId);
        String projectName = projectCostRecordService.selectProjectName(projectId);

        if (listProjectCostRecord.size() > 0) {
            log.info("项目成本列表返回成功");
            data.put("list", listProjectCostRecord);
            data.put("total", listProjectCostRecord.size());
            data.put("projectName",projectName);
            //res.message.setMessage(200, "项目成本列表返回成功", data);
        } else {
            log.warn("项目成本返回为空");
            //res.message.setMessage(400, "项目成本返回为空");
        }
        //return res.message;
        return data;
    }

    @PostMapping("/postExcel")
    @ApiOperation(value="上传项目成本报表",notes = "只读取当月成本列进行更新")
    @ResponseBody
    public Map postExcel(@RequestParam("file")MultipartFile file) throws Exception{
/*        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }*/
        String fileName = file.getOriginalFilename();
        Map<String,Object> map = new HashMap<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            log.error("上传文件格式不正确");
            map.put("error","上传文件格式不正确");
        }
        Workbook workbook = null;
        Cell cell = null;
        List<ProjectCostRecord> lists = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int moneyCol = calendar.get(Calendar.MONTH)+OFFSET;
        map.put("monthCol",moneyCol);
        try{
            /*
            通过流操作，将传入的数据转为EXCEL对象*
             */
            InputStream inputStream = file.getInputStream();
            //判断版本
            if (fileName.endsWith(EXCEL2007)) {
                workbook = new XSSFWorkbook(inputStream);
            }
            if (fileName.endsWith(EXCEL2003)) {
                workbook = new HSSFWorkbook(inputStream);
            }

            /*
            * 解析EXCEL文件
            * */
            if(workbook!=null){
                Sheet sheet = workbook.getSheetAt(0);
                int lastRowNumber = sheet.getLastRowNum();
                for(int i=1;i<=lastRowNumber;i++){
                    ProjectCostRecord projectCostRecord = new ProjectCostRecord();
                    Row row = sheet.getRow(i);
                    /*
                    * 获取excel一行记录所需信息
                    * */
                    String projeceNameInExcel = row.getCell(0).getStringCellValue();
                    Double money = row.getCell(moneyCol).getNumericCellValue();

                    /*
                    * 1.封装成本记录对象
                    * */
                    projectCostRecord.setImportTime(new Date());
                    projectCostRecord.setMoney(row.getCell(moneyCol).getNumericCellValue());
                    Integer projectId = projectCostRecordService.selectProjectId(projeceNameInExcel);
                    if(projectId==0) continue; //不更新id为0的项目
                    projectCostRecord.setFkProjectId(projectId);
                    /*
                    2.根据fk_project_id和money值更新project表
                    * */
                    Integer result = projectCostRecordService.updateProjectCurrentCost(projectId,money);
                    if(result>0){
                        /*
                        * 返回成功更新的信息并插入到project_cost_record表中
                        * */
                        lists.add(projectCostRecord);
                        projectCostRecordService.insertCostRecordDataBase(projectCostRecord);
                    }else{
                        log.error("更新失败");
                    }
                }
                map.put("data",lists);
            }
        }catch (Exception e){
            map.put("error",e.getMessage());
        }finally {
            if(workbook!=null){
                try{
                    workbook.close();
                }catch (Exception e){
                    map.put("error","解析失败!");
                }
            }
        }
        return map;
    }











    /*    @GetMapping("/generateExcel")
    @ResponseBody
    public Map generateExcelToDeskTop() throws Exception{
        //获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop+"/testexcel.xls";
        //创建输出路径的IO流
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);

        //创建工作簿对象，并获取工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        //创建行对象，设置header
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("fk_project_id");
        row.createCell(2).setCellValue("money");
        row.createCell(3).setCellValue("import_time");

        //创建行对象，设置每行数据
        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("2");
        row1.createCell(2).setCellValue("123.25");
        row1.createCell(3).setCellValue(new Date());

        //写出数据，关闭资源
        workbook.setActiveSheet(0);
        workbook.write(outputStream);
        outputStream.close();
        Map<String,Object> map = new HashMap<>();
        map.put("data",workbook.toString());
        return map;
    }*/


/*    @GetMapping("readExcel")
    @ResponseBody
    public List<HashMap> readExcelFromDeskTop() throws Exception{
        //获取桌面文件
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop+"/testexcel.xls";
        //通过流获取本地文件
        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
        //创建工作簿对象，并获取工作表
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workbook.getSheetAt(0);

        //从工作表中获取行数，并遍历
        int lastRowIndex = sheet.getLastRowNum();
        Map<String,Object> map =new HashMap<>();
        map.put("总行数",lastRowIndex);
        ArrayList<HashMap> list = new ArrayList<>();
        for(int i=1;i<=lastRowIndex;i++){
            //获取每行数据
            HSSFRow row = sheet.getRow(i);
            if(row ==null) break;
            //从每行中获取参数
            HashMap<String,String> data = new HashMap<String, String>();
            short  lastCellNum = row.getLastCellNum();
            map.put("总列数",lastCellNum);
            for(int j=0;j<lastCellNum;j++){
                //设置返回值类型
                row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                String cellValue = row.getCell(j).getStringCellValue();
                data.put("value"+j,cellValue);
            }
            //结果封装到集合中
            list.add(data);
        }
        //关闭资源，输出
        bufferedInputStream.close();
        return list;
    }*/

}
