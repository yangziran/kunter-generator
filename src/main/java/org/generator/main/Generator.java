/**
 * 
 */
package org.generator.main;

import java.util.List;

import org.generator.entity.Table;
import org.generator.make.GetTableConfig;
import org.generator.make.MakeBaseDao;
import org.generator.make.MakeBaseXml;
import org.generator.make.MakeDao;
import org.generator.make.MakeEntity;
import org.generator.make.MakeExample;
import org.generator.make.MakeMyBatisConfig;
import org.generator.make.MakeXml;

/**
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class Generator {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();
        System.out.println("Table数量：" + tables.size());

        for (final Table table : tables) {
            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        MakeEntity.makerEntity(table);
                        MakeExample.makerExample(table);
                        MakeBaseDao.makerBaseDao(table);
                        MakeDao.makerDao(table);
                        MakeBaseXml.makerBaseXml(table);
                        MakeXml.makerXml(table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        MakeMyBatisConfig.makerMyBatisConfig(tables);
    }
}