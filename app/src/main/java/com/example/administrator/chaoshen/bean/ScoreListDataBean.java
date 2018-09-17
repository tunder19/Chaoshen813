package com.example.administrator.chaoshen.bean;

import com.example.administrator.chaoshen.info.BaseSignleInfo;

import java.util.List;

public class ScoreListDataBean  {

        /**
         * matchDate : 2018-08-09
         * week : 周四
         * num : 10
         * matches : [{"hostRank":"[1]巴甲","week":"周四","hostShort":"波特诺","guestPic":
         * "http://lottery-1254240493.cosgz.myqcloud.com/app/20180807/63834042f2944841a39e320c10ce06f0.png","bf":"-","guestRank":"[6]巴甲","matchTime":"08-10 08:45","guestShort":"帕梅拉斯","guestRed":0,"hostRed":0,"matchNo":"010","text":"未","leagueShort":"解放者杯","matchId":1474,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180807/da8eeb4c6f4645efb30c93ce1f5d9dd0.png","status":0},{"hostRank":"[15]阿甲","week":"周四","hostShort":"图库曼","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180807/0ddcc2d688de4accbda73642b8260f99.png","bf":"-","guestRank":"[10]哥甲","matchTime":"08-10 08:45","guestShort":"麦国民","guestRed":0,"hostRed":0,"matchNo":"009","text":"未","leagueShort":"解放者杯","matchId":1478,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180807/60c51ac7fa2a4a14b1c423ced1494a39.png","status":0},{"hostRank":"[7]阿甲","week":"周四","hostShort":"竞技","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180807/d34d9966c2164dcea5a59b58c3223908.png","bf":"-","guestRank":"[8]阿甲","matchTime":"08-10 06:30","guestShort":"河床","guestRed":0,"hostRed":0,"matchNo":"007","text":"未","leagueShort":"解放者杯","matchId":1475,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180807/b5a3a36b83b04d499b4a57d35e196498.png","status":0},{"hostRank":"[13]巴甲","week":"周四","hostShort":"达伽马","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180807/89a09b4e7f8e4e5b9fab9418b398d216.png","bf":"-","guestRank":"[7]厄甲","matchTime":"08-10 06:30","guestShort":"基多体大","guestRed":0,"hostRed":0,"matchNo":"008","text":"未","leagueShort":"俱乐部杯","matchId":1477,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180807/3b11993e2bc44343b4adfb5040b34c85.png","status":0},{"hostRank":"[1]爱超","week":"周四","hostShort":"科克城","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/b474708ab891457786ff9729afebb0b5.png","bf":"-","guestRank":"[2]挪超","matchTime":"08-10 02:45","guestShort":"罗森博格","guestRed":0,"hostRed":0,"matchNo":"005","text":"未","leagueShort":"欧罗巴","matchId":1480,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/d6935bee0a5849c199149381697b10f7.png","status":0},{"hostRank":"[2]苏超","week":"周四","hostShort":"希伯尼安","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/26cc5a9b2a8d4972b61bf9e66412c998.png","bf":"-","guestRank":"[4]挪超","matchTime":"08-10 02:45","guestShort":"莫尔德","guestRed":0,"hostRed":0,"matchNo":"006","text":"未","leagueShort":"欧罗巴","matchId":1484,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/a5764099f2564de6b0d56f9947a39b69.png","status":0},{"hostRank":"[3]土超","week":"周四","hostShort":"伊斯坦布","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/2f8b7caf4933449490204137083cb2b7.png","bf":"-","guestRank":"[7]英超","matchTime":"08-10 02:00","guestShort":"伯恩利","guestRed":0,"hostRed":0,"matchNo":"003","text":"未","leagueShort":"欧罗巴","matchId":1479,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/7dab63e9485940e180fe74fa615cbf23.png","status":0},{"hostRank":"[6]荷甲","week":"周四","hostShort":"维迪斯","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/0d4ddcfa902a472a9ab0efc8bfa8ab9c.png","bf":"-","guestRank":"[5]瑞超","matchTime":"08-10 02:00","guestShort":"巴塞尔","guestRed":0,"hostRed":0,"matchNo":"004","text":"未","leagueShort":"欧罗巴","matchId":1483,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/c142f711b49b4ae6bb4f4dc8e956b94b.png","status":0},{"hostRank":"[11]乌超","week":"周四","hostShort":"马里乌波","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/2c32341f813c4ffb9d4b1a4d521fabce.png","bf":"-","guestRank":"[6]法甲","matchTime":"08-10 01:00","guestShort":"波尔多","guestRed":0,"hostRed":0,"matchNo":"002","text":"未","leagueShort":"欧罗巴","matchId":1482,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/f7d66ef136514a88ab57f850d42f5db8.png","status":0},{"hostRank":"[4]以超","week":"周四","hostShort":"海夏普尔","guestPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/5ccd4db8836244b49ea6053f861e70e3.png","bf":"-","guestRank":"[7]意甲","matchTime":"08-10 00:00","guestShort":"亚特兰大","guestRed":0,"hostRed":0,"matchNo":"001","text":"未","leagueShort":"欧罗巴","matchId":1481,"hostPic":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180808/fcc835bd9f16412b93debbe0605e43cc.png","status":0}]
         */

        private String matchDate;
        private String week;
        private int num;
        private List<MatchListBean> matches;

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
            return matches;
        }

        public void setMatches(List<MatchListBean> matches) {
            this.matches = matches;
        }

}
