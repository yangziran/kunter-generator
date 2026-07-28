package cn.kunter.generator.datasource;

import cn.kunter.generator.config.Context;
import cn.kunter.generator.datasource.db.mysql.MySqlDataSource;
import cn.kunter.generator.datasource.db.oracle.OracleDataSource;
import cn.kunter.generator.datasource.db.postgresql.PostgreSqlDataSource;
import cn.kunter.generator.datasource.db.sqlserver.SqlServerDataSource;
import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.datasource.excel.ExcelDataSource;
import cn.kunter.generator.util.StringUtils;
import cn.kunter.generator.exception.ConfigurationException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 数据源工厂
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class DataSourceFactory {

    private static Map<String, DataSource> dataSourceMap = Maps.newHashMap();

    static {
        Properties properties = Context.getProperties();

        // 获取数据源类型
        var sourceType = Context.getProperty("sourceType");
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
                throw new ConfigurationException("未正确配置或不支持该数据源类型: " + sourceType);
            }
        }
    }

    /**
     * 根据数据源类型获取对应的数据源实例
     * @param sourceType 数据源类型
     * @return DataSource 数据源实例
     */
    public static DataSource getDataSource(SourceType sourceType) {
        if (!dataSourceMap.containsKey(sourceType.name())) {
            throw new ConfigurationException("请正确配置数据源");
        }
        return dataSourceMap.get(sourceType.name());
    }

    public static DataSource getDataSource() {
        var sourceType = Context.getProperty("sourceType");
        if (StringUtils.isBlank(sourceType)) {
            throw new ConfigurationException("参数sourceType未配置");
        }
        SourceType type = SourceType.valueOf(sourceType.toUpperCase());
        return getDataSource(type);
    }

}
