# JAVA异常处理jar包

Java系统开发中进行全局异常管理，对参数异常、请求异常、媒体异常、数据库异常、文件异常、JSON异常、运行时异常等等进行分类处理，可以对异常进行监控并记录，减少代码冗余和重复工作量，让用户界面更友好。

# 0.主要异常及状态

```java
    COM_ARGUMENT_NOT_VALID(101,"参数无效异常"),
    COM_ARGUMENT_NOT_READARABLE(102,"参数解析失败"),
    COM_METHOD_NOT_SUPPORT(103,"不支持当前请求方法"),
    COM_MEDIA_NOT_SUPPORT(104,"不支持当前媒体类型"),
    COM_DB_OPERATION_FAILED(105,"数据库异常"),
    UPLOAD_FILE_EXCEEDED_MAXSIZE(106,"上传文件过大"),
    COM_ARGUMENT_NOT_SUPPORT_JSON(107,"JSON转换异常"),
    COM_ARGUMENT_NOT_BIND(108,"参数绑定异常"),
    COM_INTERNAL_ERROR(109,"服务运行异常");
```

# 1.集成到项目

### 1.1 下载 jar 包到本地
下载地址：
[https://github.com/YouAreOnlyOne/FastFrameJar/tree/master/ExcepDeal](https://github.com/YouAreOnlyOne/FastFrameJar/tree/master/ExcepDeal)

### 1.2 maven方式引入

把下载的jar包放入本地maven仓库；然后在项目的 pom.xml 文件中添加如下的依赖：

```
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>fastjson</artifactId>
       <version>1.2.55</version>
   </dependency>

   <dependency>
      <groupId>com.ycj.fastframe</groupId>
      <artifactId>excepdeal</artifactId>
      <version>1.0</version>
   </dependency>


```

### 1.3 lib方式引入

1）**传统的SSM框架的Spring MVC 项目**，在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；然后在项目的 pom.xml 文件中添加如下的依赖：

```

  <dependency>
     <groupId>com.alibaba</groupId>
     <artifactId>fastjson</artifactId>
     <version>1.2.55</version>
  </dependency>
        
  <dependency>
    <groupId>excepdeal</groupId>
    <artifactId>excepdeal</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/excepdeal.jar</systemPath>
  </dependency>

```

2）**基于SpringBoot构建的项目**，在 resources 目录下建立 lib 目录，并把jar包复制到 lib 目录；然后在项目的 pom.xml 文件中添加如下的依赖：

```
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.55</version>
        </dependency>
        
        <dependency>
            <groupId>excepdeal</groupId>
            <artifactId>excepdeal</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/src/main/resources/lib/excepdeal.jar</systemPath>
        </dependency>

```

---

# 2.使用方法
以监控所有的Controller为例，在controller包下，建立一个 ExceptionController 类 继承 ExceptionDealWithJson 类，并加上 @ExceptionAdvice 注解，具体代码如下：

```java
package com.cfm.web.controller;

import com.ycj.fastframe.excepdeal.ExceptionAdvice;
import com.ycj.fastframe.excepdeal.ExceptionDealWithJson;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2020/3/1
 * @time: 16:01
 * @fuction: 全局异常管理.
 */
@ExceptionAdvice
public class ExceptionController extends ExceptionDealWithJson {
    @Override
    public void exceptionInfo(Exception e) {
    //这里可以把异常进行监控、保存日志或者插入数据库
        System.out.println(e.toString());
    }

    @Override
    public String message(String s) {
        return null;
    }

    @Override
    public Object globalData() {
        return null;
    }

    @Override
    public void globalAttributes(Model model) {

    }

    @Override
    public void globalInitBinder(WebDataBinder webDataBinder) {

    }
}

```

再新建一个 TestController 类，并制造异常，具体代码如下：

```java
package com.cfm.web.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2020/2/26
 * @time: 11:18
 * @fuction: about the role of class.
 */
@Controller
public class TestController {

    @RequestMapping("/zero")
    @ResponseBody
    public String zeroException(){
        int a=8/0;
        return "success";
    }

}

```


# 3.测试结果
1）浏览器输入访问路径，返回结果为定义好的 JSON 字符串。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200301163437579.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQzNzQwMDk=,size_16,color_FFFFFF,t_70)

2）在 TestController 中没有进行捕捉异常，后台也没有抛出异常，服务器也不会异常终止，只是根据我们的设计打印了异常，这是由于在上面的 exceptionInfo() 方法中我们只是打印异常，这个方法可以把异常进行监控、保存日志或者插入数据库。

```java

	@Override
    public void exceptionInfo(Exception e) {
    //这里可以把异常进行监控、保存日志或者插入数据库
        System.out.println(e.toString());
    }

```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200301163757912.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQzNzQwMDk=,size_16,color_FFFFFF,t_70)

# 4.其它方案
1）当我们的 ExceptionController 类继承其它方案，会有不同的效果和使用方式。
主要的可用方案有：

```java

1、ExceptionDealWithJson  //返回 json 数据

2、ExceptionDealWithEntity  //返回实体数据

3、ExceptionDealWithViewAndError  //返回页面并在model中加入异常信息 e.getMessage()

4、ExceptionDealWithViewAndJson  // 返回页面并在model中加入异常信息的 json 数据

5、ExceptionDealWithViewAndStyle  // 返回页面并在model中加入 异常信息的类型
```

2）前面两种主要是返回数据，后面三种不仅返回数据还返回页面，继承相应的类之后，会有一个 viewname() 方法指定前端页面名称。在前端页面只需要通过${message} 和 ${error} 进行获取异常信息。

```javascript
    <%@ page language="java" contentType="text/html; charset=UTF-8" %> 

    <!DOCTYPE html"> 

    <html> 

    <head> 

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 

    <title>错误信息</title> 

    </head> 

    <body> 

        <h1>${message}</h1> 

        <p>${error}</p> 

    </body> 

    </html>  
```
# 5.其它相关

快速集成框架 jar 包：
[https://blog.csdn.net/u014374009/category_9535972.html](https://blog.csdn.net/u014374009/category_9535972.html)

代码主页：
[https://github.com/YouAreOnlyOne](https://github.com/YouAreOnlyOne)


技术介绍：
[https://me.csdn.net/u014374009](https://me.csdn.net/u014374009)
