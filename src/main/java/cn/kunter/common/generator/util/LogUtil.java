/**
 *
 */
package cn.kunter.common.generator.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日志处理类
 * @author yangziran
 * @version 1.0 2015年1月2日
 */
public class LogUtil {

    /** 代表方法执行开始 */
    public static final String START_METHOD = "srart";
    /** 代表方法执行结束 */
    public static final String END_METHOD = "end";
    /** 日志对象 */
    public static Logger log = Logger.getLogger(LogUtil.class.getName());

    /**
     * 工具类私有构造
     */
    private LogUtil() {
    }

    /**
     * 输出INFO日志
     * @param clazz 对象
     * @param methodName 方法名
     * @param infomsg 消息内容
     */
    public static void info(Class<?> clazz, String methodName, String infomsg) {
        log.info(clazz.getName() + "-" + methodName + "-" + infomsg);
    }

    /**
     * 方法执行开始
     * @param clazz 执行方法类的对象
     * @param methodName 当前执行的方法名称
     */
    public static void infoStart(Class<?> clazz, String methodName) {
        log.info(clazz.getName() + "-" + methodName + "-" + LogUtil.START_METHOD);
    }

    /**
     * 方法执行结束
     * @param clazz 执行方法类的对象
     * @param methodName 当前执行的方法名称
     */
    public static void infoEnd(Class<?> clazz, String methodName) {
        log.info(clazz.getName() + "-" + methodName + "-" + LogUtil.END_METHOD);
    }

    /**
     * 输出ERROR日志
     * @param className 类名
     * @param methodName 方法名
     * @param infomsg 消息内容
     */
    public static void error(Class<?> clazz, String methodName, String errMsg) {
        log.log(Level.SEVERE, clazz.getName() + "-" + methodName + "\n" + errMsg);
    }

    /**
     * 输出错误日志
     * @param errorMsg 错误信息
     */
    public static void error(String errorMsg) {
        log.log(Level.SEVERE, errorMsg);
    }

    /**
     * 输出有Exception的ERROR日志
     * @param className 类名
     * @param methodName 方法名
     * @param infomsg 消息内容
     */
    public static void error(Class<?> clazz, String methodName, Exception e) {
        log.log(Level.SEVERE, clazz.getName() + "-" + methodName, e);
    }
}
