package com.example.administrator.chaoshen.bean;

public class PlayersBean {
    /**
     * id : 968
     * leagueId : 7
     * year : 2018
     * season : J联赛 2018
     * rank : 1
     * team : 广岛三箭
     * name : 帕特里克
     * position : 前锋
     * matches : 22
     * goals : 16
     * kicks : 2
     * updateTime : 2018-08-17 12:54:25
     */

    private int id;
    private int leagueId;
    private int year;
    private String season;
    private int rank;
    private String team;
    private String name;
    private String position;
    private int matches;
    private int goals;
    private int kicks;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getKicks() {
        return kicks;
    }

    public void setKicks(int kicks) {
        this.kicks = kicks;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
