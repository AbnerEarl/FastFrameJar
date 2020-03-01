package com.cfm.web.response;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2019/4/4
 * @time: 9:01
 * @fuction: 对消息返回码进行约定，不同的数字代表不同的请求返回状态。
 */

public enum ResponseEnum {
	
	SUCCESS(0,"SUCCESS"),
	FAILED(1,"FAILED"),
	EXCEPTION(2,"EXCEPTION"),
	ERROR(3,"ERROR"),
	WRONG(4,"WRONG"),
	HANDLE(5,"HANDLE");

    private final int code;
    private final String desc;

    ResponseEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    
    public String getDesc(){
        return desc;
    }

}