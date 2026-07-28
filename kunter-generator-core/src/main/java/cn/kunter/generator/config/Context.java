package cn.kunter.generator.config;

import lombok.extern.slf4j.Slf4j;

import cn.kunter.generator.exception.ConfigurationException;

import java.io.IOException;
import java.util.Properties;

/**
 * 处理上下文
 * 全局配置开关持有者
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class Context {

    private static final Properties properties = new Properties();

    static {
        var classLoader = Context.class.getClassLoader();
        try (var inputStream = classLoader.getResourceAsStream("generatorConfig.properties")) {
            if (inputStream != null) {
                try (var reader = new java.io.InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8)) {
                    properties.load(reader);
                }
            } else {
                log.warn("未找到 generatorConfig.properties");
            }
        } catch (IOException e) {
            throw new ConfigurationException("配置加载错误", e);
        }
    }

    private Context() {
    }

    /**
     * 支持外部主动加载配置文件 (比如在 Maven 插件中)
     */
    public static void loadFromFile(java.io.File file) {
        if (file == null || !file.exists()) {
            log.warn("指定加载的配置文件不存在: {}", file != null ? file.getAbsolutePath() : "null");
            return;
        }
        try (var inputStream = new java.io.FileInputStream(file);
             var reader = new java.io.InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8)) {
            properties.load(reader);
            log.info("成功加载外部配置文件: {}", file.getAbsolutePath());
        } catch (IOException e) {
            throw new ConfigurationException("加载外部配置文件发生异常: " + file.getAbsolutePath(), e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static Properties getProperties() {
        return properties;
    }

    /**
     * 是否开启 dynamic-sql-plus 支持模式
     */
    public static boolean isDynamicPlusEnable() {
        return "true".equalsIgnoreCase(getProperty("dynamic.plus.enable", "false"));
    }

}
