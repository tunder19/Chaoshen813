package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HasRealNameActivity extends BaseActivity {
    @Bind(R.id.real_name)
    TextView realName;
    @Bind(R.id.id_number)
    TextView idNumber;

    @Override
    public int setLayoutId() {
        return R.layout.activity_has_realname;
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
        setActionBarText("实名认证");

        if (isLogin()){
            realName.setText(getUserCache().getUserName());
            idNumber.setText(getUserCache().getIdNum());

        }else {

        }
    }


}
