package com.example.administrator.chaoshen.net.bean;

public class RechargeMoneyNetBean extends BaseNetBean {
    private  String token;
    private  double money;
    private  int bankId	;
    private  int merchantId	;

    public RechargeMoneyNetBean(String token, double money, int bankId, int merchantId) {
        this.token = token;
        this.money = money;
        this.bankId = bankId;
        this.merchantId = merchantId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }
}
