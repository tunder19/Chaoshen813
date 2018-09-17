package com.example.administrator.chaoshen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BetDetailActivity;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.adapter.BetListAdapter;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.BetRecordBean;
import com.example.administrator.chaoshen.bean.ClearCache;
import com.example.administrator.chaoshen.bean.DealOrderSuccess;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.GetBetsNetBean;
import com.example.administrator.chaoshen.presenter.BetRecordPresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class BetFragment1 extends BaseFragment {
    @Bind(R.id.refreshLayout)
    PullToRefreshListView refreshLayout;
    @Bind(R.id.no_data)
    TextView no_data;

    private BetListAdapter adapter;
    private boolean isFirstLoad = true;
    public int type = 0;//0全部投注记录  1中奖投注 2待开奖
    private BetRecordPresenter mPresenter;
    private int page = 1;
    private List<BetRecordBean> mData;
    private TextView no_net_word;

    public static BetFragment1 newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        BetFragment1 fragment = new BetFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fagment_bet1;
    }

    @Override
    public void initData() {
        XLog.e("-----initData-------setUserVisibleHint---");
        type = getArguments().getInt("type");
        mPresenter = new BetRecordPresenter(this, getContext());
    }


    @Override
    protected void initViews(View root) {
        super.initViews(root);
        no_net_word=root.findViewById(R.id.no_net_word);

        refreshLayout.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
       /* adapter = new BetListAdapter(getContext(), new ArrayList<>(), this);
        refreshLayout.setAdapter(adapter);*/
       // refreshLayout.setMode(PullToRefreshBase.Mode.BOTH);
        refreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getData(type, 0);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(type, 1);
            }
        });
        refreshLayout.setLastItem(new PullToRefreshListView.LastItem() {
            @Override
            public void onLastItem() {
                getData(type,1);
            }
        });



        if (isFirstLoad && type == 0) {//全部页面并且第一次加载页面
            getData(type, 0);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO 自动生成的方法存根
                    refreshLayout.setRefreshing(true);
                }
            }, 200);
        }
    }


    public void stopLoading() {
        refreshLayout.onRefreshComplete();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            //相当于Fragment的onResume
            //网络数据刷新
            //  setNotice();
            if (isFirstLoad && getContext() != null) {
                //加载数据
                getData(type, 0);
                refreshLayout.setRefreshing();
            }
        } else {
            //相当于Fragment的onPause
            return;
        }
    }

    public boolean hasLoadDta() {
        if (mData != null) {
            return true;
        } else {
            return false;
        }
    }

    public void getData(int type, int formHead) {
        String gettype = "";
        if (type == 0) {
            gettype = "all";
        } else if (type == 1) {
            gettype = "winning";
        } else if (type == 2) {
            gettype = "waiting";
        }
        if (formHead==0){

        }
        hideNoData();
        mPresenter.getBets(new GetBetsNetBean(getUserCache().getToken(), gettype, page), formHead);
    }

    public void getDataSuccess(List<BetRecordBean> data, int formHead) {
        isFirstLoad = false;
        page++;
        if (formHead == 0) {
            mData = data;
        } else {
            mData.addAll(data);
        }
        if (adapter == null) {
            adapter = new BetListAdapter(getContext(), data, this);
            XLog.e("--------------------");
            refreshLayout.setAdapter(adapter);
        } else {
            if (formHead == 0) {
                adapter.clearList();
                adapter.setData(data);

            } else {//加载更多
                adapter.addData(data);
            }
            adapter.notifyDataSetChanged();
        }
        refreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int j=i-1;
                Bundle data=new Bundle();
                data.putLong(IntentKey.ORDER_ID,mData.get( j).getId());
                data.putString(IntentKey.LOTTERY_RULE,mData.get( j).getRule());
                data.putString(IntentKey.LOTTERY_TYPE,mData.get( j).getLotteryType());
                if ("sfc".equals(mData.get( j).getRule())) {//胜负彩
                    toActivity(BetDetailActivity.class,data);
                } else if ("r9".equals(mData.get( j).getRule())) {//任九
                    toActivity(BetDetailActivity.class,data);
                }else if ("jingcai".equals(mData.get( j).getLotteryType())) {//竞猜足球
                    toActivity(BetDetailActivity.class,data);
                }else {
                    Bundle help = new Bundle();
                    //String orderUrl = banner.getH5Url() + "/" + mData.get( j).getLotteryType() + "/order?id=" + mData.get( j).getId();
                    String orderUrl=mData.get(j).getLink();
                    help.putString(IntentKey.WEB_VIEW_URL, orderUrl);//url
                    //  help.putString(IntentKey.WEB_VIEW_URL, "http://192.168.1.80:3333/"+IntentKey.HUBEI11C5);
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    help.putString(IntentKey.ACTION_BAR_TITLE, mData.get(j).getLotteryName());
                    // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
                    help.putInt(IntentKey.OPEN_TYPE, 2); //先用2
                    toActivity(WebActivity.class, help);



                }
            }
        });

    }

    public int getType() {
        return type;
    }

    public void setNo_data(){
        no_data.setVisibility(View.VISIBLE);
    }
    public void setNo_net(){
        no_net_word.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadding(String loaddingMessage) {
        super.showLoadding(loaddingMessage);
        no_net_word.setVisibility(View.GONE);
        no_data.setVisibility(View.GONE);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEventMainThread(DealOrderSuccess info) {
        page=0;
        getData(type, 0);


    }

    public void hideNoData(){
        no_data.setVisibility(View.GONE);
        no_net_word.setVisibility(View.GONE);
    }
}
