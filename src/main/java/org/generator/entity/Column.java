/**
 * 
 */
package org.generator.entity;

/**
 * 列类型
 * @author yangziran
 * @version 1.0 2014年10月20日
 */
public class Column {

    private String columnName;
    private String javaName;
    private String sqlType;
    private String JavaType;
    private String remarks;

    /**
     * 取得 columnName
     * @return columnName String
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 设定 columnName
     * @param columnName String
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
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

    /**
     * 取得 sqlType
     * @return sqlType String
     */
    public String getSqlType() {
        return sqlType;
    }

    /**
     * 设定 sqlType
     * @param sqlType String
     */
    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    /**
     * 取得 javaType
     * @return javaType String
     */
    public String getJavaType() {
        return JavaType;
    }

    /**
     * 设定 javaType
     * @param javaType String
     */
    public void setJavaType(String javaType) {
        JavaType = javaType;
    }

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

}