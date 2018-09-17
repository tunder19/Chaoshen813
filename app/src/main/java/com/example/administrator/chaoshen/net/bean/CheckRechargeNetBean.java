package com.example.administrator.chaoshen.net.bean;

public class CheckRechargeNetBean extends BaseNetBean {
    private String token;
    private long payId;

    public CheckRechargeNetBean(String token, long payId) {
        this.token = token;
        this.payId = payId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getPayId() {
        return payId;
    }

    public void setPayId(long payId) {
        this.payId = payId;
    }
}
