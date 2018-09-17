package com.example.administrator.chaoshen.bean;

import java.util.List;

public class CheckRechargeBean {
        private  int status; //支付订单ID状态：0未处理 1充值成功 2充值失败

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
