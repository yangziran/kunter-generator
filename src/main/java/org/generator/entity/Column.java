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

    private String serial;
    private String remarks;
    private String columnName;
    private String javaName;
    private String sqlType;
    private String JavaType;
    private String isNotNull;
    private String primaryKeyOrder;
    private String foreignKey;
    private Integer length;

    /**
     * 取得 serial
     * @return serial String
     */
    public String getSerial() {
        return serial;
    }

    /**
     * 设定 serial
     * @param serial String
     */
    public void setSerial(String serial) {
        this.serial = serial;
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
     * 取得 isNotNull
     * @return isNotNull String
     */
    public String getIsNotNull() {
        return isNotNull;
    }

    /**
     * 设定 isNotNull
     * @param isNotNull String
     */
    public void setIsNotNull(String isNotNull) {
        this.isNotNull = isNotNull;
    }

    /**
     * 取得 primaryKeyOrder
     * @return primaryKeyOrder String
     */
    public String getPrimaryKeyOrder() {
        return primaryKeyOrder;
    }

    /**
     * 设定 primaryKeyOrder
     * @param primaryKeyOrder String
     */
    public void setPrimaryKeyOrder(String primaryKeyOrder) {
        this.primaryKeyOrder = primaryKeyOrder;
    }

    /**
     * 取得 foreignKey
     * @return foreignKey String
     */
    public String getForeignKey() {
        return foreignKey;
    }

    /**
     * 设定 foreignKey
     * @param foreignKey String
     */
    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * 取得 length
     * @return length Integer
     */
    public Integer getLength() {
        return length;
    }

    /**
     * 设定 length
     * @param length Integer
     */
    public void setLength(Integer length) {
        this.length = length;
    }

}