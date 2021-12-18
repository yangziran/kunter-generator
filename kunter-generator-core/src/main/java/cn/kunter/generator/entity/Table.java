package cn.kunter.generator.entity;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 表结构对象
 * @author nature
 * @version 1.0 2021/7/20
 */
@Data
@Builder
public class Table implements Serializable {

    /** 表名称（物理名称） */
    private String tableName;
    /** 类名称（表对应的实体名称） */
    private String javaName;
    /** 表备注（表名称） */
    private String remarks;

    /** 主键集合 */
    @Builder.Default
    private List<Column> primaryKeys = Lists.newArrayList();
    /** 列集合 */
    @Builder.Default
    private List<Column> columns = Lists.newArrayList();
    /** 扩展字段 */
    @Builder.Default
    private List<Column> examples = Lists.newArrayList();

    /**
     * 添加字段
     * @param column
     */
    public void addColumn(Column column) {
        columns.add(column);
    }

    /**
     * 添加主键
     * @param column
     */
    public void addPrimaryKey(Column column) {
        primaryKeys.add(column);
    }

}
