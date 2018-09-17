package com.example.administrator.chaoshen.bean;

import java.util.List;

public class M_MatchesBean  {

        /**
         * id : 2784
         * leagueId : 7
         * year : 2018
         * season : J联赛 2018
         * round : 26
         * host : 大阪樱花
         * guest : 磐田喜悦
         * score : VS
         * matchDate : 2018-09-14 18:00:00
         * updateTime : 2018-09-12 11:30:29
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
        private String hostFlag;
        private String guestFlag;


    public String getHostFlag() {
        return hostFlag;
    }

    public void setHostFlag(String hostFlag) {
        this.hostFlag = hostFlag;
    }

    public String getGuestFlag() {
        return guestFlag;
    }

    public void setGuestFlag(String guestFlag) {
        this.guestFlag = guestFlag;
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
