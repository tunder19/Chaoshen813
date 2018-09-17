package com.example.administrator.chaoshen.bean;

public class RechargeLimitBean {

        /**
         * model : {"rechargeMinimumAmount":10,"rechargeFee":0,"rechargeNonPayable":30,"cashMinimumAmount":0.01,"cashFee":0,"cashDailyRate":5,"cashAuditAmount":100000,"note":"1、提现金额最低10元起；\n2、每人每日最多提现2次；\n3、到账时间视银行处理速度而定，一般不超过2小时；","id":1}
         */

        private ModelBean model;

        public ModelBean getModel() {
            return model;
        }

        public void setModel(ModelBean model) {
            this.model = model;
        }

        public static class ModelBean {
            /**
             * rechargeMinimumAmount : 10.0
             * rechargeFee : 0.0
             * rechargeNonPayable : 30.0
             * cashMinimumAmount : 0.01
             * cashFee : 0.0
             * cashDailyRate : 5
             * cashAuditAmount : 100000.0
             * note : 1、提现金额最低10元起；
             2、每人每日最多提现2次；
             3、到账时间视银行处理速度而定，一般不超过2小时；
             * id : 1
             */

            private double rechargeMinimumAmount;
            private double rechargeFee;
            private double rechargeNonPayable;
            private double cashMinimumAmount;
            private double cashFee;
            private int cashDailyRate;
            private double cashAuditAmount;
            private String note;
            private int id;

            public double getRechargeMinimumAmount() {
                return rechargeMinimumAmount;
            }

            public void setRechargeMinimumAmount(double rechargeMinimumAmount) {
                this.rechargeMinimumAmount = rechargeMinimumAmount;
            }

            public double getRechargeFee() {
                return rechargeFee;
            }

            public void setRechargeFee(double rechargeFee) {
                this.rechargeFee = rechargeFee;
            }

            public double getRechargeNonPayable() {
                return rechargeNonPayable;
            }

            public void setRechargeNonPayable(double rechargeNonPayable) {
                this.rechargeNonPayable = rechargeNonPayable;
            }

            public double getCashMinimumAmount() {
                return cashMinimumAmount;
            }

            public void setCashMinimumAmount(double cashMinimumAmount) {
                this.cashMinimumAmount = cashMinimumAmount;
            }

            public double getCashFee() {
                return cashFee;
            }

            public void setCashFee(double cashFee) {
                this.cashFee = cashFee;
            }

            public int getCashDailyRate() {
                return cashDailyRate;
            }

            public void setCashDailyRate(int cashDailyRate) {
                this.cashDailyRate = cashDailyRate;
            }

            public double getCashAuditAmount() {
                return cashAuditAmount;
            }

            public void setCashAuditAmount(double cashAuditAmount) {
                this.cashAuditAmount = cashAuditAmount;
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
