package com.example.administrator.chaoshen.bean;

public class OpenPriceBean {


    /**
     * hubei11c5 : {"lotteryNo":"18080881","no":81,"year":2018,"lotteryType":"hubei11c5","month":8,"prizeTime":"2018-08-08 09:47:00","missing":"9|1|6|4|3|2|2|8|0|6|6,4|9|7|2|4|8|1|4|8|7|4,4|2|9|8|5|7|5|8|1|0|7,9|1|6|4|3|2|2|8|0|6|6","startTime":"2018-08-08 21:45:00","endTime":"2018-08-08 21:55:00","id":3648,"state":3,"day":8,"results":"01|02|03|04|05"}
     * daletou : {"lotteryNo":18092,"prizeRemark":"（每周一、三、六开奖）","endTime":"2018-08-08(周三)","state":3,"results":"05,07,14,18,19|04,10","lotteryType":"daletou"}
     * winlose : {"lotteryNo":"18091","prizeTime":"2018-08-06 10:00:00","lotteryName":"任选9场/胜负彩","results":"3|3|1|1|0|3|0|1|1|0|3|1|1|1","lotteryType":"winlose"}
     * jingcai : {"bf":"1 - 2","hostShort":"首尔FC(-1)","guestShort":"济州联"}
     */

    private Hubei11select5Bean hubei11c5;
    private DaletouBean daletou;
    private WinloseBean winlose;
    private JingcaiBean jingcai;
    private SsqBean ssq;

    public SsqBean getSsq() {
        return ssq;
    }

    public void setSsq(SsqBean ssq) {
        this.ssq = ssq;
    }

    public Hubei11select5Bean getHubei11c5() {
        return hubei11c5;
    }

    public void setHubei11c5(Hubei11select5Bean hubei11c5) {
        this.hubei11c5 = hubei11c5;
    }

    public Hubei11select5Bean getHubei11select5() {
        return hubei11c5;
    }

    public void setHubei11select5(Hubei11select5Bean hubei11c5) {
        this.hubei11c5 = hubei11c5;
    }

    public DaletouBean getDaletou() {
        return daletou;
    }

    public void setDaletou(DaletouBean daletou) {
        this.daletou = daletou;
    }

    public WinloseBean getWinlose() {
        return winlose;
    }

    public void setWinlose(WinloseBean winlose) {
        this.winlose = winlose;
    }

    public JingcaiBean getJingcai() {
        return jingcai;
    }

    public void setJingcai(JingcaiBean jingcai) {
        this.jingcai = jingcai;
    }


}
