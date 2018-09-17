package com.example.administrator.chaoshen.net.bean;

public class M_GetMajiaPointsNetBean extends BaseNetBean {
    private String leagueId;
    private String year;
    private String round;


    public M_GetMajiaPointsNetBean(String leagueId, String year) {
        this.leagueId = leagueId;
        this.year = year;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }
}
