package com.example.administrator.chaoshen.bean;

import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;

public class IconsBean implements Serializable,Cloneable{

    @Override
    public Object clone() {
        IconsBean stu = null;
        try{

            stu = (IconsBean)super.clone();
            XLog.e("----clone----user="+stu);
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
            XLog.e("----clone--e--user="+e);
        }
        return stu;
    }



    /**
     * mine : http://lottery-1254240493.cosgz.myqcloud.com/app/20180702/17eb4e54b33b48698a12e12fa66f7b1a.jpg
     * infoOn : http://lottery-1254240493.cosgz.myqcloud.com/app/20180702/ba2b6fc493f343048573e623f0c03129.jpg
     * android : on
     * mineOn : http://lottery-1254240493.cosgz.myqcloud.com/app/20180702/d14ae5dee6d8494d8b7a2b2f173ced38.jpg
     * global : on
     * ios : on
     * prize : http://lottery-1254240493.cosgz.myqcloud.com/app/20180702/6b5cc7e47c6b430bbfe66f269e929497.jpg
     * prizeOn : http://lottery-1254240493.cosgz.myqcloud.com/app/20180702/876dab0b299c4d0789ea2c119d48e0fe.jpg
     * service : http://lottery-1254240493.cosgz.myqcloud.com/app/20180702/6a4c216b64614ae2bad9eaba3056e818.gif
     * background : off
     * serviceOn : http://lottery-1254240493.cosgz.myqcloud.com/app/20180702/13192fda7bac4886aed8c7336b864104.jpg
     * info : http://lottery-1254240493.cosgz.myqcloud.com/app/20180702/844fbc1412ae425c8d6d977b6918f4c5.jpg
     */

    private String mine;
    private String infoOn;
    private String android;
    private String mineOn;
    private String global;
    private String ios;
    private String prize;
    private String prizeOn;
    private String service;
    private String background;
    private String serviceOn;
    private String info;

    public String getMine() {
        return mine;
    }

    public void setMine(String mine) {
        this.mine = mine;
    }

    public String getInfoOn() {
        return infoOn;
    }

    public void setInfoOn(String infoOn) {
        this.infoOn = infoOn;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getMineOn() {
        return mineOn;
    }

    public void setMineOn(String mineOn) {
        this.mineOn = mineOn;
    }

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getPrizeOn() {
        return prizeOn;
    }

    public void setPrizeOn(String prizeOn) {
        this.prizeOn = prizeOn;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getServiceOn() {
        return serviceOn;
    }

    public void setServiceOn(String serviceOn) {
        this.serviceOn = serviceOn;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
