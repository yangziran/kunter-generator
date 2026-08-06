package cn.kunter.generator.datasource.excel;

import cn.kunter.generator.datasource.DataSource;
import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.entity.Column;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;
import cn.kunter.generator.exception.DataSourceException;
import cn.kunter.generator.java.JavaTypeResolver;
import cn.kunter.generator.util.FileUtils;
import cn.kunter.generator.util.StringUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;

import java.sql.Types;
import java.util.List;

/**
 * Excel数据源
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class ExcelDataSource implements DataSource {

    private String filePath;

    public ExcelDataSource(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Table> getTables() throws DataSourceException {

        Workbook workbook;
        try {
            workbook = FileUtils.getWorkbook(filePath);
        } catch (CodeGenerationException e) {
            throw new DataSourceException("Excel数据源加载失败: " + filePath, e);
        }

        List<Table> tables = Lists.newArrayList();
        // 遍历Sheet
        for (var i = 2; i < workbook.getNumberOfSheets(); i++) {
            // 当前Sheet对象
            var sheet = workbook.getSheetAt(i);

            // 表名称（物理名称）
            var tableName = sheet.getRow(1).getCell(5).getStringCellValue();
            // 表备注（表名称）
            var tableRemarks = sheet.getRow(0).getCell(5).getStringCellValue();
            // 过滤表结构模板
            if ("table_template".equals(tableName)) {
                continue;
            }
            log.info("表名: {}, 表注释: {}", tableName, tableRemarks);

            // 将表名称转换为类名称
            var tableJavaName = StringUtils.convertTableNameToClass(tableName.toLowerCase(), "_", false);
            // 构造表信息对象
            var table = Table.builder().tableName(tableName).javaName(tableJavaName).remarks(tableRemarks).build();
            log.debug("表信息: {}", JSON.toJSONString(table));

            // 遍历Row
            for (var j = 5; j < sheet.getPhysicalNumberOfRows(); j++) {
                // 当前Row对象
                var row = sheet.getRow(j);

                if (row == null) {
                    continue;
                }

                // 编号（序号）
                var serial = getCellValueAsString(row, 0);
                if (StringUtils.isBlank(serial)) {
                    continue; // 序号为空代表可能是空行或者结束了
                }

                // 列名
                var columnName = getCellValueAsString(row, 2);
                if (StringUtils.isRemoveColumn(columnName)) {
                    continue;
                }
                // 物理名
                var jdbcName = getCellValueAsString(row, 9);
                // 类型
                var jdbcType = getCellValueAsString(row, 16);
                if (StringUtils.isNotBlank(jdbcType)) {
                    jdbcType = jdbcType.toUpperCase();
                    // 将INT转为INTEGER
                    if (StringUtils.equalsAnyIgnoreCase(jdbcType, "INT")) {
                        jdbcType = JavaTypeResolver.getJdbcType(Types.INTEGER);
                    }
                }

                // 长度
                var length = getCellValueAsString(row, 21);

                // 不为空
                var notNullValue = getCellValueAsString(row, 24);
                var notNull = StringUtils.isNotBlank(notNullValue);

                // 主键
                var primaryKeyValue = getCellValueAsString(row, 26);
                var primaryKey = StringUtils.isNotBlank(primaryKeyValue);
                
                // 主键顺序
                var primaryKeyOrder = getCellValueAsString(row, 28);

                // 备注
                var remarks = getCellValueAsString(row, 31);

                // 构造字段信息对象
                var columnJavaName = StringUtils.convertFieldToParameter(columnName, "_");
                var columnJavaType = JavaTypeResolver.getJavaType(jdbcType);
                var column = Column.builder().serial(serial).columnName(columnName).jdbcName(jdbcName)
                        .javaName(columnJavaName).jdbcType(jdbcType).javaType(columnJavaType).length(length)
                        .notNull(notNull).primaryKey(primaryKey).primaryKeyOrder(primaryKeyOrder).remarks(remarks)
                        .build();
                table.addColumn(column);

                if (primaryKey) {
                    table.addPrimaryKey(column);
                }
            }

            tables.add(table);
        }

        return tables;
    }

    @Override
    public SourceType getSourceType() {
        return SourceType.EXCEL;
    }

    private String getCellValueAsString(org.apache.poi.ss.usermodel.Row row, int cellIndex) {
        var cell = row.getCell(cellIndex);
        if (cell == null) {
            return null;
        }
        var cellType = cell.getCellType();
        if (CellType.FORMULA == cellType || CellType.NUMERIC == cellType) {
            return String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue());
        } else {
            return cell.getStringCellValue();
        }
    }

}
