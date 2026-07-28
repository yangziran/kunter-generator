package cn.kunter.generator.java;

import lombok.Getter;

/**
 * Java可见性枚举
 * @author yangziran
 * @version 1.0 2021/7/21
 */
public enum JavaVisibility {

    PUBLIC("public "),
    PRIVATE("private "),
    PROTECTED("protected "),
    DEFAULT("");

    @Getter
    private final String value;

    JavaVisibility(String value) {
        this.value = value;
    }

}
