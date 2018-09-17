package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;

import com.example.administrator.chaoshen.R;

public class PayActivity extends BaseActivity {
    @Override
    public int setLayoutId() {
        return R.layout.activty_pay;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {

    }

    @Override
    public boolean showActionBar() {
        return false;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }
}
