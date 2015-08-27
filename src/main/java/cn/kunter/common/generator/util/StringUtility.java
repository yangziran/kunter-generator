/**
 * 
 */
package cn.kunter.common.generator.util;

import java.util.StringTokenizer;

/**
 * String工具类
 * @author yangziran
 * @version 1.0 2014年9月11日
 */
public class StringUtility {

    /**
     * 构造方法
     * 工具类构造方法私有，不允许构造
     */
    private StringUtility() {
        super();
    }

    /**
     * 判断字符串为null或者长度为0
     * @param str
     * @return boolean
     * @author 阳自然
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    /**
     * 判断字符串不为null或者长度不为0
     * @param str
     * @return boolean
     * @author yangziran
     */
    public static boolean isNotEmpty(String str) {
        return (!(isEmpty(str)));
    }

    /**
     * 判断字符串为null或者长度为0或者为“ ”
     * @param str
     * @return boolean
     * @author yangziran
     */
    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串不为null或者长度不为0或者不为“ ”
     * @param str
     * @return boolean
     * @author yangziran
     */
    public static boolean isNotBlank(String str) {
        return (!(isBlank(str)));
    }

    /**
     * 首字母大写
     * @param str
     * @return
     * @author yangziran
     */
    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
                .toString();
    }

    /**
     * 首字母小写
     * @param str
     * @return
     * @author yangziran
     */
    public static String uncapitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1))
                .toString();
    }

    /**
     * 转义字符串为Java格式
     * @param s 待转义字符串
     * @return String 转义后
     * @author yangziran
     */
    public static String escapeStringForJava(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true);
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) {
                sb.append("\\\"");
            }
            else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    /**
     * 转义字符串为Xml格式
     * @param s 待转义字符串
     * @return String 转义后
     * @author yangziran
     */
    public static String escapeStringForXml(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true);
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) {
                sb.append("&quot;");
            }
            else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    /**
     * 是否为true
     * @param s 待判断字符串
     * @return boolean 返回是否为true的结果
     * @author yangziran
     */
    public static boolean isTrue(String s) {
        return "true".equalsIgnoreCase(s);
    }

    /**
     * 判断是否为SQL关键词(%,_)
     * @param s 待判断字符串
     * @return boolean 为空返回false，为‘%’或者‘_’返回true
     * @author yangziran
     */
    public static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }

    /**
     * 将表名称转换为类名称
     * @param tableName 表名称
     * @param op 分隔符 如果为空则只将第一个字母大写
     * @param flag 是否将分割的第一项内容全大写 默认为 false
     * @return String 类名称
     * @author 阳自然
     */
    public static String convertTableNameToClass(String tableName, String op, Boolean flag) {

        // 如果表名称为空 将当前对象返回
        if (isEmpty(tableName)) {
            return tableName;
        }

        // 如果为null 则设置默认值 false
        if (null == flag) {
            flag = false;
        }

        // 创建StringBuffer对象
        StringBuffer buffer = new StringBuffer();
        // 如果分隔符为空
        if (isEmpty(op)) {
            // 去除表开头的"T_"
            tableName = tableName.substring(2, tableName.length());
            if (flag) {
                // 将字符串所有字母大写
                buffer.append(tableName.toUpperCase());
            }
            else {
                // 将字符串首字母大写
                buffer.append(capitalize(tableName));
            }
        }
        // 如果分隔符不为空
        else {

            // 分割字符串
            String[] tableNameArray = tableName.split(op);
            // 循环分割好的字符串数组 从1开始：去除表开头的"T_"
            for (int i = 1; i < tableNameArray.length; i++) {
                if (flag) {
                    if (i == 1) {
                        // 将字符串所有字母大写
                        buffer.append(tableNameArray[i].toUpperCase());
                    }
                    else {
                        // 将字符串首字母大写
                        buffer.append(capitalize(tableNameArray[i]));
                    }
                }
                else {
                    // 将字符串首字母大写
                    buffer.append(capitalize(tableNameArray[i]));
                }
            }
        }

        return buffer.toString();
    }

    /**
     * 将表名称转换为参数称
     * @param tableName 表名称
     * @param op 分隔符 如果为空则只将第一个字母小写
     * @param flag 是否将分割的第一项内容全小写 默认为 false
     * @return String 类名称
     * @author 阳自然
     */
    public static String convertTableNameToParameter(String tableName, String op, Boolean flag) {

        // 如果表名称为空 将当前对象返回
        if (isEmpty(tableName)) {
            return tableName;
        }

        // 如果为null 则设置默认值 false
        if (null == flag) {
            flag = false;
        }

        // 创建StringBuffer对象
        StringBuffer buffer = new StringBuffer();
        // 如果分隔符为空
        if (isEmpty(op)) {
            if (flag) {
                // 将字符串所有字母小写
                buffer.append(tableName.toLowerCase());
            }
            else {
                // 将字符串首字母大写
                buffer.append(capitalize(tableName));
            }
        }
        // 如果分隔符不为空
        else {

            // 分割字符串
            String[] tableNameArray = tableName.split(op);
            // 循环分割好的字符串数组
            for (int i = 0; i < tableNameArray.length; i++) {
                if (flag) {
                    if (i == 0) {
                        // 将字符串所有字母小写
                        buffer.append(tableNameArray[i].toLowerCase());
                    }
                    else {
                        // 将字符串首字母大写
                        buffer.append(capitalize(tableNameArray[i]));
                    }
                }
                else {
                    // 将字符串首字母小写
                    buffer.append(uncapitalize(tableNameArray[i]));
                }
            }
        }

        return buffer.toString();
    }

    /**
     * 将字段名称转为参数名称
     * @param fieldName 字段名称
     * @param op 分隔符
     * @return String 类名称
     * @author 阳自然
     */
    public static String convertFieldToParameter(String fieldName, String op) {

        // 如果表名称为空 将当前对象返回
        if (isEmpty(fieldName)) {
            return fieldName;
        }

        // 创建StringBuffer对象
        StringBuffer buffer = new StringBuffer();
        // 如果分隔符为空
        if (isNotEmpty(op)) {
            // 分割字符串
            String[] tableNameArray = fieldName.split(op);
            // 循环分割好的字符串数组
            for (int i = 0; i < tableNameArray.length; i++) {
                if (i == 0) {
                    // 将字符串所有字母大写
                    buffer.append(tableNameArray[i]);
                }
                else {
                    // 将字符串首字母大写
                    buffer.append(capitalize(tableNameArray[i]));
                }
            }
        }

        return buffer.toString();
    }
}