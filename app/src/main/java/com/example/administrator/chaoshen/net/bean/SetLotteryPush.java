package com.example.administrator.chaoshen.net.bean;

public class SetLotteryPush extends BaseNetBean {
    private String lotteryType;
    private int status; //1是开启，0是关闭

    public SetLotteryPush(String lotteryType, int status) {
        this.lotteryType = lotteryType;
        this.status = status;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
