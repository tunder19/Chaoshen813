package com.example.administrator.chaoshen.net.bean;

public class PayOrderNetBean extends BaseNetBean {
    private  String token;
    private  long orderId;
    private  long redId	;

    public PayOrderNetBean(String token, long orderId) {
        this.token = token;
        this.orderId = orderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getRedId() {
        return redId;
    }

    public void setRedId(long redId) {
        this.redId = redId;
    }
}
