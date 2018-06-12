/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.kunter.common.generator.util;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建代码生成器所需要的对象
 * @author yangziran
 * @version 1.0 2014年9月11日
 */
public class ObjectFactory {

    private static List<ClassLoader> externalClassLoaders;
    private static List<ClassLoader> resourceClassLoaders;

    static {
        externalClassLoaders = new ArrayList<ClassLoader>();
        resourceClassLoaders = new ArrayList<ClassLoader>();
    }

    /**
     * 构造方法
     * 工具类构造方法私有，不允许构造
     */
    private ObjectFactory() {
        super();
    }

    /**
     * 添加资源文件的类加载器到集合
     * @param classLoader 资源文件类加载器
     * @author yangziran
     */
    public static synchronized void addResourceClassLoader(ClassLoader classLoader) {
        ObjectFactory.resourceClassLoaders.add(classLoader);
    }

    /**
     * 添加类文件的类加载器到集合
     * 例如：JDBC驱动
     * @param classLoader 类文件类加载器
     * @author yangziran
     */
    public static synchronized void addExternalClassLoader(ClassLoader classLoader) {
        ObjectFactory.externalClassLoaders.add(classLoader);
    }

    /**
     * 通过类名加载外部类
     * 适用于JDBC驱动加载
     * @param type 类全称
     * @return Class<?> 类对象
     * @throws ClassNotFoundException
     * @author yangziran
     */
    public static Class<?> externalClassForName(String type) throws ClassNotFoundException {

        Class<?> clazz;

        for (ClassLoader classLoader : externalClassLoaders) {
            try {
                clazz = Class.forName(type, true, classLoader);
                return clazz;
            } catch (Exception e) {
            }
        }

        return internalClassForName(type);
    }

    /**
     * 通过类名创建外部对象
     * @param type 类全称
     * @return Object 创建对象
     * @author yangziran
     */
    public static Object createExternalObject(String type) {
        Object answer;

        try {
            Class<?> clazz = externalClassForName(type);
            answer = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(
                    MessageFormat.format("Cannot instantiate object of type {0}", new Object[] { type }), e);
        }

        return answer;
    }

    /**
     * 通过类名加载内部类
     * @param type 类全称
     * @return Class<?> 类对象
     * @throws ClassNotFoundException
     * @author yangziran
     */
    public static Class<?> internalClassForName(String type) throws ClassNotFoundException {
        Class<?> clazz = null;

        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            clazz = Class.forName(type, true, cl);
        } catch (Exception e) {
        }

        if (clazz == null) {
            clazz = Class.forName(type, true, ObjectFactory.class.getClassLoader());
        }

        return clazz;
    }

    /**
     * 通过类名创建内部对象
     * @param type 类全称
     * @return Object 创建对象
     * @author yangziran
     */
    public static Object createInternalObject(String type) {
        Object answer;

        try {
            Class<?> clazz = internalClassForName(type);

            answer = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(
                    MessageFormat.format("Cannot instantiate object of type {0}", new Object[] { type }), e);
        }

        return answer;
    }

    /**
     * 获取资源路径
     * @param resource 资源
     * @return URL 资源路径
     * @author yangziran
     */
    public static URL getResource(String resource) {
        URL url;

        for (ClassLoader classLoader : resourceClassLoaders) {
            url = classLoader.getResource(resource);
            if (url != null) {
                return url;
            }
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        url = cl.getResource(resource);

        if (url == null) {
            url = ObjectFactory.class.getClassLoader().getResource(resource);
        }

        return url;
    }

    // public static JavaTypeResolver createJavaTypeResolver(Context context, List<String> warnings) {
    // JavaTypeResolverConfiguration config = context.getJavaTypeResolverConfiguration();
    // String type;
    //
    // if (config != null && config.getConfigurationType() != null) {
    // type = config.getConfigurationType();
    // if ("DEFAULT".equalsIgnoreCase(type)) {
    // type = JavaTypeResolverDefaultImpl.class.getName();
    // }
    // }
    // else {
    // type = JavaTypeResolverDefaultImpl.class.getName();
    // }
    //
    // JavaTypeResolver answer = (JavaTypeResolver) createInternalObject(type);
    // answer.setWarnings(warnings);
    //
    // if (config != null) {
    // answer.addConfigurationProperties(config.getProperties());
    // }
    //
    // answer.setContext(context);
    //
    // return answer;
    // }
    //
    // public static Plugin createPlugin(Context context, PluginConfiguration pluginConfiguration) {
    // Plugin plugin = (Plugin) createInternalObject(pluginConfiguration.getConfigurationType());
    // plugin.setContext(context);
    // plugin.setProperties(pluginConfiguration.getProperties());
    // return plugin;
    // }
    //
    // public static CommentGenerator createCommentGenerator(Context context) {
    //
    // CommentGeneratorConfiguration config = context.getCommentGeneratorConfiguration();
    // CommentGenerator answer;
    //
    // String type;
    // if (config == null || config.getConfigurationType() == null) {
    // type = DefaultCommentGenerator.class.getName();
    // }
    // else {
    // type = config.getConfigurationType();
    // }
    //
    // answer = (CommentGenerator) createInternalObject(type);
    //
    // if (config != null) {
    // answer.addConfigurationProperties(config.getProperties());
    // }
    //
    // return answer;
    // }
    //
    // public static JavaFormatter createJavaFormatter(Context context) {
    // String type = context.getProperty(PropertyRegistry.CONTEXT_JAVA_FORMATTER);
    // if (!StringUtility.stringHasValue(type)) {
    // type = DefaultJavaFormatter.class.getName();
    // }
    //
    // JavaFormatter answer = (JavaFormatter) createInternalObject(type);
    //
    // answer.setContext(context);
    //
    // return answer;
    // }
    //
    // public static XmlFormatter createXmlFormatter(Context context) {
    // String type = context.getProperty(PropertyRegistry.CONTEXT_XML_FORMATTER);
    // if (!StringUtility.stringHasValue(type)) {
    // type = DefaultXmlFormatter.class.getName();
    // }
    //
    // XmlFormatter answer = (XmlFormatter) createInternalObject(type);
    //
    // answer.setContext(context);
    //
    // return answer;
    // }
    //
    // public static IntrospectedTable createIntrospectedTable(
    // TableConfiguration tableConfiguration,
    // FullyQualifiedTable table,
    // Context context) {
    //
    // IntrospectedTable answer = createIntrospectedTableForValidation(context);
    // answer.setFullyQualifiedTable(table);
    // answer.setTableConfiguration(tableConfiguration);
    //
    // return answer;
    // }
    //
    // /**
    // * This method creates an introspected table implementation that is
    // * only usable for validation (i.e. for a context to determine
    // * if the target is ibatis2 or mybatis3).
    // * @param context
    // * @return
    // */
    // public static IntrospectedTable createIntrospectedTableForValidation(Context context) {
    // String type = context.getTargetRuntime();
    // if (!stringHasValue(type)) {
    // type = IntrospectedTableMyBatis3Impl.class.getName();
    // }
    // else if ("Ibatis2Java2".equalsIgnoreCase(type)) {
    // type = IntrospectedTableIbatis2Java2Impl.class.getName();
    // }
    // else if ("Ibatis2Java5".equalsIgnoreCase(type)) {
    // type = IntrospectedTableIbatis2Java5Impl.class.getName();
    // }
    // else if ("Ibatis3".equalsIgnoreCase(type)) {
    // type = IntrospectedTableMyBatis3Impl.class.getName();
    // }
    // else if ("MyBatis3".equalsIgnoreCase(type)) {
    // type = IntrospectedTableMyBatis3Impl.class.getName();
    // }
    // else if ("MyBatis3Simple".equalsIgnoreCase(type)) {
    // type = IntrospectedTableMyBatis3SimpleImpl.class.getName();
    // }
    //
    // IntrospectedTable answer = (IntrospectedTable) createInternalObject(type);
    // answer.setContext(context);
    //
    // return answer;
    // }
    //
    // public static IntrospectedColumn createIntrospectedColumn(Context context) {
    // String type = context.getIntrospectedColumnImpl();
    // if (!stringHasValue(type)) {
    // type = IntrospectedColumn.class.getName();
    // }
    //
    // IntrospectedColumn answer = (IntrospectedColumn) createInternalObject(type);
    // answer.setContext(context);
    //
    // return answer;
    // }
}