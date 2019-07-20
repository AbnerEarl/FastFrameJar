# 自动代码生成器

根据数据表自动生成相关代码，例如 entity、controller、service、mapper文件、dao接口也就是mapper文件对应的接口。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，添加以下依赖：
  
  ```
  
  <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus</artifactId>
      <version>2.1.8</version>
    </dependency>
    
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-generator</artifactId>
      <version>3.1.1</version>
    </dependency>
    
    <dependency>
      <groupId>autocode</groupId>
      <artifactId>autocode</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/autocode.jar</systemPath>
    </dependency>
    
   ```
    
## 生成代码

  ```
  
  DatabaseInfo sourceDb=new DatabaseInfo();
  sourceDb.setDatabaseURL("jdbc:mysql://localhost:3306/world?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
  sourceDb.setUserName("root");
  sourceDb.setPassword("wdsjnsjydjy");
  sourceDb.setDriverClass("com.mysql.cj.jdbc.Driver");
  CodeInfo codeInfo=new CodeInfo();
  String []tables={"city","sys_set_parameter"};
  Generator.generateCodeByAllTables(sourceDb,codeInfo);
  
 ```
 

  
## 其它方法
根据mapper.xml生成sql语句！

```
try {
     CreateSql.generateSql("E:\\IdeaProjects\\MyFrame\\FastFrame\\src\\main\\resources\\mapper","test.sql");
    } catch (IOException e) {
      e.printStackTrace();
   }
```
