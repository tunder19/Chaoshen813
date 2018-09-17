package com.example.administrator.chaoshen.bean;

import java.io.Serializable;

public class MatchListBean implements Serializable{
    /**
     * hostRank : [1]J联
     * week : 周三
     * hostShort : 广岛三箭
     * guestPic : http://lottery-1254240493.cosgz.myqcloud.com/app/20180709/b5bb8bcb2c674c64b612d08946d25e75.png
     * matchNo : 115
     * guestRank : [18]J联
     * matchTime : 07-11 18:00
     * leagueShort : 天皇杯
     * matchId : 900
     * hostPic : http://lottery-1254240493.cosgz.myqcloud.com/app/20180709/bd732baab484497393baef579e47d1c7.png
     * guestShort : 名古屋鲸
     */

    private String hostRank;
    private String week;
    private String hostShort;
    private String guestPic;
    private String matchNo;
    private String guestRank;
    private String matchTime;
    private String leagueShort;
    private int matchId;
    private String hostPic;
    private String guestShort;
    private String status;
    private String text;
    private String bf;
    private String halfBf;
    private int hostRed;
    private int guestRed;
    private String dataLink;
    private int headId;
    private boolean saveLocal=false;

    public boolean isSaveLocal() {
        return saveLocal;
    }

    public void setSaveLocal(boolean saveLocal) {
        this.saveLocal = saveLocal;
    }

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public String getDataLink() {
        return dataLink;
    }

    public void setDataLink(String dataLink) {
        this.dataLink = dataLink;
    }

    public int getHostRed() {
        return hostRed;
    }

    public void setHostRed(int hostRed) {
        this.hostRed = hostRed;
    }

    public int getGuestRed() {
        return guestRed;
    }

    public void setGuestRed(int guestRed) {
        this.guestRed = guestRed;
    }

    public String getHalfBf() {
        return halfBf;
    }

    public void setHalfBf(String halfBf) {
        this.halfBf = halfBf;
    }

    public String getBf() {
        return bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHostRank() {
        return hostRank;
    }

    public void setHostRank(String hostRank) {
        this.hostRank = hostRank;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getHostShort() {
        return hostShort;
    }

    public void setHostShort(String hostShort) {
        this.hostShort = hostShort;
    }

    public String getGuestPic() {
        return guestPic;
    }

    public void setGuestPic(String guestPic) {
        this.guestPic = guestPic;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getGuestRank() {
        return guestRank;
    }

    public void setGuestRank(String guestRank) {
        this.guestRank = guestRank;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getLeagueShort() {
        return leagueShort;
    }

    public void setLeagueShort(String leagueShort) {
        this.leagueShort = leagueShort;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getHostPic() {
        return hostPic;
    }

    public void setHostPic(String hostPic) {
        this.hostPic = hostPic;
    }

    public String getGuestShort() {
        return guestShort;
    }

    public void setGuestShort(String guestShort) {
        this.guestShort = guestShort;
    }
}
