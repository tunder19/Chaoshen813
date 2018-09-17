package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.List;

public class M_NewLeaguesBean implements Serializable{
    /**
     * seasons : [{"year":2018,"leagueId":7,"season":"J联赛 2018"}]
     * round : 26
     * name : J联赛
     * logo : https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/200506141453430.jpg
     * updateTime : 2018-09-11 14:38:45
     * id : 7
     */

    private int round;
    private String name;
    private String logo;
    private String updateTime;
    private int id;
    private List<SeasonsBean> seasons;
    private int total;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SeasonsBean> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonsBean> seasons) {
        this.seasons = seasons;
    }

    public static class SeasonsBean implements Serializable{
        /**
         * year : 2018
         * leagueId : 7
         * season : J联赛 2018
         */

        private int year;
        private int leagueId;
        private String season;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(int leagueId) {
            this.leagueId = leagueId;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }
    }
}
