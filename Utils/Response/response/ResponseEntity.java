package com.cfm.web.response;

import java.io.Serializable;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2019/4/4
 * @time: 9:01
 * @fuction: 消息模板，主要对用户的请求进行相应，移动端需要使用，web端酌情使用。
 */
public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = 4007479131106646741L;

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private T data;

    public ResponseEntity(Integer status) {
    	this.status = status;
    }
    
    public ResponseEntity(Integer status, T data) {
    	this.status = status;
    	this.data = data;
    }
    
    public ResponseEntity(Integer status, String msg) {
    	this.status = status;
    	this.msg = msg;
    }

    public ResponseEntity(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseEntity<T> build(Integer status, String msg, T data) {
        return new ResponseEntity<T>(status, msg, data);
    }
    
    public static <T> ResponseEntity<T> build(Integer status, String msg) {
    	return new ResponseEntity<T>(status, msg);
    }
    
    public static <T> ResponseEntity<T> build(Integer status, T data) {
        return new ResponseEntity<T>(status,data);
    }
    
    public static <T> ResponseEntity<T> build(Integer status) {
        return new ResponseEntity<T>(status);
    }
    
    public static <T> ResponseEntity<T> createSuccess() {
        return new ResponseEntity<T>(ResponseEnum.SUCCESS.getCode());
    }
    
    public static <T> ResponseEntity<T> createSuccessMsg(String msg) {
        return new ResponseEntity<T>(ResponseEnum.SUCCESS.getCode(), msg);
    }
    
    public static <T> ResponseEntity<T> createSuccess(T data) {
        return new ResponseEntity<T>(ResponseEnum.SUCCESS.getCode(), data);
    }
    
    public static <T> ResponseEntity<T> createSuccess(String msg, T data) {
        return new ResponseEntity<T>(ResponseEnum.SUCCESS.getCode(), msg, data);
    }
    
    public static <T> ResponseEntity<T> createFailed() {
        return new ResponseEntity<T>(ResponseEnum.FAILED.getCode(), ResponseEnum.FAILED.getDesc());
    }
    
    public static <T> ResponseEntity<T> createFailedMsg(String msg) {
        return new ResponseEntity<T>(ResponseEnum.FAILED.getCode(), msg);
    }
    
    public static <T> ResponseEntity<T> createFailed(T data) {
        return new ResponseEntity<T>(ResponseEnum.FAILED.getCode(), data);
    }
    
    public static <T> ResponseEntity<T> createFailed(String msg, T data) {
        return new ResponseEntity<T>(ResponseEnum.FAILED.getCode(), msg, data);
    }
    
    public static <T> ResponseEntity<T> createException() {
        return new ResponseEntity<T>(ResponseEnum.EXCEPTION.getCode(), ResponseEnum.EXCEPTION.getDesc());
    }
    
    public static <T> ResponseEntity<T> createExceptionMsg(String msg) {
        return new ResponseEntity<T>(ResponseEnum.EXCEPTION.getCode(), msg);
    }
    
    public static <T> ResponseEntity<T> createException(T data) {
        return new ResponseEntity<T>(ResponseEnum.EXCEPTION.getCode(), data);
    }
    
    public static <T> ResponseEntity<T> createException(String msg, T data) {
        return new ResponseEntity<T>(ResponseEnum.EXCEPTION.getCode(), msg, data);
    }
    
    public static <T> ResponseEntity<T> createError() {
        return new ResponseEntity<T>(ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getDesc());
    }
    
    public static <T> ResponseEntity<T> createErrorMsg(String msg) {
        return new ResponseEntity<T>(ResponseEnum.ERROR.getCode(), msg);
    }
    
    public static <T> ResponseEntity<T> createError(T data) {
        return new ResponseEntity<T>(ResponseEnum.ERROR.getCode(), data);
    }
    
    public static <T> ResponseEntity<T> createError(String msg, T data) {
        return new ResponseEntity<T>(ResponseEnum.ERROR.getCode(), msg, data);
    }
    
    public static <T> ResponseEntity<T> createWrong() {
        return new ResponseEntity<T>(ResponseEnum.WRONG.getCode(), ResponseEnum.WRONG.getDesc());
    }
    
    public static <T> ResponseEntity<T> createWrongMsg(String msg) {
        return new ResponseEntity<T>(ResponseEnum.WRONG.getCode(), msg);
    }
    
    public static <T> ResponseEntity<T> createWrong(T data) {
        return new ResponseEntity<T>(ResponseEnum.WRONG.getCode(), data);
    }
    
    public static <T> ResponseEntity<T> createWrong(String msg, T data) {
        return new ResponseEntity<T>(ResponseEnum.WRONG.getCode(), msg, data);
    }
    
    public static <T> ResponseEntity<T> createHandle() {
        return new ResponseEntity<T>(ResponseEnum.HANDLE.getCode(), ResponseEnum.HANDLE.getDesc());
    }
    
    public static <T> ResponseEntity<T> createHandleMsg(String msg) {
        return new ResponseEntity<T>(ResponseEnum.HANDLE.getCode(), msg);
    }
    
    public static <T> ResponseEntity<T> createHandle(T data) {
        return new ResponseEntity<T>(ResponseEnum.HANDLE.getCode(), data);
    }
    
    public static <T> ResponseEntity<T> createHandle(String msg, T data) {
        return new ResponseEntity<T>(ResponseEnum.HANDLE.getCode(), msg, data);
    }
    
    
}