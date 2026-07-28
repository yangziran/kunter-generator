package cn.kunter.generator.exception;

/**
 * 生成器基类异常
 * @author yangziran
 * @version 1.0 2026/07/28
 */
public class GeneratorException extends RuntimeException {

    public GeneratorException(String message) {
        super(message);
    }

    public GeneratorException(Throwable cause) {
        super(cause);
    }

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

}
