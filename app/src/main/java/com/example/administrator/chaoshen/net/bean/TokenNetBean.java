package com.example.administrator.chaoshen.net.bean;

public class TokenNetBean extends BaseNetBean {
    private String token;

    public TokenNetBean(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
