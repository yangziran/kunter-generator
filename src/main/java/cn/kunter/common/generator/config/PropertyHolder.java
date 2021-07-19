/**
 *
 */
package cn.kunter.common.generator.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 配置文件处理
 * @author yangziran
 * @version 1.0 2014年9月11日
 */
public abstract class PropertyHolder {

    /** 配置文件名称 */
    private static final String JDBC = "jdbc.properties";
    private static final String CONFIG = "config.properties";
    /** 获取到类加载器 */
    private static final ClassLoader CONTEXT_CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    /**
     * 根据文件名称加载配置文件
     * @param name 文件名称
     * @return Properties 配置文件对象
     */
    private static Properties getProperties(String name) {
        // 创建属性对象
        Properties properties = new Properties();
        try (InputStream inputStream = CONTEXT_CLASS_LOADER.getResourceAsStream(name)) {
            // 加载文件流到属性对象中
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getJDBCProperty(String name) {
        return getProperties(JDBC).getProperty(name);
    }

    public static String getConfigProperty(String name) {
        return getProperties(CONFIG).getProperty(name);
    }

    public static Boolean getBooleanVal(String key) {
        return Boolean.valueOf(getProperties(CONFIG).getProperty(key));
    }

}
