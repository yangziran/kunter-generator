package cn.kunter.generator.datasource.db.sqlserver;

import cn.kunter.generator.datasource.DataSourceFactory;
import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class SqlServerDataSourceTests {

    @Test
    void getTables() throws GeneratorException {
        var dataSource = DataSourceFactory.getDataSource(SourceType.SQLSERVER);
        assertNotNull(dataSource);
        var tableList = dataSource.getTables();
        assertNotNull(tableList);

        tableList.forEach(table -> {
            log.info(table.getTableName());
        });
    }

}
