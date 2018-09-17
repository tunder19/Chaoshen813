package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.HomeToMineBean;
import com.example.administrator.chaoshen.contans.IntentKey;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class FinishRegistActivity extends BaseActivity {
    @Bind(R.id.notice_tx)
    TextView noticeTx;
    @Bind(R.id.button_login)
    Button realName;
    @Bind(R.id.real_name)
    TextView pass;
    private int type;
    private TextView leftButton;

    @Override
    public int setLayoutId() {
        return R.layout.activity_finishegistctivity;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        type = getIntent().getExtras().getInt(IntentKey.REGISTER_TYPE);
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
        showBackwardView(0, false);
        setLeftText("绑定成功");
        if (type == 0) {
            noticeTx.setText("注册成功！");
            pass.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            noticeTx.setText("重置密码已成功！");
            realName.setText("进入个人中心");
            pass.setVisibility(View.GONE);
        } else if (type == 2) {
            noticeTx.setText("绑定成功！");
            pass.setVisibility(View.VISIBLE);
        }


        if (isLogin()) {
            if (type != 1) {
                if (1 == getUserCache().getIsRealName()) {
                    realName.setVisibility(View.GONE);
                } else {
                    realName.setVisibility(View.VISIBLE);
                }
            }
        }


        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // XToast.normal("实名认证");
                    EventBus.getDefault().post(new HomeToMineBean());
                    finish();
            }
        });

        realName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type != 1) {

                    toActivity(RealNameActivity.class, null);
                    finish();
                } else {

                    EventBus.getDefault().post(new HomeToMineBean());
                    finish();
                }



            }
        });

    }

    public void setLeftText(String notiec) {
        setActionBarText("");
        leftButton = (TextView) findViewById(R.id.button_backward_tx);
        leftButton.setText(notiec + "");
        leftButton.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
