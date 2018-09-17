package com.example.administrator.chaoshen.bean;

import java.util.List;

public class HuggBetDeatilBean {

        /**
         * statusTips :
         * tickets : [{"passType":"2串1,3串1","times":50,"text":"出票成功","bets":39,"content":"周一003[负(10.70),平(7.22),胜(4.19)]*周一005[胜(7.99),平(4.69),负(6.51)]*周一002[让球平(8.72),让球负(2.24)]","tips":"税前奖金19709.0元，税后奖金19709.5元","status":7}]
         * icon : http://lottery-1254240493.cosgz.myqcloud.com/app/20180625/13b366e35899426197e32fe9f7b1f0bf.png
         * rule : hhgg
         * untaxedBonus : 19709.0
         * source : 0
         * type : 0
         * passType : 2串1,3串1
         * times : 50
         * ruleName : 混合过关
         * id : 1004
         * lotteryNo : 20000
         * amount : 3900.0
         * finishTime : 2018-07-31 04:45:00
         * orderNo : 20180730202412036567
         * reminder : 奖金直接打入您的账户，超过100万的大奖由客服通知您本人领奖。
         * taxedBonus : 19709.5
         * actualBonus : 19709.5
         * updateTime : 2018-07-30 21:23:42
         * bets : 39
         * userId : 169098
         * lotteryType : jingcai
         * sendTime : 2018-07-30 20:40:00
         * extend : 20180730002aa^1218;20180730003aa^1219;20180730005aa^1251
         * ticketTime : 2018-07-30 21:22:47
         * matchNum : 3
         * createTime : 2018-07-30 20:41:36
         * statusText : 已中奖
         * validTime : 2018-07-30 22:44:00
         * betContent : [{"score":"1-3","match":"07-30周一002","host":"特雷勒堡","guest":"哈马比","rules":[{"result":"让球负","ruleName":"让球(1)","content":"让球平,让球负"}]},{"score":"0-1","match":"07-30周一003","host":"布雷斯特","guest":"梅斯","rules":[{"result":"负","ruleName":"胜平负","content":"负,平,胜"}]},{"score":"0-0","match":"07-30周一005","host":"乌法","guest":"莫火车头","rules":[{"result":"平","ruleName":"胜平负","content":"胜,平,负"}]}]
         * lotteryName : 竞彩足球
         * status : 11
         */

        private String statusTips;
        private String icon;
        private String rule;
        private double untaxedBonus;
        private int source;
        private int type;
        private String passType;
        private int times;
        private String ruleName;
        private int id;
        private String lotteryNo;
        private double amount;
        private String finishTime;
        private String orderNo;
        private String reminder;
        private double taxedBonus;
        private double actualBonus;
        private String updateTime;
        private int bets;
        private int userId;
        private String lotteryType;
        private String sendTime;
        private String extend;
        private String ticketTime;
        private int matchNum;
        private String createTime;
        private String statusText;
        private String validTime;
        private String lotteryName;
        private int status;
        private List<TicketsBean> tickets;
        private List<BetContentBean> betContent;

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

        public double getUntaxedBonus() {
            return untaxedBonus;
        }

        public void setUntaxedBonus(double untaxedBonus) {
            this.untaxedBonus = untaxedBonus;
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

        public String getExtend() {
            return extend;
        }

        public void setExtend(String extend) {
            this.extend = extend;
        }

        public String getTicketTime() {
            return ticketTime;
        }

        public void setTicketTime(String ticketTime) {
            this.ticketTime = ticketTime;
        }

        public int getMatchNum() {
            return matchNum;
        }

        public void setMatchNum(int matchNum) {
            this.matchNum = matchNum;
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

        public static class TicketsBean {
            /**
             * passType : 2串1,3串1
             * times : 50
             * text : 出票成功
             * bets : 39
             * content : 周一003[负(10.70),平(7.22),胜(4.19)]*周一005[胜(7.99),平(4.69),负(6.51)]*周一002[让球平(8.72),让球负(2.24)]
             * tips : 税前奖金19709.0元，税后奖金19709.5元
             * status : 7
             */

            private String passType;
            private int times;
            private String text;
            private int bets;
            private String content;
            private String tips;
            private int status;

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

            public String getTips() {
                return tips;
            }

            public void setTips(String tips) {
                this.tips = tips;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class BetContentBean {
            /**
             * score : 1-3
             * match : 07-30周一002
             * host : 特雷勒堡
             * guest : 哈马比
             * rules : [{"result":"让球负","ruleName":"让球(1)","content":"让球平,让球负"}]
             */

            private String score;
            private String match;
            private String host;
            private String guest;
            private List<RulesBean> rules;

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
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

            public List<RulesBean> getRules() {
                return rules;
            }

            public void setRules(List<RulesBean> rules) {
                this.rules = rules;
            }

            public static class RulesBean {
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
