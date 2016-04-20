/**
 * 
 */
package cn.kunter.common.generator.config;

import cn.kunter.common.generator.util.StringUtility;

/**
 * 获取各类型文件包
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class PackageHolder {

    private final static String PACKAGES = PropertyHolder.getConfigProperty("package");
    private final static boolean MODEL = PropertyHolder.getBooleanVal("model");

    /**
     * 获取到包名
     * @param tableName 表名称，model为true的时候通过“_”截取表名称前前一节为包名
     * @return
     * @author 阳自然
     */
    public static StringBuilder getPackage(String tableName) {
        StringBuilder builder = new StringBuilder();
        if (StringUtility.isNotEmpty(PACKAGES)) {
            builder.append(PACKAGES);
        }
        if (StringUtility.isNotEmpty(PACKAGES) && MODEL) {
            builder.append(".").append(tableName.split("_")[0]).append(".");
        }
        else if (StringUtility.isNotEmpty(PACKAGES) && !MODEL) {
            builder.append(".");
        }
        return builder;
    }

    /**
     * 获取到分页对象类包名
     * @return
     * @author yangziran
     */
    public static String getPageEntityPackage() {

        return getPackage("common").append("eo").toString();
    }

    /**
     * 获取到实体类包名
     * @param tableName 表名称，model为true的时候通过“_”截取表名称前前一节为包名
     * @return
     * @author yangziran
     */
    public static String getEntityPackage(String tableName) {

        return getPackage(tableName).append("eo").toString();
    }

    /**
     * 获取到自动生成DAO包名
     * @param tableName 表名称，model为true的时候通过“_”截取表名称前前一节为包名
     * @return
     * @author yangziran
     */
    public static String getBaseDaoPackage(String tableName) {

        return getPackage(tableName).append("dao.base").toString();
    }

    /**
     * 获取到扩展DAO包名
     * @param tableName 表名称，model为true的时候通过“_”截取表名称前前一节为包名
     * @return
     * @author yangziran
     */
    public static String getDaoPackage(String tableName) {

        return getPackage(tableName).append("dao").toString();
    }

    /**
     * 获取到SQLMapper包名
     * @param tableName 表名称，model为true的时候通过“_”截取表名称前前一节为包名
     * @return
     * @author yangziran
     */
    public static String getBaseXmlPackage(String tableName) {

        return getPackage(tableName).append("xml.base").toString();
    }

    /**
     * 获取到SQLMapper包名
     * @param tableName 表名称，model为true的时候通过“_”截取表名称前前一节为包名
     * @return
     * @author yangziran
     */
    public static String getXmlPackage(String tableName) {

        return getPackage(tableName).append("xml").toString();
    }
}