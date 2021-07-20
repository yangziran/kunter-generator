# Kunter Generator

---
**kunter-generator**：一款辅助MyBatis的代码生成工具，参考mybaits-generator。可以通过读取Excel、DB等数据源来生成实体、DAO、Service
等文件，同时可以根据Excel生成建表语句以及通过DB生成数据字典等能力。目前Excel支持xls、xlsx、xlsm等格式，DB支持MySQL、PostgreSQL、Oracle等。

## 分支说明

---
当前项目存在三个分支：base、lombok、dynamic-sql。base：做为最早期的版本，简化了mybaits-generator的配置同时丰富了生成文件，并将工具生成的代码隔离；lombok：结合mybatis-plus
和lombok进行代码生成，简化了BaseDAO、BaseMapper、Mapper、BaseService、BaseServiceImpl、EOExample和mybatis-config-*.
xml的文件，单表的操作使用mybatis-plus；dynamic-sql：开发完毕之后将作为Master进行维护，采用MyBatis原生支持的Dynamic 
SQL模式，解决base分支大量的EOExample文件和lombok分支使用的mybatis-plus存在的大量硬编码字符串的问题。

**MyBatis Dynamic SQL示例**：
```
    SelectStatementProvider selectStatement = select(id, firstName, lastName, fullName)
            .from(generatedAlways)
            .where(id, isGreaterThan(3))
            .orderBy(id.descending())
            .build()
            .render(RenderingStrategies.SPRING_NAMED_PARAMETER);
```

## 联系我们

---
- Email：mail@yangziran.com
- QQ群：325980480

## License

---
[Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt)
