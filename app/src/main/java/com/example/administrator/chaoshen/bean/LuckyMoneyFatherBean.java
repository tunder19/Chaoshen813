package com.example.administrator.chaoshen.bean;

import java.util.List;

public class LuckyMoneyFatherBean {

        /**
         * reds : []
         * canUseds : 0
         */

        private int canUseds;
        private List<LuckyMoneyBean> reds;

        public int getCanUseds() {
            return canUseds;
        }

        public void setCanUseds(int canUseds) {
            this.canUseds = canUseds;
        }

        public List<LuckyMoneyBean> getReds() {
            return reds;
        }

        public void setReds(List<LuckyMoneyBean> reds) {
            this.reds = reds;
        }
}
