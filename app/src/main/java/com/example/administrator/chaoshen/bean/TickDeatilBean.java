package com.example.administrator.chaoshen.bean;

import java.util.List;

public class TickDeatilBean {

    /**
     * times : 10
     * text : 出票成功
     * bets : 1
     * content : ||0|0|0|0|0|0|0||||0|0
     * status : 4
     */

    private int times;
    private String text;
    private int bets;
    private String content;
    private int status;
    private String tips;
    private String passType;


    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBets() {
        return bets;
    }

    public void setBets(int bets) {
        this.bets = bets;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
