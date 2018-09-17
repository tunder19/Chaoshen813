package com.example.administrator.chaoshen.net.bean;

public class LuckyMoneyNetBean extends BaseNetBean {
    private String token;
    private int page;
    private int size=10;
    private int queryStatus;//查询状态：0可用，1用完或过期

    public LuckyMoneyNetBean(String token, int page,int queryStatus) {
        this.token = token;
        this.page = page;
        this.queryStatus=queryStatus;
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
