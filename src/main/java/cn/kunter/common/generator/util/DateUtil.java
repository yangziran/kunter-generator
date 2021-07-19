package cn.kunter.common.generator.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理工具类
 * @author yangziran
 * @version 1.0 2016年4月20日
 */
public class DateUtil {

    /** 日期格式： yyyy年M月dd日 */
    public static final String PATTERN_YYYY_M_D_NC = "yyyy年M月d日";
    /** 格式化日期对象 */
    private static SimpleDateFormat format = new SimpleDateFormat();

    /**
     * 取得系统日期
     * @return String 日期字符串
     */
    public static String getSysDate() {

        format.applyPattern(PATTERN_YYYY_M_D_NC);
        String returnString = format.format(new Date());
        return returnString;
    }
}
