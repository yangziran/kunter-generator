# kunter-generator

[Git@OSC](http://git.oschina.net/nature/kunter-generator)
[GitHub](https://github.com/angelsinklowcn/kunter-generator)

## 特别声明：因为采用了12c特有分页语法，本工具Oracle暂时不支持Oracle 12c一下版本

本工具参考MyBatis官方generator设计而成，参考版本为（1.3.2）。具有生成项目基础代码、根据数据库生成Excel格式的设计文档、根据Excel格式生成创建数据库脚本功能，
为了方便项目随时更换底层，生成的基础代码独立目录，不建议对生成的代码进行修改；目前设计支持Oracle、MySQL、PostgreSQL

*暂未实现功能：根据数据库生成Excel、根据Excel生成SQL脚本以及PostgreSQL支持*

## 基础代码生成

生成文件列表：
- BaseDAO
- DAO
- BaseMapper
- Mapper
- EO(实体)
- EOExample(实体对应查询条件)
- mybatis-config-*.xml

配置说明：
####1. 配置文件目录：/src/main/resources
> 1.1 jdbc.properties
>>  [a] SourceType：数据源类型，可选择（DB、EXCEL）

>>  [b] path.dictionary：数据字典目录，设置数据源类型为EXCEL时必须设值，支持中文目录 ** 注意路径，必须为双斜杠或者反斜杠 **

>>  [c] DB：数据库类型，设置数据源类型为DB时必须设值，可选择（ORACLE、MYSQL、POSTGRESQL）

>>  [d] DB.xx：数据库连接属性，数据库类型相关连接属性，设置DB类型必须设值

> 1.2 config.properties
>>  [a] model：模块名称，默认表前缀，例：base

>>  [b] package：基础包名，所有包前缀，例：com.kunter

>>  [c] table：表名称，支持通配符 ** 数据源类型为EXCEL，则参数无效 建议使用EXCEL的时候分模块保存设计文档 **

>>  [d] target：输出目录，可以为绝对目录或者相对目录，例：target/ 当前kunter-generator下的target/

>>>  根据以上配置，模拟生成如下所述文件：
>>>    * BaseDAO：com.kunter.base.dao.base
>>>    * DAO：com.kunter.base.dao
>>>    * BaseMapper：com/kunter/base/xml/base
>>>    * Mapper：com/kunter/base/xml
>>>    * EO：com.kunter.base.eo
>>>    * EOExample：com.kunter.base.eo
>>>    * mybatis-config-base.xml：指定的target目录下
>>>    * 如果target参数直接指定的开发项目，如果需要，手动将BaseMapper和Mapper挪到/src/main/resources

### Main
> org/generator/main/Generator.java

### 单文件生成
> org/generator/make/Make*.java

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
* 邮箱：yangzirancn@163.com
* QQ讨论群：[325980480](http://jq.qq.com/?_wv=1027&k=TrLNcX)

# 开源赞助

![Git@OSC](http://git.oschina.net/uploads/images/2015/0608/230108_2f43d66e_6133.png "开源赞助我(支付宝)")
