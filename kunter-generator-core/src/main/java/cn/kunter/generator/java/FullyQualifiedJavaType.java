package cn.kunter.generator.java;

import cn.kunter.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Java标准类型
 * @author yangziran
 * @version 1.0 2026/07/28
 */
@Slf4j
public class FullyQualifiedJavaType implements Comparable<FullyQualifiedJavaType> {

    private static final String JAVA_LANG = "java.lang";

    private static FullyQualifiedJavaType intInstance = null;

    private static FullyQualifiedJavaType stringInstance = null;

    private static FullyQualifiedJavaType booleanPrimitiveInstance = null;

    private static FullyQualifiedJavaType objectInstance = null;

    private static FullyQualifiedJavaType dateInstance = null;

    private static FullyQualifiedJavaType criteriaInstance = null;

    private static FullyQualifiedJavaType generatedCriteriaInstance = null;

    private final List<FullyQualifiedJavaType> typeArguments;
    /** 不包含任何泛型参数的短名称。 */
    private String baseShortName;
    /** 不包含任何泛型参数的全限定名称。 */
    private String baseQualifiedName;
    private boolean explicitlyImported;
    private String packageName;
    private boolean primitive;
    private boolean isArray;
    private PrimitiveTypeWrapper primitiveTypeWrapper;
    // 以下三个值用于处理通配符类型
    private boolean wildcardType;

    private boolean boundedWildcard;

    private boolean extendsBoundedWildcard;

    /**
     * Use this constructor to construct a generic type with the specified type parameters.
     * @param fullTypeSpecification the full type specification
     */
    public FullyQualifiedJavaType(String fullTypeSpecification) {
        super();
        typeArguments = new ArrayList<>();
        parse(fullTypeSpecification);
    }

    public static FullyQualifiedJavaType getIntInstance() {
        if (intInstance == null) {
            intInstance = new FullyQualifiedJavaType("int");
        }

        return intInstance;
    }

    public static FullyQualifiedJavaType getNewListInstance() {
        // 总是返回一个新实例，因为类型可能被参数化
        return new FullyQualifiedJavaType("java.util.List");
    }

    public static FullyQualifiedJavaType getNewHashMapInstance() {
        // 总是返回一个新实例，因为类型可能被参数化
        return new FullyQualifiedJavaType("java.util.HashMap");
    }

    public static FullyQualifiedJavaType getNewArrayListInstance() {
        // 总是返回一个新实例，因为类型可能被参数化
        return new FullyQualifiedJavaType("java.util.ArrayList");
    }

    public static FullyQualifiedJavaType getNewIteratorInstance() {
        // 总是返回一个新实例，因为类型可能被参数化
        return new FullyQualifiedJavaType("java.util.Iterator");
    }

    public static FullyQualifiedJavaType getStringInstance() {
        if (stringInstance == null) {
            stringInstance = new FullyQualifiedJavaType("java.lang.String");
        }

        return stringInstance;
    }

    public static FullyQualifiedJavaType getBooleanPrimitiveInstance() {
        if (booleanPrimitiveInstance == null) {
            booleanPrimitiveInstance = new FullyQualifiedJavaType("boolean");
        }

        return booleanPrimitiveInstance;
    }

    public static FullyQualifiedJavaType getObjectInstance() {
        if (objectInstance == null) {
            objectInstance = new FullyQualifiedJavaType("java.lang.Object");
        }

        return objectInstance;
    }

    public static FullyQualifiedJavaType getDateInstance() {
        if (dateInstance == null) {
            dateInstance = new FullyQualifiedJavaType("java.util.Date");
        }

        return dateInstance;
    }

    public static FullyQualifiedJavaType getCriteriaInstance() {
        if (criteriaInstance == null) {
            criteriaInstance = new FullyQualifiedJavaType("Criteria");
        }

        return criteriaInstance;
    }

    public static FullyQualifiedJavaType getGeneratedCriteriaInstance() {
        if (generatedCriteriaInstance == null) {
            generatedCriteriaInstance = new FullyQualifiedJavaType(
                    "GeneratedCriteria");
        }

        return generatedCriteriaInstance;
    }

    /**
     * Returns the package name of a fully qualified type.
     * <p>This method calculates the package as the part of the fully qualified name up to, but not including, the last
     * element. Therefore, it does not support fully qualified inner classes. Not totally fool proof, but correct in
     * most instances.
     * @param baseQualifiedName the base qualified name
     * @return the package
     */
    private static String getPackage(String baseQualifiedName) {
        int index = baseQualifiedName.lastIndexOf('.');
        return baseQualifiedName.substring(0, index);
    }

    public boolean isExplicitlyImported() {
        return explicitlyImported;
    }

    /**
     * Returns the fully qualified name - including any generic type parameters.
     * @return Returns the fullyQualifiedName.
     */
    public String getFullyQualifiedName() {
        StringBuilder sb = new StringBuilder();
        if (wildcardType) {
            sb.append('?');
            if (boundedWildcard) {
                if (extendsBoundedWildcard) {
                    sb.append(" extends ");
                } else {
                    sb.append(" super ");
                }

                sb.append(baseQualifiedName);
            }
        } else {
            sb.append(baseQualifiedName);
        }

        if (!typeArguments.isEmpty()) {
            boolean first = true;
            sb.append('<');
            for (FullyQualifiedJavaType fqjt : typeArguments) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(fqjt.getFullyQualifiedName());

            }
            sb.append('>');
        }

        return sb.toString();
    }

    public String getFullyQualifiedNameWithoutTypeParameters() {
        return baseQualifiedName;
    }

    /**
     * Returns a list of Strings that are the fully qualified names of this type, and any generic type argument
     * associated with this type.
     * @return the import list
     */
    public List<String> getImportList() {
        List<String> answer = new ArrayList<>();
        if (isExplicitlyImported()) {
            int index = baseShortName.indexOf('.');
            if (index == -1) {
                answer.add(calculateActualImport(baseQualifiedName));
            } else {
                // 如果指定了内部类，仅导入顶级类
                String sb = packageName + '.' + calculateActualImport(baseShortName.substring(0, index));
                answer.add(sb);
            }
        }

        for (FullyQualifiedJavaType fqjt : typeArguments) {
            answer.addAll(fqjt.getImportList());
        }

        return answer;
    }

    private String calculateActualImport(String name) {
        String answer = name;
        if (this.isArray()) {
            int index = name.indexOf('[');
            if (index != -1) {
                answer = name.substring(0, index);
            }
        }
        return answer;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getShortName() {
        StringBuilder sb = new StringBuilder();
        if (wildcardType) {
            sb.append('?');
            if (boundedWildcard) {
                if (extendsBoundedWildcard) {
                    sb.append(" extends ");
                } else {
                    sb.append(" super ");
                }

                sb.append(baseShortName);
            }
        } else {
            sb.append(baseShortName);
        }

        if (!typeArguments.isEmpty()) {
            boolean first = true;
            sb.append('<');
            for (FullyQualifiedJavaType fqjt : typeArguments) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(fqjt.getShortName());

            }
            sb.append('>');
        }

        return sb.toString();
    }

    public String getShortNameWithoutTypeArguments() {
        return baseShortName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FullyQualifiedJavaType)) {
            return false;
        }

        FullyQualifiedJavaType other = (FullyQualifiedJavaType) obj;

        return getFullyQualifiedName().equals(other.getFullyQualifiedName());
    }

    @Override
    public int hashCode() {
        return getFullyQualifiedName().hashCode();
    }

    @Override
    public String toString() {
        return getFullyQualifiedName();
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public PrimitiveTypeWrapper getPrimitiveTypeWrapper() {
        return primitiveTypeWrapper;
    }

    @Override
    public int compareTo(FullyQualifiedJavaType other) {
        return getFullyQualifiedName().compareTo(other.getFullyQualifiedName());
    }

    public void addTypeArgument(FullyQualifiedJavaType type) {
        typeArguments.add(type);
    }

    private void parse(String fullTypeSpecification) {
        var spec = fullTypeSpecification.trim();

        if (spec.startsWith("?")) {
            wildcardType = true;
            spec = spec.substring(1).trim();
            if (spec.startsWith("extends ")) {
                boundedWildcard = true;
                extendsBoundedWildcard = true;
                spec = spec.substring(8);
            } else if (spec.startsWith("super ")) {
                boundedWildcard = true;
                extendsBoundedWildcard = false;
                spec = spec.substring(6);
            } else {
                boundedWildcard = false;
            }
            parse(spec);
        } else {
            var index = fullTypeSpecification.indexOf('<');
            if (index == -1) {
                simpleParse(fullTypeSpecification);
            } else {
                simpleParse(fullTypeSpecification.substring(0, index));
                var endIndex = fullTypeSpecification.lastIndexOf('>');
                if (endIndex == -1) {
                    log.error("无效的类型规范: {}", fullTypeSpecification);
                    throw new IllegalArgumentException("无效的类型规范: " + fullTypeSpecification);
                }
                genericParse(fullTypeSpecification.substring(index, endIndex + 1));
            }

            // 这远非检测数组的完美测试，但对大多数情况来说足够了。
            // 它不会检测像 byte] 这样错误指定的数组类型，
            // 但它会检测 byte[] 和 byte[   ] 这两种有效形式
            isArray = fullTypeSpecification.endsWith("]");
        }
    }

    private void simpleParse(String typeSpecification) {
        baseQualifiedName = typeSpecification.trim();
        if (baseQualifiedName.contains(".")) {
            packageName = getPackage(baseQualifiedName);
            baseShortName = baseQualifiedName
                    .substring(packageName.length() + 1);
            var index = baseShortName.lastIndexOf('.');
            if (index != -1) {
                baseShortName = baseShortName.substring(index + 1);
            }


            explicitlyImported = !JAVA_LANG.equals(packageName);
        } else {
            baseShortName = baseQualifiedName;
            explicitlyImported = false;
            packageName = "";

            switch (baseQualifiedName) {
                case "byte":
                    primitive = true;
                    primitiveTypeWrapper = PrimitiveTypeWrapper.getByteInstance();
                    break;
                case "short":
                    primitive = true;
                    primitiveTypeWrapper = PrimitiveTypeWrapper.getShortInstance();
                    break;
                case "int":
                    primitive = true;
                    primitiveTypeWrapper = PrimitiveTypeWrapper.getIntegerInstance();
                    break;
                case "long":
                    primitive = true;
                    primitiveTypeWrapper = PrimitiveTypeWrapper.getLongInstance();
                    break;
                case "char":
                    primitive = true;
                    primitiveTypeWrapper = PrimitiveTypeWrapper.getCharacterInstance();
                    break;
                case "float":
                    primitive = true;
                    primitiveTypeWrapper = PrimitiveTypeWrapper.getFloatInstance();
                    break;
                case "double":
                    primitive = true;
                    primitiveTypeWrapper = PrimitiveTypeWrapper.getDoubleInstance();
                    break;
                case "boolean":
                    primitive = true;
                    primitiveTypeWrapper = PrimitiveTypeWrapper.getBooleanInstance();
                    break;
                default:
                    primitive = false;
                    primitiveTypeWrapper = null;
                    break;
            }
        }
    }

    private void genericParse(String genericSpecification) {
        var lastIndex = genericSpecification.lastIndexOf('>');
        if (lastIndex == -1) {
            log.error("无效的类型规范: {}", genericSpecification);
            throw new IllegalArgumentException("无效的类型规范: " + genericSpecification);
        }

        var argumentString = genericSpecification.substring(1, lastIndex);
        // need to find "," outside of a <> bounds
        var st = new StringTokenizer(argumentString, ",<>", true);
        int openCount = 0;
        var sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("<".equals(token)) {
                sb.append(token);
                openCount++;
            } else if (">".equals(token)) {
                sb.append(token);
                openCount--;
            } else if (",".equals(token)) {
                if (openCount == 0) {
                    typeArguments.add(new FullyQualifiedJavaType(sb.toString()));
                    sb.setLength(0);
                } else {
                    sb.append(token);
                }
            } else {
                sb.append(token);
            }
        }

        if (openCount != 0) {
            log.error("无效的类型规范: {}", genericSpecification);
            throw new IllegalArgumentException("无效的类型规范: " + genericSpecification);
        }

        var finalType = sb.toString();
        if (StringUtils.isNotBlank(finalType)) {
            typeArguments.add(new FullyQualifiedJavaType(finalType));
        }
    }

    public boolean isArray() {
        return isArray;
    }

    public List<FullyQualifiedJavaType> getTypeArguments() {
        return typeArguments;
    }
}
