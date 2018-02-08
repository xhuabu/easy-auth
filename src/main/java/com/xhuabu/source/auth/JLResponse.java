package com.xhuabu.source.auth;

/**
 * Created by zzhoo8 on 2017/4/6.
 */
public class JLResponse {

    private int code;
    private String msg;
    private Object data;

    private static final int SUCCESS_CODE = 200;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



}
