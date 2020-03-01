package com.ycj.fastframe.excepdeal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2020/3/1
 * @time: 12:25
 * @fuction: rest接口，返回的json结构 .
 */

public  class RemoteResponse {
    private int code;//code =0 表示成功; code !=0 表示失败
    private String message;
    private Object data;
    private int status;

    public RemoteResponse(){
        this.code = 0;
        this.message = "";
        this.data = new Object();
    }

    public RemoteResponse(int code, String message, Object data, int status) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public RemoteResponse(String message, Object data, int status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public RemoteResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RemoteResponse(Object data, int status) {
        this.data = data;
        this.status = status;
    }

    public RemoteResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public RemoteResponse message(String m ){
        this.message = m;
        return this;
    }

    public RemoteResponse data( Object data ){
        this.data = data;
        return this;
    }


    public String fail(){
        this.code = 1;
        return RemoteResponse.toJSON(this);
    }
    public RemoteResponse failed(){
        this.code=1;
        return this;
    }
    public String fail(String msg){
        this.message(msg);
        this.code = 1;
        return RemoteResponse.toJSON(this);
    }
    public String success(){
        this.code = 0;
        return RemoteResponse.toJSON(this);
    }

    public RemoteResponse succeed(){
        this.code=0;
        return this;
    }
    public String success(String msg){
        this.message(msg);
        this.code = 0;
        return RemoteResponse.toJSON(this);
    }
    public static String toJSON(RemoteResponse as){
        return JSON.toJSONString(as, SerializerFeature.WriteMapNullValue);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
