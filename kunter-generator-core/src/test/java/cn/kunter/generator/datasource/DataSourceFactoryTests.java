package cn.kunter.generator.datasource;

import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class DataSourceFactoryTests {

    @Test
    void getDataSource() throws GeneratorException {
        var dataSource = DataSourceFactory.getDataSource(SourceType.EXCEL);
        assertNotNull(dataSource);
        List<Table> tableList = dataSource.getTables();
        assertNotNull(tableList);

        tableList.forEach(table -> {
            log.info(table.getTableName());
        });
    }

}
