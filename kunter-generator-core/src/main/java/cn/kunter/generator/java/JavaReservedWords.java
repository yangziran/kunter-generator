package cn.kunter.generator.java;

import cn.kunter.generator.util.StringUtils;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Java保留字
 * @author yangziran
 * @version 1.0 2021/7/21
 */
public class JavaReservedWords {

    private static final Set<String> reservedWords;

    static {
        String[] words = {
                "abstract",
                "assert",
                "boolean",
                "break",
                "byte",
                "case",
                "catch",
                "char",
                "class",
                "const",
                "continue",
                "default",
                "do",
                "double",
                "else",
                "enum",
                "extends",
                "final",
                "finally",
                "float",
                "for",
                "goto",
                "if",
                "implements",
                "import",
                "instanceof",
                "int",
                "interface",
                "long",
                "native",
                "new",
                "package",
                "private",
                "protected",
                "public",
                "return",
                "short",
                "static",
                "strictfp",
                "super",
                "switch",
                "synchronized",
                "this",
                "throw",
                "throws",
                "transient",
                "try",
                "void",
                "volatile",
                "while"
        };

        reservedWords = Sets.newHashSet(words);
    }

    /**
     * 工具类，私有构造
     */
    private JavaReservedWords() {
    }

    /**
     * 判断是否为保留字
     * @param word
     * @return
     */
    public static boolean containsWord(String word) {
        var rc = false;

        if (StringUtils.isNotBlank(word)) {
            rc = reservedWords.contains(word);
        }
        return rc;
    }

}
