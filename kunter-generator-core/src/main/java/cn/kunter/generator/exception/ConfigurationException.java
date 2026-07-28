package cn.kunter.generator.exception;

/**
 * 配置异常
 * @author yangziran
 * @version 1.0 2026/07/28
 */
public class ConfigurationException extends GeneratorException {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
