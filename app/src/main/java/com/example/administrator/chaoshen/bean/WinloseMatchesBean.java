package com.example.administrator.chaoshen.bean;


import com.example.administrator.chaoshen.info.WinloseInfo;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WinloseMatchesBean implements Serializable {
    /**
     * lotteryNo : 18082
     * endTime : 2018/07/14 21:20:00
     * lotteryName : 胜负彩
     * matches : [{"id":315,"lotteryNo":"18082","matchNo":1,"league":"世界杯","guestOdds":3.22,"hostTeam":"比利时","hostOdds":2.18,"hostRank":"世 [3]","hostHistory":"{\"recent\":{\"even\":1,\"lose\":1,\"asHost\":{\"even\":0,\"lose\":1,\"win\":2},\"matches\":10,\"win\":8,\"asGuest\":{\"even\":0,\"lose\":1,\"win\":2}}}","guestTeam":"英格兰","guestRank":"世 [12]","guestHistory":"{\"recent\":{\"even\":3,\"lose\":1,\"asHost\":{\"even\":2,\"lose\":0,\"win\":3},\"matches\":10,\"win\":6,\"asGuest\":{\"even\":2,\"lose\":0,\"win\":3}}}","evenOdds":3.64,"versusHistory":"{\"host\":{\"even\":1,\"lose\":1,\"asHost\":{\"even\":0,\"lose\":0,\"win\":0},\"win\":1},\"guest\":{\"even\":1,\"lose\":1,\"win\":1,\"asGuest\":{\"even\":0,\"lose\":0,\"win\":0}},\"matches\":3}","matchTime":"2018/07/14 22:00:00"},{"id":316,"lotteryNo":"18082","matchNo":2,"league":"世界杯","guestOdds":4.7,"hostTeam":"法国","hostOdds":1.91,"hostRank":"世 [7]","hostHistory":"{\"recent\":{\"even\":2,\"lose\":0,\"asHost\":{\"even\":1,\"lose\":0,\"win\":2},\"matches\":10,\"win\":8,\"asGuest\":{\"even\":1,\"lose\":0,\"win\":2}}}","guestTeam":"克罗地亚","guestRank":"世 [20]","guestHistory":"{\"recent\":{\"even\":3,\"lose\":2,\"asHost\":{\"even\":1,\"lose\":2,\"win\":3},\"matches\":10,\"win\":5,\"asGuest\":{\"even\":1,\"lose\":2,\"win\":3}}}","evenOdds":3.25,"versusHistory":"{\"host\":{\"even\":2,\"lose\":0,\"asHost\":{\"even\":1,\"lose\":0,\"win\":1},\"win\":1},\"guest\":{\"even\":2,\"lose\":1,\"win\":0,\"asGuest\":{\"even\":1,\"lose\":1,\"win\":0}},\"matches\":3}","matchTime":"2018/07/15 23:00:00"},{"id":317,"lotteryNo":"18082","matchNo":3,"league":"日乙","guestOdds":6.9,"hostTeam":"福冈黄蜂","hostOdds":1.42,"hostRank":"[5]","hostHistory":"{\"total\":{\"score\":36,\"even\":6,\"lose\":5,\"asHost\":{\"score\":23,\"even\":2,\"lose\":2,\"win\":7},\"win\":10},\"recent\":{\"even\":2,\"lose\":4,\"asHost\":{\"even\":1,\"lose\":3,\"win\":2},\"matches\":10,\"win\":4,\"asGuest\":{\"even\":1,\"lose\":3,\"win\":2}}}","guestTeam":"赞岐釜玉海","guestRank":"[21]","guestHistory":"{\"total\":{\"score\":18,\"even\":6,\"lose\":12,\"win\":4,\"asGuest\":{\"score\":10,\"even\":4,\"lose\":6,\"win\":2}},\"recent\":{\"even\":2,\"lose\":6,\"asHost\":{\"even\":2,\"lose\":4,\"win\":0},\"matches\":10,\"win\":2,\"asGuest\":{\"even\":2,\"lose\":4,\"win\":0}}}","evenOdds":4.08,"versusHistory":"{\"host\":{\"even\":3,\"lose\":2,\"asHost\":{\"even\":0,\"lose\":2,\"win\":1},\"win\":2},\"guest\":{\"even\":3,\"lose\":2,\"win\":2,\"asGuest\":{\"even\":0,\"lose\":1,\"win\":2}},\"matches\":7}","matchTime":"2018/07/15 17:00:00"},{"id":318,"lotteryNo":"18082","matchNo":4,"league":"日乙","guestOdds":2.86,"hostTeam":"东京绿茵","hostOdds":2.31,"hostRank":"[10]","hostHistory":"{\"total\":{\"score\":32,\"even\":8,\"lose\":6,\"asHost\":{\"score\":19,\"even\":4,\"lose\":3,\"win\":5},\"win\":8},\"recent\":{\"even\":2,\"lose\":3,\"asHost\":{\"even\":1,\"lose\":2,\"win\":3},\"matches\":10,\"win\":5,\"asGuest\":{\"even\":1,\"lose\":2,\"win\":3}}}","guestTeam":"山口雷诺法","guestRank":"[3]","guestHistory":"{\"total\":{\"score\":40,\"even\":7,\"lose\":4,\"win\":11,\"asGuest\":{\"score\":20,\"even\":5,\"lose\":2,\"win\":5}},\"recent\":{\"even\":4,\"lose\":2,\"asHost\":{\"even\":4,\"lose\":1,\"win\":2},\"matches\":10,\"win\":4,\"asGuest\":{\"even\":4,\"lose\":1,\"win\":2}}}","evenOdds":3.24,"versusHistory":"{\"host\":{\"even\":1,\"lose\":3,\"asHost\":{\"even\":1,\"lose\":1,\"win\":0},\"win\":1},\"guest\":{\"even\":1,\"lose\":1,\"win\":3,\"asGuest\":{\"even\":1,\"lose\":0,\"win\":1}},\"matches\":5}","matchTime":"2018/07/15 17:00:00"},{"id":319,"lotteryNo":"18082","matchNo":5,"league":"日乙","guestOdds":4.17,"hostTeam":"甲府风林","hostOdds":1.82,"hostRank":"[11]","hostHistory":"{\"recent\":{\"even\":1,\"lose\":1,\"asHost\":{\"even\":0,\"lose\":1,\"win\":2},\"matches\":10,\"win\":8,\"asGuest\":{\"even\":0,\"lose\":1,\"win\":2}}}","guestTeam":"岐阜FC","guestRank":"[12]","guestHistory":"{\"recent\":{\"even\":3,\"lose\":1,\"asHost\":{\"even\":2,\"lose\":0,\"win\":3},\"matches\":10,\"win\":6,\"asGuest\":{\"even\":2,\"lose\":0,\"win\":3}}}","evenOdds":3.33,"versusHistory":"{\"host\":{\"even\":1,\"lose\":1,\"asHost\":{\"even\":0,\"lose\":0,\"win\":0},\"win\":1},\"guest\":{\"even\":1,\"lose\":1,\"win\":1,\"asGuest\":{\"even\":0,\"lose\":0,\"win\":0}},\"matches\":3}","matchTime":"2018/07/15 17:00:00"},{"id":320,"lotteryNo":"18082","matchNo":6,"league":"日乙","guestOdds":2.54,"hostTeam":"新泻天鹅","hostOdds":2.7,"hostRank":"[17]","hostHistory":"{\"total\":{\"score\":26,\"even\":5,\"lose\":10,\"asHost\":{\"score\":7,\"even\":4,\"lose\":6,\"win\":1},\"win\":7},\"recent\":{\"even\":3,\"lose\":6,\"asHost\":{\"even\":1,\"lose\":4,\"win\":1},\"matches\":10,\"win\":1,\"asGuest\":{\"even\":1,\"lose\":4,\"win\":1}}}","guestTeam":"横滨FC","guestRank":"[6]","guestHistory":"{\"total\":{\"score\":36,\"even\":9,\"lose\":4,\"win\":9,\"asGuest\":{\"score\":18,\"even\":3,\"lose\":2,\"win\":5}},\"recent\":{\"even\":5,\"lose\":1,\"asHost\":{\"even\":2,\"lose\":1,\"win\":2},\"matches\":10,\"win\":4,\"asGuest\":{\"even\":2,\"lose\":1,\"win\":2}}}","evenOdds":3.05,"versusHistory":"{\"host\":{\"even\":0,\"lose\":0,\"asHost\":{\"even\":0,\"lose\":0,\"win\":3},\"win\":5},\"guest\":{\"even\":0,\"lose\":5,\"win\":0,\"asGuest\":{\"even\":0,\"lose\":3,\"win\":0}},\"matches\":5}","matchTime":"2018/07/15 18:00:00"},{"id":321,"lotteryNo":"18082","matchNo":7,"league":"日乙","guestOdds":3.04,"hostTeam":"大宫松鼠","hostOdds":2.21,"hostRank":"[7]","hostHistory":"{\"total\":{\"score\":35,\"even\":5,\"lose\":7,\"asHost\":{\"score\":16,\"even\":4,\"lose\":3,\"win\":4},\"win\":10},\"recent\":{\"even\":3,\"lose\":2,\"asHost\":{\"even\":1,\"lose\":1,\"win\":4},\"matches\":10,\"win\":5,\"asGuest\":{\"even\":1,\"lose\":1,\"win\":4}}}","guestTeam":"大分三神","guestRank":"[2]","guestHistory":"{\"total\":{\"score\":40,\"even\":4,\"lose\":6,\"win\":12,\"asGuest\":{\"score\":18,\"even\":0,\"lose\":4,\"win\":6}},\"recent\":{\"even\":1,\"lose\":5,\"asHost\":{\"even\":0,\"lose\":3,\"win\":1},\"matches\":10,\"win\":4,\"asGuest\":{\"even\":0,\"lose\":3,\"win\":1}}}","evenOdds":3.23,"versusHistory":"{\"host\":{\"even\":2,\"lose\":2,\"asHost\":{\"even\":0,\"lose\":1,\"win\":3},\"win\":6},\"guest\":{\"even\":2,\"lose\":6,\"win\":2,\"asGuest\":{\"even\":0,\"lose\":3,\"win\":1}},\"matches\":10}","matchTime":"2018/07/15 18:00:00"},{"id":322,"lotteryNo":"18082","matchNo":8,"league":"瑞超","guestOdds":4.92,"hostTeam":"达尔库尔德人","hostOdds":1.66,"hostRank":"[15]","hostHistory":"{\"recent\":{\"even\":2,\"lose\":0,\"asHost\":{\"even\":1,\"lose\":0,\"win\":2},\"matches\":10,\"win\":8,\"asGuest\":{\"even\":1,\"lose\":0,\"win\":2}}}","guestTeam":"特雷勒堡","guestRank":"[13]","guestHistory":"{\"recent\":{\"even\":3,\"lose\":2,\"asHost\":{\"even\":1,\"lose\":2,\"win\":3},\"matches\":10,\"win\":5,\"asGuest\":{\"even\":1,\"lose\":2,\"win\":3}}}","evenOdds":3.65,"versusHistory":"{\"host\":{\"even\":2,\"lose\":0,\"asHost\":{\"even\":1,\"lose\":0,\"win\":1},\"win\":1},\"guest\":{\"even\":2,\"lose\":1,\"win\":0,\"asGuest\":{\"even\":1,\"lose\":1,\"win\":0}},\"matches\":3}","matchTime":"2018/07/15 00:00:00"},{"id":323,"lotteryNo":"18082","matchNo":9,"league":"瑞超","guestOdds":3.11,"hostTeam":"布鲁马波卡纳","hostOdds":2.16,"hostRank":"[14]","hostHistory":"{\"total\":{\"score\":10,\"even\":1,\"lose\":8,\"asHost\":{\"score\":10,\"even\":1,\"lose\":2,\"win\":3},\"win\":3},\"recent\":{\"even\":1,\"lose\":7,\"asHost\":{\"even\":0,\"lose\":5,\"win\":0},\"matches\":10,\"win\":2,\"asGuest\":{\"even\":0,\"lose\":5,\"win\":0}}}","guestTeam":"天狼星","guestRank":"[16]","guestHistory":"{\"total\":{\"score\":5,\"even\":2,\"lose\":10,\"win\":1,\"asGuest\":{\"score\":3,\"even\":0,\"lose\":5,\"win\":1}},\"recent\":{\"even\":1,\"lose\":8,\"asHost\":{\"even\":0,\"lose\":3,\"win\":0},\"matches\":10,\"win\":1,\"asGuest\":{\"even\":0,\"lose\":3,\"win\":0}}}","evenOdds":3.37,"versusHistory":"{\"host\":{\"even\":3,\"lose\":2,\"asHost\":{\"even\":0,\"lose\":1,\"win\":3},\"win\":4},\"guest\":{\"even\":3,\"lose\":4,\"win\":2,\"asGuest\":{\"even\":0,\"lose\":3,\"win\":1}},\"matches\":9}","matchTime":"2018/07/15 00:00:00"},{"id":324,"lotteryNo":"18082","matchNo":10,"league":"瑞超","guestOdds":4.85,"hostTeam":"马尔默","hostOdds":1.63,"hostRank":"[10]","hostHistory":"{\"total\":{\"score\":18,\"even\":3,\"lose\":5,\"asHost\":{\"score\":11,\"even\":2,\"lose\":1,\"win\":3},\"win\":5},\"recent\":{\"even\":1,\"lose\":5,\"asHost\":{\"even\":0,\"lose\":4,\"win\":2},\"matches\":10,\"win\":4,\"asGuest\":{\"even\":0,\"lose\":4,\"win\":2}}}","guestTeam":"厄斯特松德","guestRank":"[5]","guestHistory":"{\"total\":{\"score\":20,\"even\":2,\"lose\":4,\"win\":6,\"asGuest\":{\"score\":11,\"even\":2,\"lose\":1,\"win\":3}},\"recent\":{\"even\":3,\"lose\":2,\"asHost\":{\"even\":3,\"lose\":0,\"win\":3},\"matches\":10,\"win\":5,\"asGuest\":{\"even\":3,\"lose\":0,\"win\":3}}}","evenOdds":3.84,"versusHistory":"{\"host\":{\"even\":1,\"lose\":1,\"asHost\":{\"even\":0,\"lose\":1,\"win\":1},\"win\":3},\"guest\":{\"even\":1,\"lose\":3,\"win\":1,\"asGuest\":{\"even\":0,\"lose\":1,\"win\":1}},\"matches\":5}","matchTime":"2018/07/15 00:00:00"},{"id":325,"lotteryNo":"18082","matchNo":11,"league":"瑞超","guestOdds":2.9,"hostTeam":"IFK哥德堡","hostOdds":2.29,"hostRank":"[12]","hostHistory":"{\"total\":{\"score\":13,\"even\":1,\"lose\":7,\"asHost\":{\"score\":6,\"even\":0,\"lose\":4,\"win\":2},\"win\":4},\"recent\":{\"even\":1,\"lose\":6,\"asHost\":{\"even\":1,\"lose\":3,\"win\":1},\"matches\":10,\"win\":3,\"asGuest\":{\"even\":1,\"lose\":3,\"win\":1}}}","guestTeam":"厄勒布鲁","guestRank":"[7]","guestHistory":"{\"total\":{\"score\":19,\"even\":4,\"lose\":3,\"win\":5,\"asGuest\":{\"score\":11,\"even\":2,\"lose\":1,\"win\":3}},\"recent\":{\"even\":1,\"lose\":3,\"asHost\":{\"even\":1,\"lose\":1,\"win\":2},\"matches\":10,\"win\":6,\"asGuest\":{\"even\":1,\"lose\":1,\"win\":2}}}","evenOdds":3.35,"versusHistory":"{\"host\":{\"even\":1,\"lose\":3,\"asHost\":{\"even\":1,\"lose\":0,\"win\":4},\"win\":6},\"guest\":{\"even\":1,\"lose\":6,\"win\":3,\"asGuest\":{\"even\":1,\"lose\":4,\"win\":0}},\"matches\":10}","matchTime":"2018/07/15 02:00:00"},{"id":326,"lotteryNo":"18082","matchNo":12,"league":"瑞超","guestOdds":1.71,"hostTeam":"松兹瓦尔","hostOdds":4.72,"hostRank":"[9]","hostHistory":"{\"total\":{\"score\":24,\"even\":3,\"lose\":2,\"asHost\":{\"score\":15,\"even\":0,\"lose\":1,\"win\":5},\"win\":7},\"recent\":{\"even\":2,\"lose\":3,\"asHost\":{\"even\":2,\"lose\":1,\"win\":2},\"matches\":10,\"win\":5,\"asGuest\":{\"even\":2,\"lose\":1,\"win\":2}}}","guestTeam":"AIK索尔纳","guestRank":"[1]","guestHistory":"{\"total\":{\"score\":18,\"even\":3,\"lose\":4,\"win\":5,\"asGuest\":{\"score\":4,\"even\":1,\"lose\":4,\"win\":1}},\"recent\":{\"even\":1,\"lose\":4,\"asHost\":{\"even\":0,\"lose\":3,\"win\":1},\"matches\":10,\"win\":4,\"asGuest\":{\"even\":0,\"lose\":3,\"win\":1}}}","evenOdds":3.51,"versusHistory":"{\"host\":{\"even\":1,\"lose\":3,\"asHost\":{\"even\":1,\"lose\":0,\"win\":3},\"win\":6},\"guest\":{\"even\":1,\"lose\":6,\"win\":3,\"asGuest\":{\"even\":1,\"lose\":3,\"win\":0}},\"matches\":10}","matchTime":"2018/07/15 20:00:00"},{"id":327,"lotteryNo":"18082","matchNo":13,"league":"瑞超","guestOdds":3.09,"hostTeam":"北雪平","hostOdds":2.16,"hostRank":"[3]","hostHistory":"{\"total\":{\"score\":18,\"even\":3,\"lose\":4,\"asHost\":{\"score\":14,\"even\":2,\"lose\":1,\"win\":4},\"win\":5},\"recent\":{\"even\":1,\"lose\":5,\"asHost\":{\"even\":1,\"lose\":4,\"win\":1},\"matches\":10,\"win\":4,\"asGuest\":{\"even\":1,\"lose\":4,\"win\":1}}}","guestTeam":"赫根","guestRank":"[8]","guestHistory":"{\"total\":{\"score\":27,\"even\":6,\"lose\":0,\"win\":7,\"asGuest\":{\"score\":13,\"even\":4,\"lose\":0,\"win\":3}},\"recent\":{\"even\":4,\"lose\":0,\"asHost\":{\"even\":2,\"lose\":0,\"win\":3},\"matches\":10,\"win\":5,\"asGuest\":{\"even\":2,\"lose\":0,\"win\":3}}}","evenOdds":3.39,"versusHistory":"{\"host\":{\"even\":5,\"lose\":5,\"asHost\":{\"even\":2,\"lose\":3,\"win\":0},\"win\":0},\"guest\":{\"even\":5,\"lose\":0,\"win\":5,\"asGuest\":{\"even\":2,\"lose\":0,\"win\":3}},\"matches\":10}","matchTime":"2018/07/15 20:00:00"},{"id":328,"lotteryNo":"18082","matchNo":14,"league":"瑞超","guestOdds":2.71,"hostTeam":"卡尔马","hostOdds":2.62,"hostRank":"[6]","hostHistory":"{\"total\":{\"score\":20,\"even\":2,\"lose\":4,\"asHost\":{\"score\":11,\"even\":2,\"lose\":0,\"win\":3},\"win\":6},\"recent\":{\"even\":2,\"lose\":3,\"asHost\":{\"even\":0,\"lose\":3,\"win\":3},\"matches\":10,\"win\":5,\"asGuest\":{\"even\":0,\"lose\":3,\"win\":3}}}","guestTeam":"佐加顿斯","guestRank":"[4]","guestHistory":"{\"total\":{\"score\":21,\"even\":3,\"lose\":4,\"win\":6,\"asGuest\":{\"score\":10,\"even\":1,\"lose\":3,\"win\":3}},\"recent\":{\"even\":1,\"lose\":3,\"asHost\":{\"even\":0,\"lose\":2,\"win\":3},\"matches\":10,\"win\":6,\"asGuest\":{\"even\":0,\"lose\":2,\"win\":3}}}","evenOdds":3.02,"versusHistory":"{\"host\":{\"even\":1,\"lose\":6,\"asHost\":{\"even\":0,\"lose\":4,\"win\":1},\"win\":3},\"guest\":{\"even\":1,\"lose\":3,\"win\":6,\"asGuest\":{\"even\":0,\"lose\":1,\"win\":4}},\"matches\":10}","matchTime":"2018/07/15 20:00:00"}]
     */

    private String lotteryNo;
    private String endTime;
    private String lotteryName;
    private List<MatchesBean> matches;
    private String onsale;//是否开售，1-是，0-否
    private String saleText;

    public String getSaleText() {
        return saleText;
    }

    public void setSaleText(String saleText) {
        this.saleText = saleText;
    }

    public String getOnsale() {
        return onsale;
    }

    public void setOnsale(String onsale) {
        this.onsale = onsale;
    }

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public List<MatchesBean> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchesBean> matches) {
        this.matches = matches;
    }

    public static class MatchesBean implements Serializable {
        /**
         * id : 315
         * lotteryNo : 18082
         * matchNo : 1
         * league : 世界杯
         * guestOdds : 3.22
         * hostTeam : 比利时
         * hostOdds : 2.18
         * hostRank : 世 [3]
         * hostHistory : {"recent":{"even":1,"lose":1,"asHost":{"even":0,"lose":1,"win":2},"matches":10,"win":8,"asGuest":{"even":0,"lose":1,"win":2}}}
         * guestTeam : 英格兰
         * guestRank : 世 [12]
         * guestHistory : {"recent":{"even":3,"lose":1,"asHost":{"even":2,"lose":0,"win":3},"matches":10,"win":6,"asGuest":{"even":2,"lose":0,"win":3}}}
         * evenOdds : 3.64
         * versusHistory : {"host":{"even":1,"lose":1,"asHost":{"even":0,"lose":0,"win":0},"win":1},"guest":{"even":1,"lose":1,"win":1,"asGuest":{"even":0,"lose":0,"win":0}},"matches":3}
         * matchTime : 2018/07/14 22:00:00
         */

        private int id;
        private String lotteryNo;
        private int matchNo;
        private String league;
        private double guestOdds;
        private String hostTeam;
        private double hostOdds;
        private String hostRank = "";
        private String hostHistory;
        private String guestTeam;
        private String guestRank = "";
        private String guestHistory;
        private double evenOdds;
        private String versusHistory;
        private String matchTime;
        private ArrayList selectItem = new ArrayList();
        private HistoryBean hostHistoryData;
        private HistoryBean guestHistoryData;
        private VersusHistoryBean versusHistoryBean;
        private boolean openDeatil = false; //打开明细
        private int guestHot; //1是热门
        private int hostHot;
        private String dataLink;


        private String hostShort;
        private String guestShort;


        public String getDataLink() {
            return dataLink;
        }

        public void setDataLink(String dataLink) {
            this.dataLink = dataLink;
        }

        public int getGuestHot() {
            return guestHot;
        }

        public void setGuestHot(int guestHot) {
            this.guestHot = guestHot;
        }

        public int getHostHot() {
            return hostHot;
        }

        public void setHostHot(int hostHot) {
            this.hostHot = hostHot;
        }

        public String getHostShort() {
            return hostShort;
        }

        public void setHostShort(String hostShort) {
            this.hostShort = hostShort;
        }

        public String getGuestShort() {
            return guestShort;
        }

        public void setGuestShort(String guestShort) {
            this.guestShort = guestShort;
        }

        public HistoryBean getHostHistoryData() {
            return hostHistoryData;
        }

        public void setHostHistoryData(HistoryBean hostHistoryData) {
            this.hostHistoryData = hostHistoryData;
        }

        public HistoryBean getGuestHistoryData() {
            return guestHistoryData;
        }

        public void setGuestHistoryData(HistoryBean guestHistoryData) {
            this.guestHistoryData = guestHistoryData;
        }

        public ArrayList<Integer> getSelectItem() {
            return selectItem;
        }


        public void setSelectItem(ArrayList selectItem) {
            this.selectItem = selectItem;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLotteryNo() {
            return lotteryNo;
        }

        public void setLotteryNo(String lotteryNo) {
            this.lotteryNo = lotteryNo;
        }

        public int getMatchNo() {
            return matchNo;
        }

        public void setMatchNo(int matchNo) {
            this.matchNo = matchNo;
        }

        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public double getGuestOdds() {
            return guestOdds;
        }

        public void setGuestOdds(double guestOdds) {
            this.guestOdds = guestOdds;
        }

        public String getHostTeam() {
            return hostTeam;
        }

        public void setHostTeam(String hostTeam) {
            this.hostTeam = hostTeam;
        }

        public double getHostOdds() {
            return hostOdds;
        }

        public void setHostOdds(double hostOdds) {
            this.hostOdds = hostOdds;
        }

        public String getHostRank() {
            return hostRank;
        }

        public void setHostRank(String hostRank) {
            this.hostRank = hostRank;
        }

        public String getHostHistory() {
            return hostHistory;
        }

        public void setHostHistory(String hostHistory) {
            hostHistoryData = new ResponseAnalyze<HistoryBean>().analyze(hostHistory, HistoryBean.class);
            this.hostHistory = hostHistory;
        }

        public boolean isOpenDeatil() {
            return openDeatil;
        }

        public void setOpenDeatil(boolean openDeatil) {
            this.openDeatil = openDeatil;
        }

        public String getGuestTeam() {
            return guestTeam;
        }

        public void setGuestTeam(String guestTeam) {
            this.guestTeam = guestTeam;
        }

        public String getGuestRank() {
            return guestRank;
        }

        public void setGuestRank(String guestRank) {
            this.guestRank = guestRank;
        }

        public String getGuestHistory() {
            return guestHistory;
        }

        public void setGuestHistory(String guestHistory) {
            guestHistoryData = new ResponseAnalyze<HistoryBean>().analyze(hostHistory, HistoryBean.class);
            this.guestHistory = guestHistory;
        }

        public double getEvenOdds() {
            return evenOdds;
        }

        public void setEvenOdds(double evenOdds) {
            this.evenOdds = evenOdds;
        }

        public String getVersusHistory() {
            return versusHistory;
        }

        public void setVersusHistory(String versusHistory) {
            versusHistoryBean = new ResponseAnalyze<VersusHistoryBean>().analyze(hostHistory, VersusHistoryBean.class);
            this.versusHistory = versusHistory;
        }

        public String getMatchTime() {
            return matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public VersusHistoryBean getVersusHistoryBean() {
            return versusHistoryBean;
        }

        public void setVersusHistoryBean(VersusHistoryBean versusHistoryBean) {
            this.versusHistoryBean = versusHistoryBean;
        }
    }
}
