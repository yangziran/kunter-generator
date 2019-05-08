# kunter-generator

[Git@OSC](https://git.oschina.net/nature/kunter-generator.git)
[GitHub](https://github.com/angelsinklowcn/kunter-generator.git)

## 因服务器问题，Maven私库无法提供服务，如需下载kunter-base包请私信我

## 特别声明：因为采用了12c特有分页语法，本工具Oracle暂时不支持Oracle 12c以下版本

本工具参考MyBatis官方generator设计而成，参考版本为（1.3.2）。具有生成项目基础代码、根据数据库生成Excel格式的设计文档、根据Excel格式生成创建数据库脚本功能，
为了方便项目随时更换底层，生成的基础代码独立目录，不建议对生成的代码进行修改；目前设计支持Oracle、MySQL、PostgreSQL

## 添加Service层代码生成，老的代码生成器不支持Service生成，调整了生成代码结构、整合了BaseDAO和BaseService，代码运行GeneratorService会生成整套新代码
BaseDAO和BaseService、BaseServiceImpl Jar：
仓库：http://maven.kunter.cn/content/groups/public/
<dependency>
  <groupId>cn.kunter</groupId>
  <artifactId>kunter-base</artifactId>
  <version>1.0.0</version>
</dependency>

## 更新日志

- 2019-5-7 添加生成getSelectByPrimaryKeyForUpdate方法，处理MySQL的DATETIME类型，调整SMALLINT类型在Java中映射为Integer，通过Excel生成Java或者SQL的时候过滤表结构模板
- 2018-6-6 升级部分依赖包，修正已过时方法的调用，优化代码
- 2017-11-24 ignore增加IDEA的忽略文件，对超长表名的处理及简化后Sheet名称重复问题，使用WorkbookFactory创建Workbook对象，去除采用文件名后缀判断Excel类型的操作
- 2016-7-18 修复生成的Example文件中currentSize属性get方法名称的问题
- 2016-7-14 修复因升级MySQL Connector Java版本升级造成的获取字段备注中文乱码问题
- 2016-4-28 调整代码，共通dao和service引用jar包
- 2016-4-21 调整项目包结构，添加Service层代码生成
- 2016-4-20 修正Example文件生成的格式，修正Page计算逻辑，修改文件生成日期为当前日期，Example文件中添加由当前页和当前页显示条数计算当前页起始条数，添加分页对象Page的生成
- 2015-12-21 添加JDBC连接参数，修复生成Excel一览页面表名称链接问题，修复Excel生成SQL类型读取错误
- 2015-12-18 修复PostgreSQL读取不到表列表的BUG
- 2015-12-8 添加表的别名，为了自定义联合查询可以方便使用生成的SQL片段，别名规则：表名存在“_”分隔符额取每段首字母，不存在分隔符则截取表名前三位，不满前三位的使用表全称。
- 2015-9-21 合并 loocao 推送的 非逻辑删除生成insertSelective方法时,加上_physical的问题
- 2015-9-11 修复MyBatisConfig和CreateTableSQL在分模块生成文件的错误；修改类名生成逻辑，支持不同模块下相同表名
- 2015-9-10 修改代码生成逻辑，变更代码生成方式支持跨模块生成
- 2015-9-2 修改BaseDAO和BaseXML方法名称，在不需要逻辑操作的时候将物理操作的_physical后缀去除
- 2015-8-28 修复Excel数据源读取不出数据的错误，修复Excel数据源长度读取问题，修复insertListSelective、insertListSelective_physical方法的SQL错误
- 2015-8-21 修改实体生成实现Serializable接口
- 2015-7-30 修复一些Bug，删除分支Dev，因为一些分支在Dev上修改还没同步到Master上面，一些通过Master代码生成会出现相应错误
- 2015-7-2 调整生成策略，数据逻辑操作可配置
- 2015-6-30 修复Sonar扫描出的阻断性BUG

## 基础代码生成

生成文件列表：
- Page *
- BaseService *
- BaseServiceImpl *
- Service *
- ServiceImpl *
- BaseDAO ^
- DAO
- BaseMapper
- Mapper
- EO(实体)
- EOExample(实体对应查询条件)
- mybatis-config-*.xml
- CreateTableSQL-*.sql(从EXCEL设计文档生成指定数据库的建表语句)
- 表结构一览.xlsx(从数据库生成EXCEL设计文档)

##### 标*的表示新的代码生成文件，标^的表示两套生成的不一致不兼容

配置说明：
####1. 配置文件目录：/src/main/resources
> 1.1 jdbc.properties
>>  [a] SourceType：数据源类型，可选择（DB、EXCEL）

>>  [b] path.dictionary：数据字典目录，设置数据源类型为EXCEL时必须设值，支持中文目录 ** 注意路径，必须为双斜杠或者反斜杠 **

>>  [c] DB：数据库类型，必须设值，可选择（ORACLE、MYSQL、POSTGRESQL）

>>  [d] DB.xx：数据库连接属性，数据库类型相关连接属性，设置DB类型必须设值

> 1.2 config.properties
>>  [a] model：是否按模块生成，值：true|false；如果设置为true，则通过“_”截取表名称第一个作为模块化包名称

>>  [b] logical：是否逻辑操作，值：true|false;配置需要逻辑操作需要库表中存在字段：delete_flag、create_date、create_user_id、update_date、update_user_id，正常数据delete_flag值为“0”，逻辑删除将delete_flag的值变更为“1”，其他四个字段也相应的由系统控制

>>  [c] package：基础包名，所有包前缀，例：cn.kunter

>>  [d] table：表名称，支持通配符“%”，多表名通过“,”分割 例：%|base_%|base_%,admin_account
>>    ** 数据源类型为EXCEL，则参数无效 建议使用EXCEL的时候分模块保存设计文档 **

>>  [e] target：输出目录，可以为绝对目录或者相对目录，例：target/ 当前kunter-generator下的target/

>>>  根据以上配置，模拟生成如下所述文件：
>>>    * BaseDAO：cn.kunter.base.dao.base
>>>    * DAO：cn.kunter.base.dao
>>>    * BaseMapper：cn/kunter/base/xml/base
>>>    * Mapper：cn/kunter/base/xml
>>>    * EO：cn.kunter.base.eo
>>>    * EOExample：cn.kunter.base.eo
>>>    * mybatis-config-base.xml：指定的target目录下
>>>    * 如果target参数直接指定的开发项目，如果需要，手动将BaseMapper和Mapper挪到/src/main/resources

>>>  新的生成器，模拟生成如下所述文件：
>>>    * Page：cn.kunter.common.eo
>>>    * BaseService：cn.kunter.common.service
>>>    * BastServiceImpl：cn.kunter.common.service.impl
>>>    * Service：cn.kunter.base.service
>>>    * ServiceImpl：cn.kunter.base.service.impl
>>>    * BaseDAO：cn.kunter.common.dao
>>>    * DAO：cn.kunter.base.dao
>>>    * BaseMapper：cn/kunter/base/xml/base
>>>    * Mapper：cn/kunter/base/xml
>>>    * EO：cn.kunter.base.eo
>>>    * EOExample：cn.kunter.base.eo
>>>    * mybatis-config-base.xml：指定的target目录下
>>>    * 如果target参数直接指定的开发项目，如果需要，手动将BaseMapper和Mapper挪到/src/main/resources

### Main
> cn/kunter/common/generator/main/Generator.java

> cn/kunter/common/generator/main/GeneratorService.java
>> 两套代码DAO不兼容,老版本的生成的DAO无法适用生成的Service

### 单文件生成
> cn/kunter/common/generator/make/Make*.java

### DAO方法列表
* int countByExample(<?>Example example);
* int countByExample_physical(<?>Example example);
* List<?> selectByExample(<?>Example example);
* List<?> selectByExample_physical(<?>Example example);
* int insert(<?> record);
* int insert_physical(<?> record);
* int insertList(List<?> record);
* int insertList_physical(List<?> record);
* int insertSelective(<?> record);
* int insertSelective_physical(<?> record);
* int insertListSelective(List<?> record);
* int insertListSelective_physical(List<?> record);
* int updateByExample(@Param("record") <?> record, @Param("example") <?>Example example);
* int updateByExample_physical(@Param("record") <?> record, @Param("example") <?>Example example);
* int updateByExampleSelective(@Param("record") <?> record, @Param("example") <?>Example example);
* int updateByExampleSelective_physical(@Param("record") <?> record, @Param("example") <?>Example example);
* int deleteByExample(<?>Example example);
* int deleteByExample_physical(<?>Example example);
* <?> selectByPrimaryKey(Map<String, Object> map);
* <?> selectByPrimaryKeyForUpdate(Map<String, Object> map);
* <?> selectByPrimaryKey_physical(Map<String, Object> map);
* <?> selectByPrimaryKeyForUpdate_physical(Map<String, Object> map);
* int updateByPrimaryKey(<?> record);
* int updateByPrimaryKey_physical(<?> record);
* int updateByPrimaryKeySelective(<?> record);
* int updateByPrimaryKeySelective_physical(<?> record);
* int deleteByPrimaryKey(Map<String, Object> map);
* int deleteByPrimaryKey_physical(Map<String, Object> map);

### Service方法列表
* int countByExample(<?>Example example);
* int countByExample_physical(<?>Example example);
* List<?> selectByExample(<?>Example example);
* List<?> selectByExample_physical(<?>Example example);
* Page selectPageByExample(<?>Example example);
* Page selectPageByExample_physical(<?>Example example);
* int insert(<?> record);
* int insert_physical(<?> record);
* int insertList(List<?> record);
* int insertList_physical(List<?> record);
* int insertSelective(<?> record);
* int insertSelective_physical(<?> record);
* int insertListSelective(List<?> record);
* int insertListSelective_physical(List<?> record);
* int updateByExample(<?> record, <?>Example example);
* int updateByExample_physical(<?> record, <?>Example example);
* int updateByExampleSelective(<?> record, <?>Example example);
* int updateByExampleSelective_physical(<?> record, <?>Example example);
* int deleteByExample(<?>Example example);
* int deleteByExample_physical(<?>Example example);
* <?> selectByPrimaryKey(Map<String, Object> map);
* <?> selectByPrimaryKey_physical(Map<String, Object> map);
* int updateByPrimaryKey(<?> record);
* int updateByPrimaryKey_physical(<?> record);
* int updateByPrimaryKeySelective(<?> record);
* int updateByPrimaryKeySelective_physical(<?> record);
* int deleteByPrimaryKey(Map<String, Object> map);
* int deleteByPrimaryKey_physical(Map<String, Object> map);

> <?>为对应实体，ByPrimaryKey的方法有主键时生成，_physical包含表中所有数据，无_physical的包含删除标识未标识删除的数据

### 示例

        <?>Example example = new <?>Example();
        example.or().andxxEqualTo(XXX);
        example.or().andxxEqualTo(XXX);
        example.or().andxxEqualTo(XXX);

        List<?> list = <?>Dao.selectByExample(example);

#####或

        <?>Example example = new <?>Example();
        <?>Example.Criteria criteria = example.createCriteria();
        criteria.andxxEqualTo(XXX);
        criteria.andxxEqualTo(XXX);
        criteria.andxxEqualTo(XXX);

        List<?> list = <?>Dao.selectByExample(example);

# 技术交流
* 邮箱：nature@kunter.cn‍
* QQ讨论群：[325980480](http://jq.qq.com/?_wv=1027&k=TrLNcX)

# 开源赞助

![Git@OSC](http://git.oschina.net/uploads/images/2015/0608/230108_2f43d66e_6133.png "开源赞助我(支付宝)")