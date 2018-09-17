package com.example.administrator.chaoshen.bean;

public class WinLoseDataBean {
    private  int  selPosition;
    private  WinloseMatchesBean bean;

    public WinLoseDataBean(int selPosition, WinloseMatchesBean bean) {
        this.selPosition = selPosition;
        this.bean = bean;
    }

    public int getSelPosition() {
        return selPosition;
    }

    public void setSelPosition(int selPosition) {
        this.selPosition = selPosition;
    }

    public WinloseMatchesBean getBean() {
        return bean;
    }

    public void setBean(WinloseMatchesBean bean) {
        this.bean = bean;
    }
}
