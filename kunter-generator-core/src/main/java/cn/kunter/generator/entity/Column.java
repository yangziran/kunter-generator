package cn.kunter.generator.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 列结构对象
 * 对应《表结构一览》中表模板结构
 * @author yangziran
 * @version 1.0 2021/7/20
 */
@Data
@Builder
public class Column implements Serializable {

    /** 编号（序号） */
    private String serial;
    /** 列名 */
    private String columnName;
    /** 物理名 */
    private String jdbcName;
    /** 属性名 */
    private String javaName;
    /** 类型 */
    private String jdbcType;
    /** 属性类型 */
    private String javaType;
    /** 长度（可以为空，且兼容小数的表示） */
    private String length;
    /** 不为空 */
    private Boolean notNull;
    /** 主键 */
    private Boolean primaryKey;
    /** 主键顺序 */
    private String primaryKeyOrder;
    /** 备注 */
    private String remarks;

}
