/**
 * 
 */
package cn.kunter.common.generator.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC类型转Java类型
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class JdbcTypeNameTranslator {

    private static Map<Integer, String> typeToName;
    private static Map<String, Integer> nameToType;
    private static Map<Integer, String> typeToJava;
    private static Map<String, String> nameToJava;

    static {
        typeToName = new HashMap<Integer, String>();
        typeToName.put(Types.ARRAY, "ARRAY");
        typeToName.put(Types.BIGINT, "BIGINT");
        typeToName.put(Types.BINARY, "BINARY");
        typeToName.put(Types.BIT, "BIT");
        typeToName.put(Types.BLOB, "BLOB");
        typeToName.put(Types.BOOLEAN, "BOOLEAN");
        typeToName.put(Types.CHAR, "CHAR");
        typeToName.put(Types.CLOB, "CLOB");
        typeToName.put(Types.DATALINK, "DATALINK");
        typeToName.put(Types.DATE, "DATE");
        typeToName.put(Types.DECIMAL, "DECIMAL");
        typeToName.put(Types.DISTINCT, "DISTINCT");
        typeToName.put(Types.DOUBLE, "DOUBLE");
        typeToName.put(Types.FLOAT, "FLOAT");
        typeToName.put(Types.INTEGER, "INTEGER");
        typeToName.put(Types.INTEGER, "INT");
        typeToName.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
        typeToName.put(Types.LONGVARBINARY, "LONGVARBINARY");
        typeToName.put(Types.LONGVARCHAR, "LONGVARCHAR");
        typeToName.put(Types.NCHAR, "NCHAR");
        typeToName.put(Types.NCLOB, "NCLOB");
        typeToName.put(Types.NVARCHAR, "NVARCHAR");
        typeToName.put(Types.LONGNVARCHAR, "LONGNVARCHAR");
        typeToName.put(Types.NULL, "NULL");
        typeToName.put(Types.NUMERIC, "NUMERIC");
        typeToName.put(Types.OTHER, "OTHER");
        typeToName.put(Types.REAL, "REAL");
        typeToName.put(Types.REF, "REF");
        typeToName.put(Types.SMALLINT, "SMALLINT");
        typeToName.put(Types.STRUCT, "STRUCT");
        typeToName.put(Types.TIME, "TIME");
        typeToName.put(Types.TIMESTAMP, "TIMESTAMP");
        typeToName.put(99, "DATETIME");
        typeToName.put(Types.TINYINT, "TINYINT");
        typeToName.put(Types.VARBINARY, "VARBINARY");
        typeToName.put(Types.VARCHAR, "VARCHAR");

        nameToType = new HashMap<String, Integer>();
        nameToType.put("ARRAY", Types.ARRAY);
        nameToType.put("BIGINT", Types.BIGINT);
        nameToType.put("BINARY", Types.BINARY);
        nameToType.put("BIT", Types.BIT);
        nameToType.put("BLOB", Types.BLOB);
        nameToType.put("BOOLEAN", Types.BOOLEAN);
        nameToType.put("CHAR", Types.CHAR);
        nameToType.put("CLOB", Types.CLOB);
        nameToType.put("DATALINK", Types.DATALINK);
        nameToType.put("DATE", Types.DATE);
        nameToType.put("DECIMAL", Types.DECIMAL);
        nameToType.put("DISTINCT", Types.DISTINCT);
        nameToType.put("DOUBLE", Types.DOUBLE);
        nameToType.put("FLOAT", Types.FLOAT);
        nameToType.put("INTEGER", Types.INTEGER);
        nameToType.put("INT", Types.INTEGER);
        nameToType.put("JAVA_OBJECT", Types.JAVA_OBJECT);
        nameToType.put("LONGVARBINARY", Types.LONGVARBINARY);
        nameToType.put("LONGVARCHAR", Types.LONGVARCHAR);
        nameToType.put("NCHAR", Types.NCHAR);
        nameToType.put("NCLOB", Types.NCLOB);
        nameToType.put("NVARCHAR", Types.NVARCHAR);
        nameToType.put("LONGNVARCHAR", Types.LONGNVARCHAR);
        nameToType.put("NULL", Types.NULL);
        nameToType.put("NUMERIC", Types.NUMERIC);
        nameToType.put("OTHER", Types.OTHER);
        nameToType.put("REAL", Types.REAL);
        nameToType.put("REF", Types.REF);
        nameToType.put("SMALLINT", Types.SMALLINT);
        nameToType.put("STRUCT", Types.STRUCT);
        nameToType.put("TIME", Types.TIME);
        nameToType.put("TIMESTAMP", Types.TIMESTAMP);
        nameToType.put("DATETIME", 99);
        nameToType.put("TINYINT", Types.TINYINT);
        nameToType.put("VARBINARY", Types.VARBINARY);
        nameToType.put("VARCHAR", Types.VARCHAR);

        typeToJava = new HashMap<Integer, String>();
        typeToJava.put(Types.ARRAY, "Array");
        typeToJava.put(Types.BIGINT, "Long");
        typeToJava.put(Types.BINARY, "byte[]");
        typeToJava.put(Types.BIT, "Boolean");
        typeToJava.put(Types.BLOB, "Blob");
        typeToJava.put(Types.BOOLEAN, "Boolean");
        typeToJava.put(Types.CHAR, "String");
        typeToJava.put(Types.CLOB, "Clob");
        typeToJava.put(Types.DATALINK, "DATALINK");
        typeToJava.put(Types.DATE, "java.util.Date");
        typeToJava.put(Types.DECIMAL, "java.math.BigDecimal");
        typeToJava.put(Types.DISTINCT, "DISTINCT");
        typeToJava.put(Types.DOUBLE, "Double");
        typeToJava.put(Types.FLOAT, "Float");
        typeToJava.put(Types.INTEGER, "Integer");
        typeToJava.put(Types.JAVA_OBJECT, "Object");
        typeToJava.put(Types.LONGVARBINARY, "byte[]");
        typeToJava.put(Types.LONGVARCHAR, "String");
        typeToJava.put(Types.NCHAR, "String");
        typeToJava.put(Types.NCLOB, "NClob");
        typeToJava.put(Types.NVARCHAR, "String");
        typeToJava.put(Types.LONGNVARCHAR, "String");
        typeToJava.put(Types.NULL, "NULL");
        typeToJava.put(Types.NUMERIC, "java.math.BigDecimal");
        typeToJava.put(Types.OTHER, "Object");
        typeToJava.put(Types.REAL, "Double");
        typeToJava.put(Types.REF, "REF");
        // typeToJava.put(Types.SMALLINT, "Short");
        typeToJava.put(Types.SMALLINT, "Integer");
        typeToJava.put(Types.STRUCT, "STRUCT");
        typeToJava.put(Types.TIME, "Time");
        typeToJava.put(Types.TIMESTAMP, "java.util.Date");
        typeToJava.put(99, "java.util.Date");
        typeToJava.put(Types.TINYINT, "Byte");
        typeToJava.put(Types.VARBINARY, "byte[]");
        typeToJava.put(Types.VARCHAR, "String");

        nameToJava = new HashMap<String, String>();
        nameToJava.put("ARRAY", "Array");
        nameToJava.put("BIGINT", "Long");
        nameToJava.put("BINARY", "byte[]");
        nameToJava.put("BIT", "Boolean");
        nameToJava.put("BLOB", "Blob");
        nameToJava.put("BOOLEAN", "Boolean");
        nameToJava.put("CHAR", "String");
        nameToJava.put("CLOB", "Clob");
        nameToJava.put("DATALINK", "DATALINK");
        nameToJava.put("DATE", "java.util.Date");
        nameToJava.put("DECIMAL", "java.math.BigDecimal");
        nameToJava.put("DISTINCT", "DISTINCT");
        nameToJava.put("DOUBLE", "Double");
        nameToJava.put("FLOAT", "Float");
        nameToJava.put("INTEGER", "Integer");
        nameToJava.put("INT", "Integer");
        nameToJava.put("JAVA_OBJECT", "Object");
        nameToJava.put("LONGVARBINARY", "byte[]");
        nameToJava.put("LONGVARCHAR", "String");
        nameToJava.put("NCHAR", "String");
        nameToJava.put("NCLOB", "NClob");
        nameToJava.put("NVARCHAR", "String");
        nameToJava.put("LONGNVARCHAR", "String");
        nameToJava.put("NULL", "NULL");
        nameToJava.put("NUMERIC", "java.math.BigDecimal");
        nameToJava.put("OTHER", "Object");
        nameToJava.put("REAL", "Double");
        nameToJava.put("REF", "REF");
        // nameToJava.put("SMALLINT", "Short");
        nameToJava.put("SMALLINT", "Integer");
        nameToJava.put("STRUCT", "STRUCT");
        nameToJava.put("TIME", "Time");
        nameToJava.put("TIMESTAMP", "java.util.Date");
        nameToJava.put("DATETIME", "java.util.Date");
        nameToJava.put("TINYINT", "Byte");
        nameToJava.put("VARBINARY", "byte[]");
        nameToJava.put("VARCHAR", "String");
    }

    private JdbcTypeNameTranslator() {
        super();
    }

    /**
     * Translates from a java.sql.Types values to the proper iBATIS string
     * representation of the type.
     * @param jdbcType
     *            a value from java.sql.Types
     * @return the iBATIS String representation of a JDBC type
     */
    public static String getJdbcTypeName(int jdbcType) {
        String answer = typeToName.get(jdbcType);
        if (answer == null) {
            answer = "OTHER";
        }

        return answer;
    }

    /**
     * Translates from the string representation of the type to the
     * java.sql.Types value.
     * @param jdbcTypeName
     *            the iBATIS String representation of a JDBC type
     * @return a value from java.sql.Types
     */
    public static int getJdbcType(String jdbcTypeName) {
        Integer answer = nameToType.get(jdbcTypeName);
        if (answer == null) {
            answer = Types.OTHER;
        }

        return answer;
    }

    public static String getJavaType(int jdbcType) {
        String answer = typeToJava.get(jdbcType);
        if (answer == null) {
            answer = "Object";
        }

        return answer;
    }

    public static String getJavaType(String jdbcTypeName) {
        String answer = nameToJava.get(jdbcTypeName);
        if (answer == null) {
            answer = "Object";
        }

        return answer;
    }

    public static String getBasicType(String type) {
        if (type.equals("Boolean"))
            type = "boolean";
        if (type.equals("Byte") || type.equals("java.lang.Byte"))
            type = "byte";
        else if (type.equals("Short") || type.equals("java.lang.Short"))
            type = "short";
        else if (type.equals("Integer") || type.equals("java.lang.Integer"))
            type = "int";
        else if (type.equals("Long") || type.equals("java.lang.Long"))
            type = "long";
        else if (type.equals("Float") || type.equals("java.lang.Float"))
            type = "float";
        else if (type.equals("Double") || type.equals("java.lang.Double"))
            type = "double";

        return type;
    }
}
