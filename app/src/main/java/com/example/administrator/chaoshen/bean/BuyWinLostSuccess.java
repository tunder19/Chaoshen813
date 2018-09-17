package com.example.administrator.chaoshen.bean;

public class BuyWinLostSuccess {


    private  long orderId;

    public BuyWinLostSuccess(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
