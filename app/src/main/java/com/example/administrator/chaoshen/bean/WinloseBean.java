package com.example.administrator.chaoshen.bean;

public  class WinloseBean {
    /**
     * lotteryNo : 18091
     * prizeTime : 2018-08-06 10:00:00
     * lotteryName : 任选9场/胜负彩
     * results : 3|3|1|1|0|3|0|1|1|0|3|1|1|1
     * lotteryType : winlose
     */

    private String lotteryNo;
    private String prizeTime;
    private String lotteryName;
    private String results;
    private String lotteryType;
    private String link;
    private int pushStatus=1;//赛果推送：1是开启，0是关闭


    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public String getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(String prizeTime) {
        this.prizeTime = prizeTime;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }
}