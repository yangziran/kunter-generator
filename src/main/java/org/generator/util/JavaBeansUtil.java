/*
 * 
 */
package org.generator.util;

import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import org.generator.entity.Column;

/**
 * Java类工具类
 * @author yangziran
 * @version 1.0 2014年10月21日
 */
public class JavaBeansUtil {

    /**
     * 构造方法
     * 工具类构造方法私有，不允许构造
     */
    private JavaBeansUtil() {
        super();
    }

    /**
     * JavaBeans rules:
     * eMail > geteMail() firstName > getFirstName() URL > getURL() XAxis >
     * getXAxis() a > getA() B > invalid - this method assumes that this is not
     * the case. Call getValidPropertyName first. Yaxis > invalid - this method
     * assumes that this is not the case. Call getValidPropertyName first.
     * @param property
     * @return the getter method name
     */
    public static String getGetterMethodName(String property) {

        StringBuilder sb = new StringBuilder();
        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }

        sb.insert(0, "get");

        return sb.toString();
    }

    /**
     * JavaBeans rules:
     * eMail > seteMail() firstName > setFirstName() URL > setURL() XAxis >
     * setXAxis() a > setA() B > invalid - this method assumes that this is not
     * the case. Call getValidPropertyName first. Yaxis > invalid - this method
     * assumes that this is not the case. Call getValidPropertyName first.
     * @param property
     * @return the setter method name
     */
    public static String getSetterMethodName(String property) {

        StringBuilder sb = new StringBuilder();
        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }

        sb.insert(0, "set");

        return sb.toString();
    }

    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {

        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {

            char c = inputString.charAt(i);
            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    }
                    else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    /**
     * This method ensures that the specified input string is a valid Java
     * property name. The rules are as follows:
     * 1. If the first character is lower case, then OK 2. If the first two
     * characters are upper case, then OK 3. If the first character is upper
     * case, and the second character is lower case, then the first character
     * should be made lower case
     * eMail > eMail firstName > firstName URL > URL XAxis > XAxis a > a B > b
     * Yaxis > yaxis
     * @param inputString
     * @return the valid property name
     */
    public static String getValidPropertyName(String inputString) {

        String answer;
        if (inputString == null) {
            answer = null;
        }
        else if (inputString.length() < 2) {
            answer = inputString.toLowerCase(Locale.US);
        }
        else {
            if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
                answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
            }
            else {
                answer = inputString;
            }
        }

        return answer;
    }

    /**
     * 获取到JavaBean package
     * @param packages
     * @return
     * @author yangziran
     */
    public static String getPackages(String packages) {

        StringBuilder buffer = new StringBuilder();
        buffer.append("package ").append(packages).append(";");
        OutputUtilities.newLine(buffer);
        return buffer.toString();
    }

    /**
     * 获取JavaBean 导包
     * @param imports
     * @param isStatic
     * @return
     * @author yangziran
     */
    public static String getImports(String imports, boolean isStatic, boolean isNewLine) {

        StringBuilder buffer = new StringBuilder();
        if (isNewLine) {
            OutputUtilities.newLine(buffer);
        }
        buffer.append("import ");
        if (isStatic) {
            buffer.append("static ");
        }
        buffer.append(imports).append(";");
        OutputUtilities.newLine(buffer);
        return buffer.toString();
    }

    /**
     * 类开始
     * @param visibility 修饰符
     * @param isAbstract 是否Abstract
     * @param isStatic 是否Static
     * @param isFinal 是否Final
     * @param isInterface 是否Interface
     * @param isClass 是否Class
     * @param superClass 继承
     * @param superInterface 实现
     * @param name 名称
     * @param remarks 备注
     * @return
     * @author yangziran
     */
    public static String getJavaBeansStart(
        String visibility,
        boolean isAbstract,
        boolean isStatic,
        boolean isFinal,
        boolean isInterface,
        boolean isClass,
        String superClass,
        List<String> superInterface,
        String name,
        String remarks) {

        StringBuilder buffer = new StringBuilder();
        OutputUtilities.newLine(buffer);
        buffer.append(visibility);
        if (isAbstract) {
            buffer.append("abstract ");
        }
        if (isStatic) {
            buffer.append("static ");
        }
        if (isFinal) {
            buffer.append("final ");
        }
        if (isClass && !isInterface) {
            buffer.append("class ");
        }
        if (!isClass && isInterface) {
            buffer.append("interface ");
        }
        buffer.append(name);
        if (superClass != null) {
            buffer.append(" extends ").append(superClass);
        }
        if (isClass && !isInterface) {
            if (superInterface != null && superInterface.size() > 0) {
                buffer.append(" implements ");
                boolean comma = false;
                for (String fqjt : superInterface) {
                    if (comma) {
                        buffer.append(", ");
                    }
                    else {
                        comma = true;
                    }
                    buffer.append(fqjt);
                }
            }
        }
        buffer.append(" {");
        OutputUtilities.newLine(buffer);
        return buffer.toString();
    }

    /**
     * 类结束
     * @return
     * @author yangziran
     */
    public static String getJavaBeansEnd() {

        StringBuilder buffer = new StringBuilder();
        OutputUtilities.newLine(buffer);
        buffer.append("}");
        return buffer.toString();
    }

    /**
     * 获取Java类字段
     * @param visibility 作用域
     * @param isStatic 是否为Static
     * @param isFinal 是否为Final
     * @param isTransient 是否为Transient
     * @param isVolatile 是否为Volatile
     * @param name 名称
     * @param type 类型
     * @param remarks 备注
     * @return Java类字段
     * @author yangziran
     */
    public static String getJavaBeansField(
        String visibility,
        boolean isStatic,
        boolean isFinal,
        boolean isTransient,
        boolean isVolatile,
        String name,
        String type,
        String remarks) {

        StringBuilder buffer = new StringBuilder();
        if (StringUtility.isNotEmpty(remarks)) {
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append("/** ").append(remarks).append(" */");
        }
        OutputUtilities.newLine(buffer);
        OutputUtilities.javaIndent(buffer, 1);
        buffer.append(visibility);
        if (isStatic) {
            buffer.append("static ");
        }
        if (isFinal) {
            buffer.append("final ");
        }
        if (isTransient) {
            buffer.append("transient ");
        }
        if (isVolatile) {
            buffer.append("volatile ");
        }
        buffer.append(type).append(" ").append(name).append(";");

        return buffer.toString();
    }

    /**
     * 获取JavaBean get方法
     * @param visibility
     * @param name
     * @param type
     * @param remarks
     * @return
     * @author yangziran
     */
    public static String getJavaBeansGetter(String visibility, String name, String type, String remarks) {

        StringBuilder buffer = new StringBuilder();
        if (StringUtility.isNotEmpty(remarks)) {
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append("/**");
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append(" * ").append("取得 ").append(remarks);
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append(" * ").append("@return ").append(remarks);
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append(" */");
        }
        OutputUtilities.newLine(buffer);
        OutputUtilities.javaIndent(buffer, 1);
        buffer.append(visibility).append(type).append(" ").append(getGetterMethodName(name)).append("() {");
        OutputUtilities.newLine(buffer);
        OutputUtilities.javaIndent(buffer, 2);
        buffer.append("return ").append(name).append(";");
        OutputUtilities.newLine(buffer);
        OutputUtilities.javaIndent(buffer, 1);
        buffer.append("}");
        OutputUtilities.newLine(buffer);

        return buffer.toString();
    }

    /**
     * 获取JavaBean set方法
     * @param visibility
     * @param name
     * @param type
     * @param remarks
     * @return
     * @author yangziran
     */
    public static String getJavaBeansSetter(String visibility, String name, String type, String remarks) {

        StringBuilder buffer = new StringBuilder();
        if (StringUtility.isNotEmpty(remarks)) {
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append("/**");
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append(" * ").append("设定 ").append(remarks);
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append(" * ").append("@param ").append(name).append(" ").append(remarks);
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, 1);
            buffer.append(" */");
        }
        OutputUtilities.newLine(buffer);
        OutputUtilities.javaIndent(buffer, 1);
        buffer.append(visibility).append("void ").append(JavaBeansUtil.getSetterMethodName(name)).append("(")
                .append(type).append(" ").append(name).append(") {");
        OutputUtilities.newLine(buffer);
        OutputUtilities.javaIndent(buffer, 2);
        buffer.append("this.").append(name).append(" = ").append(name).append(";");
        OutputUtilities.newLine(buffer);
        OutputUtilities.javaIndent(buffer, 1);
        buffer.append("}");
        OutputUtilities.newLine(buffer);

        return buffer.toString();
    }

    public static String getMethods(
        int indentLevel,
        String visibility,
        boolean constructor,
        boolean isInterface,
        boolean isSynchronized,
        boolean isNative,
        boolean isStatic,
        boolean isFinal,
        String returnType,
        String name,
        List<Column> parameters,
        List<Column> exceptions,
        List<String> bodyLines,
        String remarks) {

        StringBuilder buffer = new StringBuilder();
        OutputUtilities.newLine(buffer);
        OutputUtilities.javaIndent(buffer, indentLevel);
        if (!isInterface) {
            buffer.append(visibility);
            if (isStatic) {
                buffer.append("static ");
            }
            if (isFinal) {
                buffer.append("final ");
            }
            if (isSynchronized) {
                buffer.append("synchronized ");
            }
            if (isNative) {
                buffer.append("native ");
            }
            else if (bodyLines.size() == 0) {
                buffer.append("abstract ");
            }
        }

        if (!constructor) {
            if (returnType == null) {
                buffer.append("void");
            }
            else {
                buffer.append(returnType);
            }
            buffer.append(' ');
        }

        buffer.append(name).append('(');

        boolean comma = false;
        if (parameters != null && parameters.size() > 0) {
            for (Column column : parameters) {
                if (comma) {
                    buffer.append(", ");
                }
                else {
                    comma = true;
                }
                buffer.append(column.getJavaType()).append(" ").append(column.getJavaName());
            }
        }

        buffer.append(')');

        if (exceptions != null && exceptions.size() > 0) {
            buffer.append(" throws ");
            comma = false;
            for (Column column : exceptions) {
                if (comma) {
                    buffer.append(", ");
                }
                else {
                    comma = true;
                }
                buffer.append(column.getJavaType());
            }
        }

        if (bodyLines == null || bodyLines.size() == 0 || isNative) {
            buffer.append(';');
        }
        else {
            buffer.append(" {");
            indentLevel++;

            ListIterator<String> listIter = bodyLines.listIterator();
            while (listIter.hasNext()) {
                String line = listIter.next();
                if (line.startsWith("}")) {
                    indentLevel--;
                }

                OutputUtilities.newLine(buffer);
                OutputUtilities.javaIndent(buffer, indentLevel);
                buffer.append(line);

                if ((line.endsWith("{") && !line.startsWith("switch")) || line.endsWith(":")) {
                    indentLevel++;
                }

                if (line.startsWith("break")) {
                    if (listIter.hasNext()) {
                        String nextLine = listIter.next();
                        if (nextLine.startsWith("}")) {
                            indentLevel++;
                        }

                        listIter.previous();
                    }
                    indentLevel--;
                }
            }

            indentLevel--;
            OutputUtilities.newLine(buffer);
            OutputUtilities.javaIndent(buffer, indentLevel);
            buffer.append('}');
        }
        OutputUtilities.newLine(buffer);

        return buffer.toString();
    }
}