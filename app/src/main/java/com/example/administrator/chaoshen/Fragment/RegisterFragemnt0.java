package com.example.administrator.chaoshen.Fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.RegisterActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.GetVerifyCodeNetBean;
import com.example.administrator.chaoshen.presenter.RegisterPresenter;
import com.example.administrator.chaoshen.widget.CustomNumKeyView;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.lang.reflect.Method;

import butterknife.Bind;

public class RegisterFragemnt0 extends BaseFragment implements CustomNumKeyView.CallBack {

    @Bind(R.id.notice)
    TextView notice;
    @Bind(R.id.mobile_ico)
    ImageView mobileIco;
    @Bind(R.id.mobile_ed)
    EditText mobileEd;
    @Bind(R.id.button_login)
    Button buttonLogin;
    @Bind(R.id.mobile_clear)
    ImageView mobile_clear;

    @Bind(R.id.cancel_bt)
    TextView cancel_bt;
    @Bind(R.id.finish_bt)
    TextView finish_bt;


    private LinearLayout fahter;
    private PopupWindow mPop;
    private View mPopView;
    private CustomNumKeyView mCustomKeyView;
    private RegisterPresenter mPresenter;
    private CheckBox checkbox;
    private TextView server_layout;
    private LinearLayout server_layout_LL,keyboardview_ll;


    public static RegisterFragemnt0 newInstance() {

        Bundle args = new Bundle();

        RegisterFragemnt0 fragment = new RegisterFragemnt0();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragemtn_register0;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initViews(View root) {
        mPresenter = new RegisterPresenter(this, getContext());
        super.initViews(root);
        int type = ((RegisterActivity) getActivity()).getType();
        if (type == 0) {
            notice.setText("使用手机号码注册");
            ((RegisterActivity) getActivity()).setLeftText("注册");
        } else if (type == 1) {
            notice.setText("使用手机号码修改密码");
            ((RegisterActivity) getActivity()).setLeftText("找回密码");
        } else if (type == 2) {
            notice.setText("首次使用第三方应用登录需绑定手机");
            ((RegisterActivity) getActivity()).setLeftText("绑定手机");
        }
        // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        keyboardview_ll=root.findViewById(R.id.keyboardview_ll);
        mobile_clear.setVisibility(View.INVISIBLE);
        checkbox = root.findViewById(R.id.checkbox);
        server_layout = root.findViewById(R.id.server_layout);
        server_layout_LL = root.findViewById(R.id.server_layout_ll);
        //mobileEd.setInputType(InputType.TYPE_NULL);
        //disableShowInput(mobileEd);
        fahter = root.findViewById(R.id.fahter);
        mCustomKeyView = (CustomNumKeyView) root.findViewById(R.id.keyboardview);
        mobileEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPop.showAtLocation(fahter, Gravity.BOTTOM, 0, 0);
                //mCustomKeyView.setVisibility(View.VISIBLE);
                keyboardview_ll.setVisibility(View.VISIBLE);
            }
        });
        fahter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCustomKeyView.setVisibility(View.GONE);
                keyboardview_ll.setVisibility(View.GONE);
            }
        });



        initPop();
        if (type == 1) {
            server_layout_LL.setVisibility(View.GONE);
        }
        server_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                BannerBean banner = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
                data.putString(IntentKey.WEB_VIEW_URL, banner.getServiceUrl());//url
                data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data.putString(IntentKey.ACTION_BAR_TITLE, "服务协议");
                data.putInt(IntentKey.OPEN_TYPE, 9);
                toActivity(WebActivity.class, data);
            }
        });

        mobileEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = mobileEd.getText().toString().trim().length();
                if (length > 0) {
                    buttonLogin.setEnabled(true);
                    mobile_clear.setVisibility(View.VISIBLE);
                } else {
                    buttonLogin.setEnabled(false);
                    mobile_clear.setVisibility(View.INVISIBLE);
                }
                ((RegisterActivity) getActivity()).setMobile(mobileEd.getText().toString().trim());

            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkbox.isChecked()) {
                    XToast.normal("需要同意服务协议");
                    return;
                }
                //下一步发送验证码
                int length = mobileEd.getText().toString().trim().length();
                if (length != 11) {
                    XToast.normal("手机需要为11位");
                    return;
                }
                ((RegisterActivity) getActivity()).setMobile(mobileEd.getText().toString().trim());
                if (type == 0 || type == 1 || type == 2) { //判断手机是否已经注册
                    mPresenter.get_vercode(new GetVerifyCodeNetBean(((RegisterActivity) getActivity()).getMobile(), null, ((RegisterActivity) getActivity()).getType()));
                } else {
                    ((RegisterActivity) getActivity()).next();
                }
            }
        });
        mobile_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileEd.setText("");
            }
        });
    }

    public void toNext() {
        ((RegisterActivity) getActivity()).next();
    }

    private void initPop() {
       /* mPop = new PopupWindow();
        mPopView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.custom_keyboardview, null);
        mPop.setContentView(mPopView);
        mPop.setTouchable(true);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new ColorDrawable());
        mPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mCustomKeyView = (CustomNumKeyView) mPopView.findViewById(R.id.keyboardview);*/
        // 设置回调，并进行文本的插入与删除

        mCustomKeyView.setOnCallBack(this,mobileEd);
        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardview_ll.setVisibility(View.GONE);
            }
        });
        finish_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardview_ll.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void clickNum(String num) {
        XLog.e("----TextUtils.isEmpty(num)------------" + TextUtils.isEmpty(num));
        if (TextUtils.isEmpty(num) || " ".equals(num)) {
            return;
        }
        if (mobileEd.getText().length() < 11) {
            mobileEd.append(num);
            //文本长度为6时隐藏键盘
            if (mobileEd.getText().length() == 11) {
                // mCustomKeyView.
                sendEmptyUiMessageDelayed(1, 300);

            }
        }
    }

    @Override
    public void deleteNum() {
        int last = mobileEd.getText().length();
        if (last > 0) {
            //删除最后一位
            mobileEd.getText().delete(last - 1, last);
        }
    }


    @Override
    public void handlerMessage(Message msg) {
        super.handlerMessage(msg);
        if (msg.what == 1) {
            if (mPop != null) {
                mPop.dismiss();
            }
            keyboardview_ll.setVisibility(View.GONE);
            //mCustomKeyView.setVisibility(View.GONE);
        }
    }


    public void disableShowInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {//TODO: handle exception
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {//TODO: handle exception
            }
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setSelection(editText.length(), editText.length());
            }
        });


    }


}
