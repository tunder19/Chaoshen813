package com.example.administrator.chaoshen.bean;

import java.util.List;

public class NoticeBean {

        private List<NoticeListBean> noticeList;

        public List<NoticeListBean> getNoticeList() {
            return noticeList;
        }

        public void setNoticeList(List<NoticeListBean> noticeList) {
            this.noticeList = noticeList;
        }

        public static class NoticeListBean {
            /**
             * title : 世界杯决赛、三四名决赛现已开售
             * content : 世界杯三四名决赛：周六063 “比利时 VS 英格兰”、世界杯决赛：周日064 “法国 VS 克罗地亚”现已开售，请广大购彩者关注！
             * createTime : 2018-07-12 14:20:38
             * createType : 超级管理员
             * createId : FredCheng
             * updateTime : 2018-07-12 14:20:38
             * updateId : FredCheng
             * id : 7
             */

            private String title;
            private String content;
            private String createTime;
            private String createType;
            private String createId;
            private String updateTime;
            private String updateId;
            private int id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreateType() {
                return createType;
            }

            public void setCreateType(String createType) {
                this.createType = createType;
            }

            public String getCreateId() {
                return createId;
            }

            public void setCreateId(String createId) {
                this.createId = createId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateId() {
                return updateId;
            }

            public void setUpdateId(String updateId) {
                this.updateId = updateId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
}
