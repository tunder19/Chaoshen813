package com.example.administrator.chaoshen.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.HomeActivity;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.activtiy.WinLoseActivity;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.app.ActivityManager;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.HomeToMineBean;
import com.example.administrator.chaoshen.bean.ReceiverBean;
import com.example.administrator.chaoshen.bean.ToAriticle;
import com.example.administrator.chaoshen.bean.ToKaijiangFragment;
import com.example.administrator.chaoshen.bean.ToServiceBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.util.SystemUtil;
import com.example.administrator.chaoshen.widget.ActivityDialog;
import com.example.administrator.chaoshen.widget.LuckyMoneyGetDialog;
import com.example.administrator.chaoshen.widget.PrizeDialog;
import com.youth.xframe.utils.log.XLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PushService;
import de.greenrobot.event.EventBus;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private Context mContext;
    private static List<String> dialogList = new ArrayList();
    private ReceiverBean prize, activity, hongbao;
    private static ArrayList<ReceiverBean> dataList = new ArrayList<>();
    ActivityManager appmanager = ActivityManager.getInstance();
    private static boolean isShowing = false;


    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.e(dialogList.size() + "----111--------isShowing----=" + isShowing);
        Intent pushintent=new Intent(context,PushService.class);//启动极光推送的服务
        context.startService(pushintent);
        mContext = context;
        String id = JPushInterface.getRegistrationID(context);
        ApiOkHttpClient.setRid(id);
        try {
            Bundle bundle = intent.getExtras();
            XLog.e(id + "=id--------" + "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                XLog.d("[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) { //自定义全体推送 tag rid
                XLog.d("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                XLog.d("[MyReceiver] 接收到推送下来的自定义消息 JSON: " + bundle.getString(JPushInterface.EXTRA_EXTRA));

                String res = "";
                if (!TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    res = bundle.getString(JPushInterface.EXTRA_EXTRA);
                } else if (!TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_MESSAGE))) {
                    res = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                }

                ReceiverBean info = new ResponseAnalyze<ReceiverBean>().analyze(res, ReceiverBean.class);

                if (info != null) {
                    if ("0".equals(info.getPage() + "")) {//中奖弹窗
                        dialogList.add(info.getPage());

                        //prize = info;
                        dataList.add(info);
                        XLog.e("-----推送测试__集合数量=" + dataList.size());

                    } else if ("1".equals(info.getPage() + "")) { //红包弹窗
                        dialogList.add(info.getPage());
                        // hongbao = info;
                        dataList.add(info);

                    } else if ("2".equals(info.getPage() + "")) { //活动弹窗
                        dialogList.add(info.getPage());
                        // activity = info;
                        dataList.add(info);

                    }

                    if (isShowing == false) {
                        handler.sendEmptyMessage(1);
                    }
                   /* if (dialogList.size() == 1) {

                    }else {

                    }*/


                } else {
                    //数据为空
                }


                //processCustomMessage(context, bundle);
                //  Toast.makeText(context,"接收到推送下来的自定义消息",Toast.LENGTH_LONG).show();


            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) { //站外推送
                XLog.d("[MyReceiver] 接收到推送下来的通知" + bundle.getString(JPushInterface.EXTRA_EXTRA));
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                XLog.d("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {  //用户点击通知
                //bundle.getString(JPushInterface.EXTRA_EXTRA);
                XLog.d("[MyReceiver] 用户点击打开了通知");
                String res = bundle.getString(JPushInterface.EXTRA_EXTRA);
                XLog.e("-----[MyReceiver] 用户点击打开了通知-------res="+res);
                ReceiverBean info = new ResponseAnalyze<ReceiverBean>().analyze(res, ReceiverBean.class);

                if (SystemUtil.isProessRunning(mContext)){//程序在运行的情况下
                    XLog.e("---------------isProessRunning----");
                    Intent intent1 = new Intent(mContext, HomeActivity.class);
                    intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent1.setAction(Intent.ACTION_MAIN);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    mContext.getApplicationContext().startActivity(intent1);

                    toOtherActivity(info.getLink());
                   /* Intent intent1 = new Intent(mContext, HomeActivity.class);
                    intent1.putExtra(IntentKey.EXTRA_BUNDLE, info);
                    intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent1.setAction(Intent.ACTION_MAIN);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);*/


                }else {//程序没有运行的情况下
                    XLog.e("--------------isProessRunning  false-----");
                    Intent launchIntent = context.getPackageManager().
                            getLaunchIntentForPackage(APP_Contants.getPackgeName());
                    launchIntent.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    launchIntent.putExtra(IntentKey.EXTRA_BUNDLE, info);
                    context.startActivity(launchIntent);
                }





                /*Bundle help = new Bundle();
                help.putString(IntentKey.WEB_VIEW_URL, "http://www.baidu.com");//url
                help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                help.putInt(IntentKey.OPEN_TYPE, 6); //活动
                ((BaseActivity) appmanager.currentActivity()).toActivity(WebActivity.class, help);*/



            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                XLog.d("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                XLog.w("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                XLog.d("[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    XLog.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    XLog.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
   /* private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }*/







    public void toOtherActivity(String url) {

        String service = "service"; //服务大厅
        String article = "article"; //资讯
        String prize = "prize"; //开奖
        String personal = "personal"; //我的
        String home = "index"; //我的

        Bundle data = new Bundle();

        XLog.e("--refresh-----url---------" + url);
        if (url.indexOf(addString(service)) != -1 || url.indexOf(addString(home)) != -1) {//
            EventBus.getDefault().post(new ToServiceBean());
            appmanager.popAllActivityUntillOne(HomeActivity.class);
        } else if (url.indexOf(addString(article)) != -1) {
            EventBus.getDefault().post(new ToAriticle());
            appmanager.popAllActivityUntillOne(HomeActivity.class);
        } else if (url.indexOf(addString(prize)) != -1) {
            EventBus.getDefault().post(new ToKaijiangFragment());
            appmanager.popAllActivityUntillOne(HomeActivity.class);
        } else if (url.indexOf(addString(personal)) != -1) {
            EventBus.getDefault().post(new HomeToMineBean());
            appmanager.popAllActivityUntillOne(HomeActivity.class);
        } /*else if (url.indexOf(addString(IntentKey.DALETOU)) != -1) {

            return continueBuyLottery(IntentKey.DALETOU);

        } else if (url.indexOf(addString(IntentKey.HUBEI11C5)) != -1) {
            return continueBuyLottery(IntentKey.HUBEI11C5);

        } else if (url.indexOf(addString(IntentKey.SSQ)) != -1) {
            return continueBuyLottery(IntentKey.SSQ);

        }*/ else if (url.indexOf(addString(IntentKey.WIN_LOSE) + "?rule=" + IntentKey.SFC) != -1) {

            data.putInt("lotter_type", 0); //0是胜负彩  1是任9
            ((BaseActivity) appmanager.currentActivity()).toActivity(WinLoseActivity.class, data);
        } else if (url.indexOf(addString(IntentKey.WIN_LOSE) + "?rule=" + IntentKey.R9) != -1) {
            data.putInt("lotter_type", 1); //0是胜负彩  1是任9
            ((BaseActivity) appmanager.currentActivity()).toActivity(WinLoseActivity.class, data);

        } else if (url.indexOf(addString(IntentKey.JINGCAI)) != -1) {
           /// showMsg("竞彩足球");

        }else {
            data.putString(IntentKey.WEB_VIEW_URL, url);
            // data.putString(IntentKey.WEB_VIEW_URL, mCache.getAsString(IntentKey.HELP_CENTER));//url
            data.putBoolean(IntentKey.IS_PAY_ORDER, false);
            data.putInt(IntentKey.OPEN_TYPE, 0);
            ((BaseActivity)appmanager.currentActivity()).toActivity(WebActivity.class, data);
        }


    }


    /**
     * 拼接
     */
    public String addString(String content) {
        String redirect = "redirect://";
        return redirect + content;
    }


    /**
     * 拼接
     */
    public String addInvokeString(String content) {
        String redirect = "invoke://";
        return redirect + content;
    }





















    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                isShowing = true;
                XLog.e("------handleMessage-------info-----isShowing=" + isShowing);
                showPrizeDialog();
            }

            super.handleMessage(msg);
        }
    };


    public void showPrizeDialog() {
        if (dialogList.size() > 0) {
            for (int i = 0; i < dialogList.size(); i++) {
                if (dialogList.get(i).equals("0")) {
                    // XLog.e("-----dataList.get(i)=" + dataList.get(i).getBonus());
                    XLog.e(dataList.size() + "-----推送测试__集合索引=" + i);
                    new PrizeDialog(appmanager.currentActivity(), dataList.get(i), new PrizeDialog.DismissListener() {
                        @Override
                        public void finishCall() {

                            handler.sendEmptyMessageDelayed(1, 10 * 1000);
                            isShowing = false;

                        }
                    });

                    dialogList.remove(i);
                    dataList.remove(i);
                    return;

                } else if (dialogList.get(i).equals("1")) {


                    LuckyMoneyGetDialog prizeDialog = new LuckyMoneyGetDialog(appmanager.currentActivity(), dataList.get(i), new LuckyMoneyGetDialog.DismissListener() {
                        @Override
                        public void finishCall() {
                            handler.sendEmptyMessageDelayed(1, 10 * 1000);
                            isShowing = false;
                            XLog.e("------sss---isShowing=" + isShowing);
                        }
                    });


                    dialogList.remove(i);
                    dataList.remove(i);
                    return;
                } else if (dialogList.get(i).equals("2")) {
                    ActivityDialog prizeDialog = new ActivityDialog(appmanager.currentActivity(), dataList.get(i), new ActivityDialog.DismissListener() {
                        @Override
                        public void finishCall() {
                            handler.sendEmptyMessageDelayed(1, 10 * 1000);
                            isShowing = false;
                        }
                    });
                    dialogList.remove(i);
                    dataList.remove(i);
                    return;

                }

            }

        } else {
            handler.removeMessages(1);
            isShowing = false;
        }

    }


}