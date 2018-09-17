package com.example.administrator.chaoshen.bean;

import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;

public class UserBean implements Serializable , Cloneable {

    /**
     * userId : 169048
     * token : 3485c4a65fc74a4da655d0969c7863bd
     * userName : sj_159*****454
     * nickname : 159*****454
     * mobile : 159*****454
     * headIco : http://static.jjcai.net/defaul.png
     * currency : 9.99989839E8
     * nonPayableAmount : 0.0
     * freezingBalance : 0.0
     * regTime : 2018/06/09 13:49:12
     * isBandQQ : 0
     * isBandWx : 0
     * regType : 0
     */

    private long userId;
    private String token;
    private String userName;
    private String nickname;
    private String mobile;
    private String headIco;
    private double currency;
    private double nonPayableAmount;
    private double freezingBalance;
    private String regTime;
    private int isBandQQ;
    private int isBandWx;
    private long thirdId; //第三方登录id
    private int regType;//登录方式：0手机，1是微信，2QQ，3微博
    private int loginType = 0;//手机登录类型  0：手机号  1：微信 2：QQ
    private int isRealName; //是否实名认证 0 否  1认证过
    private String idNum;//身份证


    @Override
    public Object clone() {
        UserBean stu = null;
        try{

            stu = (UserBean)super.clone();
            XLog.e("----clone----user="+stu);
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
            XLog.e("----clone--e--user="+e);
        }
        return stu;
    }


    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public int getIsRealName() {
        return isRealName;
    }

    public void setIsRealName(int isRealName) {
        this.isRealName = isRealName;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public long getUserId() {
        return userId;
    }

    public long getThirdId() {
        return thirdId;
    }

    public void setThirdId(long thirdId) {
        this.thirdId = thirdId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadIco() {
        return headIco;
    }

    public void setHeadIco(String headIco) {
        this.headIco = headIco;
    }

    public double getCurrency() {
        return currency;
    }

    public void setCurrency(double currency) {
        this.currency = currency;
    }

    public double getNonPayableAmount() {
        return nonPayableAmount;
    }

    public void setNonPayableAmount(double nonPayableAmount) {
        this.nonPayableAmount = nonPayableAmount;
    }

    public double getFreezingBalance() {
        return freezingBalance;
    }

    public void setFreezingBalance(double freezingBalance) {
        this.freezingBalance = freezingBalance;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public int getIsBandQQ() {
        return isBandQQ;
    }

    public void setIsBandQQ(int isBandQQ) {
        this.isBandQQ = isBandQQ;
    }

    public int getIsBandWx() {
        return isBandWx;
    }

    public void setIsBandWx(int isBandWx) {
        this.isBandWx = isBandWx;
    }

    public int getRegType() {
        return regType;
    }

    public void setRegType(int regType) {
        this.regType = regType;
    }
}
