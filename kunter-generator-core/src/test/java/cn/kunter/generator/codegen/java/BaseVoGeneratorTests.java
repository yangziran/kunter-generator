package cn.kunter.generator.codegen.java;

import cn.kunter.generator.datasource.DataSourceFactory;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.config.Context;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * BaseVo 生成器测试
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
class BaseVoGeneratorTests {

    List<Table> tables;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        var dataSource = DataSourceFactory.getDataSource();
        assertNotNull(dataSource);
        tables = dataSource.getTables();
        assertNotNull(tables);
        log.info("表数量: {}", tables.size());
    }

    @AfterEach
    void tearDown() {
        tables.clear();
    }

    @Test
    void maker() throws Exception {
        Context.getProperties().setProperty("targetProject", "target/generated-test-sources");
        var generator = new BaseVoGenerator();
        assertNotNull(generator);

        generator.maker(tables);
        
        java.io.File dir = new java.io.File("target/generated-test-sources");
        org.junit.jupiter.api.Assertions.assertTrue(dir.exists());
        org.junit.jupiter.api.Assertions.assertTrue(dir.listFiles() != null && dir.listFiles().length > 0);
    }

}
