package com.example.administrator.chaoshen.net.bean;

public class FeedBackNetBean extends BaseNetBean {
    private String content;


    public FeedBackNetBean(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
