---
name: kunter-generator-usage
description: 使用 Kunter Generator 工具从 Excel 或数据库表结构生成 Spring Boot Java 代码与 SQL DDL 的指南。
---

# Kunter Generator 使用指南

此技能说明了如何在目标项目中使用 `kunter-generator` 工具来快速脚手架化代码（包括 Entity、Dao、Service、Controller、DDL 以及 Excel 数据字典）。

## 适用场景
当用户提出以下要求时，请使用此技能：
1. 基于已有的数据库表结构或 Excel 数据字典生成 Java 样板代码（Eo, Vo, Dto, Dao, Service, Controller）。
2. 从已有的数据库表结构逆向生成 Excel 数据字典。
3. 在连接了 MySQL、PostgreSQL、Oracle 或 SQL Server 的 Spring Boot 项目中快速搭建新模块。

## 前置条件
1. 目标项目必须是一个 Maven 项目。
2. 用户需要提供数据库连接信息，或者提供一个包含数据字典的 Excel 文件。

## 步骤 1：配置 Maven 插件
在目标项目的 `pom.xml` 文件的 `<build><plugins>` 节点下添加以下插件：

```xml
<plugin>
    <groupId>cn.kunter</groupId>
    <artifactId>kunter-generator-maven-plugin</artifactId>
    <version>3.0.0-SNAPSHOT</version> <!-- 请使用匹配的实际版本 -->
    <configuration>
        <!-- 指定配置文件的绝对或相对路径 -->
        <configurationFile>${project.basedir}/src/main/resources/generatorConfig.properties</configurationFile>
    </configuration>
</plugin>
```

## 步骤 2：创建配置文件
在步骤 1 中指定的路径下（例如 `src/main/resources/generatorConfig.properties`）创建配置文件。

**配置示例：**
```properties
# ==============================================================
# 数据源类型支持: EXCEL, MYSQL, POSTGRESQL, ORACLE, SQLSERVER
# ==============================================================
sourceType=MYSQL

# --- 如果是 EXCEL 模式，填写此项 ---
excel.filePath=../docs/表结构一览.xlsm

# --- 如果是 数据库 模式，填写以下信息 ---
#jdbc.driverClass=com.mysql.cj.jdbc.Driver
#jdbc.url=jdbc:mysql://127.0.0.1:3306/your_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
#jdbc.username=root
#jdbc.password=123456

# ==============================================================
# 基础生成配置
# ==============================================================
# 生成代码的基础包路径
base.package=com.example.yourproject
# 生成文件存放的目录路径 (可以是相对项目的绝对路径或相对路径)
targetProject=target/generated-sources
# 是否覆盖已有文件
file.override=true

# ==============================================================
# 控制器层 (Controller) 高级配置
# ==============================================================
# 是否开启 Swagger/Api 接口文档注解生成
swagger.enable=false
# 是否使用标准 RESTful 路由 (默认为 true)
# true: 使用 @PostMapping, @PutMapping, @DeleteMapping, @GetMapping
# false: 全部或主要使用 @PostMapping (非完全符合 RESTful 规范)
controller.restful=true
# 当 controller.restful=false 时，是否允许查询类接口 (get, list) 继续使用 @GetMapping (默认为 true)
# true: 查询类接口使用 @GetMapping, 增删改使用 @PostMapping
# false: 所有接口严格使用 @PostMapping
controller.allowGet=true

# ==============================================================
# 架构整合配置 (与 dynamic-sql-plus)
# ==============================================================
# 是否开启与 dynamic-sql-plus 的协同生成模式
# 开启后，实体类(Eo)会自动增加 @DynamicMapper 等元数据注解，
# 不再生成繁重的 DynamicSqlSupport 类，Dao 层自动降级为业务防腐层（仅生成一次，避免覆盖）。
# 需要项目主动引入 cn.kunter.dynamic:dynamic-sql-plus-spring-boot-starter
dynamic.plus.enable=false
```

## 步骤 3：运行生成器
在终端中运行以下 Maven 命令来执行代码生成：

```bash
mvn kunter-generator:generate
```

## 步骤 4：移动生成的代码文件
生成的文件将会被统一输出到 `targetProject` 所配置的目录中（例如 `target/generated-sources`）。
你需要将其中需要的 Java 源码文件（Controller, Service, Dao, Entity）移动或复制到用户项目的 `src/main/java` 实际源码目录下，以完成集成。

## 核心注意事项
- `kunter-generator` 以 `Table` 作为一切模型的核心中枢。它会读取数据源（Excel 或 数据库）将其转换为 `Table` 模型，然后统一触发所有已注册的生成器（这其中也包含 `ExcelGenerator`，意味着即便是从数据库读取的表结构，它依然能为你顺带生成一份全新的 Excel 数据字典）。
- 关于 RESTful 生成控制：当配置了 `controller.restful=false` 时，生成器默认退化为 RPC 风格（例如 `@PostMapping("/create")`），如果进一步配置了 `controller.allowGet=false`，则不论读写操作均统一使用 POST。
