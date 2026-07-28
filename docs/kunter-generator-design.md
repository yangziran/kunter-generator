# Kunter Generator 现代化核心设计文档

## 1. 架构定位
Kunter Generator 从单纯的 MyBatis 辅助代码生成工具，全面升级为**企业级中后台标准脚手架生成器**。
基于 MyBatis Generator (MBG) 原生的 Java DOM 体系（如 `FullyQualifiedJavaType`）构建，确保生成的代码具有原生级别的高可靠性与排版质量。

## 2. 模型分层流转规范
在数据流转层面，严格划分职责：
- **Eo (Entity Object)**：持久层实体对象，与数据库表结构一一对应，仅在 Service 层与 Dao(Mapper) 层之间流转。
- **Vo (View Object)**：前端请求入参对象，代表前端向后端发送的数据。
- **Dto (Data Transfer Object)**：后端响应出参对象，代表后端向前端返回的视图数据，内部将隐藏密码等敏感信息。

### 2.1 基础模型继承 (Base Models)
所有模型对象默认继承对应的基类（可通过配置 `kunter-generator` 进行可选生成，或关联已存在的基类）：
- `Eo` extends `BaseEo`
- `Vo` extends `BaseVo`
- `Dto` extends `BaseDto`

**约束红线**：
1. 所有基类及子类均需实现 `java.io.Serializable` 接口。
2. **绝对杜绝**手动生成或定义 `serialVersionUID`。

## 3. 技术栈大融合设计

### 3.1 全面拥抱 Lombok
生成的模型对象全面告别传统的 getter/setter 和 `BeanUtils`：
- 统一使用 `@Data`、`@NoArgsConstructor`、`@AllArgsConstructor` 注解。
- 采用 `@SuperBuilder` 替代传统的 `@Builder`，以完美支持 `Base` 体系父类属性的一键构建。
- 在子类中统一追加 `@EqualsAndHashCode(callSuper = true)`。

### 3.2 零反射的高性能对象转换 (MapStructPlus)
引入 `MapStructPlus` 彻底消灭 `Service` 层的繁琐属性复制：
- 在生成的 `Vo` 和 `Dto` 类头部自动打上 `@AutoMapper(target = XxxEo.class)` 注解。
- 在 `ServiceImpl` 中通过注入 `io.github.linpeilie.Converter`，天然实现 `Vo -> Eo` 和 `Eo -> Dto` 的极速转换。

### 3.3 强类型安全查询 (MyBatis Dynamic SQL)
彻底摒弃 XML 文件与脆弱的 `<if>` 标签拼接：
- Dao/Mapper 层的生成将基于 MyBatis Dynamic SQL 的官方模式，生成强类型的 `SelectStatementProvider` 与 `DynamicSqlSupport`。
- 业务层查询将采用原生的 Lambda 链式调用封装，杜绝任何 SQL 注入可能并获得极致的编译期检查。

## 4. 文档注释与编码规范
- 类和方法必须具有标准的 JavaDoc 注释，且格式紧凑，如 `@author` 和类描述之间**无空行**。
- 表字段的注释强制采用单行文档注释格式：`/** 注释内容 */`。
