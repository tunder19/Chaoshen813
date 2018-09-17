package com.example.administrator.chaoshen.bean;

import java.util.List;

public class M_NewLeagsBean {
    private List<M_NewLeaguesBean> leagues;
    private String isDeclare;

    public List<M_NewLeaguesBean> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<M_NewLeaguesBean> leagues) {
        this.leagues = leagues;
    }

    public String getIsDeclare() {
        return isDeclare;
    }

    public void setIsDeclare(String isDeclare) {
        this.isDeclare = isDeclare;
    }
}
