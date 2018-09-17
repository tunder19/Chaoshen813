package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.List;

public class InformationListBean  implements Serializable  {

        private List<ArticleListBean> articleList;

        public List<ArticleListBean> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<ArticleListBean> articleList) {
            this.articleList = articleList;
        }

}
