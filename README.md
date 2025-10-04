# My Finances

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

一个基于 Spring MVC 的个人财务管理系统，提供消费记录、账号管理、日记等功能的 Java Web 应用。

## 项目简介

My-Finances 是一个完整的 Spring MVC Web 应用骨架项目，集成了 Spring Security 安全框架、JdbcTemplate 数据访问、SiteMesh 页面装饰等企业级开发技术。项目采用传统的 MVC 三层架构，适合作为学习 Spring 框架或快速开发个人管理系统的基础模板。

## 主要特性

- **Spring MVC 框架**: 采用 Spring MVC 3.2 作为核心框架
- **安全认证**: 集成 Spring Security 3.2，支持基于角色的访问控制（RBAC）
- **数据访问**: 使用 Spring JdbcTemplate 进行数据库操作
- **页面装饰**: 使用 SiteMesh 2.4 统一页面布局
- **性能优化**:
  - 静态资源 GZIP 压缩（JS/CSS/图片）
  - 静态文件浏览器缓存控制
  - JSP 空白行自动去除
- **前端框架**: Bootstrap 2/3 双版本支持
- **工具集成**:
  - My97DatePicker 日期选择器
  - 正则表达式测试工具
  - 字符串处理工具
  - 微信接口支持

## 技术栈

### 后端技术
- **Spring Framework 3.2.0**: IoC 容器、AOP、MVC
- **Spring Security 3.2.0**: 安全认证与授权
- **Spring JDBC**: 数据库访问
- **SiteMesh 2.4.2**: 页面装饰器
- **Jackson 1.8.1**: JSON 处理
- **Apache Commons**: DBCP 连接池、工具类库
- **SLF4J + Log4j**: 日志框架
- **AspectJ 1.7.1**: AOP 支持

### 前端技术
- **Bootstrap 2/3**: 响应式 UI 框架
- **jQuery**: JavaScript 库
- **JSP/JSTL**: 视图层技术
- **My97DatePicker**: 日期选择组件

### 数据库
- **MySQL 5.x**: 关系型数据库

### 构建工具
- **传统 Web 项目结构**（非 Maven/Gradle）
- **WAR 包部署方式**

## 系统架构

### 目录结构

```
my-finances/
├── src/                          # 源代码目录
│   ├── com/rootls/
│   │   ├── common/              # 通用基础类
│   │   │   ├── BaseController.java      # 控制器基类
│   │   │   ├── BaseRespository.java     # 数据访问基类
│   │   │   ├── Page.java                # 分页组件
│   │   │   ├── InerCache.java           # 内部缓存
│   │   │   └── Config.java              # 配置监听器
│   │   ├── crud/                # CRUD 模块
│   │   │   ├── account/         # 账号管理
│   │   │   ├── finance/         # 财务管理
│   │   │   ├── regex/           # 正则表达式
│   │   │   ├── model/           # 数据模型
│   │   │   └── mapper/          # 数据映射
│   │   ├── user/                # 用户模块
│   │   │   ├── User.java                # 用户实体
│   │   │   ├── AuthController.java      # 认证控制器
│   │   │   ├── UserRepository.java      # 用户数据访问
│   │   │   ├── CustomUserDetailsService.java  # 自定义用户服务
│   │   │   └── MyPasswordEncoder.java   # 密码加密器
│   │   ├── utils/               # 工具类
│   │   ├── helper/              # 辅助类
│   │   ├── other/               # 其他功能
│   │   ├── weixin/              # 微信接口
│   │   └── test/                # 测试类
│   ├── applicationContext-root.xml      # Spring 根配置
│   ├── spring-security.xml             # Spring Security 配置
│   ├── dataSource.properties           # 数据源配置
│   ├── config.properties               # 应用配置
│   └── tk-filters.properties           # 过滤器配置
├── WebRoot/                     # Web 根目录
│   ├── WEB-INF/
│   │   ├── view/                # JSP 视图文件
│   │   │   ├── home.jsp         # 首页
│   │   │   ├── login.jsp        # 登录页
│   │   │   ├── finance/         # 财务管理视图
│   │   │   ├── account/         # 账号管理视图
│   │   │   ├── regex/           # 正则工具视图
│   │   │   ├── diary/           # 日记管理视图
│   │   │   ├── menu/            # 菜单管理视图
│   │   │   └── common/          # 通用组件（分页等）
│   │   ├── decorators/          # SiteMesh 装饰器
│   │   │   └── main.jsp         # 主装饰器模板
│   │   ├── lib/                 # 依赖 JAR 包（56个）
│   │   ├── web.xml              # Web 应用配置
│   │   ├── mvc-servlet.xml      # Spring MVC 配置
│   │   └── decorators.xml       # SiteMesh 配置
│   ├── resources/               # 静态资源
│   │   ├── bootstrap2/          # Bootstrap 2
│   │   ├── bootstrap3/          # Bootstrap 3
│   │   ├── js/                  # JavaScript 文件
│   │   ├── css/                 # 样式文件
│   │   └── My97DatePicker/      # 日期选择器
│   └── index.jsp                # 入口页面
├── sql/
│   └── db.sql                   # 数据库初始化脚本
├── LICENSE                      # Apache 2.0 许可证
└── README.md                    # 项目说明文档
```

### 数据库设计

项目包含 7 个核心数据表：

1. **daytip** - 日消费汇总表
2. **daytipDetail** - 日消费明细表
3. **account** - 账号信息表
4. **user** - 用户表（支持 Spring Security 认证）
5. **regextip** - 正则表达式存储表
6. **menu** - 系统菜单表
7. **diary** - 日记表

## 快速开始

### 环境要求

- **JDK**: 1.6 或更高版本
- **MySQL**: 5.x 或更高版本
- **Tomcat**: 7.x 或更高版本（或其他兼容 Servlet 2.5 的容器）
- **IDE**: IntelliJ IDEA / Eclipse（可选）

### 安装步骤

#### 1. 克隆项目

```bash
git clone https://github.com/luowei/my-finances.git
cd my-finances
```

#### 2. 创建数据库

```bash
# 登录 MySQL
mysql -u root -p

# 执行初始化脚本
source sql/db.sql
```

数据库脚本会自动创建 `db_finance` 数据库及所有表结构，并初始化默认数据：
- 默认管理员账号: `admin` / 密码: `admin` (加密后存储)
- 默认菜单数据
- 示例日记数据

#### 3. 配置数据源

编辑 `src/dataSource.properties` 文件，修改数据库连接信息：

```properties
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/db_finance
jdbc.username=root
jdbc.password=your_password
```

**重要提示**：
- 本地开发环境使用上述配置
- 部署到云平台（如百度云 BAE）需要修改为对应的数据库地址
- 例如 BAE: `jdbc:mysql://sqld.duapp.com:4050/mBdnxDHLrxXOcdhZNeWs`

#### 4. 部署应用

##### 方式一：IDE 运行（推荐开发调试）

**IntelliJ IDEA:**
1. File → Open → 选择项目目录
2. 配置 Tomcat 服务器
3. 部署 Web 应用（Artifact）
4. 启动服务器

**Eclipse:**
1. File → Import → Existing Projects into Workspace
2. 配置 Server Runtime
3. Add and Remove → 添加项目到 Tomcat
4. 启动服务器

##### 方式二：WAR 包部署（推荐生产环境）

```bash
# 打包项目为 WAR 文件（需要先配置打包工具）
# 将 my-finances.war 复制到 Tomcat webapps 目录
cp my-finances.war $TOMCAT_HOME/webapps/

# 启动 Tomcat
$TOMCAT_HOME/bin/startup.sh  # Linux/Mac
$TOMCAT_HOME/bin/startup.bat # Windows
```

#### 5. 访问应用

启动成功后，在浏览器中访问：

```
http://localhost:8080/my-finances
```

默认登录账号：
- 用户名: `admin`
- 密码: `admin`

## 功能模块

### 1. 首页
- **URL**: `http://localhost:8080/my-finances`
- **功能**: 系统首页，展示系统概览信息

### 2. 消费管理
- **消费汇总**: `http://localhost:8080/my-finances/finance/list`
  - 查看、新增、编辑、删除日消费记录
  - 按日期、类型筛选
  - 分页展示

- **消费明细**: `http://localhost:8080/my-finances/finance/list?tableName=daytipDetail`
  - 详细消费记录管理
  - 支持批量导入
  - 统计分析

### 3. 账号管理
- **URL**: `http://localhost:8080/my-finances/account/list`
- **功能**:
  - 管理各网站账号密码
  - 密码加密存储
  - 快速检索

### 4. 正则表达式工具
- **URL**: `http://localhost:8080/my-finances/regex/list`
- **功能**:
  - 正则表达式测试
  - 常用正则收藏
  - Base64 编码存储

### 5. 日记管理
- **URL**: `http://localhost:8080/my-finances/diary/list`
- **功能**:
  - 日记编写与管理
  - 按日期检索
  - 富文本支持

### 6. 其他工具
- **字符串处理**: `http://localhost:8080/my-finances/other/toTools`
  - 常用字符串转换工具
  - 编码解码
  - 格式化工具

### 7. 系统管理
- **清除缓存**: `http://localhost:8080/my-finances/cache/clear`
  - 清除内部缓存
  - 无需认证即可访问

## 核心配置说明

### Spring Security 配置

位于 `src/spring-security.xml`，主要配置：

```xml
<!-- 访问控制规则 -->
<intercept-url pattern="/user/login" access="permitAll"/>
<intercept-url pattern="/**" access="hasRole('ROLE_ADMIN')" />

<!-- 自定义登录页面 -->
<form-login login-page="/user/login"
            default-target-url="/home"/>

<!-- 密码加密器 -->
<password-encoder ref="passwordEncoder"/>
```

**安全特性**：
- 基于角色的访问控制（ROLE_ADMIN）
- 自定义密码加密（MyPasswordEncoder）
- 会话管理
- 登录/登出处理
- CSRF 保护（可选开启）

### Spring MVC 配置

位于 `WebRoot/WEB-INF/mvc-servlet.xml`：

```xml
<!-- 视图解析器 -->
<bean id="viewResolver" class="org.springframework.web.servlet.view.UrlBasedViewResolver">
    <property name="prefix" value="/WEB-INF/view/"/>
    <property name="suffix" value=".jsp"/>
</bean>

<!-- 文件上传 -->
<bean id="multipartResolver"
      class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>

<!-- 异常处理 -->
<bean id="exceptionResolver" class="com.rootls.helper.ExceptionHandler"/>
```

### 性能优化配置

**GZIP 压缩** (`web.xml`):
```xml
<filter>
    <filter-name>GZIPFilter</filter-name>
    <filter-class>com.tacitknowledge.filters.gzipfilter.GZIPFilter</filter-class>
</filter>
<!-- 压缩 *.js, *.css, *.png -->
```

**静态资源缓存**:
```xml
<filter>
    <filter-name>CacheFilter</filter-name>
    <filter-class>com.tacitknowledge.filters.cache.CacheHeaderFilter</filter-class>
</filter>
<!-- 缓存 *.jsp, *.gif, *.jpg, *.png, *.css -->
```

## 开发指南

### 添加新功能模块

#### 1. 创建数据表

在 `sql/db.sql` 中添加表结构：

```sql
CREATE TABLE `your_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
```

#### 2. 创建实体类

在 `src/com/rootls/crud/model/` 创建实体：

```java
package com.rootls.crud.model;

public class YourEntity {
    private Integer id;
    private String fieldName;
    // getters and setters
}
```

#### 3. 创建数据访问层

继承 `BaseRespository`:

```java
package com.rootls.crud.your_module;

import com.rootls.common.BaseRespository;
import org.springframework.stereotype.Repository;

@Repository
public class YourRepository extends BaseRespository<YourEntity> {
    // 自定义查询方法
}
```

#### 4. 创建控制器

继承 `BaseController`:

```java
package com.rootls.crud.your_module;

import com.rootls.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/your_module")
public class YourController extends BaseController {

    @Autowired
    private YourRepository repository;

    @RequestMapping("/list")
    public String list(Model model) {
        // 实现逻辑
        return "your_module/list";
    }
}
```

#### 5. 创建视图

在 `WebRoot/WEB-INF/view/your_module/` 创建 JSP 文件。

#### 6. 添加菜单

在数据库 `menu` 表中插入菜单项：

```sql
INSERT INTO menu(menuName, menuUrl, describtion, parentId, sort)
VALUES ('您的模块', '/your_module/list', '模块描述', 2, 10);
```

### 分页功能使用

项目内置了分页组件 `com.rootls.common.Page`:

```java
@RequestMapping("/list")
public String list(@RequestParam(defaultValue = "1") int pageNumber, Model model) {
    Page<YourEntity> page = repository.findAll(pageNumber);
    addPageInfo(model, page, "/your_module/list");
    return "your_module/list";
}
```

在 JSP 中引入分页组件：

```jsp
<%@ include file="../common/pagination.jsp" %>
```

### 自定义工具类

项目提供了丰富的工具类 (`com.rootls.utils`):

- **BASE64**: Base64 编解码
- **SecurityUtil**: 安全工具
- **JaxbUtil**: XML 处理
- **CompressUtil**: 压缩工具
- **ClassUtil**: 反射工具

## 常见问题

### Q1: 启动时出现 "Access denied for user" 错误？

**解决方案**: 检查 `src/dataSource.properties` 中的数据库用户名和密码是否正确。

### Q2: 登录后无法访问功能页面，提示 "Unauthorized"？

**解决方案**:
1. 检查用户角色是否为 `ROLE_ADMIN`
2. 查看 `spring-security.xml` 中的访问控制配置
3. 清除浏览器 Cookie 和缓存后重新登录

### Q3: 页面样式错乱？

**解决方案**:
1. 检查静态资源路径是否正确
2. 确认 `web.xml` 中的 `mvc:default-servlet-handler` 已启用
3. 清除浏览器缓存

### Q4: 如何修改默认管理员密码？

**方案一**（推荐）：登录系统后通过用户管理功能修改

**方案二**：直接修改数据库
```java
// 使用 MyPasswordEncoder 生成加密密码
String encodedPassword = new MyPasswordEncoder().encode("new_password");
// 更新到数据库 user 表
```

### Q5: 部署到云平台时需要注意什么？

**重要配置**：
1. 修改 `dataSource.properties` 数据库连接
2. 检查云平台的 Servlet 容器版本兼容性
3. 确认静态资源访问路径
4. 配置日志输出路径

## 项目演示

- **运行效果截图**: [查看截图](https://raw.github.com/luowei/my-finances/master/doc/img/main.png)
- **视频讲解**: [在线观看](http://www.tudou.com/programs/view/yu3ngrW7N_A)

## 资源链接

- **GitHub 仓库**: [https://github.com/luowei/my-finances](https://github.com/luowei/my-finances)
- **作者博客**: [http://blog.163.com/luowei505050@126](http://blog.163.com/luowei505050@126)
- **技术论坛**: [http://rootls.com](http://rootls.com)

## 技术支持

如有问题或建议，欢迎通过以下方式联系：

- 提交 GitHub Issue
- 访问技术论坛 [rootls.com](http://rootls.com)
- 邮箱: luowei505050@126.com

## 许可证

本项目采用 [Apache License 2.0](LICENSE) 开源许可证。

## 更新日志

- **2014-04-30**: 添加 Spring Security 验证方式及日记功能
- **2014-03**: 添加 JS/CSS 压缩优化
- **初始版本**: 搭建基础 Spring MVC 框架

---

**注意事项**：
- 本项目仅供学习和个人使用
- 生产环境部署前请做好安全加固
- 定期备份数据库数据
- 建议使用 HTTPS 协议保护数据传输安全

**开发工具推荐**：
- 使用 [IntelliJ IDEA](https://www.jetbrains.com/idea/) 进行开发
- 使用 [Postman](https://www.postman.com/) 测试 API
- 使用 [Navicat](https://www.navicat.com/) 管理数据库
- 使用 [Prose.io](http://prose.io) 编辑 Markdown 文档

---

> 如果觉得这个项目对你有帮助，欢迎 Star ⭐️
