package com.example.administrator.chaoshen.bean;

public class JingcaiBean {
    /**
     * bf : 1 - 2
     * hostShort : 首尔FC(-1)
     * guestShort : 济州联
     */

    private String bf;
    private String hostShort;
    private String guestShort;
    private String prizeTime;
    private String link;
    private int pushStatus=1;//赛果推送：1是开启，0是关闭


    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(String prizeTime) {
        this.prizeTime = prizeTime;
    }

    public String getBf() {
        return bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }

    public String getHostShort() {
        return hostShort;
    }

    public void setHostShort(String hostShort) {
        this.hostShort = hostShort;
    }

    public String getGuestShort() {
        return guestShort;
    }

    public void setGuestShort(String guestShort) {
        this.guestShort = guestShort;
    }
}