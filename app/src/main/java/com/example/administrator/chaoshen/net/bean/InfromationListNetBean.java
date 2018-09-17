package com.example.administrator.chaoshen.net.bean;

public class InfromationListNetBean extends BaseNetBean {
    private int pageNum;
    private int pageSize=20;
    private long classifyId;

    public InfromationListNetBean(int pageNum, long classifyId) {
        this.pageNum = pageNum;
        this.classifyId = classifyId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
