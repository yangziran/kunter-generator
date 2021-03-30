/**
 * 
 */
package cn.kunter.common.generator.make;

import java.util.ArrayList;
import java.util.List;
import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.type.JavaVisibility;
import cn.kunter.common.generator.util.DateUtil;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.JavaBeansUtil;
import cn.kunter.common.generator.util.OutputUtilities;
import cn.kunter.common.generator.util.StringUtility;

/**
 * 扩展ServiceImpl生成
 * @author yangziran
 * @version 1.0 2016年4月21日
 */
public class MakeServiceImpl {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        MakeServiceImpl.makeService(table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    /**
     * 扩展ServiceImpl生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makeService(Table table) throws Exception {

        String tableName = table.getTableName();
        String javaName = table.getJavaName();

        String serviceImplPackages = PackageHolder.getServiceImplPackage(tableName);
        String daoPackages = PackageHolder.getDaoPackage(tableName);
        String servicePackages = PackageHolder.getServicePackage(tableName);

        StringBuilder builder = new StringBuilder();
        builder.append(JavaBeansUtil.getPackages(serviceImplPackages));

        builder.append(JavaBeansUtil.getImports(daoPackages + "." + javaName + "Dao", false, true));
        builder.append(JavaBeansUtil.getImports(servicePackages + "." + javaName + "Service", false, false));
        builder.append(JavaBeansUtil.getImports("org.springframework.stereotype.Service", false, false));

        builder.append(JavaBeansUtil.getImports("javax.annotation.Resource", false, true));

        List<String> superInterface = new ArrayList<>();
        superInterface.add(javaName + "Service");

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
        OutputUtilities.newLine(builder);
        builder.append("@Service");
        OutputUtilities.newLine(builder);
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                true, null, superInterface, javaName + "ServiceImpl", table.getRemarks()));

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("@Resource");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaVisibility.PRIVATE.getValue() + " " + javaName + "Dao "
                + StringUtility.uncapitalize(javaName) + "Dao;");
        OutputUtilities.newLine(builder);

        builder.append(JavaBeansUtil.getJavaBeansEnd());
        OutputUtilities.newLine(builder);

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + serviceImplPackages.replaceAll("\\.", "/") + "/"
                + javaName + "ServiceImpl.java", builder.toString());
    }
}
