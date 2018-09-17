package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserEditActivity extends BaseActivity {
    @Bind(R.id.user_nick_name)
    EditText userNickName;
    @Bind(R.id.clear_name)
    ImageView clearName;

    @Override
    public int setLayoutId() {
        return R.layout.activtiy_useredit;
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
        setActionBarText("个人资料");
        Intent data = new Intent();
        if (isLogin()) {
            userNickName.setText(getUserCache().getNickname());
        }
        userNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
             /*   data.putExtra(IntentKey.GET_NICKNAME, userNickName.getText().toString());
                setResult(Constants.CHANGE_USER_NICKNAME, data);*/
            }
        });

        Button bt = getRightIcon();
        bt.setVisibility(View.VISIBLE);
        bt.setText("保存");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(userNickName.getText().toString())) {
                    data.putExtra(IntentKey.GET_NICKNAME, userNickName.getText().toString());
                    setResult(Constants.CHANGE_USER_NICKNAME, data);
                    finish();
                }else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.clear_name)
    public void onViewClicked() {
        userNickName.setText("");
    }
}
