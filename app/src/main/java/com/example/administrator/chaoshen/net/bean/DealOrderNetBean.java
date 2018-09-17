package com.example.administrator.chaoshen.net.bean;

public class DealOrderNetBean extends BaseNetBean {
    private  long orderId;

    public DealOrderNetBean(long orderId) {
        this.orderId = orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }
}
