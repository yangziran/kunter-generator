package cn.kunter.generator.util;

import cn.kunter.generator.java.FullyQualifiedJavaType;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 输出工具类
 * @author yangziran
 * @version 1.0 2021/7/21
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OutputUtils {

    /** 换行符 */
    private static final String LINESEPARATOR;

    static {
        var ls = System.getProperty("line.separator");
        if (StringUtils.isBlank(ls)) {
            ls = "\n";
        }
        LINESEPARATOR = ls;
    }

    /**
     * Java缩进
     * 每一级缩进为四个空格
     * @param sb 需要附加到的StringBuilder
     * @param indentLevel 需要缩进的级别
     */
    public static void javaIndent(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("    ");
        }
    }

    /**
     * Kotlin缩进
     * 每一级缩进为四个空格
     * @param sb 需要附加到的StringBuilder
     * @param indentLevel 需要缩进的级别
     */
    public static void kotlinIndent(StringBuilder sb, int indentLevel) {
        javaIndent(sb, indentLevel);
    }

    /**
     * XML缩进
     * 每一级缩进为两个空格
     * @param sb 需要附加到的StringBuilder
     * @param indentLevel 需要缩进的级别
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
        sb.append(LINESEPARATOR);
    }

    /**
     * 新行
     * @param sb
     * @param indentLevel
     */
    public static void newLine(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append(LINESEPARATOR);
        }
    }

    public static Set<String> calculateImports(Set<FullyQualifiedJavaType> importedTypes) {
        StringBuilder sb = new StringBuilder();
        Set<String> importStrings = Sets.newTreeSet();
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
