package com.example.administrator.chaoshen.net.bean;

public class GetBetsNetBean extends BaseNetBean {
    private String token;
    private String type; //类型, all全部, winning已中奖, waiting待开奖
    private int page;
    private int size;


    public GetBetsNetBean(String token, String type,int page) {
        this.token = token;
        this.type = type;
        this.page=page;
    }


    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
