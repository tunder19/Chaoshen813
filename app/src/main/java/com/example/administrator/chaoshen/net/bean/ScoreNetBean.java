package com.example.administrator.chaoshen.net.bean;

public class ScoreNetBean extends BaseNetBean {
    private int pageNum = 1;
    private int pageSize = 100;
    private String matchDate;

    public ScoreNetBean(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }
}
