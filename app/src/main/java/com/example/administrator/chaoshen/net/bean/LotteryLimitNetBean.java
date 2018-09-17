package com.example.administrator.chaoshen.net.bean;

import java.util.PrimitiveIterator;

public class LotteryLimitNetBean extends BaseNetBean {
    private  String lotteryType;


    public LotteryLimitNetBean(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }
}
