package com.example.administrator.chaoshen.Fragment;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BetActivity;
import com.example.administrator.chaoshen.activtiy.CashActivity;
import com.example.administrator.chaoshen.activtiy.CheckCashActivity;
import com.example.administrator.chaoshen.activtiy.DealRecordActivity;
import com.example.administrator.chaoshen.activtiy.FinishRegistActivity;
import com.example.administrator.chaoshen.activtiy.KeFuActivity;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.activtiy.LuckyMoneyActivity;
import com.example.administrator.chaoshen.activtiy.MessageCenterActivity;
import com.example.administrator.chaoshen.activtiy.RechargeActivity;
import com.example.administrator.chaoshen.activtiy.SettingActivity;
import com.example.administrator.chaoshen.activtiy.UserActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.ClearCache;
import com.example.administrator.chaoshen.bean.LoginSuccess;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.bean.WebViewRefreshBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.MinePresenter;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.util.MathUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tabs_user_roundimage)
    RoundedImageView tabsUserRoundimage;
    @Bind(R.id.bg_mine_top)
    LinearLayout bgMineTop;
    @Bind(R.id.tabs_user_loginway)
    ImageView tabsUserLoginway;
    @Bind(R.id.head_icon)
    RelativeLayout headIcon;
    @Bind(R.id.to_login)
    TextView toLogin;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_id)
    TextView userId;
    @Bind(R.id.user_money)
    TextView userMoney;
    @Bind(R.id.has_login)
    LinearLayout hasLogin;
    @Bind(R.id.setting_icon)
    ImageView settingIcon;
    @Bind(R.id.recharge_monoey)
    LinearLayout rechargeMonoey;
    @Bind(R.id.cash_monoey)
    LinearLayout cashMonoey;
    @Bind(R.id.buy_record)
    RelativeLayout buyRecord;
    @Bind(R.id.bussiness_deatil)
    RelativeLayout bussinessDeatil;
    @Bind(R.id.count_message)
    TextView countMessage;
    @Bind(R.id.message_deatil)
    RelativeLayout messageDeatil;
    @Bind(R.id.luckymongey_deatil)
    RelativeLayout luckymongeyDeatil;
    @Bind(R.id.show_has_record)
    TextView showHasRecord;
    @Bind(R.id.user_deatil)
    RelativeLayout userDeatil;
    @Bind(R.id.introduce_friends)
    RelativeLayout introduceFriends;
    @Bind(R.id.help_center)
    RelativeLayout helpCenter;
    @Bind(R.id.kefu_center)
    RelativeLayout kefu_center;
    private PullToRefreshScrollView refresh_scroll;
    private MinePresenter minePresenter;
    private boolean isFirstLoad = false;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initData() {
        /*if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);*/
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                onInit(root);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });


    }

    private void onInit(View root) {
        minePresenter = new MinePresenter(this, getContext());
        refresh_scroll = root.findViewById(R.id.refresh_scroll);
        refresh_scroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //刷新

                if (!isLogin()) {
                    XToast.normal("请先登录");
                    refresh_scroll.onRefreshComplete();
                    toActivity(LoginActivity.class,null);
                } else {
                    minePresenter.getUserInfo(new TokenNetBean(getUserCache().getToken()));
                }

            }
        });
        initHead();


        if (isLogin()) {
            setUsetData(getUserCache());
            minePresenter.getUserInfo(new TokenNetBean(getUserCache().getToken()));
        }
        tabsUserRoundimage.setOnClickListener(this);
    }


    private void initHead() {
        bgMineTop.post(new Runnable() {
            @Override
            public void run() {

                int statusHigh = XDensityUtils.getStatusBarHeight();
                try {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bgMineTop.getLayoutParams();
                    params.height = statusHigh + bgMineTop.getHeight();
                    bgMineTop.setLayoutParams(params);
                } catch (Exception e) {
                }
            }
        });
    }


    @OnClick({R.id.head_icon, R.id.setting_icon, R.id.to_login, R.id.kefu_center,
            R.id.has_login, R.id.recharge_monoey, R.id.cash_monoey, R.id.buy_record, R.id.bussiness_deatil,
            R.id.message_deatil, R.id.luckymongey_deatil, R.id.user_deatil, R.id.introduce_friends, R.id.help_center})
    public void onViewClicked(View view) {
        Bundle data = new Bundle();
        switch (view.getId()) {

            case R.id.head_icon:

                break;
            case R.id.has_login:
                break;
            case R.id.recharge_monoey:
               /* Bundle data=new Bundle();
                data.putString(IntentKey.WEB_VIEW_URL,"https://www.baidu.com/");
                data.putBoolean(IntentKey.IS_PAY_ORDER,true);
                toActivity(WebActivity.class,data);*/
                if (isLogin()) {
                    toActivity(RechargeActivity.class, null);
                } else {
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                }
                break;
            case R.id.cash_monoey:
                if (isLogin()) {
                    toActivity(CheckCashActivity.class, null);
                } else {
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                }


                //toActivity(CashActivity.class, null);
                break;
            case R.id.buy_record:
                if (isLogin()) {
                    toActivity(BetActivity.class, null);
                } else {
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                }
                break;
            case R.id.bussiness_deatil:
                if (isLogin()) {
                    toActivity(DealRecordActivity.class, null);
                } else {
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                }

                break;
            case R.id.message_deatil:
                if (isLogin()) {
                    toActivity(MessageCenterActivity.class, null);
                    setMessageCount(0);
                } else {
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                }

                break;
            case R.id.luckymongey_deatil:
                if (isLogin()) {
                    toActivity(LuckyMoneyActivity.class, null);
                } else {
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                }

                break;
            case R.id.user_deatil:
                // toActivity(RealNameActivity.class, null);
                if (isLogin()) {
                    toActivity(UserActivity.class, null);
                } else {
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                }
                break;
            case R.id.introduce_friends:
                if (!isLogin()){
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                    return;
                }
              /*  data.putString(IntentKey.WEB_VIEW_URL, mdata.getParams());
                data.putBoolean(IntentKey.IS_PAY_ORDER, true);
                toActivityForResult(WebActivity.class, data, Constants.RECHARGE_SUCCESS);*/
                // toActivity(WebView.class,data);
                BannerBean banner = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
                data.putString(IntentKey.WEB_VIEW_URL, banner.getRecommendUrl());//url
                // data.putString(IntentKey.WEB_VIEW_URL, mCache.getAsString(IntentKey.SERVICE_URL));//url
                data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data.putString(IntentKey.ACTION_BAR_TITLE, "好友推荐");
                data.putString(IntentKey.WEB_TITLE,"好友推荐");
                data.putString(IntentKey.WEB_URL,banner.getInviteUrl());
                data.putInt(IntentKey.OPEN_TYPE, 11);
                toActivity(WebActivity.class, data);



                break;
            case R.id.help_center:
                BannerBean banner1 = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
                data.putString(IntentKey.WEB_VIEW_URL, banner1.getHelpUrl());
                // data.putString(IntentKey.WEB_VIEW_URL, mCache.getAsString(IntentKey.HELP_CENTER));//url
                data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data.putString(IntentKey.ACTION_BAR_TITLE, "帮助中心");
                data.putInt(IntentKey.OPEN_TYPE, 0);
                toActivity(WebActivity.class, data);
                break;
            case R.id.to_login:
                //登录页面
                XCache mCache = XCache.get(getContext());
                // if (TextUtils.isEmpty(mCache.getAsString(IntentKey.USER))){ //用户为空
                toActivity(LoginActivity.class, null);
                //}
                break;
            case R.id.setting_icon:
                toActivity(SettingActivity.class, null);


                break;

            case R.id.kefu_center:
                if (isLogin()) {
                    toActivity(KeFuActivity.class, null);
                }else {
                    showMsg("请先登录");
                    toActivity(LoginActivity.class, null);
                }
                break;
        }
    }


   /* @Subscribe
    public void onEventMainThread(RefreshDataBean info) {
        if (isLogin()) {
            minePresenter.getUserInfo(new TokenNetBean(getUserCache().getToken()));
        } else {
            setUInoLogin();
        }


    }*/

    public void refreshUser(){
        minePresenter.getUserInfo(new TokenNetBean(getUserCache().getToken()));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            //相当于Fragment的onResume
            //网络数据刷新
            //  setNotice();
            //initHead();

        } else {
            //相当于Fragment的onPause
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad && getContext() != null) {
            //加载数据
            if (isLogin()) {
                minePresenter.getUserInfo(new TokenNetBean(getUserCache().getToken()));
            } else {
                setUInoLogin();
            }
        }
    }

    /*@Subscribe
    public void onEventMainThread(ClearCache info) {
        cleraUser();


    }*/

    public void setUInoLogin() {
        XLog.e("-----1------as----IntentKey.USER--");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toLogin.setVisibility(View.VISIBLE);
                hasLogin.setVisibility(View.GONE);
                tabsUserRoundimage.setImageResource(R.drawable.img_my_sport_profile);
                tabsUserLoginway.setVisibility(View.GONE);
            }
        });
    }

    public void stopRefreishing() {
        refresh_scroll.onRefreshComplete();
    }

    public void setUsetData(UserBean user) {
        isFirstLoad = true;
        if (user == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toLogin.setVisibility(View.INVISIBLE);
                hasLogin.setVisibility(View.VISIBLE);
                tabsUserRoundimage.post(new DownloadImageRunnable(tabsUserRoundimage, user.getHeadIco(), R.drawable.img_my_sport_profile, mImageLoader, mOpt));
                if (user.getRegType() == 0) {
                    tabsUserLoginway.setVisibility(View.INVISIBLE);
                } else if (user.getRegType() == 1) {
                    tabsUserLoginway.setVisibility(View.VISIBLE);
                    tabsUserLoginway.setImageResource(R.drawable.weixin_icon);
                } else if (user.getRegType() == 2) {
                    tabsUserLoginway.setVisibility(View.VISIBLE);
                    tabsUserLoginway.setImageResource(R.drawable.qq_icon);
                }
                userName.setText(user.getNickname());
                userId.setText("ID:" + user.getUserId());
                if (1 == user.getIsRealName()) {
                    showHasRecord.setVisibility(View.GONE);
                } else {
                    showHasRecord.setVisibility(View.VISIBLE);
                }
                userMoney.setText("余额:" + MathUtil.big2(user.getCurrency()) + "元");
            }
        });

        EventBus.getDefault().post(new WebViewRefreshBean());//webview 刷新
    }

    public void cleraUser() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toLogin.setVisibility(View.VISIBLE);
                hasLogin.setVisibility(View.INVISIBLE);
                // tabsUserRoundimage.post(new DownloadImageRunnable(tabsUserRoundimage, user.getHeadIco(), R.drawable.img_my_sport_profile, mImageLoader, mOpt));
                tabsUserRoundimage.setImageResource(R.drawable.img_my_sport_profile);
                tabsUserLoginway.setVisibility(View.GONE);

                userName.setText("");
                userId.setText("ID:");
                showHasRecord.setVisibility(View.VISIBLE);
                userMoney.setText("余额:");
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tabs_user_roundimage:
                XLog.e("----------tabs_user_roundimage-----" + isLogin());
                if (!isLogin()) {
                    //  XLog.e("----------tabs_user_roundimage---11--"+isLogin());
                    toActivity(LoginActivity.class, null);
                }
                break;
        }
    }

    public void setMessageCount(int count) {

        if (count <= 0) {
            count = 0;
            countMessage.setText(count + "");
            countMessage.setVisibility(View.INVISIBLE);
        } else {
            countMessage.setText(count + "");
            countMessage.setVisibility(View.INVISIBLE);
        }
    }
}
