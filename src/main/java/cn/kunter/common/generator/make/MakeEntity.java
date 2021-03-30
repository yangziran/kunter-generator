/**
 * 
 */
package cn.kunter.common.generator.make;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Column;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.type.JavaVisibility;
import cn.kunter.common.generator.util.DateUtil;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.JavaBeansUtil;
import cn.kunter.common.generator.util.OutputUtilities;
import cn.kunter.common.generator.util.StringUtility;

/**
 * 实体类生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeEntity {

    private static final String COMMON_FIELDS = PropertyHolder.getConfigProperty("commonFields");

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        MakeEntity.makerEntity(table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    /**
     * 实体类生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makerEntity(Table table) throws Exception {

        String tableName = table.getTableName();
        String javaName = table.getJavaName();

        String entityPackages = PackageHolder.getEntityPackage(tableName);
        String baseEntityPackages = PackageHolder.getBaseEntityPackage();

        StringBuilder builder = new StringBuilder();
        // 包结构
        builder.append(JavaBeansUtil.getPackages(entityPackages));

        // 导包
        builder.append(JavaBeansUtil.getImports(baseEntityPackages + ".BaseEo", false, true));
        builder.append(JavaBeansUtil.getImports("com.baomidou.mybatisplus.annotation.TableId", false, false));
        builder.append(JavaBeansUtil.getImports("com.baomidou.mybatisplus.annotation.TableName", false, false));
        builder.append(JavaBeansUtil.getImports("lombok.Data", false, false));

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * ");
        builder.append(tableName);
        builder.append(" 表的实体类");
        OutputUtilities.newLine(builder);
        builder.append(" * @author 工具生成");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 " + DateUtil.getSysDate());
        OutputUtilities.newLine(builder);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append("@Data");
        OutputUtilities.newLine(builder);
        builder.append("@TableName(\"").append(tableName).append("\")");

        // 类开始
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                true, "BaseEo", null, javaName, table.getRemarks()));

        List<Column> pkList = table.getPrimaryKey();
        // 字段定义
        for (Column column : table.getCols()) {

            if (StringUtility.isNotBlank(COMMON_FIELDS)
                    && StringUtils.containsAnyIgnoreCase(column.getColumnName(), COMMON_FIELDS.split(","))) {
                continue;
            }

            if (pkList.contains(column)) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("@TableId");
            }

            builder.append(JavaBeansUtil.getJavaBeansField(JavaVisibility.PRIVATE.getValue(), false, false, false,
                    false, column.getJavaName(), column.getJavaType(), column.getRemarks()));
        }
        OutputUtilities.newLine(builder);

        // 类结束
        builder.append(JavaBeansUtil.getJavaBeansEnd());
        OutputUtilities.newLine(builder);

        // 输出文件
        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + entityPackages.replaceAll("\\.", "/") + "/"
                + javaName + ".java", builder.toString());
    }
}
