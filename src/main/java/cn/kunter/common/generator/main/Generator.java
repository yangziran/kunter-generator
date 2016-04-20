/**
 * 
 */
package cn.kunter.common.generator.main;

import java.util.List;

import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.make.GetTableConfig;
import cn.kunter.common.generator.make.MakeBaseDao;
import cn.kunter.common.generator.make.MakeBaseXml;
import cn.kunter.common.generator.make.MakeDao;
import cn.kunter.common.generator.make.MakeEntity;
import cn.kunter.common.generator.make.MakeExample;
import cn.kunter.common.generator.make.MakeMyBatisConfig;
import cn.kunter.common.generator.make.MakePageEntity;
import cn.kunter.common.generator.make.MakeXml;

/**
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class Generator {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

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
        MakePageEntity.makePageEntity();
    }
}