package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.Any9Fragment;
import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.RegisterFragemnt0;
import com.example.administrator.chaoshen.Fragment.RegisterFragemnt1;
import com.example.administrator.chaoshen.Fragment.RegisterFragemnt2;
import com.example.administrator.chaoshen.Fragment.WinLoseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.FinishRegisterActivityBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.WinLoseDataBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.youth.xframe.utils.log.XLog;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class RegisterActivity extends BaseWithFragmentActivity {
    private TextView leftButton;
    private int type;//0是注册  1是找回密码 2绑定手机号码
    private String mobile;
    private String verCode;
    private long inviteCode; //邀请码
    private long thirdId;    //第三方的值


    @Override
    public BaseFragment initFragment(int pos) {
        BaseFragment fragment = null;
        switch (pos) {
            case 0:
                fragment = RegisterFragemnt0.newInstance();
                break;
            case 1:
                fragment = RegisterFragemnt1.newInstance();
                break;
            case 2:
                fragment = RegisterFragemnt2.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public void initView() {
        setActionBarText("");
        check(0);
        mBackwardbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPos == 0) {
                    finish();
                } else {
                    mCurrentPos--;
                    check(mCurrentPos);
                }
            }
        });
    }

    public int getType() {
        return type;
    }

    public void setLeftText(String notiec) {
        leftButton = (TextView) findViewById(R.id.button_backward_tx);
        leftButton.setText(notiec + "");
        leftButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        type = getIntent().getExtras().getInt(IntentKey.REGISTER_TYPE);
        thirdId= getIntent().getExtras().getLong(IntentKey.THIRD_ID);
        XLog.e("--------thirdId="+thirdId);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        XLog.e("--------mCurrentPos="+mCurrentPos);
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mCurrentPos == 0) {
                finish();
            } else {
                mCurrentPos--;
                check(mCurrentPos);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public long getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(long inviteCode) {
        this.inviteCode = inviteCode;
    }

    public long getThirdId() {
        return thirdId;
    }

    public void setThirdId(long thirdId) {
        this.thirdId = thirdId;
    }


    @Subscribe
    public void onEventMainThread(FinishRegisterActivityBean info) {
        Bundle data = new Bundle();
        data.putInt(IntentKey.REGISTER_TYPE, type);
        toActivity(FinishRegistActivity.class, data);
        finish();

    }


}
