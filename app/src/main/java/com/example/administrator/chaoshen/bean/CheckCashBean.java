package com.example.administrator.chaoshen.bean;

import java.util.List;

public class CheckCashBean {

        private List<ModeListBean> modeList;

        public List<ModeListBean> getModeList() {
            return modeList;
        }

        public void setModeList(List<ModeListBean> modeList) {
            this.modeList = modeList;
        }

        public static class ModeListBean {
            /**
             * type : 0
             * sort : 1
             * title : 提现到支付宝
             * isRecommend : 1
             * status : 1
             * amount : 5000.0
             * note : 1、提现金额最低10元起，最高5万元；
             2、每人每日最多提现5次；
             3、到账时间视支付宝公司处理速度而定，一般不超过2小时。
             * id : 3
             */

            private int type;
            private int sort;
            private String title;
            private int isRecommend;
            private int status;
            private double amount;
            private String note;
            private int id;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(int isRecommend) {
                this.isRecommend = isRecommend;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
}
