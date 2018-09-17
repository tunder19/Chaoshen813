package com.example.administrator.chaoshen.bean;

public class LeaguesBean {
    /**
     * id : 7
     * name : J联赛
     * logo : https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/200506141453430.jpg
     * round : 23
     * updateTime : 2018-08-17 12:54:24
     */

    private int id;
    private String name;
    private String logo;
    private int round;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
