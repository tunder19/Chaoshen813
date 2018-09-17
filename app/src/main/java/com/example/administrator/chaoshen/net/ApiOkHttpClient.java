package com.example.administrator.chaoshen.net;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;
import com.example.administrator.chaoshen.net.bean.CourData;
import com.example.administrator.chaoshen.net.bean.LoginThirdNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.net.bean.UploadPhoneNetBean;
import com.example.administrator.chaoshen.util.SystemUtil;
import com.google.gson.Gson;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XAppUtils;
import com.youth.xframe.utils.XNetworkUtils;
import com.youth.xframe.utils.log.XLog;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiOkHttpClient {
    private static Location mLocation;
    private static String rid;
    public static OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)//设置读取超时为10秒
            .connectTimeout(15, TimeUnit.SECONDS)//设置链接超时为10秒
            .writeTimeout(15,TimeUnit.SECONDS)//设置写的超时时间
            .retryOnConnectionFailure(false)
           .build();

    public static Gson gs = new Gson();
    //Request request = new Request.Builder();


    public static void postData(Context ctx, Object data, String url, Callback callback) {
        JSONObject jsonObject = new JSONObject();
        String objectStr;
        if (data == null) {
            objectStr = "";
        } else {
            objectStr = gs.toJson(data);
        }

        try {
            jsonObject.put("data", objectStr + "");
            jsonObject.put("channelId", "A100001");
            jsonObject.put("timestamp", getFormatTime(System.currentTimeMillis(), "yyyyMMddHHmmss"));
            jsonObject.put("platform", 1);
            jsonObject.put("version", XAppUtils.getVersionName(ctx) + "");


            if (XNetworkUtils.isWifiConnected()) {
                jsonObject.put("network", "wifi");
            } else if (XNetworkUtils.is4G()) {
                jsonObject.put("network", "4g");
            } else {
                jsonObject.put("network", "其他联网方式");
            }
            jsonObject.put("rid", rid + "");

            jsonObject.put("device", SystemUtil.getDeviceBrand() + SystemUtil.getSystemModel() + "");
            jsonObject.put("os", SystemUtil.getSystemVersion());
            XCache mcahe = XCache.get(ctx);
            String imei = "";
            if (!TextUtils.isEmpty(mcahe.getAsString(IntentKey.IMEI))) {
                //  mcahe.put(IntentKey.IMEI, mcahe.getAsString(IntentKey.IMEI));
                imei = mcahe.getAsString(IntentKey.IMEI);
                XLog.e("-------缓存获取------E1----" + mcahe.getAsString(IntentKey.IMEI));
            } else {
                try {
                    imei = SystemUtil.getIMEI(ctx);
                    mcahe.put(IntentKey.IMEI, imei);
                    XLog.e("------IMEI码-------E1----" + mcahe.getAsString(IntentKey.IMEI));
                } catch (Exception e) {

                    imei = SystemUtil.getUniquePsuedoID();
                    mcahe.put(IntentKey.IMEI, imei);
                    XLog.e("------虚拟ID-------E1----" + mcahe.getAsString(IntentKey.IMEI));
                }
            }
            jsonObject.put("sid", imei);
            //经纬度注入
            if (mLocation != null) {
                jsonObject.put("lng", mLocation.getLongitude());
                jsonObject.put("lat", mLocation.getLatitude());
            }
            XCache mCache = XCache.get(ctx);
            if (mCache.getAsObject(IntentKey.USER) != null && !TextUtils.isEmpty(((UserBean) mCache.getAsObject(IntentKey.USER)).getToken())) {
                jsonObject.put("token", ((UserBean) mCache.getAsObject(IntentKey.USER)).getToken());
            }
            jsonObject.put("productId", APP_Contants.getProductId());


            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            Request request = new Request.Builder()
                    .post(body)
                    .url(url).
                            build();
            client.newCall(request).enqueue(callback);
            printRequestLogForPost("Post", url, jsonObject);
        } catch (Exception e1) {
            Toast.makeText(ctx, "访问网络错误", Toast.LENGTH_LONG).show();
            e1.printStackTrace();
        }

    }


    public static String getRid() {
        return rid;
    }

    public static void setRid(String rid) {
        ApiOkHttpClient.rid = rid;
    }

    public Location getmLocation() {
        return mLocation;
    }

    public static void setmLocation(Location location) {
        mLocation = location;
    }

    public static void cancelAllReq() {
        client.dispatcher().cancelAll();
    }


    private static void printRequestLogForPost(String method, String url,
                                               JSONObject jsonObject) {
        StringBuilder strbuilder = new StringBuilder(method);
        strbuilder.append("|");
        strbuilder.append(url);
        strbuilder.append("=======");

        if (jsonObject != null) {
            strbuilder.append(jsonObject.toString());
        }
        XLog.e("OKHTTP==========" + strbuilder.toString());
    }

    /**
     * 格式化时间
     *
     * @param milliseconds
     * @param format       yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getFormatTime(long milliseconds, String format) {
        String dateString = "";
        Date date = new Date(milliseconds);
        //date.setTime(milliseconds);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            dateString = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }


    /**
     * 登录接口
     */
    public static void userlogin(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.USER_LOGIN, callback);
    }

    /**
     * 第三方登录接口
     */
    public static void userloginInThird(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.USER_LOGIN_THIRD, callback);
    }


    /**
     * 启动接口
     */
    public static void testJiekou2(Context context, Callback callback) {
        postData(context, null, "http://r209.chaoshenwan.com/lottery/launching", callback);
    }


    /**
     * 获取胜负彩投注接口
     */
    public static void getWinLose(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.GET_WINLOSE, callback);
    }


    /**
     * 胜负彩投注
     */
    public static void winlose_bet(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.WINLOSE_BET, callback);
    }

    /**
     * 胜负彩投注
     */
    public static void pay_order(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.PAY_ORDER, callback);
    }

    /**
     * 获取用户信息
     */
    public static void getUserInfo(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_USERINFO, callback);
    }


    /**
     * 注册接口
     */
    public static void registUser(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.REGIST_USER, callback);
    }


    /**
     * 获取验证码
     */
    public static void get_verifycode(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.REGIST_GETVERIFYCODE, callback);
    }

    /**
     * 忘记密码
     */
    public static void forget_password(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.FORGET_PASSWORD, callback);
    }


    /**
     * 编辑用户
     */
    public static void edit_UserInfo(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.EDIT_USERINFO, callback);
    }

    /**
     * 获取用户已绑定的银行卡列表
     */
    public static void get_bind_card(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_BIND_CARD, callback);
    }


    /**
     * 绑定银行卡
     */
    public static void get_addbank(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_ADDBANK, callback);
    }

    /**
     * 绑定银行卡
     */
    public static void get_BankName(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_BANKNAMEBYNO, callback);
    }


    /**
     * 微信登录
     */
    public static void wechat_login(Context context, LoginThirdNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_THIRDLOGIN, callback);
    }

    /**
     * 用户交易记录
     */
    public static void get_bets(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_BET, callback);
    }

    /**
     * 申请提现
     */
    public static void apply_cash(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.APPLY_CASH, callback);
    }

    /**
     * 查询银行卡渠道
     */
    public static void queryPayChannels(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.QUERY_PAY_CHANNELS, callback);
    }

    /**
     * 查询银行卡渠道
     */
    public static void rechargeMoney(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.RECHARGE_MONEY, callback);
    }

    /**
     * 查询银行卡渠道
     */
    public static void test_info(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.UPLOADFILES, callback);
    }


    /**
     * 查询是否完成充值
     */
    public static void checkRecharge(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_RECHARGESTATUS, callback);
    }

    /**
     * 修改密码
     */
    public static void editPassword(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.EDIT_PASSWORD, callback);
    }


    /**
     * 获取头像列表
     */
    public static void getUserHeadIcos(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_USER_HEADICOS, callback);
    }

    /**
     * 获取交易记录
     */
    public static void get_transactions(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_TRANSACTIONS, callback);
    }

    /**
     * 获取投注详情
     */
    public static void betDetail(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.BET_DETAIL, callback);
    }

    /**
     * 出票明细
     */
    public static void get_tickets(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_TICKETS, callback);
    }

    /**
     * 红包明细
     */
    public static void get_luckymoney(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_REDPACKET_LIST, callback);
    }

    /**
     * 轮播图
     */
    public static void get_AdvertList(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_ADVERTLIST, callback);
    }

    /**
     * 购物车可用红包明细
     */
    public static void get_ordertRedPackets(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_ORDERTREDPACKETS, callback);
    }

    /**
     * 我的消息
     */
    public static void getMessages(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_MESSAGES, callback);
    }

    /**
     * 我的消息数量
     */
    public static void getUnreadMsgCount(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_UNREAD_MSGCOUNT, callback);
    }


    /**
     * 取消订单
     */
    public static void delOrder(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.DEL_ORDER, callback);
    }


    /**
     * 彩种投注倍数和投注金额限制
     */
    public static void lotteryLimit(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.LOTTERY_LIMIT, callback);
    }


    /**
     * 资讯分类接口
     */
    public static void getArticleClassify(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_ARTICLE_CLASSIFY, callback);
    }

    /**
     * 资讯列表接口
     */
    public static void getArticleList(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_ARTICLELIST, callback);
    }


    /**
     * 检查红包
     */
    public static void checkRedPacket(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.CHECK_REDPACKET, callback);
    }


    /**
     * 退出
     */
    public static void userLogout(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.USER_LOGOUT, callback);
    }

    /**
     * 开奖列表
     */
    public static void openPrice(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.OPEN_PRIZES, callback);
    }


    /**
     * 竞彩足球即时比分
     */
    public static void getJinCaiMatchesScore(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.JING_CAI_PRIZES, callback);
    }

    /**
     * 查询提现去到
     */
    public static void getCashModeSettingList(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_CASHMODE_SETTINGLIST, callback);
    }

    /**
     * 获取彩种列表
     */
    public static void getlotterys(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_LOTTERYS, callback);
    }


    /**
     * 信息播报
     */
    public static void getMessageAv(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_INFORMATION_BROADCASTLIST, callback);
    }

    /**
     * 公告
     */
    public static void getNoticeList(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_NOTICELIST, callback);
    }

    /**
     * 活动
     */
    public static void getActivityList(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_ACTIVITYLIST, callback);
    }


    /**
     * 更新
     */
    public static void checkUpdate(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.GET_LASTCLIENT, callback);
    }


    /**
     * 活动弹窗
     */
    public static void popupActivity(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.POPUP_ACTIVITY, callback);
    }

    /***
     *
     * 充值提现设定接口
     *
     */

    public static void checkRechageMoney(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.RECHARGE_CASHSETTING, callback);
    }


    /**
     * 高频彩倒计时
     */
    public static void countDown(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.LOTTERY_COUNTDOWN, callback);
    }

    /**
     * 意见反馈
     */
    public static void feedback(Context context, BaseNetBean data, Callback callback) {
        //  postInJosn(context, Urls.USER_LOGIN, data, responseHandler);
        postData(context, data, Urls.USER_FEEDBACK, callback);
    }


    /**
     * 上传通信录
     */
    public static void uploadConur(Context context, List<CourData> data, Callback callback) {
        postData(context, data, Urls.UPLOAD_PHONES, callback);
    }




    /**
     * 用户开奖推送接口
     */
    public static void getPrizePush(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.PRIZE_PUSH, callback);
    }


    /**
     * 马_甲资讯轮播图
     */
    public static void getArticleAdvertList(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.GET_ARTICLEADVERTLIST, callback);
    }


    /**
     * 查询足球赛事信息
     */
    public static void getFootBallDeatil(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.FOOTBALL, callback);
    }


    /**
     * 查询足球积分榜
     */
    public static void getFootBallScores(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.FOOTBALL_POINTS, callback);
    }


    /**
     * 查询足球射手榜
     */
    public static void getFootBallplayers(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.FOOTBALL_PLAYERS, callback);
    }


    /**
     * 查询足球赛事榜
     */
    public static void getFootBallMatches(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.FOOTBALL_MATCHES, callback);
    }


    /**
     * 获取球队投票列表
     */
    public static void getVotes(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.FOOTBALL_VOTES, callback);
    }


    /**
     * 获取包模式接口
     */
    public static void getMode(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.GET_MODEL, callback);
    }



    /**
     * 获取球队投票列表
     */
    public static void getTeams(Context context, BaseNetBean data, Callback callback) {
        postData(context, data, Urls.FOOTBALL_TEAMS, callback);
    }


}
