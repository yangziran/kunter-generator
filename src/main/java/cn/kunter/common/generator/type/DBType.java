/**
 *
 */
package cn.kunter.common.generator.type;

/**
 * 数据库类型枚举
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public enum DBType {

    ORACLE("Oracle"), MYSQL("MySQL"), POSTGRESQL("PostgreSQL");

    private final String value;

    DBType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
