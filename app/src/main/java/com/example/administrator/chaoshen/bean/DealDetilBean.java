package com.example.administrator.chaoshen.bean;

import com.example.administrator.chaoshen.info.BaseInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DealDetilBean extends BaseInfo {


    /**
     * head : {"msg":"成功","code":1}
     * data : [{"id":12241,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:50:17","updateTime":"2018-07-27 18:50:17","status":1,"balance":23814.209999999966,"orderId":"742","channelId":120},{"id":12231,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:49:48","updateTime":"2018-07-27 18:49:48","status":1,"balance":23834.209999999966,"orderId":"741","channelId":120},{"id":12221,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:46:36","updateTime":"2018-07-27 18:46:36","status":1,"balance":23854.209999999966,"orderId":"740","channelId":120},{"id":12211,"userId":169088,"amout":-60,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:46:04","updateTime":"2018-07-27 18:46:04","status":1,"balance":23874.209999999966,"orderId":"739","channelId":120},{"id":12201,"userId":169088,"amout":-60,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:42:55","updateTime":"2018-07-27 18:42:55","status":1,"balance":23934.209999999966,"orderId":"738","channelId":120},{"id":12191,"userId":169088,"amout":-200,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 18:40:47","updateTime":"2018-07-27 18:40:47","status":1,"balance":23994.209999999966,"orderId":"737","channelId":120},{"id":12081,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"充值成功","createTime":"2018-07-27 17:25:57","updateTime":"2018-07-27 17:25:57","status":1,"balance":0,"orderId":"414","channelId":120},{"id":12071,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"充值成功","createTime":"2018-07-27 17:22:39","updateTime":"2018-07-27 17:22:39","status":1,"balance":0,"orderId":"411","channelId":120},{"id":12061,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"订单付款","remark":"付款成功","createTime":"2018-07-27 17:07:49","updateTime":"2018-07-27 17:07:49","status":1,"balance":24194.18999999997,"orderId":"723","channelId":120},{"id":12011,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"充值成功","createTime":"2018-07-27 16:23:24","updateTime":"2018-07-27 16:23:24","status":1,"balance":0,"orderId":"389","channelId":120},{"id":12001,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"充值成功","createTime":"2018-07-27 16:22:58","updateTime":"2018-07-27 16:22:58","status":1,"balance":0,"orderId":"387","channelId":120},{"id":11511,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-26 10:22:49","updateTime":"2018-07-26 10:22:49","status":1,"balance":0,"orderId":"342","channelId":120},{"id":11491,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-26 10:17:32","updateTime":"2018-07-26 10:17:32","status":1,"balance":0,"orderId":"340","channelId":120},{"id":11481,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-26 10:13:59","updateTime":"2018-07-26 10:13:59","status":1,"balance":0,"orderId":"339","channelId":120},{"id":11471,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-26 10:06:51","updateTime":"2018-07-26 10:06:51","status":1,"balance":0,"orderId":"338","channelId":120},{"id":11461,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-26 10:02:20","updateTime":"2018-07-26 10:02:20","status":1,"balance":0,"orderId":"333","channelId":120},{"id":11311,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 20:59:21","updateTime":"2018-07-25 20:59:21","status":1,"balance":0,"orderId":"293","channelId":120},{"id":11301,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 20:57:59","updateTime":"2018-07-25 20:57:59","status":1,"balance":0,"orderId":"289","channelId":120},{"id":11281,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 20:55:57","updateTime":"2018-07-25 20:55:57","status":1,"balance":0,"orderId":"284","channelId":120},{"id":11261,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 20:47:11","updateTime":"2018-07-25 20:47:11","status":1,"balance":0,"orderId":"271","channelId":120},{"id":11251,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 20:44:15","updateTime":"2018-07-25 20:44:15","status":1,"balance":0,"orderId":"270","channelId":144},{"id":11091,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 14:29:32","updateTime":"2018-07-25 14:29:32","status":1,"balance":0,"orderId":"206","channelId":120},{"id":10931,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 13:56:03","updateTime":"2018-07-25 13:56:03","status":1,"balance":0,"orderId":"202","channelId":120},{"id":10901,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 11:20:57","updateTime":"2018-07-25 11:20:57","status":1,"balance":0,"orderId":"182","channelId":120},{"id":10891,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 11:19:01","updateTime":"2018-07-25 11:19:01","status":1,"balance":0,"orderId":"181","channelId":120},{"id":10881,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 11:15:39","updateTime":"2018-07-25 11:15:39","status":1,"balance":0,"orderId":"180","channelId":120},{"id":10831,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 10:50:04","updateTime":"2018-07-25 10:50:04","status":1,"balance":0,"orderId":"173","channelId":120},{"id":10821,"userId":169088,"amout":0.01,"source":1,"typeNote":"1","type":9,"typeDesc":"在线充值(第三方支付)","remark":"在线充值(第三方支付)","createTime":"2018-07-25 10:48:08","updateTime":"2018-07-25 10:48:08","status":1,"balance":0,"orderId":"171","channelId":120},{"id":10771,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 10:41:57","status":1,"balance":24214,"orderId":"","channelId":120},{"id":10741,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 10:29:40","status":1,"balance":24224,"orderId":"","channelId":120},{"id":10731,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 10:28:59","status":1,"balance":24234,"orderId":"","channelId":120},{"id":10721,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 10:24:38","status":1,"balance":24244,"orderId":"","channelId":120},{"id":10711,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 10:19:12","status":1,"balance":24254,"orderId":"","channelId":120},{"id":10701,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 10:19:03","status":1,"balance":24264,"orderId":"","channelId":120},{"id":10681,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 10:00:53","status":1,"balance":24274,"orderId":"","channelId":120},{"id":10671,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 09:56:33","status":1,"balance":24284,"orderId":"","channelId":120},{"id":10661,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-25 09:55:27","status":1,"balance":24294,"orderId":"","channelId":120},{"id":10051,"userId":169088,"amout":-2000,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 18:53:14","updateTime":"2018-07-23 18:53:14","status":1,"balance":24304,"orderId":"631","channelId":120},{"id":10041,"userId":169088,"amout":-40,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 18:52:53","updateTime":"2018-07-23 18:52:53","status":1,"balance":26304,"orderId":"630","channelId":120},{"id":10031,"userId":169088,"amout":-4070,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 18:51:34","updateTime":"2018-07-23 18:51:34","status":1,"balance":26344,"orderId":"629","channelId":120},{"id":10021,"userId":169088,"amout":-1100,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 18:49:28","updateTime":"2018-07-23 18:49:28","status":1,"balance":30414,"orderId":"628","channelId":120},{"id":10011,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 18:49:14","updateTime":"2018-07-23 18:49:14","status":1,"balance":31514,"orderId":"627","channelId":120},{"id":10001,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 18:43:26","updateTime":"2018-07-23 18:43:26","status":1,"balance":31534,"orderId":"623","channelId":120},{"id":9991,"userId":169088,"amout":-3984,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 18:38:52","updateTime":"2018-07-23 18:38:52","status":1,"balance":31554,"orderId":"618","channelId":120},{"id":9981,"userId":169088,"amout":-380,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 16:36:09","updateTime":"2018-07-23 16:36:09","status":1,"balance":35538,"orderId":"619","channelId":120},{"id":9971,"userId":169088,"amout":-40040,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 16:30:45","updateTime":"2018-07-23 16:30:45","status":1,"balance":35918,"orderId":"617","channelId":120},{"id":9951,"userId":169088,"amout":-4004,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 16:27:54","updateTime":"2018-07-23 16:27:54","status":1,"balance":75958,"orderId":"615","channelId":120},{"id":9941,"userId":169088,"amout":-6578,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 12:13:48","updateTime":"2018-07-23 12:13:48","status":1,"balance":79962,"orderId":"613","channelId":120},{"id":9931,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 11:58:34","updateTime":"2018-07-23 11:58:34","status":1,"balance":86540,"orderId":"614","channelId":120},{"id":9921,"userId":169088,"amout":-160,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 11:55:12","updateTime":"2018-07-23 11:55:12","status":1,"balance":86560,"orderId":"611","channelId":120},{"id":9911,"userId":169088,"amout":160,"source":1,"typeNote":"1","type":7,"typeDesc":"系统撤单(订单退款)","remark":"系统撤单(订单退款)","createTime":"2018-07-23 11:46:37","updateTime":"2018-07-23 11:46:37","status":1,"balance":0,"orderId":"609","channelId":120},{"id":9901,"userId":169088,"amout":-40040,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 11:44:04","updateTime":"2018-07-23 11:44:04","status":1,"balance":86560,"orderId":"610","channelId":120},{"id":9891,"userId":169088,"amout":-160,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 11:43:47","updateTime":"2018-07-23 11:43:47","status":1,"balance":126600,"orderId":"609","channelId":120},{"id":9881,"userId":169088,"amout":-13400,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 11:43:32","updateTime":"2018-07-23 11:43:32","status":1,"balance":126760,"orderId":"608","channelId":120},{"id":9871,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-23 11:43:04","updateTime":"2018-07-23 11:43:04","status":1,"balance":140160,"orderId":"607","channelId":120},{"id":9831,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-21 19:33:15","status":1,"balance":140180,"orderId":"","channelId":120},{"id":9821,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-21 19:32:31","status":1,"balance":140190,"orderId":"","channelId":120},{"id":9811,"userId":169088,"amout":-10,"source":1,"typeNote":"2","type":18,"typeDesc":"提现","createId":"169088","createTime":"2018-07-21 19:30:19","status":1,"balance":140200,"orderId":"","channelId":120},{"id":9791,"userId":169088,"amout":40040,"source":1,"typeNote":"1","type":7,"typeDesc":"系统撤单(订单退款)","remark":"系统撤单(订单退款)","createTime":"2018-07-21 18:22:21","updateTime":"2018-07-21 18:22:21","status":1,"balance":0,"orderId":"604","channelId":120},{"id":9781,"userId":169088,"amout":-20,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-21 18:22:16","updateTime":"2018-07-21 18:22:16","status":1,"balance":100170,"orderId":"604","channelId":120},{"id":9711,"userId":169088,"amout":81920,"source":1,"typeNote":"1","type":7,"typeDesc":"系统撤单(订单退款)","remark":"系统撤单(订单退款)","createTime":"2018-07-21 18:20:01","updateTime":"2018-07-21 18:20:01","status":1,"balance":0,"orderId":"600","channelId":120},{"id":9701,"userId":169088,"amout":-81920,"source":1,"typeNote":"2","type":15,"typeDesc":"余额支付订单","remark":"余额支付订单","createTime":"2018-07-21 18:20:00","updateTime":"2018-07-21 18:20:00","status":1,"balance":18270,"orderId":"600","channelId":120},{"id":9681,"userId":169088,"amout":100000,"source":3,"typeNote":"1","type":12,"typeDesc":"正常手动充值","remark":"测试","createId":"FredCheng","createTime":"2018-07-21 17:46:28","updateTime":"2018-07-21 17:46:28","status":1,"balance":0,"channelId":143},{"id":9021,"userId":169088,"amout":10,"source":3,"typeNote":"1","type":8,"typeDesc":"奖金派发","remark":"dsf","createId":"FredCheng","createTime":"2018-07-19 14:59:48","updateTime":"2018-07-19 14:59:48","status":1,"balance":0,"channelId":143},{"id":9001,"userId":169088,"amout":10,"source":3,"typeNote":"1","type":8,"typeDesc":"奖金派发","remark":"sdf","createId":"FredCheng","createTime":"2018-07-19 14:54:16","updateTime":"2018-07-19 14:54:16","status":1,"balance":0,"channelId":143},{"id":8991,"userId":169088,"amout":10,"source":3,"typeNote":"1","type":5,"typeDesc":"转账充值","remark":"dfg","createId":"FredCheng","createTime":"2018-07-19 14:52:15","updateTime":"2018-07-19 14:52:15","status":1,"balance":0,"channelId":143},{"id":8971,"userId":169088,"amout":10,"source":3,"typeNote":"1","type":4,"typeDesc":"活动赠送充值","remark":"dsf","createId":"FredCheng","createTime":"2018-07-19 14:50:34","updateTime":"2018-07-19 14:50:34","status":1,"balance":0,"channelId":143},{"id":8961,"userId":169088,"amout":10,"source":3,"typeNote":"1","type":3,"typeDesc":"内部充值","remark":"sdfs","createId":"FredCheng","createTime":"2018-07-19 14:50:11","updateTime":"2018-07-19 14:50:11","status":1,"balance":0,"channelId":143},{"id":8951,"userId":169088,"amout":20,"source":3,"typeNote":"1","type":8,"typeDesc":"奖金派发","remark":"dfg","createId":"FredCheng","createTime":"2018-07-19 14:49:00","updateTime":"2018-07-19 14:49:00","status":1,"balance":0,"channelId":143}]
     */

    @SerializedName("head")
    private HeadBean headX;
    private List<DataBean> data;

    public HeadBean getHeadX() {
        return headX;
    }

    public void setHeadX(HeadBean headX) {
        this.headX = headX;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class HeadBean {
        /**
         * msg : 成功
         * code : 1
         */

        private String msg;
        private int code;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static class DataBean {
        /**
         * id : 12241
         * userId : 169088
         * amout : -20
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
         * createId : 169088
         */

        private int id;
        private int userId;
        private int amout;
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
        private String createId;

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

        public int getAmout() {
            return amout;
        }

        public void setAmout(int amout) {
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

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }
    }
}
