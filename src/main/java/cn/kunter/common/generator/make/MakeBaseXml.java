/**
 * 
 */
package cn.kunter.common.generator.make;

import java.util.List;

import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Column;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.type.DBType;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.OutputUtilities;

/**
 * SQLMapper生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeBaseXml {

    private final static boolean LOGICAL = Boolean.valueOf(PropertyHolder.getConfigProperty("logical"));
    private final static String DB_TYPE = DBType.valueOf(PropertyHolder.getJDBCProperty("DB")).getValue();
    private final static String PACKAGES = PackageHolder.getBaseXmlPackage();
    private final static String DAO_PACKAGES = PackageHolder.getDaoPackage();
    private final static String ENTITY_PACKAGES = PackageHolder.getEntityPackage();

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        MakeBaseXml.makerBaseXml(table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    /**
     * SQLMapper生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makerBaseXml(Table table) throws Exception {

        String namespace = DAO_PACKAGES + "." + table.getJavaName() + "Dao";
        String type = ENTITY_PACKAGES + "." + table.getJavaName();
        String typeExample = ENTITY_PACKAGES + "." + table.getJavaName() + "Example";

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        OutputUtilities.newLine(builder);
        builder.append(
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        OutputUtilities.newLine(builder);
        builder.append("<mapper namespace=\"" + namespace + "\">");

        /** ---------- resultMap Start ---------- */
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<resultMap id=\"BaseResultMap\" type=\"" + type + "\">");
        for (Column column : table.getCols()) {
            for (Column key : table.getPrimaryKey()) {
                if (column.getJavaName().equals(key.getJavaName())) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 2);
                    builder.append("<id column=\"" + column.getColumnName() + "\" jdbcType=\"" + column.getSqlType()
                            + "\" property=\"" + column.getJavaName() + "\" />");
                }
            }
        }
        for (Column column : table.getCols()) {
            String tmp = null;
            for (Column key : table.getPrimaryKey()) {
                if (column.getJavaName().equals(key.getJavaName())) {
                    tmp = "id";
                }
            }
            if (tmp == null) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("<result column=\"" + column.getColumnName() + "\" jdbcType=\"" + column.getSqlType()
                        + "\" property=\"" + column.getJavaName() + "\" />");
            }
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</resultMap>");
        /** ---------- resultMap End ---------- */

        /** ---------- Example_Where_Clause Start ---------- */
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<sql id=\"Example_Where_Clause\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<foreach collection=\"oredCriteria\" item=\"criteria\" separator=\"or\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<if test=\"criteria.valid\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("<trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 5);
        builder.append("<foreach collection=\"criteria.criteria\" item=\"criterion\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 6);
        builder.append("<choose>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("<when test=\"criterion.noValue\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("and ${criterion.condition}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("</when>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("<when test=\"criterion.singleValue\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("and ${criterion.condition} #{criterion.value}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("</when>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("<when test=\"criterion.betweenValue\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("</when>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("<when test=\"criterion.listValue\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("and ${criterion.condition}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append(
                "<foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 9);
        builder.append("#{listItem}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("</foreach>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("</when>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 6);
        builder.append("</choose>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 5);
        builder.append("</foreach>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</foreach>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</sql>");
        /** ---------- Example_Where_Clause End ---------- */

        /** ---------- Update_By_Example_Where_Clause Start ---------- */
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<sql id=\"Update_By_Example_Where_Clause\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<foreach collection=\"example.oredCriteria\" item=\"criteria\" separator=\"or\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<if test=\"criteria.valid\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("<trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 5);
        builder.append("<foreach collection=\"criteria.criteria\" item=\"criterion\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 6);
        builder.append("<choose>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("<when test=\"criterion.noValue\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("and ${criterion.condition}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("</when>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("<when test=\"criterion.singleValue\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("and ${criterion.condition} #{criterion.value}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("</when>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("<when test=\"criterion.betweenValue\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("</when>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("<when test=\"criterion.listValue\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("and ${criterion.condition}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append(
                "<foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 9);
        builder.append("#{listItem}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 8);
        builder.append("</foreach>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 7);
        builder.append("</when>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 6);
        builder.append("</choose>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 5);
        builder.append("</foreach>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</foreach>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</sql>");
        /** ---------- Update_By_Example_Where_Clause End ---------- */

        /** ---------- Base_Column_List Start ---------- */
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<sql id=\"Base_Column_List\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        for (int i = 0; i < table.getCols().size(); i++) {
            if (i != 0) {
                builder.append(",");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
            }
            Column column = table.getCols().get(i);
            builder.append(column.getColumnName());
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</sql>");
        /** ---------- Base_Column_List End ---------- */

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据条件统计表中数据数量 未删除【删除标识=0】 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<select id=\"countByExample\" parameterType=\"" + typeExample
                    + "\" resultType=\"java.lang.Integer\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("select");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("count(*)");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("from");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("delete_flag = '0'");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<if test=\"_parameter != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<trim prefix=\"and (\" suffix=\")\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("<include refid=\"Example_Where_Clause\" />");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</if>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</select>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 根据条件统计表中数据数量 所有数据 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<select id=\"countByExample_physical\" parameterType=\"" + typeExample
                + "\" resultType=\"java.lang.Integer\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("select");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("count(*)");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("from");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("where");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("1 = 1");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<if test=\"_parameter != null\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<trim prefix=\"and (\" suffix=\")\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("<include refid=\"Example_Where_Clause\" />");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</select>");

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据条件查询表中数据 未删除【删除标识=0】 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<select id=\"selectByExample\" parameterType=\"" + typeExample
                    + "\" resultMap=\"BaseResultMap\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("select");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<if test=\"distinct\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("distinct");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</if>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<include refid=\"Base_Column_List\" />");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("from");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("delete_flag = '0'");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<if test=\"_parameter != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<trim prefix=\"and (\" suffix=\")\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("<include refid=\"Example_Where_Clause\" />");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</if>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<if test=\"orderByClause != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("order by");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("${orderByClause}");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</if>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<if test=\"currentSize != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                builder.append("offset #{currentSize} rows fetch next #{pageSize} rows only");
            }
            else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                builder.append("limit #{currentSize} , #{pageSize}");
            }
            else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                builder.append("limit #{pageSize} offset #{currentSize}");
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</if>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</select>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 根据条件查询表中数据 所有数据 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<select id=\"selectByExample_physical\" parameterType=\"" + typeExample
                + "\" resultMap=\"BaseResultMap\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("select");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<if test=\"distinct\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("distinct ");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<include refid=\"Base_Column_List\" />");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("from");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("where");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("1 = 1");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<if test=\"_parameter != null\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<trim prefix=\"and (\" suffix=\")\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("<include refid=\"Example_Where_Clause\" />");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<if test=\"orderByClause != null\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("order by");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("${orderByClause}");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<if test=\"currentSize != null\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
            builder.append("offset #{currentSize} rows fetch next #{pageSize} rows only");
        }
        else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
            builder.append("limit #{currentSize} , #{pageSize}");
        }
        else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
            builder.append("limit #{pageSize} offset #{currentSize}");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</select>");

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 往表中插入一条数据 系统字段由系统处理 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<insert id=\"insert\" parameterType=\"" + type + "\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("insert into");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append(column.getColumnName() + ",");
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                if (column.getJavaName().equals("deleteFlag")) {
                    builder.append("'0',");
                }
                else if (column.getJavaName().equals("createDate") || column.getJavaName().equals("updateDate")) {
                    if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                        builder.append("sysdate,");
                    }
                    else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                        builder.append("sysdate(),");
                    }
                    else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                        builder.append("now(),");
                    }
                }
                else {
                    builder.append("#{" + column.getJavaName() + ",jdbcType=" + column.getSqlType() + "},");
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</insert>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 往表中插入一条数据 系统字段需要输入 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<insert id=\"insert_physical\" parameterType=\"" + type + "\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("insert into");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(column.getColumnName() + ",");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("#{" + column.getJavaName() + ",jdbcType=" + column.getSqlType() + "},");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</insert>");

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 往表中批量插入数据 系统字段由系统处理 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<insert id=\"insertList\" parameterType=\"java.util.List\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("insert into");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append(column.getColumnName() + ",");
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append(
                    "<foreach collection=\"list\" item=\"item\" open=\"values (\" close=\")\" separator=\" ), ( \">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<trim suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 4);
                if (column.getJavaName().equals("deleteFlag")) {
                    builder.append("'0',");
                }
                else if (column.getJavaName().equals("createDate") || column.getJavaName().equals("updateDate")) {
                    if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                        builder.append("sysdate,");
                    }
                    else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                        builder.append("sysdate(),");
                    }
                    else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                        builder.append("now(),");
                    }
                }
                else {
                    builder.append("#{item." + column.getJavaName() + ",jdbcType=" + column.getSqlType() + "},");
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</foreach>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</insert>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 往表中批量插入数据 系统字段需要输入 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<insert id=\"insertList_physical\" parameterType=\"java.util.List\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("insert into");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(column.getColumnName() + ",");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append(
                "<foreach collection=\"list\" item=\"item\" open=\"values (\" close=\")\" separator=\" ), ( \">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<trim suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("#{item." + column.getJavaName() + ",jdbcType=" + column.getSqlType() + "},");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</foreach>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</insert>");

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 往表中插入一条数据 字段为空不插入 系统字段由系统处理 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<insert id=\"insertSelective\" parameterType=\"" + type + "\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("insert into");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                if (column.getJavaName().equals("deleteFlag") || column.getJavaName().equals("createDate")
                        || column.getJavaName().equals("updateDate")) {
                    builder.append(column.getColumnName() + ",");
                }
                else {
                    builder.append("<if test=\"" + column.getJavaName() + " != null\">");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 4);
                    builder.append(column.getColumnName() + ",");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 3);
                    builder.append("</if>");
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                if (column.getJavaName().equals("deleteFlag")) {
                    builder.append("'0',");
                }
                else if (column.getJavaName().equals("createDate") || column.getJavaName().equals("updateDate")) {
                    if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                        builder.append("sysdate,");
                    }
                    else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                        builder.append("sysdate(),");
                    }
                    else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                        builder.append("now(),");
                    }
                }
                else {
                    builder.append("<if test=\"" + column.getJavaName() + " != null\">");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 4);
                    builder.append("#{" + column.getJavaName() + ",jdbcType=" + column.getSqlType() + "},");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 3);
                    builder.append("</if>");
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</insert>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 往表中插入一条数据 字段为空不插入 系统字段需要输入 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<insert id=\"insertSelective_physical\" parameterType=\"" + type + "\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("insert into");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<if test=\"" + column.getJavaName() + " != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append(column.getColumnName() + ",");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</if>");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<if test=\"" + column.getJavaName() + " != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("#{" + column.getJavaName() + ",jdbcType=" + column.getSqlType() + "},");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</if>");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</insert>");

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 往表中批量插入数据 字段为空不插入 系统字段由系统处理 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<insert id=\"insertListSelective\" parameterType=\"java.util.List\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("insert into");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                if (column.getJavaName().equals("deleteFlag") || column.getJavaName().equals("createDate")
                        || column.getJavaName().equals("updateDate")) {
                    builder.append(column.getColumnName() + ",");
                }
                else {
                    builder.append("<if test=\"" + column.getJavaName() + " != null\">");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 4);
                    builder.append(column.getColumnName() + ",");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 3);
                    builder.append("</if>");
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append(
                    "<foreach collection=\"list\" item=\"item\" open=\"values (\" close=\")\" separator=\" ), ( \">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<trim suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 4);
                if (column.getJavaName().equals("deleteFlag")) {
                    builder.append("'0',");
                }
                else if (column.getJavaName().equals("createDate") || column.getJavaName().equals("updateDate")) {
                    if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                        builder.append("sysdate,");
                    }
                    else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                        builder.append("sysdate(),");
                    }
                    else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                        builder.append("now(),");
                    }
                }
                else {
                    builder.append("<if test=\"" + column.getJavaName() + " != null\">");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 5);
                    builder.append("#{" + column.getJavaName() + ",jdbcType=" + column.getSqlType() + "},");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 4);
                    builder.append("</if>");
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</foreach>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</insert>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 往表中批量插入数据 字段为空不插入 系统字段需要输入 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<insert id=\"insertListSelective_physical\" parameterType=\"java.util.List\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("insert into");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<if test=\"" + column.getJavaName() + " != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append(column.getColumnName() + ",");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</if>");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append(
                "<foreach collection=\"list\" item=\"item\" open=\"values (\" close=\")\" separator=\" ), ( \">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<trim suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("<if test=\"" + column.getJavaName() + " != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 5);
            builder.append("#{" + column.getJavaName() + ",jdbcType=" + column.getSqlType() + "},");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("</if>");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</foreach>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</insert>");

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据条件修改数据 未删除【删除标识=0】 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<update id=\"updateByExample\" parameterType=\"java.util.Map\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("update");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<trim prefix=\"set\" suffix=\"\" suffixOverrides=\",\">");
            for (Column column : table.getCols()) {
                if (!column.getColumnName().equals("create_date") && !column.getColumnName().equals("create_user_id")) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 3);
                    if (column.getColumnName().equals("update_date")) {
                        builder.append(column.getColumnName()).append(" = ");
                        if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                            builder.append("sysdate,");
                        }
                        else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                            builder.append("sysdate(),");
                        }
                        else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                            builder.append("now(),");
                        }
                    }
                    else {
                        builder.append(column.getColumnName() + " = #{record." + column.getJavaName() + ",jdbcType="
                                + column.getSqlType() + "},");
                    }
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("delete_flag = '0'");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<if test=\"_parameter != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<trim prefix=\"and (\" suffix=\")\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("<include refid=\"Update_By_Example_Where_Clause\" />");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</if>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</update>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 根据条件修改数据 所有数据 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<update id=\"updateByExample_physical\" parameterType=\"java.util.Map\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("update");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<trim prefix=\"set\" suffix=\"\" suffixOverrides=\",\">");
        for (Column column : table.getCols()) {
            if (!column.getColumnName().equals("create_date") && !column.getColumnName().equals("create_user_id")) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                if (column.getColumnName().equals("update_date")) {
                    builder.append(column.getColumnName()).append(" = ");
                    if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                        builder.append("sysdate,");
                    }
                    else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                        builder.append("sysdate(),");
                    }
                    else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                        builder.append("now(),");
                    }
                }
                else {
                    builder.append(column.getColumnName() + " = #{record." + column.getJavaName() + ",jdbcType="
                            + column.getSqlType() + "},");
                }
            }
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("where");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("1 = 1");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<if test=\"_parameter != null\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<trim prefix=\"and (\" suffix=\")\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("<include refid=\"Update_By_Example_Where_Clause\" />");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</update>");

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据条件修改数据 字段为空不修改 未删除【删除标识=0】 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<update id=\"updateByExampleSelective\" parameterType=\"java.util.Map\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("update");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<set>");
            for (Column column : table.getCols()) {
                if (!column.getColumnName().equals("create_date") && !column.getColumnName().equals("create_user_id")) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 3);
                    if (column.getColumnName().equals("update_date")) {
                        builder.append(column.getColumnName()).append(" = ");
                        if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                            builder.append("sysdate,");
                        }
                        else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                            builder.append("sysdate(),");
                        }
                        else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                            builder.append("now(),");
                        }
                    }
                    else if (column.getColumnName().equals("update_user_id")) {
                        builder.append(column.getColumnName() + " = #{record." + column.getJavaName() + ",jdbcType="
                                + column.getSqlType() + "},");
                    }
                    else {
                        builder.append("<if test=\"record." + column.getJavaName() + " != null\">");
                        OutputUtilities.newLine(builder);
                        OutputUtilities.javaIndent(builder, 4);
                        builder.append(column.getColumnName() + " = #{record." + column.getJavaName() + ",jdbcType="
                                + column.getSqlType() + "},");
                        OutputUtilities.newLine(builder);
                        OutputUtilities.javaIndent(builder, 3);
                        builder.append("</if>");
                    }
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</set>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("delete_flag = '0'");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<if test=\"_parameter != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<trim prefix=\"and (\" suffix=\")\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("<include refid=\"Update_By_Example_Where_Clause\" />");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</if>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</update>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 根据条件修改数据 字段为空不修改 所有数据 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<update id=\"updateByExampleSelective_physical\" parameterType=\"java.util.Map\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("update");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<set>");
        for (Column column : table.getCols()) {
            if (!column.getColumnName().equals("create_date") && !column.getColumnName().equals("create_user_id")) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                if (column.getColumnName().equals("update_date")) {
                    builder.append(column.getColumnName()).append(" = ");
                    if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                        builder.append("sysdate,");
                    }
                    else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                        builder.append("sysdate(),");
                    }
                    else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                        builder.append("now(),");
                    }
                }
                else if (column.getColumnName().equals("update_user_id")) {
                    builder.append(column.getColumnName()).append(" = #{record." + column.getJavaName())
                            .append(",jdbcType=").append(column.getSqlType() + "},");
                }
                else {
                    builder.append("<if test=\"record.").append(column.getJavaName()).append(" != null\">");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 4);
                    builder.append(column.getColumnName()).append(" = #{record." + column.getJavaName())
                            .append(",jdbcType=").append(column.getSqlType() + "},");
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 3);
                    builder.append("</if>");
                }
            }
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</set>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("where");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("1 = 1");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<if test=\"_parameter != null\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<trim prefix=\"and (\" suffix=\")\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("<include refid=\"Update_By_Example_Where_Clause\" />");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</update>");

        if (LOGICAL) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据条件删除数据 逻辑删除 将【删除标识=1】 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<delete id=\"deleteByExample\" parameterType=\"" + typeExample + "\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("update");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("set");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("delete_flag = '1'");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("delete_flag = '0'");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<if test=\"_parameter != null\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<trim prefix=\"and (\" suffix=\")\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 4);
            builder.append("<include refid=\"Example_Where_Clause\" />");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("</trim>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</if>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</delete>");
        }

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<!-- 根据条件删除数据 物理删除 -->");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<delete id=\"deleteByExample_physical\" parameterType=\"" + typeExample + "\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("delete from");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append(table.getTableName());
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("where");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("1 = 1");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<if test=\"_parameter != null\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("<trim prefix=\"and (\" suffix=\")\">");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 4);
        builder.append("<include refid=\"Example_Where_Clause\" />");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 3);
        builder.append("</trim>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("</if>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</delete>");

        // 有主键
        if (table.getPrimaryKey() != null && table.getPrimaryKey().size() > 0) {

            if (LOGICAL) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("<!-- 根据主键查询数据 未删除【删除标识=0】 -->");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append(
                        "<select id=\"selectByPrimaryKey\" parameterType=\"java.util.Map\" resultMap=\"BaseResultMap\">");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("select");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append("<include refid=\"Base_Column_List\" />");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("from");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append(table.getTableName());
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("where");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append("delete_flag = '0'");
                for (int i = 0; i < table.getPrimaryKey().size(); i++) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 2);
                    builder.append("and ");
                    Column column = table.getPrimaryKey().get(i);
                    builder.append(column.getColumnName()).append(" = #{").append(column.getJavaName()).append("}");
                }
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("</select>");
            }

            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据主键查询数据 所有数据 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append(
                    "<select id=\"selectByPrimaryKey_physical\" parameterType=\"java.util.Map\" resultMap=\"BaseResultMap\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("select");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("<include refid=\"Base_Column_List\" />");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("from");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("1 = 1");
            for (int i = 0; i < table.getPrimaryKey().size(); i++) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("and ");
                Column column = table.getPrimaryKey().get(i);
                builder.append(column.getColumnName()).append(" = #{").append(column.getJavaName()).append("}");
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</select>");

            if (LOGICAL) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("<!-- 根据主键修改数据 未删除【删除标识=0】 -->");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("<update id=\"updateByPrimaryKey\" parameterType=\"" + type + "\">");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("update");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append(table.getTableName());
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("<set>");
                for (Column column : table.getCols()) {
                    if (!column.getColumnName().equals("create_date")
                            && !column.getColumnName().equals("create_user_id")) {
                        OutputUtilities.newLine(builder);
                        OutputUtilities.javaIndent(builder, 3);
                        if (column.getColumnName().equals("update_date")) {
                            builder.append(column.getColumnName()).append(" = ");
                            if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                                builder.append("sysdate,");
                            }
                            else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                                builder.append("sysdate(),");
                            }
                            else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                                builder.append("now(),");
                            }
                        }
                        else {
                            builder.append(column.getColumnName() + " = #{" + column.getJavaName() + ",jdbcType="
                                    + column.getSqlType() + "},");
                        }
                    }
                }
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("</set>");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("where");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append("delete_flag = '0'");
                for (int i = 0; i < table.getPrimaryKey().size(); i++) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 2);
                    builder.append("and ");
                    Column column = table.getPrimaryKey().get(i);
                    builder.append(column.getColumnName()).append(" = #{").append(column.getJavaName())
                            .append(",jdbcType=").append(column.getSqlType()).append("}");
                }
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("</update>");
            }

            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据主键修改数据 所有数据 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<update id=\"updateByPrimaryKey_physical\" parameterType=\"" + type + "\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("update");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<set>");
            for (Column column : table.getCols()) {
                if (!column.getColumnName().equals("create_date") && !column.getColumnName().equals("create_user_id")) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 3);
                    builder.append(column.getColumnName() + " = #{" + column.getJavaName() + ",jdbcType="
                            + column.getSqlType() + "},");
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</set>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("1 = 1");
            for (int i = 0; i < table.getPrimaryKey().size(); i++) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("and ");
                Column column = table.getPrimaryKey().get(i);
                builder.append(column.getColumnName()).append(" = #{").append(column.getJavaName()).append(",jdbcType=")
                        .append(column.getSqlType()).append("}");
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</update>");

            if (LOGICAL) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("<!-- 根据主键修改数据 字段为空不修改 未删除【删除标识=0】 -->");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("<update id=\"updateByPrimaryKeySelective\" parameterType=\"" + type + "\">");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("update");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append(table.getTableName());
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("<set>");
                for (Column column : table.getCols()) {
                    if (!column.getColumnName().equals("create_date")
                            && !column.getColumnName().equals("create_user_id")) {
                        OutputUtilities.newLine(builder);
                        OutputUtilities.javaIndent(builder, 3);
                        if (column.getColumnName().equals("update_date")) {
                            builder.append(column.getColumnName()).append(" = ");
                            if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                                builder.append("sysdate,");
                            }
                            else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                                builder.append("sysdate(),");
                            }
                            else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                                builder.append("now(),");
                            }
                        }
                        else if (column.getColumnName().equals("update_user_id")) {
                            builder.append(column.getColumnName() + " = #{" + column.getJavaName() + ",jdbcType="
                                    + column.getSqlType() + "},");
                        }
                        else {
                            builder.append("<if test=\"" + column.getJavaName() + " != null\">");
                            OutputUtilities.newLine(builder);
                            OutputUtilities.javaIndent(builder, 4);
                            builder.append(column.getColumnName() + " = #{" + column.getJavaName() + ",jdbcType="
                                    + column.getSqlType() + "},");
                            OutputUtilities.newLine(builder);
                            OutputUtilities.javaIndent(builder, 3);
                            builder.append("</if>");
                        }
                    }
                }
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("</set>");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("where ");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append("delete_flag = '0'");
                for (int i = 0; i < table.getPrimaryKey().size(); i++) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 2);
                    builder.append("and ");
                    Column column = table.getPrimaryKey().get(i);
                    builder.append(column.getColumnName()).append(" = #{").append(column.getJavaName())
                            .append(",jdbcType=").append(column.getSqlType()).append("}");
                }
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("</update>");
            }

            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据主键修改数据 字段为空不修改 所有数据 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<update id=\"updateByPrimaryKeySelective_physical\" parameterType=\"" + type + "\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("update");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<set>");
            for (Column column : table.getCols()) {
                if (!column.getColumnName().equals("create_date") && !column.getColumnName().equals("create_user_id")) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 3);
                    if (column.getColumnName().equals("update_date")) {
                        builder.append(column.getColumnName()).append(" = ");
                        if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                            builder.append("sysdate,");
                        }
                        else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                            builder.append("sysdate(),");
                        }
                        else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                            builder.append("now(),");
                        }
                    }
                    else if (column.getColumnName().equals("update_user_id")) {
                        builder.append(column.getColumnName() + " = #{" + column.getJavaName() + ",jdbcType="
                                + column.getSqlType() + "},");
                    }
                    else {
                        builder.append("<if test=\"" + column.getJavaName() + " != null\">");
                        OutputUtilities.newLine(builder);
                        OutputUtilities.javaIndent(builder, 4);
                        builder.append(column.getColumnName() + " = #{" + column.getJavaName() + ",jdbcType="
                                + column.getSqlType() + "},");
                        OutputUtilities.newLine(builder);
                        OutputUtilities.javaIndent(builder, 3);
                        builder.append("</if>");
                    }
                }
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("</set>");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("1 = 1");
            for (int i = 0; i < table.getPrimaryKey().size(); i++) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("and ");
                Column column = table.getPrimaryKey().get(i);
                builder.append(column.getColumnName()).append(" = #{").append(column.getJavaName()).append(",jdbcType=")
                        .append(column.getSqlType()).append("}");
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</update>");

            if (LOGICAL) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("<!-- 根据主键删除数据 逻辑删除 将【删除标识=1】 -->");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("<update id=\"deleteByPrimaryKey\" parameterType=\"java.util.Map\">");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("update");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append(table.getTableName());
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("set");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append("delete_flag = '1'");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("where");
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 3);
                builder.append("delete_flag = '0'");
                for (int i = 0; i < table.getPrimaryKey().size(); i++) {
                    OutputUtilities.newLine(builder);
                    OutputUtilities.javaIndent(builder, 2);
                    builder.append("and ");
                    Column column = table.getPrimaryKey().get(i);
                    builder.append(column.getColumnName()).append(" = #{").append(column.getJavaName()).append("}");
                }
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 1);
                builder.append("</update>");
            }

            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<!-- 根据主键删除数据 物理删除 -->");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("<delete id=\"deleteByPrimaryKey_physical\" parameterType=\"java.util.Map\">");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("delete from");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append(table.getTableName());
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("where");
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 3);
            builder.append("1 = 1");
            for (int i = 0; i < table.getPrimaryKey().size(); i++) {
                OutputUtilities.newLine(builder);
                OutputUtilities.javaIndent(builder, 2);
                builder.append("and ");
                Column column = table.getPrimaryKey().get(i);
                builder.append(column.getColumnName()).append(" = #{").append(column.getJavaName()).append("}");
            }
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 1);
            builder.append("</delete>");
        }

        OutputUtilities.newLine(builder);
        builder.append("</mapper>");

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + PACKAGES.replaceAll("\\.", "/") + "/Base"
                + table.getJavaName() + "Mapper.xml", builder.toString());
    }
}
