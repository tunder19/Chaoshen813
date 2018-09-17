package com.example.administrator.chaoshen.bean;

import com.example.administrator.chaoshen.info.BaseInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DealDeatilBean extends BaseInfo {

    /**
     * head : {"msg":"成功","code":1}
     * data : [{"id":12241,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:50:17","updateTime":"2018-07-27 18:50:17","status":1,"balance":23814.209999999966,"orderId":"742","channelId":120},{"id":12231,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:49:48","updateTime":"2018-07-27 18:49:48","status":1,"balance":23834.209999999966,"orderId":"741","channelId":120},{"id":12221,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:46:36","updateTime":"2018-07-27 18:46:36","status":1,"balance":23854.209999999966,"orderId":"740","channelId":120},{"id":12211,"userId":169088,"amout":-60,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:46:04","updateTime":"2018-07-27 18:46:04","status":1,"balance":23874.209999999966,"orderId":"739","channelId":120},{"id":12201,"userId":169088,"amout":-60,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:42:55","updateTime":"2018-07-27 18:42:55","status":1,"balance":23934.209999999966,"orderId":"738","channelId":120},{"id":12191,"userId":169088,"amout":-200,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:40:47","updateTime":"2018-07-27 18:40:47","status":1,"balance":23994.209999999966,"orderId":"737","channelId":120},{"id":12081,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"充值成功","createTime":"2018-07-27 17:25:57","updateTime":"2018-07-27 17:25:57","status":1,"balance":0,"orderId":"414","channelId":120},{"id":12071,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"充值成功","createTime":"2018-07-27 17:22:39","updateTime":"2018-07-27 17:22:39","status":1,"balance":0,"orderId":"411","channelId":120},{"id":12061,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 17:07:49","updateTime":"2018-07-27 17:07:49","status":1,"balance":24194.18999999997,"orderId":"723","channelId":120},{"id":12011,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"充值成功","createTime":"2018-07-27 16:23:24","updateTime":"2018-07-27 16:23:24","status":1,"balance":0,"orderId":"389","channelId":120}]
     */

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * id : 12241
         * userId : 169088
         * amout : -20.0
         * source : 1
         * typeNote : 2
         * type : 15
         * typeDesc : 订单付款
         * remark : 付款成功
         * createTime : 2018-07-27 18:50:17
         * updateTime : 2018-07-27 18:50:17
         * status : 1
         * balance : 23814.209999999966
         * orderId : 742
         * channelId : 120
         */

        private int id;
        private int userId;
        private double amout;
        private int source;
        private String typeNote;
        private int type;
        private String typeDesc;
        private String remark;
        private String createTime;
        private String updateTime;
        private int status;
        private double balance;
        private String orderId;
        private int channelId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public double getAmout() {
            return amout;
        }

        public void setAmout(double amout) {
            this.amout = amout;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getTypeNote() {
            return typeNote;
        }

        public void setTypeNote(String typeNote) {
            this.typeNote = typeNote;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getChannelId() {
            return channelId;
        }

        public void setChannelId(int channelId) {
            this.channelId = channelId;
        }
    }
}
