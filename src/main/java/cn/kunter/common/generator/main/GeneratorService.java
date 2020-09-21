/**
 * 
 */
package cn.kunter.common.generator.main;

import java.util.List;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.make.service.GetTableConfig;
import cn.kunter.common.generator.make.service.MakeBaseDao;
import cn.kunter.common.generator.make.service.MakeBaseExample;
import cn.kunter.common.generator.make.service.MakeBaseService;
import cn.kunter.common.generator.make.service.MakeBaseServiceImpl;
import cn.kunter.common.generator.make.service.MakeBaseXml;
import cn.kunter.common.generator.make.service.MakeDao;
import cn.kunter.common.generator.make.service.MakeEntity;
import cn.kunter.common.generator.make.service.MakeMyBatisConfig;
import cn.kunter.common.generator.make.service.MakePageEntity;
import cn.kunter.common.generator.make.service.MakeService;
import cn.kunter.common.generator.make.service.MakeServiceImpl;
import cn.kunter.common.generator.make.service.MakeXml;

/**
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class GeneratorService {

    public static void main(String[] args) throws Exception {

        // 生成基础操作类，一般一个项目仅需要生成一次
        MakePageEntity.makePageEntity();
        MakeBaseExample.makerExample();
        MakeBaseDao.makerBaseDao();
        MakeBaseService.makeBaseService();
        MakeBaseServiceImpl.makeBaseService();

        List<Table> tables = GetTableConfig.getTableConfig();

        for (final Table table : tables) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        MakeEntity.makerEntity(table);

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
    }
}