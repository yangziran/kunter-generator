package cn.kunter.generator.exception;

/**
 * 数据源异常
 * @author yangziran
 * @version 1.0 2026/07/28
 */
public class DataSourceException extends GeneratorException {

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(Throwable cause) {
        super(cause);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
