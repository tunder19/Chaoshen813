package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class M_MatchListBean implements Serializable {
    private List<MatchListBean>  matchListBeanList=new ArrayList<>();

    public List<MatchListBean> getMatchListBeanList() {
        return matchListBeanList;
    }

    public void setMatchListBeanList(List<MatchListBean> matchListBeanList) {
        this.matchListBeanList = matchListBeanList;
    }
}
