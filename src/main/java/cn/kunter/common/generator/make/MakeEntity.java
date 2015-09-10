/**
 * 
 */
package cn.kunter.common.generator.make;

import java.util.ArrayList;
import java.util.List;

import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Column;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.type.JavaVisibility;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.JavaBeansUtil;
import cn.kunter.common.generator.util.OutputUtilities;

/**
 * 实体类生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeEntity {

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

        String entityPackages = PackageHolder.getEntityPackage(table.getTableName());

        StringBuilder builder = new StringBuilder();
        // 包结构
        builder.append(JavaBeansUtil.getPackages(entityPackages));
        // 导包
        builder.append(JavaBeansUtil.getImports("java.io.Serializable", false, true));

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
        builder.append("@SuppressWarnings(\"serial\")");

        // 实体实现序列化
        List<String> superInterface = new ArrayList<String>();
        superInterface.add("Serializable");
        // 类开始
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                true, null, superInterface, table.getJavaName(), table.getRemarks()));

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
        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + entityPackages.replaceAll("\\.", "/") + "/"
                + table.getJavaName() + ".java", builder.toString());
    }
}
