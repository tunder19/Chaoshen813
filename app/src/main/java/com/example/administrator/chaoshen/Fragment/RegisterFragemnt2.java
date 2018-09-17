package com.example.administrator.chaoshen.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.RegisterActivity;
import com.example.administrator.chaoshen.net.bean.RegistNetBean;
import com.example.administrator.chaoshen.presenter.RegisterPresenter;
import com.youth.xframe.widget.XToast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterFragemnt2 extends BaseFragment implements TextWatcher {
    @Bind(R.id.notice)
    TextView notice;
    @Bind(R.id.password_1)
    EditText password1;
    @Bind(R.id.password_2)
    EditText password2;
    @Bind(R.id.button_login)
    Button buttonLogin;
    private RegisterPresenter mPresenter;

    public static RegisterFragemnt2 newInstance() {

        Bundle args = new Bundle();

        RegisterFragemnt2 fragment = new RegisterFragemnt2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragemtn_register2;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);

        mPresenter = new RegisterPresenter(this, getContext());
        int type = ((RegisterActivity) getActivity()).getType();
        if (type == 0) {
            notice.setText("请设置登录密码");
            ((RegisterActivity) getActivity()).setLeftText("上一步");
            buttonLogin.setText("确定注册");
        } else if (type == 1) {
            notice.setText("重新设置登录密码");
            //notice.setText("验证成功，请重置密码");
            ((RegisterActivity) getActivity()).setLeftText("上一步");
            buttonLogin.setText("确定");
        } else if (type == 2) {
            notice.setText("绑定成功，请设置登录密码");
            ((RegisterActivity) getActivity()).setLeftText("上一步");
            buttonLogin.setText("确定");
        }

        password1.addTextChangedListener(this);
        password2.addTextChangedListener(this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leng1 = password1.getText().toString().trim();
                String leng2 = password2.getText().toString().trim();
                if (leng1.equals(leng2)) {
                    if (type == 1) {
                        mPresenter.forget_password(new RegistNetBean(((RegisterActivity) getActivity()).getMobile(),
                                leng1, ((RegisterActivity) getActivity()).getVerCode(), ((RegisterActivity) getActivity()).getInviteCode(),
                                ((RegisterActivity) getActivity()).getThirdId()));
                    } else {
                        mPresenter.registUser(new RegistNetBean(((RegisterActivity) getActivity()).getMobile(),
                                leng1, ((RegisterActivity) getActivity()).getVerCode(), ((RegisterActivity) getActivity()).getInviteCode(),
                                ((RegisterActivity) getActivity()).getThirdId()));
                    }
                } else {
                    XToast.normal("密码不一致");
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String leng1 = password1.getText().toString().trim();
        String leng2 = password2.getText().toString().trim();
        if (leng1.length() > 1 && leng2.length() > 1) {
            buttonLogin.setEnabled(true);
        } else {
            buttonLogin.setEnabled(false);
        }
    }
}
