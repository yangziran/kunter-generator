/**
 * 
 */
package cn.kunter.common.generator.util;

/**
 * DAO方法名称工具类
 * @author 阳自然
 * @version 1.0 2015年9月2日
 */
public class DaoMethodNameUtil {

    /** 物理操作标识 */
    private static final String PHYSICAL = "_physical";
    /** 统计表中数据 */
    private static final String COUNTBYEXAMPLE = "countByExample";
    /** 根据条件查询 */
    private static final String SELECTBYEXAMPLE = "selectByExample";
    /** 根据条件分页查询 */
    private static final String SELECTPAGEBYEXAMPLE = "selectPageByExample";
    /** 插入数据 */
    private static final String INSERT = "insert";
    /** 批量插入数据 */
    private static final String INSERTLIST = "insertList";
    /** 插入数据（为null的不插入） */
    private static final String INSERTSELECTIVE = "insertSelective";
    /** 批量插入数据（为null的不插入） */
    private static final String INSERTLISTSELECTIVE = "insertListSelective";
    /** 根据条件修改数据 */
    private static final String UPDATEBYEXAMPLE = "updateByExample";
    /** 根据条件修改数据（为null的不修改） */
    private static final String UPDATEBYEXAMPLESELECTIVE = "updateByExampleSelective";
    /** 根据条件删除数据 */
    private static final String DELETEBYEXAMPLE = "deleteByExample";
    /** 根据主键查询数据 */
    private static final String SELECTBYPRIMARYKEY = "selectByPrimaryKey";
    /** 根据主键修改数据 */
    private static final String UPDATEBYPRIMARYKEY = "updateByPrimaryKey";
    /** 根据主键修改数据（为null的不修改） */
    private static final String UPDATEBYPRIMARYKEYSELECTIVE = "updateByPrimaryKeySelective";
    /** 根据主键删除数据 */
    private static final String DELETEBYPRIMARYKEY = "deleteByPrimaryKey";

    /**
     * 获取统计数据方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getCountByExample(boolean physical) {
        return physical ? COUNTBYEXAMPLE : COUNTBYEXAMPLE + PHYSICAL;
    }

    /**
     * 获取查询数据方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getSelectByExample(boolean physical) {
        return physical ? SELECTBYEXAMPLE : SELECTBYEXAMPLE + PHYSICAL;
    }

    /**
     * 获取分页查询数据方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getSelectPageByExample(boolean physical) {
        return physical ? SELECTPAGEBYEXAMPLE : SELECTPAGEBYEXAMPLE + PHYSICAL;
    }

    /**
     * 获取插入方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getInsert(boolean physical) {
        return physical ? INSERT : INSERT + PHYSICAL;
    }

    /**
     * 获取批量插入方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getInsertList(boolean physical) {
        return physical ? INSERTLIST : INSERTLIST + PHYSICAL;
    }

    /**
     * 获取插入不为空字段方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getInsertSelective(boolean physical) {
        return physical ? INSERTSELECTIVE : INSERTSELECTIVE + PHYSICAL;
    }

    /**
     * 获取批量插入不为空字段方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getInsertListSelective(boolean physical) {
        return physical ? INSERTLISTSELECTIVE : INSERTLISTSELECTIVE + PHYSICAL;
    }

    /**
     * 获取根据条件修改方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getUpdateByExample(boolean physical) {
        return physical ? UPDATEBYEXAMPLE : UPDATEBYEXAMPLE + PHYSICAL;
    }

    /**
     * 获取根据条件修改不为null字段方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getUpdateByExampleSelective(boolean physical) {
        return physical ? UPDATEBYEXAMPLESELECTIVE : UPDATEBYEXAMPLESELECTIVE + PHYSICAL;
    }

    /**
     * 获取根据主键删除方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getDeleteByExample(boolean physical) {
        return physical ? DELETEBYEXAMPLE : DELETEBYEXAMPLE + PHYSICAL;
    }

    /**
     * 获取根据主键查询方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getSelectByPrimaryKey(boolean physical) {
        return physical ? SELECTBYPRIMARYKEY : SELECTBYPRIMARYKEY + PHYSICAL;
    }

    /**
     * 获取根据主键修改方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getUpdateByPrimaryKey(boolean physical) {
        return physical ? UPDATEBYPRIMARYKEY : UPDATEBYPRIMARYKEY + PHYSICAL;
    }

    /**
     * 获取根据主键修改不为null字段方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getUpdateByPrimaryKeySelective(boolean physical) {
        return physical ? UPDATEBYPRIMARYKEYSELECTIVE : UPDATEBYPRIMARYKEYSELECTIVE + PHYSICAL;
    }

    /**
     * 获取根据主键删除方法名称
     * @param physical 是否逻辑操作 物理操作logical参数取非传入
     * @return String 方法名称
     * @author 阳自然
     */
    public static String getDeleteByPrimaryKey(boolean physical) {
        return physical ? DELETEBYPRIMARYKEY : DELETEBYPRIMARYKEY + PHYSICAL;
    }
}
