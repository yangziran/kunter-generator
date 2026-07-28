package cn.kunter.generator.codegen.java;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.config.PackageHolder;
import cn.kunter.generator.entity.Column;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;
import cn.kunter.generator.util.FileUtils;
import cn.kunter.generator.config.PropertyHolder;
import cn.kunter.generator.util.OutputUtils;
import cn.kunter.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import cn.kunter.generator.config.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.io.File;

/**
 * 实体对象生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class EntityGenerator implements Generator {

    @Override
    public void maker(Table table) throws CodeGenerationException {
        String tableName = table.getTableName();
        String javaName = table.getJavaName();
        String eoName = javaName + "Eo";

        String entityPackages = PackageHolder.getEntityPackage(tableName);
        String baseEoPackage = PackageHolder.getBaseEoPackage();

        StringBuilder builder = new StringBuilder();
        
        // 包声明
        builder.append("package ").append(entityPackages).append(";");
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
        builder.append("import ").append(baseEoPackage).append(".BaseEo;");
        OutputUtils.newLine(builder, 2);

        boolean swaggerEnable = "true".equalsIgnoreCase(Context.getProperty("swagger.enable"));
        if (swaggerEnable) {
            builder.append("import io.swagger.annotations.ApiModel;");
            OutputUtils.newLine(builder);
            builder.append("import io.swagger.annotations.ApiModelProperty;");
            OutputUtils.newLine(builder, 2);
        }

        if (Context.isDynamicPlusEnable()) {
            builder.append("import cn.kunter.dynamic.annotations.DynamicMapper;");
            OutputUtils.newLine(builder);
            builder.append("import cn.kunter.dynamic.annotations.TableId;");
            OutputUtils.newLine(builder);
            builder.append("import cn.kunter.dynamic.annotations.TableColumn;");
            OutputUtils.newLine(builder, 2);
        }

        // 类注释
        builder.append("/**");
        OutputUtils.newLine(builder);
        builder.append(" * ").append(tableName).append(" 表的实体类");
        OutputUtils.newLine(builder);
        builder.append(" * @author kunter-generator");
        OutputUtils.newLine(builder);
        builder.append(" * @version 1.0 ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append(" */");
        OutputUtils.newLine(builder);

        // Lombok 注解
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

        if (swaggerEnable) {
            builder.append("@ApiModel(value = \"").append(StringUtils.isBlank(table.getRemarks()) ? tableName : table.getRemarks()).append("\")");
            OutputUtils.newLine(builder);
        }

        if (Context.isDynamicPlusEnable()) {
            builder.append("@DynamicMapper(tableName = \"").append(tableName).append("\")");
            OutputUtils.newLine(builder);
        }

        // 类定义
        builder.append("public class ").append(eoName).append(" extends BaseEo {");
        OutputUtils.newLine(builder, 2);

        // 字段定义
        for (Column column : table.getColumns()) {
            OutputUtils.javaIndent(builder, 1);
            String columnRemark = StringUtils.isBlank(column.getRemarks()) ? column.getColumnName() : column.getRemarks();
            builder.append("/** ").append(columnRemark).append(" */");
            OutputUtils.newLine(builder);
            
            if (swaggerEnable) {
                OutputUtils.javaIndent(builder, 1);
                builder.append("@ApiModelProperty(value = \"").append(columnRemark).append("\")");
                OutputUtils.newLine(builder);
            }
            
            if (Context.isDynamicPlusEnable()) {
                boolean isPrimaryKey = false;
                for (Column pk : table.getPrimaryKeys()) {
                    if (pk.getColumnName().equals(column.getColumnName())) {
                        isPrimaryKey = true;
                        break;
                    }
                }
                if (isPrimaryKey) {
                    OutputUtils.javaIndent(builder, 1);
                    builder.append("@TableId");
                    OutputUtils.newLine(builder);
                }
                if (!column.getColumnName().equals(column.getJavaName())) {
                    OutputUtils.javaIndent(builder, 1);
                    builder.append("@TableColumn(\"").append(column.getColumnName()).append("\")");
                    OutputUtils.newLine(builder);
                }
            }
            
            OutputUtils.javaIndent(builder, 1);
            
            // 简单的类型映射（项目中应有 JavaTypeResolver，但这里为示范简化处理，直接用 JavaName）
            String javaType = column.getJavaType();
            if (StringUtils.isBlank(javaType)) {
                javaType = "String"; // 默认
            } else if (javaType.contains(".")) {
                javaType = javaType.substring(javaType.lastIndexOf(".") + 1);
            }
            
            builder.append("private ").append(javaType).append(" ").append(column.getJavaName()).append(";");
            OutputUtils.newLine(builder, 2);
        }

        builder.append("}");
        OutputUtils.newLine(builder);

        // 输出文件
        // 获取 target 输出路径，如果没有则默认 user.dir
        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + entityPackages.replaceAll("\\.", "/") + "/" + eoName + ".java";
        FileUtils.writeFile(filePath, builder.toString());
    }

}
