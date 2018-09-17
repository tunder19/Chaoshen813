package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InformationList implements Serializable{
    private ArrayList<ArticleClassiBean> list=new ArrayList<>();

    public ArrayList<ArticleClassiBean> getList() {
        return list;
    }

    public void setList(ArrayList<ArticleClassiBean> list) {
        this.list = list;
    }
}
