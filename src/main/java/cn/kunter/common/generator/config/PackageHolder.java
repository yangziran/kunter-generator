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

    private final static String BASE_PACKAGES = PropertyHolder.getConfigProperty("basePackage");
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

    /**
     * 获取到分页对象类包名
     * @return
     * @author yangziran
     */
    public static String getPageEntityPackage() {

        // 默认使用kunter-base包中的Page对象
        if (StringUtility.isNotEmpty(BASE_PACKAGES)) {
            return new StringBuilder(BASE_PACKAGES).append(".eo").toString();
        }

        return getPackage("common").append("eo").toString();
    }

    /**
     * 获取到CommonBaseDAO包名
     * @return
     * @author yangziran
     */
    public static String getCommonBaseDaoPackage() {

        // 默认使用kunter-base包中的Dao对象
        if (StringUtility.isNotEmpty(BASE_PACKAGES)) {
            return new StringBuilder(BASE_PACKAGES).append(".dao").toString();
        }

        return getPackage("common").append("dao").toString();
    }

    /**
     * 获取到BaseService包名
     * @return
     * @author yangziran
     */
    public static String getBaseServicePackage() {

        // 默认使用kunter-base包中的Service对象
        if (StringUtility.isNotEmpty(BASE_PACKAGES)) {
            return new StringBuilder(BASE_PACKAGES).append(".service").toString();
        }

        return getPackage("common").append("service").toString();
    }

    /**
     * 获取到BaseServiceImpl包名
     * @return
     * @author yangziran
     */
    public static String getBaseServiceImplPackage() {

        // 默认使用kunter-base包中的ServiceImpl对象
        if (StringUtility.isNotEmpty(BASE_PACKAGES)) {
            return new StringBuilder(BASE_PACKAGES).append(".service.impl").toString();
        }

        return getPackage("common").append("service.impl").toString();
    }

    public static void main(String[] args) {
        System.out.println(getPageEntityPackage());
        System.out.println(getCommonBaseDaoPackage());
        System.out.println(getBaseServicePackage());
        System.out.println(getBaseServiceImplPackage());
    }

    /**
     * 获取到自动生成Service包名
     * @param tableName 表名称，model为true的时候通过“_”截取表名称前前一节为包名
     * @return
     * @author yangziran
     */
    public static String getServicePackage(String tableName) {

        return getPackage(tableName).append("service").toString();
    }

    /**
     * 获取到扩展ServiceImpl包名
     * @param tableName 表名称，model为true的时候通过“_”截取表名称前前一节为包名
     * @return
     * @author yangziran
     */
    public static String getServiceImplPackage(String tableName) {

        return getPackage(tableName).append("service.impl").toString();
    }
}