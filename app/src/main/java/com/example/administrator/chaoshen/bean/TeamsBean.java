package com.example.administrator.chaoshen.bean;

import java.io.Serializable;

public class TeamsBean implements Serializable{
    /**
     * id : 264
     * leagueId : 1
     * year : 2018
     * name : 阿森纳
     * flag : http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/2P01h7uI.png
     */

    private int id;
    private int leagueId;
    private int year;
    private String name;
    private String flag;
    private int votes;
    private boolean showAnimation=true;


    public boolean isShowAnimation() {
        return showAnimation;
    }

    public void setShowAnimation(boolean showAnimation) {
        this.showAnimation = showAnimation;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
