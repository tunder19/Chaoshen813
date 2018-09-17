package com.example.administrator.chaoshen.bean;

import java.util.List;

public class M_MineMachesBean {

    /**
     * matchDate : 2018-09-10
     * week : 周一
     * num : 3
     * matches : [{"hostRank":"70","week":"周一","hostShort":"俄罗斯","guestPic":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/app/20180909/22ee0c6385564e0285b5979434c3bb64.png","bf":"5 : 1","guestRank":"46","matchTime":"09-11 00:00","guestShort":"捷克","guestRed":0,"dataId":1043595,"hostRed":0,"halfBf":"3 : 0","matchNo":"001","dataLink":"https://r209.chaoshenwan.com/h5/match?matchId=1043595","text":"完场","leagueShort":"国际赛","guestLeague":" 世","matchId":2704,"hostLeague":" 世","hostPic":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/app/20180909/bceede3f6ef24e9bbd5da8b3e34a8efc.png","status":2},{"hostRank":"3","week":"周一","hostShort":"捷克21","guestPic":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/app/20180909/4d22ace73b9d4a35a6e7e5b86eb168cf.png","bf":"1 : 2","guestRank":"1","matchTime":"09-11 00:00","guestShort":"希腊21","guestRed":0,"dataId":1019867,"hostRed":0,"halfBf":"0 : 1","matchNo":"007","dataLink":"https://r209.chaoshenwan.com/h5/match?matchId=1019867","text":"完场","leagueShort":"欧青预赛","guestLeague":"A组","matchId":2705,"hostLeague":"A组","hostPic":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/app/20180909/9a7777f06ba64dc1b6d54772b4763506.png","status":2},{"hostRank":"4","week":"周一","hostShort":"葡萄牙","guestPic":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/app/20180908/da5b2cfe9fc64f8f94a03bf8a6594b94.png","bf":"1 : 0","guestRank":"19","matchTime":"09-11 02:45","guestShort":"意大利","guestRed":0,"dataId":1039790,"hostRed":0,"halfBf":"0 : 0","matchNo":"002","dataLink":"https://r209.chaoshenwan.com/h5/match?matchId=1039790","text":"完场","leagueShort":"欧国联","guestLeague":" 世","matchId":2695,"hostLeague":" 世","hostPic":"http://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/app/20180908/cc0252a927d249638823135e34de6aee.png","status":2}]
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

    public List<MatchListBean> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchListBean> matches) {
        this.matches = matches;
    }
}
