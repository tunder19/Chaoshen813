package com.example.administrator.chaoshen.net.info;

/**
 * Created by Change on 2015/5/15.
 */
public class BaseInfo {
    private String state;
    private String message;



    public String getCode() {
        return state;
    }

    public void setCode(String code) {
        this.state = code;
    }

    public String getErrorMsg() {
        return message;
    }

    public void setErrorMsg(String errorMsg) {
        this.message = errorMsg;
    }




}
