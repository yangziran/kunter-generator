package cn.kunter.common.generator.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息对象
 * @author 阳自然
 * @version 1.0 2015年8月9日
 */
@SuppressWarnings("serial")
public class Page implements Serializable {

    /** 当前页，前端所请求页面数 */
    private Integer page;
    /** 页面显示数，页面显示条数 */
    private Integer size;
    /** 起始条数，当前页所显示数据在数据库中的起始位置 */
    private Integer current;
    /** 总页数，通过数据库总条数和页面所显示条数计算 */
    private Integer total;
    /** 总条数，数据库中符合条件的数据条数 */
    private Integer records;
    /** 当前页面显示数据 */
    private List<?> rows;

    public Page() {
    }

    public Page(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    /**
     * 取得 page
     * @return page Integer
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 设定 page
     * @param page Integer
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 取得 size
     * @return size Integer
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设定 size
     * @param size Integer
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 取得 current
     * @return current Integer
     */
    public Integer getCurrent() {

        if (page > 0) {
            --page;
        }

        if (page == 0) {
            current = 0;
        } else {
            current = page * size;
        }
        return current;
    }

    /**
     * 设定 current
     * @param current Integer
     */
    public void setCurrent(Integer current) {
        this.current = current;
    }

    /**
     * 取得 total
     * @return total Integer
     */
    public Integer getTotal() {

        if (records % size == 0) {
            total = records / size;
        } else {
            total = records / size + 1;
        }
        return total;
    }

    /**
     * 设定 total
     * @param total Integer
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 取得 records
     * @return records Integer
     */
    public Integer getRecords() {
        return records;
    }

    /**
     * 设定 records
     * @param records Integer
     */
    public void setRecords(Integer records) {
        this.records = records;
    }

    /**
     * 取得 rows
     * @return rows List<?>
     */
    public List<?> getRows() {
        return rows;
    }

    /**
     * 设定 rows
     * @param rows List<?>
     */
    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
