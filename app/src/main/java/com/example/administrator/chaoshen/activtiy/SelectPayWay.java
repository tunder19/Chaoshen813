package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.DirectPaYAdapter;
import com.example.administrator.chaoshen.adapter.RechargeistAdapter;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.widget.PayDialog;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectPayWay extends BaseActivity {
    @Bind(R.id.pay_method)
    ListView payMethod;
    @Bind(R.id.pay_bt)
    Button payBt;
    private DirectPaYAdapter adapter;
    private int selectPosition = 0;

    @Override
    public int setLayoutId() {
        return R.layout.activity_select_payway;
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
        super.initView();
        selectPosition = getIntent().getIntExtra(IntentKey.PAY_WAY_SELECTION, 0);
        setActionBarText("支付方式");
        List<RechargeListBean> rechargeListBeans = getBannerCache().getRechargeListBeans();
        adapter = new DirectPaYAdapter(this, getContext(), rechargeListBeans,selectPosition);
        payMethod.setAdapter(adapter);
        payMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                XLog.e("------onItemClick----------" + i);
                adapter.clearCheckBox();
                CheckBox checkBox = view.findViewById(R.id.checkbox);
                checkBox.setChecked(true);
                selectPosition = i;
            }
        });

        payBt.setEnabled(true);
        payBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayDialog.selectPosition = selectPosition;
                PayDialog.setPayWay();
                finish();
            }
        });
    }


}
