package cn.kunter.generator.config;

import cn.kunter.generator.util.StringUtils;

import java.util.StringJoiner;

/**
 * 文件包处理
 * @author yangziran
 * @version 1.0 2021/12/20
 */
public class PackageHolder {

    /** 默认包名, 外部未配置时使用 */
    private static final String DEFAULT_PACKAGES = "cn.kunter";
    /** 默认以'_'分割表名, 并将前缀作为模块名 */
    public static final boolean DEFAULT_MODEL = true;

    /**
     * 获取包名
     * @param tableName 表名称
     * @return StringJoiner StringJoiner对象, 方便构建包名
     */
    public static StringJoiner getPackages(String tableName) {
        var joiner = new StringJoiner(".");
        if (StringUtils.isNotBlank(DEFAULT_PACKAGES)) {
            joiner.add(DEFAULT_PACKAGES);
        }
        if (DEFAULT_MODEL) {
            joiner.add(tableName.split("_")[0]);
        }
        return joiner;
    }

    /**
     * 获取实体包名
     * @param tableName 表名称
     * @return String 包名
     */
    public static String getEntityPackage(String tableName) {
        return getPackages(tableName).add("eo").toString();
    }

    /**
     * 获取DTO包名
     * @param tableName 表名称
     * @return String 包名
     */
    public static String getDtoPackage(String tableName) {
        return getPackages(tableName).add("dto").toString();
    }

    /**
     * 获取VO包名
     * @param tableName 表名称
     * @return String 包名
     */
    public static String getVoPackage(String tableName) {
        return getPackages(tableName).add("vo").toString();
    }

    /**
     * 获取DAO包名
     * @param tableName 表名称
     * @return String 包名
     */
    public static String getDaoPackage(String tableName) {
        return getPackages(tableName).add("dao").toString();
    }

    /**
     * 获取Service包名
     * @param tableName 表名称
     * @return String 包名
     */
    public static String getServicePackage(String tableName) {
        return getPackages(tableName).add("service").toString();
    }

    /**
     * 获取Service实现包名
     * @param tableName 表名称
     * @return String 包名
     */
    public static String getServiceImplPackage(String tableName) {
        return getPackages(tableName).add("service").add("impl").toString();
    }

}
