package cn.kunter.generator.codegen.java;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.config.Context;
import cn.kunter.generator.config.PackageHolder;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;
import cn.kunter.generator.util.FileUtils;
import cn.kunter.generator.util.OutputUtils;
import cn.kunter.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * Service实现生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class ServiceImplGenerator implements Generator {

    @Override
    public void maker(Table table) throws CodeGenerationException {
        String tableName = table.getTableName();
        String javaName = table.getJavaName();
        String serviceImplName = javaName + "ServiceImpl";
        String eoName = javaName + "Eo";

        String serviceImplPackages = PackageHolder.getServiceImplPackage(tableName);
        String daoPackages = PackageHolder.getDaoPackage(tableName);
        String servicePackages = PackageHolder.getServicePackage(tableName);
        
        String voPackage = PackageHolder.getVoPackage(tableName);
        String dtoPackage = PackageHolder.getDtoPackage(tableName);
        String entityPackages = PackageHolder.getEntityPackage(tableName);

        StringBuilder builder = new StringBuilder();

        // 包声明
        builder.append("package ").append(serviceImplPackages).append(";");
        OutputUtils.newLine(builder, 2);

        // 导入
        builder.append("import static org.mybatis.dynamic.sql.SqlBuilder.*;");
        OutputUtils.newLine(builder);
        builder.append("import org.mybatis.dynamic.sql.render.RenderingStrategies;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(daoPackages).append(".").append(javaName).append("DynamicSqlSupport.*;");
        OutputUtils.newLine(builder, 2);

        builder.append("import org.springframework.stereotype.Service;");
        OutputUtils.newLine(builder);
        builder.append("import javax.annotation.Resource;");
        OutputUtils.newLine(builder);
        builder.append("import java.util.List;");
        OutputUtils.newLine(builder);
        builder.append("import java.util.stream.Collectors;");
        OutputUtils.newLine(builder);
        builder.append("import io.github.linpeilie.Converter;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(daoPackages).append(".").append(javaName).append("Dao;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(servicePackages).append(".").append(javaName).append("Service;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(entityPackages).append(".").append(eoName).append(";");
        OutputUtils.newLine(builder);
        builder.append("import ").append(voPackage).append(".").append(javaName).append("Vo;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(dtoPackage).append(".").append(javaName).append("Dto;");
        OutputUtils.newLine(builder, 2);

        // 类注释
        builder.append("/**");
        OutputUtils.newLine(builder);
        builder.append(" * ").append(tableName).append(" 表的业务实现类");
        OutputUtils.newLine(builder);
        builder.append(" * @author kunter-generator");
        OutputUtils.newLine(builder);
        builder.append(" * @version 1.0 ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append(" */");
        OutputUtils.newLine(builder);
        
        builder.append("@Service");
        OutputUtils.newLine(builder);
        builder.append("public class ").append(serviceImplName).append(" implements ").append(javaName).append("Service {");
        OutputUtils.newLine(builder, 2);

        String daoInstance = StringUtils.uncapitalize(javaName) + "Dao";
        String tableInstance = StringUtils.uncapitalize(javaName);

        // 注入依赖
        OutputUtils.javaIndent(builder, 1);
        builder.append("@Resource");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("private ").append(javaName).append("Dao ").append(daoInstance).append(";");
        OutputUtils.newLine(builder, 2);
        
        OutputUtils.javaIndent(builder, 1);
        builder.append("@Resource");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("private Converter converter;");
        OutputUtils.newLine(builder, 2);
        
        // 实现方法 add
        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 新增 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("@Override");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public void add(").append(javaName).append("Vo vo) {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append(eoName).append(" eo = converter.convert(vo, ").append(eoName).append(".class);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("var insertStatement = insert(eo).into(").append(tableInstance).append(").build().render(RenderingStrategies.MYBATIS3);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append(daoInstance).append(".insert(insertStatement);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        // 实现方法 update
        // 注意，update 需要基于主键，这里假设有 id
        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 更新 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("@Override");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public void update(").append(javaName).append("Vo vo) {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        // 为了演示方便，仅抛出异常或展示简单结构。真实场景需要动态匹配主键，这里简化使用主键 id
        builder.append(eoName).append(" eo = converter.convert(vo, ").append(eoName).append(".class);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("// 根据主键更新 (请根据实际表结构调整主键列)");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("var updateStatement = update(").append(tableInstance).append(")");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".set(").append(javaName).append("DynamicSqlSupport.id).equalToWhenPresent(eo.getId()) // 示例字段");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".where(").append(javaName).append("DynamicSqlSupport.id, isEqualTo(eo.getId()))");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".build().render(RenderingStrategies.MYBATIS3);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append(daoInstance).append(".update(updateStatement);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        // 实现方法 delete
        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 删除 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("@Override");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public void delete(Long id) {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("var deleteStatement = deleteFrom(").append(tableInstance).append(")");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".where(").append(javaName).append("DynamicSqlSupport.id, isEqualTo(id))");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".build().render(RenderingStrategies.MYBATIS3);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append(daoInstance).append(".delete(deleteStatement);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        // 实现方法 get
        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 获取单条记录 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("@Override");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public ").append(javaName).append("Dto get(Long id) {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("var selectStatement = select(").append(tableInstance).append(".allColumns())");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".from(").append(tableInstance).append(")");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".where(").append(javaName).append("DynamicSqlSupport.id, isEqualTo(id))");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".build().render(RenderingStrategies.MYBATIS3);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("return ").append(daoInstance).append(".selectOne(selectStatement)");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".map(eo -> converter.convert(eo, ").append(javaName).append("Dto.class))");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".orElse(null);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);
        
        // 实现方法 list
        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 列表查询 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("@Override");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public List<").append(javaName).append("Dto> list() {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("var selectStatement = select(").append(tableInstance).append(".allColumns())");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".from(").append(tableInstance).append(")");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append(".build().render(RenderingStrategies.MYBATIS3);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("List<").append(eoName).append("> eos = ").append(daoInstance).append(".selectMany(selectStatement);");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("return eos.stream().map(eo -> converter.convert(eo, ").append(javaName).append("Dto.class)).collect(Collectors.toList());");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder, 2);

        builder.append("}");
        OutputUtils.newLine(builder);

        // 输出文件
        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + serviceImplPackages.replaceAll("\\.", "/") + "/" + serviceImplName + ".java";
        boolean override = "true".equalsIgnoreCase(Context.getProperty("file.override"));
        FileUtils.writeFile(filePath, builder.toString(), override);
    }

}
