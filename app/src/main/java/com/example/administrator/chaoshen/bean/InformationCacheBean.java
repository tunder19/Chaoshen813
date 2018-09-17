package com.example.administrator.chaoshen.bean;

import android.content.Context;

import com.youth.xframe.cache.XCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InformationCacheBean implements Serializable {
    private long classifyId;
    private List<ArticleListBean> list;
    private int page;


    public long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(long classifyId) {
        this.classifyId = classifyId;
    }

    public List<ArticleListBean> getList() {
        return list;
    }

    public void setList(List<ArticleListBean> list) {
        this.list = list;
    }

    public InformationCacheBean(Context context, long classifyId, List<ArticleListBean> list,int page) {
        this.classifyId = classifyId;
            this.list = list;
            this.page=page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
