package com.example.administrator.chaoshen.bean;

public class SsqBean {


    /**
     * lotteryNo : 18100
     * pushStatus : 0
     * link : https://r209.chaoshenwan.com/h5/daletou/prizes
     * prizeRemark : （每周一、三、六开奖）
     * endTime : 2018-08-27(周一)
     * state : 3
     * results : 06,20,21,26,30|06,09
     * lotteryType : daletou
     */

    private int lotteryNo;
    private int pushStatus=1;
    private String link;
    private String prizeRemark;
    private String endTime;
    private int state;
    private String results;
    private String lotteryType;

    public int getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(int lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

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
