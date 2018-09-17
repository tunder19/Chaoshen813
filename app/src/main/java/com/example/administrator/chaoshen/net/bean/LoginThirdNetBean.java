package com.example.administrator.chaoshen.net.bean;

public class LoginThirdNetBean extends BaseNetBean {
    private int type ; //登录类别：1微信，2QQ
    private String openid;
    private String accessToken;
    private String token;
    private String code;

    public LoginThirdNetBean(int type, String token, String code) {
        this.type = type;
        this.token = token;
        this.code = code;
    }

    public LoginThirdNetBean(int type, String openid, String accessToken, String token) {
        this.type = type;
        this.openid = openid;
        this.accessToken = accessToken;
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
