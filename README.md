# Kunter Generator

---

**kunter-generator** 是一款企业级高可用的 MyBatis 代码脚手架生成工具。它旨在深度融合前沿后端技术，帮助研发团队瞬间构建出符合规范、优雅且极高性能的底层代码。

## 🌟 核心愿景与架构演进

当前项目主干分支 (`dynamic-sql`) 已经脱胎换骨，从一个基础的代码生成工具，升级为**高度约束与解放双手的开发引擎**。它最大的亮点在于实现了“代码”与“数据库”之间的**双向解析生成链路**：

1. **“代码优先 (Excel 驱动)”模式**：
   - 输入：整理好的 Excel 数据字典。
   - 输出：全套 Java 代码模型（Controller、Service、Dao、Entity、Vo、Dto） + 数据库 `schema.sql` 建表脚本。
2. **“库优先 (DB 驱动)”模式**：
   - 输入：MySQL 等结构化关系型数据库。
   - 输出：全套 Java 代码模型 + 逆向生成的全量 Excel 格式数据字典（`表结构一览.xlsx`），极大地便利了接手老项目时的文档补充工作。

### 技术栈全面革新
- **全面拥抱 MyBatis Dynamic SQL**：摒弃传统 XML 的 `<if>` 条件拼接，通过生成强类型的 `DynamicSqlSupport` 和 Provider，支持原生的 Java Lambda 安全链式查询。
- **严格的实体与职责分层**：
  - **Eo** (Entity Object)：持久层专属实体，安全收敛。
  - **Vo** (View Object)：入参传输视图对象。
  - **Dto** (Data Transfer Object)：出参响应对象。
- **MapStructPlus 深度集成**：生成出的 Vo 和 Dto 自带 `@AutoMapper` 映射注解，结合注入 `Converter`，无需编写任何映射接口与 `BeanUtils` 拷贝逻辑。
- **Lombok 最佳实践**：原生内置 `@SuperBuilder` 构建器体系，天然支持 `BaseEo/BaseVo/BaseDto` 的继承与公共属性追溯。

## 🚀 快速开始 (Quick Start)

我们提供了一个演示工程 `kunter-generator-demo` 供您直接体验。您可以选择两种方式生成代码。

### 方式一：通过 Main 方法直启（适合调试开发）
在 `kunter-generator-demo` 模块中，找到 `CoreRunnerDemo.java` 并直接运行其 `main` 方法，代码将生成在 `target/generated-sources/` 目录下。

### 方式二：通过 Maven Plugin 触发（适合项目集成）
在 `kunter-generator-demo` 模块下执行：
```bash
mvn clean kunter-generator:all
```
*提示：Kunter 已经为您打包了 `kunter-generator-maven-plugin`，您可以将其引入到任何其他业务模块中，实现 Maven 编译周期的自动生成。*

## ⚙️ 进阶配置速查

在执行代码生成前，您可以在 `generatorConfig.properties` 中配置极其丰富的开关特性：

```properties
# 核心数据源选择（可选 mysql, oracle, postgresql, sqlserver, excel）
sourceType=excel
# 当数据源为 excel 时生效
excel.filePath=../docs/表结构一览.xlsm

# 【核心特性】前缀过滤。支持逗号分隔多前缀精准剔除（如剔除 t_ 和 sys_）
table.prefix.ignore=t_,sys_

# 【核心特性】生成 RESTful 规范控制层
# 默认为 true。如果您公司的安全网关/WAF 拦截了 PUT 和 DELETE 等方法，
# 请将其设置为 false，生成器将自动降级为 POST 方法（如 @PostMapping("/update")）。
controller.restful=true

# 【核心特性】覆盖保护策略
# 如果为 false，对于已存在的文件（如手写了扩展逻辑的 Service/Controller）生成器将跳过写入，
# 仅对 Dto、Vo、Eo、Dao 等基础模型进行强制更新覆盖。
file.override=false

# 目标包名配置
target.project=src/main/java
target.package=cn.kunter.example
```

## 📦 依赖引入与配置指南

当您的项目使用 `kunter-generator` 生成代码后，为了让生成的代码正常编译运行，您必须在业务项目中按照以下顺序和配置引入对应的依赖。

### 1. 业务环境依赖 (Maven 示例)

```xml
<dependencies>
    <!-- MyBatis Dynamic SQL 核心依赖 -->
    <dependency>
        <groupId>org.mybatis.dynamic-sql</groupId>
        <artifactId>mybatis-dynamic-sql</artifactId>
        <version>1.5.0</version> <!-- 建议使用最新版本 -->
    </dependency>

    <!-- MapStructPlus Spring Boot Starter (必须) -->
    <dependency>
        <groupId>io.github.linpeilie</groupId>
        <artifactId>mapstruct-plus-spring-boot-starter</artifactId>
        <version>1.5.1</version>
    </dependency>

    <!-- Lombok (必须，建议1.18.30以上) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### 2. 编译插件顺序配置 (核心关键)

因为 Lombok 和 MapStructPlus 均使用了 JSR 269 编译期注解处理器 (Annotation Processor)，必须通过配置 `maven-compiler-plugin` 来保证 **先让 Lombok 生成 getter/setter -> MapStructPlus 根据注解生成 Mapper 接口 -> MapStruct 原生引擎生成最终实现类**。

请务必在 `<build><plugins>` 节点下配置：

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <encoding>UTF-8</encoding>
        <annotationProcessorPaths>
            <!-- 1. Lombok 优先执行 -->
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.30</version>
            </path>
            <!-- 2. MapStructPlus 拦截注解并生成 Mapper 接口 -->
            <path>
                <groupId>io.github.linpeilie</groupId>
                <artifactId>mapstruct-plus-processor</artifactId>
                <version>1.5.1</version>
            </path>
            <!-- 3. Lombok与MapStruct绑定器 -->
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok-mapstruct-binding</artifactId>
                <version>0.2.0</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

## 联系我们
---
- Email：mail@yangziran.com
- QQ群：325980480

## License
---
[Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt)
