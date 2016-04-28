/**
 * 
 */
package cn.kunter.common.generator.main;

import java.util.List;

import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.make.GetTableConfig;
import cn.kunter.common.generator.make.MakeBaseXml;
import cn.kunter.common.generator.make.MakeEntity;
import cn.kunter.common.generator.make.MakeExample;
import cn.kunter.common.generator.make.MakeMyBatisConfig;
import cn.kunter.common.generator.make.MakeXml;
import cn.kunter.common.generator.make.service.MakeDao;
import cn.kunter.common.generator.make.service.MakePageEntity;
import cn.kunter.common.generator.make.service.MakeService;
import cn.kunter.common.generator.make.service.MakeServiceImpl;

/**
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class GeneratorService {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {
            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        MakeEntity.makerEntity(table);
                        MakeExample.makerExample(table);

                        MakeBaseXml.makerBaseXml(table);
                        MakeXml.makerXml(table);

                        MakeDao.makerDao(table);

                        MakeService.makeService(table);
                        MakeServiceImpl.makeService(table);
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