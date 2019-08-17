# Redis简单集成到Spring框架

为了更简单的使用 redis ，把redis常用的工具类、配置类，进行了封装，只需要安装完redis之后，在项目简单配置下，就可以使用redis。其主要作用是利用内存换速度，让数据请求更多的命中在redis缓存中，不再每次都去请求数据库，从而提升性能。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，除了添加spring相关依赖以外，还需添加以下依赖：
  
  ```
  
    <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-redis</artifactId>
      <version>1.6.0.RELEASE</version>
    </dependency>
   
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-pool2</artifactId>
      <version>2.6.2</version>
    </dependency>
   
    <dependency>
      <groupId>redis.clients</groupId>
      <artifactId>jedis</artifactId>
      <version>2.7.2</version>
    </dependency>
    
    <dependency>
      <groupId>redis</groupId>
      <artifactId>redis</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/redis.jar</systemPath>
    </dependency>
    
   ```
    
## 接入项目

1、在spring配置文件中加入相关配置：
```
 <!--Spring整合Redis-->
    <!--设置连接池-->
    <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <!-- 最小空闲连接数 -->
        <property name="minIdle" value="5"/>
        <!-- 最大空闲连接数 -->
        <property name="maxIdle" value="300"/>
        <!-- 最大连接数 -->
        <property name="maxTotal" value="600" />
        <!-- 每次释放连接的最大数目 -->
        <property name="numTestsPerEvictionRun" value="1024" />
        <!-- 释放连接的扫描间隔（毫秒） -->
        <property name="timeBetweenEvictionRunsMillis" value="30000" />
        <!-- 连接最小空闲时间 -->
        <property name="minEvictableIdleTimeMillis" value="1800000" />
        <!-- 获取连接时的最大等待毫秒数,小于零:阻塞不确定的时间,默认-1 -->
        <property name="maxWaitMillis" value="1000" />
        <!-- 在获取连接的时候检查有效性, 默认false -->
        <property name="testOnBorrow" value="true" />
        <property name="testOnReturn" value="true" />
        <!-- 在空闲时检查有效性, 默认false -->
        <property name="testWhileIdle" value="true" />
        <!-- 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true -->
        <property name="blockWhenExhausted" value="false" />
        <!-- 连接空闲多久后释放, 当空闲时间>该值 且 空闲连接>最大空闲连接数 时直接释放 -->
        <property name="softMinEvictableIdleTimeMillis" value="10000"/>
    </bean>
    
    <!-- jedis客户端单机版 -->
    <bean id="redisClient" class="redis.clients.jedis.JedisPool">
        <constructor-arg name="host" value="192.168.99.100"></constructor-arg>
        <constructor-arg name="port" value="6379"></constructor-arg>
        <!--<constructor-arg name="password" value="123456"></constructor-arg>-->
        <constructor-arg name="poolConfig" ref="poolConfig"></constructor-arg>
        <constructor-arg name="timeout" value="100000"></constructor-arg>
    </bean>
    <bean id="JedisClient" class="com.ycj.fastframe.redis.jedis.JedisClientSingle"/>


    <!--配置redis模版和缓存-->
    <!-- 配置JedisConnectionFacory -->
    <bean id="jedisConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <property name="hostName" value="192.168.99.100" />
        <property name="port" value="6379" />
        <!--<property name="password" value="123456" />-->
        <property name="database" value="0" />
        <property name="poolConfig" ref="poolConfig" />
    </bean>

     <!--配置redistempLate-->
    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
        <property name="connectionFactory" ref="jedisConnectionFactory" />
    </bean>

     <!--配置RedisCacheManager-->
    <bean id="redisCacheManager" class="org.springframework.data.redis.cache.RedisCacheManager">
        <constructor-arg name="redisOperations" ref="redisTemplate" />
        <property name="defaultExpiration" value="3000" />
    </bean>

     <!--配置RedisCacheConfig-->
    <bean id="redisCacheConfig" class="com.ycj.fastframe.redis.cache.RedisCacheConfig">
        <constructor-arg ref="jedisConnectionFactory" />
        <constructor-arg ref="redisTemplate" />
        <constructor-arg ref="redisCacheManager" />
    </bean>
    
    
    <!------------------------------------------- 集群版，与上面的单机版二选一 ----------------------------------------->
    <!-- 配置jedis集群 -->
    <bean class="redis.clients.jedis.JedisCluster">
        <constructor-arg name="nodes">
            <set>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="${cluster.host1}" />
                    <constructor-arg name="port" value="${cluster.port1}" />
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="${cluster.host2}" />
                    <constructor-arg name="port" value="${cluster.port2}" />
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="${cluster.host3}" />
                    <constructor-arg name="port" value="${cluster.port3}" />
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="${cluster.host4}" />
                    <constructor-arg name="port" value="${cluster.port4}" />
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="${cluster.host5}" />
                    <constructor-arg name="port" value="${cluster.port5}" />
                </bean>
                <bean class="redis.clients.jedis.HostAndPort">
                    <constructor-arg name="host" value="${cluster.host6}" />
                    <constructor-arg name="port" value="${cluster.port6}" />
                </bean>
            </set>
        </constructor-arg>
    </bean>

    <!--配置集群版工具类-->
    <bean class="com.ycj.fastframe.redis.redis.RedisCluster" />

```

# 使用方法
1、新建一个TestRedisService类，进行测试：
```
@Service
public class TestRedisService {
    @Autowired
    private LogAlarmService logAlarmService;
    @Autowired
    JedisClientSingle jedisClient;


    public String getUser(String id){
        LogAlarm logAlarm=null;
        long s = System.currentTimeMillis();
        logAlarm= (LogAlarm) jedisClient.getObject(id);
        long e = System.currentTimeMillis();
        System.out.println("redis耗时："+(e-s));
        if (logAlarm==null){
            s=System.currentTimeMillis();
            EntityWrapper<LogAlarm> userEntityWrapper=new EntityWrapper<>();
            userEntityWrapper.where("id={0}",id);
            logAlarm = logAlarmService.selectOne(userEntityWrapper);
            e=System.currentTimeMillis();
            System.out.println("数据库耗时："+(e-s));
            if (logAlarm!=null){
                jedisClient.setObject(id,logAlarm);
            }

        }else {
            System.out.println("从redis缓存中命中");
        }
        return JSON.toJSONString(logAlarm);
    }

    @Cacheable("getAlarm")
    public String getAlarm(String id){
        long s=System.currentTimeMillis();
        EntityWrapper<LogAlarm> userEntityWrapper=new EntityWrapper<>();
        userEntityWrapper.where("id={0}",id);
        LogAlarm logAlarm = logAlarmService.selectOne(userEntityWrapper);
        long e=System.currentTimeMillis();
        System.out.println("数据库耗时："+(e-s));

        return JSON.toJSONString(logAlarm);
    }
}

```

2、在Controller中调用相关的方法进行测试即可。
 

## 配置问题
关于整合redis，需要添加配置文件的问题，笔者对配置文件进行了初步的连接，也曾设想过通过注解来动态实现，其主要考虑使用了ClassPathXmlApplicationContext 进行初步的测试，如下新建一个MyRedisConfig类，加上@Component 注解，并使用 @PostConstruct 注解初始化方法，来加载并获得相关的 bean ：
```
@Component
public class MyRedisConfig {
    public static ApplicationContext applicationContext;
    public static JedisClientSingle jedisClient;
    @PostConstruct
    public void init(){
        RedisInfo redisInfo=new RedisInfo();
        redisInfo.setIp("192.168.99.100");
        redisInfo.setPort(6379);
        applicationContext = RedisConfig.initRedisSingle(redisInfo);
        if (applicationContext!=null){
            System.out.println("redis启动结果：true");
            jedisClient= (JedisClientSingle) applicationContext.getBean("JedisClient");
        }else {
            System.out.println("redis启动结果：false");

        }
    }
}

```


