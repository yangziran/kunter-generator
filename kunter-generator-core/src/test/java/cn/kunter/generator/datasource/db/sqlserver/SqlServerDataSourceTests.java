package cn.kunter.generator.datasource.db.sqlserver;

import cn.kunter.generator.datasource.DataSourceFactory;
import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * SqlServerDataSource 测试
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
class SqlServerDataSourceTests {

    @Disabled
    @Test
    void getTables() throws Exception {
        var dataSource = DataSourceFactory.getDataSource(SourceType.SQLSERVER);
        assertNotNull(dataSource);
        var tableList = dataSource.getTables();
        assertNotNull(tableList);

        tableList.forEach(table -> {
            log.info(table.getTableName());
        });
    }

}
