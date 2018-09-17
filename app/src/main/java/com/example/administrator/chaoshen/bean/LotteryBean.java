package com.example.administrator.chaoshen.bean;

import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;
import java.util.List;

public class LotteryBean implements Serializable , Cloneable{
    /**
     * lotteryType : ssq
     * lotteryName : 双色球
     * icon : http://lottery-1254240493.cosgz.myqcloud.com/app/20180718/7043ecd5998d430993113c945f8b2ea5.png
     * flag : 1
     * text : drtfg
     * rules : [{"rule":"ssqpt","ruleName":"普通投注","status":1},{"rule":"ssqdt","ruleName":"胆拖投注","status":1}]
     * url : http://192.168.1.80:3333/hubei11c5
     * animation : 1
     */

    private String lotteryType;
    private String lotteryName;
    private String icon;
    private int flag;
    private String text;
    private String url;
    private int animation;//是否启用动画效果, 1-是,0-否
    private List<RulesBean> rules;
    private String corner;
    private int isEnter;//1可进入，0不可进入
    private String outText;//不可进入提示语
    private int countDown;//是否需要倒计时，1是，0否
    private int  seconds;






    @Override
    public Object clone() {
        LotteryBean stu = null;
        try{

            stu = (LotteryBean)super.clone();
            XLog.e("----clone----user="+stu);
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
            XLog.e("----clone--e--user="+e);
        }
        return stu;
    }


    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getCountDown() {
        return countDown;
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }

    public int getIsEnter() {
        return isEnter;
    }

    public void setIsEnter(int isEnter) {
        this.isEnter = isEnter;
    }

    public String getOutText() {
        return outText;
    }

    public void setOutText(String outText) {
        this.outText = outText;
    }

    public String getCorner() {
        return corner;
    }

    public void setCorner(String corner) {
        this.corner = corner;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public List<RulesBean> getRules() {
        return rules;
    }

    public void setRules(List<RulesBean> rules) {
        this.rules = rules;
    }

    public static class RulesBean implements Serializable, Cloneable {
        /**
         * rule : ssqpt
         * ruleName : 普通投注
         * status : 1
         */

        private String rule;
        private String ruleName;
        private int status;

        @Override
        public Object clone() {
            RulesBean stu = null;
            try{

                stu = (RulesBean)super.clone();
                XLog.e("----clone----user="+stu);
            }catch(CloneNotSupportedException e) {
                e.printStackTrace();
                XLog.e("----clone--e--user="+e);
            }
            return stu;
        }


        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
