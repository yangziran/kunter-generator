/**
 * 
 */
package org.generator.make;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.generator.config.PropertyHolder;
import org.generator.entity.Table;
import org.generator.util.StringUtility;

/**
 * 根据DB创建Excel格式的数据库设计文档
 * @author 阳自然
 * @version 1.0 2015年6月11日
 */
public class MakeDatabaseOfExcel {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        // 创建新的Excel 工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建修改履历Sheet
        Sheet sheet = workbook.createSheet("修改履历");

        // 创建格式
        CellStyle cellStyle = workbook.createCellStyle();
        // 单元格边框
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        // 垂直居中对齐
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        for (int i = 0; i < 33; i++) {
            Row row = sheet.createRow(i);
            // 设置行高
            row.setHeightInPoints(15);
            for (int j = 0; j < 6; j++) {
                Cell cell = row.createCell(j);
                if (j == 0) {
                    cell.setCellType(Cell.CELL_TYPE_FORMULA);
                }
                else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                }
                cell.setCellStyle(cellStyle);
            }
        }

        // 在索引0的位置创建行（最顶端的行）
        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(25);

        // 3.1 创建字体，设置其为粗体：
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);
        // 单元格边框
        cellStyle1.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle1.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle1.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle1.setBorderRight(CellStyle.BORDER_THIN);
        // 垂直居中对齐
        cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 水平居中对齐
        cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);

        // 3.3应用格式
        // 在索引0的位置创建单元格（左上端）
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle1);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("修正履历一览");

        // 在索引0的位置创建行（最顶端的行）
        row = sheet.createRow(1);
        cellStyle1.setFillBackgroundColor(new XSSFColor(java.awt.Color.BLUE).getIndexed());
        // 设置行高
        row.setHeightInPoints(30);
        cell = row.createCell(0);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle1);
        cell.setCellValue("修正日期");
        cell = row.createCell(1);
        cell.setCellStyle(cellStyle1);
        cell.setCellValue("修正表名");
        cell = row.createCell(2);
        cell.setCellStyle(cellStyle1);
        cell.setCellValue("表物理名");
        cell = row.createCell(3);
        cell.setCellStyle(cellStyle1);
        cell.setCellValue("修正内容");
        cell = row.createCell(4);
        cell.setCellStyle(cellStyle1);
        cell.setCellValue("修正理由");
        cell = row.createCell(5);
        cell.setCellStyle(cellStyle1);
        cell.setCellValue("修正者");

        // 合并单元格 第一行 A到F
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(region);
        // 冻结第1列第2行
        sheet.createFreezePane(1, 2);
        // 设置列宽
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 5500);
        sheet.setColumnWidth(2, 5500);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 2000);

        // 创建表一览Sheet
        sheet = workbook.createSheet("表一览");

        // 遍历表结构创建表设计书
        for (Table table : tables) {
            String sheetName = StringUtility.isNotEmpty(table.getRemarks()) ? table.getRemarks() : table.getTableName();
            sheet = workbook.createSheet(sheetName);
        }

        StringBuilder fileName = new StringBuilder();
        String model = PropertyHolder.getConfigProperty("model");
        if (StringUtility.isNotEmpty(model)) {
            fileName.append(model).append("-");
        }
        fileName.append("表结构一览.xlsx");

        // 新建一输出文件流
        FileOutputStream fileOut = new FileOutputStream(
                PropertyHolder.getConfigProperty("target") + fileName.toString());

        // 把相应的Excel 工作簿存盘
        workbook.write(fileOut);
        fileOut.flush();

        // 操作结束，关闭文件
        fileOut.close();
        workbook.close();
    }
}
