/**
 * 
 */
package org.generator.make;

import java.util.List;

import org.generator.config.PackageHolder;
import org.generator.config.PropertyHolder;
import org.generator.entity.Table;
import org.generator.type.JavaVisibility;
import org.generator.util.FileUtil;
import org.generator.util.JavaBeansUtil;
import org.generator.util.OutputUtilities;

/**
 * 扩展DAO生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeDao {

    private final static String PACKAGES = PackageHolder.getDaoPackage();
    private final static String BASEDAO_PACKAGES = PackageHolder.getBaseDaoPackage();

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        MakeDao.makerDao(table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    /**
     * 扩展DAO生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makerDao(Table table) throws Exception {

        StringBuilder builder = new StringBuilder();
        builder.append(JavaBeansUtil.getPackages(PACKAGES));

        builder.append(JavaBeansUtil.getImports(BASEDAO_PACKAGES + ".Base" + table.getJavaName() + "Dao", false, true));

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * 类名称：");
        builder.append(table.getTableName());
        builder.append("表的DAO接口类");
        builder.append(table.getJavaName() + "Dao");
        OutputUtilities.newLine(builder);
        builder.append(" * 内容摘要：自行追加的数据库操作方法");
        OutputUtilities.newLine(builder);
        builder.append(" * @author TODO 请在此处填写你的名字");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 2015年1月1日");
        OutputUtilities.newLine(builder);
        builder.append(" */");
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, true,
                false, "Base" + table.getJavaName() + "Dao", null, table.getJavaName() + "Dao", table.getRemarks()));

        builder.append(JavaBeansUtil.getJavaBeansEnd());

        FileUtil.writeFile(
                PropertyHolder.getConfigProperty("target") + PACKAGES.replaceAll("\\.", "/") + "/"
                        + table.getJavaName() + "Dao.java", builder.toString());
    }
}
