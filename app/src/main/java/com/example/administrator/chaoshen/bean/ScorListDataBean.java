package com.example.administrator.chaoshen.bean;

import java.util.List;

public class ScorListDataBean {

            /**
             * matchDate : 2018-07-11
             * week : 周三
             * matchList : [{"hostRank":"[1]J联","week":"周三","hostShort":"广岛三箭","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180709/b5bb8bcb2c674c64b612d08946d25e75.png","matchNo":"115","guestRank":"[18]J联","matchTime":"07-11 18:00","leagueShort":"天皇杯","matchId":900,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180709/bd732baab484497393baef579e47d1c7.png","guestShort":"名古屋鲸"}]
             * num : 1
             */

            private String matchDate;
            private String week;
            private int num;
            private List<MatchListBean> matchList;

            public String getMatchDate() {
                return matchDate;
            }

            public void setMatchDate(String matchDate) {
                this.matchDate = matchDate;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public List<MatchListBean> getMatchList() {
                return matchList;
            }

            public void setMatchList(List<MatchListBean> matchList) {
                this.matchList = matchList;
            }

}
