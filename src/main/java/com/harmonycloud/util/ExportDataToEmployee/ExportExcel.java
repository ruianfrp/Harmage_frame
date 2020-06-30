package com.harmonycloud.util.ExportDataToEmployee;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExportExcel<T extends Serializable> {
    private HSSFWorkbook workbook;
    private String tableName;
    private Class<T> type;
    private String sheetName;

    public ExportExcel(File excelFile, String tableName, Class<T> type,
                       String sheetName) throws FileNotFoundException,
            IOException {
        workbook = new HSSFWorkbook(new FileInputStream(excelFile));
        this.tableName = tableName;
        this.type = type;
        this.sheetName = sheetName;
        InsertData();
    }

    // 向表中写入数据
    public void InsertData() {
        log.info("Begin to export data to employee");
        ExcuteData excuteData = new ExcuteData();
        List<List> datas = getDatasInSheet(this.sheetName);
        // 向表中添加数据之前先删除表中数据
        String strSql = "delete from " + this.tableName;
        excuteData.ExcuData(strSql);
        // 拼接sql语句
        for (int i = 1; i < datas.size(); i++) {
            strSql = "insert into " + this.tableName + "(";
            List row = datas.get(i);
            for (short n = 0; n < row.size(); n++) {
                TableCell excel = (TableCell) row.get(n);
                if (n != row.size() - 1)
                    strSql += excel.get_name() + ",";
                else
                    strSql += excel.get_name() + ")";
            }
            strSql += " values (";
            for (int n = 0; n < row.size(); n++) {
                TableCell excel = (TableCell) row.get(n);
                try {
                    if (n != row.size() - 1) {
                        strSql += getTypeChangeValue(excel) + ",";
                    } else
                        strSql += getTypeChangeValue(excel) + ")";
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //执行sql
            excuteData.ExcuData(strSql);
        }

        log.info("Success to export data to employee");

    }

    /**
     * 获得表中的数据
     *
     * @param sheetName 表格索引(EXCEL 是多表文档,所以需要输入表索引号)
     * @return 由LIST构成的行和表
     */
    public List<List> getDatasInSheet(String sheetName) {
        List<List> result = new ArrayList<List>();
        // 获得指定的表
        HSSFSheet sheet = workbook.getSheet(sheetName);
        // 获得数据总行数
        int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {
            return result;
        }
        // 逐行读取数据
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            // 获得行对象
            HSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                List<TableCell> rowData = new ArrayList<TableCell>();
                // 获得本行中单元格的个数
                int columnCount = sheet.getRow(0).getLastCellNum();
                // 获得本行中各单元格中的数据
                for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    HSSFCell cell = row.getCell(columnIndex);
                    // 获得指定单元格中数据
                    Object cellStr = this.getCellString(cell);
                    TableCell TableCell = new TableCell();
                    TableCell.set_name(getCellString(sheet.getRow(0).getCell(columnIndex)).toString());
                    TableCell.set_value(cellStr == null ? "" : cellStr.toString());
                    rowData.add(TableCell);
                }
                result.add(rowData);
            }
        }
        return result;
    }

    /**
     * 获得单元格中的内容
     *
     * @param cell
     * @return result
     */
    protected Object getCellString(HSSFCell cell) {
        Object result = null;
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {

                case HSSFCell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                    result = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    result = null;
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    result = null;
                    break;
            }
        }
        return result;
    }

    // 根据类型返回相应的值
    @SuppressWarnings("unchecked")
    public String getTypeChangeValue(TableCell excelElement)
            throws RuntimeException, Exception {
        String colName = excelElement.get_name();
        String colValue = excelElement.get_value();
        String retValue = "";
        if (colValue.equals("")) {
            return null;
        }
        if(colName.equals("contacts_tel")){
            BigDecimal bd = new BigDecimal(colValue);
            colValue = bd.toPlainString();
        }
        retValue = "'" + colValue + "'";
        return retValue;
    }

}