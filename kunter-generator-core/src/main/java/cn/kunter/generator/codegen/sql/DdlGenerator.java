package cn.kunter.generator.codegen.sql;

import cn.kunter.generator.codegen.Generator;
import cn.kunter.generator.entity.Column;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.CodeGenerationException;
import cn.kunter.generator.util.FileUtils;
import cn.kunter.generator.util.OutputUtils;
import cn.kunter.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

/**
 * DDL生成器
 * 生成建表语句
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class DdlGenerator implements Generator {

    @Override
    public void maker(List<Table> tables) throws CodeGenerationException {
        log.info("开始生成建表语句, table size: {}", tables.size());
        
        StringBuilder builder = new StringBuilder();
        builder.append("-- ----------------------------");
        OutputUtils.newLine(builder);
        builder.append("-- Kunter Generator DDL Script");
        OutputUtils.newLine(builder);
        builder.append("-- Generated at: ").append(LocalDate.now());
        OutputUtils.newLine(builder);
        builder.append("-- ----------------------------");
        OutputUtils.newLine(builder, 2);

        for (Table table : tables) {
            builder.append("DROP TABLE IF EXISTS `").append(table.getTableName()).append("`;");
            OutputUtils.newLine(builder);
            builder.append("CREATE TABLE `").append(table.getTableName()).append("` (");
            OutputUtils.newLine(builder);
            
            boolean first = true;
            for (Column column : table.getColumns()) {
                if (!first) {
                    builder.append(",");
                    OutputUtils.newLine(builder);
                }
                first = false;
                
                builder.append("  `").append(column.getColumnName()).append("` ");
                
                String type = StringUtils.isNotBlank(column.getJdbcType()) ? column.getJdbcType().toUpperCase() : "VARCHAR";
                builder.append(type);
                if (StringUtils.isNotBlank(column.getLength())) {
                    builder.append("(").append(column.getLength()).append(")");
                }
                
                if (column.getNotNull() != null && column.getNotNull()) {
                    builder.append(" NOT NULL");
                }
                
                if (StringUtils.isNotBlank(column.getRemarks())) {
                    builder.append(" COMMENT '").append(column.getRemarks()).append("'");
                }
            }
            
            if (table.getPrimaryKeys() != null && !table.getPrimaryKeys().isEmpty()) {
                builder.append(",");
                OutputUtils.newLine(builder);
                builder.append("  PRIMARY KEY (");
                boolean firstPk = true;
                for (Column pk : table.getPrimaryKeys()) {
                    if (!firstPk) builder.append(", ");
                    builder.append("`").append(pk.getColumnName()).append("`");
                    firstPk = false;
                }
                builder.append(")");
            }
            
            OutputUtils.newLine(builder);
            builder.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");
            if (StringUtils.isNotBlank(table.getRemarks())) {
                builder.append(" COMMENT='").append(table.getRemarks()).append("'");
            }
            builder.append(";");
            OutputUtils.newLine(builder, 2);
        }

        String targetPath = System.getProperty("user.dir") + "/target/generated-sources";
        String filePath = targetPath + "/schema.sql";
        FileUtils.writeFile(filePath, builder.toString());
        log.info("建表语句生成完毕: {}", filePath);
    }

}
