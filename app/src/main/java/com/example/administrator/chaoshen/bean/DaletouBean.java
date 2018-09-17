package com.example.administrator.chaoshen.bean;

public  class DaletouBean {
    /**
     * lotteryNo : 18092
     * prizeRemark : （每周一、三、六开奖）
     * endTime : 2018-08-08(周三)
     * state : 3
     * results : 05,07,14,18,19|04,10
     * lotteryType : daletou
     */

    private int lotteryNo;
    private String prizeRemark;
    private String endTime;
    private int state;
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

    public int getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(int lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public String getPrizeRemark() {
        return prizeRemark;
    }

    public void setPrizeRemark(String prizeRemark) {
        this.prizeRemark = prizeRemark;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
