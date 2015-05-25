/**
 * 
 */
package org.generator.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 数据库属性设置
 * @author yangziran
 * @version 1.0 2014年9月11日
 */
public abstract class PropertyHolder {

    // 获取到类加载器
    private static ClassLoader parent = Thread.currentThread().getContextClassLoader();
    /** 属性文件名称 */
    private static final String JDBC = "jdbc.properties";
    private static final String CONFIG = "config.properties";

    /**
     * 获取到JDBC属性
     * @return
     * @author yangziran
     */
    private static Properties getJDBCProperties() {

        // 将属性文件加载到流中
        InputStream inputStream = parent.getResourceAsStream(JDBC);
        // 创建属性对象
        Properties properties = new Properties();
        try {
            // 加载文件流到属性对象中
            properties.load(inputStream);
            // 关闭文件流
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 获取到代码生成属性
     * @return
     * @author yangziran
     */
    private static Properties getConfigProperties() {

        // 将属性文件加载到流中
        InputStream inputStream = parent.getResourceAsStream(CONFIG);
        // 创建属性对象
        Properties properties = new Properties();
        try {
            // 加载文件流到属性对象中
            properties.load(inputStream);
            // 关闭文件流
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getJDBCProperty(String name) {
        return getJDBCProperties().getProperty(name);
    }

    public static String getConfigProperty(String name) {
        return getConfigProperties().getProperty(name);
    }
}