# 内网穿透及映射

把内网主机映射成为公网主机，满足内外网穿透，在不同的业务场景中，需要把内网的主机映射成为公网主机来对外提供服务，在公网主机有限的情况，基于netty根据端口号做一个数据映射服务，可以穿透网站、数据库等各种应用，满足日常的使用。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，添加以下依赖：
  
  ```
  
  <dependency>
      <groupId>io.netty</groupId>
      <artifactId>netty-all</artifactId>
      <version>4.1.33.Final</version>
    </dependency>

    <dependency>
      <groupId>commons-cli</groupId>
      <artifactId>commons-cli</artifactId>
      <version>1.4</version>
    </dependency>

    <dependency>
      <groupId>org.json</groupId>
      <artifactId>json</artifactId>
      <version>20180813</version>
    </dependency>
    
    <dependency>
      <groupId>natx</groupId>
      <artifactId>natx</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/natx.jar</systemPath>
    </dependency>
    
   ```
    
## 服务端开启服务

  ```
  
  public class TestNat {
    public static void main(String[] args) {
        NatxServer.startNat(9090,"123456");
    }
}
  
 ```
 
## 服务端停止服务

```

NatxServer.endNat();

```

## 客户端映射穿透

https://github.com/YouAreOnlyOne/NATnetty



  
## 其它方法
数据同步中还有其他方法，根据实际业务需要进行使用！
