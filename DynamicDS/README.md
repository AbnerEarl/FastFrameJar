# 动态数据源切换

满足mysql、oracle等主流数据库进行动态数据源切换。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，需要包含以下依赖：
  
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
      <groupId>datasyn</groupId>
      <artifactId>datasyn</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/ds.jar</systemPath>
    </dependency>
    
   ```
    
## 注册项目的数据源

继承DynamicDataSourceRegister类，并完成相关的方法重写。

  ```
  
import com.ycj.fastframe.dynamicds.aop.DynamicDataSourceRegister;
import com.ycj.fastframe.dynamicds.entity.DataBaseInfo;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2019/11/17
 * @time: 12:57
 * @fuction: about the role of class.
 */
public class MyDataSource extends DynamicDataSourceRegister {
    @Override
    public DataBaseInfo initDefaultDataSource(Environment env) {
        // 读取主数据源
        DataBaseInfo dataBaseInfo=new DataBaseInfo();
        dataBaseInfo.setDriver(env.getProperty("spring.datasource.driver"));
        dataBaseInfo.setUrl(env.getProperty("spring.datasource.url"));
        dataBaseInfo.setUsername(env.getProperty("spring.datasource.username"));
        dataBaseInfo.setPassword(env.getProperty("spring.datasource.password"));
        dataBaseInfo.setType("com.alibaba.druid.pool.DruidDataSource");
        return dataBaseInfo;
    }

    @Override
    public List<DataBaseInfo> initOtherDataSources(Environment env) {
        return null;
    }
}

  
 ```

## 设置数据源切换规则

架构中采用了LRU算法的缓存策略，以适应高并发和高性能需求，只需要继承BindDynamicDataSourceByUserIdentifier类，并完成相关的方法重写。

```
  
import com.ycj.fastframe.dynamicds.aop.BindDynamicDataSourceByUserIdentifier;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2019/11/16
 * @time: 14:00
 * @fuction: about the role of class.
 */


@Aspect
@Order(-1)//保证在@Transactional之前执行
@Component
public class TestDynamicDataSource extends BindDynamicDataSourceByUserIdentifier {


    @Override
    @Pointcut("execution(public * com.ycj.fastframe.controller..*.*(..))")
    public void dataSourceAspectPath() {

    }

    @Override
    public void logPrint(String msg) {
        System.out.println(msg);
    }

    @Override
    public int initCacheSize() {
        return 10;
    }

    @Override
    public String setDataSouceForUser(String userUniqueIdentifier) {
        if (userUniqueIdentifier.contains("1001")){
            return "project1";
        }else if (userUniqueIdentifier.contains("1002")){
            return "project2";
        }else {
            return "dataSource";
        }

    }

    @Override
    public String getUserUniqueIdentifier(HttpServletRequest request, String requestArgs) {
        return request.getSession().getId();
    }



    public static void main(String[] args) {

    }
}


```


  
## 其它方法
动态数据源切换有四种实现方式：

一、通过注解，这个最简单

只需要继承BindDynamicDataSourceByAnnotation类，并在controller中的每个方法前面添加  @RegisterDataSource(name = "数据源名称")  注解即可，如下
```
@Aspect
@Order(-1)//保证在@Transactional之前执行
@Component
public class TestDynamicDataSource extends BindDynamicDataSourceByAnnotation {
}

```

二、通过继承BindDynamicDataSourceByArgs类，来实现，步骤跟上面一样，都很简单。

三、通过继承BindDynamicDataSourceByRequest类，来实现，步骤跟上面一样，都很简单。

四、通过继承BindDynamicDataSourceByUserIdentifier类，来实现，步骤跟上面一样，都很简单。
