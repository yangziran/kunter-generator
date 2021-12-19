package cn.kunter.generator.datasource;

import cn.kunter.generator.datasource.db.mysql.MySqlDataSource;
import cn.kunter.generator.datasource.db.oracle.OracleDataSource;
import cn.kunter.generator.datasource.db.postgresql.PostgreSqlDataSource;
import cn.kunter.generator.datasource.db.sqlserver.SqlServerDataSource;
import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.datasource.excel.ExcelDataSource;
import cn.kunter.generator.util.StringUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

/**
 * 数据源工厂
 * @author nature
 * @version 1.0 2021/8/14
 */
@Slf4j
public class DataSourceFactory {

    private static Map<String, DataSource> dataSourceMap = Maps.newHashMap();

    static {
        var classLoader = DataSourceFactory.class.getClassLoader();
        var properties = new Properties();
        try (var inputStream = classLoader.getResourceAsStream("generatorConfig.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("配置加载错误", e);
        }

        // 获取数据源类型
        var sourceType = properties.getProperty("sourceType");
        Optional<SourceType> sourceTypeOptional = Arrays.stream(SourceType.values()).filter(type -> StringUtils.equalsAnyIgnoreCase(type.name(), sourceType)).findAny();
        if (sourceTypeOptional.isPresent()) {
            if (StringUtils.equalsAnyIgnoreCase(sourceType, SourceType.MYSQL.name())) {

                var mysqlDataSource = new MySqlDataSource(properties);
                dataSourceMap.put(mysqlDataSource.getSourceType().name(), mysqlDataSource);
            } else if (StringUtils.equalsAnyIgnoreCase(sourceType, SourceType.POSTGRESQL.name())) {

                var postgreSqlDataSource = new PostgreSqlDataSource(properties);
                dataSourceMap.put(postgreSqlDataSource.getSourceType().name(), postgreSqlDataSource);
            } else if (StringUtils.equalsAnyIgnoreCase(sourceType, SourceType.ORACLE.name())) {

                var oracleDataSource = new OracleDataSource(properties);
                dataSourceMap.put(oracleDataSource.getSourceType().name(), oracleDataSource);
            } else if (StringUtils.equalsAnyIgnoreCase(sourceType, SourceType.SQLSERVER.name())) {

                var sqlServerDataSource = new SqlServerDataSource(properties);
                dataSourceMap.put(sqlServerDataSource.getSourceType().name(), sqlServerDataSource);
            } else if (StringUtils.equalsAnyIgnoreCase(sourceType, SourceType.EXCEL.name())) {

                var filePath = properties.getProperty("excel.filePath");
                if (StringUtils.isNotBlank(filePath)) {
                    var excelDataSource = new ExcelDataSource(filePath);
                    dataSourceMap.put(excelDataSource.getSourceType().name(), excelDataSource);
                }
            } else {
                log.error("请正确配置数据源类型");
            }
        }
    }

    public static DataSource getDataSource(SourceType sourceType) {
        if (!dataSourceMap.containsKey(sourceType.name())) {
            log.error("请正确配置数据源");
            return null;
        }
        return dataSourceMap.get(sourceType.name());
    }

}
