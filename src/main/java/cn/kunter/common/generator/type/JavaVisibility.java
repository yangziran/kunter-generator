/*
 *
 */
package cn.kunter.common.generator.type;

/**
 * Java类的枚举
 * @author yangziran
 * @version 1.0 2014年10月21日
 */
public enum JavaVisibility {

    PUBLIC("public "), PRIVATE("private "), PROTECTED("protected "), DEFAULT("");

    private final String value;

    JavaVisibility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
