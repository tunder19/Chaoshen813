package com.example.administrator.chaoshen.bean;

public class SelectLuckmoneyBean {
    private String selItem;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public SelectLuckmoneyBean(String selItem,String link) {
        this.selItem = selItem;
        this.link=link;
    }

    public String getSelItem() {
        return selItem;
    }

    public void setSelItem(String selItem) {
        this.selItem = selItem;
    }
}
