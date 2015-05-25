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

/**
 * SQLMapper生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeXml {

    private final static String PACKAGES = PackageHolder.getXmlPackage();
    private final static String DAO_PACKAGES = PackageHolder.getDaoPackage();

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        MakeXml.makerXml(table);
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
    public static void makerXml(Table table) throws Exception {

        String namespace = DAO_PACKAGES + "." + table.getJavaName() + "Dao";

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        OutputUtilities.newLine(builder);
        builder.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        OutputUtilities.newLine(builder);
        builder.append("<mapper namespace=\"" + namespace + "\">");

        OutputUtilities.newLine(builder);
        builder.append("</mapper>");

        FileUtil.writeFile(
                PropertyHolder.getConfigProperty("target") + PACKAGES.replaceAll("\\.", "/") + "/"
                        + table.getJavaName() + "Mapper.xml", builder.toString());
    }
}
