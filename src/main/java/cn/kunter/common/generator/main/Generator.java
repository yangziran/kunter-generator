/**
 * 
 */
package cn.kunter.common.generator.main;

import java.util.List;
import cn.kunter.common.generator.entity.Table;
import cn.kunter.common.generator.make.GetTableConfig;
import cn.kunter.common.generator.make.MakeBaseEntity;
import cn.kunter.common.generator.make.MakeDao;
import cn.kunter.common.generator.make.MakeEntity;
import cn.kunter.common.generator.make.MakeService;
import cn.kunter.common.generator.make.MakeServiceImpl;

/**
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class Generator {

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();
        MakeBaseEntity.makerEntity(tables.get(0));

        for (final Table table : tables) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        MakeEntity.makerEntity(table);

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
    }
}