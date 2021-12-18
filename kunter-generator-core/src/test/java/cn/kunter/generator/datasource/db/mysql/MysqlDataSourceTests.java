package cn.kunter.generator.datasource.db.mysql;

import cn.kunter.generator.datasource.DataSourceFactory;
import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MysqlDataSourceTests {

    @Test
    void getTables() throws GeneratorException {
        var dataSource = DataSourceFactory.getDataSource(SourceType.MYSQL);
        assertNotNull(dataSource);
        List<Table> tableList = dataSource.getTables();
        assertNotNull(tableList);

        tableList.forEach(table -> {
            log.info(table.getTableName());
        });
    }

}
