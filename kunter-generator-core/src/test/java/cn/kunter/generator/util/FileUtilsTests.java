package cn.kunter.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FileUtils 测试
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
class FileUtilsTests {

    @Test
    void getWorkbook() throws Exception {

        var filePath = "../docs/表结构一览.xlsm";
        File file = new File(filePath);
        if (!file.exists()) {
            log.warn("Excel file not found, skipping getWorkbook test");
            return;
        }
        var workbook = FileUtils.getWorkbook(filePath);
        assertNotNull(workbook, "Workbook should not be null");
        assertTrue(workbook.getNumberOfSheets() > 0, "Workbook should have at least one sheet");

        // 遍历Sheet
        for (var i = 2; i < workbook.getNumberOfSheets(); i++) {
            var sheet = workbook.getSheetAt(i);

            // 表名称（物理名称）
            var tableName = sheet.getRow(1).getCell(5).getStringCellValue();
            // 表备注（表名称）
            var tableRemarks = sheet.getRow(0).getCell(5).getStringCellValue();
            log.info("tableName: {}, tableRemarks: {}", tableName, tableRemarks);

            // 遍历Row
            for (var j = 5; j < sheet.getPhysicalNumberOfRows(); j++) {
                var row = sheet.getRow(j);

                // 编号（序号）
                var serialCell = row.getCell(0);
                var serialCellType = serialCell.getCellType();
                String serial;
                // 判断为公式或者数值类型，采用getNumericCellValue获取值，并转换成String
                if (CellType.FORMULA == serialCellType || CellType.NUMERIC == serialCellType) {
                    serial = String.valueOf(Double.valueOf(serialCell.getNumericCellValue()).intValue());
                }
                // 其他类型，使用getStringCellValue获取值
                else {
                    serial = serialCell.getStringCellValue();
                }

                // 列名
                var columnName = row.getCell(2).getStringCellValue();

                // 物理名
                var sqlName = row.getCell(9).getStringCellValue();

                // 类型
                var sqlType = row.getCell(16).getStringCellValue().toUpperCase();

                // 长度
                var lengthCell = row.getCell(21);
                var lengthCellType = lengthCell.getCellType();
                String length;
                // 判断为公式或者数值类型，采用getNumericCellValue获取值，并转换成String
                if (CellType.FORMULA == lengthCellType || CellType.NUMERIC == lengthCellType) {
                    length = String.valueOf(Double.valueOf(lengthCell.getNumericCellValue()).intValue());
                }
                // 其他类型，使用getStringCellValue获取值
                else {
                    length = lengthCell.getStringCellValue();
                }

                // 不为空
                var notNullValue = row.getCell(24).getStringCellValue();
                var notNull = false;
                if (StringUtils.isNotBlank(notNullValue)) {
                    notNull = true;
                }

                // 主键
                var primaryKeyValue = row.getCell(26).getStringCellValue();
                var primaryKey = false;
                if (StringUtils.isNotBlank(primaryKeyValue)) {
                    primaryKey = true;
                }
                // 主键顺序
                var primaryKeyOrderCell = row.getCell(28);
                var primaryKeyOrderCellType = primaryKeyOrderCell.getCellType();
                String primaryKeyOrder;
                // 判断为公式或者数值类型，采用getNumericCellValue获取值，并转换成String
                if (CellType.FORMULA == primaryKeyOrderCellType || CellType.NUMERIC == primaryKeyOrderCellType) {
                    primaryKeyOrder = String.valueOf(Double.valueOf(primaryKeyOrderCell.getNumericCellValue())
                            .intValue());
                }
                // 其他类型，使用getStringCellValue获取值
                else {
                    primaryKeyOrder = primaryKeyOrderCell.getStringCellValue();
                }

                // 备注
                var remarks = row.getCell(31).getStringCellValue();

                log.info("编号：{}，列名：{}，物理名：{}，类型：{}，长度：{}，不为空：{}，主键：{}，主键顺序：{}，备注：{}", serial, columnName, sqlName,
                        sqlType, length, notNull, primaryKey, primaryKeyOrder, remarks);
            }
        }

    }

    @Test
    void testWriteFile() throws Exception {
        File tempDir = Files.createTempDirectory("kunter-generator-test").toFile();
        tempDir.deleteOnExit();

        String fileName = new File(tempDir, "TestFile.java").getAbsolutePath();
        String content = "public class TestFile {}";

        // 测试无覆盖模式写入文件
        FileUtils.writeFile(fileName, content, false);
        File expectedFile = new File(fileName);
        assertTrue(expectedFile.exists(), "File should be created");
        String readContent = Files.readString(expectedFile.toPath());
        assertEquals(content, readContent);

        // 测试覆盖模式写入文件
        String newContent = "public class TestFile { // overridden }";
        FileUtils.writeFile(fileName, newContent, true);
        String readNewContent = Files.readString(expectedFile.toPath());
        assertEquals(newContent, readNewContent);
    }

}
