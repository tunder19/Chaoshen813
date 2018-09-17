package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.administrator.chaoshen.Fragment.Any9Fragment;
import com.example.administrator.chaoshen.Fragment.WinLoseShopingFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.PayOrderSuccess;
import com.example.administrator.chaoshen.bean.WinLoseDataBean;
import com.example.administrator.chaoshen.bean.WinloseMatchesBean;
import com.youth.xframe.utils.log.XLog;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class ShopingCarActivity extends BaseActivity {
    private WinLoseShopingFragment mFragment;



    @Override
    public int setLayoutId() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.layout_activity_shoppingcar;
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initView() {
        setActionBarText("方案内容");

        mFragment = WinLoseShopingFragment.newInstance(getIntent().getExtras());
        mFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.content, mFragment).commit();
        /*mBackwardbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    @Override
    public boolean showActionBar() {
        return true;
    }



    @Override
    public boolean showPopUpBar() {
        return false;
    }



    @Subscribe
    public void onEventMainThread( PayOrderSuccess info) {
           finish();

    }

}
