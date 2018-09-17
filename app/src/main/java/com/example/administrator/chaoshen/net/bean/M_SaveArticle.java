package com.example.administrator.chaoshen.net.bean;

import com.example.administrator.chaoshen.bean.ArticleListBean;

import java.io.Serializable;
import java.util.List;

public class M_SaveArticle implements Serializable {
    private List<ArticleListBean> list;

    public List<ArticleListBean> getList() {
        return list;
    }

    public void setList(List<ArticleListBean> list) {
        this.list = list;
    }
}
