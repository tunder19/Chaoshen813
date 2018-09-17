package com.example.administrator.chaoshen.net.bean;

public class UserLoginNetBean extends BaseNetBean{
    private String mobile;
    private String password;

    public UserLoginNetBean(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
