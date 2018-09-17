package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.List;

public class LuckyMoneyBean implements Serializable {
    /**
     * redType : 1
     * consumeAmount : 10.0
     * applyScope : 全场通用
     * redPacketAmount : 1.0
     * remark : 新手注册活动赠送
     * startTime : 2018-07-26 00:00:00
     * endTime : 2018-08-02 23:59:59
     * id : 101
     * type : 1
     * userId : 169118
     * status : 0
     * scopes : ["jingcai","sfc"]
     */

    private int redType;
    private double consumeAmount;
    private String applyScope;
    private double redPacketAmount;
    private String remark;
    private String startTime;
    private String endTime;
    private int id;
    private int type;
    private int userId;
    private int status;
    private List<String> scopes;
    private List<LotterysBean> lotterys;




    public static class LotterysBean implements Serializable {
        /**
         * link : https://r209.chaoshenwan.com/h5/ssq
         * lotteryType : ssq
         */

        private String link;
        private String lotteryType;
        private String icon;

        private String name;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLotteryType() {
            return lotteryType;
        }

        public void setLotteryType(String lotteryType) {
            this.lotteryType = lotteryType;
        }
    }

    public List<LotterysBean> getLotterys() {
        return lotterys;
    }

    public void setLotterys(List<LotterysBean> lotterys) {
        this.lotterys = lotterys;
    }

    public int getRedType() {
        return redType;
    }

    public void setRedType(int redType) {
        this.redType = redType;
    }

    public double getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(double consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public String getApplyScope() {
        return applyScope;
    }

    public void setApplyScope(String applyScope) {
        this.applyScope = applyScope;
    }

    public double getRedPacketAmount() {
        return redPacketAmount;
    }

    public void setRedPacketAmount(double redPacketAmount) {
        this.redPacketAmount = redPacketAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }
}
