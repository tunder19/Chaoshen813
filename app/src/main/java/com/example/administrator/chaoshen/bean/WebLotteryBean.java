package com.example.administrator.chaoshen.bean;

public class WebLotteryBean {
    private String orderId;
    private String amount;
    private String lotteryType;


    public WebLotteryBean(String orderId, String amount, String lotteryType) {
        this.orderId = orderId;
        this.amount = amount;
        this.lotteryType = lotteryType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }
}
