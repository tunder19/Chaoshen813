package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.LuckyMoneyListAdapter;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.OrdertRedPacketsNetBean;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UseLuckyMoneyActivity extends BaseActivity {
    @Bind(R.id.refreshLayout_1)
    ListView refreshLayout1;
    private FragmentTransaction trx;
    private List<LuckyMoneyBean> redList;
    private LuckyMoneyListAdapter adapter;
    private long redId=0;

    @Override
    public int setLayoutId() {
        return R.layout.activity_user_luckymoney;
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
        if (getIntent()!=null){
            redId=   getIntent().getLongExtra(IntentKey.RED_ID,0);
        }

        setActionBarText("使用红包");
        redList = (List<LuckyMoneyBean>) getIntent().getSerializableExtra(IntentKey.ORDER_LIST);
        adapter=new LuckyMoneyListAdapter(this,getContext(),redList,redId);
        refreshLayout1.setAdapter(adapter);
        View foot=View.inflate(getContext(),R.layout.layout_nouse_lucky,null);
        refreshLayout1.addFooterView(foot);
        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LuckyMoneyBean bean =new LuckyMoneyBean();
                bean.setId(0);
                bean.setRedPacketAmount(0);
                setResult(bean);
                finish();
            }
        });
    }

    public void setResult(LuckyMoneyBean bean) {
        Intent in = new Intent();
        in.putExtra(IntentKey.GET_LUCKY_MONEYID,bean);
        getActivity().setResult(Constants.SELECT_LUCKY_MONEY, in);
        getActivity().finish();
    }
}
