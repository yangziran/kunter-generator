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

    private static final String BASE_PACKAGES = PropertyHolder.getConfigProperty("basePackage");
    private static final String PACKAGES = PropertyHolder.getConfigProperty("package");
    private static final boolean MODEL = PropertyHolder.getBooleanVal("model");

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
        } else if (StringUtility.isNotEmpty(PACKAGES) && !MODEL) {
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
     * 获取到基础实体类包名
     * @return
     * @author yangziran
     */
    public static String getBaseEntityPackage() {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_PACKAGES);
        return builder.append(".eo").toString();
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
     * 获取到BaseDAO包名
     * @return
     * @author yangziran
     */
    public static String getBaseDaoPackage() {
        return "com.baomidou.mybatisplus.core.mapper.BaseMapper";
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
