package com.example.administrator.chaoshen.bean;

import java.util.List;

public class MessageCenterBean {
        /**
         * flag : 1
         * type : 2
         * deviceId : 1
         * userId : 169088
         * content : 中心出票失败，方案撤单
         * pushId : 1
         * titile : 方案撤单
         * typeText : 撤单
         * publisher : 1
         * publisherText : 系统
         * id : 2
         * pushTime : 2018-07-31 10:24:00
         * status : 0
         */

        private int flag;
        private int type;
        private int deviceId;
        private int userId;
        private String content;
        private int pushId;
        private String titile;
        private String typeText;
        private int publisher;
        private String publisherText;
        private int id;
        private String pushTime;
        private int status;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPushId() {
            return pushId;
        }

        public void setPushId(int pushId) {
            this.pushId = pushId;
        }

        public String getTitile() {
            return titile;
        }

        public void setTitile(String titile) {
            this.titile = titile;
        }

        public String getTypeText() {
            return typeText;
        }

        public void setTypeText(String typeText) {
            this.typeText = typeText;
        }

        public int getPublisher() {
            return publisher;
        }

        public void setPublisher(int publisher) {
            this.publisher = publisher;
        }

        public String getPublisherText() {
            return publisherText;
        }

        public void setPublisherText(String publisherText) {
            this.publisherText = publisherText;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPushTime() {
            return pushTime;
        }

        public void setPushTime(String pushTime) {
            this.pushTime = pushTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
}
