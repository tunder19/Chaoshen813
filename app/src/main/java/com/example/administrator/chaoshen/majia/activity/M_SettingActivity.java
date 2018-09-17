package com.example.administrator.chaoshen.majia.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.util.CleanMessageUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class M_SettingActivity extends BaseActivity {
    @Bind(R.id.about_setting)
    RelativeLayout aboutSetting;
    @Bind(R.id.clear_cache)
    RelativeLayout clearCache;
    @Bind(R.id.cache_size)
    TextView cache_size;


    @Override
    public int setLayoutId() {
        return R.layout.m_activity_settting;
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
        setActionBarText("设置");
        changeBarColor();
        aboutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(M_AboutActivity.class,null);
            }
        });
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
            }
        });
        try {
            cache_size.setText(CleanMessageUtil.getTotalCacheSize(getContext()) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void clearCache() {
        if (getUserCache() != null && getBannerCache() != null) {
            UserBean user = (UserBean) getUserCache().clone();
            BannerBean data = (BannerBean) ((BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN)).clone();
            //   String serviceUrl = mCache.getAsString(IntentKey.SERVICE_LIST);
            //  String help_center = mCache.getAsString(IntentKey.HELP_CENTER);
            // String service_url = mCache.getAsString(IntentKey.SERVICE_URL);
            // String h5_url = mCache.getAsString(IntentKey.H5_URL);


            //  CleanMessageUtil.clearAllCache(getContext());
            mCache.clear();
            mCache.put(IntentKey.USER, user);
            mCache.put(IntentKey.BANNER_BEAN, data);


            // mCache.put(IntentKey.SERVICE_LIST, serviceUrl);
            //  mCache.put(IntentKey.HELP_CENTER, help_center);
            // mCache.put(IntentKey.SERVICE_URL, service_url);
            //  mCache.put(IntentKey.H5_URL, h5_url);
            showMsg("清理成功");
            cache_size.setText("");
        }else {
            mCache.clear();
            showMsg("清理成功");
            cache_size.setText("");
        }
    }

}
