package com.example.administrator.chaoshen.bean;


import java.io.Serializable;
import java.util.List;

public class InformationBean implements Serializable {

    private List<ArticleClassifyListBean> articleClassifyList;
    private String isDeclare;


    public String getIsDeclare() {
        return isDeclare;
    }

    public void setIsDeclare(String isDeclare) {
        this.isDeclare = isDeclare;
    }

    public List<ArticleClassifyListBean> getArticleClassifyList() {
        return articleClassifyList;
    }

    public void setArticleClassifyList(List<ArticleClassifyListBean> articleClassifyList) {
        this.articleClassifyList = articleClassifyList;
    }

    public static class ArticleClassifyListBean implements Serializable {
        /**
         * id : 11
         * name : 公益
         * pid : 0
         * labelNames : 胜负彩,公益
         * labelIds : 22,26
         * sort : 5
         * createId : FredCheng
         * createTime : 2018-07-06 11:01:48
         * updateId : FredCheng
         * updateTime : 2018-08-01 18:58:36
         */

        private long id;
        private String name;
        private int pid;
        private String labelNames;
        private String labelIds;
        private int sort;
        private String createId;
        private String createTime;
        private String updateId;
        private String updateTime;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getLabelNames() {
            return labelNames;
        }

        public void setLabelNames(String labelNames) {
            this.labelNames = labelNames;
        }

        public String getLabelIds() {
            return labelIds;
        }

        public void setLabelIds(String labelIds) {
            this.labelIds = labelIds;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateId() {
            return updateId;
        }

        public void setUpdateId(String updateId) {
            this.updateId = updateId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
