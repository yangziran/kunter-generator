package cn.kunter.generator.datasource;

import cn.kunter.generator.datasource.enums.SourceType;
import cn.kunter.generator.entity.Table;
import cn.kunter.generator.exception.DataSourceException;

import java.util.List;

/**
 * 数据源接口
 * @author yangziran
 * @version 1.0 2026/07/28
 */
public interface DataSource {

    List<Table> getTables() throws DataSourceException;

    SourceType getSourceType();

}
