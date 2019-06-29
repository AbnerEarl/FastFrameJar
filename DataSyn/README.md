# 海量数据高效同步

满足mysql、oracle等主流数据库进行跨库数据传输、备份、同步。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，添加以下依赖：
  
  """
  
  <!-- https://mvnrepository.com/artifact/org.dom4j/dom4j -->
    <dependency>
      <groupId>dom4j</groupId>
      <artifactId>dom4j</artifactId>
      <version>1.6.1</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/jaxen/jaxen -->
    <dependency>
      <groupId>jaxen</groupId>
      <artifactId>jaxen</artifactId>
      <version>1.1-beta-6</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.40</version>
      <!--<scope>runtime</scope>-->
    </dependency>
    
    <dependency>
      <groupId>syndata</groupId>
      <artifactId>syndata</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/syndata.jar</systemPath>
    </dependency>
    
   """
    
## 日志接口
  """
  
  public class MyLogRecord implements LogInterface {
    @Override
    public boolean recordDataSynLog(String message) {
        System.out.println("日志插入数据库成功！");
        return false;
    }
  }
  
 """

## 全量同步

"""
  public class TestDataSyn {
    public static void main(String[] args) {
        DatabaseInfo sourceDb=new DatabaseInfo();
        sourceDb.setDatabaseURL("jdbc:mysql://127.0.0.1:3306/test");
        sourceDb.setUserName("root");
        sourceDb.setPassword("123456");

        DatabaseInfo destinDb=new DatabaseInfo();
        destinDb.setDatabaseURL("jdbc:mysql://119.23.30.116:3306/test");
        destinDb.setUserName("root");
        destinDb.setPassword("123456");

        List<String> tableList=new ArrayList<>();
        tableList.add("test");

        MyLogRecord myLogRecord=new MyLogRecord();
        SynDataMain.synDatabaseAll(sourceDb,destinDb,tableList,20,100000,myLogRecord);
      }
   }

"""

## 增量同步

  """
  
  public class TestDataSyn {
    public static void main(String[] args) {
        DatabaseInfo sourceDb=new DatabaseInfo();
        sourceDb.setDatabaseURL("jdbc:mysql://127.0.0.1:3306/test");
        sourceDb.setUserName("root");
        sourceDb.setPassword("123456");

        DatabaseInfo destinDb=new DatabaseInfo();
        destinDb.setDatabaseURL("jdbc:mysql://119.23.30.116:3306/test");
        destinDb.setUserName("root");
        destinDb.setPassword("123456");

        List<String> tableList=new ArrayList<>();
        tableList.add("test");

        MyLogRecord myLogRecord=new MyLogRecord();
        SynDataMain.synDatabaseIncrement(sourceDb,destinDb,tableList,20,100000,myLogRecord);
      }
    }

  """
  
## 其它方法
数据同步中还有其他方法，根据实际业务需要进行使用！
