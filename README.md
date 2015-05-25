#kunter-generator

生成基于MyBatis的DAO、SQLMap、EO和mybatis-config-*.xml

为了区别工具生成和开发者添加的内容DAO和SQLMap生成的内容都会保存为当前包的base包下，剥离工具生成的代码和开发者的代码为了随时可以替换工具生成代码而不对开发者的代码产生影响

配置文件目录：/src/main/resources
  jdbc.properties：数据库连接参数，DB为数据库类型，目前只支持Oracle和MySQL，设置为MYSQL就会读取MySQL相关的数据库信息

  config.properties：model：模块名，例如 base；package：基础包名，例如 com.kunter；table：表名称，如果为%则读取所有base_开头的表，如果生成单表则表名去除模块名称；target：输出目录，例如 target/ 当前kunter-generator下的target/

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
