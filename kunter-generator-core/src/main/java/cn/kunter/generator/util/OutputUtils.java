package cn.kunter.generator.util;

import cn.kunter.generator.java.FullyQualifiedJavaType;
import lombok.var;

import java.util.Set;
import java.util.TreeSet;

/**
 * 输出工具类
 * @author nature
 * @version 1.0 2021/7/21
 */
public class OutputUtils {

    private static final String lineSeparator;

    static {
        var ls = System.getProperty("line.separator");
        if (StringUtils.isBlank(ls)) {
            ls = "\n";
        }
        lineSeparator = ls;
    }

    /**
     * 工具类，私有构造
     */
    private OutputUtils() {
        super();
    }

    /**
     * Java缩进
     * 四个空格一级
     * @param sb
     * @param indentLevel
     */
    public static void javaIndent(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("    ");
        }
    }

    /**
     * XML缩进
     * 两个空格一级
     * @param sb
     * @param indentLevel
     */
    public static void xmlIndent(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  ");
        }
    }

    /**
     * 新行
     * @param sb
     */
    public static void newLine(StringBuilder sb) {
        sb.append(lineSeparator);
    }

    /**
     * 新行
     * @param sb
     * @param indentLevel
     */
    public static void newLine(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append(lineSeparator);
        }
    }

    public static Set<String> calculateImports(Set<FullyQualifiedJavaType> importedTypes) {
        StringBuilder sb = new StringBuilder();
        Set<String> importStrings = new TreeSet<String>();
        for (FullyQualifiedJavaType fqjt : importedTypes) {
            for (String importString : fqjt.getImportList()) {
                sb.setLength(0);
                sb.append("import ");
                sb.append(importString);
                sb.append(';');
                importStrings.add(sb.toString());
            }
        }

        return importStrings;
    }

}
