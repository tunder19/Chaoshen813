package com.example.administrator.chaoshen.bean;

public class MatchesBean {
    /**
     * id : 2520
     * leagueId : 7
     * year : 2018
     * season : J联赛 2018
     * round : 23
     * host : 札幌冈萨多
     * guest : 东京FC
     * score : VS
     * matchDate : 2018-08-19 12:00:00
     * updateTime : 2018-08-17 12:54:25
     */

    private int id;
    private int leagueId;
    private int year;
    private String season;
    private int round;
    private String host;
    private String guest;
    private String score;
    private String matchDate;
    private String updateTime;

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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
