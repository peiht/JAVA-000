1、通过注解aop的形式实现主库和从库的读写分离

AOP + springboot + mybatisplus + hirariCP

效果：

![image-20201202164157762](/Users/hitopei/Documents/GitHub/JAVA-000/Week_07/aop.png)

2、主要代码：

配置文件：

```yaml
datasource:
  master:
    jdbcUrl: jdbc:mysql://127.0.0.1:3306/db?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&useSSL=false
    username: root
    password:
  slave:
    jdbcUrl: jdbc:mysql://127.0.0.1:3316/db?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&autoReconnect=true&useSSL=false
    username: root
    password:
```



将配置的数据源初始化，注入到bean

```java
@Bean(name = "master")
    @ConfigurationProperties(prefix = "datasource.master")
    public DataSource dataSourceMaster(){
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "datasource.slave")
    public DataSource dataSourceSlave(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource dynamicDataSource(){
        DynamicDataSource dataSource = new DynamicDataSource();
        DataSource master = dataSourceMaster();
        DataSource slave = dataSourceSlave();

        dataSource.setDefaultTargetDataSource(master);
        Map<Object, Object> map = new HashMap<>();
        map.put(DataSourceType.Master, master);
        map.put(DataSourceType.Slave, slave);
        dataSource.setTargetDataSources(map);
        return dataSource;
    }
```

继承类AbstractRoutingDataSource, ThreadLocal去存储数据源的信息。

```java
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}

public class DataSourceContextHolder {

    private final static ThreadLocal<String> local = new ThreadLocal<>();

    public static void putDataSource(String name){
        local.set(name);
    }

    public static String getDataSource() {
        return local.get();
    }
}
```

定义注解，和注解使用的位置

```java
//定义注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyDataSource {

    /**
     * 默认是master
     * @return value
     */
    DataSourceType value() default DataSourceType.Master;
}

//注解的使用
    @MyDataSource(value = DataSourceType.Master)
    public boolean addUser(User user) {
        user.setcTime(new Date());
        user.setuTime(new Date());
        return this.save(user);
    }

    @MyDataSource(value = DataSourceType.Slave)
    public List<User> listUsers() {
        return this.list();
    }

```

3、该方法主要是有侵入式，每次写个方法必须使用注解去指定某个方法