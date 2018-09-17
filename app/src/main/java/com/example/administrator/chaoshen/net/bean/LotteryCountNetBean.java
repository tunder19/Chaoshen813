package com.example.administrator.chaoshen.net.bean;

public class LotteryCountNetBean extends BaseNetBean {
    private String lotteryType;

    public LotteryCountNetBean(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }
}
