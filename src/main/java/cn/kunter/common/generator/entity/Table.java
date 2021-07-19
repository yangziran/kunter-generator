/**
 *
 */
package cn.kunter.common.generator.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 表类型
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class Table {

    /** 表名称 */
    private String tableName;
    /** Class名称 */
    private String javaName;
    // /** 表别称 */
    // private String alias;
    /** 表备注 */
    private String remarks;
    /** 主键集合 */
    private List<Column> primaryKey = new ArrayList<Column>();
    /** 外键集合 */
    private List<Column> exportedKey = new ArrayList<Column>();
    /** 列集合 */
    private List<Column> cols = new ArrayList<Column>();
    /** 扩展字段 */
    private List<Column> example = new ArrayList<Column>();

    public Table() {
        Column orderByClause = new Column();
        orderByClause.setJavaName("orderByClause");
        orderByClause.setJavaType("String");
        orderByClause.setRemarks("排序");
        example.add(orderByClause);

        Column distinct = new Column();
        distinct.setJavaName("distinct");
        distinct.setJavaType("boolean");
        distinct.setRemarks("去重");
        example.add(distinct);

        Column oredCriteria = new Column();
        oredCriteria.setJavaName("oredCriteria");
        oredCriteria.setJavaType("List<Criteria>");
        oredCriteria.setRemarks("条件集合");
        example.add(oredCriteria);

        Column currentPage = new Column();
        currentPage.setJavaName("currentPage");
        currentPage.setJavaType("Integer");
        currentPage.setRemarks("分页 当前页");
        example.add(currentPage);

        Column currentSize = new Column();
        currentSize.setJavaName("currentSize");
        currentSize.setJavaType("Integer");
        currentSize.setRemarks("分页 当前页起始条数");
        example.add(currentSize);

        Column pageSize = new Column();
        pageSize.setJavaName("pageSize");
        pageSize.setJavaType("Integer");
        pageSize.setRemarks("分页 每页显示条数");
        example.add(pageSize);
    }

    /**
     * 取得 tableName
     * @return tableName String
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设定 tableName
     * @param tableName String
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 取得 javaName
     * @return javaName String
     */
    public String getJavaName() {
        return javaName;
    }

    /**
     * 设定 javaName
     * @param javaName String
     */
    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    // /**
    // * 取得 alias
    // * @return alias String
    // */
    // public String getAlias() {
    // return alias;
    // }
    //
    // /**
    // * 设定 alias
    // * @param alias String
    // */
    // public void setAlias(String alias) {
    // this.alias = alias;
    // }

    /**
     * 取得 remarks
     * @return remarks String
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设定 remarks
     * @param remarks String
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 取得 primaryKey
     * @return primaryKey List<Column>
     */
    public List<Column> getPrimaryKey() {
        return primaryKey;
    }

    /**
     * 设定 primaryKey
     * @param primaryKey List<Column>
     */
    public void setPrimaryKey(List<Column> primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void addPrimaryKey(Column column) {
        primaryKey.add(column);
    }

    /**
     * 取得 exportedKey
     * @return exportedKey List<Column>
     */
    public List<Column> getExportedKey() {
        return exportedKey;
    }

    /**
     * 设定 exportedKey
     * @param exportedKey List<Column>
     */
    public void setExportedKey(List<Column> exportedKey) {
        this.exportedKey = exportedKey;
    }

    public void addExportedKey(Column column) {
        exportedKey.add(column);
    }

    /**
     * 取得 cols
     * @return cols List<Column>
     */
    public List<Column> getCols() {
        return cols;
    }

    /**
     * 设定 cols
     * @param cols List<Column>
     */
    public void setCols(List<Column> cols) {
        this.cols = cols;
    }

    public void addCols(Column column) {
        cols.add(column);
    }

    /**
     * 取得 example
     * @return example List<Column>
     */
    public List<Column> getExample() {
        return example;
    }

    /**
     * 设定 example
     * @param example List<Column>
     */
    public void setExample(List<Column> example) {
        this.example = example;
    }
}
