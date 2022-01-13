package cn.kunter.generator.datasource.db.mysql;

import cn.kunter.generator.config.JdbcConnectionConfig;
import cn.kunter.generator.datasource.DataSource;
import cn.kunter.generator.datasource.db.JdbcConnectionFactory;
import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.entity.Column;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.GeneratorException;
import cn.kunter.generator.java.JavaTypeResolver;
import cn.kunter.generator.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * MySQL数据源
 * @author yangziran
 * @version 1.0 2021/7/20
 */
@Slf4j
public class MySqlDataSource implements DataSource {

    private JdbcConnectionFactory jdbcConnectionFactory;

    public MySqlDataSource(Properties properties) {
        var driverClass = properties.getProperty("db.driverClass");
        var connectionUrl = properties.getProperty("db.url");
        var userId = properties.getProperty("db.username");
        var password = properties.getProperty("db.password");

        var jdbcConnectionConfig = JdbcConnectionConfig.builder().driverClass(driverClass).connectionUrl(connectionUrl).userId(userId).password(password).build();
        this.jdbcConnectionFactory = new JdbcConnectionFactory(jdbcConnectionConfig);
    }

    @Override
    public List<Table> getTables() throws GeneratorException {

        List<Table> tables = Lists.newArrayList();
        try (var connection = jdbcConnectionFactory.getConnection()) {
            var metaData = connection.getMetaData();

            // 第一个参数：数据库名称，第二个参数：模式、登录名，第三个参数：表名称，第四个参数：类型(数组)
            var set = metaData.getSchemas(connection.getCatalog(), "");
            var schema = "";
            if (set.next()) {
                schema = set.getString(2);
            }
            var tableSet = metaData.getTables(connection.getCatalog(), schema, "%", new String[]{"TABLE"});

            while (tableSet.next()) {
                // 表名称（物理名称）
                var tableName = tableSet.getString("TABLE_NAME");
                // 表备注（表名称）
                var tableRemarks = tableSet.getString("REMARKS");
                log.info("tableName: {}, tableRemarks: {}", tableName, tableRemarks);

                // 将表名称转换为类名称
                var tableJavaName = StringUtils.convertTableNameToClass(tableName.toLowerCase(), "_", false);
                // 构造表信息对象
                var table = Table.builder().tableName(tableName).javaName(tableJavaName).remarks(tableRemarks).build();
                log.debug("table: {}", JSON.toJSONString(table));

                // 获取到主键集合
                var key = metaData.getPrimaryKeys(connection.getCatalog(), schema, tableName);
                while (key.next()) {
                    var columnName = key.getString("COLUMN_NAME");
                    if (columnName.indexOf("‘") == 0 && columnName.lastIndexOf("’") > 1) {
                        columnName = columnName.substring(1, columnName.length() - 1);
                    }

                    // 构造字段信息对象
                    var columnJavaName = StringUtils.convertFieldToParameter(columnName, "_");
                    var column = Column.builder().columnName(columnName).javaName(columnJavaName).primaryKey(true).primaryKeyOrder(String.valueOf(key.getRow())).build();
                    table.addPrimaryKey(column);
                }
                // 关闭连接
                key.close();

                // 获取到列集合
                var columns = metaData.getColumns(connection.getCatalog(), schema, tableName, "%");
                while (columns.next()) {
                    var columnName = columns.getString("COLUMN_NAME");
                    if (columnName.indexOf("‘") == 0 && columnName.lastIndexOf("’") > 1) {
                        columnName = columnName.substring(1, columnName.length() - 1);
                    }
                    var serial = String.valueOf(columns.getRow());
                    var jdbcType = JavaTypeResolver.getJdbcType(columns.getInt("DATA_TYPE"));
                    var remarks = columns.getString("REMARKS");
                    var notNull = columns.getString("IS_NULLABLE").equalsIgnoreCase("YES") ? true : false;
                    var length = columns.getInt("COLUMN_SIZE");

                    // 构造字段信息对象
                    var columnJavaName = StringUtils.convertFieldToParameter(columnName, "_");
                    var columnJavaType = JavaTypeResolver.getJavaType(jdbcType);
                    var column = Column.builder().serial(serial).columnName(columnName).javaName(columnJavaName).jdbcType(jdbcType).javaType(columnJavaType).length(String.valueOf(length)).notNull(notNull).remarks(remarks).build();
                    table.addColumn(column);
                }
                // 关闭连接
                columns.close();

                tables.add(table);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return tables;
    }

    @Override
    public SourceType getSourceType() {
        return SourceType.MYSQL;
    }

}
