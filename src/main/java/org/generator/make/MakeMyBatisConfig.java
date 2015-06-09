/**
 * 
 */
package org.generator.make;

import java.util.List;

import org.generator.config.PackageHolder;
import org.generator.config.PropertyHolder;
import org.generator.entity.Table;
import org.generator.util.FileUtil;
import org.generator.util.OutputUtilities;
import org.generator.util.StringUtility;

/**
 * MyBatisConfig生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeMyBatisConfig {

    private final static String ENTITY_PACKAGES = PackageHolder.getEntityPackage();
    private final static String BASEXML_PACKAGES = PackageHolder.getBaseXmlPackage();
    private final static String XML_PACKAGES = PackageHolder.getXmlPackage();

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

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        OutputUtilities.newLine(builder);
        builder.append("<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-config.dtd\">");
        OutputUtilities.newLine(builder);
        builder.append("<configuration>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<settings>");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 2);
        builder.append("<setting name=\"logImpl\" value=\"LOG4J2\" />");
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</settings>");

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<typeAliases>");
        for (Table table : tables) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<typeAlias alias=\"" + table.getJavaName() + "\" type=\"" + ENTITY_PACKAGES + "."
                    + table.getJavaName() + "\" />");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</typeAliases>");

        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("<mappers>");
        for (Table table : tables) {
            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<mapper resource=\"" + BASEXML_PACKAGES.replaceAll("\\.", "/") + "/Base"
                    + table.getJavaName() + "Mapper.xml\" />");

            OutputUtilities.newLine(builder);
            OutputUtilities.javaIndent(builder, 2);
            builder.append("<mapper resource=\"" + XML_PACKAGES.replaceAll("\\.", "/") + "/" + table.getJavaName()
                    + "Mapper.xml\" />");
        }
        OutputUtilities.newLine(builder);
        OutputUtilities.javaIndent(builder, 1);
        builder.append("</mappers>");

        OutputUtilities.newLine(builder);
        builder.append("</configuration>");

        StringBuilder configName = new StringBuilder();
        configName.append("mybatis-config");
        String model = PropertyHolder.getConfigProperty("model");
        if (StringUtility.isNotEmpty(model)) {
            configName.append("-").append(model);
        }
        configName.append(".xml");

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + configName.toString(), builder.toString());
    }
}
