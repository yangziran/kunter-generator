package cn.kunter.generator.codegen.java;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.config.Context;
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
 * MyBatis Dynamic SQL Support 生成器
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class DynamicSqlSupportGenerator implements Generator {

    @Override
    public void maker(Table table) throws CodeGenerationException {
        String tableName = table.getTableName();
        String javaName = table.getJavaName();
        String supportName = javaName + "DynamicSqlSupport";
        String innerTableName = javaName;

        String daoPackages = PackageHolder.getDaoPackage(tableName);

        StringBuilder builder = new StringBuilder();

        // 包声明
        builder.append("package ").append(daoPackages).append(";");
        OutputUtils.newLine(builder, 2);

        // 导入
        builder.append("import java.sql.JDBCType;");
        OutputUtils.newLine(builder);
        builder.append("import org.mybatis.dynamic.sql.SqlColumn;");
        OutputUtils.newLine(builder);
        builder.append("import org.mybatis.dynamic.sql.SqlTable;");
        OutputUtils.newLine(builder, 2);

        // 类注释
        builder.append("/**");
        OutputUtils.newLine(builder);
        builder.append(" * ").append(tableName).append(" 表的动态 SQL Support 类");
        OutputUtils.newLine(builder);
        builder.append(" * @author kunter-generator");
        OutputUtils.newLine(builder);
        builder.append(" * @version 1.0 ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append(" */");
        OutputUtils.newLine(builder);

        // 外部类定义
        builder.append("public final class ").append(supportName).append(" {");
        OutputUtils.newLine(builder, 2);

        // 表实例
        String tableInstanceName = StringUtils.uncapitalize(javaName);
        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 内部表结构实例 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public static final ").append(innerTableName).append(" ").append(tableInstanceName).append(" = new ").append(innerTableName).append("();");
        OutputUtils.newLine(builder, 2);

        // 外部列常量
        for (Column column : table.getColumns()) {
            OutputUtils.javaIndent(builder, 1);
            builder.append("/** ").append(StringUtils.isBlank(column.getRemarks()) ? column.getColumnName() : column.getRemarks()).append(" */");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 1);
            String javaType = getSimpleJavaType(column.getJavaType());
            builder.append("public static final SqlColumn<").append(javaType).append("> ").append(column.getJavaName()).append(" = ").append(tableInstanceName).append(".").append(column.getJavaName()).append(";");
            OutputUtils.newLine(builder);
        }
        OutputUtils.newLine(builder);

        // 内部类定义
        OutputUtils.javaIndent(builder, 1);
        builder.append("/** 动态 SQL 内部表结构 */");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 1);
        builder.append("public static final class ").append(innerTableName).append(" extends SqlTable {");
        OutputUtils.newLine(builder, 2);

        // 内部列常量
        for (Column column : table.getColumns()) {
            OutputUtils.javaIndent(builder, 2);
            builder.append("/** ").append(StringUtils.isBlank(column.getRemarks()) ? column.getColumnName() : column.getRemarks()).append(" */");
            OutputUtils.newLine(builder);
            OutputUtils.javaIndent(builder, 2);
            String javaType = getSimpleJavaType(column.getJavaType());
            String jdbcType = StringUtils.isNotBlank(column.getJdbcType()) ? column.getJdbcType().toUpperCase() : "VARCHAR";
            // 修正部分 JDBC 类型名称
            if ("INT".equals(jdbcType)) jdbcType = "INTEGER";
            if ("DATETIME".equals(jdbcType)) jdbcType = "TIMESTAMP";
            
            builder.append("public final SqlColumn<").append(javaType).append("> ").append(column.getJavaName()).append(" = column(\"").append(column.getColumnName()).append("\", JDBCType.").append(jdbcType).append(");");
            OutputUtils.newLine(builder);
        }
        OutputUtils.newLine(builder);

        // 内部类构造函数
        OutputUtils.javaIndent(builder, 2);
        builder.append("public ").append(innerTableName).append("() {");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 3);
        builder.append("super(\"").append(tableName).append("\");");
        OutputUtils.newLine(builder);
        OutputUtils.javaIndent(builder, 2);
        builder.append("}");
        OutputUtils.newLine(builder);

        OutputUtils.javaIndent(builder, 1);
        builder.append("}");
        OutputUtils.newLine(builder);

        builder.append("}");
        OutputUtils.newLine(builder);

        // 输出文件
        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/" + daoPackages.replaceAll("\\.", "/") + "/" + supportName + ".java";
        boolean override = "true".equalsIgnoreCase(Context.getProperty("file.override"));
        FileUtils.writeFile(filePath, builder.toString(), override);
    }

    private String getSimpleJavaType(String javaType) {
        if (StringUtils.isBlank(javaType)) return "String";
        if (javaType.contains(".")) {
            return javaType.substring(javaType.lastIndexOf(".") + 1);
        }
        return javaType;
    }

}
