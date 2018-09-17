package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.List;

public class HasShowInformation implements Serializable{

    private List<String> hasShow;

    public HasShowInformation(List<String> hasShow) {
        this.hasShow = hasShow;
    }

    public List<String> getHasShow() {
        return hasShow;
    }

    public void setHasShow(List<String> hasShow) {
        this.hasShow = hasShow;
    }
}
