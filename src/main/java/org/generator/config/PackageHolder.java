/**
 * 
 */
package org.generator.config;

import org.generator.util.StringUtility;

/**
 * 获取各类型文件包
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class PackageHolder {

    private final static String PACKAGES = PropertyHolder.getConfigProperty("package");
    private final static String MODEL = PropertyHolder.getConfigProperty("model");

    /**
     * 获取到实体类包名
     * @return
     * @author yangziran
     */
    public static String getEntityPackage() {

        StringBuilder builder = new StringBuilder();
        if (StringUtility.isNotEmpty(PACKAGES)) {
            builder.append(PACKAGES);
        }
        if (StringUtility.isNotEmpty(PACKAGES) && StringUtility.isNotEmpty(MODEL)) {
            builder.append(".").append(MODEL).append(".");
        }
        builder.append("eo");

        return builder.toString();
    }

    /**
     * 获取到自动生成DAO包名
     * @return
     * @author yangziran
     */
    public static String getBaseDaoPackage() {

        StringBuilder builder = new StringBuilder();
        if (StringUtility.isNotEmpty(PACKAGES)) {
            builder.append(PACKAGES);
        }
        if (StringUtility.isNotEmpty(PACKAGES) && StringUtility.isNotEmpty(MODEL)) {
            builder.append(".").append(MODEL).append(".");
        }
        builder.append("dao.base");

        return builder.toString();
    }

    /**
     * 获取到扩展DAO包名
     * @return
     * @author yangziran
     */
    public static String getDaoPackage() {

        StringBuilder builder = new StringBuilder();
        if (StringUtility.isNotEmpty(PACKAGES)) {
            builder.append(PACKAGES);
        }
        if (StringUtility.isNotEmpty(PACKAGES) && StringUtility.isNotEmpty(MODEL)) {
            builder.append(".").append(MODEL).append(".");
        }
        builder.append("dao");

        return builder.toString();
    }

    /**
     * 获取到SQLMapper包名
     * @return
     * @author yangziran
     */
    public static String getBaseXmlPackage() {

        StringBuilder builder = new StringBuilder();
        if (StringUtility.isNotEmpty(PACKAGES)) {
            builder.append(PACKAGES);
        }
        if (StringUtility.isNotEmpty(PACKAGES) && StringUtility.isNotEmpty(MODEL)) {
            builder.append(".").append(MODEL).append(".");
        }
        builder.append("xml.base");

        return builder.toString();
    }

    /**
     * 获取到SQLMapper包名
     * @return
     * @author yangziran
     */
    public static String getXmlPackage() {

        StringBuilder builder = new StringBuilder();
        if (StringUtility.isNotEmpty(PACKAGES)) {
            builder.append(PACKAGES);
        }
        if (StringUtility.isNotEmpty(PACKAGES) && StringUtility.isNotEmpty(MODEL)) {
            builder.append(".").append(MODEL).append(".");
        }
        builder.append("xml");

        return builder.toString();
    }
}