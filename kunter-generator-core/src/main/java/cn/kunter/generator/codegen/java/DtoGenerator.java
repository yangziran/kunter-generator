package cn.kunter.generator.codegen.java;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.config.PackageHolder;
import cn.kunter.generator.entity.Column;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;
import cn.kunter.generator.util.FileUtils;
import cn.kunter.generator.util.OutputUtils;
import cn.kunter.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * DTO生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class DtoGenerator implements Generator {

    @Override
    public void maker(Table table) throws CodeGenerationException {
        String tableName = table.getTableName();
        String javaName = table.getJavaName();
        String dtoName = javaName + "Dto";

        String dtoPackages = PackageHolder.getDtoPackage(tableName);
        String baseDtoPackage = PackageHolder.getBaseDtoPackage();
        String eoPackage = PackageHolder.getEntityPackage(tableName);

        StringBuilder builder = new StringBuilder();

        // 包声明
        builder.append("package ").append(dtoPackages).append(";");
        OutputUtils.newLine(builder, 2);

        // 导入
        builder.append("import lombok.Data;");
        OutputUtils.newLine(builder);
        builder.append("import lombok.NoArgsConstructor;");
        OutputUtils.newLine(builder);
        builder.append("import lombok.AllArgsConstructor;");
        OutputUtils.newLine(builder);
        builder.append("import lombok.EqualsAndHashCode;");
        OutputUtils.newLine(builder);
        builder.append("import lombok.experimental.SuperBuilder;");
        OutputUtils.newLine(builder);
        builder.append("import io.github.linpeilie.annotations.AutoMapper;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(baseDtoPackage).append(".BaseDto;");
        OutputUtils.newLine(builder);
        builder.append("import ").append(eoPackage).append(".").append(javaName).append("Eo;");
        OutputUtils.newLine(builder, 2);

        // 类注释
        builder.append("/**");
        OutputUtils.newLine(builder);
        builder.append(" * ").append(tableName).append(" 表的响应出参视图对象");
        OutputUtils.newLine(builder);
        builder.append(" * @author kunter-generator");
        OutputUtils.newLine(builder);
        builder.append(" * @version 1.0 ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append(" */");
        OutputUtils.newLine(builder);

        // Lombok & AutoMapper 注解
        builder.append("@Data");
        OutputUtils.newLine(builder);
        builder.append("@EqualsAndHashCode(callSuper = true)");
        OutputUtils.newLine(builder);
        builder.append("@NoArgsConstructor");
        OutputUtils.newLine(builder);
        builder.append("@AllArgsConstructor");
        OutputUtils.newLine(builder);
        builder.append("@SuperBuilder");
        OutputUtils.newLine(builder);
        builder.append("@AutoMapper(target = ").append(javaName).append("Eo.class)");
        OutputUtils.newLine(builder);

        // 类定义
        builder.append("public class ").append(dtoName).append(" extends BaseDto {");
        OutputUtils.newLine(builder, 2);

        // 字段定义
        for (Column column : table.getColumns()) {
            OutputUtils.javaIndent(builder, 1);
            builder.append("/** ").append(StringUtils.isBlank(column.getRemarks()) ? column.getColumnName() : column.getRemarks()).append(" */");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            
            String javaType = column.getJavaType();
            if (StringUtils.isBlank(javaType)) {
                javaType = "String";
            } else if (javaType.contains(".")) {
                javaType = javaType.substring(javaType.lastIndexOf(".") + 1);
            }
            
            builder.append("private ").append(javaType).append(" ").append(column.getJavaName()).append(";");
            OutputUtils.newLine(builder, 2);
        }

        builder.append("}");
        OutputUtils.newLine(builder);

        // 输出文件
        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + dtoPackages.replaceAll("\\.", "/") + "/" + dtoName + ".java";
        FileUtils.writeFile(filePath, builder.toString());
    }

}
