package cn.kunter.generator.datasource.db.sqlserver;

import cn.kunter.generator.config.JdbcConnectionConfig;
import cn.kunter.generator.datasource.DataSource;
import cn.kunter.generator.datasource.db.JdbcConnectionFactory;
import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Properties;

/**
 * SqlServer数据源
 * @author yangziran
 * @version 1.0 2021/12/19
 */
@Slf4j
public class SqlServerDataSource implements DataSource {

    private JdbcConnectionFactory jdbcConnectionFactory;

    public SqlServerDataSource(Properties properties) {
        var driverClass = properties.getProperty("db.driverClass");
        var connectionUrl = properties.getProperty("db.url");
        var userId = properties.getProperty("db.username");
        var password = properties.getProperty("db.password");

        var jdbcConnectionConfig = JdbcConnectionConfig.builder().driverClass(driverClass).connectionUrl(connectionUrl).userId(userId).password(password).build();
        this.jdbcConnectionFactory = new JdbcConnectionFactory(jdbcConnectionConfig);
    }

    @Override
    public List<Table> getTables() throws GeneratorException {
        return Lists.newArrayList();
    }

    @Override
    public SourceType getSourceType() {
        return SourceType.SQLSERVER;
    }

}
