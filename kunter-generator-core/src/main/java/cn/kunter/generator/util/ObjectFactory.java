package cn.kunter.generator.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

/**
 * 对象工厂
 * @author yangziran
 * @version 1.0 2021/7/21
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectFactory {

    private static final List<ClassLoader> externalClassLoaders;

    static {
        externalClassLoaders = Lists.newArrayList();
    }

    /**
     * 清理类加载器
     */
    public static void reset() {
        externalClassLoaders.clear();
    }

    /**
     * 添加自定义类加载器到集合
     * @param classLoader
     */
    public static synchronized void addExternalClassLoader(ClassLoader classLoader) {
        ObjectFactory.externalClassLoaders.add(classLoader);
    }

    /**
     * 获取从上下文或者客户端提供的类加载器加载的类
     * 用户JDBC驱动类的加载
     * @param type
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> externalClassForName(String type) throws ClassNotFoundException {

        for (var classLoader : externalClassLoaders) {
            try {
                return Class.forName(type, true, classLoader);
            } catch (Exception e) {
                log.warn("type: {} {}", type, e.getMessage());
            }
        }

        return internalClassForName(type);
    }

    public static Class<?> internalClassForName(String type) throws ClassNotFoundException {
        Class<?> clazz = null;

        try {
            var classLoader = Thread.currentThread().getContextClassLoader();
            clazz = Class.forName(type, true, classLoader);
        } catch (Exception e) {
            log.warn("type: {} {}", type, e.getMessage());
        }

        if (ObjectUtils.isEmpty(clazz)) {
            clazz = Class.forName(type, true, ObjectFactory.class.getClassLoader());
        }

        return clazz;
    }

}
