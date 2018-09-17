package com.example.administrator.chaoshen.net;

import android.text.TextUtils;

/**
 * Created by Change on 2015/4/20.
 */
public class Urls {

    private static final String H5_HOST = "http://www.dadasports.cn/";
    public static final String HOST_URL = "https://r209.chaoshenwan.com/lottery";         //正式接
    //public static final String HOST_URL = "http://192.168.1.78:8080";

    //    public static final String HOST_H5 = "https://r209.chaoshenwan.com/h5/";
    public static final String HOST_H5 = "http://192.168.1.80:3333/";


    // public static final String HOST_URL = "http://192.168.3.209:8080/";

    //public static final String HOST_URL = "http://pay.woyao518.com/lottery";
    // public static final String HOST_URL = "http://r208.chaoshenwan.com/lottery/";

    public static final String IMAGE_URL = "http://image.dadasports.cn/";


    private static final String SPRIT = "/";


    static class Group {

        //装备列表
        public static final String GROUP_EQUIPMENT = "equipment";
        public static final String WINLOSE = "winlose";
        public static final String USER = "user";
        public static final String ORDER = "order";
        public static final String JING_CAI = "jingcai";
        public static final String HIGH_FREQ = "highfreq";
        public static final String FOOTBALL = "football";

    }

    /**
     * 拼接url
     *
     * @param group
     * @param name
     * @return
     */
    static String joint(String group, String name) {
        if (TextUtils.isEmpty(group)) {
            return HOST_URL + SPRIT + name;
        } else if (TextUtils.isEmpty(name)) {
            return HOST_URL + SPRIT + group;
        } else if (TextUtils.isEmpty(name) & TextUtils.isEmpty(group)) {
            return HOST_URL;
        } else {
            return HOST_URL + SPRIT + group + SPRIT + name;
        }


    }

    /**
     * 获取图片完整URL
     *
     * @param imgUrl
     * @return
     */
    public static String getImageUrl(String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            imgUrl = "";
        } else if (imgUrl.startsWith("drawable")) {
            return imgUrl;
        } else if (!imgUrl.startsWith("http")) {
            if (imgUrl.startsWith("/")) {
                imgUrl = imgUrl.substring(1);
            }
            imgUrl = Urls.IMAGE_URL + imgUrl;
        }
        return imgUrl;
    }


    /**
     * 微信支付信息
     *
     * @param orderId
     * @return
     */
   /* public static final String postWeixinPayUrl(long orderId) {
        String weixin = orderId + "/weixin";
        return joint(Group.GROUP_PAY, weixin);
    }*/


    /**
     * 微信支付信息
     *
     * @param orderId
     * @return
     */
  /*  public static final String postAliPayUrl(long orderId) {
        String weixin = orderId + "/alipay";
        return joint(Group.GROUP_PAY, weixin);
    }*/


    /**
     * 获取装备列表
     */
    public static final String GROUP_USER_EQUIPMENT = joint(Group.GROUP_EQUIPMENT, "list ");

    /**
     * 测试接口
     */
    public static final String GET_TEST = joint("", "");

    public static final String GET_WINLOSE = joint(Group.WINLOSE, "matches");

    public static final String USER_LOGIN = joint(Group.USER, "mobileLogin");
    public static final String USER_LOGIN_THIRD = joint(Group.USER, "appSdkThirdLogin");

    public static final String WINLOSE_BET = joint(Group.WINLOSE, "bet");

    public static final String PAY_ORDER = joint(Group.ORDER, "payOrder");

    public static final String GET_USERINFO = joint(Group.USER, "getUserInfo");

    public static final String REGIST_USER = joint(Group.USER, "mobileReg");
    public static final String REGIST_GETVERIFYCODE = joint("", "verifyCode");

    public static final String FORGET_PASSWORD = joint(Group.USER, "forgetMobile");
    public static final String EDIT_USERINFO = joint(Group.USER, "editUserInfo");
    public static final String GET_BIND_CARD = joint(Group.USER, "bankcards");
    public static final String GET_ADDBANK = joint(Group.USER, "addbank");
    public static final String GET_BANKNAMEBYNO = joint(Group.USER, "getBankNameByNo");
    public static final String GET_THIRDLOGIN = joint(Group.USER, "thirdLogin");

    public static final String GET_BET = joint(Group.USER, "bets");
    public static final String APPLY_CASH = joint(Group.USER, "withdraw");
    public static final String QUERY_PAY_CHANNELS = joint(Group.USER, "queryPayChannels");
    public static final String RECHARGE_MONEY = joint(Group.USER, "rechargeMoney");

    public static final String UPLOADFILES = joint("", "uploadFiles");
    public static final String GET_RECHARGESTATUS = joint(Group.USER, "getRechargeStatus");
    public static final String EDIT_PASSWORD = joint(Group.USER, "editPassword");
    public static final String GET_USER_HEADICOS = joint(Group.USER, "getUserHeadIcos");
    public static final String GET_TRANSACTIONS = joint(Group.USER, "transactions");

    public static final String BET_DETAIL = joint(Group.USER, "betDetail");
    public static final String GET_TICKETS = joint(Group.USER, "tickets");
    public static final String GET_REDPACKET_LIST = joint(Group.USER, "getRedPacketList");
    public static final String GET_ADVERTLIST = joint("", "config");
    public static final String GET_ORDERTREDPACKETS = joint(Group.USER, "ordertRedPackets");
    public static final String GET_MESSAGES = joint("", "getMessages");
    public static final String GET_UNREAD_MSGCOUNT = joint("", "getUnreadMsgCount");
    public static final String DEL_ORDER = joint(Group.ORDER, "delOrder");
    public static final String LOTTERY_LIMIT = joint("", "lotteryLimit");
    public static final String GET_ARTICLE_CLASSIFY = joint("", "getArticleClassify");
    public static final String GET_ARTICLELIST = joint("", "getArticleList");

    public static final String CHECK_REDPACKET = joint(Group.USER, "checkRedPacket");
    public static final String USER_LOGOUT = joint(Group.USER, "userLogout");
    public static final String OPEN_PRIZES = joint("", "prizes");

    public static final String JING_CAI_PRIZES = joint(Group.JING_CAI, "scores");
    public static final String GET_CASHMODE_SETTINGLIST = joint("", "getCashModeSettingList");


    public static final String GET_LOTTERYS = joint("", "lotterys");

    public static final String GET_INFORMATION_BROADCASTLIST = joint("", "getInformationBroadcastList");
    public static final String GET_NOTICELIST = joint("", "getNoticeList");
    public static final String GET_ACTIVITYLIST = joint("", "getActivityList");

    public static final String GET_LASTCLIENT = joint("", "getLastClient");
    public static final String POPUP_ACTIVITY = joint("", "popupActivity");
    public static final String RECHARGE_CASHSETTING = joint("", "rechargeCashSetting");
    public static final String LOTTERY_COUNTDOWN = joint(Group.HIGH_FREQ, "countdown");
    public static final String USER_FEEDBACK = joint(Group.USER, "feedback");

    public static final String UPLOAD_PHONES = joint("", "phones");
    public static final String GET_ARTICLEADVERTLIST = joint("", "getArticleAdvertList");

    public static final String PRIZE_PUSH = joint(Group.USER, "prizePush");
    public static final String FOOTBALL = joint(Group.FOOTBALL, "leagues");

    public static final String FOOTBALL_POINTS = joint(Group.FOOTBALL, "points");
    public static final String FOOTBALL_PLAYERS = joint(Group.FOOTBALL, "players");
    public static final String FOOTBALL_MATCHES = joint(Group.FOOTBALL, "matches");

    public static final String FOOTBALL_VOTES = joint(Group.FOOTBALL, "votes");
    public static final String GET_MODEL = joint("", "getModel");
    public static final String FOOTBALL_TEAMS = joint(Group.FOOTBALL, "teams");

}
