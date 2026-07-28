package cn.kunter.generator.datasource.db;

import cn.kunter.generator.exception.DataSourceException;
import java.sql.Connection;
import java.util.Properties;

/**
 * 连接工厂
 * @author yangziran
 * @version 1.0 2021/7/21
 */
public interface ConnectionFactory {

    /**
     * 获取连接
     * @return 数据库连接
     * @throws DataSourceException 数据源异常
     */
    Connection getConnection() throws DataSourceException;

    /**
     * 添加配置属性
     * @param properties
     */
    void addConfigurationProperties(Properties properties);

}
