package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.net.bean.ChangePasswordNetBean;
import com.example.administrator.chaoshen.net.bean.GetVerifyCodeNetBean;
import com.example.administrator.chaoshen.presenter.PasswordEditPresenter;
import com.youth.xframe.utils.XKeyboardUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordEditActivity extends BaseActivity {

    @Bind(R.id.user_old_pass)
    EditText userOldPass;
    @Bind(R.id.clear_old_pass)
    ImageView clearOldPass;
    @Bind(R.id.user_new_pass)
    EditText userNewPass;
    @Bind(R.id.clear_new_pass)
    ImageView clearNewPass;
    @Bind(R.id.button_change)
    Button buttonChange;
    @Bind(R.id.pass_code)
    EditText passCode;
    @Bind(R.id.get_code)
    TextView getCode;
    private PasswordEditPresenter mPresenter;

    @Override
    public int setLayoutId() {
        return R.layout.activtiy_passwordedit;
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
        setActionBarText("修改密码");
        //Intent data = new Intent();
        mPresenter = new PasswordEditPresenter(this, getContext());
        clearOldPass.setVisibility(View.INVISIBLE);
        clearNewPass.setVisibility(View.INVISIBLE);
        userOldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (userOldPass.getText().length()>0){
                    clearOldPass.setVisibility(View.VISIBLE);
                }else {
                    clearOldPass.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (userNewPass.getText().length()>0){
                    clearNewPass.setVisibility(View.VISIBLE);
                }else {
                    clearNewPass.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.clear_old_pass, R.id.clear_new_pass, R.id.button_change, R.id.get_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_old_pass:
                userOldPass.setText("");
                break;
            case R.id.clear_new_pass:
                userNewPass.setText("");
                break;
            case R.id.button_change:
                //提交申请
                String oldPass = userOldPass.getText().toString().trim();
                String newPass = userNewPass.getText().toString().trim();
                String code = passCode.getText().toString().trim();
                if (oldPass.length() > 1 && newPass.length() > 1 && code.length() > 1) {
                    if (!oldPass.equals(newPass)) {
                        showMsg("密码不一致");
                        return;
                    } else {
                         mPresenter.editPass(new ChangePasswordNetBean(getUserCache().getToken(),passCode.getText().toString().trim(),oldPass));
                    }
                }
                break;
            case R.id.get_code:
                //获取验证码
                if (isLogin()) {
                    mPresenter.get_vercode(new GetVerifyCodeNetBean("", getUserCache().getToken(), 3));
                }
                break;
        }
    }


    public void startTime() {
        sendEmptyUiMessageDelayed(1, 1000);
    }

    private int count = 60;

    @Override
    public void handlerMessage(Message msg) {

        if (msg.what == 1) {
            mUiHandler.removeMessages(1);
            if (count > 0) {
                count--;
                getCode.setText(count + "秒");
                getCode.setClickable(false);
                startTime();
            }else {
                count=60;
                getCode.setText("重新发送");
                getCode.setClickable(true);
            }
        }

    }

    @Override
    protected void onDestroy() {
        mUiHandler.removeMessages(1);
        super.onDestroy();

    }

    public void showPassWord(){
        passCode.setText("");
        passCode.performClick();
        XKeyboardUtils.openKeyboard(getContext(),passCode);
    }




}
