package com.example.administrator.chaoshen.net.bean;

public class ChangePasswordNetBean extends BaseNetBean {
    private String token;
    private String code;
    private String password;

    public ChangePasswordNetBean(String token, String oldPassword, String password) {
        this.token = token;
        this.code = oldPassword;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldPassword() {
        return code;
    }

    public void setOldPassword(String oldPassword) {
        this.code = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
