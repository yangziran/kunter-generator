/**
 * 
 */
package org.generator.make;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.generator.config.PropertyHolder;
import org.generator.db.ConnectionFactory;
import org.generator.entity.Column;
import org.generator.entity.Table;
import org.generator.type.DBType;
import org.generator.type.JdbcTypeNameTranslator;
import org.generator.util.StringUtility;

/**
 * 获取数据库表结构
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class GetTableConfig {

    private final static String DB_TYPE = DBType.valueOf(PropertyHolder.getJDBCProperty("DB")).getValue();

    /**
     * 获取数据库表结构
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static List<Table> getTableConfig() throws SQLException {

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
        ResultSet tables = metaData.getTables(connection.getCatalog(), metaData.getUserName(), DB_TYPE
                .equals(DBType.ORACLE.getValue()) ? tableName.toString().toUpperCase() : tableName.toString()
                .toLowerCase(), new String[] { "TABLE" });
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
                column.setJavaName(StringUtility.convertFieldToParameter(key.getString("COLUMN_NAME").toLowerCase(),
                        "_"));
                table.addPrimaryKey(column);
            }

            // 获取到列集合
            ResultSet columns = metaData.getColumns(connection.getCatalog(), metaData.getUserName(), TABLE_NAME, null);
            while (columns.next()) {
                Column column = new Column();
                column.setColumnName(columns.getString("COLUMN_NAME"));
                column.setJavaName(StringUtility.convertFieldToParameter(
                        columns.getString("COLUMN_NAME").toLowerCase(), "_"));
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
}
