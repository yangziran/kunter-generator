package cn.kunter.generator.exception;

/**
 * 代码生成异常
 * @author yangziran
 * @version 1.0 2026/07/28
 */
public class CodeGenerationException extends GeneratorException {

    public CodeGenerationException(String message) {
        super(message);
    }

    public CodeGenerationException(Throwable cause) {
        super(cause);
    }

    public CodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

}
