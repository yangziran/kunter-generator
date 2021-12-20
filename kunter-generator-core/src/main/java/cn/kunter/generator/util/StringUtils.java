package cn.kunter.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 字符串工具类
 * @author yangziran
 * @version 1.0 2021/7/20
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 工具类，私有构造
     */
    private StringUtils() {
        super();
    }

    /**
     * 将表名称转换为类名称
     * @param tableName 表名称
     * @param op 分隔符, 如果为空则只将第一个字母大写
     * @param flag 是否将分割的第一项内容全大写, 默认为: false
     * @return String 类名称
     */
    public static String convertTableNameToClass(String tableName, String op, Boolean flag) {

        // 表名称为空, 返回null
        if (isBlank(tableName)) {
            log.warn("convertTableNameToClass 表名称为空");
            return null;
        }

        // flag为null, 设置默认值: false
        if (ObjectUtils.isEmpty(flag)) {
            flag = false;
        }

        // 创建StringBuffer对象
        var buffer = new StringBuffer();
        // 分隔符为空
        if (isBlank(op)) {
            // flag == true, 将字符串所有字母大写, 反之将字符串首字母大写
            buffer.append(flag ? tableName.toUpperCase() : capitalize(tableName));
        }
        // 分隔符不为空
        else {
            // 分割字符串
            var tableNameArray = tableName.split(op);
            // 遍历字符串数组
            for (var i = 0; i < tableNameArray.length; i++) {
                if (flag && i == 0) {
                    // 将字符串所有字母大写
                    buffer.append(tableNameArray[i].toUpperCase());
                } else {
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
     * @param op 分隔符, 如果为空则只将第一个字母小写
     * @param flag 是否将分割的第一项内容全小写, 默认为: false
     * @return String 类名称
     */
    public static String convertTableNameToParameter(String tableName, String op, Boolean flag) {

        // 表名称为空, 返回null
        if (isBlank(tableName)) {
            log.warn("convertTableNameToParameter 表名称为空");
            return null;
        }

        // flag为null, 设置默认值: false
        if (ObjectUtils.isEmpty(flag)) {
            flag = false;
        }

        // 创建StringBuffer对象
        var buffer = new StringBuffer();
        // 分隔符为空
        if (isEmpty(op)) {
            // flag == true, 将字符串所有字母小写, 反之将字符串首字母小写
            buffer.append(flag ? tableName.toLowerCase() : uncapitalize(tableName));
        }
        // 分隔符不为空
        else {
            // 分割字符串
            var tableNameArray = tableName.split(op);
            // 遍历字符串数组
            for (var i = 0; i < tableNameArray.length; i++) {
                if (flag) {
                    if (i == 0) {
                        // 将字符串所有字母小写
                        buffer.append(tableNameArray[i].toLowerCase());
                    } else {
                        // 将字符串首字母大写
                        buffer.append(capitalize(tableNameArray[i]));
                    }
                } else {
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
     * @return String 参数名称
     */
    public static String convertFieldToParameter(String fieldName, String op) {

        // 字段名称为空, 返回null
        if (isEmpty(fieldName)) {
            log.warn("convertFieldToParameter 字段名称为空");
            return null;
        }

        // 创建StringBuffer对象
        var buffer = new StringBuffer();
        // 如果分隔符不为空
        if (isNotEmpty(op)) {
            // 分割字符串
            var fieldNameArray = fieldName.split(op);
            // 遍历分割好的字符串数组
            for (var i = 0; i < fieldNameArray.length; i++) {
                if (i == 0) {
                    // 将字符串所有字母小写
                    buffer.append(fieldNameArray[i].toLowerCase());
                } else {
                    // 将字符串首字母大写
                    buffer.append(capitalize(fieldNameArray[i]));
                }
            }
        } else {
            buffer.append(fieldName.toLowerCase());
        }

        return buffer.toString();
    }

}
