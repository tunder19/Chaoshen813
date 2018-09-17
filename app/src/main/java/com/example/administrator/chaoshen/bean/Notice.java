package com.example.administrator.chaoshen.bean;

import android.text.TextUtils;

public class Notice {
    private String text="";
    private int type=0 ;
    private String icon="";

    public Notice(String text, int type, String icon) {
        this.text = text;
        this.type = type;
        if (!TextUtils.isEmpty(icon)){
            this.icon = icon;
        }

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
