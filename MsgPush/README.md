# 全平台消息推送

不借助第三方消息推送平台，基于netty-socketio中事件驱动，建立消息推送服务，搭建服务端，并在服务端使用消息推送，可在一定程度上保证消息推送的可控性、安全性等。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，添加以下依赖：
  
  ```
  
  <dependency>
      <groupId>com.corundumstudio.socketio</groupId>
      <artifactId>netty-socketio</artifactId>
      <!--<version>1.7.12</version>-->
      <version>1.7.7</version>
    </dependency>
    
    <dependency>
      <groupId>msgpush</groupId>
      <artifactId>msgpush</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/msgpush.jar</systemPath>
    </dependency>
    
   ```
    
## 服务端开启服务

  ```
  
  public class TestNat {
    public static void main(String[] args) {
        msgPushServer=new MsgPushServer("localhost",9092,null);
    }
}
  
 ```
 
## 服务端停止服务

```

msgPushServer.stopServer();

```

## 服务端推送消息

```

UserMessage userMessage=new UserMessage();
userMessage.setUuid(uuid);
userMessage.setMessage("指定用户，欢迎使用！");
msgPushServer.sendMessageToOneUser(userMessage);

```

## 客户端接受消息

```
<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.0.3/socket.io.js"></script>
<script src="../../../BessEnergySystem/static/js/moment.min.js"></script>
<script src="../../../BessEnergySystem/static/js/socketio_client.js"></script>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

<script type="text/javascript">
    //构建客户端实例
     var client = SocketioClient.createClient('localhost',9092,key);
    //接收消息处理
    client.receive('msg_event',function(data){
        console.info(data);
        // 在此处更新页面显示（提示效果）
        alert(data);
    });
    //向服务端发送消息
    function sendMsg(){
        client.send('msg_event', 'Test Client!');
    }
</script>


```



  
## 其它方法
消息推送中还有其他方法，根据实际业务需要进行使用！

msgPushServer.sendMessageToAllClient();

msgPushServer.sendMessageToOneClient();

msgPushServer.sendMessageToPartClient();

msgPushServer.isNotOnline();

msgPushServer.sendMessageToPartUser();
