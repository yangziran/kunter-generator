package cn.kunter.common.generator.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 类名称：CollectionUtil 集合类的工具类
 * 内容摘要：集合类的常用处理
 * @author yangzr
 * @version 1.0 2012-6-16
 */
public class CollectionUtil {

    /** 排序规则 升序 */
    public static int ASC = 1;
    /** 排序规则 降序 */
    public static int DESC = 2;

    /**
     * 工具类私有构造
     */
    private CollectionUtil() {
    }

    /**
     * 通过Set 去除List中的重复数据
     * @param paramList 要去除重复的List
     * @return List 处理后的List
     */
    public static <T> List<T> removeDuplicateObj(List<T> paramList) {

        // 定义去重用的Set,并将List数据放入该Set
        Set<T> tempSet = new HashSet<T>(paramList);

        // 将Set中的集合，放到一个临时的链表中(tempList)
        Iterator<T> iterator = tempSet.iterator();
        // 定义返回处理 后的集合对象
        List<T> tempList = new ArrayList<T>();
        while (iterator.hasNext()) {
            tempList.add(iterator.next());
        }
        return tempList;
    }

    /**
     * 对指定List进行排序
     * @param objects List数据
     * @param order 排序规则
     */
    public static void sort(List<Object> objects, final int order) {

        // 实现Comparator接口实现排序
        Collections.sort(objects, new Comparator<Object>() {

            // 实现Comparator的compare方法
            @Override
            public int compare(Object o1, Object o2) {
                // 判断是否为降序
                if (DESC == order) {
                    // 按降序排序
                    return o2.hashCode() - o1.hashCode();
                }
                // 如果不是降序
                else {
                    // 按升序排序
                    return o1.hashCode() - o2.hashCode();
                }
            }
        });
    }

    /**
     * 根据指定属性进行排序
     * @param objects 待排序集合
     * @param propertyName 属性名称
     * @param order 排序方式
     */
    public static void sortByProperty(
        List<?> objects,
        final String propertyName,
        final String propertyType,
        final int order) {

        // 实现Comparator接口实现排序
        Collections.sort(objects, new Comparator<Object>() {

            // 实现Comparator的compare方法
            @Override
            public int compare(Object r1, Object r2) {
                // 取得类型
                Class<?> clazz = r1.getClass();
                double result = 0;
                try {
                    Method method = null;
                    // 获取 get方法
                    method = clazz.getMethod(
                            "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1),
                            new Class[] {});
                    // 如果是String 类型 则将方法执行的结果转换成String类型并用compareTo方法是行排序
                    if (String.class.getSimpleName().equals(propertyType)) {
                        if (DESC == order) {
                            result = (StringUtility.parseString(method.invoke(r2)))
                                    .compareTo(StringUtility.parseString(method.invoke(r1)));
                        }
                        else {
                            result = (StringUtility.parseString(method.invoke(r1)))
                                    .compareTo(StringUtility.parseString(method.invoke(r2)));
                        }
                    }
                    // 如果是Integer类型 则将方法执行的结果转换成Integer类型并排序
                    else if (Integer.class.getSimpleName().equals(propertyType)) {
                        if (DESC == order) {
                            result = ((Integer) method.invoke(r2)) - ((Integer) method.invoke(r1));
                        }
                        else {
                            result = ((Integer) method.invoke(r1)) - ((Integer) method.invoke(r2));
                        }
                    }
                    // 如果是Float类型 则将方法执行的结果转换成Float类型并排序
                    else if (Float.class.getSimpleName().equals(propertyType)) {
                        if (DESC == order) {
                            result = ((Float) method.invoke(r2)) - ((Float) method.invoke(r1));
                        }
                        else {
                            result = ((Float) method.invoke(r1)) - ((Float) method.invoke(r2));
                        }
                    }
                    // 如果是Double类型 则将方法执行的结果转换成Double类型并排序
                    else if (Double.class.getSimpleName().equals(propertyType)) {
                        if (DESC == order) {
                            result = ((Double) method.invoke(r2)) - ((Double) method.invoke(r1));
                        }
                        else {
                            result = ((Double) method.invoke(r1)) - ((Double) method.invoke(r2));
                        }
                    }
                    // 如果是Long类型 则将方法执行的结果转换成Long类型并排序
                    else if (Long.class.getSimpleName().equals(propertyType)) {
                        if (DESC == order) {
                            result = ((Long) method.invoke(r2)) - ((Long) method.invoke(r1));
                        }
                        else {
                            result = ((Long) method.invoke(r1)) - ((Long) method.invoke(r2));
                        }
                    }
                } catch (SecurityException e) {
                    LogUtil.error(CollectionUtil.class, "sortByProperty", e);
                } catch (NoSuchMethodException e) {
                    LogUtil.error(CollectionUtil.class, "sortByProperty", e);
                } catch (IllegalArgumentException e) {
                    LogUtil.error(CollectionUtil.class, "sortByProperty", e);
                } catch (IllegalAccessException e) {
                    LogUtil.error(CollectionUtil.class, "sortByProperty", e);
                } catch (InvocationTargetException e) {
                    LogUtil.error(CollectionUtil.class, "sortByProperty", e);
                }

                // 确定返回值
                if (result > 0) {
                    return 1;
                }
                else if (result < 0) {
                    return -1;
                }
                return 0;
            }
        });
    }
}