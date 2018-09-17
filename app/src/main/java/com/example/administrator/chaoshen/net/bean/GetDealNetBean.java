package com.example.administrator.chaoshen.net.bean;

public class GetDealNetBean extends BaseNetBean {
    private String token;
    private int page;
    private int size=10;

    public GetDealNetBean(String token, int page) {
        this.token = token;
        this.page = page;
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
}
