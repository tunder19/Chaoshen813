package com.example.administrator.chaoshen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.LuckyMoneyActivity;
import com.example.administrator.chaoshen.adapter.LuckyMoneyAdapter;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.LuckyMoneyNetBean;
import com.example.administrator.chaoshen.presenter.LuckyMoneyPresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.Bind;

public class LuckyMoneyFragment extends BaseFragment {

    @Bind(R.id.refreshLayout_1)
    PullToRefreshListView refreshLayout1;
    @Bind(R.id.no_lucky_money)
    TextView no_lucky_money;
    @Bind(R.id.no_net_word)
    TextView no_net_word;

    private int page = 1;
    private LuckyMoneyPresenter mPresenter;
    private int status;
    private LuckyMoneyAdapter mAdapter;
    private boolean fromShoppingCar = false;

    public static LuckyMoneyFragment newInstance(int status, boolean isFromShoppingCar) { //0是查询可用的 1是查询过期的

        Bundle args = new Bundle();
        args.putInt(IntentKey.LUCKY_MONEY, status);
        args.putBoolean(IntentKey.ISFROMSHOPPINGCAR, isFromShoppingCar);
        LuckyMoneyFragment fragment = new LuckyMoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dealrecord;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        super.initView();
        if (getArguments() != null) {
            status = getArguments().getInt(IntentKey.LUCKY_MONEY);
            fromShoppingCar = getArguments().getBoolean(IntentKey.ISFROMSHOPPINGCAR);
            mPresenter = new LuckyMoneyPresenter(this, getContext());
            getNet(0, status);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO 自动生成的方法存根
                    refreshLayout1.setRefreshing(true);
                }
            }, 200);


            refreshLayout1.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
           // refreshLayout1.setMode(PullToRefreshBase.Mode.BOTH);
            refreshLayout1.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    page=1;
                    getNet(0, status);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    getNet(1, status);
                }
            });

            refreshLayout1.setLastItem(new PullToRefreshListView.LastItem() {
                @Override
                public void onLastItem() {
                    getNet(1,status);
                }
            });

            mAdapter = new LuckyMoneyAdapter(this, getContext(), fromShoppingCar);
            refreshLayout1.setAdapter(mAdapter);
        }
    }

    private void getNet(int type, int status) {
        mPresenter.get_luckymoney(new LuckyMoneyNetBean(getUserCache().getToken(), page, status), type);
    }

    public void getData(List<LuckyMoneyBean> data, int fromWhere) {//0从头部刷新  1加载更多
        page++;
        if (fromWhere == 0) {

            mAdapter.setmData(data);

        } else {
            mAdapter.getmData().addAll(data);
        }

        mAdapter.notifyDataSetChanged();


    }

    public void setResult(LuckyMoneyBean bean) {
        Intent in = new Intent();
        in.putExtra(IntentKey.GET_LUCKY_MONEYID, bean);
        getActivity().setResult(Constants.SELECT_LUCKY_MONEY, in);
        getActivity().finish();
    }

    public void setTitle(int cont) {
        ((LuckyMoneyActivity) getActivity()).setTitle1(cont);
    }

    public void stopLoading() {
        refreshLayout1.onRefreshComplete();
    }

    public void  setNo_lucky_money(){
        no_lucky_money.setVisibility(View.VISIBLE);
    }
    public void  setno_net_word(){
        no_net_word.setVisibility(View.VISIBLE);
    }
    @Override
    public void showLoadding(String loaddingMessage) {
        super.showLoadding(loaddingMessage);
        no_net_word.setVisibility(View.GONE);
        no_lucky_money.setVisibility(View.GONE);

    }


}
