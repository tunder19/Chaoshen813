package com.example.administrator.chaoshen.bean;

import java.util.List;

public class RechargeBean {

        /**
         * bankId : 5
         * merchantId : 17
         * bankName : 支付宝
         * bankLogo : http://jzj20170601-1252399895.cosgz.myqcloud.com/app/alipay.png
         */

        private int bankId;
        private int merchantId;
        private String bankName;
        private String bankLogo;

        public int getBankId() {
            return bankId;
        }

        public void setBankId(int bankId) {
            this.bankId = bankId;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankLogo() {
            return bankLogo;
        }

        public void setBankLogo(String bankLogo) {
            this.bankLogo = bankLogo;
        }
}
