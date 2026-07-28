package cn.kunter.generator.datasource.db;

import cn.kunter.generator.config.JdbcConnectionConfig;
import cn.kunter.generator.util.ObjectFactory;
import cn.kunter.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import cn.kunter.generator.exception.DataSourceException;
import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

/**
 * JDBC连接工厂
 * @author yangziran
 * @version 1.0 2021/7/21
 */
@Slf4j
public class JdbcConnectionFactory implements ConnectionFactory {

    private String driverClass;
    private String connectionUrl;
    private String userId;
    private String password;
    private Properties otherProperties;

    public JdbcConnectionFactory(JdbcConnectionConfig config) {
        super();
        this.driverClass = config.getDriverClass();
        this.connectionUrl = config.getConnectionUrl();
        this.userId = config.getUserId();
        this.password = config.getPassword();
        this.otherProperties = config.getProperties();
    }

    /**
     * 获取数据库连接
     *
     * @return Connection 数据库连接
     * @throws DataSourceException 数据源异常
     */
    @Override
    public Connection getConnection() throws DataSourceException {

        var properties = new Properties();
        if (StringUtils.isNotBlank(userId)) {
            properties.setProperty("user", userId);
        }
        if (StringUtils.isNotBlank(password)) {
            properties.setProperty("password", password);
        }
        properties.putAll(otherProperties);
        // 设置可以获取remarks信息
        properties.setProperty("remarks", "true");
        // 设置可以获取tables remarks信息
        properties.setProperty("useInformationSchema", "true");

        var driver = getDriver();
        try {
            var connection = driver.connect(connectionUrl, properties);
            if (ObjectUtils.isEmpty(connection)) {
                throw new DataSourceException("无法连接到数据库(可能是驱动或URL错误)");
            }
            return connection;
        } catch (Exception e) {
            throw new DataSourceException("获取数据库连接发生异常", e);
        }
    }

    /**
     * 获取JDBC驱动
     *
     * @return Driver JDBC驱动程序
     * @throws DataSourceException 数据源异常
     */
    private Driver getDriver() throws DataSourceException {
        Driver driver;
        try {
            var clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new DataSourceException("获取JDBC驱动程序时发生异常", e);
        }

        return driver;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        driverClass = properties.getProperty("driverClass");
        connectionUrl = properties.getProperty("connectionURL");
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
