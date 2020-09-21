/**
 * 
 */
package cn.kunter.common.generator.make.service;

import java.util.ArrayList;
import java.util.List;

import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.make.GetTableConfig;
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

        String serviceImplPackages = PackageHolder.getServiceImplPackage(table.getTableName());
        String daoPackages = PackageHolder.getDaoPackage(table.getTableName());
        String entityPackages = PackageHolder.getEntityPackage(table.getTableName());
        String servicePackages = PackageHolder.getServicePackage(table.getTableName());
        String baseServiceImplPackages = PackageHolder.getBaseServiceImplPackage();

        StringBuilder builder = new StringBuilder();
        builder.append(JavaBeansUtil.getPackages(serviceImplPackages));

        builder.append(JavaBeansUtil.getImports("org.springframework.beans.factory.annotation.Autowired", false, true));
        builder.append(JavaBeansUtil.getImports("org.springframework.stereotype.Service", false, false));
        builder.append(
                JavaBeansUtil.getImports("org.springframework.transaction.annotation.Transactional", false, false));

        builder.append(JavaBeansUtil.getImports(daoPackages + "." + table.getJavaName() + "Dao", false, true));
        builder.append(JavaBeansUtil.getImports(entityPackages + "." + table.getJavaName(), false, false));
        builder.append(JavaBeansUtil.getImports(servicePackages + "." + table.getJavaName() + "Service", false, false));
        builder.append(JavaBeansUtil.getImports(baseServiceImplPackages + ".BaseServiceImpl", false, false));

        List<String> superInterface = new ArrayList<>();
        superInterface.add(table.getJavaName() + "Service");

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * ");
        builder.append(table.getTableName());
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
        builder.append("@Transactional");
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                true, "BaseServiceImpl<" + table.getJavaName() + "Dao, " + table.getJavaName() + ">", superInterface,
                table.getJavaName() + "ServiceImpl", table.getRemarks()));

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("@Autowired");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaVisibility.PRIVATE.getValue() + " " + table.getJavaName() + "Dao "
                + StringUtility.uncapitalize(table.getJavaName()) + "Dao;");

        List<String> bodyLines = new ArrayList<>();
        bodyLines.add("return " + StringUtility.uncapitalize(table.getJavaName()) + "Dao;");
        OutputUtilities.newLine(builder);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("@Override");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, false,
                false, table.getJavaName() + "Dao", "getDao", null, null, bodyLines, null));

        builder.append(JavaBeansUtil.getJavaBeansEnd());
        OutputUtilities.newLine(builder);

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + serviceImplPackages.replaceAll("\\.", "/") + "/"
                + table.getJavaName() + "ServiceImpl.java", builder.toString());
    }
}
