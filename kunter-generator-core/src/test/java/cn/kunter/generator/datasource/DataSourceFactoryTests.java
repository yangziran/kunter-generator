package cn.kunter.generator.datasource;

import cn.kunter.generator.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class DataSourceFactoryTests {

    @Test
    void getDataSource() throws GeneratorException {
        var dataSource = DataSourceFactory.getDataSource();
        assertNotNull(dataSource);
        var tableList = dataSource.getTables();
        assertNotNull(tableList);

        tableList.forEach(table -> {
            log.info(table.getTableName());
        });
    }

}
