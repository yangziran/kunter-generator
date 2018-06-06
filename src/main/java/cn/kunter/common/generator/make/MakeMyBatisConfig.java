/**
 * 
 */
package cn.kunter.common.generator.make;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.OutputUtilities;

/**
 * MyBatisConfig生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeMyBatisConfig {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();
        MakeMyBatisConfig.makerMyBatisConfig(tables);
    }

    /**
     * MyBatisConfig生成
     * @param tables
     * @throws Exception
     * @author yangziran
     */
    public static void makerMyBatisConfig(List<Table> tables) throws Exception {

        StringBuilder builder1 = new StringBuilder();
        builder1.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        OutputUtilities.newLine(builder1);
        builder1.append("<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-config.dtd\">");
        OutputUtilities.newLine(builder1);
        builder1.append("<configuration>");
        OutputUtilities.newLine(builder1);
        OutputUtilities.javaIndent(builder1, 1);
        builder1.append("<settings>");
        OutputUtilities.newLine(builder1);
        OutputUtilities.javaIndent(builder1, 2);
        builder1.append("<setting name=\"logImpl\" value=\"LOG4J2\" />");
        OutputUtilities.newLine(builder1);
        OutputUtilities.javaIndent(builder1, 1);
        builder1.append("</settings>");

        OutputUtilities.newLine(builder1);
        OutputUtilities.javaIndent(builder1, 1);
        builder1.append("<typeAliases>");

        StringBuilder builder2 = new StringBuilder();
        OutputUtilities.newLine(builder2);
        OutputUtilities.javaIndent(builder2, 1);
        builder2.append("</typeAliases>");
        OutputUtilities.newLine(builder2);
        OutputUtilities.javaIndent(builder2, 1);
        builder2.append("<mappers>");

        StringBuilder builder3 = new StringBuilder();
        OutputUtilities.newLine(builder3);
        OutputUtilities.javaIndent(builder3, 1);
        builder3.append("</mappers>");
        OutputUtilities.newLine(builder3);
        builder3.append("</configuration>");

        StringBuilder mybatis = new StringBuilder();
        if (PropertyHolder.getBooleanVal("model")) {

            Map<String, StringBuilder[]> configMap = new HashMap<>();
            for (Table table : tables) {
                String tModel = table.getTableName().split("_")[0];
                if (!configMap.containsKey(tModel)) {
                    StringBuilder typeAlias = new StringBuilder();
                    StringBuilder mapper = new StringBuilder();
                    setTable(typeAlias, mapper, table);

                    configMap.put(tModel, new StringBuilder[] { typeAlias, mapper });
                }
                else {
                    StringBuilder[] builder = configMap.get(tModel);
                    setTable(builder[0], builder[1], table);
                }
            }

            for (String model : configMap.keySet()) {
                StringBuilder[] builder = configMap.get(model);

                mybatis.setLength(0);
                mybatis.append(builder1).append(builder[0]).append(builder2).append(builder[1]).append(builder3);

                StringBuilder configName = new StringBuilder();
                configName.append("mybatis-config-").append(model).append(".xml");

                FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + configName.toString(), mybatis.toString());
            }
        }
        else {

            StringBuilder typeAlias = new StringBuilder();
            StringBuilder mapper = new StringBuilder();
            for (Table table : tables) {
                setTable(typeAlias, mapper, table);
            }

            mybatis.append(builder1).append(typeAlias).append(builder2).append(mapper).append(builder3);

            FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + "mybatis-config.xml", mybatis.toString());
        }
    }

    /**
     * 设置MyBatisConfig中的实体和SQLMapper
     * @param typeAlias
     * @param mapper
     * @param table
     * @author 阳自然
     */
    private static void setTable(StringBuilder typeAlias, StringBuilder mapper, Table table) {

        OutputUtilities.newLine(typeAlias);
        OutputUtilities.javaIndent(typeAlias, 2);
        typeAlias.append("<typeAlias alias=\"" + table.getJavaName() + "\" type=\"" + PackageHolder.getEntityPackage(table.getTableName()) + "." + table.getJavaName() + "\" />");

        OutputUtilities.newLine(mapper);
        OutputUtilities.javaIndent(mapper, 2);
        mapper.append("<mapper resource=\"" + PackageHolder.getBaseXmlPackage(table.getTableName()).replaceAll("\\.", "/") + "/Base" + table.getJavaName() + "Mapper.xml\" />");

        OutputUtilities.newLine(mapper);
        OutputUtilities.javaIndent(mapper, 2);
        mapper.append("<mapper resource=\"" + PackageHolder.getXmlPackage(table.getTableName()).replaceAll("\\.", "/") + "/" + table.getJavaName() + "Mapper.xml\" />");
    }
}
