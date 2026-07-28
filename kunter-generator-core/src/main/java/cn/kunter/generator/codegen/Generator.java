package cn.kunter.generator.codegen;

import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;

import java.util.List;

/**
 * Generator Interface
 * @author yangziran
 * @version 1.0 2026/07/28
 */
public interface Generator {

    /**
     * 生成代码（单表）
     * @param table 表
     * @throws CodeGenerationException
     */
    default void maker(Table table) throws CodeGenerationException {}

    /**
     * 生成代码（多表）
     * @param tables 列表
     * @throws CodeGenerationException
     */
    default void maker(List<Table> tables) throws CodeGenerationException {}

}
