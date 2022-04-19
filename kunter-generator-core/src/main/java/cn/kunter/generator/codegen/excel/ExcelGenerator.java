package cn.kunter.generator.codegen.excel;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.entity.Column;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.GeneratorException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Excel生成器
 * 生成Excel格式的数据字典(格式参考模板)
 * @author yangziran
 * @version 1.0 2021/12/20
 */
@Slf4j
public class ExcelGenerator implements Generator {

    @Override
    public void maker(List<Table> tables) throws GeneratorException {
        log.info("开始生成Excel格式数据字典, 表数量: {}", tables.size());

        // 创建新的Excel工作薄（2007）
        var workbook = new XSSFWorkbook();
        // 生成修改履历Sheet
        makerHistorySheet(workbook);
        // 生成表一览Sheet
        makerListSheet(workbook, tables);

        // 遍历集合生成表结构Sheet
        for (var table : tables) {
            makerTableSheet(workbook, table);
        }

        // 创建输出流
        var path = Paths.get("", "表结构一览.xlsx");
        try (var outputStream = Files.newOutputStream(path)) {
            // 写Excel文件，并刷新流
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException exception) {
            log.error("写文件失败", exception);
            throw new GeneratorException("写文件失败", exception);
        } finally {
            // 写文件结束，关闭文件
            IOUtils.closeQuietly(workbook);
            log.info("结束生成Excel格式数据字典");
        }
    }

    /**
     * 生成表结构Sheet
     * @param workbook
     * @param table
     */
    private void makerTableSheet(Workbook workbook, Table table) {
        log.info("生成表结构Sheet: {}", table.getTableName());

        // 创建表结构Sheet
        var sheet = workbook.createSheet(table.getTableName());
        // 设置默认行高
        sheet.setDefaultRowHeightInPoints(20);
        // 设置默认列宽
        sheet.setDefaultColumnWidth(3);

        // 设置所有单元格默认格式
        var cellStyle = getCellStyle(workbook);
        for (var i = 0; i < table.getColumns().size() + 5; i++) {
            var row = sheet.createRow(i);
            for (var j = 0; j < 46; j++) {
                Cell cell = row.createCell(j, CellType.STRING);
                cell.setCellStyle(cellStyle);
            }
        }

        var blueCellStyle = getBlueCellStyle(workbook);
        blueCellStyle.setAlignment(HorizontalAlignment.LEFT);
        var contextCellStyle = getCellStyle(workbook);
        contextCellStyle.setAlignment(HorizontalAlignment.LEFT);
        // 第一行处理
        // 表名
        makerTableCell(sheet, 1, 1, 5, blueCellStyle, "表名", null);
        makerTableCell(sheet, 1, 6, 19, contextCellStyle, table.getRemarks(), null);
        // 创建者
        makerTableCell(sheet, 1, 25, 5, blueCellStyle, "创建者", null);
        makerTableCell(sheet, 1, 30, 6, contextCellStyle, "自动生成", null);
        // 修改者
        makerTableCell(sheet, 1, 36, 5, blueCellStyle, "修改者", null);
        makerTableCell(sheet, 1, 41, 6, contextCellStyle, null, null);

        // 创建日期格式的样式
        var cellStyleDate = getCellStyle(workbook);
        var format = workbook.createDataFormat();
        cellStyleDate.setDataFormat(format.getFormat("yyyy-mm-dd"));
        cellStyleDate.setAlignment(HorizontalAlignment.LEFT);
        // 第二行处理
        // 物理名
        makerTableCell(sheet, 2, 1, 5, blueCellStyle, "物理名", null);
        makerTableCell(sheet, 2, 6, 19, contextCellStyle, table.getTableName(), null);
        // 创建日期
        makerTableCell(sheet, 2, 25, 5, blueCellStyle, "创建日期", null);
        makerTableCell(sheet, 2, 30, 6, cellStyleDate, LocalDate.now().toString(), null);
        // 修改日期
        makerTableCell(sheet, 2, 36, 5, blueCellStyle, "修改日期", null);
        makerTableCell(sheet, 2, 41, 6, cellStyleDate, null, null);

        var createHelper = workbook.getCreationHelper();
        // 第三行处理
        // 概要
        makerTableCell(sheet, 3, 1, 5, blueCellStyle, "概要", null);
        makerTableCell(sheet, 3, 6, 35, contextCellStyle, table.getRemarks(), null);
        var link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        link.setAddress("#表一览!A1");
        makerTableCell(sheet, 3, 41, 6, getLinkCellStyle(workbook), "返回列表", link);

        // 第四行处理
        makerTableCell(sheet, 4, 1, 46, null, null, null);

        // 第五行处理 标题
        var titleCellStyle = getBlueCellStyle(workbook);
        var rowIndex = 5;
        var cellIndex = 1;
        for (var i = 0; i < TABLE_ROW_TITLE.size(); i++) {
            var value = TABLE_ROW_TITLE.get(i);
            var cellCount = TABLE_ROW_TITLE_CELL.get(i);
            makerTableCell(sheet, rowIndex, cellIndex, cellCount, titleCellStyle, value, null);
            cellIndex += cellCount;
        }

        // 表结构行
        var leftCellStyle = getLeftCellStyle(workbook);
        var centerCellStyle = getCellStyle(workbook);
        centerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        var rightCellStyle = getCellStyle(workbook);
        rightCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        for (var i = 0; i < table.getColumns().size(); i++) {
            var rowNum = i + 6;
            var column = table.getColumns().get(i);

            makerTableCell(sheet, rowNum, 1, TABLE_ROW_TITLE_CELL.get(0), centerCellStyle, column.getSerial(), null);

            makerTableCell(sheet, rowNum, 3, TABLE_ROW_TITLE_CELL.get(1), leftCellStyle, column.getRemarks(), null);

            makerTableCell(sheet, rowNum, 10, TABLE_ROW_TITLE_CELL.get(2), leftCellStyle, column.getColumnName(), null);

            makerTableCell(sheet, rowNum, 17, TABLE_ROW_TITLE_CELL.get(3), centerCellStyle, column.getJdbcType().toLowerCase(), null);

            makerTableCell(sheet, rowNum, 22, TABLE_ROW_TITLE_CELL.get(4), rightCellStyle, column.getLength(), null);

            makerTableCell(sheet, rowNum, 25, TABLE_ROW_TITLE_CELL.get(5), centerCellStyle, column.getNotNull() ? null : "○", null);

            String pkValue = null;
            for (var columnKey : table.getPrimaryKeys()) {
                if (columnKey.getColumnName().equals(column.getColumnName())) {
                    pkValue = "○";
                    break;
                }
            }
            makerTableCell(sheet, rowNum, 27, TABLE_ROW_TITLE_CELL.get(6), centerCellStyle, pkValue, null);

            String pkOrder = null;
            for (Column columnKey : table.getPrimaryKeys()) {
                if (columnKey.getColumnName().equals(column.getColumnName())) {
                    pkOrder = columnKey.getPrimaryKeyOrder();
                    break;
                }
            }
            makerTableCell(sheet, rowNum, 29, TABLE_ROW_TITLE_CELL.get(7), centerCellStyle, pkOrder, null);

            makerTableCell(sheet, rowNum, 32, TABLE_ROW_TITLE_CELL.get(8), leftCellStyle, column.getRemarks(), null);
        }
    }

    /** 表结构Sheet页表头 */
    private static final List<String> TABLE_ROW_TITLE = Lists.newArrayList("编号", "列名", "物理名", "类型", "长度", "不为空", "主键", "主键顺序", "备注");
    private static final List<Integer> TABLE_ROW_TITLE_CELL = Lists.newArrayList(2, 7, 7, 5, 3, 2, 2, 3, 15);

    /**
     * 创建表结构内容
     * @param sheet
     * @param rowIndex 行号, 从1开始
     * @param cellIndex 列号, 从1开始
     * @param cellCount 列宽, 跨多少列
     * @param cellStyle 列样式
     * @param value 列内容
     * @param link 链接
     */
    private void makerTableCell(Sheet sheet, Integer rowIndex, Integer cellIndex, Integer cellCount, CellStyle cellStyle, String value, Hyperlink link) {

        int rowNum = rowIndex - 1;
        int firstCol = cellIndex - 1;
        int lastCol = firstCol + cellCount - 1;
        // 列合并
        var region = new CellRangeAddress(rowNum, rowNum, firstCol, lastCol);
        sheet.addMergedRegion(region);
        // 获取到行
        var row = sheet.getRow(rowNum);
        // 创建列并设置样式和内容
        var cell = row.createCell(firstCol, CellType.STRING);
        cell.setCellStyle(cellStyle);
        if (StringUtils.isNotBlank(value)) {
            cell.setCellValue(value);
        }
        if (ObjectUtils.isNotEmpty(link)) {
            cell.setHyperlink(link);
        }
    }

    /**
     * 生成表一览Sheet
     * @param workbook
     * @param tables
     */
    private void makerListSheet(Workbook workbook, List<Table> tables) {
        log.info("生成表一览Sheet");

        // 创建表一览Sheet
        var sheet = workbook.createSheet("表一览");
        var createHelper = workbook.getCreationHelper();

        // 创建表头行
        makerListTitleRow(workbook, sheet);

        var now = new Date();
        // 获取一览行样式
        var contextCellStyle = getCellStyle(workbook);
        var linkCellStyle = getLinkCellStyle(workbook);
        linkCellStyle.setAlignment(HorizontalAlignment.LEFT);
        var leftCellStyle = getLeftCellStyle(workbook);
        var dateCellStyle = getDateCellStyle(workbook);
        // 遍历表集合
        for (var i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);

            // 创建表行
            var contextRow = sheet.createRow(i + 2);
            // 设置行高
            contextRow.setHeightInPoints(20);

            var cell = contextRow.createCell(0, CellType.STRING);
            cell.setCellValue(i + 1);
            cell.setCellStyle(contextCellStyle);
            cell = contextRow.createCell(1, CellType.STRING);
            cell.setCellStyle(linkCellStyle);
            cell.setCellValue(table.getRemarks());
            var link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            link.setAddress("#" + table.getTableName() + "!A1");
            cell.setHyperlink(link);
            cell = contextRow.createCell(2, CellType.STRING);
            cell.setCellStyle(linkCellStyle);
            cell.setCellValue(table.getTableName());
            link = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            link.setAddress("#" + table.getTableName() + "!A1");
            cell.setHyperlink(link);
            cell = contextRow.createCell(3, CellType.STRING);
            cell.setCellStyle(contextCellStyle);
            cell.setCellStyle(leftCellStyle);
            cell.setCellValue(table.getRemarks());
            cell = contextRow.createCell(4, CellType.STRING);
            cell.setCellStyle(contextCellStyle);
            cell.setCellValue("自动生成");
            cell = contextRow.createCell(5, CellType.STRING);
            cell.setCellStyle(dateCellStyle);
            cell.setCellValue(now);
            cell = contextRow.createCell(6, CellType.STRING);
            cell.setCellStyle(contextCellStyle);
            cell = contextRow.createCell(7, CellType.STRING);
            cell.setCellStyle(dateCellStyle);
        }

        // 合并单元格 第一行 A到F
        var region = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(region);
        // 冻结第1列第2行
        sheet.createFreezePane(8, 2);
        // 设置列宽
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 6500);
        sheet.setColumnWidth(2, 6500);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(4, 3500);
        sheet.setColumnWidth(5, 3500);
        sheet.setColumnWidth(6, 3500);
        sheet.setColumnWidth(7, 3500);
    }

    /** 一览Sheet页表头 */
    private static final List<String> LIST_ROW_TITLE = Lists.newArrayList("No.", "表名", "表物理名", "概要", "创建者", "创建日期", "修改者", "修改日期");

    /**
     * 创建履历Sheet页表头行
     * @param workbook
     * @param sheet
     */
    private void makerListTitleRow(Workbook workbook, Sheet sheet) {
        // 创建行对象
        var row = sheet.createRow(1);
        // 设置行高: 25
        row.setHeightInPoints(25);
        // 获取样式
        var cellStyle = getBlueCellStyle(workbook);

        // 遍历集合创建标题列
        for (var i = 0; i < LIST_ROW_TITLE.size(); i++) {
            // 创建单元格
            var cell = row.createCell(i);
            // 设置单元格样式
            cell.setCellStyle(cellStyle);
            // 设置单元格内容
            cell.setCellValue(LIST_ROW_TITLE.get(i));
        }
    }

    /**
     * 生成修改履历Sheet
     * @param workbook
     */
    private void makerHistorySheet(Workbook workbook) {
        log.info("生成修改履历Sheet");

        // 创建修改履历Sheet
        var sheet = workbook.createSheet("修改履历");

        // 获取单元格样式
        var cellStyle = getCellStyle(workbook);
        // 设置字体为粗体
        cellStyle.setFont(getBoldFont(workbook));
        // 创建标题行
        var row = sheet.createRow(0);
        row.setHeightInPoints(30);
        var cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("修改履历一览");

        // 创建表头行
        makerHistoryTitleRow(workbook, sheet);

        // 获取履历行样式
        var contextCellStyle = getCellStyle(workbook);
        var leftCellStyle = getLeftCellStyle(workbook);
        var dateCellStyle = getDateCellStyle(workbook);
        // 创建履历行，默认30行
        for (var i = 2; i < 32; i++) {
            // 创建行
            var contextRow = sheet.createRow(i);
            // 设置行高
            contextRow.setHeightInPoints(20);

            // 创建列
            for (var j = 0; j < 6; j++) {
                var contextCell = contextRow.createCell(j, CellType.STRING);
                contextCell.setCellStyle(contextCellStyle);

                // 设置修改内容和修改理由内容居左
                if (j == 3 || j == 4) {
                    contextCell.setCellStyle(leftCellStyle);
                }

                // 设置修改日期格式
                if (j == 0) {
                    // 首列设置日期格式
                    contextCell.setCellStyle(dateCellStyle);
                }
            }
        }

        // 合并第一行单元格, A到F
        var region = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(region);
        // 冻结标题行和表头行
        sheet.createFreezePane(6, 2);
        // 设置列宽
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 6500);
        sheet.setColumnWidth(2, 6500);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(4, 7000);
        sheet.setColumnWidth(5, 3500);
    }

    /** 履历Sheet页表头 */
    private static final List<String> HISTORY_ROW_TITLE = Lists.newArrayList("修改日期", "修改表名", "表物理名", "修改内容", "修改理由", "修改者");

    /**
     * 创建履历Sheet页表头行
     * @param workbook
     * @param sheet
     */
    private void makerHistoryTitleRow(Workbook workbook, Sheet sheet) {
        // 创建行对象
        var row = sheet.createRow(1);
        // 设置行高: 25
        row.setHeightInPoints(25);
        // 获取样式
        var cellStyle = getBlueCellStyle(workbook);

        // 遍历集合创建标题列
        for (var i = 0; i < HISTORY_ROW_TITLE.size(); i++) {
            // 创建单元格
            var cell = row.createCell(i);
            // 设置单元格样式
            cell.setCellStyle(cellStyle);
            // 设置单元格内容
            cell.setCellValue(HISTORY_ROW_TITLE.get(i));
        }
    }

    /**
     * 获取单元格样式
     * @param workbook
     * @return
     */
    private CellStyle getCellStyle(Workbook workbook) {

        // 创建Style对象
        var cellStyle = workbook.createCellStyle();
        // 设置单元格边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        // 设置水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体
        cellStyle.setFont(getDefaultFont(workbook));

        return cellStyle;
    }

    /**
     * 获取单元背景样式
     * @param workbook
     * @return
     */
    private CellStyle getBlueCellStyle(Workbook workbook) {

        // 获取单元格样式对象
        var cellStyle = getCellStyle(workbook);
        // 设置单元格颜色
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());

        return cellStyle;
    }

    /**
     * 获取单元格日期样式
     * @param workbook
     * @return
     */
    private CellStyle getDateCellStyle(Workbook workbook) {

        // 获取数据格式化对象
        var dataFormat = workbook.createDataFormat();
        // 获取单元格样式对象
        var cellStyle = getCellStyle(workbook);
        // 设置单元格格式
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));

        return cellStyle;
    }

    /**
     * 获取单元居左样式
     * @param workbook
     * @return
     */
    private CellStyle getLeftCellStyle(Workbook workbook) {

        // 获取单元格样式对象
        var cellStyle = getCellStyle(workbook);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        return cellStyle;
    }

    /**
     * 获取单元格链接样式
     * @param workbook
     * @return
     */
    private CellStyle getLinkCellStyle(Workbook workbook) {

        // 获取字体
        var font = getDefaultFont(workbook);
        font.setColor(IndexedColors.BLUE.getIndex());
        // 获取单元格样式对象
        var cellStyle = getCellStyle(workbook);
        cellStyle.setFont(font);

        return cellStyle;
    }

    /**
     * 获取默认字体(宋体), 字体大小: 10
     * @param workbook
     * @return
     */
    private Font getDefaultFont(Workbook workbook) {
        return getFont(workbook, "宋体", (short) 14, false);
    }

    /**
     * 获取加粗字体(宋体), 字体大小: 12
     * @param workbook
     * @return
     */
    private Font getBoldFont(Workbook workbook) {
        return getFont(workbook, "宋体", (short) 16, true);
    }

    /**
     * 获取字体
     * @param workbook 工作簿对象
     * @param name 字体名称
     * @param size 字体大小
     * @param bold 是否加粗
     * @return Font
     */
    private Font getFont(Workbook workbook, String name, short size, boolean bold) {

        // 创建Font对象
        var font = workbook.createFont();
        font.setFontName(name);
        font.setFontHeightInPoints(size);
        font.setBold(bold);

        return font;
    }

}
