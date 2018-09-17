package com.example.administrator.chaoshen.bean;

import com.example.administrator.chaoshen.info.BaseSignleInfo;

import java.io.Serializable;
import java.util.List;

public class MessageDataBean implements Serializable {

        private List<InformationBroadcastListBean > informationBroadcastList;

        public List<InformationBroadcastListBean> getInformationBroadcastList() {
            return informationBroadcastList;
        }

        public void setInformationBroadcastList(List<InformationBroadcastListBean> informationBroadcastList) {
            this.informationBroadcastList = informationBroadcastList;
        }

        public static class InformationBroadcastListBean implements Serializable{
            /**
             * mold : 中奖
             * category : 人工添加
             * buyTime : 2018-07-09 00:00:00
             * content : 大胜归来666喜中888.00元[竞彩足球混合过关]
             * createId : FredCheng
             * createTime : 2018-07-10 20:23:41
             * updateId : FredCheng
             * updateTime : 2018-07-10 20:23:41
             * id : 4
             */

            private String mold;
            private String category;
            private String buyTime;
            private String content;
            private String createId;
            private String createTime;
            private String updateId;
            private String updateTime;
            private int id;
            private String image;


            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getMold() {
                return mold;
            }

            public void setMold(String mold) {
                this.mold = mold;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getBuyTime() {
                return buyTime;
            }

            public void setBuyTime(String buyTime) {
                this.buyTime = buyTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
}
