package cn.kunter.generator.java;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Date;
import java.util.Objects;

/**
 * Java类型转换
 * @author yangziran
 * @version 1.0 2021/8/13
 */
public class JavaTypeResolver {

    protected static final Table<Integer, String, String> typeMap = HashBasedTable.create();

    static {
        typeMap.put(Types.ARRAY, "ARRAY", "Array");
        typeMap.put(Types.BIGINT, "BIGINT", Long.class.getName());
        typeMap.put(Types.BINARY, "BINARY", "byte[]");
        typeMap.put(Types.BIT, "BIT", Boolean.class.getName());
        typeMap.put(Types.BLOB, "BLOB", "byte[]");
        typeMap.put(Types.BOOLEAN, "BOOLEAN", Boolean.class.getName());
        typeMap.put(Types.CHAR, "CHAR", String.class.getName());
        typeMap.put(Types.CLOB, "CLOB", String.class.getName());
        typeMap.put(Types.DATALINK, "DATALINK", Object.class.getName());
        typeMap.put(Types.DATE, "DATE", Date.class.getName());
        typeMap.put(Types.DECIMAL, "DECIMAL", BigDecimal.class.getName());
        typeMap.put(Types.DISTINCT, "DISTINCT", Object.class.getName());
        typeMap.put(Types.DOUBLE, "DOUBLE", Double.class.getName());
        typeMap.put(Types.FLOAT, "FLOAT", Float.class.getName());
        typeMap.put(Types.INTEGER, "INTEGER", Integer.class.getName());
        typeMap.put(Types.JAVA_OBJECT, "JAVA_OBJECT", Object.class.getName());
        typeMap.put(Types.LONGNVARCHAR, "LONGNVARCHAR", String.class.getName());
        typeMap.put(Types.LONGVARBINARY, "LONGVARBINARY", "byte[]");
        typeMap.put(Types.LONGVARCHAR, "LONGVARCHAR", String.class.getName());
        typeMap.put(Types.NCHAR, "NCHAR", String.class.getName());
        typeMap.put(Types.NCLOB, "NCLOB", String.class.getName());
        typeMap.put(Types.NVARCHAR, "NVARCHAR", String.class.getName());
        typeMap.put(Types.NULL, "NULL", Object.class.getName());
        typeMap.put(Types.NUMERIC, "NUMERIC", BigDecimal.class.getName());
        typeMap.put(Types.OTHER, "OTHER", Object.class.getName());
        typeMap.put(Types.REAL, "REAL", Float.class.getName());
        typeMap.put(Types.REF, "REF", Object.class.getName());
        typeMap.put(Types.SMALLINT, "SMALLINT", Integer.class.getName());
        typeMap.put(Types.STRUCT, "STRUCT", Object.class.getName());
        typeMap.put(Types.TIME, "TIME", Date.class.getName());
        typeMap.put(Types.TIMESTAMP, "TIMESTAMP", Date.class.getName());
        typeMap.put(99, "DATETIME", Date.class.getName());
        typeMap.put(Types.TINYINT, "TINYINT", Integer.class.getName());
        typeMap.put(Types.VARBINARY, "VARBINARY", "byte[]");
        typeMap.put(Types.VARCHAR, "VARCHAR", String.class.getName());
        // Java 8 新增类型
        typeMap.put(Types.TIME_WITH_TIMEZONE, "TIME_WITH_TIMEZONE", OffsetTime.class.getName());
        typeMap.put(Types.TIMESTAMP_WITH_TIMEZONE, "TIMESTAMP_WITH_TIMEZONE", OffsetDateTime.class.getName());
    }


    /**
     * 工具类，私有构造
     */
    private JavaTypeResolver() {
    }

    /**
     * 获取JDBC类型
     * @param type
     * @return
     */
    public static String getJdbcType(Integer type) {
        var answer = typeMap.row(type);
        if (Objects.isNull(answer) || answer.isEmpty()) {
            return "OTHER";
        }

        var key = answer.keySet().stream().findFirst();
        return key.get();
    }

    /**
     * 获取JDBC类型
     * @param type
     * @return
     */
    public static Integer getJdbcType(String type) {
        var answer = typeMap.column(type);
        if (Objects.isNull(answer) || answer.isEmpty()) {
            return Types.OTHER;
        }

        var key = answer.keySet().stream().findFirst();
        return key.get();
    }

    /**
     * 获取Java类型
     * @param type
     * @return
     */
    public static String getJavaType(Integer type) {
        var answer = typeMap.row(type);
        if (Objects.isNull(answer) || answer.isEmpty()) {
            return Object.class.getName();
        }

        var value = answer.entrySet().stream().findFirst();
        return value.get().getValue();
    }

    /**
     * 获取Java类型
     * @param type
     * @return
     */
    public static String getJavaType(String type) {
        var answer = typeMap.column(type);
        if (Objects.isNull(answer) || answer.isEmpty()) {
            return Object.class.getName();
        }

        var value = answer.entrySet().stream().findFirst();
        return value.get().getValue();
    }

}
