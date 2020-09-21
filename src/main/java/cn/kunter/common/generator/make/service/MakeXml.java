/**
 * 
 */
package cn.kunter.common.generator.make.service;

import java.util.List;

import cn.kunter.common.generator.config.PackageHolder;
import cn.kunter.common.generator.config.PropertyHolder;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.util.FileUtil;
import cn.kunter.common.generator.util.OutputUtilities;

/**
 * SQLMapper生成
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class MakeXml {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {

            Thread thread = new Thread(new Runnable() {

                @Override
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

        String xmlPackages = PackageHolder.getXmlPackage(table.getTableName());
        String daoPackages = PackageHolder.getDaoPackage(table.getTableName());

        String namespace = daoPackages + "." + table.getJavaName() + "Dao";

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        OutputUtilities.newLine(builder);
        builder.append(
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        OutputUtilities.newLine(builder);
        builder.append("<mapper namespace=\"" + namespace + "\">");

        OutputUtilities.newLine(builder);
        builder.append("</mapper>");

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + xmlPackages.replaceAll("\\.", "/") + "/"
                + table.getJavaName() + "Mapper.xml", builder.toString());
    }
}
