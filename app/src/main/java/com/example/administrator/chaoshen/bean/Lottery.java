package com.example.administrator.chaoshen.bean;

public class Lottery {
    private String imageIcon;
    private String name;
    private int noticeTpe=0; //0没背景 1有背景
    private String notice;
    private String topNotice;


    public Lottery(String imageIcon, String name, int noticeTpe, String notice, String topNotice) {
        this.imageIcon = imageIcon;
        this.name = name;
        this.noticeTpe = noticeTpe;
        this.notice = notice;
        this.topNotice = topNotice;
    }

    public String getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(String imageIcon) {
        this.imageIcon = imageIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoticeTpe() {
        return noticeTpe;
    }

    public void setNoticeTpe(int noticeTpe) {
        this.noticeTpe = noticeTpe;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTopNotice() {
        return topNotice;
    }

    public void setTopNotice(String topNotice) {
        this.topNotice = topNotice;
    }
}
