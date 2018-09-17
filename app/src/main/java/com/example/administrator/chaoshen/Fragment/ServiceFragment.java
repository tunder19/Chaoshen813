package com.example.administrator.chaoshen.Fragment;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.provider.ContactsContract;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.HomeActivity;
import com.example.administrator.chaoshen.activtiy.NoticeActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.activtiy.WinLoseActivity;
import com.example.administrator.chaoshen.adapter.LotteryRecycleAdapter;
import com.example.administrator.chaoshen.bean.ActivityHomeBean;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.CheckRedBean;
import com.example.administrator.chaoshen.bean.CheckVersionBean;
import com.example.administrator.chaoshen.bean.LoginSuccess;
import com.example.administrator.chaoshen.bean.Lottery;
import com.example.administrator.chaoshen.bean.LotteryBean;
import com.example.administrator.chaoshen.bean.MessageDataBean;
import com.example.administrator.chaoshen.bean.ToScoreFragment;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.LotteryCountNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.ServicePresenter;
import com.example.administrator.chaoshen.util.ImageLoaderShowUtil;
import com.example.administrator.chaoshen.util.MarqueeTextView;
import com.example.administrator.chaoshen.util.MathUtil;
import com.example.administrator.chaoshen.util.ObservableScrollView;
import com.example.administrator.chaoshen.widget.ActivityHomeDialog;
import com.example.administrator.chaoshen.widget.LuckyMoneyTodDialog;
import com.example.administrator.chaoshen.widget.LuckyMoneyTomorrowDialog;
import com.example.administrator.chaoshen.widget.GridDivider;
import com.example.administrator.chaoshen.widget.VersionUpdateDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XAppUtils;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.utils.permission.XPermission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class ServiceFragment extends BaseFragment implements OnBannerListener, ObservableScrollView.OnObservableScrollViewListener, View.OnClickListener {

    private RelativeLayout mRefreshLayout,adv_bg;
    private Banner banner;
    private DisplayImageOptions opt;
    private LinearLayout top_ingnore, top_can_ingore;
    private ObservableScrollView my_scrollView;
    private RelativeLayout ingnore_high;
    private TextView top_bar_name;
    private Button top_bar_button;
    private double mHeight;
    private MarqueeTextView marqueeTv;
    private ArrayList<Lottery> lotteries;
    private Spinner spinner;
    private ServicePresenter mPresenter;
    private BannerBean banner_data;
    private RecyclerView popup_re;
    private LinearLayout gonggao_icon, score_icon, activity_icon, help_icon;
    public static List<LotteryBean> lotteryData;
    private LotteryRecycleAdapter popAdapter;
    private List<ActivityHomeBean> popData;
    private int isInit = 0;
    private int First = 0x001;
    private int Third = 0x003;
    private Map<String, Double> timeMap;
    private LinearLayout lottery_bg;


    public static ServiceFragment newInstance() {

        Bundle args = new Bundle();

        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == First) {
            mPresenter.checkRechageMoney();
        }
        if (msg.what == 2) {

           /* int seconds = msg.arg2;
            XLog.e(seconds + "--------------msg.what" + msg.what);
            if (seconds < 0) {
                mPresenter.getLotteryCount(new LotteryCountNetBean(lotteryData.get(msg.arg1).getLotteryType()));
            } else {
                seconds--;
                notifyPosition(msg.arg1, seconds);

                setDataForTime(msg.arg1, seconds);
            }*/

            for (Map.Entry<String, Double> vo : timeMap.entrySet()) {

                if (vo.getValue() < 0) {
                    vo.setValue(0d);
                    mUiHandler.removeMessages(2);
                    mPresenter.getLotteryCount(new LotteryCountNetBean(""));
                } else {
                    double time = vo.getValue();
                    //XLog.e("---------------time=" + time);
                    time=time-1;
                    vo.setValue(time);


                }
            }

            notifyPosition();
            setDataForTime(timeMap);

        } else if (msg.what == Third) {
            showPopActivity();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    public void initData() {
        mPresenter = new ServicePresenter(this, getContext());
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // processLogic(savedInstanceState);
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


    protected void onInit(View root) {

        mRefreshLayout = root.findViewById(R.id.refreshLayout);
        popup_re = root.findViewById(R.id.popup_re);
        lottery_bg=root.findViewById(R.id.lottery_bg);
        adv_bg=root.findViewById(R.id.adv_bg);
        // mRefreshLayout.setDelegate(this);
        my_scrollView = root.findViewById(R.id.my_scrollView);
        my_scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
               /* XToast.success("加载更多数据中");
                my_scrollView.onRefreshComplete();*/
                getDataNet();
            }
        });
        marqueeTv = root.findViewById(R.id.marqueeTv);

        gonggao_icon = root.findViewById(R.id.gonggao_icon);
        score_icon = root.findViewById(R.id.score_icon);
        activity_icon = root.findViewById(R.id.activity_icon);
        help_icon = root.findViewById(R.id.help_icon);
        gonggao_icon.setOnClickListener(this);
        score_icon.setOnClickListener(this);
        activity_icon.setOnClickListener(this);
        help_icon.setOnClickListener(this);

        banner = (Banner) root.findViewById(R.id.banner);
        getDataNet();
        setCanIngnoreBar(root);
        handelDispatch();
        // initAllLotterys();
        initSpinner(root);

        setBannerData();
        isInit = 1;
    }

    public int getIsInit() {
        return isInit;
    }

    public void setIsInit(int isInit) {
        this.isInit = isInit;
    }

    public void setBannerData() {
        XLog.e(popup_re + "-----------------mApp=" + mApp);
        BannerBean data = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
        setBanner(data);

    }


    //统一获取网络信息控制刷新

    public void getDataNet() {

        // mPresenter.getAdv();

        //intiBanber((BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN));
        if (isLogin()) {
            mPresenter.checkRedPacket(new TokenNetBean(getUserCache().getToken()));
        }

        mPresenter.get_lotterys();
        mPresenter.getMessageAv();
        mPresenter.checkUpdate();
        mPresenter.popupActivity();
        sendEmptyUiMessageDelayed(First, 5000); //5秒请求到充_值金额查询
        //getContacts();
    }


    public void popActivity() {
        mPresenter.popupActivity();
    }


    public void updateApp(CheckVersionBean.ClientInfoBean data) {
        if (data.getVersionCode() <= MathUtil.StringtoLong(XAppUtils.getVersionName(getContext()))) {
            //  Toast.makeText(getContext(),"当前是最新版本",Toast.LENGTH_LONG).show();
            return;
        }
        XLog.e("----data.getNeedUpdate()-=" + data.getNeedUpdate());
        if (0 == (data.getNeedUpdate())) {//普通更新
            if ("1".equals(mCache.getAsString(IntentKey.NO_SHOW_UPDATE))) {
                //不再显示
                return;
            } else {
                // 显示
                VersionUpdateDialog dialog = new VersionUpdateDialog(getContext(), data);
            }
        } else {
            VersionUpdateDialog dialog = new VersionUpdateDialog(getContext(), data);
        }
    }


    public void setLotterys(List<LotteryBean> data) {
        lotteryData = data;
        //initAllLotterys(data);
        setRecycle(data);
        mCache.put(IntentKey.LOTTERY_LIST, (Serializable) data);
        my_scrollView.onRefreshComplete();
    }

    public void setNoNetForLottery() {
        List<LotteryBean> data = (List<LotteryBean>) mCache.getAsObject(IntentKey.LOTTERY_LIST);
        lotteryData = data;
        if (data != null) {
            setRecycle(data);
        }
        my_scrollView.onRefreshComplete();
    }


    public void setRecycle(List<LotteryBean> lotterybean) {
        ((SimpleItemAnimator) popup_re.getItemAnimator()).setSupportsChangeAnimations(false);
        //    popup_re.getItemAnimator().setChangeDuration(0);
        popup_re.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //  popup_re.addItemDecoration(new DividerGridItemDecoration(getContext()));
        popup_re.addItemDecoration(new GridDivider(getContext(), 1, Color.parseColor("#db6375")));

        //url
//  help.putString(IntentKey.WEB_VIEW_URL, "http://192.168.1.80:3333/"+IntentKey.HUBEI11C5);
// help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
//大乐透
//0是胜负彩  1是任9
//0是胜负彩  1是任9
        popAdapter = new LotteryRecycleAdapter(getContext(), lotterybean, new LotteryRecycleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {

                if (lotterybean.get(i).getIsEnter() == 0) {
                    showMsg(lotterybean.get(i).getOutText());
                    return;
                }

                if (!TextUtils.isEmpty(lotterybean.get(i).getUrl())) {
                    String name = lotterybean.get(i).getLotteryName();
                    int openType = 0;
                    switch (lotterybean.get(i).getLotteryType()) {
                        case IntentKey.DALETOU:
                            openType = 3;
                            break;
                        case IntentKey.HUBEI11C5:
                            openType = 2;
                            break;
                        case IntentKey.SSQ:
                            openType = 8;
                            break;

                    }
                    if (openType == 0) {
                        openType = 9;
                    }
                    Bundle help = new Bundle();

                    help.putString(IntentKey.WEB_VIEW_URL, lotterybean.get(i).getUrl());//url
                    //  help.putString(IntentKey.WEB_VIEW_URL, "http://192.168.1.80:3333/"+IntentKey.HUBEI11C5);
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    help.putString(IntentKey.ACTION_BAR_TITLE, name);
                    help.putBoolean(IntentKey.CLOSE_ACTION_BAR,true);
                    // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
                    help.putInt(IntentKey.OPEN_TYPE, openType); //大乐透
                    toActivity(WebActivity.class, help);
                } else {
                    Bundle data = new Bundle();
                    switch (lotterybean.get(i).getLotteryType()) {
                        case IntentKey.SFC:

                            data.putInt("lotter_type", 0); //0是胜负彩  1是任9
                            toActivity(WinLoseActivity.class, data);
                            break;

                        case IntentKey.R9:
                            data.putInt("lotter_type", 1); //0是胜负彩  1是任9
                            toActivity(WinLoseActivity.class, data);
                            break;
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int section, int position) {

            }
        }, mImageLoader, mOpt);
        lottery_bg.setVisibility(View.GONE);
        popup_re.setAdapter(popAdapter);


        int count = 0;
        for (int i = 0; i < lotterybean.size(); i++) {
            if (lotterybean.get(i).getCountDown() == 1) {//开启倒计时
                // myViewHolder.lottery_notice1.setVisibility(View.VISIBLE);
                // myViewHolder.lottery_notice1.setText("距截止"+);
                count++;


            }
        }
        if (count > 0) {
            mPresenter.getLotteryCount(new LotteryCountNetBean(""));
        }

    }

    /**
     * 设置倒计时
     */
    public void setDataForTime(Map<String, Double> map) {
        timeMap = map;
        mUiHandler.removeMessages(2);
        sendEmptyUiMessageDelayed(2, 1000);
       /* for (Map.Entry<String, Double> vo : map.entrySet()) {
            vo.getKey();
            vo.getValue();
            for (int i = 0; i < lotteryData.size(); i++) {
                if (vo.getKey().equals(lotteryData.get(i).getLotteryType())) { //发送倒计时
                    mUiHandler.removeMessages(i);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.arg1 = i;
                    msg.arg2 = (int) ((vo.getValue()) / 1000);
                    sendUiMessageDelayed(msg, 1000);
                }
            }
        }*/


    }

    private void notifyPosition() {
        for (Map.Entry<String, Double> vo : timeMap.entrySet()) {
            vo.getKey();
            vo.getValue();
            for (int i = 0; i < lotteryData.size(); i++) {
                if (vo.getKey().equals(lotteryData.get(i).getLotteryType())) { //发送倒计时
                    popAdapter.getmLottery().get(i).setSeconds((int) (vo.getValue() / 1));
                    popAdapter.notifyItemChanged(i);

                }
            }
        }


    }

    /* @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).addOtherFragment();
    }*/

    /* public static int bets(String[] bets, boolean combination) {
        return combination ?
                bets(combinations(bets)) :
                bets(bets);
    }*/


    /**
     * 设置投注倍数
     */
    private void initSpinner(View root) {
        spinner = root.findViewById(R.id.spinner);

        String[] arr = {"100倍", "50倍", "10倍"};
        //创建ArrayAdapter对象
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, arr);
        spinner.setAdapter(adapter);

        /*//这里设置的是Spinner的样式 ， 输入 simple_之后会提示有4人，如果专属spinner的话应该是俩种，在特殊情况可自己定义样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //设置Adapter了
        mSpinner.setAdapter(adapter);
        //监听Spinner的操作
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               //选取时候的操作
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   mTv.setText(adapter.getItem(position));
                                               }
                                               //没被选取时的操作
                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {
                                                   mTv.setText("No anything");
                                               }*/

    }


    /**
     * 设置滚动公告
     */
    public void initNotice(List<MessageDataBean.InformationBroadcastListBean> data) {
        mCache.put(IntentKey.MESSAGE_BO, (Serializable) data);
        marqueeTv.setTextArraysAndClickListener(data, mImageLoader, mOpt, null);
    }

    /**
     * 设置标题的透明度
     */
    private void initTopBar(View root) {
        ingnore_high = root.findViewById(R.id.ingnore_high);


        top_can_ingore.getBackground().setAlpha(0);
        //top_bar_name.getBackground().setAlpha(0);
        top_bar_name.setTextColor(Color.argb(0, 255, 255, 255));
        top_bar_button.getBackground().setAlpha(0);
        banner.post(new Runnable() {
            @Override
            public void run() {
                int statusHigh = XDensityUtils.getStatusBarHeight();
                mHeight = banner.getHeight() - 2 * statusHigh; //height is ready
                startListener();
            }
        });

    }

    /**
     * 活动弹窗
     */

    public void showActivtiyDialog(List<ActivityHomeBean> data) {
        popData = data;
        showPopActivity();
    }

    private void showPopActivity() {

        if (popData.size() > 0) {
            ActivityHomeDialog dialog = new ActivityHomeDialog(getContext(), popData.get(0), new ActivityHomeDialog.DismissListener() {
                @Override
                public void finishCall() {
                    sendEmptyUiMessageDelayed(Third, 1000 * 10);
                    //showPopActivity();
                }
            });
            popData.remove(0);
        } else {
            return;
        }
    }


    /**
     * 禁用顶部透明度改变
     */
    public void startListener() {
        //   my_scrollView.setOnObservableScrollViewListener(this);
    }

    public void stopListener() {
        //   my_scrollView.setOnObservableScrollViewListener(null);
    }


    /**
     * scrollView和下拉刷新冲突的处理
     */
    private void handelDispatch() {
        my_scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                //    mRefreshLayout.setPullDownRefreshEnable(my_scrollView.getScrollY() == 0);

            }
        });
    }

    /**
     * 轮播图init
     */
    public void intiBanber(BannerBean data) {
        if (setBanner(data)) return;
        // mCache.put(IntentKey.BANNER_BEAN, data);
    }

    private boolean setBanner(BannerBean data) {
        banner_data = data;
        if (data == null || data.getAdvertList().size() == 0 || data.getAdvertList() == null) {
            return true;
        }
        ArrayList urls = new ArrayList();
        for (int i = 0; i < data.getAdvertList().size(); i++) {
            urls.add(data.getAdvertList().get(i).getPicUrl());
        }

        initImageLoaderF();

        banner.setImages(urls)
                .setImageLoader(new ImageLoaderShowUtil(mImageLoader, opt))
                .start();

        banner.setOnBannerListener(this);
        adv_bg.setVisibility(View.GONE);
        banner.setVisibility(View.VISIBLE);
        return false;
    }

    public void bannerNoData() {
        BannerBean data = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
        if (data != null) {//缓存不为空
            if (setBanner(data)) return;
        }
    }

    protected void initImageLoaderF() {
        //                .showImageOnLoading(R.drawable.default_icon)
//                .displayer(new FadeInBitmapDisplayer(300))
        opt = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.lunbo_nodata)
                .showImageOnFail(R.drawable.lunbo_nodata)
//                .showImageOnLoading(R.drawable.default_icon)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    private void setCanIngnoreBar(View root) {
        int statusHigh = XDensityUtils.getStatusBarHeight();
        top_ingnore = root.findViewById(R.id.top_ingnore);
        top_can_ingore = root.findViewById(R.id.top_can_ingore);
        top_bar_name = root.findViewById(R.id.top_bar_name);
        top_bar_button = root.findViewById(R.id.top_bar_button);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) top_ingnore.getLayoutParams();
        params.height = statusHigh;
        top_ingnore.setLayoutParams(params);
        initTopBar(root);
    }


    @Override
    public void OnBannerClick(int position) {
        if (TextUtils.isEmpty(banner_data.getAdvertList().get(position).getLink())) {
            return;
        }
        //  XToast.success("点击了轮播图" + position);
        Bundle help = new Bundle();
        help.putString(IntentKey.WEB_VIEW_URL, banner_data.getAdvertList().get(position).getLink());//url
        help.putBoolean(IntentKey.IS_PAY_ORDER, false);
        // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
        help.putInt(IntentKey.OPEN_TYPE, 1); //关闭按钮显示
        toActivity(WebActivity.class, help);
    }

    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        /** 标题滚动透明度改变功能 */
        if (t <= 0) {
            top_can_ingore.getBackground().setAlpha(0);
            top_bar_name.setTextColor(Color.argb(0, 255, 255, 255));
            top_bar_button.getBackground().setAlpha(0);
            //顶部图处于最顶部，标题栏透明
        } else if (t > 0 && t < mHeight) {

            //滑动过程中，渐变
            float scale = (float) (t / mHeight);//算出滑动距离比例
            int alpha = (int) (255 * scale);//得到透明度
            XLog.e("滑动距离__mHeight=" + mHeight + "-----t=" + t + "------alpha=" + alpha + "-------scale=" + scale);
            top_can_ingore.getBackground().setAlpha(alpha);
            top_bar_name.setTextColor(Color.argb(alpha, 255, 255, 255));
            top_bar_button.getBackground().setAlpha(alpha);
        } else {

            //过顶部图区域，标题栏定色
            top_can_ingore.getBackground().setAlpha(255);
            top_bar_name.setTextColor(Color.argb(255, 255, 255, 255));
            top_bar_button.getBackground().setAlpha(255);
        }
    }


    @Override
    public void onDestroy() {
        if (marqueeTv != null) {
            marqueeTv.releaseResources();
        }
        super.onDestroy();
    }


    public void showRedDialog(List<CheckRedBean> data) {//类型，1今天红包，2明天红包
        if (data == null) return;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getNum() == 0) {
                continue;
            }
            XLog.e("-----------------getType--------"+data.get(i).getType());
            if (data.get(i).getType() == 1) {
                if (mCache.getAsString(IntentKey.TODAY) == null || !mCache.getAsString(IntentKey.TODAY).equals(data.get(i).getId() + "")) {
                    new LuckyMoneyTodDialog(getContext(), data.get(i), null);
                    mCache.put(IntentKey.TODAY, data.get(i).getId() + "");
                }

            } else if (data.get(i).getType() == 2) {
                // if (mCache.getAsString(IntentKey.TOMORROW,))

                if (mCache.getAsString(IntentKey.TOMORROW) == null || !mCache.getAsString(IntentKey.TOMORROW).equals(data.get(i).getId() + "")) {
                    new LuckyMoneyTomorrowDialog(getContext(), data.get(i), null);
                    mCache.put(IntentKey.TOMORROW, data.get(i).getId() + "");
                }
            }

        }
    }


    @Override
    public void onClick(View view) {
        Bundle data = new Bundle();
        switch (view.getId()) {

            case R.id.gonggao_icon:

                data.putInt(IntentKey.NOTCIE_ACTIVTY, 0);
                toActivity(NoticeActivity.class, data);

                break;
            case R.id.score_icon:
                ((HomeActivity) getActivity()).getIcon_3_rl().performClick();
                EventBus.getDefault().post(new ToScoreFragment());
                break;
            case R.id.activity_icon:
                data.putInt(IntentKey.NOTCIE_ACTIVTY, 1);
                toActivity(NoticeActivity.class, data);
                break;
            case R.id.help_icon:
                BannerBean banner = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
                if (banner == null || TextUtils.isEmpty(banner.getHelpUrl())) {
                    showMsg("暂无响应");
                    return;
                }

                data.putString(IntentKey.WEB_VIEW_URL, banner.getHelpUrl());//url
                data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data.putString(IntentKey.ACTION_BAR_TITLE, "帮助中心");
                data.putInt(IntentKey.OPEN_TYPE, 0);
                toActivity(WebActivity.class, data);
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        XLog.e("-----------------onDestroyView--");
    }


}
