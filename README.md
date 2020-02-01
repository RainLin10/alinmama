> 毕设-简易C2C市场 
>
> 采用的框架与技术：Spring-boot、Redis、RabbitMQ、Shiro


# 数据库设计

## Mysql

数据库设计——将用户名和密码与用户其他信息分成两张表，有什么好处？

```
面向对象方面考虑用户信息就是用户本身，用户名和密码只是登陆钥匙
性能方面考虑登陆验证的时候列较少，查询速度快。
安全性考虑防止在查询用户信息时，把密码也直接查询出来，会容易被攻击和进行恶意操作。
```

**user**

| 字段名   | 类型    | 长度 | 是否空        | 自动增长       | 主键        |
| -------- | ------- | ---- | ------------- | -------------- | ----------- |
| id       | int     | 5    | NOT NULL      | AUTO_INCREMENT | PRIMARY KEY |
| account  | varchar | 255  | NOT NULL      |                |             |
| password | varchat | 255  | NOT NULL      |                |             |
| role     | varchat | 255  | NOT NULL user |                |             |

**user_info**

| 字段名   | 类型    | 长度 | 是否空   | 自动增长       | 主键        |
| ---- | ---- | ---- | ---- | ---- | ---- |
| user_id  | int     | 5 | NOT NULL |  | PRIMARY KEY |
| nick_name | vharchar | 255 | NOT NULL |      |      |
| introduction | vharchar | 255 | | | |
| isBusiness | char | 1 | NOT NULL 否 | | |
| gender | char | 1 | NOT NULL 男 | | |
| avatar | varchar | 255 |  | | |
| register_time | bigint | 255 | NOT NULL | | |
| login_time | bigint | 255 | NOT NULL | | |
| login_times | int | 10 | NOT NULL | | |

**commodity**

| 字段名   | 类型    | 长度 | 是否空   | 自动增长       | 主键        |
| ---- | ---- | ---- | ---- | ---- | ---- |
| id | int | 10 | NOT NULL | AUTO_INCREMENT | PRIMARY KEY |
| user_id  | int     | 5 | NOT NULL |  |  |
| name | varchar | 255 | NOT NULL |      |      |
| introduction | varchar | 255 | NOT NULL | | |
| price | decimal | 7，2 | NOT NULL | | |
| carousel | varchar |  | NOT NULL | | |
| state | int | 1 | NOT NULL 0 | | |
| nick_name | varchar | 255 | NOT NULL | | |

## Redis的使用

| Set                                                          |      |      |      |      |      |      |      |      |
| ------------------------------------------------------------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| key:"apply_seller"<br />value:用户id集合<br />注释:正在申请卖家资格的id集合 |      |      |      |      |      |      |      |      |
| key:"user"+用户id+"commodity_car"<br />value:商品id集合<br />注释:用户的购物车商品id集合 |      |      |      |      |      |      |      |      |
| key:"user"+用户id+"commodity"<br />value:商品id集合<br />注释:用户的订单商品id集合 |      |      |      |      |      |      |      |      |
| key:"commodity"+商品id+"user"<br />value用户id集合<br />注释:购物某商品的所有用户集合 |      |      |      |      |      |      |      |      |
|                                                              |      |      |      |      |      |      |      |      |
|                                                              |      |      |      |      |      |      |      |      |
|                                                              |      |      |      |      |      |      |      |      |
|                                                              |      |      |      |      |      |      |      |      |









# 请求设计

## 路径映射

### registry.addViewControllers

> **/to_login** -----> **login.html**
> **/index** -----> **index.html**
> **/to_register** -----> **register.html**
> **/unAuth** -----> **unAuth.html** 
> **/admin** -----> **admin.html** 

## Controller设计

### loginController

> 登录成功进入主页或者登陆失败返回登录页
>
> **/login** -----> **login.html**/    **/**
>
> 注销后进入主页
>
> **/logout** -----> **index.html**
>
> 注册后进入登录页
>
> **/register** -----> **login.html**

### AdminController

> 进入管理员页面时查询所有用户
>
> **/admin/findAllUsers** -----> **admin.html**
>
> 在管理员页面修改用户信息
>
> **/admin/updateUserInfo** -----> **/admin/findAllUsers**

# 用户权限设计

| 角色   | 修改个人信息 |   | 客服操作 | 购物操作[买] | 商家操作[卖] | 管理员操作 |
| ------ | ---- | ---- | ----  | -------- | -------- | ---------- |
| user   | **有** |      | 无  | **有**   | 无       | 无         |
| seller | **有** |  | 无  | **有** | **有**   | 无         |
| service[客服] | **有** |   | **有** | 无 | 无 | 无 |
| admin  | **有         |      | **有** | 无       | 无       | **有**     |

```
//管理员
filterMap.put("/admin/**","roles[admin]");
//卖家
filterMap.put("/seller/**", "roles[service]");
//购物
filterMap.put("/apply/**", "roles[user,seller]");
//客服
filterMap.put("/service/**", "roles[service,admin]");
//用户操作
filterMap.put("/user/**", "roles[user,admin,seller,service]");
```


# 笔记

## 什么是starters？

以下应用程序starters是Spring Boot在`org.springframework.boot`组下提供的：

| 名称                                   | 描述                                                         |
| -------------------------------------- | ------------------------------------------------------------ |
| spring-boot-starter                    | 核心Spring Boot starter，包括自动配置支持，日志和YAML        |
| spring-boot-starter-actuator           | 生产准备的特性，用于帮我们**监控和管理应用**                 |
| spring-boot-starter-amqp               | 对”**高级消息队列协议**”的支持，通过spring-rabbit实现        |
| spring-boot-starter-aop                | 对**面向切面编程**的支持，包括spring-aop和AspectJ            |
| spring-boot-starter-batch              | 对**Spring Batch**的支持，包括HSQLDB数据库                   |
| spring-boot-starter-cloud-connectors   | 对**Spring Cloud Connectors**的支持，简化在云平台下（例如，Cloud Foundry 和Heroku）服务的连接 |
| spring-boot-starter-data-elasticsearch | 对**Elasticsearch**搜索和分析引擎的支持，包括spring-data-elasticsearch |
| spring-boot-starter-data-gemfire       | 对**GemFire**分布式数据存储的支持，包括spring-data-gemfire   |
| spring-boot-starter-data-jpa           | 对”Java持久化API”的支持，包括**spring-data-jpa**，**spring-orm**和**Hibernate** |
| spring-boot-starter-data-mongodb       | 对**MongoDB** NOSQL数据库的支持，包括spring-data-mongodb     |
| spring-boot-starter-data-rest          | 对通过REST暴露Spring Data仓库的支持，通过spring-data-rest-webmvc实现 |
| spring-boot-starter-data-solr          | 对**Apache Solr**搜索平台的支持，包括spring-data-solr        |
| spring-boot-starter-freemarker         | 对**FreeMarker**模板引擎的支持                               |
| spring-boot-starter-groovy-templates   | 对**Groovy**模板引擎的支持                                   |
| spring-boot-starter-hateoas            | 对基于**HATEOAS**的RESTful服务的支持，通过spring-hateoas实现 |
| spring-boot-starter-hornetq            | 对”**Java消息服务API**”的支持，通过HornetQ实现               |
| spring-boot-starter-integration        | 对普通spring-**integration**模块的支持                       |
| spring-boot-starter-jdbc               | 对**JDBC**数据库的支持                                       |
| spring-boot-starter-jersey             | 对**Jersey** RESTful Web服务框架的支持                       |
| spring-boot-starter-jta-atomikos       | 对JTA分布式事务的支持，通过**Atomikos**实现                  |
| spring-boot-starter-jta-bitronix       | 对JTA分布式事务的支持，通过**Bitronix**实现                  |
| spring-boot-starter-mail               | 对javax.**mail**的支持                                       |
| spring-boot-starter-mobile             | 对spring-**mobile**的支持                                    |
| spring-boot-starter-mustache           | 对**Mustache**模板引擎的支持                                 |
| spring-boot-starter-redis              | 对REDIS键值数据存储的支持，包括**spring-redis**              |
| spring-boot-starter-security           | 对**spring-security**的支持                                  |
| spring-boot-starter-social-facebook    | 对spring-social-**facebook**的支持                           |
| spring-boot-starter-social-linkedin    | 对spring-social-**linkedin**的支持                           |
| spring-boot-starter-social-twitter     | 对spring-social-**twitter**的支持                            |
| spring-boot-starter-test               | 对常用测试依赖的支持，包括**JUnit**, **Hamcrest**和**Mockito**，还有**spring-test**模块 |
| spring-boot-starter-thymeleaf          | 对**Thymeleaf**模板引擎的支持，包括和Spring的集成            |
| spring-boot-starter-velocity           | 对**Velocity**模板引擎的支持                                 |
| spring-boot-starter-web                | 对全栈web开发的支持， 包括**Tomcat**和**spring-webmvc**      |
| spring-boot-starter-websocket          | 对**WebSocket**开发的支持                                    |
| spring-boot-starter-ws                 | 对**Spring Web**服务的支持                                   |

## SSM中的xml文件在boot里的替代

![image-20200117163603522](D:\Project\alinmama\README.assets\image-20200117163603522.png)

等同于

![image-20200117163649310](D:\Project\alinmama\README.assets\image-20200117163649310.png)

## application.properties能配置那些东西？

```properties
Server:

server.address ---------服务器地址
server.port ---------服务器端口
server.context-parameters.[param name] ---------设置 servlet 上下文参数
server.context-path ---------应用上下文路径

Session

server.session.cookie.domain ------回话 Cookie 的域
server.session.cookie.comment Cookie ---------注释
server.session.cookie.max-age Cookie ---------最大保存时间（单位 s）
server.session.cookie.name Cookie ---------名称
server.session.cookie.timeout ---------超时时间

Tomcat

server.tomcat.accesslog.directory ----创建日志文件的目录
server.tomcat.accesslog.enabled ---------是否开启访问日志（默认：false）
server.tomcat.accesslog.pattern ---------访问日志的格式（默认common）
server.tomcat.accesslog.prefix ---------日志名前缀（默认：access_log）
server.tomcat.accesslog.suffix ---------日志名后缀（默认：.log）
server.tomcat.max-http-header-sizeHttp ---------消息头最大字节数（默认：0）
server.tomcat.uri-encoding ---------用来解码 URI 的字符编码

DataSource
spring.datasource.allow-pool-suspension 是否允许池暂停（pool suspension）。在开启池暂停后会有性能会受到一定影响，除非你 真的需要这个功能（例如在冗余的系统下），否则不要开启它。该属性只在使用 Hikari 数 据库连接池时有用。（默认值： false 。）
spring.datasource.name 数据源的名称。
spring.datasource.username 数据库的登录用户名。
spring.datasource.password 数据库的登录密码。
spring.datasource.url 数据库的 JDBC URL。
spring.datasource.jdbc-url 用来创建连接的 JDBC URL。
spring.datasource.driver-class-name JDBC 驱动的全限定类名。默认根据 URL 自动检测。
spring.datasource.pool-name 连接池名称。
spring.datasource.max-active 连接池中的最大活跃连接数。
spring.datasource.connection-timeout连接超时（单位毫秒）
spring.datasource.max-age 连接池中连接的最长寿命。
spring.datasource.max-idle 连接池中的最大空闲连接数。
spring.datasource.max-lifetime 连接池中连接的最长寿命（单位为毫秒）。
spring.datasource.max-open-prepared-statements 开启状态的 PreparedStatement 的数量上限。
spring.datasource.max-wait 连接池在等待返回连接时，最长等待多少毫秒再抛出异常。
spring.datasource.maximum-pool-size 连接池能达到的最大规模，包含空闲连接的数量和使用中的连接数量。
spring.datasource.min-evictable-idle-time-millis 一个空闲连接被空闲连接释放器（如果存在的话）优雅地释放前，最短会在连接池里停 留多少时间。
spring.datasource.min-idle 连接池里始终应该保持的最小连接数。（用于 DBCP 和 Tomcat 连接池。）
spring.datasource.minimum-idle: HikariCP 试图在连接池里维持的最小空闲连接数。
spring.datasource.alternate-username-allowed 是否允许其它用户名
spring.datasource.auto-commit 更新操作是否自动提交
spring.datasource.abandon-when-percentage-full 一个百分比形势的阈值，超过该阈值则关闭并报告被弃用的连接
spring.datasource.catalog 默认的 Catalog 名称
spring.datasource.commit-on-return 在连接归还时，连接池是否要提交挂起的事务
spring.datasource.connection-init-sql 在所有新连接创建时都会执行的 SQL 语句，该语句会在连接加入连接池前执行。
spring.datasource.connection-init-sqls 在物理连接第一次创建时执行的 SQL 语句列表。（用于 DBCP 连接池。）
spring.datasource.connection-properties.[key] 设置创建连接时使用的属性。（用于 DBCP 连接池。）
spring.datasource.continue-on-error 初始化数据库时发生错误不要终止。（默认值： false）
spring.datasource.data 指向数据（数据库操纵语言，Data Manipulation Language，DML）脚本资源的引用。
spring.datasource.data-source-class-name 用于获取连接的数据源的全限定类名。
spring.datasource.data-source-jndi 用于获取连接的数据源的 JNDI 位置。
spring.datasource.data-source-properties.[key] 设置创建数据源时使用的属性。（用于 Hikari 连接池。）
spring.datasource.db-properties 设置创建数据源时使用的属性。（用于 Tomcat 连接池。）
spring.datasource.default-auto-commit 连接上的操作是否自动提交。
spring.datasource.default-catalog 连接的默认 Catalog。
spring.datasource.default-read-only 连接的默认只读状态。
spring.datasource.default-transaction-isolation 连接的默认事务隔离级别。
spring.datasource.fair-queue 是否以 FIFO 方式返回连接。
spring.datasource.health-check-properties.[key] 设置要纳入健康检查的属性。（用于 Hikari 连接池。）
spring.datasource.idle-timeout 连接池中的连接能保持闲置状态的最长时间，单位为毫秒。（默认值： 10 。）
spring.datasource.ignore-exception-on-pre-load 初始化数据库连接池时是否要忽略连接。
spring.datasource.init-sql在连接第一次创建时运行的自定义查询。
spring.datasource.initial-size 在连接池启动时要建立的连接数。
spring.datasource.initialization-fail-fast 在连接池创建时，如果达不到最小连接数是否要抛出异常。（默认值： true 。）
spring.datasource.initialize 使用 data.sql 初始化数据库。（默认值： true 。）
spring.datasource.isolate-internal-queries 是否要隔离内部请求。（默认值： false 。）
spring.datasource.jdbc-interceptors 一个分号分隔的类名列表，这些类都扩展了 JdbcInterceptor 类。这些拦截器会插入 java.sql.Connection 对象的操作链里（用于 Tomcat 连接池）
spring.datasource.jmx-enabled 开启 JMX 支持（如果底层连接池提供该功能的话）。（默认值： false 。）
spring.datasource.jndi-name 数据源的 JNDI 位置。设置了该属性则忽略类、URL、用户名和密码属性。
spring.datasource.leak-detection-threshold 用来检测 Hikari 连接池连接泄露的阈值，单位为毫秒。
spring.datasource.log-abandoned 是否针对弃用语句或连接的应用程序代码记录下跟踪栈。用于 DBCP 连接池。（默认值： false 。）
spring.datasource.log-validation-errors 在使用 Tomcat 连接池时是否要记录验证错误。
spring.datasource.login-timeout 连接数据库的超时时间（单位为秒）。
spring.datasource.num-tests-per-eviction-run 空闲对象释放器线程（如果存在的话）每次运行时要检查的对象数。
spring.datasource.platform 在 Schema 资源（schema-${platform}.sql）里要使用的平台。（默认值： all 。）
spring.datasource.pool-prepared-statements 是否要将 Statement 放在池里。
spring.datasource.propagate-interrupt-state 对于等待连接的中断线程，是否要传播中断状态。
spring.datasource.read-only 在使用 Hikari 连接池时将数据源设置为只读。
spring.datasource.register-mbeans Hikari 连接池是否要注册 JMX MBean。
spring.datasource.remove-abandoned 被弃用的连接在到达弃用超时后是否应该被移除。
spring.datasource.remove-abandoned-timeout 连接在多少秒后应该考虑弃用。
spring.datasource.rollback-on-return 在连接归还连接池时，是否要回滚挂起的事务。
spring.datasource.schema Schema（数据定义语言，Data Definition Language，DDL）脚本资源的引用。
spring.datasource.separator SQL 初始化脚本里的语句分割符。（默认值： ; 。）
spring.datasource.sql-script-encoding SQL 脚本的编码。
spring.datasource.suspect-timeout 在记录一个疑似弃用连接前要等待多少秒。
spring.datasource.test-on-borrow 从连接池中借用连接时是否要进行测试。
spring.datasource.test-on-connect 在建立连接时是否要进行测试。
spring.datasource.test-on-return 在将连接归还到连接池时是否要进行测试。
spring.datasource.test-while-idle 在连接空闲时是否要进行测试。
spring.datasource.time-between-eviction-runs-millis 在两次空闲连接验证、弃用连接清理和空闲池大小调整之间睡眠的毫秒数。
spring.datasource.transaction-isolation 在使用 Hikari 连接池时设置默认事务隔离级别。
spring.datasource.use-disposable-connection-facade 连接是否要用一个门面（facade）封装起来，在调用了 Connection.close() 后就不能 再使用这个连接了
spring.datasource.use-equals 在比较方法名时是否使用 String.equals() 来代替 == 。
spring.datasource.use-lock 在操作连接对象时是否要加锁。
spring.datasource.validation-interval 执行连接验证的间隔时间，单位为毫秒。
spring.datasource.validation-query 在连接池里的连接返回给调用者或连接池时，要执行的验证 SQL 查询。
spring.datasource.validation-query-timeout 在连接验证查询执行失败前等待的超时时间，单位为秒。
spring.datasource.validation-timeout 在连接验证失败前等待的超时时间，单位为秒。（用于 Hikari 连接池。）
spring.datasource.validator-class-name 可选验证器类的全限定类名，用于执行测试查询。
spring.datasource.xa.data-source-class-name XA 数据源的全限定类名。
spring.datasource.xa.properties 要传递给 XA 数据源的属性。

Data

spring.data.jpa.repositories.enabled 开启 JPA 仓库（默认：true）
spring.data.mongodb.authentication-database 身份认证数据库名
spring.data.mongodb.database 数据库名
spring.data.mongodb.field-naming-strategy 要使用的 FieldNamingStrategy 的全限定名。
spring.data.mongodb.grid.fs.database GridFS 数据库名称
spring.data.mongodb.host MongoDB 服务器地址
spring.data.mongodb.username MongoDB 账号
spring.data.mongodb.passwordMongoDB 密码
spring.data.mongodb.port 端口号
spring.data.mongodb.repositories.enabled 开启 Mongo 仓库（默认值：true）
spring.data.mongodb.uri Mongo 数据库 URI。设置了该属性后就主机和端口号会被忽略。（默认值： mongodb:// localhost/test）
spring.data.rest.base-path 用于发布仓库资源的基本路径
spring.data.rest.default-page-size 分页数据的默认页大小（默认：20）
spring.data.rest.limit-param-name用于标识一次返回多少记录的 URL 查询字符串参数名。（默认值： size ）
spring.data.rest.max-page-sieze: 最大分页大小（默认：1000）
spring.data.rest.page-param-name URL 查询字符串参数的名称，用来标识返回哪一页。（默认值： page ）
spring.data.rest.return-body-on-create 在创建实体后是否返回一个响应体（默认：false）
spring.data.rest.return-body-on-update 在更新实体后是否返回一个响应体（默认：false）
spring.data.rest.sort-param-name URL 查询字符串参数的名称，用来表示结果排序的方向（默认：name）
spring.data.solr.host Solr 的主机地址。如果设置了 zk-host 则忽略该属性。（默认值： http://127.0.0.1: 8983/solr ）
spring.data.solr.repositories.enabled 开启 solr 仓库（默认：true）
spring.data.solr.zk-host zk 主机地址，格式为 “主机 - 端口”

Cache

spring.cache.cache-names 如果底层缓存管理器支持缓存名的话，可以在这里指定要创建的缓存名列表，用逗号分 隔。通常这会禁用运行时创建其他额外缓存的能力。
spring.cache.config 用来初始化 EhCache 的配置文件位置
spring.cache.guava.spec 用来创建缓存 Spec
spring.cache.hazelcast.config 用来初始化 Hazeleast 的配置文件位置
spring.cache.infinispan.config 用来初始化 Infinispan 配置文件位置
spring.cache.jcache.config用来初始化缓存管理器的配置文件的位置，配置文件依赖于底层的缓存实现
spring.cache.jcache.provider CachingProvider 实现的全限定类名，用来获取 JSR-107 兼容的缓存管理器，仅在 Classpath 里有不只一个 JSR-107 实现时才需要这个属性。
spring.cache.type 缓存类型，默认根据环境自动检测

multipart

multipart.enabled 开启上传支持（默认：true）
multipart.file-size-threshold: 大于该值的文件会被写到磁盘上
multipart.location 上传文件存放位置
multipart.max-file-size最大文件大小
multipart.max-request-size 最大请求大小

Jsp-servelt

server.jsp-servelt.class-name针对 jsp 使用的 Servlet 类名（默认：org.apache.jasper.servlet.JspServlet）
server.jsp-servlet.registered JspServelt 是否要注册到内嵌的 Servlet 容器里（默认 true）
server.jsp-servlet.init-parameters[param name] 设置 Jsp Servlet 初始化参数
server.servlet-path主分发器 Servlet 的路径（默认：/）

View

spring.view.prefix Spring MVC 视图前缀。
spring.view.suffixSpring MVC 视图后缀。

Resources

spring.resources.add-mappings 开启默认资源处理。（默认值： true 。）
spring.resources.cache-period 资源处理器对资源的缓存周期，单位为秒。
spring.resources.chain.cache 对资源链开启缓存。（默认值： true 。）
spring.resources.chain.enabled 开启 Spring 资源处理链。（默认关闭的，除非至少开启了一个策略。）
spring.resources.chain.html-com.yylnb.test.application-cache 开启 HTML5 应用程序缓存证明重写。（默认值： false 。）
spring.resources.chain.strategy.content.enabled 开启内容版本策略。（默认值： false 。）
spring.resources.chain.strategy.content.paths 要运用于版本策略的模式列表，用逗号分隔。（默认值： [/**] 。）
spring.resources.chain.strategy.fixed.enabled开启固定版本策略。（默认值： false 。）
spring.resources.chain.strategy.fixed.paths要运用于固定版本策略的模式列表，用逗号分隔。
spring.resources.chain.strategy.fixed.version 用于固定版本策略的版本字符串。
spring.resources.static-locations 静态资源位置。默认为 classpath: [/META-INF/resources/, /resources/, /static/, /public/] 加上 context:/（Servlet 上下文的根目录）。

Redis

spring.redis.database 连接工厂使用的数据库索引。（默认值： 0 。）
spring.redis.host Redis 服务器主机地址。（默认值： localhost 。）
spring.redis.passwordRedis 服务器的登录密码。
spring.redis.pool.max-active连接池在指定时间里能分配的最大连接数。负数表示无限制。（默认值： 8 。）
spring.redis.pool.max-idle 连接池里的最大空闲连接数。负数表示空闲连接数可以是无限大。（默认值： 8 。）
spring.redis.pool.max-wait当连接池被耗尽时，分配连接的请求应该在抛出异常前被阻塞多长时间（单位为秒）。负 数表示一直阻塞。（默认值： -1 。）
spring.redis.pool.min-idle 连接池里要维持的最小空闲连接数。该属性只有在设置为正数时才有效。（默认值： 0 。）
spring.redis.port Redis 服务器端口。（默认值： 6379 。）
spring.redis.sentinel.master Redis 服务器的名字。
spring.redis.sentinel.nodes形如“主机: 端口”配对的列表，用逗号分隔。
spring.redis.timeout 连接超时时间，单位为秒。（默认值： 0 。）

MVC

spring.mvc.async.request-timeout 异步请求处理超时前的等待时间（单位为毫秒）。如果没有设置该属性，则使用底层实现 的默认超时时间，比如，Tomcat 上使用 Servlet 3 时超时时间为 10 秒。
spring.mvc.date-format 要使用的日期格式（比如 dd/MM/yyyy ）。
spring.mvc.favicon.enabled 开启 favicon.ico 的解析。（默认值： true 。）
spring.mvc.ignore-default-model-on-redirect 在重定向的场景下，是否要忽略“默认”模型对象的内容。（默认值： true 。）
spring.mvc.locale 要使用的地域配置。
spring.mvc.message-codes-resolver-format 消息代码格式（ PREFIX_ERROR_CODE 、 POSTFIX_ERROR_CODE ）。
spring.mvc.view.prefix Spring MVC 视图前缀。
spring.mvc.view.suffix Spring MVC 视图后缀。

Mongodb

spring.mongodb.embedded.features要开启的特性列表，用逗号分隔。
spring.mongodb.embedded.version 要使用的 Mongo 版本。（默认值： 2.6.10 。）

FreeMarker

spring.freemarker.allow-request-override HttpServletRequest 的属性是否允许覆盖（隐藏）控制器生成的同名模型属性。
spring.freemarker.allow-session-override HttpSession 的属性是否允许覆盖（隐藏）控制器生成的同名模型属性。
spring.freemarker.cache 开启模板缓存。
spring.freemarker.charset 模板编码。
spring.freemarker.check-template-location 检查模板位置是否存在。
spring.freemarker.content-type Content-Type 的值。
spring.freemarker.enabled 开启 FreeMarker 的 MVC 视图解析。
spring.freemarker.expose-request-attributes 在模型合并到模板前，是否要把所有的请求属性添加到模型里。
spring.freemarker.expose-session-attributes 在模型合并到模板前，是否要把所有的 HttpSession 属性添加到模型里。
spring.freemarker.expose-spring-macro-helpers 是否发布供 Spring 宏程序库使用的 RequestContext ，并将命其名为 springMacro- RequestContext
spring.freemarker.prefer-file-system-access 加载模板时优先通过文件系统访问。文件系统访问能够实时检测到模板变更。（默认值： true 。）
spring.freemarker.prefix 在构建 URL 时添加到视图名称前的前缀。
spring.freemarker.request-context-attribute 在所有视图里使用的 RequestContext 属性的名称。
spring.freemarker.settings 要传递给 FreeMarker 配置的各种键。
spring.freemarker.suffix 在构建 URL 时添加到视图名称后的后缀。
spring.freemarker.template-loader-path 模板路径列表，用逗号分隔。（默认值： [“classpath:/templates/”] 。）
spring.freemarker.view-names 可解析的视图名称的白名单

HTTP

spring.http.converters.preferred-json-mapper HTTP 消息转换时优先使用 JSON 映射器。
spring.http.encoding.charset HTTP 请求和响应的字符集。如果没有显式地指定 Content-Type 头，则将该属性值作为 这个头的值。（默认值： UTF-8 。）
spring.http.encoding.enabled 开启 HTTP 编码支持。（默认值： true 。）
spring.http.encoding.force 强制将 HTTP 请求和响应编码为所配置的字符集。（默认值： true 。）

Mail

spring.mail.default-encoding 默认的 MimeMessage 编码。（默认值： UTF-8 。）
spring.mail.host SMTP 服务器主机地址。
spring.mail.jndi-name会话的 JNDI 名称。设置之后，该属性的优先级要高于其他邮件设置。
spring.mail.password SMTP 服务器的登录密码。
spring.mail.port SMTP 服务器的端口号。
spring.mail.properties 附加的 JavaMail 会话属性。
spring.mail.protocol SMTP 服务器用到的协议。（默认值： smtp 。）
spring.mail.test-connection 在启动时测试邮件服务器是否可用。（默认值： false 。）
spring.mail.username SMTP 服务器的登录用户名。

Groovy

spring.groovy.template.allow-request-override HttpServletRequest 的属性是否允许覆盖（隐藏）控制器生成的同名模型属性。
spring.groovy.template.allow-session-override HttpSession 的属性是否允许覆盖（隐藏）控制器生成的同名模型属性。
spring.groovy.template.cache 开启模板缓存。
spring.groovy.template.charset 模板编码。
spring.groovy.template.check-template-location 检查模板位置是否存在。
spring.groovy.template.configuration.auto-escape 模型变量在模板里呈现时是否要做转义。（默认值： false 。）
spring.groovy.template.configuration.auto-indent 模板是否要自动呈现缩进。（默认值： false 。）
spring.groovy.template.configuration.auto-indent-string 开启自动缩进时用于缩进的字符串，可以是 SPACES ，也可以是 TAB 。（默认值： SPACES 。）
spring.groovy.template.configuration.auto-new-line 模板里是否要呈现新的空行。（默认值： false 。）
spring.groovy.template.configuration.base-template-class 模板基类。
spring.groovy.template.configuration.cache-templates 模板是否应该缓存。（默认值： true 。）
spring.groovy.template.configuration.declaration-encoding 用来写声明头的编码
spring.groovy.template.configuration.expand-empty-elements 没有正文的元素该用短形式（例如， ）还是扩展形式（例如， ）来书 写。（默认值： false）
spring.groovy.template.configuration.locale 设置模板地域。
spring.groovy.template.configuration.new-line-string 在自动空行开启后用来呈现空行的字符串。（默认为系统的 line.separator 属性值。）
spring.groovy.template.configuration.resource-loader-path Groovy 模板的路径。（默认值： classpath:/templates/ 。）
spring.groovy.template.configuration.use-double-quotes 属性是该用双引号还是单引号。（默认值： false 。）
spring.groovy.template.content-type Content-Type 的值。
spring.groovy.template.enabled 开启 Groovy 模板的 MVC 视图解析。
spring.groovy.template.expose-request-attributes 在模型合并到模板前，是否要把所有的请求属性添加到模型里。
spring.groovy.template.expose-session-attributes 在模型合并到模板前，是否要把所有的 HttpSession 属性添加到模型里。
spring.groovy.template.expose-spring-macro-helpers 是否发布供 Spring 宏程序库使用的 RequestContext ，并将其命名为 springMacro- RequestContext
spring.groovy.template.prefix 在构建 URL 时，添加到视图名称前的前缀。
spring.groovy.template.request-context-attribute 所有视图里使用的 RequestContext 属性的名称。
spring.groovy.template.resource-loader-path 模板路径（默认值： classpath:/ templates/ 。）
spring.groovy.template.suffix 在构建 URL 时，添加到视图名称后的后缀。
spring.groovy.template.view-names 可解析的视图名称白名单。

Jackson

spring.jackson.date-format 日期格式字符串（yyyy-MM-dd HH:mm:ss）或日期格式类的全限定类名。
spring.jackson.deserialization 影响 Java 对象反序列化的 Jackson on/off 特性。
spring.jackson.generator 用于生成器的 Jackson on/off 特性。
spring.jackson.joda-date-time-format Joda 日期时间格式字符串（yyyy-MM-dd HH:mm:ss）。如果没有配置，而 date-format 又配置了一个格式字符串的话，会将它作为降级配置。
spring.jackson.locale 用于格式化的地域值。
spring.jackson.mapper Jackson 的通用 on/off 特性。
spring.jackson.parser 用于解析器的 Jackson on/off 特性。
spring.jackson.property-naming-strategy Jackson 的 PropertyNamingStrategy 中的一个常量（ CAMEL_CASE_TO_LOWERCASE WITH_UNDERSCORES ）。也可以设置 PropertyNamingStrategy 的子类的全限定类名。
spring.jackson.serialization 影响 Java 对象序列化的 Jackson on/off 特性。
spring.jackson.serialization-inclusion 控制序列化时要包含哪些属性。可选择 Jackson 的 JsonInclude.Include 枚举里的某个值。
spring.jackson.time-zone 格式化日期时使用的时区。可以配置各种可识别的时区标识符，比如 America/Los_ Angeles 或者 GMT+10 。

H2

spring.h2.console.enabled 开启控制台。（默认值： false 。）
spring.h2.console.path 可以找到控制台的路径。（默认值： /h2-console

Hornetq

spring.hornetq.embedded.cluster-password 集群密码。默认在启动时随机生成。
spring.hornetq.embedded.data-directory 日志文件目录。如果关闭了持久化功能则不需要该属性。
spring.hornetq.embedded.enabled 如果有 HornetQ 服务器 API，则开启嵌入模式。（默认值： true 。）
spring.hornetq.embedded.persistent 开启持久化存储。（默认值： false 。）
spring.hornetq.embedded.queues 启动时要创建的队列列表，用逗号分隔。（默认值： [] 。）
spring.hornetq.embedded.server-id 服务器 ID。默认使用自增长计数器。（默认值： 0 。）
spring.hornetq.embedded.topics 启动时要创建的主题列表，用逗号分隔。（默认值： [] 。）
spring.hornetq.host HornetQ 的主机。（默认值： localhost 。）
spring.hornetq.mode HornetQ 的部署模式，默认为自动检测。可以显式地设置为 native 或 embedded 。
spring.hornetq.port HornetQ 的端口。（默认值： 5445 。）

Jersey

spring.jersey.filter.order Jersey 过滤器链的顺序。（默认值： 0 。）
spring.jersey.init 通过 Servlet 或过滤器传递给 Jersey 的初始化参数。
spring.jersey.type Jersey 集成类型。可以是 servlet 或者 filter 。

Jms

spring.jms.jndi-name 连接工厂的 JNDI 名字。设置了该属性，则优先于其他自动配置的连接工厂。
spring.jms.listener.acknowledge-mode 容器的应答模式（acknowledgment mode）。默认情况下，监听器使用自动应答。
spring.jms.listener.auto-startup 启动时自动启动容器。（默认值： true 。）
spring.jms.listener.concurrency 并发消费者的数量下限。
spring.jms.listener.max-concurrency 并发消费者的数量上限。
spring.jms.pub-sub-domain 如果是主题而非队列，指明默认的目的地类型是否支持 Pub/Sub。（默认值： false 。）

Jmx

spring.jmx.default-domain JMX 域名。
spring.jmx.enabled 将管理 Bean 发布到 JMX 域里。（默认值： true 。）
spring.jmx.server MBeanServer 的 Bean 名称。（默认值： mbeanServer 。）

Jpa

spring.jpa.database 要操作的目标数据库，默认自动检测。也可以通过 databasePlatform 属性进行设置。
spring.jpa.database-platform 要操作的目标数据库，默认自动检测。也可以通过 Database 枚举来设置。
spring.jpa.generate-ddl 启动时要初始化 Schema。（默认值： false 。）
spring.jpa.hibernate.ddl-auto DDL 模式（ none 、 validate 、 update 、 create 和 create-drop ）。这是 hibernate. hbm2ddl.auto 属性的一个快捷方式。在使用嵌入式数据库时，默认为 create-drop , 其他情况下默认为 none 。
spring.jpa.hibernate.naming-strategy Hibernate 命名策略的全限定类名。
spring.jpa.open-in-view 注册 OpenEntityManagerInViewInterceptor ，在请求的整个处理过程中，将一个 JPA EntityManager 绑定到线程上。（默认值： true）
spring.jpa.propertiesJPA 提供方要设置的额外原生属性。
spring.jpa.show-sql 在使用 Bitronix Transaction Manager 时打开 SQL 语句日志。（默认值： false 。）

Jta

spring.jta.allow-multiple-lrc 在使用 Bitronix Transaction Manager 时，事务管理器是否应该允许一个事务涉及多个 LRC 资源。（默认值： false）
spring.jta.asynchronous2-pc 在使用 Bitronix Transaction Manager 时，是否异步执行两阶段提交。（默认值： false 。）
spring.jta.background-recovery-interval在使用 Bitronix Transaction Manager 时，多久运行一次恢复过程，单位为分钟。（默认值： 1 ）
spring.jta.background-recovery-interval-seconds 在使用 Bitronix Transaction Manager 时，多久运行一次恢复过程，单位为秒。（默认值： 60 ）
spring.jta.current-node-only-recovery 在使用 Bitronix Transaction Manager 时，恢复是否要滤除不包含本 JVM 唯一 ID 的 XID。（默认值： true ）
spring.jta.debug-zero-resource-transaction 在使用 Bitronix Transaction Manager 时，对于没有涉及任何资源的事务，是否要跟踪并记 录它们的创建和提交调用栈。（默认值： false）
spring.jta.default-transaction-timeout 在使用 Bitronix Transaction Manager 时，默认的事务超时时间，单位为秒。（默认值： 60 。）
spring.jta.disable-jmx 在使用 Bitronix Transaction Manager 时，是否要禁止注册 JMX MBean。（默认值： false 。）
spring.jta.enabled 开启 JTA 支持。（默认值： true 。）
spring.jta.exception-analyzer 在使用 Bitronix Transaction Manager 时用到的异常分析器。设置为 null 时使用默认异常分析器，也可以设置自定义异常分析器的全限定类名。
spring.jta.filter-log-status 在使用 Bitronix Transaction Manager 时，是否只记录必要的日志。开启该参数时能减少分 段（fragment）空间用量，但调试更复杂了。（默认值： false）
spring.jta.force-batching-enabled 在使用 Bitronix Transaction Manager 时，是否批量输出至磁盘。禁用批处理会严重降低事 务管理器的吞吐量。（默认值： true 。）
spring.jta.forced-write-enabled 在使用 Bitronix Transaction Manager 时，日志是否强制写到磁盘上。在生产环境里不要设 置为 false ，因为不强制写到磁盘上无法保证完整性。（默认值： true）
spring.jta.graceful-shutdown-interval 在使用 Bitronix Transaction Manager 时，要关闭的话，事务管理器在放弃事务前最多等它 多少秒。（默认值： 60）
spring.jta.jndi-transaction-synchronization-registry-name 在使用 Bitronix Transaction Manager 时，事务同步注册表应该绑定到哪个 JNDI 下。（默认 值： java:comp/TransactionSynchronizationRegistry）
spring.jta.jndi-user-transaction-name 在使用 Bitronix Transaction Manager 时，用户事务应该绑定到哪个 JNDI 下。（默认值： java:comp/UserTransaction 。）
spring.jta.journal 在使用 Bitronix Transaction Manager 时，要用的日志名。可以是 disk 、 null 或者全限定类 名。（默认值： disk 。）

spring.jta.log-dir 事务日志目录。

spring.jta.log-part1-filename 日志分段文件 1 的名称。（默认值： btm1.tlog 。）
spring.jta.log-part2-filename 日志分段文件 2 的名称。（默认值： btm2.tlog 。）
spring.jta.max-log-size-in-mb 在使用 Bitronix Transaction Manager 时，日志分段文件的最大兆数。日志越大，事务就被 允许在未终结状态停留越长时间。但是，如果文件大小限制得太小，事务管理器在分段 满了的时候就会暂停更长时间。（默认值： 2 。）
spring.jta.resource-configuration-filename Bitronix Transaction Manager 的配置文件名。
spring.jta.server-id 唯一标识 Bitronix Transaction Manager 实例的 ID。
spring.jta.skip-corrupted-logs 是否跳过损坏的日志文件。（默认值： false 。）
spring.jta.transaction-manager-id 事务管理器的唯一标识符。
spring.jta.warn-about-zero-resource-transaction 在使用 Bitronix Transaction Manager 时，是否要对执行时没有涉及任何资源的事务作出告 警。（默认值： true 。）

Messages

spring.messages.basename 逗号分隔的基本名称列表，都遵循 ResourceBundle 的惯例。本质上这就是一个全限定 的 Classpath 位置，如果不包含包限定符（比如 org.mypackage ），就会从 Classpath 的根部开始解析。（默认值： messages 。）
spring.messages.cache-seconds 加载的资源包文件的缓存失效时间，单位为秒。在设置为 -1 时，包会永远缓存。（默认值： -1 。）
spring.messages.encoding消息包的编码。（默认值： UTF-8 。）
Mobile
spring.mobile.devicedelegatingviewresolver.enable-fallback 开启降级解析支持。（默认值： false 。）spring.mobile.devicedelegatingviewresolver.enabled 开启设备视图解析器。（默认值： false 。）spring.mobile.devicedelegatingviewresolver.mobile-prefix 添加到移动设备视图名前的前缀。（默认值： mobile/ 。）spring.mobile.devicedelegatingviewresolver.mobile-suffix 添加到移动设备视图名后的后缀。spring.mobile.devicedelegatingviewresolver.normal-prefix 添加到普通设备视图名前的前缀。spring.mobile.devicedelegatingviewresolver.normal-suffix 添加到普通设备视图名后的后缀。spring.mobile.devicedelegatingviewresolver.tablet-prefix添加到平板设备视图名前的前缀。（默认值： tablet/ 。）spring.mobile.devicedelegatingviewresolver.tablet-suffix 添加到平板设备视图名后的后缀。spring.mobile.sitepreference.enabled 开启 SitePreferenceHandler 。（默认值： true 。）

Mustache

spring.mustache.cache 开启模板缓存。
spring.mustache.charset 模板编码。
spring.mustache.check-template-location 检查模板位置是否存在。
spring.mustache.content-type Content-Type 的值。
spring.mustache.enabled 开启 Mustache 的 MVC 视图解析。
spring.mustache.prefix添加到模板名前的前缀。（默认值： classpath:/ templates/ 。）
spring.mustache.suffix 添加到模板名后的后缀。（默认值： .html 。）
spring.mustache.view-names 可解析的视图名称的白名单。

Rabbitmq

spring.rabbitmq.addresses 客户端应该连接的地址列表，用逗号分隔。
spring.rabbitmq.dynamic 创建一个 AmqpAdmin Bean。（默认值： true 。）
spring.rabbitmq.host RabbitMQ 主机地址。（默认值： localhost 。）
spring.rabbitmq.listener.acknowledge-mode 容器的应答模式。
spring.rabbitmq.listener.auto-startup 启动时自动开启容器。（默认值： true 。）
spring.rabbitmq.listener.concurrency 消费者的数量下限。
spring.rabbitmq.listener.max-concurrency 消费者的数量上限。
spring.rabbitmq.listener.prefetch 单个请求里要处理的消息数。该数值不应小于事务数（如果用到的话）。
spring.rabbitmq.listener.transaction-size 一个事务里要处理的消息数。为了保证效果，应该不大于预先获取的数量。
spring.rabbitmq.password进行身份验证的密码。
spring.rabbitmq.port RabbitMQ 端口。（默认值： 5672 。）
spring.rabbitmq.requested-heartbeat 请求心跳超时，单位为秒； 0 表示不启用心跳。
spring.rabbitmq.ssl.enabled 开启 SSL 支持。（默认值： false 。）
spring.rabbitmq.ssl.key-store 持有 SSL 证书的 KeyStore 路径。
spring.rabbitmq.ssl.key-store-password 访问 KeyStore 的密码。
spring.rabbitmq.ssl.trust-store 持有 SSL 证书的 TrustStore。
spring.rabbitmq.ssl.trust-store-password 访问 TrustStore 的密码。
spring.rabbitmq.username 进行身份验证的用户名。
spring.rabbitmq.virtual-host 在连接 RabbitMQ 时的虚拟主机。
SendGrid
spring.sendgrid.password SendGrid 密码。spring.sendgrid.proxy.host SendGrid 代理主机地址。spring.sendgrid.proxy.port SendGrid 代理端口。spring.sendgrid.username SendGrid 用户名。

Social

spring.social.auto-connection-views 针对所支持的提供方开启连接状态视图。（默认值： false 。）
spring.social.facebook.app-id 应用程序 ID。
spring.social.facebook.app-secret 应用程序的密钥。
spring.social.linkedin.app-id 应用程序 ID。
spring.social.linkedin.app-secret 应用程序的密钥。
spring.social.twitter.app-id 应用程序 ID。
spring.social.twitter.app-secret 应用程序的密钥。

Thymeleaf

spring.thymeleaf.cache开启模板缓存。（默认值： true 。）
spring.thymeleaf.check-template-location 检查模板位置是否存在。（默认值： true 。）
spring.thymeleaf.content-type Content-Type 的值。（默认值： text/html 。）
spring.thymeleaf.enabled 开启 MVC Thymeleaf 视图解析。（默认值： true 。）
spring.thymeleaf.encoding模板编码。（默认值： UTF-8 。）
spring.thymeleaf.excluded-view-names 要被排除在解析之外的视图名称列表，用逗号分隔。
spring.thymeleaf.mode 要运用于模板之上的模板模式。另见 StandardTemplate- ModeHandlers 。（默认值： HTML5 。）
spring.thymeleaf.prefix 在构建 URL 时添加到视图名称前的前缀。（默认值： classpath:/templates/ 。）
spring.thymeleaf.suffix 在构建 URL 时添加到视图名称后的后缀。（默认值： .html 。）
spring.thymeleaf.template-resolver-order Thymeleaf 模板解析器在解析器链中的顺序。默认情况下，它排在第一位。顺序从 1 开始只有在定义了额外的 TemplateResolver Bean 时才需要设置这个属性。
spring.thymeleaf.view-names 可解析的视图名称列表，用逗号分隔。

Velocity
spring.velocity.allow-request-override HttpServletRequest 的属性是否允许覆盖（隐藏）控制器生成的同名模型属性。
spring.velocity.allow-session-override HttpSession` 的属性是否允许覆盖（隐藏）控制器生成的同名模型属性。
spring.velocity.cache 开启模板缓存。
spring.velocity.charset 模板编码。
spring.velocity.check-template-location 检查模板位置是否存在。
spring.velocity.content-type Content-Type 的值。
spring.velocity.date-tool-attribute DateTool 辅助对象在视图的 Velocity 上下文里呈现的名字。
spring.velocity.enabled 开启 Velocity 的 MVC 视图解析。
spring.velocity.expose-request-attributes 在模型合并到模板前，是否要把所有的请求属性添加到模型里。
spring.velocity.expose-session-attributes 在模型合并到模板前，是否要把所有的 HttpSession 属性添加到模型里。
spring.velocity.expose-spring-macro-helpers 是否发布供 Spring 宏程序库使用的 RequestContext ，并将其名命为 springMacro- RequestContext 。
spring.velocity.number-tool-attribute NumberTool 辅助对象在视图的 Velocity 上下文里呈现的名字。
spring.velocity.prefer-file-system-access 加载模板时优先通过文件系统访问。文件系统访问能够实时检测到模板变更。（默认值： true 。）
spring.velocity.prefix在构建 URL 时添加到视图名称前的前缀。
spring.velocity.properties 额外的 Velocity 属性。
spring.velocity.request-context-attribute所有视图里使用的 Request- Context 属性的名称。
spring.velocity.resource-loader-path 模板路径。（默认值： classpath:/ templates/ 。）
spring.velocity.suffix 在构建 URL 时添加到视图名称后的后缀。
spring.velocity.toolbox-config-location Velocity Toolbox 的配置位置，比如 /WEB-INF/toolbox.xml。自动加载 Velocity Tools 工具定 义文件，将所定义的全部工具发布到指定的作用域内。
spring.velocity.view-names可解析的视图名称白名单。

其它

spring.aop.auto 添加 @EnableAspectJAutoProxy(默认：true)
spring.com.yylnb.test.application.admin.enabled开启应用程序的管理功能 (默认：false)
spring.artemis.embedded.cluster-password 集群密码。默认在启东市随机生成
spring.artemis.embedded.persistent开启持久化存储 (默认：false)
spring.autoconfigure.exclude 要排除的自动配置
```

## 日志的输出格式

springboot默认是 **SLF4J** + **Logback** 

```
  日志输出格式：
		%d表示日期时间，
		%thread表示线程名，
		%-5level：级别从左显示5个字符宽度
		%logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
		%msg：日志消息，
		%n是换行符
    -->
    %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
```

## 如何定制错误页面？

**1）、有模板引擎的情况下；error/状态码;** 【将错误页面命名为  错误状态码.html 放在模板引擎文件夹里面的 error文件夹下】，发生此状态码的错误就会来到  对应的页面；

​			我们可以使用4xx和5xx作为错误页面的文件名来匹配这种类型的所有错误，精确优先（优先寻找精确的状态码.html）；		

​			页面能获取的信息；

​				timestamp：时间戳

​				status：状态码

​				error：错误提示

​				exception：异常对象

​				message：异常消息

​				errors：JSR303数据校验的错误都在这里

2）、没有模板引擎（模板引擎找不到这个错误页面），静态资源文件夹下找；

3）、以上都没有错误页面，就是默认来到SpringBoot默认的错误提示页面；

## 拦截器、过滤器、监听器有什么区别？

## 注解版的mybatis怎么使用

![image-20200119113456855](D:\Project\alinmama\README.assets\image-20200119113456855.png)![image-20200119113512435](D:\Project\alinmama\README.assets\image-20200119113512435.png)![image-20200119113525567](D:\Project\alinmama\README.assets\image-20200119113525567.png)![image-20200119113539098](D:\Project\alinmama\README.assets\image-20200119113539098.png)

如果要使用文件配置版

```properties
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml 指定全局配置文件的位置
  mapper-locations: classpath:mybatis/mapper/*.xml  指定sql映射文件的位置
```

## Bootstrap 

```
mx-auto//元素居中
text-center//文本居中
```

## FileInput插件

### 一、引入文件

<link href="../css/bootstrap.min.css"rel="stylesheet">
<link href="../css/**fileinput.css**" media="all"rel="stylesheet" type="text/css" />
<scriptsrc="../js/jquery-2.0.3.min.js"></script>
<script src="../js/**fileinput.js**"type="text/javascript"></script>
<script src="../js/bootstrap.min.js"type="text/javascript"></script>
### 二、初始化设置

```javascript
//初始化fileinput
initFileInput();
function initFileInput() {
    $("#uploadImg").fileinput({
        language: 'zh', //设置语言
        dropZoneTitle: '可以将图片拖放到这里 …支持多文件上传',
        uploadUrl: "index.php", //上传的地址
        uploadExtraData: function(previewId, index) {   //该插件可以向您的服务器方法发送附加数据。这可以通过uploadExtraData在键值对中设置为关联数组对象来完成。所以如果你有设置uploadExtraData={id:'kv-1'}，在PHP中你可以读取这些数据$_POST['id']
            var id = $('#id').val();
            return {seriesId: id};
        },
        allowedFileExtensions: ['jpg','png'],//接收的文件后缀
        uploadAsync: true, //默认异步上传
        showUpload: true, //是否显示上传按钮
        showRemove: true, //显示移除按钮
        showPreview: true, //是否显示预览
        showCancel:true,   //是否显示文件上传取消按钮。默认为true。只有在AJAX上传过程中，才会启用和显示
        showCaption: true,//是否显示文件标题，默认为true
        browseClass: "btn btn-primary", //文件选择器/浏览按钮的CSS类。默认为btn btn-primary
        dropZoneEnabled: true,//是否显示拖拽区域
        minImageWidth: 50, //图片的最小宽度
        minImageHeight: 50,//图片的最小高度
        maxImageWidth: 1000,//图片的最大宽度
        maxImageHeight: 1000,//图片的最大高度
        maxFileSize: 1024,//单位为kb，如果为0表示不限制文件大小
        minFileCount: 1, //每次上传允许的最少文件数。如果设置为0，则表示文件数是可选的。默认为0
        maxFileCount: 0, //每次上传允许的最大文件数。如果设置为0，则表示允许的文件数是无限制的。默认为0
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",//当检测到用于预览的不可读文件类型时，将在每个预览文件缩略图中显示的图标。默认为<i class="glyphicon glyphicon-file"></i>  
        layoutTemplates:{
            actionUpload:''//去除上传预览缩略图中的上传图片
            actionZoom:'',   //去除上传预览缩略图中的查看详情预览的缩略图标
            actionDownload:'' //去除上传预览缩略图中的下载图标
            actionDelete:'', //去除上传预览的缩略图中的删除图标
        },//对象用于渲染布局的每个部分的模板配置。您可以设置以下模板来控制窗口小部件布局.eg:去除上传图标
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",//字符串，当文件数超过设置的最大计数时显示的消息 maxFileCount。默认为：选择上传的文件数（{n}）超出了允许的最大限制{m}。请重试您的上传！
    }).on('filebatchpreupload', function(event, data) { //该方法将在上传之前触发
        var id = $('#id option:selected').val();
        if(id == 0){
            return {
                message: "请选择", // 验证错误信息在上传前要显示。如果设置了这个设置，插件会在调用时自动中止上传，并将其显示为错误消息。您可以使用此属性来读取文件并执行自己的自定义验证
                data:{} // any other data to send that can be referred in `filecustomerror`
            };
        }
    });
}
//fileuploaded此事件仅针对ajax上传触发，并在每个缩略图文件上传完成后触发。此事件仅针对ajax上传并在以下情况下触发：当点击每个预览缩略图中的上传图标并且文件上传成功时，或者当你有 uploadAsync设置为true您已触发批量上传。在这种情况下，fileuploaded每一个人选择的文件被上传成功后，触发事件。
var id_str = '';
$('#uploadImg').on('fileuploaded', function(event, data, previewId, index) {
    if(typeof(data.response.id) != 'undefined'){
        id_str = id_str+data.response.id+',';
    }
});
// filebatchuploadcomplete此事件仅在ajax上传和完成同步或异步ajax批量上传后触发。
$('#uploadImg').on('filebatchuploadcomplete',function (event,files,extra) {
    if(id_str.length == 0){
        layer.msg('上传失败', {icon: 0});//弹框提示
        return false;
    }
    setTimeout(function(){ //执行延时关闭
        closeSelf();
    },1000);
});
```

### 三、Options 说明

| 属性名                   | 属性类型     | 描述说明                                                     | 默认值                                                       |
| ------------------------ | ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| language                 | String       | 多语言设置，使用时需提前引入locales文件夹下对应的语言文件，中文zh，引入语言文件必须放在fileinput.js之后 | 'en'                                                         |
| showCaption              | Boolean      | 是否显示被选文件的**简介**                                   | true                                                         |
| showBrowse               | Boolean      | 是否显示**浏览按钮**                                         | true                                                         |
| showPreview              | Boolean      | 是否显示预**览区域**                                         | true                                                         |
| showRemove               | Boolean      | 是否显示**移除按钮**                                         | true,                                                        |
| showUpload               | Boolean      | 是否显示**上传按钮**                                         | true,                                                        |
| showCancel               | Boolean      | 是否显示**取消按钮**                                         | true,                                                        |
| showClose:               | Boolean      | 是否显示**关闭按钮**                                         | true                                                         |
| showUploadedThumbs       | Boolean      |                                                              | true                                                         |
| browseOnZoneClick        | Boolean      |                                                              | false                                                        |
| autoReplace              | Boolean      | 是否自动替换当前图片，设置为true时，再次选择文件， 会将当前的文件替换掉。 | false                                                        |
| generateFileId           | Object       |                                                              | null                                                         |
| previewClass             | String       | 添加预览按钮的类属性                                         | ‘’                                                           |
| captionClass             | String       |                                                              | ‘’                                                           |
| frameClass               | String       |                                                              | 'krajee-default'                                             |
| mainClass                | String       |                                                              | 'file-caption-main'                                          |
| mainTemplate             | Object       |                                                              | null                                                         |
| purifyHtml               | Boolean      |                                                              | true                                                         |
| fileSizeGetter           | Object       |                                                              | null                                                         |
| initialCaption           | String       |                                                              | ''                                                           |
| initialPreview           | Array/Object |                                                              | []                                                           |
| initialPreviewDelimiter  | String       |                                                              | '*$$*'                                                       |
| initialPreviewAsData     | Boolean      |                                                              | false                                                        |
| initialPreviewFileType   | String       |                                                              | 'image'                                                      |
| initialPreviewConfig     | Array/Object |                                                              | []                                                           |
| initialPreviewThumbTags  | Array/Object |                                                              | []                                                           |
| previewThumbTags         | Object       |                                                              | {}                                                           |
| initialPreviewShowDelete | Boolean      |                                                              | true                                                         |
| removeFromPreviewOnError | Boolean      |                                                              | false                                                        |
| deleteUrl                | String       | 删除图片时的请求路径                                         | ''                                                           |
| deleteExtraData          | Object       | 删除图片时额外传入的参数                                     | {}                                                           |
| overwriteInitial         | Boolean      |                                                              | true                                                         |
| previewZoomButtonIcons   | Object       |                                                              | {prev: '',next: '',toggleheader: '',fullscreen: '',borderless: '',close: ''}, |
| previewZoomButtonClasses | Object       |                                                              | {prev: 'btn btn-navigate',next: 'btn btn-navigate',toggleheader: 'btn btn-default btn-header-toggle',fullscreen: 'btn btn-default',borderless: 'btn btn-default',close: 'btn btn-default'}, |
| preferIconicPreview      | Boolrean     |                                                              | false                                                        |
| preferIconicZoomPreview  | Boolrean     |                                                              | false                                                        |
| allowedPreviewTypes      | undefined    |                                                              | undefined                                                    |
| allowedPreviewMimeTypes  | Object       |                                                              | null                                                         |
| allowedFileTypes         | Object       | 接收的文件后缀，如['jpg', 'gif', 'png'],不填将不限制上传文件后缀类型 | null                                                         |
| allowedFileExtensions    | Object       |                                                              | null                                                         |
| defaultPreviewContent    | Object       |                                                              | null                                                         |
| customLayoutTags         | Object       |                                                              | {}                                                           |
| customPreviewTags        | Object       |                                                              | {}                                                           |
| previewFileIcon          | String       |                                                              | ''                                                           |
| previewFileIconClass     | String       |                                                              | 'file-other-icon'                                            |
| previewFileIconSettings  | Object       |                                                              | {}                                                           |
| previewFileExtSettings   | Object       |                                                              | {}                                                           |
| buttonLabelClass         | String       |                                                              | 'hidden-xs'                                                  |
| browseIcon               | String       |                                                              | ' '                                                          |
| browseClass              | String       |                                                              | 'btn btn-primary'                                            |
| removeIcon               | String       |                                                              | ''                                                           |
| removeClass              | String       |                                                              | 'btn btn-default'                                            |
| cancelIcon               | String       |                                                              | ''                                                           |
| cancelClass              | String       |                                                              | 'btn btn-default'                                            |
| uploadIcon               | String       |                                                              | ''                                                           |
| uploadClass              | String       |                                                              | 'btn btn-default'                                            |
| uploadUrl                | String       | 上传文件路径                                                 | null                                                         |
| uploadAsync              | boolean      | 是否为异步上传                                               | true                                                         |
| uploadExtraData          |              | 上传文件时额外传递的参数设置                                 | {}                                                           |
| zoomModalHeight          | number       |                                                              | 480                                                          |
| minImageWidth            | String       | 图片的最小宽度                                               | null                                                         |
| minImageHeight           | String       | 图片的最小高度                                               | null                                                         |
| maxImageWidth            | String       | 图片的最大宽度                                               | null                                                         |
| maxImageHeight           | String       | 图片的最大高度                                               | null                                                         |
| resizeImage              | boolean      |                                                              | false                                                        |
| resizePreference         | String       |                                                              | 'width'                                                      |
| resizeQuality            | number       |                                                              | 0.92                                                         |
| resizeDefaultImageType   | String       |                                                              | 'image/jpeg'                                                 |
| minFileSize              | number       | 单位为kb，上传文件的最小大小值                               | 0                                                            |
| maxFileSize              | number       | 单位为kb，如果为0表示不限制文件大小                          | 0                                                            |
| resizeDefaultImageType   | number       |                                                              | 25600(25MB)                                                  |
| minFileCount             | number       | 表示同时最小上传的文件个数                                   | 0                                                            |
| maxFileCount             | number       | 表示允许同时上传的最大文件个数                               | 0                                                            |
| validateInitialCount     | boolean      |                                                              | false                                                        |
| msgValidationErrorClass  | String       |                                                              | 'text-danger'                                                |
| msgValidationErrorIcon   | String       |                                                              | ' '                                                          |
| msgErrorClass            | String       |                                                              | 'file-error-message'                                         |
| progressThumbClass       | String       |                                                              | "progress-bar progress-bar-success progress-bar-striped active" |
| rogressClass             | String       |                                                              | "progress-bar progress-bar-success progress-bar-striped active" |
| progressCompleteClass    | String       |                                                              | "progress-bar progress-bar-success"                          |
| progressErrorClass       | String       |                                                              | "progress-bar progress-bar-danger"                           |
| progressUploadThreshold  | number       |                                                              | 99                                                           |
| previewFileType          | String       | 预览文件类型,内置['image', 'html', 'text', 'video', 'audio', 'flash', 'object',‘other‘]等格式 | 'image'                                                      |
| elCaptionContainer       | String       |                                                              | null                                                         |
| elCaptionText            | String       | 设置标题栏提示信息                                           | null                                                         |
| elPreviewContainer       | String       |                                                              | null                                                         |
| elPreviewImage           | String       |                                                              | null                                                         |
| elPreviewStatus          | String       |                                                              | null                                                         |
| elErrorContainer         | String       |                                                              | null                                                         |
| errorCloseButton         | String       |                                                              | '<span class="close kv-error-close">×</span>'                |
| slugCallback             | function     | 选择后未上传前 回调方法                                      | null                                                         |
| dropZoneEnabled          | boolean      | 是否显示拖拽区域                                             | true                                                         |
| dropZoneTitleClass       | String       | 拖拽区域类属性设置                                           | 'file-drop-zone-title'                                       |
| fileActionSettings       | Object       | 设置预览图片的显示样式                                       | {showRemove: true,showUpload: false,showZoom: true,showDrag: true,removeIcon: '',removeClass: 'btn btn-xs btn-default',removeTitle: 'Remove file',uploadIcon: '',uploadClass: 'btn btn-xs btn-default',uploadTitle: 'Upload file',zoomIcon: '',zoomClass: 'btn btn-xs btn-default',zoomTitle: 'View Details',dragIcon: '',dragClass: 'text-info',dragTitle: 'Move / Rearrange',dragSettings: {},indicatorNew: '',indicatorSuccess: '',indicatorError: '',indicatorLoading: '',indicatorNewTitle: 'Not uploaded yet',indicatorSuccessTitle: 'Uploaded',indicatorErrorTitle: 'Upload Error',indicatorLoadingTitle: 'Uploading ...'} |
| otherActionButtons       | String       |                                                              | ''                                                           |
| textEncoding             | String       | 编码设置                                                     | 'UTF-8'                                                      |
| ajaxSettings             | Object       |                                                              | {}                                                           |
| ajaxDeleteSettings       | Object       |                                                              | {}                                                           |
| showAjaxErrorDetails     | boolean      |                                                              | true                                                         |

### 四、提示说明设置

| 属性名                  | 默认值                                                       | 中文                                                         |
| ----------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| fileSingle              | file                                                         | 文件                                                         |
| filePlural              | files                                                        | 个文件                                                       |
| browseLabel             | Browse &hellip                                               | 选择 …                                                       |
| removeLabel             | Remove                                                       | 移除                                                         |
| removeTitle             | Clear selected files                                         | 清除选中文件                                                 |
| cancelLabel             | Cancel                                                       | 取消                                                         |
| cancelTitle             | Abort ongoing upload                                         | 取消进行中的上传                                             |
| uploadLabel             | Upload                                                       | 上传                                                         |
| uploadTitle             | Upload selected files                                        | 上传选中文件                                                 |
| msgNo                   | No                                                           | 没有                                                         |
| msgNoFilesSelected      | No files selected                                            | “”                                                           |
| msgCancelled            | Cancelled                                                    | 取消                                                         |
| msgZoomModalHeading     | Detailed Preview                                             | 详细预览                                                     |
| msgSizeTooSmall         | File "{name}" (**{size} KB**) is too small and must be larger than **{minSize} KB**. | File "{name}" (**{size} KB**) is too small and must be larger than **{minSize} KB**. |
| msgSizeTooLarge         | File "{name}" (**{size} KB**) exceeds maximum allowed upload size of **{maxSize} KB**. | 文件 "{name}" (**{size} KB**) 超过了允许大小 **{maxSize} KB**. |
| msgFilesTooLess         | You must select at least **{n}** {files} to upload.          | 你必须选择最少 **{n}** {files} 来上传.                       |
| msgFilesTooMany         | Number of files selected for upload **({n})** exceeds maximum allowed limit of **{m}**. | 选择的上传文件个数 **({n})** 超出最大文件的限制个数 **{m}**. |
| msgFileNotFound         | File "{name}" not found!                                     | 文件 "{name}" 未找到!                                        |
| msgFileSecured          | Security restrictions prevent reading the file "{name}".     | 安全限制，为了防止读取文件 "{name}".                         |
| msgFileNotReadable      | File "{name}" is not readable.                               | 文件 "{name}" 不可读.                                        |
| msgFilePreviewAborted   | File preview aborted for "{name}".                           | 取消 "{name}" 的预览.                                        |
| msgFilePreviewError     | An error occurred while reading the file "{name}".           | 读取 "{name}" 时出现了一个错误.                              |
| msgInvalidFileName      | Invalid or unsupported characters in file name "{name}".     | Invalid or unsupported characters in file name "{name}".     |
| msgInvalidFileType      | Invalid type for file "{name}". Only "{types}" files are supported. | 不正确的类型 "{name}". 只支持 "{types}" 类型的文件.          |
| msgInvalidFileExtension | Invalid extension for file "{name}". Only "{extensions}" files are supported. | 不正确的文件扩展名 "{name}". 只支持 "{extensions}" 的文件扩展名. |
| msgFileTypes            | {'image': 'image','html': 'HTML','text': 'text','video': 'video','audio': 'audio','flash': 'flash','pdf': 'PDF','object': 'object'}, | {'image': 'image','html': 'HTML','text': 'text','video': 'video','audio': 'audio','flash': 'flash','pdf': 'PDF','object': 'object'}, |
| msgUploadAborted        | The file upload was aborted                                  | 该文件上传被中止                                             |
| msgUploadThreshold      | Processing...                                                | Processing...                                                |
| msgUploadBegin          | Initializing...                                              | Initializing...                                              |
| msgUploadEnd            | Done                                                         | Done                                                         |
| msgUploadEmpty          | No valid data available for upload.                          | No valid data available for upload.                          |
| msgValidationError      | Validation Error                                             | 验证错误                                                     |
| msgLoading              | Loading file {index} of {files} …                            | 加载第 {index} 文件 共 {files} …                             |
| msgProgress             | Loading file {index} of {files} - {name} - {percent}% completed. | 加载第 {index} 文件 共 {files} - {name} - {percent}% 完成.   |
| msgSelected             | {n} {files} selected                                         | {n} {files} 选中                                             |
| msgFoldersNotAllowed    | Drag & drop files only! {n} folder(s) dropped were skipped.  | 只支持拖拽文件! 跳过 {n} 拖拽的文件夹.                       |
| msgImageWidthSmall      | Width of image file "{name}" must be at least {size} px.     | 宽度的图像文件的"{name}"的必须是至少{size}像素.              |
| msgImageHeightSmall     | Height of image file "{name}" must be at least {size} px.    | 图像文件的"{name}"的高度必须至少为{size}像素.                |
| msgImageWidthLarge      | Width of image file "{name}" cannot exceed {size} px.        | 宽度的图像文件"{name}"不能超过{size}像素.                    |
| msgImageHeightLarge     | Height of image file "{name}" cannot exceed {size} px.       | 图像文件"{name}"的高度不能超过{size}像素.                    |
| msgImageResizeError     | Could not get the image dimensions to resize.                | 无法获取的图像尺寸调整。                                     |
| msgImageResizeException | Error while resizing the image.<pre>{errors}</pre>           | 错误而调整图像大小。<pre>{errors}</pre>                      |
| msgAjaxError            | Something went wrong with the {operation} operation. Please try again later! | Something went wrong with the {operation} operation. Please try again later! |
| msgAjaxProgressError    | {operation} failed                                           | {operation} failed                                           |
| ajaxOperations          | {deleteThumb: 'file delete', uploadThumb: 'file upload', uploadBatch: 'batch file upload', uploadExtra: 'form data upload' }, | {deleteThumb: 'file delete',uploadThumb: 'file upload', uploadBatch: 'batch file upload',uploadExtra: 'form data upload'}, |
| dropZoneTitle           | Drag & drop files here …                                     | 拖拽文件到这里 … 支持多文件同时上传                          |
| dropZoneClickTitle      | (or click to select {files})                                 | (或点击{files}按钮选择文件)                                  |
| previewZoomButtonTitles | {prev: 'View previous file',next: 'View next file', toggleheader: 'Toggle header',fullscreen: 'Toggle full screen', borderless: 'Toggle borderless mode', close: 'Close detailed preview' } | { prev: '预览上一个文件',next: '预览下一个文件',toggleheader: '缩放', fullscreen: '全屏', borderless: '无边界模式',close: '关闭当前预览'} |
| fileActionSettings      |                                                              | { removeTitle: '删除文件',uploadTitle: '上传文件',zoomTitle: '查看详情',dragTitle: '移动 / 重置',indicatorNewTitle: '没有上传', indicatorSuccessTitle: '上传',indicatorErrorTitle: '上传错误', indicatorLoadingTitle: '上传 ...'}, |

### 五、Method说明

| 方法名                 | 描述                                                         |
| ---------------------- | ------------------------------------------------------------ |
| fileerror              | 异步上传错误结果处理$('#uploadfile').on('fileerror', function(event, data, msg) {}); |
| fileuploaded           | 异步上传成功结果处理$("#uploadfile").on("fileuploaded", function (event, data, previewId, index) {}) |
| filebatchuploaderror   | 批量上传错误结果处理$('#uploadfile').on('filebatchuploaderror', function(event, data, msg) {}); |
| filebatchuploadsuccess | 批量上传成功结果处理$('#uploadfile').on('filepreupload', function(event, data, previewId, index) {}); |
| filebatchselected      | 选择文件后处理事件$("#fileinput").on("filebatchselected", function(event, files) {}); |
| upload                 | 文件上传方法$("#fileinput").fileinput("upload");             |
| fileuploaded           | 上传成功后处理方法$("#fileinput").on("fileuploaded", function(event, data, previewId, index) {}); |
| filereset              |                                                              |
| fileclear              | 点击浏览框右上角X 清空文件前响应事件$("#fileinput").on("fileclear",function(event, data, msg){}); |
| filecleared            | 点击浏览框右上角X 清空文件后响应事件$("#fileinput").on("filecleared",function(event, data, msg){}); |
| fileimageuploaded      | 在预览框中图片已经完全加载完毕后回调的事件                   |







