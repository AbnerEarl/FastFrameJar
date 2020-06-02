# web系统通用日志监测

基于面向切面的思想，使用aspectj和CGliB代理实现非侵入式用户操作日志记录，通过自定义注解实现获得每个方法的注释、功能等信息，以及日志类型的分类，通过暴露从session获取用户信息和保存日志两个接口，实现不同项目的快速接入。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录（Springboot构建的项目可以在resources目录下构建）；

2、在maven配置文件，即 pom.xml 中，除了添加spring相关依赖以外，还需添加以下依赖（Springboot构建的项目可以省略其中关于spring相关的依赖）：
  
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
    
## 接入项目
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
 
4、在spring配置文件进行配置

（Springboot构建的项目可以省略，在启动类上添加注解：@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)  ；

也可以把配置写成spring配置文件，在springboot启动类上引入spring.xml配置文件 @ImportResource({"classpath:/spring.xml"})  ；

还可以在 springboot的默认配置文件中添加：在application.yml中有以下配置
```
spring:
  aop:
    #auto: true #默认为true，可省略
    proxy-target-class: true # 默认为false即JDK动态代理，我们一般要设为true，使用CGLIB代理）：
```
```
<bean id="logService" class="com.construct.test.log.TestLogSupervise"></bean>
<aop:aspectj-autoproxy expose-proxy="true" proxy-target-class="true" />
<context:annotation-config/>
```

## 其它可选
如果希望用户请求时所执行的方法，对应的说明和注释能够同步存入用户操作日志记录中，则需在每个请求方法前加入一行注解 @MethodLog()，如下：
```
    @RequestMapping(value = "user/login")
    @MethodLog(remark = "请求登录页面",operType = "0",desc = "移动端用户请求登录页面")
    public String loginIndex(Model model){
        return "mobile/login/login";
    }
```
