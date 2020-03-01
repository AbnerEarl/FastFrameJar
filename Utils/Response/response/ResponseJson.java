package com.cfm.web.response;

import java.util.List;

/**
 * @author: Frank
 * @email 1320259466@qq.com
 * @date: 2019/4/4
 * @time: 9:01
 * @fuction: 消息模板，主要对用户的请求进行相应，移动端需要使用，web端酌情使用。
 */
public class ResponseJson<T> {

    /**
     * 生成带total总条数和列表的JSON串
     */
    private Integer total;

    private List<T> rows;
  
    public Integer getTotal() {
        return total;
    }

    public ResponseJson<T> setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public List<T> getRows() {
        return rows;
    }

    public ResponseJson<T> setRows(List<T> rows) {
        this.rows = rows;
        return this;
    }

}