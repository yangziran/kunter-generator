package cn.kunter.generator.demo;

import cn.kunter.generator.codegen.GeneratorFactory;
import cn.kunter.generator.config.Context;
import cn.kunter.generator.entity.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Demo 模块完整生成流程测试
 * @author yangziran
 * @version 1.0 2026/07/28
 */
class KunterGeneratorDemoTests {

    @TempDir
    static Path sharedTempDir;

    @BeforeAll
    static void setup() {
        // 将 targetProject 重定向到临时目录，防止污染 src/main/java
        Context.getProperties().setProperty("targetProject", sharedTempDir.toAbsolutePath().toString());
        // 使用 EXCEL 模式以避免对本地 MySQL 的依赖
        Context.getProperties().setProperty("sourceType", "EXCEL");
        Context.getProperties().setProperty("excel.filePath", "../docs/表结构一览.xlsm");
    }

    @Test
    void testEndToEndGeneration() throws Exception {
        Table table = Table.builder()
                .tableName("user_info")
                .javaName("UserInfo")
                .remarks("用户信息表")
                .build();
        
        cn.kunter.generator.entity.Column idColumn = cn.kunter.generator.entity.Column.builder()
                .columnName("id")
                .jdbcName("id")
                .javaName("id")
                .jdbcType("BIGINT")
                .javaType("java.lang.Long")
                .primaryKey(true)
                .build();
        table.addColumn(idColumn);
        table.addPrimaryKey(idColumn);
        
        List<Table> tables = List.of(table);

        // 执行生成
        GeneratorFactory.executeAll(tables);

        // 验证预期文件是否已成功生成
        File outputDir = new File(System.getProperty("user.dir") + "/target/generated-sources");
        
        // 检查基本预期的包结构
        // 默认情况下，PackageHolder 会将包生成到 cn.kunter + modelName (从 user_info 截取 -> user)
        String basePath = "cn/kunter/user";
        
        File eoDir = new File(outputDir, basePath + "/eo");
        assertTrue(eoDir.exists() && eoDir.isDirectory(), "Eo 目录应当存在");
        File[] eos = eoDir.listFiles((dir, name) -> name.endsWith("Eo.java"));
        assertNotNull(eos);
        assertTrue(eos.length > 0, "应当生成了 Eo 文件");

        File daoDir = new File(outputDir, basePath + "/dao");
        assertTrue(daoDir.exists() && daoDir.isDirectory(), "Dao 目录应当存在");
        File[] daos = daoDir.listFiles((dir, name) -> name.endsWith("Dao.java"));
        assertNotNull(daos);
        assertTrue(daos.length > 0, "应当生成了 Dao 文件");

        File serviceDir = new File(outputDir, basePath + "/service");
        assertTrue(serviceDir.exists() && serviceDir.isDirectory(), "Service 目录应当存在");
    }
}
