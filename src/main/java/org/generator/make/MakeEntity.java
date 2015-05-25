/**
 * 
 */
package org.generator.make;

import java.util.List;

import org.generator.config.PackageHolder;
import org.generator.config.PropertyHolder;
import org.generator.entity.Column;
import org.generator.entity.Table;
import org.generator.type.JavaVisibility;
import org.generator.util.FileUtil;
import org.generator.util.JavaBeansUtil;
import org.generator.util.OutputUtilities;

/**
 * 实体类生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeEntity {

    private final static String PACKAGES = PackageHolder.getEntityPackage();

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {

            Thread thread = new Thread(new Runnable() {

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

        StringBuilder builder = new StringBuilder();
        // 包结构
        builder.append(JavaBeansUtil.getPackages(PACKAGES));

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * 类名称：");
        builder.append(table.getTableName());
        builder.append("表的实体类");
        builder.append(table.getJavaName());
        OutputUtilities.newLine(builder);
        builder.append(" * 内容摘要：");
        builder.append(table.getTableName());
        builder.append("表的各个元素的取得、设定方法");
        OutputUtilities.newLine(builder);
        builder.append(" * @author 工具生成");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 2015年1月1日");
        OutputUtilities.newLine(builder);
        builder.append(" */");

        // 类开始
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                true, null, null, table.getJavaName(), table.getRemarks()));

        // 字段定义
        for (Column column : table.getCols()) {
            builder.append(JavaBeansUtil.getJavaBeansField(JavaVisibility.PRIVATE.getValue(), false, false, false,
                    false, column.getJavaName(), column.getJavaType(), column.getRemarks()));
        }

        OutputUtilities.newLine(builder);
        // Get/Set
        for (Column column : table.getCols()) {

            builder.append(JavaBeansUtil.getJavaBeansGetter(JavaVisibility.PUBLIC.getValue(), column.getJavaName(),
                    column.getJavaType(), column.getRemarks()));

            builder.append(JavaBeansUtil.getJavaBeansSetter(JavaVisibility.PUBLIC.getValue(), column.getJavaName(),
                    column.getJavaType(), column.getRemarks()));
        }

        // 类结束
        builder.append(JavaBeansUtil.getJavaBeansEnd());

        // 输出文件
        FileUtil.writeFile(
                PropertyHolder.getConfigProperty("target") + PACKAGES.replaceAll("\\.", "/") + "/"
                        + table.getJavaName() + ".java", builder.toString());
    }
}
