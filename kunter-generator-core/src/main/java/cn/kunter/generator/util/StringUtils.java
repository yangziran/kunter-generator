package cn.kunter.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 字符串工具类
 * @author nature
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
     * @param op 分隔符 如果为空则只将第一个字母大写
     * @param flag 是否将分割的第一项内容全大写 默认为 false
     * @return String 类名称
     */
    public static String convertToClass(String tableName, String op, Boolean flag) {

        // 如果表名称为空 将当前对象返回
        if (isBlank(tableName)) {
            return null;
        }

        // 如果为null 则设置默认值 false
        if (ObjectUtils.isEmpty(flag)) {
            flag = false;
        }

        // 创建StringBuffer对象
        StringBuffer buffer = new StringBuffer();
        // 如果分隔符为空
        if (isBlank(op)) {
            // flag == true 将字符串所有字母大写，反之将字符串首字母大写
            buffer.append(flag ? tableName.toUpperCase() : capitalize(tableName));
        }
        // 如果分隔符不为空
        else {
            // 分割字符串
            String[] tableNameArray = tableName.split(op);
            // 循环分割好的字符串数组
            for (int i = 0; i < tableNameArray.length; i++) {
                if (flag) {
                    if (i == 0) {
                        // 将字符串所有字母大写
                        buffer.append(tableNameArray[i].toUpperCase());
                    } else {
                        // 将字符串首字母大写
                        buffer.append(capitalize(tableNameArray[i]));
                    }
                } else {
                    // 将字符串首字母大写
                    buffer.append(capitalize(tableNameArray[i]));
                }
            }
        }

        return buffer.toString();
    }


}
