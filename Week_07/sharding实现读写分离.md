1.sharding jdbc实现读写分离

框架使用spring boot + shardingSphere jdbc + mybatis plus结合实现的简单的增删改查功能。

结果图：

![sharding](/Users/hitopei/Documents/GitHub/JAVA-000/Week_07/sharding.png)

新增操作走的是master 查找走的是slave

2、sharding-jdbc在配置文件的主要配置，一些参数是参考的我们线上系统的配置。

```yaml
spring:
  shardingsphere:
    datasource:
      names: master, slave
      master:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://127.0.0.1:3306/db?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&useSSL=false
        username: root
        password:
        connection-timeout: 30000
        idle-timeout: 600000
        max-lifetime: 1800000
        connection-test-query: select 1
        maximum-pool-size: 20
        minimum-idle: 5
        pool-name: APIHikariCP
        auto-commit: true
      slave:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://127.0.0.1:3316/db?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&useSSL=false
        username: root
        password:
        connection-timeout: 30000
        idle-timeout: 600000
        max-lifetime: 1800000
        connection-test-query: select 1
        maximum-pool-size: 20
        minimum-idle: 5
        pool-name: APIHikariCP
        auto-commit: true
    masterslave:
      load-balance-algorithm-type: round_robin
      name: ms
      master-data-source-name: master
      slave-data-source-names: slave
    props:
      sql.show: true
```

3、一般查询都是走从库的，但是也有很多场景需要实时查询新增的信息，数据同步到从库需要时间，为了避免延时需要从主库中查询新增的数据。

使用HintManager查询强制走主库。

```java
HintManager hintManager = HintManager.getInstance();
hintManager.setMasterRouteOnly();
User user = this.getById(id);
hintManager.close();
```

效果：

```bash
2020-12-03 10:15:45.475  INFO 90705 --- [nio-8080-exec-2] ShardingSphere-SQL                       : Actual SQL: master ::: SELECT id,password,phone,c_time,u_time FROM user WHERE id=?
```

从master进行的查询

