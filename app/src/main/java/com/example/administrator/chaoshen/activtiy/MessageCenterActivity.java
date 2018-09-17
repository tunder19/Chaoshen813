package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.MessageCenterAdapter;
import com.example.administrator.chaoshen.bean.DealDeatilBean;
import com.example.administrator.chaoshen.bean.MessageCenterBean;
import com.example.administrator.chaoshen.net.bean.GetDealNetBean;
import com.example.administrator.chaoshen.presenter.MessageCenterPresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageCenterActivity extends BaseActivity {
    @Bind(R.id.refreshLayout_1)
    PullToRefreshListView refreshLayout1;
    @Bind(R.id.no_data)
    TextView no_data;
    private MessageCenterPresenter messageCenterPresenter;
    private int page = 1;
    private MessageCenterAdapter madapter;


    @Override
    public int setLayoutId() {
        return R.layout.actiity_messagecenter;
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
        setActionBarText("通知消息");
        messageCenterPresenter=new MessageCenterPresenter(this,getContext());
        madapter=new MessageCenterAdapter(this);


        refreshLayout1.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
       // refreshLayout1.setMode(PullToRefreshBase.Mode.BOTH);
        refreshLayout1.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        refreshLayout1.setLastItem(new PullToRefreshListView.LastItem() {
            @Override
            public void onLastItem() {
                getNet(1);
            }
        });


        refreshLayout1.setAdapter(madapter);
        getNet(0);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO 自动生成的方法存根
                refreshLayout1.setRefreshing(true);
            }
        }, 200);
    }

    private void getNet(int type) {
        messageCenterPresenter.getMessages(new GetDealNetBean(getUserCache().getToken(), page), type);
    }

    public void getData(List<MessageCenterBean> data,int fromWhere) {//0从头部刷新  1加载更多
        page++;
        if (fromWhere==0){
            madapter.setData(data);

        }else {
            madapter.getData().addAll(data);
        }

        madapter.notifyDataSetChanged();


    }

    public void stopLoading(){
        refreshLayout1.onRefreshComplete();
    }

    public void setNo_data(){
        no_data.setVisibility(View.VISIBLE);
    }
    @Override
    public void showLoadding(String loaddingMessage) {
        super.showLoadding(loaddingMessage);
        no_data.setVisibility(View.GONE);

    }


}
