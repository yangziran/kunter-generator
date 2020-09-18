/**
 * 
 */
package cn.kunter.common.generator.make.service;

import java.util.ArrayList;
import java.util.List;

import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Method;
import cn.kunter.common.generator.entity.Parameter;
import cn.kunter.common.generator.type.FullyQualifiedJavaType;
import cn.kunter.common.generator.type.JavaVisibility;
import cn.kunter.common.generator.util.DaoMethodNameUtil;
import cn.kunter.common.generator.util.DateUtil;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.JavaBeansUtil;
import cn.kunter.common.generator.util.OutputUtilities;

/**
 * 生成基础ServiceImpl类
 * @author yangziran
 * @version 1.0 2016年4月21日
 */
public class MakeBaseServiceImpl {

    private final static boolean LOGICAL = PropertyHolder.getBooleanVal("logical");

    public static void main(String[] args) throws Exception {

        MakeBaseServiceImpl.makeBaseService();
    }

    /**
     * 生成基础ServiceImpl类
     * @throws Exception
     * @author yangziran
     */
    public static void makeBaseService() throws Exception {

        String baseServiceImplPackages = PackageHolder.getBaseServiceImplPackage();
        String baseDaoPackages = PackageHolder.getCommonBaseDaoPackage();
        String baseServicePackages = PackageHolder.getBaseServicePackage();

        StringBuilder builder = new StringBuilder();
        builder.append(JavaBeansUtil.getPackages(baseServiceImplPackages));

        builder.append(JavaBeansUtil.getImports("java.util.List", false, true));
        builder.append(JavaBeansUtil.getImports("java.util.Map", false, false));

        builder.append(JavaBeansUtil.getImports(baseDaoPackages + ".BaseDao", false, true));
        builder.append(JavaBeansUtil.getImports(PackageHolder.getPageEntityPackage() + ".Page", false, false));
        builder.append(JavaBeansUtil.getImports(baseServicePackages + ".BaseService", false, false));

        List<String> superInterface = new ArrayList<>();
        superInterface.add("BaseService<T, Example>");

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * 公共业务处理类实现");
        OutputUtilities.newLine(builder);
        builder.append(" * 单表的增删改查以及统计方法，包含物理逻辑操作");
        OutputUtilities.newLine(builder);
        builder.append(" * @author 工具生成");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 " + DateUtil.getSysDate());
        OutputUtilities.newLine(builder);
        builder.append(" */");
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), true, false, false, false,
                true, null, superInterface, "BaseServiceImpl<Dao extends BaseDao<T, Example>, T, Example>", null));

        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, false,
                false, "Dao", "getDao", null, null, new ArrayList<String>(), null));

        Method method = new Method();
        FullyQualifiedJavaType fqjt;
        Parameter parameter;
        if (LOGICAL) {
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getCountByExample(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Example");
            parameter = new Parameter(fqjt, "example");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(example);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据条件统计表中数据数量 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 数据条数");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
            OutputUtilities.newLine(builder);
        }

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getCountByExample(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(example);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据条件统计表中数据数量 所有数据");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 数据条数");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getSelectByExample(LOGICAL));
            fqjt = new FullyQualifiedJavaType("List<T>");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Example");
            parameter = new Parameter(fqjt, "example");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(example);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据条件查询表中数据 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 数据集合");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getSelectByExample(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("List<T>");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(example);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据条件查询表中数据 所有数据");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 数据集合");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getSelectPageByExample(LOGICAL));
            fqjt = new FullyQualifiedJavaType("Page");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Example");
            parameter = new Parameter(fqjt, "example");
            method.addParameter(parameter);
            method.addBodyLine("int total = getDao().countByExample(example);");
            method.addBodyLine("List<T> data = getDao().selectByExample(example);");
            method.addBodyLine("int currentPage = 0;");
            method.addBodyLine("int pageSize = 10;");
            method.addBodyLine("try {");
            method.addBodyLine("currentPage = example.getClass().getDeclaredField(\"currentPage\").getInt(example);");
            method.addBodyLine("pageSize = example.getClass().getDeclaredField(\"pageSize\").getInt(example);");
            method.addBodyLine(
                    "} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {");
            method.addBodyLine("e.printStackTrace();");
            method.addBodyLine("}");
            method.addBodyLine("Page page = new Page(currentPage, pageSize);");
            method.addBodyLine("page.setRecords(total);");
            method.addBodyLine("page.setRows(data);");
            method.addBodyLine("return page;");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据条件分页查询表中数据 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 分页对象");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getSelectPageByExample(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("Page");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        method.addBodyLine("int total = getDao().countByExample(example);");
        method.addBodyLine("List<T> data = getDao().selectByExample(example);");
        method.addBodyLine("int currentPage = 0;");
        method.addBodyLine("int pageSize = 10;");
        method.addBodyLine("try {");
        method.addBodyLine("currentPage = example.getClass().getDeclaredField(\"currentPage\").getInt(example);");
        method.addBodyLine("pageSize = example.getClass().getDeclaredField(\"pageSize\").getInt(example);");
        method.addBodyLine(
                "} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {");
        method.addBodyLine("e.printStackTrace();");
        method.addBodyLine("}");
        method.addBodyLine("Page page = new Page(currentPage, pageSize);");
        method.addBodyLine("page.setRecords(total);");
        method.addBodyLine("page.setRows(data);");
        method.addBodyLine("return page;");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据条件分页查询表中数据 所有数据");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 分页对象");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getInsert(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("T");
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(record);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 往表中插入一条数据 系统字段由系统处理");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getInsert(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("T");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(record);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 往表中插入一条数据 系统字段需要输入");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getInsertList(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("List<T>");
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(record);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 往表中批量插入数据 系统字段由系统处理");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getInsertList(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("List<T>");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(record);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 往表中批量插入数据 系统字段需要输入");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getInsertSelective(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("T");
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(record);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 往表中插入一条数据 字段为空不插入 系统字段由系统处理");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getInsertSelective(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("T");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(record);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 往表中插入一条数据 字段为空不插入 系统字段需要输入");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getInsertListSelective(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("List<T>");
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(record);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 往表中批量插入数据 字段为空不插入 系统字段由系统处理");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getInsertListSelective(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("List<T>");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(record);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 往表中批量插入数据 字段为空不插入 系统字段需要输入");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getUpdateByExample(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("T");
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            fqjt = new FullyQualifiedJavaType("Example");
            parameter = new Parameter(fqjt, "example");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(record, example);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据条件修改数据 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getUpdateByExample(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("T");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(record, example);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据条件修改数据 所有数据");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getUpdateByExampleSelective(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("T");
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            fqjt = new FullyQualifiedJavaType("Example");
            parameter = new Parameter(fqjt, "example");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(record, example);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据条件修改数据 字段为空不修改 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getUpdateByExampleSelective(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("T");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        fqjt = new FullyQualifiedJavaType("Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(record, example);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据条件修改数据 字段为空不修改 所有数据");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getDeleteByExample(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Example");
            parameter = new Parameter(fqjt, "example");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(example);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据条件删除数据 逻辑删除 将【删除标识=1】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getDeleteByExample(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("Example");
        parameter = new Parameter(fqjt, "example");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(example);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据条件删除数据 物理删除");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getSelectByPrimaryKey(LOGICAL));
            fqjt = new FullyQualifiedJavaType("T");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Map<String, Object>");
            parameter = new Parameter(fqjt, "map");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(map);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据主键查询数据 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 数据对象");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getSelectByPrimaryKey(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("T");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("Map<String, Object>");
        parameter = new Parameter(fqjt, "map");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(map);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据主键查询数据 所有数据");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 数据对象");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getUpdateByPrimaryKey(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("T");
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(record);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据主键修改数据 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getUpdateByPrimaryKey(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("T");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(record);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据主键修改数据 所有数据");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getUpdateByPrimaryKeySelective(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("T");
            parameter = new Parameter(fqjt, "record");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(record);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据主键修改数据 字段为空不修改 未删除【删除标识=0】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getUpdateByPrimaryKeySelective(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("T");
        parameter = new Parameter(fqjt, "record");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(record);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据主键修改数据 字段为空不修改 所有数据");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setName(DaoMethodNameUtil.getDeleteByPrimaryKey(LOGICAL));
            fqjt = new FullyQualifiedJavaType("int");
            method.setReturnType(fqjt);
            fqjt = new FullyQualifiedJavaType("Map<String, Object>");
            parameter = new Parameter(fqjt, "map");
            method.addParameter(parameter);
            method.addBodyLine("return getDao()." + method.getName() + "(map);");
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * 根据主键删除数据 逻辑删除 将【删除标识=1】");
            for (Parameter parame : method.getParameters()) {
                method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
            }
            method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
            method.addJavaDocLine(" */");
            OutputUtilities.newLine(builder);
            builder.append(method.getFormattedContent(1, false));
        }

        OutputUtilities.newLine(builder);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(DaoMethodNameUtil.getDeleteByPrimaryKey(!LOGICAL));
        fqjt = new FullyQualifiedJavaType("int");
        method.setReturnType(fqjt);
        fqjt = new FullyQualifiedJavaType("Map<String, Object>");
        parameter = new Parameter(fqjt, "map");
        method.addParameter(parameter);
        method.addBodyLine("return getDao()." + method.getName() + "(map);");
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 根据主键删除数据 物理删除");
        for (Parameter parame : method.getParameters()) {
            method.addJavaDocLine(" * @param " + parame.getName() + " " + parame.getType());
        }
        method.addJavaDocLine(" * @return " + method.getReturnType() + " 结果数量");
        method.addJavaDocLine(" */");
        OutputUtilities.newLine(builder);
        builder.append(method.getFormattedContent(1, false));
        OutputUtilities.newLine(builder);
        builder.append(JavaBeansUtil.getJavaBeansEnd());
        OutputUtilities.newLine(builder);

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + baseServiceImplPackages.replaceAll("\\.", "/")
                + "/BaseServiceImpl.java", builder.toString());
    }
}
