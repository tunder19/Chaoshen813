package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.net.bean.FeedBackNetBean;
import com.example.administrator.chaoshen.presenter.KefuPresenter;
import com.youth.xframe.utils.XKeyboardUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KeFuActivity extends BaseActivity {
    @Bind(R.id.commit_editext)
    EditText commitEditext;
    @Bind(R.id.word_limit)
    TextView wordLimit;
    @Bind(R.id.commit_editext_father)
    RelativeLayout commitEditextFather;
    @Bind(R.id.button_login)
    Button buttonLogin;
    private KefuPresenter mPresenter;

    @Override
    public int setLayoutId() {
        return R.layout.activtiy_kefu;
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
        setActionBarText("意见反馈");
        mPresenter=new KefuPresenter(this,getContext());
       // XKeyboardUtils.openKeyboard(getContext(), commitEditext);
        commitEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = commitEditext.getText().toString().length();
                wordLimit.setText(length + "/200");
                if (length>5){
                    buttonLogin.setEnabled(true);
                }else {
                    buttonLogin.setEnabled(false);
                }
            }
        });

        getBackLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.button_login)
    public void onViewClicked() {
        mPresenter.feedback(new FeedBackNetBean( commitEditext.getText().toString()));
        closeKeyboard();

    }
}
