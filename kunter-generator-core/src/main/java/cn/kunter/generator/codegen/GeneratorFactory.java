package cn.kunter.generator.codegen;

import cn.kunter.generator.codegen.excel.ExcelGenerator;
import cn.kunter.generator.codegen.java.*;
import cn.kunter.generator.codegen.sql.DdlGenerator;
import cn.kunter.generator.entity.Table;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 生成器工厂
 * @author yangziran
 * @version 1.0 2021/12/20
 */
@Slf4j
public class GeneratorFactory {

    private static Map<String, Generator> generatorMap = Maps.newHashMap();

    static {
        generatorMap.put("baseEo", new BaseEoGenerator());
        generatorMap.put("baseVo", new BaseVoGenerator());
        generatorMap.put("baseDto", new BaseDtoGenerator());
        generatorMap.put("entity", new EntityGenerator());
        generatorMap.put("vo", new VoGenerator());
        generatorMap.put("dto", new DtoGenerator());
        generatorMap.put("dao", new DaoGenerator());
        generatorMap.put("dynamicSqlSupport", new DynamicSqlSupportGenerator());
        generatorMap.put("service", new ServiceGenerator());
        generatorMap.put("serviceImpl", new ServiceImplGenerator());
        generatorMap.put("excel", new ExcelGenerator());
        generatorMap.put("ddl", new DdlGenerator());
        generatorMap.put("controller", new ControllerGenerator());
    }

    /**
     * 执行所有已注册的生成器
     * @param tables 表集合
     */
    public static void executeAll(List<Table> tables) {
        log.info("开始执行代码生成，共计 {} 个表", tables.size());
        for (Map.Entry<String, Generator> entry : generatorMap.entrySet()) {
            String name = entry.getKey();
            Generator generator = entry.getValue();
            log.info("正在执行生成器: {}", name);
            try {
                // 尝试批量生成接口（如 Excel、DDL）
                generator.maker(tables);
                // 尝试逐表生成接口（如 Java 代码）
                for (Table table : tables) {
                    generator.maker(table);
                }
            } catch (Exception e) {
                log.error("生成器 {} 执行异常: ", name, e);
            }
        }
        log.info("代码生成执行完毕！");
    }

}
