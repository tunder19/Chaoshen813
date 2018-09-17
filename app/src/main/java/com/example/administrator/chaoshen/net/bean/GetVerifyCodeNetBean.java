package com.example.administrator.chaoshen.net.bean;

public class GetVerifyCodeNetBean extends BaseNetBean {
    private String mobile; //手机号码（当0注册，1找回密码，2绑定手机号码为必须输入）
    private String token;  //登录后的token,用来验证是否登录（3变更手机为必须输入）
    private int type; //0注册，1找回密码，2绑定手机号码，3变更手机

    public GetVerifyCodeNetBean(String mobile, String token, int type) {
        this.mobile = mobile;
        this.token = token;
        this.type = type;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
