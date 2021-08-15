package cn.kunter.generator.datasource;

import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.datasource.excel.ExcelDataSource;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 数据源工厂
 * @author nature
 * @version 1.0 2021/8/14
 */
@Slf4j
public class DataSourceFactory {

    private static Map<String, DataSource> dataSourceMap = Maps.newHashMap();

    static {
        ClassLoader classLoader = DataSourceFactory.class.getClassLoader();
        Properties properties = new Properties();
        try (InputStream inputStream = classLoader.getResourceAsStream("generatorConfig.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("配置加载错误", e);
        }

        dataSourceMap.put(SourceType.EXCEL.name(), new ExcelDataSource(properties.getProperty("excel.filePath")));
    }

    public static DataSource getDataSource(SourceType sourceType) {
        if (dataSourceMap.containsKey(sourceType.name())) {
            return dataSourceMap.get(sourceType.name());
        }
        return null;
    }

}
