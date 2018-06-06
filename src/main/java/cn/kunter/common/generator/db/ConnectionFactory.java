/**
 *
 */
package cn.kunter.common.generator.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import cn.kunter.common.generator.config.ConfigurationHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.type.DBType;
import cn.kunter.common.generator.util.ObjectFactory;
import cn.kunter.common.generator.util.StringUtility;

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
            driver = (Driver) clazz.getDeclaredConstructor().newInstance();
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
        // 不推荐建立无服务器的身份验证SSL连接
        // 据MySQL的5.5.45+，5.6.26+和5.7.6+要求SSL连接必须在默认情况下建立的，
        // 如果没有设置明确的选项。为了符合现有的应用程序不使用SSL的verifyServerCertificate属性设置为“假”。
        // 您需要既可以明确地设置useSSL= false来禁用SSL，或者设置useSSL= TRUE，并提供信任存储服务器证书验证。
        props.setProperty("useSSL", "false");
        // 设置可以获取remarks信息
        props.setProperty("remarks", "true");
        // 设置可以获取tables remarks信息
        props.setProperty("useInformationSchema", "true");
        // 设置连接编码
        props.setProperty("characterEncoding", "utf8");
        // 当数据库连接异常中断时，是否自动重新连接？
        props.setProperty("autoReconnect", "true");
        // 自动重连成功后，连接是否设置为只读？
        props.setProperty("failOverReadOnly", "false");

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