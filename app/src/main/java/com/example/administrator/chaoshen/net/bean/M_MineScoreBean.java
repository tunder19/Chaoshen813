package com.example.administrator.chaoshen.net.bean;

import java.lang.reflect.Array;

public class M_MineScoreBean extends  BaseNetBean {
    private int[]  matchIds;

    public M_MineScoreBean(int[] matchIds) {
        this.matchIds = matchIds;
    }
}
