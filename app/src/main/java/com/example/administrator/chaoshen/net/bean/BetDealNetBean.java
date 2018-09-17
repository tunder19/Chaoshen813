package com.example.administrator.chaoshen.net.bean;

public class BetDealNetBean extends BaseNetBean {
    private  long orderId;
    private String token;


    public BetDealNetBean(long orderId, String token) {
        this.orderId = orderId;
        this.token = token;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
