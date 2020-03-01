package com.cfm.web.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2019/4/4
 * @time: 9:01
 * @fuction: 消息模板，主要对用户的请求进行相应，移动端需要使用，web端酌情使用。
 */
public class Response {
    public int code;
    public String msg;
    public Object data;
    public int count;

    public Response(){
        this.code = 0;
        this.msg = "";
        this.data = new Object();
    }
    public Response message( String m ){
        this.msg = m;
        return this;
    }

    public Response count( int m ){
        this.count = m;
        return this;
    }

    public Response data( Object data ){
        this.data = data;
        return this;
    }
    public String fail(){
        this.code = 1;
        return Response.toJSON(this);
    }
    public String fail(String msg){
        this.message(msg);
        this.code = 1;
        return Response.toJSON(this);
    }
    public String success(){
        this.code = 0;
        return Response.toJSON(this);
    }
     public String success(String msg){
        this.message(msg);
        this.code = 0;
        return Response.toJSON(this);
    }

    public static String toJSON(Response as){
        return JSON.toJSONString(as, SerializerFeature.WriteMapNullValue);
    }
}
