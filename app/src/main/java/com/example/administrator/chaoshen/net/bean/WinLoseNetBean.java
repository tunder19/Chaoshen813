package com.example.administrator.chaoshen.net.bean;

public class WinLoseNetBean  extends BaseNetBean{
    private String rule;
    private String lotteryNo;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public WinLoseNetBean(String rule, String lotteryNo) {
        this.rule = rule;
        this.lotteryNo = lotteryNo;
    }
}
