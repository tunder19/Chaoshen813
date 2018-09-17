package com.example.administrator.chaoshen.bean;

import java.util.List;

public class BetRecordBean {

    /**
     * statusTips : 已退款
     * icon : http://lottery-1254240493.cosgz.myqcloud.com/app/20180625/c8e251a53df4425a8a12e69fd7fa5a58.png
     * rule : r9
     * source : 1
     * type : 0 //0普通 1合买 2追号
     * times : 10
     * ruleName : 任选9场
     * id : 604
     * lotteryNo : 18085
     * amount : 20.0
     * finishTime : 2018-07-21 21:00:00
     * orderNo : 20180721159221809309
     * updateTime : 2018-07-21 18:22:20
     * bets : 1
     * userId : 169088
     * lotteryType : winlose
     * createTime : 2018-07-21 18:22:09
     * statusText : 已撤单
     * validTime : 2018-07-21 20:50:00
     * betContent : 0|0|0|0|0|0|0|0|0|0|0|0|0|0
     * lotteryName : 胜负彩
     * status : 6
     */

    private String statusTips;
    private String icon;
    private String rule;
    private int source;
    private int type;
    private int times;
    private String ruleName;
    private int id;
    private String lotteryNo;
    private double amount;
    private String finishTime;
    private String orderNo;
    private String updateTime;
    private int bets;
    private int userId;
    private String lotteryType;
    private String createTime;
    private String statusText;
    private String validTime;
    private String betContent;
    private String lotteryName;
    private int status;
    private String link;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatusTips() {
        return statusTips;
    }

    public void setStatusTips(String statusTips) {
        this.statusTips = statusTips;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getBets() {
        return bets;
    }

    public void setBets(int bets) {
        this.bets = bets;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getBetContent() {
        return betContent;
    }

    public void setBetContent(String betContent) {
        this.betContent = betContent;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
