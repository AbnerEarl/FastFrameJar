var SocketioClient = {
    createClient:function(host,port,uuid){
        var client = {};
        //服务器域名/IP
        client.host = host;
        //socketio服务端口号
        client.port = port;
        var url='http://'+host+':'+port+'?uuid='+uuid;
        client.socket = io(url);
        client.socket.on('connect', () => {
            console.log(client.socket.id); // 'G5p5...'
    });
        client.socket.on('connect', () => {
            console.log("连接成功");
    });
        client.socket.on('connect_error', (error) => {
            console.error("连接错误");
    });
        client.socket.on('connect_timeout', (timeout) => {
            console.log("连接超时");
    });
        client.socket.on('error', (error) => {
            // ...
        });
        client.socket.on('disconnect', (reason) => {
            console.log("关闭连接");
    });
        client.socket.on('reconnect', (attemptNumber) => {
            console.log("重新连接");
    });
        client.socket.on('reconnect_error', (error) => {
            // ...
        });
        client.socket.on('reconnect_failed', () => {
            // ...
        });
        client.socket.on('ping', () => {
            //console.log("向服务器端发送数据包");
        });
        client.socket.on('pong', (latency) => {
            //console.log("接收服务器数据包");
        });
        // 向服务端发送消息
        client.send = function(event,msg,callback){
            if(callback){
                client.socket.emit(event, msg,(data) =>callback);
            }else{
                client.socket.emit(event, msg);
            }
        }
        // 接收服务端消息
        client.receive = function(event,callback){
            client.socket.on(event,callback);
        }
        return client;
    }
}