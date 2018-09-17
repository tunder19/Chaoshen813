package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.adapter.TicketAdapter;
import com.example.administrator.chaoshen.adapter.TicketJingCaiAdapter;
import com.example.administrator.chaoshen.bean.TickDeatilBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.TicketNetBean;
import com.example.administrator.chaoshen.presenter.TicketPresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TicketDeatilActivity extends BaseActivity {
    @Bind(R.id.refreshLayout_ll)
    PullToRefreshListView refreshLayoutLl;
    private boolean fromHead = true;
    private int page = 1;
    private TicketPresenter mPresenter;
    private long orderId = 0;
    private AppBaseAdapter adapter;
    private List<TickDeatilBean> mData = new ArrayList<>();
    private String lotteryType;

    @Override
    public int setLayoutId() {
        return R.layout.activtyi_ticket_deatil;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        if (getIntent() != null) {
            orderId = getIntent().getLongExtra(IntentKey.ORDER_ID, 0);
            lotteryType = getIntent().getStringExtra(IntentKey.LOTTERY_TYPE);
            XLog.e("-------------lotteryType----"+lotteryType);
        }
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }

    @Override
    public void initView() {
        setActionBarText("出票明细");
        if (!isLogin()) {
            return;
        }
        mPresenter = new TicketPresenter(this, getContext());
        mPresenter.getTickets(new TicketNetBean(getUserCache().getToken(), orderId, page), fromHead);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO 自动生成的方法存根
                refreshLayoutLl.setRefreshing(true);
            }
        }, 200);
        if ("winlose".equals(lotteryType)) {
            adapter = new TicketAdapter(getContext());
        } else if ("jingcai".equals(lotteryType)) {
            adapter = new TicketJingCaiAdapter(getContext());
        }
        refreshLayoutLl.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        refreshLayoutLl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                fromHead = true;
                page = 1;
                mPresenter.getTickets(new TicketNetBean(getUserCache().getToken(), orderId, page), fromHead);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                fromHead = false;
                mPresenter.getTickets(new TicketNetBean(getUserCache().getToken(), orderId, page), fromHead);
            }
        });
        refreshLayoutLl.setLastItem(new PullToRefreshListView.LastItem() {
            @Override
            public void onLastItem() {
                fromHead = false;
                mPresenter.getTickets(new TicketNetBean(getUserCache().getToken(), orderId, page), fromHead);
            }
        });



        refreshLayoutLl.setAdapter(adapter);
    }

    public void getData(List<TickDeatilBean> data, boolean fromHead) {
        page++;
        if (fromHead) {

            if ("winlose".equals(lotteryType)) {
                ((TicketAdapter) adapter).getData().clear();
                ((TicketAdapter) adapter).setData(data);
            } else if ("jingcai".equals(lotteryType)) {
                ((TicketJingCaiAdapter) adapter).getData().clear();
                ((TicketJingCaiAdapter) adapter).setData(data);
            }


        } else {
            if ("winlose".equals(lotteryType)) {
                ((TicketAdapter) adapter).getData().addAll(data);
            } else if ("jingcai".equals(lotteryType)) {
                ((TicketJingCaiAdapter) adapter).getData().addAll(data);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void stopRefresh() {
        refreshLayoutLl.onRefreshComplete();
    }

}
