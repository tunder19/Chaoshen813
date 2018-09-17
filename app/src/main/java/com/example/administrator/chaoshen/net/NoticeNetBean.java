package com.example.administrator.chaoshen.net;

import com.example.administrator.chaoshen.net.bean.BaseNetBean;

public class NoticeNetBean extends BaseNetBean {

    private int page;
    private int size=10;

    public NoticeNetBean(int pageNum ) {
        this.page = pageNum;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
