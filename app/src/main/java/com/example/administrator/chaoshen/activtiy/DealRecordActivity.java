package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.DealDeatilAdapter;
import com.example.administrator.chaoshen.bean.DealDeatilBean;
import com.example.administrator.chaoshen.net.bean.GetDealNetBean;
import com.example.administrator.chaoshen.presenter.DealRecordPresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;


public class DealRecordActivity extends BaseActivity {
    PullToRefreshListView refreshLayout;
    private int page = 1;
    private DealRecordPresenter mPresenter;
    private DealDeatilAdapter madapter;
    private TextView no_net_word,no_data;

    @Override
    public int setLayoutId() {
        return R.layout.activity_dealrecord;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {

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
        setActionBarText("交易记录");
        if (!isLogin()) {
            showMsg("请先登录");
            toActivity(LoginActivity.class,null);
            return;
        }
        no_net_word= (TextView) findViewById(R.id.no_net_word);
        no_data= (TextView) findViewById(R.id.no_data);
        refreshLayout= (PullToRefreshListView) findViewById(R.id.refreshLayout_1);
        madapter=new DealDeatilAdapter(getContext());
        refreshLayout.setAdapter(madapter);
        mPresenter = new DealRecordPresenter(this, getContext());
        getNet(0);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO 自动生成的方法存根
                refreshLayout.setRefreshing(true);
            }
        }, 200);

        //refreshLayout.setMode(PullToRefreshBase.Mode.BOTH);
        refreshLayout.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        refreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
              //  mPresenter.getDealRocrod(new GetDealNetBean(getUserCache().getToken(), page), 0);
                getNet(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
             //   mPresenter.getDealRocrod(new GetDealNetBean(getUserCache().getToken(), page), 1);
                getNet(1);
            }
        });
        refreshLayout.setLastItem(new PullToRefreshListView.LastItem() {
            @Override
            public void onLastItem() {
                getNet(1);
            }
        });

    }

    public void stopLoading(){
        refreshLayout.onRefreshComplete();
    }
    private void getNet(int type) {
        mPresenter.getDealRocrod(new GetDealNetBean(getUserCache().getToken(), page), type);
    }

    public void getData(List<DealDeatilBean.DataBean> data, int fromWhere) {//0从头部刷新  1加载更多
        page++;
        if (fromWhere==0){
            madapter.getData().clear();
           madapter.setData(data);

        }else {
            madapter.getData().addAll(data);
        }

        madapter.notifyDataSetChanged();


    }



    @Override
    public void showLoadding(String loaddingMessage) {
        super.showLoadding(loaddingMessage);
        no_net_word.setVisibility(View.GONE);
        no_data.setVisibility(View.GONE);

    }

    public void setNo_net_word(){
        no_net_word.setVisibility(View.VISIBLE);
    }



    public void setNo_data(){
        no_data.setVisibility(View.VISIBLE);
    }



}
