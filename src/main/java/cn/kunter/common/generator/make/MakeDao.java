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
 * 扩展DAO生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeDao {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                @Override
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

        String daoPackages = PackageHolder.getDaoPackage(table.getTableName());
        String baseDaoPackages = PackageHolder.getBaseDaoPackage(table.getTableName());

        StringBuilder builder = new StringBuilder();
        builder.append(JavaBeansUtil.getPackages(daoPackages));

        builder.append(JavaBeansUtil.getImports(baseDaoPackages + ".Base" + table.getJavaName() + "Dao", false, true));

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
        builder.append(" * @version 1.0 " + DateUtil.getSysDate());
        OutputUtilities.newLine(builder);
        builder.append(" */");
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, true, false, "Base" + table.getJavaName() + "Dao", null, table.getJavaName() + "Dao",
                table.getRemarks()));

        builder.append(JavaBeansUtil.getJavaBeansEnd());

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + daoPackages.replaceAll("\\.", "/") + "/" + table.getJavaName() + "Dao.java", builder.toString());
    }
}
