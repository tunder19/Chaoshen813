package com.example.administrator.chaoshen.bean;

import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;

public class SettingBean implements Serializable, Cloneable {
    private int rechargeMode;//系统设置,rechargeMode（充值模式）：0是预充模式，1是直接支付模式


    @Override
    public Object clone() {
        ConstsBean stu = null;
        try {

            stu = (ConstsBean) super.clone();
            XLog.e("----clone----user=" + stu);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            XLog.e("----clone--e--user=" + e);
        }
        return stu;
    }


    public int getRechargeMode() {
        return rechargeMode;
    }

    public void setRechargeMode(int rechargeMode) {
        this.rechargeMode = rechargeMode;
    }


}
