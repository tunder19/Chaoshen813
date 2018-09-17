package com.example.administrator.chaoshen.net.bean;

public class ApplyCashNetBean extends  BaseNetBean {
    private String token;
    private double amount;
    private String bankId;
    private int type;//0提现到支付宝，1提现到银行卡

    public ApplyCashNetBean(String token, double amount,String id,int type) {
        this.token = token;
        this.amount = amount;
        bankId=id;
        this.type=type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
