package cn.kunter.generator.datasource.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 连接工厂
 * @author yangziran
 * @version 1.0 2021/7/21
 */
public interface ConnectionFactory {

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * 添加配置属性
     * @param properties
     */
    void addConfigurationProperties(Properties properties);

}
