package com.example.administrator.chaoshen.bean;

import java.util.ArrayList;
import java.util.List;

public class WinloseFatherBean {
    private String status;//是否开售，1-是，0-否
    private List<WinloseMatchesBean> results;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WinloseMatchesBean> getList() {
        return results;
    }

    public void setList(List<WinloseMatchesBean> list) {
        this.results = list;
    }
}
