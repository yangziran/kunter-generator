package cn.kunter.generator.codegen.excel;

import cn.kunter.generator.datasource.DataSourceFactory;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.GeneratorException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Excel生成器测试
 * @author yangziran
 * @version 1.0 2022/1/13
 */
@Slf4j
class ExcelGeneratorTests {

    List<Table> tables;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        var dataSource = DataSourceFactory.getDataSource();
        assertNotNull(dataSource);
        tables = dataSource.getTables();
        assertNotNull(tables);
        log.info("table size: {}", tables.size());
    }

    @AfterEach
    void tearDown() {
        tables.clear();
    }

    @Test
    void maker() {
        var generator = new ExcelGenerator();
        assertNotNull(generator);

        try {
            generator.maker(tables);
        } catch (GeneratorException e) {
            log.error(e.getMessage(), e);
        }
    }

}
