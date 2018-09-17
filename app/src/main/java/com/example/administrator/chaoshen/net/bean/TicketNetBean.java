package com.example.administrator.chaoshen.net.bean;

public class TicketNetBean extends  BaseNetBean {
    private String token;
    private long orderId;
    private int page;
    private int size=10;

    public TicketNetBean(String token, long orderId, int page) {
        this.token = token;
        this.orderId = orderId;
        this.page = page;
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
}
