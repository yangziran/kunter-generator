---
name: kunter-generator-assistant
description: Kunter Generator 企业级脚手架使用指南，指导 Agent 如何在业务项目中集成与运行该生成器。
---

# Kunter Generator 智能助手规范

当您在一个基于 `kunter-generator` 构建的业务项目中进行代码生成、扩展或维护时，您 **必须严格遵守以下约定**。

## 1. 核心架构认知
`kunter-generator` 是一个强约束的企业级代码生成器。它的输出产物遵循极其严格的分层：
- **Eo (Entity Object)**：对应数据库表的持久层实体，由 `EntityGenerator` 强制覆盖生成。
- **Vo (View Object)**：前端入参视图对象。
- **Dto (Data Transfer Object)**：后端出参响应对象。
- **Base 基类体系**：所有的 `Eo` / `Vo` / `Dto` 均自动继承相应的 `BaseEo` / `BaseVo` / `BaseDto`，且子类自动生成 `@SuperBuilder` 与 `@EqualsAndHashCode(callSuper = true)`。

**警告**：如果用户让你修改 `Entity` 实体类中的字段，**绝对不要去手动修改 Java 代码**！请去修改数据库表结构，或者修改 Excel 模板配置，然后重新运行 Generator。所有基础模型代码都是被保护且会被强制覆盖的！

## 2. 插件执行方式
在宿主项目（业务项目）中运行生成器，只需执行以下命令：
```bash
mvn clean kunter-generator:all
```
*前提条件*：业务项目的 `pom.xml` 中必须配置了该插件，并且在项目根目录（或由 `<configurationFile>` 指定的路径）存放了合法的 `generatorConfig.properties`。

## 3. 防覆盖机制与开发原则 (Override Policy)
不要担心生成器会毁掉您的业务逻辑。在底层实现上，代码生成器执行两套不同的覆写策略：
- **模型类 (Eo / Vo / Dto)**：`override = true`。这些文件属于底层依赖，无论你手写了什么都会被无情抹除。**禁止在里面写业务逻辑**！
- **业务逻辑层 (Service / Dao / Controller / DynamicSqlSupport)**：`override = false`（除非在 `generatorConfig.properties` 中显式指定 `file.override=true`）。这些类只会在首次时生成骨架，之后再次运行生成器时会自动跳过。

## 4. 技术栈集成约束
- **MapStructPlus**：生成的 `Vo` 和 `Dto` 已经默认附带了 `@AutoMapper(target = XxxEo.class)`。在 `Service` 层编写对象转换逻辑时，不要用传统的 `BeanUtils` 拷贝，直接注入 `io.github.linpeilie.Converter` 使用。
- **MyBatis Dynamic SQL**：不要在手写代码中尝试创建传统的 MyBatis XML 映射文件，本项目一律采用强类型的 Java Lambda 风格进行 SQL 拼接。
- **裸返回 Controller**：生成的 Controller 会返回裸对象，不要擅自将其改为包裹统一 `Result` 或 `Response` 的格式，该项目由外部切面进行统一封装处理。
- **RESTful 接口配置**：默认采用严格的 RESTful 规范（使用 `@PutMapping` 等）。如果目标项目环境限制了 HTTP 方法，可以在 `generatorConfig.properties` 中配置 `controller.restful=false`，此时代码生成器会自动将修改和删除接口降级为 `@PostMapping("/update")` 等常规 POST 接口。
- **多前缀过滤**：生成器支持在 `generatorConfig.properties` 中配置 `table.prefix.ignore=t_,sys_`，支持逗号分隔多前缀精准剔除。

## 5. 处理异常与排错
如果你在运行 `mvn kunter-generator:all` 时遇到报错，请根据以下异常类快速定位：
- `ConfigurationException`：查配置，通常是 properties 文件没配稳或路径错了。
- `DataSourceException`：查数据库或 Excel，连不上库或者表格格式被破坏。
- `CodeGenerationException`：查模板写入权限。

## 示例配置参考 (`generatorConfig.properties`)
```properties
# 数据库连接
jdbc.driverClass=com.mysql.cj.jdbc.Driver
jdbc.connectionURL=jdbc:mysql://localhost:3306/db
jdbc.userId=root
jdbc.password=root

# 全局特性开关
table.prefix.ignore=t_,sys_
swagger.enable=true
file.override=false
controller.restful=true

# 目标包名配置
target.project=src/main/java
target.package=com.example.project
```
