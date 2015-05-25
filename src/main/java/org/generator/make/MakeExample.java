/**
 * 
 */
package org.generator.make;

import java.util.ArrayList;
import java.util.List;

import org.generator.config.PackageHolder;
import org.generator.config.PropertyHolder;
import org.generator.entity.Column;
import org.generator.entity.Field;
import org.generator.entity.Method;
import org.generator.entity.Parameter;
import org.generator.entity.Table;
import org.generator.type.FullyQualifiedJavaType;
import org.generator.type.JavaVisibility;
import org.generator.util.FileUtil;
import org.generator.util.JavaBeansUtil;
import org.generator.util.OutputUtilities;

/**
 * 实体查询条件生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeExample {

    private final static String PACKAGES = PackageHolder.getEntityPackage();

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        MakeExample.makerExample(table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    /**
     * 实体查询条件生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makerExample(Table table) throws Exception {

        StringBuilder builder = new StringBuilder();
        // 包结构
        builder.append(JavaBeansUtil.getPackages(PACKAGES));
        // 导包
        builder.append(JavaBeansUtil.getImports("java.util.ArrayList", false, true));
        builder.append(JavaBeansUtil.getImports("java.util.List", false, false));
        boolean BigDecimal = false;
        boolean Date = false;
        for (Column column : table.getCols()) {
            if (column.getJavaType().equals("java.math.BigDecimal")) {
                BigDecimal = true;
            }
            if (column.getJavaType().equals("java.util.Date")) {
                Date = true;
            }
        }

        if (BigDecimal) {
            builder.append(JavaBeansUtil.getImports("java.math.BigDecimal", false, false));
        }
        if (Date) {
            builder.append(JavaBeansUtil.getImports("java.util.Date", false, false));
        }

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * 类名称：");
        builder.append(table.getTableName());
        builder.append("表的查询条件类");
        builder.append(table.getJavaName() + "Example");
        OutputUtilities.newLine(builder);
        builder.append(" * 内容摘要：");
        builder.append(table.getTableName());
        builder.append("表的各个元素的查询条件");
        OutputUtilities.newLine(builder);
        builder.append(" * @author 工具生成");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 2015年1月1日");
        OutputUtilities.newLine(builder);
        builder.append(" */");
        // 类开始
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                true, null, null, table.getJavaName() + "Example", table.getRemarks()));

        // 字段定义
        for (Column column : table.getExample()) {
            builder.append(JavaBeansUtil.getJavaBeansField(JavaVisibility.PROTECTED.getValue(), false, false, false,
                    false, column.getJavaName(), column.getJavaType(), column.getRemarks()));
        }
        OutputUtilities.newLine(builder);

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaVisibility.PUBLIC.getValue()).append(table.getJavaName()).append("Example() {");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("oredCriteria = new ArrayList<Criteria>();");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("}");
        OutputUtilities.newLine(builder);

        for (Column column : table.getExample()) {

            builder.append(JavaBeansUtil.getJavaBeansGetter(JavaVisibility.PUBLIC.getValue(), column.getJavaName(),
                    column.getJavaType(), column.getRemarks()));

            builder.append(JavaBeansUtil.getJavaBeansSetter(JavaVisibility.PUBLIC.getValue(), column.getJavaName(),
                    column.getJavaType(), column.getRemarks()));
        }

        List<Column> parameters = new ArrayList<Column>();
        Column column = new Column();
        column.setJavaName("criteria");
        column.setJavaType("Criteria");
        parameters.add(column);
        List<String> bodyLines = new ArrayList<String>();
        bodyLines.add("oredCriteria.add(criteria);");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, false,
                false, null, "or", parameters, null, bodyLines, null));

        bodyLines = new ArrayList<String>();
        bodyLines.add("Criteria criteria = createCriteriaInternal();");
        bodyLines.add("oredCriteria.add(criteria);");
        bodyLines.add("return criteria;");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, false,
                false, "Criteria", "or", null, null, bodyLines, null));

        bodyLines = new ArrayList<String>();
        bodyLines.add("Criteria criteria = createCriteriaInternal();");
        bodyLines.add("if (oredCriteria.size() == 0) {");
        bodyLines.add("oredCriteria.add(criteria);");
        bodyLines.add("}");
        bodyLines.add("return criteria;");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, false,
                false, "Criteria", "createCriteria", null, null, bodyLines, null));

        bodyLines = new ArrayList<String>();
        bodyLines.add("Criteria criteria = new Criteria();");
        bodyLines.add("return criteria;");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PROTECTED.getValue(), false, false, false, false,
                false, false, "Criteria", "createCriteriaInternal", null, null, bodyLines, null));

        bodyLines = new ArrayList<String>();
        bodyLines.add("oredCriteria.clear();");
        bodyLines.add("orderByClause = null;");
        bodyLines.add("distinct = false;");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, false,
                false, null, "clear", null, null, bodyLines, null));

        /** ---------- GeneratedCriteria Start ---------- */
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PROTECTED.getValue(), true, true, false, false,
                true, null, null, "GeneratedCriteria", null));

        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setName("criteria");
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("java.util.List<Criterion>");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        Method method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        method.setName("GeneratedCriteria");
        method.addBodyLine("super();");
        method.addBodyLine("criteria = new ArrayList<Criterion>();");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("boolean");
        method.setReturnType(fqjt);
        method.setName("isValid");
        method.addBodyLine("return criteria.size() > 0;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("List<Criterion>");
        method.setReturnType(fqjt);
        method.setName("getAllCriteria");
        method.addBodyLine("return criteria;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("List<Criterion>");
        method.setReturnType(fqjt);
        method.setName("getCriteria");
        method.addBodyLine("return criteria;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        fqjt = new FullyQualifiedJavaType("String");
        Parameter parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        method.setName("addCriterion");
        method.addBodyLine("if (condition == null) {");
        method.addBodyLine("throw new RuntimeException(\"Value for condition cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("criteria.add(new Criterion(condition));");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "property");
        method.addParameter(parameter);
        method.setName("addCriterion");
        method.addBodyLine("if (value == null) {");
        method.addBodyLine("throw new RuntimeException(\"Value for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("criteria.add(new Criterion(condition, value));");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value1");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value2");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "property");
        method.addParameter(parameter);
        method.setName("addCriterion");
        method.addBodyLine("if (value1 == null || value2 == null) {");
        method.addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("criteria.add(new Criterion(condition, value1, value2));");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        for (Column cols : table.getCols()) {

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            method.setName("and" + cols.getJavaName() + "IsNull");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " is null\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            method.setName("and" + cols.getJavaName() + "IsNotNull");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " is not null\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(cols.getJavaType());
            parameter = new Parameter(fqjt, "value");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "EqualTo");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " =\", value, \"" + cols.getJavaName()
                    + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(cols.getJavaType());
            parameter = new Parameter(fqjt, "value");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "NotEqualTo");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " <>\", value, \"" + cols.getJavaName()
                    + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(cols.getJavaType());
            parameter = new Parameter(fqjt, "value");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "GreaterThan");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " >\", value, \"" + cols.getJavaName()
                    + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(cols.getJavaType());
            parameter = new Parameter(fqjt, "value");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "GreaterThanOrEqualTo");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " >=\", value, \"" + cols.getJavaName()
                    + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(cols.getJavaType());
            parameter = new Parameter(fqjt, "value");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "LessThan");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " <\", value, \"" + cols.getJavaName()
                    + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(cols.getJavaType());
            parameter = new Parameter(fqjt, "value");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "LessThanOrEqualTo");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " <=\", value, \"" + cols.getJavaName()
                    + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("List<" + cols.getJavaType() + ">");
            parameter = new Parameter(fqjt, "values");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "In");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " in\", values, \"" + cols.getJavaName()
                    + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("List<" + cols.getJavaType() + ">");
            parameter = new Parameter(fqjt, "values");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "NotIn");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " not in\", values, \"" + cols.getJavaName()
                    + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(cols.getJavaType());
            parameter = new Parameter(fqjt, "value");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "Like");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " like\", \"%\" + value + \"%\", \""
                    + cols.getJavaName() + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(cols.getJavaType());
            parameter = new Parameter(fqjt, "value");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "NotLike");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " not like\", \"%\" + value + \"%\", \""
                    + cols.getJavaName() + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Integer");
            parameter = new Parameter(fqjt, "value1");
            method.addParameter(parameter);
            fqjt = new FullyQualifiedJavaType("Integer");
            parameter = new Parameter(fqjt, "value2");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "Between");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " between\", value1, value2, \""
                    + cols.getJavaName() + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));

            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            fqjt = new FullyQualifiedJavaType("Criteria");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Integer");
            parameter = new Parameter(fqjt, "value1");
            method.addParameter(parameter);
            fqjt = new FullyQualifiedJavaType("Integer");
            parameter = new Parameter(fqjt, "value2");
            method.addParameter(parameter);
            method.setName("and" + cols.getJavaName() + "NotBetween");
            method.addBodyLine("addCriterion(\"" + cols.getColumnName() + " not between\", value1, value2, \""
                    + cols.getJavaName() + "\");");
            method.addBodyLine("return (Criteria) this;");
            OutputUtilities.newLine(builder, 2);
            builder.append(method.getFormattedContent(2, false));
        }

        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansEnd());
        /** ---------- GeneratedCriteria End ---------- */

        /** ---------- Criteria Start ---------- */
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, true, false, false,
                true, "GeneratedCriteria", null, "Criteria", null));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        method.setName("Criteria");
        method.addBodyLine("super();");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(2, false));

        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansEnd());
        /** ---------- Criteria End ---------- */

        /** ---------- Criterion Start ---------- */
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, true, false, false,
                true, null, null, "Criterion", null));

        field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setName("condition");
        fqjt = new FullyQualifiedJavaType("String");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setName("value");
        fqjt = new FullyQualifiedJavaType("Object");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setName("secondValue");
        fqjt = new FullyQualifiedJavaType("Object");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setName("noValue");
        fqjt = new FullyQualifiedJavaType("boolean");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setName("singleValue");
        fqjt = new FullyQualifiedJavaType("boolean");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setName("betweenValue");
        fqjt = new FullyQualifiedJavaType("boolean");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setName("listValue");
        fqjt = new FullyQualifiedJavaType("boolean");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setName("typeHandler");
        fqjt = new FullyQualifiedJavaType("String");
        field.setType(fqjt);
        OutputUtilities.newLine(builder);
        builder.append(field.getFormattedContent(2));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        method.setName("Criterion");
        method.addBodyLine("super();");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("String");
        method.setReturnType(fqjt);
        method.setName("getCondition");
        method.addBodyLine("return condition;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Object");
        method.setReturnType(fqjt);
        method.setName("getValue");
        method.addBodyLine("return value;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Object");
        method.setReturnType(fqjt);
        method.setName("getSecondValue");
        method.addBodyLine("return secondValue;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("boolean");
        method.setReturnType(fqjt);
        method.setName("isNoValue");
        method.addBodyLine("return noValue;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("boolean");
        method.setReturnType(fqjt);
        method.setName("isSingleValue");
        method.addBodyLine("return singleValue;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("boolean");
        method.setReturnType(fqjt);
        method.setName("isBetweenValue");
        method.addBodyLine("return betweenValue;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("boolean");
        method.setReturnType(fqjt);
        method.setName("isListValue");
        method.addBodyLine("return listValue;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("String");
        method.setReturnType(fqjt);
        method.setName("getTypeHandler");
        method.addBodyLine("return typeHandler;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        method.setName("Criterion");
        method.addBodyLine("super();");
        method.addBodyLine("this.condition = condition;");
        method.addBodyLine("this.typeHandler = null;");
        method.addBodyLine("this.noValue = true;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "typeHandler");
        method.addParameter(parameter);
        method.setName("Criterion");
        method.addBodyLine("super();");
        method.addBodyLine("this.condition = condition;");
        method.addBodyLine("this.value = value;");
        method.addBodyLine("this.typeHandler = typeHandler;");
        method.addBodyLine("if (value instanceof List<?>) {");
        method.addBodyLine("this.listValue = true;");
        method.addBodyLine("} else {");
        method.addBodyLine("this.singleValue = true;");
        method.addBodyLine("}");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("Criterion");
        method.addBodyLine("this(condition, value, null);");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "secondValue");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "typeHandler");
        method.addParameter(parameter);
        method.setName("Criterion");
        method.addBodyLine("super();");
        method.addBodyLine("this.condition = condition;");
        method.addBodyLine("this.value = value;");
        method.addBodyLine("this.secondValue = secondValue;");
        method.addBodyLine("this.typeHandler = typeHandler;");
        method.addBodyLine("this.betweenValue = true;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "secondValue");
        method.addParameter(parameter);
        method.setName("Criterion");
        method.addBodyLine("this(condition, value, secondValue, null);");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansEnd());
        /** ---------- Criterion End ---------- */

        builder.append(JavaBeansUtil.getJavaBeansEnd());

        // 输出文件
        FileUtil.writeFile(
                PropertyHolder.getConfigProperty("target") + PACKAGES.replaceAll("\\.", "/") + "/"
                        + table.getJavaName() + "Example.java", builder.toString());
    }
}
