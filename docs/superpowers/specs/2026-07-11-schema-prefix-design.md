# Schema 前缀功能设计（v2.3.1）

日期：2026-07-11
分支：dev/v2.3.1
来源：PR #24（已关闭）提出的需求 —— 生成的 SQL 中表名带 schema 前缀

## 背景与目标

当用户连接串的默认库不是目标库时，生成的 SQL 需要 `schema.table` 形式的限定表名才能直接执行。原 PR #24 用 `tableElement.getParent().getName()` 无条件加前缀，存在两个问题：parent 节点在不同数据源下可能不是 schema；无条件改变所有用户的输出。

目标：

1. 用 `DasUtil.getSchema(tableElement)` 获取 schema 名（IntelliJ Database 插件的正规 API，无 schema 时返回空串）。
2. 做成可配置项，**默认关闭**，现有用户升级后行为不变。
3. 为新增逻辑补单元测试（项目首次引入单测）。

## 非目标

- 不做标识符引号/转义（现有裸表名同样不做，保持一致）。
- 不做「仅非默认 schema 才加前缀」的智能判断（跨 DBMS 不可靠，YAGNI）。
- 不修复 `.form` 文件与手写 GUI 代码的既有脱节。

## 设计

### 配置项

`SqlGeneratorConfigComponent.SqlGeneratorConfig` 增加字段 `boolean useSchemaPrefix`（默认 `false`），带 getter/setter，随现有 `sqlGenerator.xml` 持久化。

### UI

`SqlGeneratorConfigGUI` 构造器中按 `beautySqlFormatCheckBox` 的同一模式新增复选框，文案 "Qualify table name with schema"，放在第 1 行（formatter 复选框下方）。`createUI()` 读取配置初始化选中态，`apply()` 写回。`SqlGeneratorConfigPage.isModified()` 增加新字段的比较。

### 表名生成

- `Util.qualifyTableName(String schema, String tableName)`：纯函数，schema 为 null 或空串时返回 `tableName`，否则返回 `schema + "." + tableName`。
- `TableInfo` 新增 `getTableName(boolean useSchemaPrefix)`：开启时 `Util.qualifyTableName(DasUtil.getSchema(tableElement), tableElement.getName())`，关闭时等价于现有 `getTableName()`。
- `BaseSqlGeneratorAction.actionPerformed()`：把 `SqlGeneratorConfigComponent.getInstance(...)` 的读取提前到循环前，替换 `$TABLE_NAME$` 时传入开关。

### 改动文件

| 文件 | 改动 |
|---|---|
| `utils/Util.java` | 新增 `qualifyTableName` 纯函数 |
| `utils/TableInfo.java` | 新增 `getTableName(boolean)` 重载 |
| `action/BaseSqlGeneratorAction.java` | 配置读取提前，传入开关 |
| `configurable/SqlGeneratorConfigComponent.java` | 新增 `useSchemaPrefix` 字段 |
| `configurable/SqlGeneratorConfigGUI.java` | 新增复选框 |
| `configurable/SqlGeneratorConfigPage.java` | `isModified()` 比较新字段 |
| `src/test/java/.../UtilTest.java` | 新增（见测试计划） |
| `src/test/java/.../SqlGeneratorConfigTest.java` | 新增（见测试计划） |
| `build.gradle.kts` | 版本 2.3.0 → 2.3.1 |
| `plugin.xml` | change-notes 增加 2.3.1 条目 |

## 测试计划

测试类只依赖纯 Java，不触碰平台类，用已就绪的 JUnit 4.13.2 直接跑 `./gradlew test`：

- `UtilTest.qualifyTableName`：正常 schema → `schema.table`；空串 schema → 裸表名；null schema → 裸表名。
- `SqlGeneratorConfigTest`：`new SqlGeneratorConfig()` 的 `useSchemaPrefix` 默认为 `false`（回归保护：默认不改变现有行为）。

`TableInfo`/`Action` 涉及 `DbTable` 等平台类，无轻量集成测试手段，沿用 `./gradlew verifyPlugin` 作为平台兼容性门禁。

## 验证步骤

1. `./gradlew test` 全绿。
2. `./gradlew buildPlugin` 构建成功。
3. `./gradlew verifyPlugin` 结果 Compatible。

## 限制与已声明假设

- schema 名含特殊字符时不加引号，生成的 SQL 可能需要用户手工调整（与现有表名行为一致）。
- `DasUtil.getSchema` 对个别数据源可能返回空串，此时输出退化为裸表名 —— 安全的降级行为。
