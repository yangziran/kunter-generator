package cn.kunter.generator.codegen.java;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.config.Context;
import cn.kunter.generator.config.PackageHolder;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;
import cn.kunter.generator.util.FileUtils;
import cn.kunter.generator.util.OutputUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * DAO生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class DaoGenerator implements Generator {

    @Override
    public void maker(Table table) throws CodeGenerationException {
        String tableName = table.getTableName();
        String javaName = table.getJavaName();
        String daoName = javaName + "Dao";
        String eoName = javaName + "Eo";
        String mapperName = javaName + "Mapper";

        String daoPackages = PackageHolder.getDaoPackage(tableName);
        String entityPackages = PackageHolder.getEntityPackage(tableName);

        StringBuilder builder = new StringBuilder();

        if (Context.isDynamicPlusEnable()) {
            // 包声明
            builder.append("package ").append(daoPackages).append(";");
            OutputUtils.newLine(builder, 2);

            builder.append("import org.springframework.stereotype.Repository;");
            OutputUtils.newLine(builder);
            builder.append("import org.springframework.beans.factory.annotation.Autowired;");
            OutputUtils.newLine(builder);
            builder.append("import ").append(entityPackages).append(".").append(eoName).append(";");
            OutputUtils.newLine(builder, 2);

            // 类注释
            builder.append("/**");
            OutputUtils.newLine(builder);
            builder.append(" * ").append(tableName).append(" 表的防腐层 (DAO)");
            OutputUtils.newLine(builder);
            builder.append(" * 该类将不会被工具覆盖，您可以在此处编写复杂的多表查询和定制 SQL。");
            OutputUtils.newLine(builder);
            builder.append(" * @author kunter-generator");
            OutputUtils.newLine(builder);
            builder.append(" * @version 1.0 ").append(LocalDate.now());
            OutputUtils.newLine(builder);
            builder.append(" */");
            OutputUtils.newLine(builder);
            
            builder.append("@Repository");
            OutputUtils.newLine(builder);
            builder.append("public class ").append(daoName).append(" {");
            OutputUtils.newLine(builder, 2);
            
            OutputUtils.javaIndent(builder, 1);
            builder.append("@Autowired");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            // 假设 Mapper 和 Entity 同包或者有自己的规则，这里简化用全限定名或同包
            builder.append("private ").append(entityPackages).append(".").append(mapperName).append(" mapper;");
            OutputUtils.newLine(builder, 2);

            // 示例方法
            OutputUtils.javaIndent(builder, 1);
            builder.append("public ").append(eoName).append(" selectByPrimaryKey(Long id) {");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 2);
            builder.append("return mapper.selectByPrimaryKey(id);");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("}");
            OutputUtils.newLine(builder, 2);
            
            builder.append("}");
            OutputUtils.newLine(builder);

            String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
            String filePath = targetPath + "/" + daoPackages.replaceAll("\\.", "/") + "/" + javaName + "Dao.java";
            
            // 开启 plus 后，强制只生成一次，不可覆盖
            FileUtils.writeFile(filePath, builder.toString(), false);
            log.info("DAO 防腐层生成完毕: {}", filePath);
        } else {
            // 原生 MyBatis Dynamic SQL Dao 接口生成模式
            builder.append("package ").append(daoPackages).append(";");
            OutputUtils.newLine(builder, 2);

            builder.append("import java.util.List;");
            OutputUtils.newLine(builder);
            builder.append("import java.util.Optional;");
            OutputUtils.newLine(builder);
            builder.append("import org.apache.ibatis.annotations.Mapper;");
            OutputUtils.newLine(builder);
            builder.append("import org.apache.ibatis.annotations.DeleteProvider;");
            OutputUtils.newLine(builder);
            builder.append("import org.apache.ibatis.annotations.InsertProvider;");
            OutputUtils.newLine(builder);
            builder.append("import org.apache.ibatis.annotations.SelectProvider;");
            OutputUtils.newLine(builder);
            builder.append("import org.apache.ibatis.annotations.UpdateProvider;");
            OutputUtils.newLine(builder);
            builder.append("import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;");
            OutputUtils.newLine(builder);
            builder.append("import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;");
            OutputUtils.newLine(builder);
            builder.append("import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;");
            OutputUtils.newLine(builder);
            builder.append("import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;");
            OutputUtils.newLine(builder);
            builder.append("import org.mybatis.dynamic.sql.util.SqlProviderAdapter;");
            OutputUtils.newLine(builder);
            builder.append("import ").append(entityPackages).append(".").append(eoName).append(";");
            OutputUtils.newLine(builder, 2);

            builder.append("/**");
            OutputUtils.newLine(builder);
            builder.append(" * ").append(tableName).append(" 表的DAO接口类");
            OutputUtils.newLine(builder);
            builder.append(" * 继承 MyBatis Dynamic SQL 基础接口以获得原生强类型查询能力");
            OutputUtils.newLine(builder);
            builder.append(" * @author kunter-generator");
            OutputUtils.newLine(builder);
            builder.append(" * @version 1.0 ").append(LocalDate.now());
            OutputUtils.newLine(builder);
            builder.append(" */");
            OutputUtils.newLine(builder);
            
            builder.append("@Mapper");
            OutputUtils.newLine(builder);
            builder.append("public interface ").append(daoName).append(" {");
            OutputUtils.newLine(builder, 2);
            
            OutputUtils.javaIndent(builder, 1);
            builder.append("/** 批量查询 */");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("@SelectProvider(type=SqlProviderAdapter.class, method=\"select\")");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("List<").append(eoName).append("> selectMany(SelectStatementProvider selectStatement);");
            OutputUtils.newLine(builder, 2);

            OutputUtils.javaIndent(builder, 1);
            builder.append("/** 单个查询 */");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("@SelectProvider(type=SqlProviderAdapter.class, method=\"select\")");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("Optional<").append(eoName).append("> selectOne(SelectStatementProvider selectStatement);");
            OutputUtils.newLine(builder, 2);

            OutputUtils.javaIndent(builder, 1);
            builder.append("/** 插入数据 */");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("@InsertProvider(type=SqlProviderAdapter.class, method=\"insert\")");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("int insert(InsertStatementProvider<").append(eoName).append("> insertStatement);");
            OutputUtils.newLine(builder, 2);

            OutputUtils.javaIndent(builder, 1);
            builder.append("/** 更新数据 */");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("@UpdateProvider(type=SqlProviderAdapter.class, method=\"update\")");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("int update(UpdateStatementProvider updateStatement);");
            OutputUtils.newLine(builder, 2);

            OutputUtils.javaIndent(builder, 1);
            builder.append("/** 删除数据 */");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("@DeleteProvider(type=SqlProviderAdapter.class, method=\"delete\")");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            builder.append("int delete(DeleteStatementProvider deleteStatement);");
            OutputUtils.newLine(builder, 2);
            
            builder.append("}");
            OutputUtils.newLine(builder);

            String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
            String filePath = targetPath + "/" + daoPackages.replaceAll("\\.", "/") + "/" + javaName + "Dao.java";
            boolean override = "true".equalsIgnoreCase(Context.getProperty("file.override"));
            FileUtils.writeFile(filePath, builder.toString(), override);
            log.info("DAO接口生成完毕: {}", filePath);
        }
    }

}
