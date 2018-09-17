package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.chaoshen.Fragment.InformationFragment;
import com.example.administrator.chaoshen.Fragment.KaijiangFragment;
import com.example.administrator.chaoshen.Fragment.MineFragment;
import com.example.administrator.chaoshen.Fragment.ServiceFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.ClearCache;
import com.example.administrator.chaoshen.bean.ConstsBean;
import com.example.administrator.chaoshen.bean.HomeToMineBean;
import com.example.administrator.chaoshen.bean.IconsBean;
import com.example.administrator.chaoshen.bean.LoginSuccess;
import com.example.administrator.chaoshen.bean.ReceiverBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.SelectLuckmoneyBean;
import com.example.administrator.chaoshen.bean.ToAriticle;
import com.example.administrator.chaoshen.bean.ToKaijiangFragment;
import com.example.administrator.chaoshen.bean.ToScoreFragment;
import com.example.administrator.chaoshen.bean.ToServiceBean;
import com.example.administrator.chaoshen.contans.AppStatusConstant;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.HomePresenter;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.util.NotificationsUtils;
import com.example.administrator.chaoshen.util.ScreenManager;
import com.example.administrator.chaoshen.util.ScreenReceiverUtil;
import com.example.administrator.chaoshen.widget.NoScrollViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.xframe.utils.log.XLog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class HomeActivity extends BaseActivity {
    private NoScrollViewPager mViewPager;
    private List<Fragment> fragments;
    private ImageView icon_1, icon_2, icon_3, icon_4, bottom_bar_bg;
    private LinearLayout icon_1_rl, icon_2_rl, icon_3_rl, icon_4_rl, bottom_bar;
    private int selectPostion;
    private HomePresenter homePresenter;
    private boolean isFirst=true;
    private ServiceFragment serviceFragment;
    private InformationFragment informationFragment;
    private KaijiangFragment kaijiangFragment;
    private MineFragment mineFragment;
    // 动态注册锁屏等广播
    private ScreenReceiverUtil mScreenListener;
    // 1像素Activity管理类
    private ScreenManager mScreenManager;
    private ScreenReceiverUtil.SreenStateListener mScreenListenerer=new ScreenReceiverUtil.SreenStateListener() {
        @Override
        public void onSreenOn() {
            mScreenManager.finishActivity();
        }

        @Override
        public void onSreenOff() {
            // 接到锁屏广播，将SportsActivity切换到可见模式
            // "咕咚"、"乐动力"、"悦动圈"就是这么做滴
//            Intent intent = new Intent(SportsActivity.this,SportsActivity.class);
//            startActivity(intent);
            // 如果你觉得，直接跳出SportActivity很不爽
            // 那么，我们就制造个"1像素"惨案
            mScreenManager.startActivity();

        }

        @Override
        public void onUserPresent() {

        }
    };


    @Override
    public void initView() {

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                onInit();
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });


    }

    protected void onInit() {
        setSwipeBackEnable(false);
        homePresenter = new HomePresenter(this, getContext());
        homePresenter.getAdv();
        mViewPager = (NoScrollViewPager) findViewById(R.id.activity_detail_viewpager);

        fragments = new ArrayList<>();

        serviceFragment = ServiceFragment.newInstance();
        fragments.add(serviceFragment);
        mViewPager.setOffscreenPageLimit(3);

        informationFragment = InformationFragment.newInstance();
        fragments.add(informationFragment);
        kaijiangFragment = KaijiangFragment.newInstance();
        fragments.add(kaijiangFragment);
        mineFragment = MineFragment.newInstance();
        fragments.add(mineFragment);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));

        if (getIntent().getSerializableExtra(IntentKey.EXTRA_BUNDLE)!=null){
            ReceiverBean receiverBean = (ReceiverBean) getIntent().getSerializableExtra(IntentKey.EXTRA_BUNDLE);
            toOtherActivity(receiverBean.getLink());
        }

        if (!NotificationsUtils.isNotificationEnabled(getContext())){
            NotificationsUtils.toNotifySetting(this);
        }

        // 1. 注册锁屏广播监听器
        /*mScreenListener = new ScreenReceiverUtil(this);
        mScreenManager = ScreenManager.getScreenManagerInstance(this);
        mScreenListener.setScreenReceiverListener(mScreenListenerer);*/

    }



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
     * 轮播图init
     */
    public void intiBanber(BannerBean data) {

        mCache.put(IntentKey.BANNER_BEAN, data);
        XLog.e("-------------intiBanber-----------=" + mCache.getAsObject(IntentKey.BANNER_BEAN));
        setBottomData(data.getIcons());
    }



    private void setBottomData(IconsBean constsBean) {


        icon_1 = (ImageView) findViewById(R.id.icon_1);
        icon_1_rl = (LinearLayout) findViewById(R.id.icon_1_rl);
        icon_1_rl.setOnClickListener((HomeActivity) getActivity());
        icon_2 = (ImageView) findViewById(R.id.icon_2);
        icon_2_rl = (LinearLayout) findViewById(R.id.icon_2_rl);
        icon_2_rl.setOnClickListener((HomeActivity) getActivity());
        icon_3 = (ImageView) findViewById(R.id.icon_3);
        icon_3_rl = (LinearLayout) findViewById(R.id.icon_3_rl);
        icon_3_rl.setOnClickListener((HomeActivity) getActivity());
        icon_4 = (ImageView) findViewById(R.id.icon_4);
        icon_4_rl = (LinearLayout) findViewById(R.id.icon_4_rl);
        icon_4_rl.setOnClickListener((HomeActivity) getActivity());


        if (!TextUtils.isEmpty(constsBean.getBackground())) {

            bottom_bar_bg = (ImageView) findViewById(R.id.bottom_bar_bg);

            bottom_bar_bg.post(new DownloadImageRunnable(bottom_bar_bg, constsBean.getBackground(), R.color.bg, mImageLoader, mOpt));


            Bitmap bitmap = getDiscCacheImage(constsBean.getBackground());
            if (bitmap != null) {
                Drawable drawable = new BitmapDrawable(bitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bottom_bar = (LinearLayout) findViewById(R.id.bottom_bar);
                    bottom_bar.setBackground(drawable);
                }
            }

        }





        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //  scrollTabIndicator(position, positionOffset); // positionOffset: 表示ViewPager界面滚动的百分比

            }

            @Override
            public void onPageSelected(int position) {
                selectPostion = position;
                if (fragments.get(position) instanceof InformationFragment) {
                    // showActionBarNoBack("资讯");
                    //   ((InformationFragment) fragments.get(position)).setDataShow();
                } else {
                    getAcionbarRl().setVisibility(View.GONE);
                }


                initBottomIcon(position, constsBean);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initBottomIcon(selectPostion, constsBean);

    }

    private void initBottomIcon(int position, IconsBean constsBean) {
        if ("on".equals(constsBean.getAndroid())) {
            if (position == 0) {

                icon_1.post(new DownloadImageRunnable(icon_1, constsBean.getServiceOn(), R.drawable.service_select, mImageLoader, mOpt));
                icon_2.post(new DownloadImageRunnable(icon_2, constsBean.getInfo(), R.drawable.new_unselect, mImageLoader, mOpt));
                icon_3.post(new DownloadImageRunnable(icon_3, constsBean.getPrize(), R.drawable.price_unselect, mImageLoader, mOpt));
                icon_4.post(new DownloadImageRunnable(icon_4, constsBean.getMine(), R.drawable.mine_unselect, mImageLoader, mOpt));

            } else if (position == 1) {
                icon_1.post(new DownloadImageRunnable(icon_1, constsBean.getService(), R.drawable.service_unselect, mImageLoader, mOpt));
                icon_2.post(new DownloadImageRunnable(icon_2, constsBean.getInfoOn(), R.drawable.new_select, mImageLoader, mOpt));
                icon_3.post(new DownloadImageRunnable(icon_3, constsBean.getPrize(), R.drawable.price_unselect, mImageLoader, mOpt));
                icon_4.post(new DownloadImageRunnable(icon_4, constsBean.getMine(), R.drawable.mine_unselect, mImageLoader, mOpt));


            } else if (position == 2) {
                icon_1.post(new DownloadImageRunnable(icon_1, constsBean.getService(), R.drawable.service_unselect, mImageLoader, mOpt));
                icon_2.post(new DownloadImageRunnable(icon_2, constsBean.getInfo(), R.drawable.new_unselect, mImageLoader, mOpt));
                icon_3.post(new DownloadImageRunnable(icon_3, constsBean.getPrizeOn(), R.drawable.price_select, mImageLoader, mOpt));
                icon_4.post(new DownloadImageRunnable(icon_4, constsBean.getMine(), R.drawable.mine_unselect, mImageLoader, mOpt));


            } else if (position == 3) {
                icon_1.post(new DownloadImageRunnable(icon_1, constsBean.getService(), R.drawable.service_unselect, mImageLoader, mOpt));
                icon_2.post(new DownloadImageRunnable(icon_2, constsBean.getInfo(), R.drawable.new_unselect, mImageLoader, mOpt));
                icon_3.post(new DownloadImageRunnable(icon_3, constsBean.getPrize(), R.drawable.price_unselect, mImageLoader, mOpt));
                icon_4.post(new DownloadImageRunnable(icon_4, constsBean.getMineOn(), R.drawable.mine_select, mImageLoader, mOpt));


            }
        } else {
            if (position == 0) {
                icon_1.setImageResource(R.drawable.service_select);
                icon_2.setImageResource(R.drawable.new_unselect);
                icon_3.setImageResource(R.drawable.price_unselect);
                icon_4.setImageResource(R.drawable.mine_unselect);
            } else if (position == 1) {
                icon_1.setImageResource(R.drawable.service_unselect);
                icon_2.setImageResource(R.drawable.new_select);
                icon_3.setImageResource(R.drawable.price_unselect);
                icon_4.setImageResource(R.drawable.mine_unselect);
            } else if (position == 2) {
                icon_1.setImageResource(R.drawable.service_unselect);
                icon_2.setImageResource(R.drawable.new_unselect);
                icon_3.setImageResource(R.drawable.price_select);
                icon_4.setImageResource(R.drawable.mine_unselect);
            } else if (position == 3) {
                icon_1.setImageResource(R.drawable.service_unselect);
                icon_2.setImageResource(R.drawable.new_unselect);
                icon_3.setImageResource(R.drawable.price_unselect);
                icon_4.setImageResource(R.drawable.mine_select);
            }
        }
    }


/*    public void setBottomIcon() {
        BannerBean banner = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
        banner.getIcons();

        if (selectPostion == 0) {
            icon_1.setImageResource(R.drawable.service_select);
            icon_2.setImageResource(R.drawable.new_unselect);
            icon_3.setImageResource(R.drawable.price_unselect);
            icon_4.setImageResource(R.drawable.mine_unselect);
        } else if (selectPostion == 1) {
            icon_1.setImageResource(R.drawable.service_unselect);
            icon_2.setImageResource(R.drawable.new_select);
            icon_3.setImageResource(R.drawable.price_unselect);
            icon_4.setImageResource(R.drawable.mine_unselect);
        } else if (selectPostion == 2) {
            icon_1.setImageResource(R.drawable.service_unselect);
            icon_2.setImageResource(R.drawable.new_unselect);
            icon_3.setImageResource(R.drawable.price_select);
            icon_4.setImageResource(R.drawable.mine_unselect);
        } else if (selectPostion == 3) {
            icon_1.setImageResource(R.drawable.service_unselect);
            icon_2.setImageResource(R.drawable.new_unselect);
            icon_3.setImageResource(R.drawable.price_unselect);
            icon_4.setImageResource(R.drawable.mine_select);
        }
    }*/


    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {

    }

    @Override
    public boolean showActionBar() {
        return false;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private final String[] TITLES = {"抱大腿", "当大腿"};

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }

    public LinearLayout getIcon_3_rl() {
        return icon_3_rl;
    }

    public void setIcon_3_rl(LinearLayout icon_3_rl) {
        this.icon_3_rl = icon_3_rl;
    }

    @Override
    public void onClick(View v) {
        XLog.e(v.getId() + "------v.getId()---------" + R.id.icon_2_rl);
        switch (v.getId()) {
            case R.id.icon_1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.icon_2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.icon_3:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.icon_4:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.icon_1_rl:
                mViewPager.setCurrentItem(0);
                // icon_1.performClick();
                break;
            case R.id.icon_2_rl:
                mViewPager.setCurrentItem(1);
                //icon_2.performClick();
                break;
            case R.id.icon_3_rl:
                mViewPager.setCurrentItem(2);
                //icon_3.performClick();
                break;
            case R.id.icon_4_rl:
                mViewPager.setCurrentItem(3);
                //icon_4.performClick();
                break;

        }
    }


    public void bannerNoData() {
        BannerBean data = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
        if (data != null) {//缓存不为空
            // mCache.put(IntentKey.BANNER_BEAN, data);
            setBottomData(data.getIcons());
        } else {
            showMsg("网络错误");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(HomeToMineBean info) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                toCurrentItem(3);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });

    }


    @Subscribe
    public void onEventMainThread(LoginSuccess info) {
        XLog.e("-----------LoginSuccess------");
        homePresenter.getAdv();
       /* serviceFragment.setBannerData();
        serviceFragment.getDataNet();*/

    }

    public void refreshServiceData(){
        if (isFirst){
            isFirst=false;
            return;
        }
        if (serviceFragment!=null){

                serviceFragment.setBannerData();
                serviceFragment.getDataNet();

        }
    }

    public ServiceFragment getService(){
        return serviceFragment;
    }


    /*@Subscribe
    public void onEventMainThread(SelectLuckmoneyBean info) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                toCurrentItem(0);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });

    }*/

    @Subscribe
    public void onEventMainThread(ToServiceBean info) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                toCurrentItem(0);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });

    }


    @Subscribe
    public void onEventMainThread(ToScoreFragment info) {
        if (fragments.get(2).isAdded()){
            if (fragments.get(2) instanceof  KaijiangFragment){
                ((KaijiangFragment)fragments.get(2)). changeFragment(1);
            }
        }


    }


    @Subscribe
    public void onEventMainThread(ClearCache info) {
        if (fragments.get(3).isAdded()){
            if (fragments.get(3) instanceof  MineFragment){
                ((MineFragment)fragments.get(3)).  cleraUser();
            }
        }



    }


    @Subscribe
    public void onEventMainThread(RefreshDataBean info) {
        if (fragments.get(3).isAdded()){
            if (fragments.get(3) instanceof  MineFragment){
                ((MineFragment)fragments.get(3)).  cleraUser();

                if (isLogin()) {
                    //minePresenter.getUserInfo(new TokenNetBean(getUserCache().getToken()));
                    ((MineFragment)fragments.get(3)).refreshUser();
                } else {
                    ((MineFragment)fragments.get(3)). setUInoLogin();
                }
            }
        }




    }



    @Subscribe
    public void onEventMainThread(ToAriticle info) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                toCurrentItem(1);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });

    }

    @Subscribe
    public void onEventMainThread(ToKaijiangFragment info) {
        XLog.e("---------------");
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                toCurrentItem(2);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });
        //  mViewPager.setCurrentItem(0);


    }

    public void toCurrentItem(int index) {
        mViewPager.setCurrentItem(index);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public Bitmap getDiscCacheImage(String uri) {//这里的uri一般就是图片网址
        Bitmap bitmap = ImageLoader.getInstance().getMemoryCache().get(uri);
        if (bitmap == null) {
            String path = ImageLoader.getInstance().getDiskCache().get(uri).getPath();
            if (!TextUtils.isEmpty(path)) {
                bitmap = BitmapFactory.decodeFile(path);

            }
        }
        return bitmap;
    }


   /* @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(AppStatusConstant.KEY_HOME_ACTION, AppStatusConstant.ACTION_BACK_TO_HOME);
        switch (action) {
            case AppStatusConstant.ACTION_RESTART_APP:
                protectApp();
                break;
        }
    }

    @Override
    protected void protectApp() {
        startActivity(new Intent(this, AdvertisementActivity.class));
        finish();
    }*/

}
