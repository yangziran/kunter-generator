/**
 *
 */
package cn.kunter.common.generator.make.database;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Column;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.make.GetTableConfig;
import cn.kunter.common.generator.util.StringUtility;

/**
 * 根据DB创建Excel格式的数据库设计文档
 * @author 阳自然
 * @version 1.0 2015年6月11日
 */
public class MakeDatabaseOfExcel {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();
        MakeDatabaseOfExcel.makerSheet(tables);
    }

    /**
     * 生成Sheet
     * @param tables
     * @throws Exception
     * @author 阳自然
     */
    public static void makerSheet(List<Table> tables) throws Exception {

        // 创建新的Excel 工作簿
        Workbook workbook = new XSSFWorkbook();

        // 生成履历Sheet
        makerHisSheet(workbook);

        // 生成表一览
        makerListSheet(workbook, tables);

        // 遍历表结构创建表设计书
        for (Table table : tables) {
            makerTableSheet(workbook, table);
        }

        // 新建一输出文件流
        FileOutputStream fileOut = new FileOutputStream(PropertyHolder.getConfigProperty("target") + "表结构一览.xlsx");

        // 把相应的Excel 工作簿存盘
        workbook.write(fileOut);
        fileOut.flush();

        // 操作结束，关闭文件
        fileOut.close();
        workbook.close();
    }

    /**
     * 生成TableSheet
     * @param workbook
     * @param table
     * @throws Exception
     * @author 阳自然
     */
    public static void makerTableSheet(Workbook workbook, Table table) throws Exception {

        // 生成表设计Sheet 备注不规范会出现错误 所以使用TableName为Sheet名称
        String tableName = table.getTableName();
        // 处理因Excel的sheet名称不能超过31字符长度，自动截取sheetName出现重复问题
        if (tableName.length() > 31) {
            // 分割表名，获取首字母 TODO 如果首字母相同则还是会出现重复的问题
            tableName = StringUtility.convertTableNameToAlias(tableName, "_");
        }
        Sheet sheet = null;
        try {
            sheet = workbook.createSheet(tableName);
        } catch (IllegalArgumentException ex) {
            sheet = workbook.createSheet(tableName + "1");
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 350);
        // 设置默认列宽
        sheet.setDefaultColumnWidth(2);

        CellStyle cellStyle = getCellStyle(workbook);
        // 设置所有单元格默认格式
        for (int i = 0; i < table.getCols().size() + 5; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 44; j++) {
                Cell cell = row.createCell(j, CellType.STRING);
                cell.setCellStyle(cellStyle);
            }
        }

        // 列合并
        // 第一行处理
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(region);
        Row row = sheet.getRow(0);
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(getCellStyleBlue(workbook));
        cell.setCellValue("表名");
        region = new CellRangeAddress(0, 0, 7, 21);
        sheet.addMergedRegion(region);
        cell = row.createCell(7, CellType.STRING);
        cell.setCellStyle(getCellStyle(workbook));
        cell.setCellValue(table.getRemarks());
        region = new CellRangeAddress(0, 0, 22, 26);
        sheet.addMergedRegion(region);
        cell = row.createCell(22, CellType.STRING);
        cell.setCellStyle(getCellStyleBlue(workbook));
        cell.setCellValue("作成者");
        region = new CellRangeAddress(0, 0, 27, 32);
        sheet.addMergedRegion(region);
        cell = row.createCell(27, CellType.STRING);
        cell.setCellStyle(getCellStyle(workbook));
        cell.setCellValue("自动生成");
        region = new CellRangeAddress(0, 0, 33, 37);
        sheet.addMergedRegion(region);
        cell = row.createCell(33, CellType.STRING);
        cell.setCellStyle(getCellStyleBlue(workbook));
        cell.setCellValue("修正者");
        region = new CellRangeAddress(0, 0, 38, 43);
        sheet.addMergedRegion(region);
        cell = row.createCell(38, CellType.STRING);
        cell.setCellStyle(getCellStyle(workbook));

        // 创建日期格式的样式
        CellStyle cellStyleDate = getCellStyle(workbook);
        DataFormat format = workbook.createDataFormat();
        cellStyleDate.setDataFormat(format.getFormat("yyyy-mm-dd"));
        // 第二行处理
        row = sheet.getRow(1);
        region = new CellRangeAddress(1, 1, 0, 6);
        sheet.addMergedRegion(region);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(getCellStyleBlue(workbook));
        cell.setCellValue("物理名");
        region = new CellRangeAddress(1, 1, 7, 21);
        sheet.addMergedRegion(region);
        cell = row.createCell(7, CellType.STRING);
        cell.setCellStyle(getCellStyle(workbook));
        cell.setCellValue(table.getTableName());
        region = new CellRangeAddress(1, 1, 22, 26);
        sheet.addMergedRegion(region);
        cell = row.createCell(22, CellType.STRING);
        cell.setCellStyle(getCellStyleBlue(workbook));
        cell.setCellValue("作成日期");
        region = new CellRangeAddress(1, 1, 27, 32);
        sheet.addMergedRegion(region);
        cell = row.createCell(27, CellType.STRING);
        cell.setCellStyle(cellStyleDate);
        cell.setCellValue(new Date());
        region = new CellRangeAddress(1, 1, 33, 37);
        sheet.addMergedRegion(region);
        cell = row.createCell(33, CellType.STRING);
        cell.setCellStyle(getCellStyleBlue(workbook));
        cell.setCellValue("修正日期");
        region = new CellRangeAddress(1, 1, 38, 43);
        sheet.addMergedRegion(region);
        cell = row.createCell(38, CellType.STRING);
        cell.setCellStyle(cellStyleDate);

        CreationHelper createHelper = workbook.getCreationHelper();
        // 第三行处理
        row = sheet.getRow(2);
        region = new CellRangeAddress(2, 2, 0, 6);
        sheet.addMergedRegion(region);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(getCellStyleBlue(workbook));
        cell.setCellValue("概要");
        region = new CellRangeAddress(2, 2, 7, 37);
        sheet.addMergedRegion(region);
        cell = row.createCell(7, CellType.STRING);
        cell.setCellStyle(getCellStyle(workbook));
        cell.setCellValue(table.getRemarks());
        region = new CellRangeAddress(2, 2, 38, 43);
        sheet.addMergedRegion(region);
        Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        link.setAddress("#表一览!A1");
        cell = row.createCell(38, CellType.STRING);
        cell.setCellStyle(getLinkStyle(workbook));
        cell.setHyperlink(link);
        cell.setCellValue("返回列表");

        // 第四行处理
        row = sheet.getRow(3);
        region = new CellRangeAddress(3, 3, 0, 43);
        sheet.addMergedRegion(region);

        CellStyle sellStyleTitle = getCellStyleBlue(workbook);
        sellStyleTitle.setAlignment(HorizontalAlignment.CENTER);

        // 第五行处理 标题
        row = sheet.getRow(4);
        region = new CellRangeAddress(4, 4, 0, 1);
        sheet.addMergedRegion(region);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("编号");
        region = new CellRangeAddress(4, 4, 2, 7);
        sheet.addMergedRegion(region);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("列名");
        region = new CellRangeAddress(4, 4, 8, 12);
        sheet.addMergedRegion(region);
        cell = row.createCell(8, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("物理名");
        region = new CellRangeAddress(4, 4, 13, 17);
        sheet.addMergedRegion(region);
        cell = row.createCell(13, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("类型");
        region = new CellRangeAddress(4, 4, 18, 20);
        sheet.addMergedRegion(region);
        cell = row.createCell(18, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("长度");
        region = new CellRangeAddress(4, 4, 21, 22);
        sheet.addMergedRegion(region);
        cell = row.createCell(21, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("不为空");
        region = new CellRangeAddress(4, 4, 23, 24);
        sheet.addMergedRegion(region);
        cell = row.createCell(23, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("主键");
        region = new CellRangeAddress(4, 4, 25, 27);
        sheet.addMergedRegion(region);
        cell = row.createCell(25, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("主键顺序");
        region = new CellRangeAddress(4, 4, 28, 29);
        sheet.addMergedRegion(region);
        cell = row.createCell(28, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("外键");
        region = new CellRangeAddress(4, 4, 30, 43);
        sheet.addMergedRegion(region);
        cell = row.createCell(30, CellType.STRING);
        cell.setCellStyle(sellStyleTitle);
        cell.setCellValue("备注");

        CellStyle cellStyleCenter = getCellStyle(workbook);
        cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
        // 设置所有单元格默认格式
        for (int i = 0; i < table.getCols().size(); i++) {
            Column column = table.getCols().get(i);

            int rowNum = i + 5;
            row = sheet.getRow(rowNum);
            region = new CellRangeAddress(rowNum, rowNum, 0, 1);
            sheet.addMergedRegion(region);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellStyle(cellStyleCenter);
            cell.setCellValue(column.getSerial());
            region = new CellRangeAddress(rowNum, rowNum, 2, 7);
            sheet.addMergedRegion(region);
            cell = row.createCell(2, CellType.STRING);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(column.getRemarks());
            region = new CellRangeAddress(rowNum, rowNum, 8, 12);
            sheet.addMergedRegion(region);
            cell = row.createCell(8, CellType.STRING);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(column.getColumnName());
            region = new CellRangeAddress(rowNum, rowNum, 13, 17);
            sheet.addMergedRegion(region);
            cell = row.createCell(13, CellType.STRING);
            cell.setCellStyle(cellStyleCenter);
            cell.setCellValue(column.getSqlType().toLowerCase());
            region = new CellRangeAddress(rowNum, rowNum, 18, 20);
            sheet.addMergedRegion(region);
            cell = row.createCell(18, CellType.STRING);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(column.getLength());
            region = new CellRangeAddress(rowNum, rowNum, 21, 22);
            sheet.addMergedRegion(region);
            cell = row.createCell(21, CellType.STRING);
            cell.setCellStyle(cellStyleCenter);
            cell.setCellValue("NO".equals(column.getIsNotNull()) ? "○" : null);
            region = new CellRangeAddress(rowNum, rowNum, 23, 24);
            sheet.addMergedRegion(region);
            cell = row.createCell(23, CellType.STRING);
            cell.setCellStyle(cellStyleCenter);
            for (Column columnKey : table.getPrimaryKey()) {
                if (columnKey.getColumnName().equals(column.getColumnName())) {
                    cell.setCellValue("○");
                    break;
                }
            }
            region = new CellRangeAddress(rowNum, rowNum, 25, 27);
            sheet.addMergedRegion(region);
            cell = row.createCell(25, CellType.STRING);
            cell.setCellStyle(cellStyleCenter);
            for (Column columnKey : table.getPrimaryKey()) {
                if (columnKey.getColumnName().equals(column.getColumnName())) {
                    cell.setCellValue(columnKey.getPrimaryKeyOrder());
                    break;
                }
            }
            region = new CellRangeAddress(rowNum, rowNum, 28, 29);
            sheet.addMergedRegion(region);
            cell = row.createCell(28, CellType.STRING);
            cell.setCellStyle(cellStyleCenter);
            for (Column columnExp : table.getExportedKey()) {
                if (columnExp.getColumnName().equals(column.getColumnName())) {
                    cell.setCellValue("○");
                    break;
                }
            }
            region = new CellRangeAddress(rowNum, rowNum, 30, 43);
            sheet.addMergedRegion(region);
            cell = row.createCell(30, CellType.STRING);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(column.getRemarks());
        }
    }

    /**
     * 生成表一览Sheet
     * @param workbook
     * @param tables
     * @throws Exception
     * @author 阳自然
     */
    public static void makerListSheet(Workbook workbook, List<Table> tables) throws Exception {

        // 创建表一览Sheet
        Sheet sheet = workbook.createSheet("表一览");

        // 创建日期格式的样式
        CellStyle cellStyleDate = getCellStyle(workbook);
        DataFormat format = workbook.createDataFormat();
        cellStyleDate.setDataFormat(format.getFormat("yyyy-mm-dd"));
        // 水平居中对齐
        cellStyleDate.setAlignment(HorizontalAlignment.CENTER);

        CreationHelper createHelper = workbook.getCreationHelper();

        // 遍历所有的表
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);

            // 去除两行列标题
            Row row = sheet.createRow(i + 2);
            // 设置行高
            row.setHeightInPoints(15);

            Cell cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(i + 1);
            cell.setCellStyle(getCellStyle(workbook));
            cell = row.createCell(1, CellType.STRING);
            cell.setCellStyle(getLinkStyle(workbook));
            cell.setCellValue(table.getRemarks());
            Hyperlink link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            link.setAddress("#" + table.getTableName() + "!A1");
            cell.setHyperlink(link);
            cell = row.createCell(2, CellType.STRING);
            cell.setCellStyle(getLinkStyle(workbook));
            cell.setCellValue(table.getTableName());
            link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            link.setAddress("#" + table.getTableName() + "!A1");
            cell.setHyperlink(link);
            cell = row.createCell(3, CellType.STRING);
            cell.setCellStyle(getCellStyle(workbook));
            cell.setCellValue(table.getRemarks());
            cell = row.createCell(4, CellType.STRING);
            cell.setCellStyle(getCellStyle(workbook));
            cell.setCellValue("自动生成");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellStyle(cellStyleDate);
            cell.setCellValue(new Date());
            cell = row.createCell(6, CellType.STRING);
            cell.setCellStyle(getCellStyle(workbook));
            cell = row.createCell(7, CellType.STRING);
            cell.setCellStyle(cellStyleDate);
        }

        // 在索引0的位置创建行（最顶端的行）
        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(25);

        // 3.1 创建字体，设置其为粗体：
        Font font = getFont(workbook, 12);
        font.setBold(true);
        CellStyle cellStyle = getCellStyle(workbook);
        cellStyle.setFont(font);
        // 水平居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 3.3应用格式
        // 在索引0的位置创建单元格（左上端）
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("表一览");

        CellStyle cellStyleBlue = getCellStyleBlue(workbook);
        // 水平居中对齐
        cellStyleBlue.setAlignment(HorizontalAlignment.CENTER);

        // 在索引1的位置创建行（第二行）
        row = sheet.createRow(1);
        // 设置行高
        row.setHeightInPoints(30);
        cell = row.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("No.");
        cell = row.createCell(1);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("表名");
        cell = row.createCell(2);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("表物理名");
        cell = row.createCell(3);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("概要");
        cell = row.createCell(4);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("作成者");
        cell = row.createCell(5);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("作成日期");
        cell = row.createCell(6);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("修正者");
        cell = row.createCell(7);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("修正日期");

        // 合并单元格 第一行 A到F
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(region);
        // 冻结第1列第2行
        sheet.createFreezePane(8, 2);
        // 设置列宽
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 6500);
        sheet.setColumnWidth(2, 6500);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(4, 2500);
        sheet.setColumnWidth(5, 2500);
        sheet.setColumnWidth(6, 2500);
        sheet.setColumnWidth(7, 2500);
    }

    /**
     * 生成修改履历Sheet
     * @param workbook
     * @throws Exception
     * @author 阳自然
     */
    public static void makerHisSheet(Workbook workbook) throws Exception {

        // 创建修改履历Sheet
        Sheet sheet = workbook.createSheet("修改履历");

        // 日期了类型的单元格格式
        CellStyle cellStyleDate = getCellStyle(workbook);
        DataFormat format = workbook.createDataFormat();
        cellStyleDate.setDataFormat(format.getFormat("yyyy-mm-dd"));
        // 水平居中对齐
        cellStyleDate.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < 33; i++) {
            Row row = sheet.createRow(i);
            // 设置行高
            row.setHeightInPoints(15);
            for (int j = 0; j < 6; j++) {
                Cell cell = row.createCell(j, CellType.STRING);
                cell.setCellStyle(getCellStyle(workbook));
                if (j == 0) {
                    cell.setCellStyle(cellStyleDate);
                }
            }
        }

        // 在索引0的位置创建行（最顶端的行）
        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(25);

        // 设置字体为粗体
        Font font = getFont(workbook, 12);
        font.setBold(true);
        CellStyle cellStyleBold = getCellStyle(workbook);
        cellStyleBold.setFont(font);
        // 水平居中对齐
        cellStyleBold.setAlignment(HorizontalAlignment.CENTER);

        // 3.3应用格式
        // 在索引0的位置创建单元格（左上端）
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(cellStyleBold);
        cell.setCellValue("修正履历一览");

        CellStyle cellStyleBlue = getCellStyleBlue(workbook);
        // 水平居中对齐
        cellStyleBlue.setAlignment(HorizontalAlignment.CENTER);

        // 在索引1的位置创建行（第二行）
        row = sheet.createRow(1);
        // 设置行高
        row.setHeightInPoints(30);
        cell = row.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("修正日期");
        cell = row.createCell(1);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("修正表名");
        cell = row.createCell(2);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("表物理名");
        cell = row.createCell(3);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("修正内容");
        cell = row.createCell(4);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("修正理由");
        cell = row.createCell(5);
        cell.setCellStyle(cellStyleBlue);
        cell.setCellValue("修正者");

        // 合并单元格 第一行 A到F
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(region);
        // 冻结第6列第2行
        sheet.createFreezePane(6, 2);
        // 设置列宽
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 5500);
        sheet.setColumnWidth(2, 5500);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 2000);
    }

    /**
     * 普通单元格样式
     * @param workbook
     * @return
     * @author 阳自然
     */
    private static CellStyle getCellStyle(Workbook workbook) {

        // 创建格式 普通格式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(getFont(workbook, null));
        // 单元格边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        // 垂直居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return cellStyle;
    }

    /**
     * 蓝色背景样式
     * @param workbook
     * @return
     * @author 阳自然
     */
    private static CellStyle getCellStyleBlue(Workbook workbook) {

        CellStyle cellStyle = getCellStyle(workbook);

        // 设置单元格颜色
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());

        return cellStyle;
    }

    private static CellStyle getLinkStyle(Workbook workbook) {

        Font font = getFont(workbook, null);
        font.setColor(IndexedColors.BLUE.getIndex());

        CellStyle cellStyle = getCellStyle(workbook);
        cellStyle.setFont(font);

        return cellStyle;
    }

    /**
     * 获取字体
     * @param workbook
     * @param fontSize
     * @return
     * @author 阳自然
     */
    private static Font getFont(Workbook workbook, Integer fontSize) {

        // 如果字体大小为空 默认设置 10
        if (fontSize == null) {
            fontSize = 10;
        }

        Font font = workbook.createFont();
        font.setFontName("宋体");
        // 设置字体大小
        font.setFontHeightInPoints(fontSize.shortValue());

        return font;
    }
}
