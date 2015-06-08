# kunter-generator

## 特别声明：因为采用了12c特有分页语法，本工具Oracle暂时不支持Oracle 12c一下版本

本工具参考MyBatis官方generator设计而成，参考版本为（1.3.2）。具有生成项目基础代码、根据数据库生成Excel格式的设计文档、根据Excel格式生成创建数据库脚本功能，
为了方便项目随时更换底层，生成的基础代码独立目录，不建议对生成的代码进行修改；目前设计支持Oracle、MySQL、PostgreSQL

*暂未实现功能：根据数据库生成Excel、根据Excel生成SQL脚本以及PostgreSQL支持*

## 基础代码生成

1.生成文件列表：
- BaseDAO
- DAO
- BaseMapper
- Mapper
- EO(实体)
- EOExample(实体对应查询条件)
- mybatis-config-*.xml

配置说明：
1. 配置文件目录：/src/main/resources
  1.1 jdbc.properties：数据库连接参数，DB为数据库类型，目前只支持Oracle和MySQL，设置为MYSQL就会读取MySQL相关的数据库信息
  1.2 config.properties：model：模块名，例如 base；package：基础包名，例如 com.kunter；table：表名称，如果为%则读取所有base_开头的表，如果生成单表则表名去除模块名称；target：输出目录，例如 target/ 当前kunter-generator下的target/
1. item
  * item 1
  * item 2
根据以上配置生成的文件结构如下（需要手动copy到开发项目下）：

  DAO:com.kunter.base.dao

  BaseDAO：com.kunter.base.dao.base

  SQLMap:com/kunter/base/xml

  BaseSQLMap：com/kunter/base/xml/base

  EO:com.kunter.base.eo

  EOExample:com.kunter.base.eo

  mybatis-config-base.xml:根目录

Main:org/generator/main/Generator.java

单文件生成：org/generator/make包下面


生成的单表DAO方法及相关SQL：

    /**
     * 根据条件统计表中数据数量 未删除【删除标识=0】
     * @param example AccountInfoExample
     * @return int 数据条数
     */
    int countByExample(AccountInfoExample example);

    /**
     * 根据条件统计表中数据数量 所有数据
     * @param example AccountInfoExample
     * @return int 数据条数
     */
    int countByExample_physical(AccountInfoExample example);

    /**
     * 根据条件查询表中数据 未删除【删除标识=0】
     * @param example AccountInfoExample
     * @return List<AccountInfo> 数据集合
     */
    List<AccountInfo> selectByExample(AccountInfoExample example);

    /**
     * 根据条件查询表中数据 所有数据
     * @param example AccountInfoExample
     * @return List<AccountInfo> 数据集合
     */
    List<AccountInfo> selectByExample_physical(AccountInfoExample example);

    /**
     * 往表中插入一条数据 系统字段由系统处理
     * @param record AccountInfo
     * @return int 结果数量
     */
    int insert(AccountInfo record);

    /**
     * 往表中插入一条数据 系统字段需要输入
     * @param record AccountInfo
     * @return int 结果数量
     */
    int insert_physical(AccountInfo record);

    /**
     * 往表中批量插入数据 系统字段由系统处理
     * @param record List<AccountInfo>
     * @return int 结果数量
     */
    int insertList(List<AccountInfo> record);

    /**
     * 往表中批量插入数据 系统字段需要输入
     * @param record List<AccountInfo>
     * @return int 结果数量
     */
    int insertList_physical(List<AccountInfo> record);

    /**
     * 往表中插入一条数据 字段为空不插入 系统字段由系统处理
     * @param record AccountInfo
     * @return int 结果数量
     */
    int insertSelective(AccountInfo record);

    /**
     * 往表中插入一条数据 字段为空不插入 系统字段需要输入
     * @param record AccountInfo
     * @return int 结果数量
     */
    int insertSelective_physical(AccountInfo record);

    /**
     * 往表中批量插入数据 字段为空不插入 系统字段由系统处理
     * @param record List<AccountInfo>
     * @return int 结果数量
     */
    int insertListSelective(List<AccountInfo> record);

    /**
     * 往表中批量插入数据 字段为空不插入 系统字段需要输入
     * @param record List<AccountInfo>
     * @return int 结果数量
     */
    int insertListSelective_physical(List<AccountInfo> record);

    /**
     * 根据条件修改数据 未删除【删除标识=0】
     * @param record AccountInfo
     * @param example AccountInfoExample
     * @return int 结果数量
     */
    int updateByExample(@Param("record") AccountInfo record, @Param("example") AccountInfoExample example);

    /**
     * 根据条件修改数据 所有数据
     * @param record AccountInfo
     * @param example AccountInfoExample
     * @return int 结果数量
     */
    int updateByExample_physical(@Param("record") AccountInfo record, @Param("example") AccountInfoExample example);

    /**
     * 根据条件修改数据 字段为空不修改 未删除【删除标识=0】
     * @param record AccountInfo
     * @param example AccountInfoExample
     * @return int 结果数量
     */
    int updateByExampleSelective(@Param("record") AccountInfo record, @Param("example") AccountInfoExample example);

    /**
     * 根据条件修改数据 字段为空不修改 所有数据
     * @param record AccountInfo
     * @param example AccountInfoExample
     * @return int 结果数量
     */
    int updateByExampleSelective_physical(
        @Param("record") AccountInfo record,
        @Param("example") AccountInfoExample example);

    /**
     * 根据条件删除数据 逻辑删除 将【删除标识=1】
     * @param example AccountInfoExample
     * @return int 结果数量
     */
    int deleteByExample(AccountInfoExample example);

    /**
     * 根据条件删除数据 物理删除
     * @param example AccountInfoExample
     * @return int 结果数量
     */
    int deleteByExample_physical(AccountInfoExample example);

    /**
     * 根据主键查询数据 未删除【删除标识=0】
     * @param map Map<String, Object>
     * @return AccountInfo 数据对象
     */
    AccountInfo selectByPrimaryKey(Map<String, Object> map);

    /**
     * 根据主键查询数据 所有数据
     * @param map Map<String, Object>
     * @return AccountInfo 数据对象
     */
    AccountInfo selectByPrimaryKey_physical(Map<String, Object> map);

    /**
     * 根据主键修改数据 未删除【删除标识=0】
     * @param record AccountInfo
     * @return int 结果数量
     */
    int updateByPrimaryKey(AccountInfo record);

    /**
     * 根据主键修改数据 所有数据
     * @param record AccountInfo
     * @return int 结果数量
     */
    int updateByPrimaryKey_physical(AccountInfo record);

    /**
     * 根据主键修改数据 字段为空不修改 未删除【删除标识=0】
     * @param record AccountInfo
     * @return int 结果数量
     */
    int updateByPrimaryKeySelective(AccountInfo record);

    /**
     * 根据主键修改数据 字段为空不修改 所有数据
     * @param record AccountInfo
     * @return int 结果数量
     */
    int updateByPrimaryKeySelective_physical(AccountInfo record);

    /**
     * 根据主键删除数据 逻辑删除 将【删除标识=1】
     * @param map Map<String, Object>
     * @return int 结果数量
     */
    int deleteByPrimaryKey(Map<String, Object> map);

    /**
     * 根据主键删除数据 物理删除
     * @param map Map<String, Object>
     * @return int 结果数量
     */
    int deleteByPrimaryKey_physical(Map<String, Object> map);




EOExample：查询条件封装类，使用如下：

        AccountInfoExample example = new AccountInfoExample();
        // 账号
        example.or().anduserAccountEqualTo(username);
        // 邮件
        example.or().anduserMailEqualTo(username);
        // 手机
        example.or().anduserMobileEqualTo(username);

        // 根据用户名查询账号信息是否存在
        List<AccountInfo> accountInfoList = accountInfoDao.selectByExample(example);

或者：

        AccountInfoExample example = new AccountInfoExample();
        AccountInfoExample.Criteria criteria = example.createCriteria();
        criteria.anduserAccountEqualTo(username);
        criteria.anduserMailEqualTo(username);
        criteria.anduserMobileEqualTo(username);

        // 根据用户名查询账号信息是否存在
        List<AccountInfo> accountInfoList = accountInfoDao.selectByExample(example);