/**
 * 
 */
package cn.kunter.common.generator.make.service;

import java.util.ArrayList;
import java.util.List;
import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Column;
import cn.kunter.common.generator.entity.Field;
import cn.kunter.common.generator.entity.Method;
import cn.kunter.common.generator.entity.Parameter;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.type.FullyQualifiedJavaType;
import cn.kunter.common.generator.type.JavaVisibility;
import cn.kunter.common.generator.util.DateUtil;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.JavaBeansUtil;
import cn.kunter.common.generator.util.OutputUtilities;

/**
 * 实体查询条件生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeBaseExample {

    public static void main(String[] args) throws Exception {

        MakeBaseExample.makerExample();
    }

    /**
     * 实体查询条件生成
     * @throws Exception
     * @author yangziran
     */
    public static void makerExample() throws Exception {

        String baseExamplePackages = PackageHolder.getBaseExamplePackage();

        StringBuilder builder = new StringBuilder();
        // 包结构
        builder.append(JavaBeansUtil.getPackages(baseExamplePackages));
        // 导包
        builder.append(JavaBeansUtil.getImports("java.util.ArrayList", false, true));
        builder.append(JavaBeansUtil.getImports("java.util.List", false, false));

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * 查询条件基础类");
        OutputUtilities.newLine(builder);
        builder.append(" * @author 工具生成");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 " + DateUtil.getSysDate());
        OutputUtilities.newLine(builder);
        builder.append(" */");
        // 类开始
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                true, null, null, "BaseExample", "", true));

        Table table = new Table();
        // 字段定义
        for (Column column : table.getExample()) {
            builder.append(JavaBeansUtil.getJavaBeansField(JavaVisibility.PROTECTED.getValue(), false, false, false,
                    false, column.getJavaName(), column.getJavaType(), column.getRemarks()));
        }
        OutputUtilities.newLine(builder);

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaVisibility.PUBLIC.getValue()).append("Example() {");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("oredCriteria = new ArrayList<Criteria>();");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("}");
        OutputUtilities.newLine(builder);

        for (Column column : table.getExample()) {

            // 特殊处理分页计算当前页起始条数
            if (column.getJavaName().equals("currentSize")) {
                List<String> bodyLines = new ArrayList<String>();
                bodyLines.add("if (currentPage != null) {");
                bodyLines.add("if (currentPage > 0) {");
                bodyLines.add("--currentPage;");
                bodyLines.add("}");
                bodyLines.add("if (currentPage == 0) {");
                bodyLines.add("currentSize = 0;");
                bodyLines.add("}");
                bodyLines.add("else {");
                bodyLines.add("currentSize = currentPage * pageSize;");
                bodyLines.add("}");
                bodyLines.add("}");
                bodyLines.add("return currentSize;");
                builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false,
                        false, false, column.getJavaType(), JavaBeansUtil.getGetterMethodName(column.getJavaName()),
                        null, null, bodyLines, null));
            }
            else {
                builder.append(JavaBeansUtil.getJavaBeansGetter(JavaVisibility.PUBLIC.getValue(), column.getJavaName(),
                        column.getJavaType(), column.getRemarks()));
            }

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
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PROTECTED.getValue(), true, true, false, false,
                true, null, null, "GeneratedCriteria", null, false));

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
        method.setName("addCriterion");
        method.addBodyLine("if (value == null) {");
        method.addBodyLine("throw new RuntimeException(\"Value for condition cannot be null\");");
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
        method.setName("addCriterion");
        method.addBodyLine("if (value1 == null || value2 == null) {");
        method.addBodyLine("throw new RuntimeException(\"Between values for condition cannot be null\");");
        method.addBodyLine("}");
        method.addBodyLine("criteria.add(new Criterion(condition, value1, value2));");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        method.setName("andIsNull");
        method.addBodyLine("addCriterion(condition + \" is null\");");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        method.setName("andIsNotNull");
        method.addBodyLine("addCriterion(condition + \" is not null\");");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andEqualTo");
        method.addBodyLine("addCriterion(condition + \" =\", value);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andNotEqualTo");
        method.addBodyLine("addCriterion(condition + \" <>\", value);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andGreaterThan");
        method.addBodyLine("addCriterion(condition + \" >\", value);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andGreaterThanOrEqualTo");
        method.addBodyLine("addCriterion(condition + \" >=\", value);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andLessThan");
        method.addBodyLine("addCriterion(condition + \" <\", value);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andLessThanOrEqualTo");
        method.addBodyLine("addCriterion(condition + \" <=\", value);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("List<Object>");
        parameter = new Parameter(fqjt, "values");
        method.addParameter(parameter);
        method.setName("andIn");
        method.addBodyLine("addCriterion(condition + \" in\", values);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("List<Object>");
        parameter = new Parameter(fqjt, "values");
        method.addParameter(parameter);
        method.setName("andNotIn");
        method.addBodyLine("addCriterion(condition + \" not in\", values);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andLike");
        method.addBodyLine("addCriterion(condition + \" like\", \"%\" + value + \"%\");");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andLikeLeft");
        method.addBodyLine("addCriterion(condition + \" like\", \"%\" + value);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andLikeRight");
        method.addBodyLine("addCriterion(condition + \" like\", value + \"%\");");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value");
        method.addParameter(parameter);
        method.setName("andNotLike");
        method.addBodyLine("addCriterion(condition + \" not like\", \"%\" + value + \"%\");");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value1");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value2");
        method.addParameter(parameter);
        method.setName("andBetween");
        method.addBodyLine("addCriterion(condition + \" between\", value1, value2);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        fqjt = new FullyQualifiedJavaType("Criteria");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("String");
        parameter = new Parameter(fqjt, "condition");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value1");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Object");
        parameter = new Parameter(fqjt, "value2");
        method.addParameter(parameter);
        method.setName("andNotBetween");
        method.addBodyLine("addCriterion(condition + \" not between\", value1, value2);");
        method.addBodyLine("return (Criteria) this;");
        OutputUtilities.newLine(builder, 2);
        builder.append(method.getFormattedContent(2, false));

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansEnd(false));
        /** ---------- GeneratedCriteria End ---------- */

        /** ---------- Criteria Start ---------- */
        OutputUtilities.newLine(builder, 2);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, true, false, false,
                true, "GeneratedCriteria", null, "Criteria", null, false));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setConstructor(true);
        method.setName("Criteria");
        method.addBodyLine("super();");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(2, false));

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansEnd(false));
        /** ---------- Criteria End ---------- */

        /** ---------- Criterion Start ---------- */
        OutputUtilities.newLine(builder, 2);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, true, false, false,
                true, null, null, "Criterion", null, false));

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

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(JavaBeansUtil.getJavaBeansEnd(false));
        /** ---------- Criterion End ---------- */

        OutputUtilities.newLine(builder);
        builder.append(JavaBeansUtil.getJavaBeansEnd());
        OutputUtilities.newLine(builder);

        // 输出文件
        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + baseExamplePackages.replaceAll("\\.", "/")
                + "/BaseExample.java", builder.toString());
    }
}
