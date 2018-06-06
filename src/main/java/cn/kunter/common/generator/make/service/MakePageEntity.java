/**
 * 
 */
package cn.kunter.common.generator.make.service;

import java.util.ArrayList;
import java.util.List;

import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Column;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.type.JavaVisibility;
import cn.kunter.common.generator.util.DateUtil;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.JavaBeansUtil;
import cn.kunter.common.generator.util.OutputUtilities;

/**
 * 生成分页对象Page类
 * @author yangziran
 * @version 1.0 2016年4月20日
 */
public class MakePageEntity {

    public static void main(String[] args) throws Exception {

        MakePageEntity.makePageEntity();
    }

    /**
     * 生成分页对象Page类
     * @throws Exception
     * @author yangziran
     */
    public static void makePageEntity() throws Exception {

        Table table = new Table();
        table.setJavaName("Page");

        Column page = new Column();
        page.setJavaName("page");
        page.setJavaType("int");
        page.setRemarks("当前页，前端所请求页面数");
        table.addCols(page);

        Column size = new Column();
        size.setJavaName("size");
        size.setJavaType("int");
        size.setRemarks("页面显示数，页面显示条数");
        table.addCols(size);

        Column current = new Column();
        current.setJavaName("current");
        current.setJavaType("int");
        current.setRemarks("起始条数，当前页所显示数据在数据库中的起始位置");
        table.addCols(current);

        Column total = new Column();
        total.setJavaName("total");
        total.setJavaType("int");
        total.setRemarks("总页数，通过数据库总条数和页面所显示条数计算");
        table.addCols(total);

        Column records = new Column();
        records.setJavaName("records");
        records.setJavaType("int");
        records.setRemarks("总条数，数据库中符合条件的数据条数");
        table.addCols(records);

        Column rows = new Column();
        rows.setJavaName("rows");
        rows.setJavaType("List<?>");
        rows.setRemarks("当前页面显示数据");
        table.addCols(rows);

        String pageEntityPackages = PackageHolder.getPageEntityPackage();

        StringBuilder builder = new StringBuilder();
        // 包结构
        builder.append(JavaBeansUtil.getPackages(pageEntityPackages));
        // 导包
        builder.append(JavaBeansUtil.getImports("java.io.Serializable", false, true));
        builder.append(JavaBeansUtil.getImports("java.util.List", false, false));

        OutputUtilities.newLine(builder);
        builder.append("/**");
        OutputUtilities.newLine(builder);
        builder.append(" * 类名称：分页信息对象");
        OutputUtilities.newLine(builder);
        builder.append(" * 内容摘要：分页字段的封装");
        OutputUtilities.newLine(builder);
        builder.append(" * @author 工具生成");
        OutputUtilities.newLine(builder);
        builder.append(" * @version 1.0 " + DateUtil.getSysDate());
        OutputUtilities.newLine(builder);
        builder.append(" */");
        OutputUtilities.newLine(builder);
        builder.append("@SuppressWarnings(\"serial\")");

        // 实体实现序列化
        List<String> superInterface = new ArrayList<String>();
        superInterface.add("Serializable");
        // 类开始
        builder.append(JavaBeansUtil.getJavaBeansStart(JavaVisibility.PUBLIC.getValue(), false, false, false, false, true, null, superInterface, table.getJavaName(), table.getRemarks()));

        // 字段定义
        for (Column column : table.getCols()) {
            builder.append(JavaBeansUtil.getJavaBeansField(JavaVisibility.PRIVATE.getValue(), false, false, false, false, column.getJavaName(), column.getJavaType(), column.getRemarks()));
        }

        List<String> bodyLines = new ArrayList<String>();
        bodyLines.add("");
        OutputUtilities.newLine(builder);
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), true, false, false, false, false, false, null, table.getJavaName(), null, null, bodyLines, null));

        List<Column> parameters = new ArrayList<Column>();
        Column paramColumn = new Column();
        paramColumn.setJavaName("page");
        paramColumn.setJavaType("int");
        parameters.add(paramColumn);
        paramColumn = new Column();
        paramColumn.setJavaName("size");
        paramColumn.setJavaType("int");
        parameters.add(paramColumn);
        bodyLines = new ArrayList<String>();
        bodyLines.add("this.page = page;");
        bodyLines.add("this.size = size;");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), true, false, false, false, false, false, null, table.getJavaName(), parameters, null, bodyLines, null));

        // Get/Set
        for (Column column : table.getCols()) {

            // 特殊处理分页计算当前页起始条数
            if (column.getJavaName().equals("current")) {
                List<String> currentPage = new ArrayList<String>();
                currentPage.add("if (page > 0) {");
                currentPage.add("--page;");
                currentPage.add("}");
                currentPage.add("if (page == 0) {");
                currentPage.add("current = 0;");
                currentPage.add("}");
                currentPage.add("else {");
                currentPage.add("current = page * size;");
                currentPage.add("}");
                currentPage.add("return current;");
                builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, false, false, column.getJavaType(), column.getJavaName(), null, null,
                        currentPage, "起始条数，当前页所显示数据在数据库中的起始位置"));
            }
            else if (column.getJavaName().equals("total")) {
                List<String> currentPage = new ArrayList<String>();
                currentPage.add("if (records % size == 0) {");
                currentPage.add("total = records / size;");
                currentPage.add("}");
                currentPage.add("else {");
                currentPage.add("total = records / size + 1;");
                currentPage.add("}");
                currentPage.add("return total;");
                builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, false, false, column.getJavaType(), column.getJavaName(), null, null,
                        currentPage, "总页数，通过数据库总条数和页面所显示条数计算"));
            }
            else {
                builder.append(JavaBeansUtil.getJavaBeansGetter(JavaVisibility.PUBLIC.getValue(), column.getJavaName(), column.getJavaType(), column.getRemarks()));
            }

            builder.append(JavaBeansUtil.getJavaBeansSetter(JavaVisibility.PUBLIC.getValue(), column.getJavaName(), column.getJavaType(), column.getRemarks()));
        }

        Column currentPage = new Column();
        currentPage.setJavaName("currentPage");
        currentPage.setJavaType("int");
        Column perPage = new Column();
        perPage.setJavaName("perPage");
        perPage.setJavaType("int");
        Column totalRecord = new Column();
        totalRecord.setJavaName("totalRecord");
        totalRecord.setJavaType("int");

        List<Column> getStartRecordParameters = new ArrayList<>();
        getStartRecordParameters.add(currentPage);
        getStartRecordParameters.add(perPage);
        List<String> getStartRecord = new ArrayList<String>();
        getStartRecord.add("int startRecord = 0;");
        getStartRecord.add("if (currentPage > 0) {");
        getStartRecord.add("--currentPage;");
        getStartRecord.add("}");
        getStartRecord.add("if (currentPage == 0) {");
        getStartRecord.add("startRecord = 0;");
        getStartRecord.add("}");
        getStartRecord.add("else {");
        getStartRecord.add("startRecord = currentPage * perPage;");
        getStartRecord.add("}");
        getStartRecord.add("return startRecord;");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, true, false, "int", "getStartRecord", getStartRecordParameters, null, getStartRecord,
                "获取起始条数"));

        List<Column> getTotalPageConutParameters = new ArrayList<>();
        getTotalPageConutParameters.add(totalRecord);
        getTotalPageConutParameters.add(perPage);
        List<String> getTotalPageConut = new ArrayList<String>();
        getTotalPageConut.add("int totalPage = 0;");
        getTotalPageConut.add("if (totalRecord % perPage == 0) {");
        getTotalPageConut.add("totalPage = totalRecord / perPage;");
        getTotalPageConut.add("}");
        getTotalPageConut.add("else {");
        getTotalPageConut.add("totalPage = totalRecord / perPage + 1;");
        getTotalPageConut.add("}");
        getTotalPageConut.add("return totalPage;");
        builder.append(JavaBeansUtil.getMethods(1, JavaVisibility.PUBLIC.getValue(), false, false, false, false, true, false, "int", "getTotalPageConut", getTotalPageConutParameters, null,
                getTotalPageConut, "获取总页数"));

        // 类结束
        builder.append(JavaBeansUtil.getJavaBeansEnd());

        // 输出文件
        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + pageEntityPackages.replaceAll("\\.", "/") + "/" + table.getJavaName() + ".java", builder.toString());
    }
}
