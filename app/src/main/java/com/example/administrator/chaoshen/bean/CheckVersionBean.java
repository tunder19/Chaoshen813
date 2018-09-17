package com.example.administrator.chaoshen.bean;

public class CheckVersionBean {

        /**
         * clientInfo : {"versionCode":10001,"version":"1.0.1","createTime":"2018-06-01 11:49:08","fileUrl":"https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/app-release.apk","needUpdate":0,"title":"升级测试","remark":"升级拉。","isOpen":1}
         */

        private ClientInfoBean clientInfo;

        public ClientInfoBean getClientInfo() {
            return clientInfo;
        }

        public void setClientInfo(ClientInfoBean clientInfo) {
            this.clientInfo = clientInfo;
        }

        public static class ClientInfoBean {
            /**
             * versionCode : 10001
             * version : 1.0.1
             * createTime : 2018-06-01 11:49:08
             * fileUrl : https://lottery-1256971398.cos.ap-guangzhou.myqcloud.com/app-release.apk
             * needUpdate : 0
             * title : 升级测试
             * remark : 升级拉。
             * isOpen : 1
             */

            private int versionCode;
            private String version;
            private String createTime;
            private String fileUrl;
            private int needUpdate;
            private String title;
            private String remark;
            private int isOpen;

            public int getVersionCode() {
                return versionCode;
            }

            public void setVersionCode(int versionCode) {
                this.versionCode = versionCode;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getFileUrl() {
                return fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }

            public int getNeedUpdate() {
                return needUpdate;
            }

            public void setNeedUpdate(int needUpdate) {
                this.needUpdate = needUpdate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getIsOpen() {
                return isOpen;
            }

            public void setIsOpen(int isOpen) {
                this.isOpen = isOpen;
            }
        }
}
