/**
 * 
 */
package org.generator.make;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.generator.config.PropertyHolder;
import org.generator.db.ConnectionFactory;
import org.generator.entity.Column;
import org.generator.entity.Table;
import org.generator.type.DBType;
import org.generator.type.JdbcTypeNameTranslator;
import org.generator.type.SourceType;
import org.generator.util.StringUtility;

/**
 * 获取数据库表结构
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class GetTableConfig {

    // 数据源类型
    private final static String SOURCE_TYPE = SourceType.valueOf(PropertyHolder.getJDBCProperty("SourceType"))
            .getValue();
    // 数据库类型
    private final static String DB_TYPE = DBType.valueOf(PropertyHolder.getJDBCProperty("DB")).getValue();

    /**
     * 获取库表结构
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static List<Table> getTableConfig() throws Exception {

        if (SourceType.DB.getValue().equals(SOURCE_TYPE)) {
            return getDBTableConfig();
        }
        else {
            return getExcelTableConfig();
        }
    }

    /**
     * 获取数据源为数据库的库表表结构
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static List<Table> getDBTableConfig() throws SQLException {

        Connection connection = ConnectionFactory.getConnection();

        String model = PropertyHolder.getConfigProperty("model");
        StringBuilder tableName = new StringBuilder();
        if (StringUtility.isNotEmpty(model)) {
            tableName.append(model).append("_");
        }
        String tablec = PropertyHolder.getConfigProperty("table");
        if (StringUtility.isNotEmpty(tablec)) {
            tableName.append(tablec);
        }
        else {
            tableName.append("%");
        }

        DatabaseMetaData metaData = connection.getMetaData();
        // 第一个参数：数据库名称，第二个参数：模式、登录名，第三个参数：表名称，第四个参数：类型(数组)
        ResultSet tables = metaData.getTables(
                connection.getCatalog(), metaData.getUserName(), DB_TYPE.equals(DBType.ORACLE.getValue())
                        ? tableName.toString().toUpperCase() : tableName.toString().toLowerCase(),
                new String[] { "TABLE" });

        List<Table> tableList = new ArrayList<Table>();
        while (tables.next()) {
            // 获取表信息
            Table table = new Table();
            String TABLE_NAME = tables.getString("TABLE_NAME");
            String REMARKS = tables.getString("REMARKS");
            table.setTableName(TABLE_NAME);
            table.setJavaName(StringUtility.convertTableNameToClass(TABLE_NAME.toLowerCase(), "_", false));
            table.setRemarks(REMARKS);

            // 获取到主键集合
            ResultSet key = metaData.getPrimaryKeys(connection.getCatalog(), metaData.getUserName(), TABLE_NAME);
            while (key.next()) {
                Column column = new Column();
                column.setColumnName(key.getString("COLUMN_NAME"));
                column.setJavaName(
                        StringUtility.convertFieldToParameter(key.getString("COLUMN_NAME").toLowerCase(), "_"));
                table.addPrimaryKey(column);
            }

            // 获取到列集合
            ResultSet columns = metaData.getColumns(connection.getCatalog(), metaData.getUserName(), TABLE_NAME, null);
            while (columns.next()) {
                Column column = new Column();
                column.setColumnName(columns.getString("COLUMN_NAME"));
                column.setJavaName(
                        StringUtility.convertFieldToParameter(columns.getString("COLUMN_NAME").toLowerCase(), "_"));
                column.setSqlType(JdbcTypeNameTranslator.getJdbcTypeName(columns.getInt("DATA_TYPE")));
                column.setJavaType(JdbcTypeNameTranslator.getJavaType(columns.getInt("DATA_TYPE")));
                column.setRemarks(columns.getString("REMARKS"));

                table.addCols(column);
            }
            tableList.add(table);
        }

        for (Table table : tableList) {
            for (Column column1 : table.getPrimaryKey()) {
                for (Column column2 : table.getCols()) {
                    if (column1.getJavaName().equals(column2.getJavaName())) {
                        column1.setJavaType(column2.getJavaType());
                        column1.setSqlType(column2.getSqlType());
                        column1.setRemarks(column2.getRemarks());
                    }
                }
            }
        }

        return tableList;
    }

    /**
     * 获取数据源为Excel的库表表结构
     * @return
     * @author yangziran
     * @throws Exception
     */
    public static List<Table> getExcelTableConfig() throws Exception {

        Workbook wb = null;
        // 获取输入流对象
        InputStream is = null;
        try {
            // 获取文件对象
            File file = new File(PropertyHolder.getJDBCProperty("path.dictionary"));
            // 判断文件是否存在
            if (file.exists()) {
                // 获取文件的后缀
                String ext = FilenameUtils.getExtension(file.getName());
                // 判断是否为2007及以上版本
                boolean xlsx = ext.equals("xlsx");
                // 获取输入流对象
                is = new FileInputStream(file);
                // 得到工作簿对象 2007及以上版本需要获取 XSSFWorkbook对象，95~2003版本需要获取HSSFWorkbook对象
                wb = xlsx ? new XSSFWorkbook(is) : new HSSFWorkbook(is);
            }
        } catch (FileNotFoundException e) {
            throw new Exception(e);
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if (wb != null) {
                wb.close();
            }
            // 输入流不为空
            if (is != null) {
                try {
                    // 关闭输入流
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        List<Table> tableList = new ArrayList<Table>();
        // 获取到总的Sheet循环
        for (int i = 2; i < wb.getNumberOfSheets(); i++) {
            // 获取到当前的Sheet对象
            Sheet sheet = wb.getSheetAt(i);
            // 获取到表名称
            String TABLE_NAME = sheet.getRow(1).getCell(3).getStringCellValue();
            // 获取到表物理名称
            String REMARKS = sheet.getRow(0).getCell(3).getStringCellValue();

            // 获取表信息
            Table table = new Table();
            table.setTableName(TABLE_NAME);
            table.setJavaName(StringUtility.convertTableNameToClass(TABLE_NAME.toLowerCase(), "_", false));
            table.setRemarks(REMARKS);

            // 获取到当前Sheet的最后一行下标加1为总行数 循环行
            for (int j = 5; j < sheet.getPhysicalNumberOfRows(); j++) {
                // 获取到当前行对象
                Row row = sheet.getRow(j);
                // 编号
                String serial = String.valueOf(row.getCell(0).getNumericCellValue());
                // 列名
                String columnName = row.getCell(4).getStringCellValue();
                // 物理名
                String physical = row.getCell(1).getStringCellValue();
                // 类型
                String type = row.getCell(8).getStringCellValue().toUpperCase();
                // 是否为空
                String isNotNull = row.getCell(13).getStringCellValue();
                // 主键
                String primaryKey = row.getCell(15).getStringCellValue();
                // 主键顺序
                Double primaryKeyOrder = row.getCell(17).getNumericCellValue();
                // 外键
                String foreignKey = row.getCell(18).getStringCellValue();
                // 备注
                String remarks = row.getCell(21).getStringCellValue();

                // 长度
                Cell cell = row.getCell(11);

                Column column = new Column();
                column.setColumnName(columnName);
                column.setJavaName(StringUtility.convertFieldToParameter(columnName.toLowerCase(), "_"));
                column.setSqlType(JdbcTypeNameTranslator.getJdbcTypeName(JdbcTypeNameTranslator.getJdbcType(type)));
                column.setJavaType(JdbcTypeNameTranslator.getJavaType(type));
                column.setRemarks(physical + " " + remarks);

                // 主键列不为空
                if (StringUtility.isNotEmpty(primaryKey)) {
                    table.addPrimaryKey(column);
                }
                table.addCols(column);
            }

            tableList.add(table);
        }

        return tableList;
    }
}
