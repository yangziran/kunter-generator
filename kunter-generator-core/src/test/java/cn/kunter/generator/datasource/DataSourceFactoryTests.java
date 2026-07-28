package cn.kunter.generator.datasource;

import cn.kunter.generator.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import cn.kunter.generator.datasource.enums.SourceType;

/**
 * DataSourceFactory 测试
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
class DataSourceFactoryTests {

    @Disabled
    @Test
    void getDataSource() throws Exception {
        var dataSource = DataSourceFactory.getDataSource();
        assertNotNull(dataSource);
        var tableList = dataSource.getTables();
        assertNotNull(tableList);

        tableList.forEach(table -> {
            log.info(table.getTableName());
        });
    }

    @Test
    void testFailFastForInvalidSourceType() {
        assertThrows(IllegalArgumentException.class, () -> {
            DataSourceFactory.getDataSource(SourceType.valueOf("UNKNOWN"));
        });
    }

}
