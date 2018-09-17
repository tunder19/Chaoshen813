package com.example.administrator.chaoshen.net.bean;

public class EditPasswordNetBean extends BaseNetBean {
    private String token;
    private String oldPassword;
    private String password;

    public EditPasswordNetBean(String token, String oldPassword, String password) {
        this.token = token;
        this.oldPassword = oldPassword;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
