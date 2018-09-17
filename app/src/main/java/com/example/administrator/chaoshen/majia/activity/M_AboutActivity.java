package com.example.administrator.chaoshen.majia.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class M_AboutActivity extends BaseActivity {


    @Bind(R.id.ban_quan)
    TextView banQuan;
    @Bind(R.id.a_icon)
    ImageView aIcon;

    @Override
    public int setLayoutId() {
        return R.layout.m_activity_about;
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
        setActionBarText("关于");
        changeBarColor();
        banQuan.setText(getResources().getString(R.string.app_name) + "数据助手版权所有2017~2018");
        aIcon.setImageResource(R.mipmap.app_icon);
    }

}
