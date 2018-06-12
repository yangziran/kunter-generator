/**
 * 
 */
package cn.kunter.common.generator.make;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.db.ConnectionFactory;
import cn.kunter.common.generator.entity.Column;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.type.DBType;
import cn.kunter.common.generator.type.JdbcTypeNameTranslator;
import cn.kunter.common.generator.type.SourceType;
import cn.kunter.common.generator.util.CollectionUtil;
import cn.kunter.common.generator.util.ExcelUtil;
import cn.kunter.common.generator.util.LogUtil;
import cn.kunter.common.generator.util.StringUtility;

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

        LogUtil.infoStart(GetTableConfig.class, "getTableConfig");

        List<Table> tableList = new ArrayList<Table>();
        // 根据数据类型获取表结构信息
        if (SourceType.DB.getValue().equals(SOURCE_TYPE)) {
            tableList.addAll(getDBTableConfig());
        }
        else {
            tableList.addAll(getExcelTableConfig());
        }

        CollectionUtil.sortByProperty(tableList, "tableName", "String", CollectionUtil.ASC);

        LogUtil.info(GetTableConfig.class, "getTableConfig", "获取到表" + tableList.size() + "个");

        LogUtil.infoEnd(GetTableConfig.class, "getTableConfig");

        return tableList;
    }

    /**
     * 获取数据源为数据库的库表表结构
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static List<Table> getDBTableConfig() throws SQLException {

        LogUtil.infoStart(GetTableConfig.class, "getDBTableConfig");

        // 获取到数据库连接
        Connection connection = ConnectionFactory.getConnection();

        // 处理表名称 支持全库生成 有以下几种模式:1、全库，%；2、多表，xx_%|xx_%,yy_%|xx_%,yy_bb|xx_aa,yy_bb
        String tableName = PropertyHolder.getConfigProperty("table");
        String[] tableNames = tableName.split(",");

        List<Table> tableList = new ArrayList<Table>();
        for (String string : tableNames) {
            String name = DB_TYPE.equals(DBType.ORACLE.getValue()) ? string.toUpperCase() : string.toLowerCase();

            tableList.addAll(getTableList(connection, name));
        }

        // 关闭连接
        connection.close();

        LogUtil.infoEnd(GetTableConfig.class, "getDBTableConfig");

        return tableList;
    }

    /**
     * 处理配置文件中设定的表结构
     * @param connection
     * @param tableName
     * @return
     * @throws SQLException
     * @author 阳自然
     */
    private static List<Table> getTableList(Connection connection, String tableName) throws SQLException {

        LogUtil.infoStart(GetTableConfig.class, "getTableList");

        DatabaseMetaData metaData = connection.getMetaData();

        // 第一个参数：数据库名称，第二个参数：模式、登录名，第三个参数：表名称，第四个参数：类型(数组)
        // System.out.println(metaData.getUserName()); TODO Oracle没做测试
        ResultSet set = metaData.getSchemas(connection.getCatalog(), "");
        String schema = "";
        if (set.next()) {
            schema = set.getString(2);
        }
        ResultSet tables = metaData.getTables(connection.getCatalog(), schema, tableName, new String[] { "TABLE" });

        List<Table> tableList = new ArrayList<Table>();
        while (tables.next()) {
            // 获取表信息
            Table table = new Table();
            String TABLE_NAME = tables.getString("TABLE_NAME");
            String REMARKS = tables.getString("REMARKS");
            table.setTableName(TABLE_NAME);
            table.setJavaName(StringUtility.convertTableNameToClass(TABLE_NAME.toLowerCase(), "_", false));
            // table.setAlias(StringUtility.convertTableNameToAlias(TABLE_NAME.toLowerCase(), "_"));
            table.setRemarks(REMARKS);

            // 获取到主键集合
            ResultSet key = metaData.getPrimaryKeys(connection.getCatalog(), schema, TABLE_NAME);
            while (key.next()) {
                Column column = new Column();
                String COLUMN_NAME = key.getString("COLUMN_NAME");
                if (COLUMN_NAME.indexOf("‘") == 0 && COLUMN_NAME.lastIndexOf("’") > 1) {
                    COLUMN_NAME = COLUMN_NAME.substring(1, COLUMN_NAME.length() - 1);
                }
                column.setColumnName(COLUMN_NAME);
                column.setJavaName(StringUtility.convertFieldToParameter(COLUMN_NAME.toLowerCase(), "_"));
                column.setPrimaryKeyOrder(String.valueOf(key.getRow()));
                table.addPrimaryKey(column);
            }
            // 关闭连接
            key.close();

            // 获取到外键集合
            ResultSet exp = metaData.getExportedKeys(connection.getCatalog(), schema, TABLE_NAME);
            while (exp.next()) {
                Column column = new Column();
                String PKCOLUMN_NAME = exp.getString("PKCOLUMN_NAME");
                if (PKCOLUMN_NAME.indexOf("‘") == 0 && PKCOLUMN_NAME.lastIndexOf("’") > 1) {
                    PKCOLUMN_NAME = PKCOLUMN_NAME.substring(1, PKCOLUMN_NAME.length() - 1);
                }
                // String COLUMN_NAME = exp.getString("COLUMN_NAME");
                // if (COLUMN_NAME.indexOf("‘") == 0 && COLUMN_NAME.lastIndexOf("’") > 1) {
                // COLUMN_NAME = COLUMN_NAME.substring(1, COLUMN_NAME.length() - 1);
                // }
                column.setColumnName(PKCOLUMN_NAME);
                column.setJavaName(StringUtility.convertFieldToParameter(PKCOLUMN_NAME.toLowerCase(), "_"));
                table.addExportedKey(column);
            }
            // 关闭连接
            exp.close();

            // 获取到列集合
            ResultSet columns = metaData.getColumns(connection.getCatalog(), schema, TABLE_NAME, "%");
            while (columns.next()) {
                Column column = new Column();
                String COLUMN_NAME = columns.getString("COLUMN_NAME");
                if (COLUMN_NAME.indexOf("‘") == 0 && COLUMN_NAME.lastIndexOf("’") > 1) {
                    COLUMN_NAME = COLUMN_NAME.substring(1, COLUMN_NAME.length() - 1);
                }
                column.setSerial(String.valueOf(columns.getRow()));
                column.setColumnName(COLUMN_NAME);
                column.setJavaName(StringUtility.convertFieldToParameter(COLUMN_NAME.toLowerCase(), "_"));
                column.setSqlType(JdbcTypeNameTranslator.getJdbcTypeName(columns.getInt("DATA_TYPE")));
                column.setJavaType(JdbcTypeNameTranslator.getJavaType(columns.getInt("DATA_TYPE")));
                column.setRemarks(columns.getString("REMARKS"));
                column.setIsNotNull(columns.getString("IS_NULLABLE"));
                column.setLength(columns.getInt("COLUMN_SIZE"));
                table.addCols(column);
            }
            // 关闭连接
            columns.close();

            tableList.add(table);
        }

        // 关闭连接
        tables.close();

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

        LogUtil.infoEnd(GetTableConfig.class, "getTableList");

        return tableList;
    }

    /**
     * 获取数据源为Excel的库表表结构
     * @return
     * @author yangziran
     * @throws Exception
     */
    public static List<Table> getExcelTableConfig() throws Exception {

        LogUtil.infoStart(GetTableConfig.class, "getExcelTableConfig");

        Workbook wb = ExcelUtil.getWorkbook();

        List<Table> tableList = new ArrayList<Table>();
        // 获取到总的Sheet循环
        for (int i = 2; i < wb.getNumberOfSheets(); i++) {
            // 获取到当前的Sheet对象
            Sheet sheet = wb.getSheetAt(i);
            // 获取到表名称
            String TABLE_NAME = sheet.getRow(1).getCell(7).getStringCellValue();
            // 获取到表物理名称
            String REMARKS = sheet.getRow(0).getCell(7).getStringCellValue();

            // 获取表信息
            Table table = new Table();
            table.setTableName(TABLE_NAME);
            table.setJavaName(StringUtility.convertTableNameToClass(TABLE_NAME.toLowerCase(), "_", false));
            // table.setAlias(StringUtility.convertTableNameToAlias(TABLE_NAME.toLowerCase(), "_"));
            table.setRemarks(REMARKS);

            // 获取到当前Sheet的最后一行下标加1为总行数 循环行
            for (int j = 5; j < sheet.getPhysicalNumberOfRows(); j++) {
                // 获取到当前行对象
                Row row = sheet.getRow(j);
                // 编号
                String serial = row.getCell(0).getStringCellValue();
                // 列名
                String columnName = row.getCell(8).getStringCellValue();
                // 物理名
                String physical = row.getCell(2).getStringCellValue();
                // 类型
                String type = row.getCell(13).getStringCellValue().toUpperCase();
                // 是否为空
                String isNotNull = row.getCell(21).getStringCellValue();
                // 主键
                String primaryKey = row.getCell(23).getStringCellValue();
                // 主键顺序
                String primaryKeyOrder = row.getCell(25).getStringCellValue();
                // 外键
                String foreignKey = row.getCell(28).getStringCellValue();
                // 备注
                // String remarks = row.getCell(30).getStringCellValue();
                // 长度
                Integer length = null;
                Cell cell = row.getCell(18);
                Double cellVal = cell.getNumericCellValue();
                if (null != cellVal) {
                    length = (int) cell.getNumericCellValue();
                }

                Column column = new Column();
                column.setSerial(serial);
                column.setColumnName(columnName);
                column.setJavaName(StringUtility.convertFieldToParameter(columnName.toLowerCase(), "_"));
                column.setSqlType(JdbcTypeNameTranslator.getJdbcTypeName(JdbcTypeNameTranslator.getJdbcType(type)));
                column.setJavaType(JdbcTypeNameTranslator.getJavaType(type));
                column.setRemarks(physical);
                column.setIsNotNull(isNotNull);
                column.setPrimaryKeyOrder(primaryKeyOrder);
                column.setForeignKey(foreignKey);
                column.setLength(length);

                // 主键列不为空
                if (StringUtility.isNotEmpty(primaryKey)) {
                    table.addPrimaryKey(column);
                }
                table.addCols(column);
            }

            tableList.add(table);
        }

        LogUtil.infoEnd(GetTableConfig.class, "getExcelTableConfig");

        return tableList;
    }
}