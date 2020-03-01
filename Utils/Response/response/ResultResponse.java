package com.ycj.webapp.internationalization.pojo;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2020/2/22
 * @time: 10:47
 * @fuction: about the role of class.
 */
public class ResultResponse {
    private String msg;
    private Object data;
    private int status;
    private int count;
    private int code;

    public ResultResponse(){

    }

    public ResultResponse(String msg, Object data, int status, int count, int code) {
        this.msg = msg;
        this.data = data;
        this.status = status;
        this.count = count;
        this.code = code;
    }

    public ResultResponse(String msg, Object data, int status, int code) {
        this.msg = msg;
        this.data = data;
        this.status = status;
        this.code = code;
    }

    public ResultResponse(String msg, Object data, int code) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }
    public ResultResponse(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public void success(){
        this.code=0;
    }

    public void failed(){
        this.code=1;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
