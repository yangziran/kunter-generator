package cn.kunter.generator.demo;

import cn.kunter.generator.codegen.GeneratorFactory;
import cn.kunter.generator.datasource.DataSourceFactory;
import cn.kunter.generator.entity.Table;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心引擎直接调用启动示例 (Demo)
 * @author yangziran
 * @version 1.0 2026/07/28
 */
public class CoreRunnerDemo {

    private static final Logger log = LoggerFactory.getLogger(CoreRunnerDemo.class);

    public static void main(String[] args) {
        log.info("Kunter Generator Demo 已启动...");
        try {
            var dataSource = DataSourceFactory.getDataSource();
            if (dataSource == null) {
                log.error("无法初始化数据源，请检查 resources/generatorConfig.properties");
                return;
            }
            List<Table> tables = dataSource.getTables();
            if (tables == null || tables.isEmpty()) {
                log.warn("未解析到任何表结构");
                return;
            }
            log.info("成功解析表结构，共计 {} 个表", tables.size());
            
            // 调度全量生成
            GeneratorFactory.executeAll(tables);
            
            log.info("Kunter Generator Demo 已完成。代码已生成到 target/generated-sources 目录下。");
        } catch (Exception e) {
            log.error("代码生成执行异常 (请检查 generatorConfig.properties 中配置的数据源是否可连通)", e);
        }
    }

}
