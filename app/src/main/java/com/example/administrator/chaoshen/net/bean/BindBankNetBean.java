package com.example.administrator.chaoshen.net.bean;

public class BindBankNetBean extends BaseNetBean {

    public BindBankNetBean(String token, String cardNo, String cardType, String bankName, String mobile, int isDefault) {
        this.token = token;
        this.cardNo = cardNo;
        this.cardType = cardType;
        this.bankName = bankName;
        this.mobile = mobile;
        this.isDefault = isDefault;//0不是  1默认
    }

    private String token;
    private String cardNo;
    private String cardType;
    private String bankName;
    private String province;
    private String city;
    private String area;
    private String mobile;
    private int isDefault;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
