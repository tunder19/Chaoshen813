package com.example.administrator.chaoshen.bean;

import java.io.Serializable;
import java.util.List;

public class BetDeatilBean implements Serializable {


    /**
     * head : {"msg":"成功","code":1}
     * data : {"statusTips":"投注站已完成出票","tickets":[{"times":10,"text":"出票成功","bets":2,"content":"0|0|0|1|1|1|1|0|0|0|0|10|0|0","status":4}],"icon":"http://lottery-1254240493.cosgz.myqcloud.com/app/20180625/c8e251a53df4425a8a12e69fd7fa5a58.png","rule":"sfc","source":1,"type":0,"times":10,"ruleName":"胜负彩","id":630,"lotteryNo":"18086","amount":40,"finishTime":"2018-07-25 23:00:00","orderNo":"20180723030521850378","reminder":"奖金直接打入您的账户，超过100万的大奖由客服通知您本人领奖。","updateTime":"2018-07-23 18:58:34","bets":2,"userId":169088,"lotteryType":"winlose","sendTime":"2018-07-23 18:50:58","ticketTime":"2018-07-23 18:56:24","createTime":"2018-07-23 18:52:50","statusText":"购买成功","validTime":"2018-07-25 22:50:00","betContent":[{"result":"3","score":"2:0","league":"国冠杯","match":1,"host":"尤文图斯","guest":"拜仁慕尼黑","content":"0"},{"result":"1","score":"2:2","league":"国冠杯","match":2,"host":"多特蒙德","guest":"本菲卡","content":"0"},{"result":"0","score":"1:2","league":"国冠杯","match":3,"host":"曼彻斯特城","guest":"利物浦","content":"0"},{"result":"0","score":"1:4","league":"国冠杯","match":4,"host":"罗马","guest":"托特纳姆热刺","content":"1"},{"result":"1","score":"1:1","league":"国冠杯","match":5,"host":"AC米兰","guest":"曼彻斯特联","content":"1"},{"score":"VS","league":"国冠杯","match":6,"host":"马德里竞技","guest":"阿森纳","content":"1"},{"score":"VS","league":"欧罗巴","match":7,"host":"阿伯丁","guest":"伯恩利","content":"1"},{"score":"VS","league":"欧罗巴","match":8,"host":"布列斯特迪纳摩","guest":"阿特罗米托斯","content":"0"},{"score":"VS","league":"欧罗巴","match":9,"host":"比亚韦斯托克","guest":"里奥阿维","content":"0"},{"score":"VS","league":"欧罗巴","match":10,"host":"LASK林茨","guest":"利勒斯特罗姆","content":"0"},{"score":"VS","league":"巴甲","match":11,"host":"米内罗美洲","guest":"巴西国际","content":"0"},{"result":"3","score":"2:0","league":"巴甲","match":12,"host":"米内罗竞技","guest":"巴拉纳","content":"10"},{"result":"3","score":"2:0","league":"巴甲","match":13,"host":"科林蒂安","guest":"克鲁塞罗","content":"0"},{"score":"VS","league":"巴甲","match":14,"host":"格雷米奥","guest":"圣保罗","content":"0"}],"lotteryName":"胜负彩","status":7}
     */
    /**
     * statusTips : 投注站已完成出票
     * tickets : [{"times":10,"text":"出票成功","bets":2,"content":"0|0|0|1|1|1|1|0|0|0|0|10|0|0","status":4}]
     * icon : http://lottery-1254240493.cosgz.myqcloud.com/app/20180625/c8e251a53df4425a8a12e69fd7fa5a58.png
     * rule : sfc
     * source : 1
     * type : 0
     * times : 10
     * ruleName : 胜负彩
     * id : 630
     * lotteryNo : 18086
     * amount : 40.0
     * finishTime : 2018-07-25 23:00:00
     * orderNo : 20180723030521850378
     * reminder : 奖金直接打入您的账户，超过100万的大奖由客服通知您本人领奖。
     * updateTime : 2018-07-23 18:58:34
     * bets : 2
     * userId : 169088
     * lotteryType : winlose
     * sendTime : 2018-07-23 18:50:58
     * ticketTime : 2018-07-23 18:56:24
     * createTime : 2018-07-23 18:52:50
     * statusText : 购买成功
     * validTime : 2018-07-25 22:50:00
     * betContent : [{"result":"3","score":"2:0","league":"国冠杯","match":1,"host":"尤文图斯","guest":"拜仁慕尼黑","content":"0"},{"result":"1","score":"2:2","league":"国冠杯","match":2,"host":"多特蒙德","guest":"本菲卡","content":"0"},{"result":"0","score":"1:2","league":"国冠杯","match":3,"host":"曼彻斯特城","guest":"利物浦","content":"0"},{"result":"0","score":"1:4","league":"国冠杯","match":4,"host":"罗马","guest":"托特纳姆热刺","content":"1"},{"result":"1","score":"1:1","league":"国冠杯","match":5,"host":"AC米兰","guest":"曼彻斯特联","content":"1"},{"score":"VS","league":"国冠杯","match":6,"host":"马德里竞技","guest":"阿森纳","content":"1"},{"score":"VS","league":"欧罗巴","match":7,"host":"阿伯丁","guest":"伯恩利","content":"1"},{"score":"VS","league":"欧罗巴","match":8,"host":"布列斯特迪纳摩","guest":"阿特罗米托斯","content":"0"},{"score":"VS","league":"欧罗巴","match":9,"host":"比亚韦斯托克","guest":"里奥阿维","content":"0"},{"score":"VS","league":"欧罗巴","match":10,"host":"LASK林茨","guest":"利勒斯特罗姆","content":"0"},{"score":"VS","league":"巴甲","match":11,"host":"米内罗美洲","guest":"巴西国际","content":"0"},{"result":"3","score":"2:0","league":"巴甲","match":12,"host":"米内罗竞技","guest":"巴拉纳","content":"10"},{"result":"3","score":"2:0","league":"巴甲","match":13,"host":"科林蒂安","guest":"克鲁塞罗","content":"0"},{"score":"VS","league":"巴甲","match":14,"host":"格雷米奥","guest":"圣保罗","content":"0"}]
     * lotteryName : 胜负彩
     * status : 7
     */

    private String statusTips = "";
    private String icon = "";
    private String rule = "";
    private int source;
    private int type;
    private int times;
    private String ruleName = "";
    private int id;
    private String lotteryNo = "";
    private double amount;
    private String finishTime = "";
    private String orderNo = "";
    private String reminder = "";
    private String updateTime = "";
    private int bets;
    private int userId;
    private String lotteryType = "";
    private String sendTime = "";
    private String ticketTime = "";
    private String createTime = "";
    private String statusText = "";
    private String validTime = "";
    private String lotteryName = "";
    private int status;
    private List<TicketsBean> tickets;
    private List<BetContentBean> betContent;
    private String payDetail = "";
    private long orderId;
    private String passType;
    private double taxedBonus; //税后
    private double actualBonus;
    private String refunds;
    private String minEstimateBonus;
    private String maxEstimateBonus;
    private String inivitQrcode;
    private String prizeTime;
    private String matchNum;


    public String getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(String matchNum) {
        this.matchNum = matchNum;
    }

    public String getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(String prizeTime) {
        this.prizeTime = prizeTime;
    }

    public String getInivitQrcode() {
        return inivitQrcode;
    }

    public void setInivitQrcode(String inivitQrcode) {
        this.inivitQrcode = inivitQrcode;
    }

    public String getMinEstimateBonus() {
        return minEstimateBonus;
    }

    public void setMinEstimateBonus(String minEstimateBonus) {
        this.minEstimateBonus = minEstimateBonus;
    }

    public String getMaxEstimateBonus() {
        return maxEstimateBonus;
    }

    public void setMaxEstimateBonus(String maxEstimateBonus) {
        this.maxEstimateBonus = maxEstimateBonus;
    }

    public double getTaxedBonus() {
        return taxedBonus;
    }

    public void setTaxedBonus(double taxedBonus) {
        this.taxedBonus = taxedBonus;
    }

    public double getActualBonus() {
        return actualBonus;
    }

    public void setActualBonus(double actualBonus) {
        this.actualBonus = actualBonus;
    }

    public String getRefunds() {
        return refunds;
    }

    public void setRefunds(String refunds) {
        this.refunds = refunds;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatusTips() {
        return statusTips;
    }

    public void setStatusTips(String statusTips) {
        this.statusTips = statusTips;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getBets() {
        return bets;
    }

    public void setBets(int bets) {
        this.bets = bets;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TicketsBean> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketsBean> tickets) {
        this.tickets = tickets;
    }

    public List<BetContentBean> getBetContent() {
        return betContent;
    }

    public void setBetContent(List<BetContentBean> betContent) {
        this.betContent = betContent;
    }

    public static class TicketsBean implements Serializable {
        /**
         * times : 10
         * text : 出票成功
         * bets : 2
         * content : 0|0|0|1|1|1|1|0|0|0|0|10|0|0
         * status : 4
         */

        private int times;
        private String text;
        private int bets;
        private String content;
        private int status;

        private String passType;
        private String tips;


        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getPassType() {
            return passType;
        }

        public void setPassType(String passType) {
            this.passType = passType;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getBets() {
            return bets;
        }

        public void setBets(int bets) {
            this.bets = bets;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class BetContentBean implements Serializable {
        /**
         * result : 3
         * score : 2:0
         * league : 国冠杯
         * match : 1
         * host : 尤文图斯
         * guest : 拜仁慕尼黑
         * content : 0
         */

        private String result;
        private String score;
        private String league;
        private String match;
        private String host;
        private String guest;
        private String content;

        private List<RulesBean> rules;

        public List<RulesBean> getRules() {
            return rules;
        }

        public void setRules(List<RulesBean> rules) {
            this.rules = rules;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public String getMatch() {
            return match;
        }

        public void setMatch(String match) {
            this.match = match;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getGuest() {
            return guest;
        }

        public void setGuest(String guest) {
            this.guest = guest;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        public static class RulesBean implements Serializable {
            /**
             * result : 让球负
             * ruleName : 让球(1)
             * content : 让球平,让球负
             */

            private String result;
            private String ruleName;
            private String content;

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String getRuleName() {
                return ruleName;
            }

            public void setRuleName(String ruleName) {
                this.ruleName = ruleName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
