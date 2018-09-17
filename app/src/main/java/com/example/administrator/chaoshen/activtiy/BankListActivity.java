package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.BankListAdapter;
import com.example.administrator.chaoshen.bean.BankCardsFBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BankListActivity extends BaseActivity {
    @Bind(R.id.refresh_list)
    ListView refreshList;
    private int selectItem=-1;
    private BankCardsFBean data;
    private BankListAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_banklist;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        data = (BankCardsFBean) getIntent().getSerializableExtra(CashActivity.getBankList);

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
    public void finish() {
        Intent intent = new Intent();
        selectItem=adapter.getSelectPosition();
        intent.putExtra(CashActivity.getBankList, selectItem);
        setResult(Constants.RETURN_SELECT_BANK, intent);
        super.finish();
    }

    @Override
    public void initView() {
        setActionBarTitle("选择银行卡");
        if (data != null) {
            adapter=new BankListAdapter(this,this,data.getList());
            refreshList.setAdapter(adapter);
        }

    }


}
