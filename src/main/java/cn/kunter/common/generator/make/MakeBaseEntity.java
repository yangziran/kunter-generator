/**
 * 
 */
package cn.kunter.common.generator.make;

import java.util.ArrayList;
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
public class MakeBaseEntity {

    private static final String COMMON_FIELDS = PropertyHolder.getConfigProperty("commonFields");

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        MakeBaseEntity.makerEntity(tables.get(0));
    }

    /**
     * 实体类生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makerEntity(Table table) throws Exception {

        String baseEntityPackages = PackageHolder.getBaseEntityPackage();

        StringBuilder builder = new StringBuilder();
        // 包结构
        builder.append(JavaBeansUtil.getPackages(baseEntityPackages));

        // 导包
        builder.append(JavaBeansUtil.getImports("lombok.Data", false, true));
        builder.append(JavaBeansUtil.getImports("java.io.Serializable", false, true));

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * 基础实体对象");
        OutputUtilities.newLine(builder);
        builder.append(" * @author 工具生成");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 " + DateUtil.getSysDate());
        OutputUtilities.newLine(builder);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append("@Data");

        // 实体实现序列化
        List<String> superInterface = new ArrayList<String>();
        superInterface.add("Serializable");
        // 类开始
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                true, null, superInterface, "BaseEo", table.getRemarks()));

        // 字段定义
        for (Column column : table.getCols()) {

            if (StringUtility.isBlank(COMMON_FIELDS)
                    || !StringUtils.containsAnyIgnoreCase(column.getColumnName(), COMMON_FIELDS.split(","))) {
                continue;
            }

            builder.append(JavaBeansUtil.getJavaBeansField(JavaVisibility.PRIVATE.getValue(), false, false, false,
                    false, column.getJavaName(), column.getJavaType(), column.getRemarks()));
        }

        OutputUtilities.newLine(builder);

        // 类结束
        builder.append(JavaBeansUtil.getJavaBeansEnd());
        OutputUtilities.newLine(builder);

        // 输出文件
        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + baseEntityPackages.replaceAll("\\.", "/") + "/"
                + "BaseEo.java", builder.toString());
    }
}
