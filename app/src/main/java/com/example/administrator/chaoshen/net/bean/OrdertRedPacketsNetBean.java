package com.example.administrator.chaoshen.net.bean;

public class OrdertRedPacketsNetBean extends BaseNetBean {
    private String token;
    private int page;
    private int size=10;
    private  long orderId;
    private String lotteryType;
    private String amount;


    public OrdertRedPacketsNetBean(String token,  String lotteryType, String amount) {
        this.token = token;
        this.lotteryType = lotteryType;
        this.amount=amount;
    }


    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
