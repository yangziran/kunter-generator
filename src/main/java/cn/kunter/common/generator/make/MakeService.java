/**
 * 
 */
package cn.kunter.common.generator.make;

import java.util.List;
import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.type.JavaVisibility;
import cn.kunter.common.generator.util.DateUtil;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.JavaBeansUtil;
import cn.kunter.common.generator.util.OutputUtilities;

/**
 * 扩展Service生成
 * @author yangziran
 * @version 1.0 2016年4月21日
 */
public class MakeService {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        MakeService.makeService(table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    /**
     * 扩展Service生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makeService(Table table) throws Exception {

        String tableName = table.getTableName();
        String javaName = table.getJavaName();

        String servicePackages = PackageHolder.getServicePackage(tableName);

        StringBuilder builder = new StringBuilder();
        builder.append(JavaBeansUtil.getPackages(servicePackages));

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * ");
        builder.append(tableName);
        builder.append(" 表的业务处理类");
        OutputUtilities.newLine(builder);
        builder.append(" * 自行追加的业务方法");
        OutputUtilities.newLine(builder);
        builder.append(" * @author TODO 请在此处填写你的名字");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 " + DateUtil.getSysDate());
        OutputUtilities.newLine(builder);
        builder.append(" */");
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, true,
                false, null, null, javaName + "Service", table.getRemarks()));

        builder.append(JavaBeansUtil.getJavaBeansEnd());
        OutputUtilities.newLine(builder);

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + servicePackages.replaceAll("\\.", "/") + "/"
                + javaName + "Service.java", builder.toString());
    }
}
