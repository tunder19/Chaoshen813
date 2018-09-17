package com.example.administrator.chaoshen.net.bean;

public class WinLoseBetNetBean extends BaseNetBean{
    private String token;
    private String lotteryNo;
    private String rule;
    private String bet;
    private int times;

    public WinLoseBetNetBean(String token, String lotteryNo, String rule, String bet, int times) {
        this.token = token;
        this.lotteryNo = lotteryNo;
        this.rule = rule;
        this.bet = bet;
        this.times = times;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
