package com.harmonycloud.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
public class Message<T> {
    @ApiModelProperty(value = "code")
    private int code;
    @ApiModelProperty(value = "描述")
    private String msg;
    @ApiModelProperty(value = "对象")
    private T data;

    public void setMessage(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
}