# web系统日志记录

基于面向切面的思想，使用aspectj和CGliB代理实现非侵入式用户操作日志记录，通过自定义注解实现获得每个方法的注释、功能等信息，以及日志类型的分类，通过暴露从session获取用户信息和保存日志两个接口，实现不同项目的快速接入。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，除了添加spring相关依赖以外，还需添加以下依赖：
  
  ```
  
   <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.8.13</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.0.4.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>5.0.4.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>servlet-api</artifactId>
      <version>2.5</version>
      <scope>provided</scope>
    </dependency>
    
    <dependency>
      <groupId>logsuper</groupId>
      <artifactId>logsuper</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/logsuper.jar</systemPath>
    </dependency>
    
   ```
    
## 接入
1、新建一个类实现GetUserBySession接口，实现从session中获取用户信息：
```
public class TestGetUserBySession implements GetUserBySession {
    @Override
    public OperateUser getUserByRequestSession(HttpServletRequest request) {
        return null;
    }
}
```

2、新建一个类实现SaveOperateLog接口，实现日志的保存方法，（不实现具体的保存方法也可以，jar包配置了通过jdbc的方式保存日志信息到数据，这种方式不是很好，推荐通过注入Service方式实现日志保存方法，日志的实体为 UserOperateLog ）：
```
public class TestSaveOperateLog implements SaveOperateLog {
    @Override
    public boolean saveUserOperateLog(UserOperateLog userOperateLog) {
        //可以通过调用Service方法对日志信息保存到数据库，下面只是进行简单的打印测试
        System.out.println(userOperateLog.getUserName());
        System.out.println(userOperateLog.getMethodName());
        System.out.println(userOperateLog.getRequestArgs());
        return false;
    }
}
```

3、新建一个类继承RequestLogAspect类，实现切面的定义和相关参数的初始化：
```
@Aspect
@Component
@Order(1)
public class TestLogSupervise extends RequestLogAspect {

    @Override
    @Pointcut("execution(public * com.construct.controller.mobile.h5..*.*(..))")
    public void webLog() {
    }

    @Override
    public void initLogSaveAndGetUser() {
        TestSaveOperateLog testSaveOperateLog=new TestSaveOperateLog();
        this.saveOperateLog=testSaveOperateLog;

        TestGetUserBySession testGetUserBySession=new TestGetUserBySession();
        this.getUserBySession=testGetUserBySession;
        //下面的参数是表示通过jdbc方式保存日志信息到数据库，如果自己实现了Service相关方法，只需要上面的参数即可，下面的不用管。
        DatabaseInfo databaseInfo=new DatabaseInfo();
        databaseInfo.setDatabaseURL("jdbc:mysql://39.1.96.1:3306/bess_cloud_cloud?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false");
        databaseInfo.setUserName("root");
        databaseInfo.setPassword("123456");
        autoSaveLog(databaseInfo);
    }
}

 ```
 
4、在spring配置文件进行配置：
```
<bean id="logService" class="com.construct.test.log.TestLogSupervise"></bean>
<aop:aspectj-autoproxy expose-proxy="true" proxy-target-class="true" />
<context:annotation-config/>
```
