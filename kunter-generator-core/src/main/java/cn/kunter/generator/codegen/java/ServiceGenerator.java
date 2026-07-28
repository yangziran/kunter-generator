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
 * Service生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class ServiceGenerator implements Generator {

    @Override
    public void maker(Table table) throws CodeGenerationException {
        String tableName = table.getTableName();
        String javaName = table.getJavaName();
        String serviceName = javaName + "Service";

        String servicePackages = PackageHolder.getServicePackage(tableName);
        String voPackage = PackageHolder.getVoPackage(tableName);
        String dtoPackage = PackageHolder.getDtoPackage(tableName);

        StringBuilder builder = new StringBuilder();

        // 包声明
        builder.append("package ").append(servicePackages).append(";");
        OutputUtils.newLine(builder, 2);

        // 导入
        builder.append("import java.util.List;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(voPackage).append(".").append(javaName).append("Vo;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(dtoPackage).append(".").append(javaName).append("Dto;");
        OutputUtils.newLine(builder, 2);

        // 类注释
        builder.append("/**");
        OutputUtils.newLine(builder);
        builder.append(" * ").append(tableName).append(" 表的业务接口类");
        OutputUtils.newLine(builder);
        builder.append(" * @author kunter-generator");
        OutputUtils.newLine(builder);
        builder.append(" * @version 1.0 ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append(" */");
        OutputUtils.newLine(builder);
        
        builder.append("public interface ").append(serviceName).append(" {");
        OutputUtils.newLine(builder, 2);
        
        // 基础方法
        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 新增 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("void add(").append(javaName).append("Vo vo);");
        OutputUtils.newLine(builder, 2);

        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 更新 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("void update(").append(javaName).append("Vo vo);");
        OutputUtils.newLine(builder, 2);

        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 删除 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("void delete(Long id);");
        OutputUtils.newLine(builder, 2);

        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 获取单条记录 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append(javaName).append("Dto get(Long id);");
        OutputUtils.newLine(builder, 2);

        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 列表查询 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("List<").append(javaName).append("Dto> list();");
        OutputUtils.newLine(builder, 2);
        
        builder.append("}");
        OutputUtils.newLine(builder);

        // 输出文件
        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + servicePackages.replaceAll("\\.", "/") + "/" + serviceName + ".java";
        boolean override = "true".equalsIgnoreCase(Context.getProperty("file.override"));
        FileUtils.writeFile(filePath, builder.toString(), override);
    }

}
