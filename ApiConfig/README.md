# 服务接口管理

通过Swagger来管理服务平台对外提供的接口，方便开发测试，对于后台开发中节约与客户端开发者的交流成本，避免通过修改复杂的文档完成接口对接，使得项目开发更具有简洁性、高效性。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，添加以下依赖：
  
  ```
  
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.9.2</version>
    </dependency>
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.9.2</version>
    </dependency>

     <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.9.7</version>
    </dependency>
    
    <dependency>
      <groupId>apiconfig</groupId>
      <artifactId>apiconfig</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/apiconfig.jar</systemPath>
    </dependency>
    
   ```
    
## 继承注入
1、在任意非controller目录下，新建一个类 TestSwaggerApi
  ```
  
@Configuration
@EnableSwagger2
@EnableWebMvc
public class TestSwaggerApi extends ApiConfig {

    @Override
    public List<String> setPackagesPath() {
        //设置需要检测显示的包路径
        List<String> arrayList = new ArrayList<>();
        arrayList.add("com.construct.controller.mobile.h5");
        return arrayList;
    }

    @Override
    public boolean withMethodAnnotation() {
        //设置是否根据注解形式来扫描显示的接口
        return false;
    }
}
  
 ```
 
## SpringMVC文件配置扫描（SpringBoot项目可跳过）

```

    <mvc:resources mapping="swagger-ui.html" location="classpath:/META-INF/resources/" />
    <mvc:resources mapping="/webjars/**" location="classpath:/META-INF/resources/webjars/" />
    <!-- 上一步所建立的类路径 -->
    <bean id="testSwagger" class="com.construct.test.TestSwaggerApi"></bean>
  
```

## 编写接口
1、举例子
```
@Controller
@RequestMapping("/person")
@Api(value="个人业务")
public class PersonController {
    @RequestMapping(value="/getPerson",method= RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getPersons() {
        return "请求成功";
    }
}
```

## 查看与测试接口

1、在浏览器访问如下地址：

http://IP地址:端口/项目名称/swagger-ui.html



  
## 其它方法
1、设置分组及接口路径，新建一个

```
@Component
@Primary
public class DocPath extends DocumentationConfig {

    @Override
    public List<SwaggerResource> setApiPathInfo() {
        List<SwaggerResource> swaggerResourceList=new ArrayList<>();
        swaggerResourceList.add(swaggerResource("默认分组API接口", "/v2/api-docs", "0.0.0.1"));
        return null;
    }
}
```

## swagger常用注解说明
```
- @Api()用于类； 
表示标识这个类是swagger的资源 
- @ApiOperation()用于方法； 
表示一个http请求的操作 
- @ApiParam()用于方法，参数，字段说明； 
表示对参数的添加元数据（说明或是否必填等） 
- @ApiModel()用于类 
表示对类进行说明，用于参数用实体类接收 
- @ApiModelProperty()用于方法，字段 
表示对model属性的说明或者数据操作更改 
- @ApiIgnore()用于类，方法，方法参数 
表示这个方法或者类被忽略 
- @ApiImplicitParam() 用于方法 
表示单独的请求参数 
- @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
```

```
作用范围	  API	  使用位置
对象属性	  @ApiModelProperty	  用在出入参数对象的字段上
协议集描述 	@Api  	用于controller类上
协议描述	  @ApiOperation 	用在controller的方法上
Response集 	@ApiResponses	  用在controller的方法上
Response	  @ApiResponse	  用在 @ApiResponses里边
非对象参数集	  @ApiImplicitParams	  用在controller的方法上
非对象参数描述 	@ApiImplicitParam 	用在@ApiImplicitParams的方法里边
描述返回对象的意义	  @ApiModel	  用在返回对象类上

```
