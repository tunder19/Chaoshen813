package com.example.administrator.chaoshen.bean;

public class LimitBean {

        /**
         * minTimes : 1
         * maxTimes : 999
         * maxAmout : 20000
         * lotteryType : r9
         */

        private String minTimes="1";
        private String maxTimes="999";
        private String maxAmout="99999";
        private String lotteryType;

        public String getMinTimes() {
            return minTimes;
        }

        public void setMinTimes(String minTimes) {
            this.minTimes = minTimes;
        }

        public String getMaxTimes() {
            return maxTimes;
        }

        public void setMaxTimes(String maxTimes) {
            this.maxTimes = maxTimes;
        }

        public String getMaxAmout() {
            return maxAmout;
        }

        public void setMaxAmout(String maxAmout) {
            this.maxAmout = maxAmout;
        }

        public String getLotteryType() {
            return lotteryType;
        }

        public void setLotteryType(String lotteryType) {
            this.lotteryType = lotteryType;
        }
}
