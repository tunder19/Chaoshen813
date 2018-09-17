package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.ActivityAdapter;
import com.example.administrator.chaoshen.adapter.NoticeAdapter;
import com.example.administrator.chaoshen.bean.ActivityBean;
import com.example.administrator.chaoshen.bean.NoticeBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.NoticeNetBean;
import com.example.administrator.chaoshen.net.bean.InfromationListNetBean;
import com.example.administrator.chaoshen.presenter.NoticePresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoticeActivity extends BaseActivity {
    @Bind(R.id.refresh_list)
    PullToRefreshListView refreshList;
    @Bind(R.id.no_net_word)
    TextView noNetWord;
    @Bind(R.id.no_data)
    TextView noData;

    private int type=0;
    private NoticePresenter mPresenter;
    private int page=1;
    private NoticeAdapter mNoticeAdapter;
    private ActivityAdapter mActivityAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_notice_layout;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        type=  getIntent().getIntExtra(IntentKey.NOTCIE_ACTIVTY,0);//0是公告  1是活动
    }

    @Override
    public void initView() {
        super.initView();
        if (type==0){
            setActionBarText("公告");
        }else {
            setActionBarText("活动");
        }
        mPresenter=new NoticePresenter(this,getContext());

        refreshList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
      //  refreshList.setMode(PullToRefreshBase.Mode.BOTH);
        refreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(0);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(1);
            }
        });

        getData(0);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO 自动生成的方法存根
                refreshList.setRefreshing(true);
            }
        }, 200);

        refreshList.setLastItem(new PullToRefreshListView.LastItem() {
            @Override
            public void onLastItem() {
                getData(1);
            }
        });

    }





    public void getData(int fromHead) {

        if (fromHead == 0) {
            page = 1;
        }
        if (mPresenter != null) {
            mPresenter.getData(new NoticeNetBean(page), fromHead,type);
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


    public void no_data() {
        if (noData != null) {
            noData.setVisibility(View.VISIBLE);
        }
    }

    public void has_data() {
        if (noData != null) {
            noData.setVisibility(View.GONE);
        }
    }

    public void showNetWork() {
        // no_net_word.setVisibility(View.VISIBLE);
    }

    public void stopLoading() {
        if (refreshList != null) {
            refreshList.onRefreshComplete();
        }
    }

    public void getNoticeData(List<NoticeBean.NoticeListBean> data,int form){
            page++;
        if (mNoticeAdapter==null){
            mNoticeAdapter=new NoticeAdapter(getContext(),data);
            refreshList.setAdapter(mNoticeAdapter);
        }
        if (form==0){
            mNoticeAdapter.setData(data);

        }else {
            mNoticeAdapter.getData().addAll(data);

        }
            mNoticeAdapter.notifyDataSetChanged();
    }


    public void getActivityData(List<ActivityBean> data,int form){
        page++;
        if (mActivityAdapter==null){
            mActivityAdapter=new ActivityAdapter(this,getContext(),data);
            refreshList.setAdapter(mActivityAdapter);
        }
        if (form==0){
            mActivityAdapter.setData(data);

        }else {
            mActivityAdapter.getData().addAll(data);

        }
        mActivityAdapter.notifyDataSetChanged();



    }

}
