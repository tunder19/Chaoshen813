package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.youth.xframe.utils.XAppUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {
    @Bind(R.id.ban_quan)
    TextView banQuan;
    @Bind(R.id.version_code)
    TextView version_code;
    @Bind(R.id.a_icon)
    ImageView a_icon;


    @Override
    public int setLayoutId() {
        return R.layout.activity_about;
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
        banQuan.setText("版权 : "+getResources().getString(R.string.app_name)+"版权所有");
        version_code.setText("V"+XAppUtils.getVersionName(getContext())+"");
        a_icon.setImageResource(R.mipmap.app_icon);
    }

}
