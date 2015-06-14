/**
 * 
 */
package org.generator.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import org.generator.config.ConfigurationHolder;
import org.generator.config.PropertyHolder;
import org.generator.type.DBType;
import org.generator.util.ObjectFactory;
import org.generator.util.StringUtility;

/**
 * 数据库连接工厂，单列
 * @author yangziran
 * @version 1.0 2014年9月11日
 */
public class ConnectionFactory {

    private static ConnectionFactory instance = new ConnectionFactory();

    public static ConnectionFactory getInstance() {
        return instance;
    }

    /**
     * 构造方法
     */
    private ConnectionFactory() {
        super();
    }

    /**
     * 获取数据库驱动类
     * @param config 数据库连接属性
     * @return Driver 数据库驱动
     * @author yangziran
     */
    private Driver getDriver(ConfigurationHolder config) {

        String driverClass = config.getDriverClass();
        Driver driver;

        try {
            Class<?> clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(MessageFormat.format("获取JDBC Driver错误", new Object[] {}), e);
        }

        return driver;
    }

    /**
     * 获取数据库连接
     * @param config 数据库连接属性
     * @return Connection 数据库连接
     * @throws SQLException
     * @author yangziran
     */
    public Connection getConnection(ConfigurationHolder config) throws SQLException {
        Driver driver = getDriver(config);

        Properties props = new Properties();
        // 设置可以获取remarks信息
        props.setProperty("remarks", "true");
        // 设置可以获取tables remarks信息
        props.setProperty("useInformationSchema", "true");
        props.setProperty("characterEncoding", "utf-8");

        if (StringUtility.isNotEmpty(config.getUserId())) {
            props.setProperty("user", config.getUserId());
        }

        if (StringUtility.isNotEmpty(config.getPassword())) {
            props.setProperty("password", config.getPassword());
        }

        Connection conn = driver.connect(config.getConnectionURL(), props);

        if (conn == null) {
            throw new SQLException(MessageFormat.format("无法连接到数据库（可能是驱动/URL错误）", new Object[] {}));
        }

        return conn;
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static Connection getConnection() throws SQLException {

        ConfigurationHolder config = new ConfigurationHolder();
        // 获取到数据库类型
        String db = DBType.valueOf(PropertyHolder.getJDBCProperty("DB")).getValue();
        config.setDriverClass(PropertyHolder.getJDBCProperty(db + ".driverClass"));
        config.setConnectionURL(PropertyHolder.getJDBCProperty(db + ".url"));
        config.setUserId(PropertyHolder.getJDBCProperty(db + ".username"));
        config.setPassword(PropertyHolder.getJDBCProperty(db + ".password"));

        Connection connection = ConnectionFactory.getInstance().getConnection(config);

        return connection;
    }
}