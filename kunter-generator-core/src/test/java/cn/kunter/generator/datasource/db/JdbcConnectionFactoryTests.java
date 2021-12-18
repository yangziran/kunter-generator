package cn.kunter.generator.datasource.db;

import cn.kunter.generator.config.JdbcConnectionConfig;
import cn.kunter.generator.datasource.DataSourceFactory;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class JdbcConnectionFactoryTests {

    @Test
    void getConnection() {
        var classLoader = DataSourceFactory.class.getClassLoader();
        assertNotNull(classLoader);
        var properties = new Properties();
        try (var inputStream = classLoader.getResourceAsStream("generatorConfig.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("配置加载错误", e);
        }
        assertNotNull(properties);
        log.info("{}", JSON.toJSONString(properties));

        val driverClass = properties.getProperty("db.driverClass");
        val connectionUrl = properties.getProperty("db.url");
        val userId = properties.getProperty("db.username");
        val password = properties.getProperty("db.password");

        JdbcConnectionConfig jdbcConnectionConfig = JdbcConnectionConfig.builder().driverClass(driverClass).connectionUrl(connectionUrl).userId(userId).password(password).build();
        assertNotNull(jdbcConnectionConfig);
        log.info("{}", JSON.toJSONString(jdbcConnectionConfig));
        JdbcConnectionFactory jdbcConnectionFactory = new JdbcConnectionFactory(jdbcConnectionConfig);
        assertNotNull(jdbcConnectionFactory);
        try {
            Connection connection = jdbcConnectionFactory.getConnection();
            assertNotNull(connection);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

}
