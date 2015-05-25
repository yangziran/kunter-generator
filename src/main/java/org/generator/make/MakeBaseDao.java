/**
 * 
 */
package org.generator.make;

import java.util.List;

import org.generator.config.PackageHolder;
import org.generator.config.PropertyHolder;
import org.generator.entity.Method;
import org.generator.entity.Parameter;
import org.generator.entity.Table;
import org.generator.type.FullyQualifiedJavaType;
import org.generator.type.JavaVisibility;
import org.generator.util.FileUtil;
import org.generator.util.JavaBeansUtil;
import org.generator.util.OutputUtilities;

/**
 * 自动生成DAO生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeBaseDao {

    private final static String PACKAGES = PackageHolder.getBaseDaoPackage();
    private final static String ENTITY_PACKAGES = PackageHolder.getEntityPackage();

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        MakeBaseDao.makerBaseDao(table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    /**
     * 自动生成DAO生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makerBaseDao(Table table) throws Exception {

        StringBuilder builder = new StringBuilder();
        builder.append(JavaBeansUtil.getPackages(PACKAGES));

        builder.append(JavaBeansUtil.getImports("java.util.List", false, true));
        builder.append(JavaBeansUtil.getImports("java.util.Map", false, false));
        builder.append(JavaBeansUtil.getImports("org.apache.ibatis.annotations.Param", false, true));
        builder.append(JavaBeansUtil.getImports(ENTITY_PACKAGES + "." + table.getJavaName(), false, true));
        builder.append(JavaBeansUtil.getImports(ENTITY_PACKAGES + "." + table.getJavaName() + "Example", false, false));

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * 类名称：");
        builder.append(table.getTableName());
        builder.append("表的BaseDAO接口类");
        builder.append("Base" + table.getJavaName() + "Dao");
        OutputUtilities.newLine(builder);
        builder.append(" * 内容摘要：针对于单表的基础操作：增删改查以及统计方法，包含物理逻辑操作");
        OutputUtilities.newLine(builder);
        builder.append(" * @author 工具生成");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 2015年1月1日");
        OutputUtilities.newLine(builder);
        builder.append(" */");
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), true, false, false, true,
                false, null, null, "Base" + table.getJavaName() + "Dao", table.getRemarks()));

        Method method = new Method();
        method.setName("countByExample");
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName() + "Example");
        Parameter parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件统计表中数据数量 未删除【删除标识=0】");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 数据条数");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("countByExample_physical");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件统计表中数据数量 所有数据");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 数据条数");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("selectByExample");
        fqjt = new FullyQualifiedJavaType("List<" + table.getJavaName() + ">");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件查询表中数据 未删除【删除标识=0】");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 数据集合");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("selectByExample_physical");
        fqjt = new FullyQualifiedJavaType("List<" + table.getJavaName() + ">");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件查询表中数据 所有数据");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 数据集合");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("insert");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName());
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 往表中插入一条数据 系统字段由系统处理");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("insert_physical");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName());
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 往表中插入一条数据 系统字段需要输入");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("insertList");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("List<" + table.getJavaName() + ">");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 往表中批量插入数据 系统字段由系统处理");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("insertList_physical");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("List<" + table.getJavaName() + ">");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 往表中批量插入数据 系统字段需要输入");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("insertSelective");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName());
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 往表中插入一条数据 字段为空不插入 系统字段由系统处理");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("insertSelective_physical");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName());
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 往表中插入一条数据 字段为空不插入 系统字段需要输入");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("insertListSelective");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("List<" + table.getJavaName() + ">");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 往表中批量插入数据 字段为空不插入 系统字段由系统处理");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("insertListSelective_physical");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("List<" + table.getJavaName() + ">");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 往表中批量插入数据 字段为空不插入 系统字段需要输入");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("updateByExample");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("@Param(\"record\") " + table.getJavaName());
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("@Param(\"example\") " + table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件修改数据 未删除【删除标识=0】");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ")
                    .append(parame.getType().toString().split(" ")[1]);
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("updateByExample_physical");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("@Param(\"record\") " + table.getJavaName());
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("@Param(\"example\") " + table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件修改数据 所有数据");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ")
                    .append(parame.getType().toString().split(" ")[1]);
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("updateByExampleSelective");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("@Param(\"record\") " + table.getJavaName());
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("@Param(\"example\") " + table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件修改数据 字段为空不修改 未删除【删除标识=0】");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ")
                    .append(parame.getType().toString().split(" ")[1]);
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("updateByExampleSelective_physical");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("@Param(\"record\") " + table.getJavaName());
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("@Param(\"example\") " + table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件修改数据 字段为空不修改 所有数据");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ")
                    .append(parame.getType().toString().split(" ")[1]);
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("deleteByExample");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件删除数据 逻辑删除 将【删除标识=1】");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setName("deleteByExample_physical");
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType(table.getJavaName() + "Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * 根据条件删除数据 物理删除");
        for (Parameter parame : method.getParameters()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, true));

        // 有主键
        if (table.getPrimaryKey() != null && table.getPrimaryKey().size() > 0) {

            OutputUtilities.newLine(builder);
            method = new Method();
            method.setName("selectByPrimaryKey");
            fqjt = new FullyQualifiedJavaType(table.getJavaName());
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Map<String, Object>");
            parameter = new Parameter(fqjt, "map");
            method.addParameter(parameter);
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("/**");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * 根据主键查询数据 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @return ").append(method.getReturnType()).append(" 数据对象");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, true));

            OutputUtilities.newLine(builder);
            method = new Method();
            method.setName("selectByPrimaryKey_physical");
            fqjt = new FullyQualifiedJavaType(table.getJavaName());
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Map<String, Object>");
            parameter = new Parameter(fqjt, "map");
            method.addParameter(parameter);
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("/**");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * 根据主键查询数据 所有数据");
            for (Parameter parame : method.getParameters()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @return ").append(method.getReturnType()).append(" 数据对象");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, true));

            OutputUtilities.newLine(builder);
            method = new Method();
            method.setName("updateByPrimaryKey");
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(table.getJavaName());
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("/**");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * 根据主键修改数据 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, true));

            OutputUtilities.newLine(builder);
            method = new Method();
            method.setName("updateByPrimaryKey_physical");
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(table.getJavaName());
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("/**");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * 根据主键修改数据 所有数据");
            for (Parameter parame : method.getParameters()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, true));

            OutputUtilities.newLine(builder);
            method = new Method();
            method.setName("updateByPrimaryKeySelective");
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(table.getJavaName());
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("/**");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * 根据主键修改数据 字段为空不修改 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, true));

            OutputUtilities.newLine(builder);
            method = new Method();
            method.setName("updateByPrimaryKeySelective_physical");
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType(table.getJavaName());
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("/**");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * 根据主键修改数据 字段为空不修改 所有数据");
            for (Parameter parame : method.getParameters()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, true));

            OutputUtilities.newLine(builder);
            method = new Method();
            method.setName("deleteByPrimaryKey");
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Map<String, Object>");
            parameter = new Parameter(fqjt, "map");
            method.addParameter(parameter);
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("/**");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * 根据主键删除数据 逻辑删除 将【删除标识=1】");
            for (Parameter parame : method.getParameters()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, true));

            OutputUtilities.newLine(builder);
            method = new Method();
            method.setName("deleteByPrimaryKey_physical");
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Map<String, Object>");
            parameter = new Parameter(fqjt, "map");
            method.addParameter(parameter);
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("/**");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * 根据主键删除数据 物理删除");
            for (Parameter parame : method.getParameters()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(" * @param ").append(parame.getName()).append(" ").append(parame.getType());
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" * @return ").append(method.getReturnType()).append(" 结果数量");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, true));
        }

        builder.append(JavaBeansUtil.getJavaBeansEnd());

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + PACKAGES.replaceAll("\\.", "/") + "/Base"
                + table.getJavaName() + "Dao.java", builder.toString());
    }
}
