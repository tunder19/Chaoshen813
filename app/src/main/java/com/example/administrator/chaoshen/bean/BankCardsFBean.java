package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.List;

public class BankCardsFBean implements Serializable {
    private List<BankCardsBean> list;

    public List<BankCardsBean> getList() {
        return list;
    }

    public void setList(List<BankCardsBean> list) {
        this.list = list;
    }
}
