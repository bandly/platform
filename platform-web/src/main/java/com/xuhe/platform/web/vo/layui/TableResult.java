package com.xuhe.platform.web.vo.layui;

import java.io.Serializable;
import java.util.List;

/**
 * @author liqiang
 * @date 2019/05/29
 * @description:
 */
public class TableResult<T> implements Serializable {

    private Integer code = 0;
    private String msg;
    private Integer count;
    private List<T> data;

    public TableResult(Integer count, List<T> data) {
        this.count = count;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
