package com.example.administrator.chaoshen.activtiy;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.net.bean.EditUserNetBean;
import com.example.administrator.chaoshen.presenter.RealNamePresenter;
import com.example.administrator.chaoshen.presenter.RegisterPresenter;
import com.example.administrator.chaoshen.widget.CustomNumKeyView;
import com.example.administrator.chaoshen.widget.KeyBoardPopupwindow;
import com.youth.xframe.utils.XKeyboardUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RealNameActivity extends BaseActivity implements TextWatcher, CustomNumKeyView.CallBack {
    @Bind(R.id.real_name)
    EditText realName;
    @Bind(R.id.real_numer)
    TextView mobileEd;
    @Bind(R.id.button_login)

    Button buttonLogin;
    private RealNamePresenter mPresenter;
    private LinearLayout fahter;
    private View mPopView;
    private CustomNumKeyView mCustomKeyView;
    private PopupWindow mPop;

    @Override
    public int setLayoutId() {
        return R.layout.activity_realname;
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
        mPresenter = new RealNamePresenter(this, getContext());
        setActionBarTitle("实名认证");
        realName.addTextChangedListener(this);
        mobileEd.addTextChangedListener(this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLogin()) {
                    XToast.normal("请先登录");
                    toActivity(LoginActivity.class,null);
                } else {
                    EditUserNetBean data = new EditUserNetBean();
                    data.setIdNum(mobileEd.getText().toString().trim());
                    data.setUserName(realName.getText().toString().trim());
                    data.setToken(getUserCache().getToken());
                    mPresenter.real_name(data);
                }
            }
        });

        realName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realName.setCursorVisible(true);
            }
        });
        mobileEd.setInputType(InputType.TYPE_NULL);
        fahter = (LinearLayout) findViewById(R.id.fahter);
        mobileEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XKeyboardUtils.closeKeyboard(getActivity());
                realName.setCursorVisible(false);
                mPop.showAtLocation(fahter, Gravity.BOTTOM, 0, 0);
            }
        });
        initPop();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String realNameS = realName.getText().toString().trim();
        String realNumerS = mobileEd.getText().toString().trim();
        if (realNameS.length() > 1 && realNumerS.length() > 15) {
            buttonLogin.setEnabled(true);
        } else {
            buttonLogin.setEnabled(false);
        }


    }




    private void initPop() {
        mPop = new PopupWindow();
//        mCustomKeyView=new CustomNumKeyView(this);
        mPopView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.custom_keyboardview, null);
        mPop.setContentView(mPopView);
        mPop.setTouchable(true);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new ColorDrawable());
        mPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mCustomKeyView = (CustomNumKeyView) mPopView.findViewById(R.id.keyboardview);
        mCustomKeyView.setHave_X(true);
        // 设置回调，并进行文本的插入与删除
        mCustomKeyView.setOnCallBack(this);
    }

    @Override
    public void clickNum(String num) {
        if (TextUtils.isEmpty(num) || " ".equals(num)) {
            return;
        }
        if (mobileEd.getText().length() <= 18) {
            mobileEd.append(num);
            //文本长度为6时隐藏键盘
            if (mobileEd.getText().length() == 18) {
                // mCustomKeyView.
                sendEmptyUiMessageDelayed(1, 500);

            }
        }
    }

    @Override
    public void deleteNum() {
        int last = mobileEd.getText().toString().length();
        if (last > 0) {
            //删除最后一位
            String temp;
            temp = (mobileEd.getText().toString()).substring(0, (mobileEd.getText().toString()).length() - 1);
            // mobileEd.getText().delete(last - 1, last);
            mobileEd.setText(temp);
        }
    }
}
