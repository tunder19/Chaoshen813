package com.example.administrator.chaoshen.bean;

public class RefreshDataBean {
    private int type; //0是注册  1是找回密码 2绑定手机号码
    private Double money;//充值金额
    private boolean payOrderSuccess=false;


    public boolean isPayOrderSuccess() {
        return payOrderSuccess;
    }

    public void setPayOrderSuccess(boolean payOrderSuccess) {
        this.payOrderSuccess = payOrderSuccess;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
