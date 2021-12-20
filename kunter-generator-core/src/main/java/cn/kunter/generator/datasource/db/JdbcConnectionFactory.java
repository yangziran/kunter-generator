package cn.kunter.generator.datasource.db;

import cn.kunter.generator.config.JdbcConnectionConfig;
import cn.kunter.generator.util.ObjectFactory;
import cn.kunter.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC连接工厂
 * @author yangziran
 * @version 1.0 2021/7/21
 */
@Slf4j
public class JdbcConnectionFactory implements ConnectionFactory {

    private String driverClass;
    private String connectionURL;
    private String userId;
    private String password;
    private Properties otherProperties;

    public JdbcConnectionFactory(JdbcConnectionConfig config) {
        super();
        this.driverClass = config.getDriverClass();
        this.connectionURL = config.getConnectionUrl();
        this.userId = config.getUserId();
        this.password = config.getPassword();
        this.otherProperties = config.getProperties();
    }

    @Override
    public Connection getConnection() throws SQLException {

        var properties = new Properties();
        if (StringUtils.isNotBlank(userId)) {
            properties.setProperty("user", userId);
        }
        if (StringUtils.isNotBlank(password)) {
            properties.setProperty("password", password);
        }
        properties.putAll(otherProperties);

        var driver = getDriver();
        var connection = driver.connect(connectionURL, properties);
        if (ObjectUtils.isEmpty(connection)) {
            log.error("getConnection 无法连接到数据库(可能是驱动或URL错误)");
            throw new SQLException("无法连接到数据库(可能是驱动或URL错误)");
        }

        return connection;
    }

    private Driver getDriver() {
        Driver driver;
        try {
            var clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            log.error("getDriver 获取JDBC驱动程序时发生异常", e);
            throw new RuntimeException("获取JDBC驱动程序时发生异常", e);
        }

        return driver;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        driverClass = properties.getProperty("driverClass");
        connectionURL = properties.getProperty("connectionURL");
        userId = properties.getProperty("userId");
        password = properties.getProperty("password");

        otherProperties = new Properties();
        otherProperties.putAll(properties);

        otherProperties.remove("driverClass");
        otherProperties.remove("connectionURL");
        otherProperties.remove("userId");
        otherProperties.remove("password");
    }

}
