package com.example.administrator.chaoshen.activtiy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.MyApplication;
import com.example.administrator.chaoshen.bean.FinishRegisterActivityBean;
import com.example.administrator.chaoshen.bean.HideLoadingBean;
import com.example.administrator.chaoshen.bean.QQbean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.LoginThirdNetBean;
import com.example.administrator.chaoshen.net.bean.UserLoginNetBean;
import com.example.administrator.chaoshen.presenter.LoginPresenter;
import com.example.administrator.chaoshen.widget.ClearEditText;
import com.example.administrator.chaoshen.widget.CustomNumKeyView;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youth.xframe.utils.XKeyboardUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class LoginActivity extends BaseActivity implements CustomNumKeyView.CallBack {
    @Bind(R.id.mobile_ed)
    EditText mobileEd;
    @Bind(R.id.password_ed)
    EditText passwordEd;
    @Bind(R.id.button_login)
    Button buttonLogin;
    @Bind(R.id.user_register)
    TextView userRegister;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.wechat_login)
    ImageView wechatLogin;
    @Bind(R.id.qq_login)
    ImageView qqLogin;
    @Bind(R.id.mobile_ico)
    ImageView mobileIco;

    @Bind(R.id.mobile_clear)
    ImageView mobile_clear;
    @Bind(R.id.password_clear)
    ImageView password_clear;

    @Bind(R.id.linear)
    RelativeLayout linear;


    @Bind(R.id.cancel_bt)
    TextView cancel_bt;
    @Bind(R.id.finish_bt)
    TextView finish_bt;
    @Bind(R.id.keyboardview_ll)
    LinearLayout keyboardview_ll;


    private TextView leftButton;

    private PopupWindow mPop;
    private View mPopView;
    private RelativeLayout mLinearlayout;
    private CustomNumKeyView mCustomKeyView;
    private LoginPresenter mPresenter;
    private static Tencent mTencent;
    private UserInfo mInfo;
    public static final int FINISH_WECHAT = 100121;
    private String addS = "LotteryApp";//自定义H5识别


    @Override
    public void handlerMessage(Message msg) {
        super.handlerMessage(msg);
        switch (msg.what) {
            case 1:
                // mPop.dismiss();
                keyboardview_ll.setVisibility(View.GONE);
                sendEmptyUiMessageDelayed(2, 200);
                break;
            case 2:
                showPassWord();
                break;
        }
    }

    public void showPassWord() {
        passwordEd.performClick();
        XKeyboardUtils.openKeyboard(getContext(), passwordEd);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        mTencent = ((MyApplication) getApplication()).mTencent;
        leftButton = (TextView) findViewById(R.id.button_backward_tx);
        leftButton.setText("登录");
        setActionBarText("");
        // mobileEd.setInputType(InputType.TYPE_NULL);
        mobileEd.setShowSoftInputOnFocus(false);
        mLinearlayout = (RelativeLayout) findViewById(R.id.linear);
        mobileEd.clearFocus();
        passwordEd.clearFocus();
        initPop(); //自定义键盘
        passwordEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int mobile = mobileEd.getText().toString().trim().length();
                if (mobile != 0 && passwordEd.getText().toString().trim().length() != 0) {
                    buttonLogin.setEnabled(true);
                } else {
                    buttonLogin.setEnabled(false);
                }

                if (passwordEd.getText().length() > 0) {
                    password_clear.setVisibility(View.VISIBLE);
                } else {
                    password_clear.setVisibility(View.INVISIBLE);
                }

            }
        });

        passwordEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);

                    imm.showSoftInput(passwordEd, 0);
                } else {
                    // 此处为失去焦点时的处理内容
                    InputMethodManager imm = (InputMethodManager) getContext().
                            getSystemService(INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(passwordEd.getWindowToken(), 0);
                }
            }
        });


        mobileEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    keyboardview_ll.setVisibility(View.VISIBLE);
                } else {
                    // 此处为失去焦点时的处理内容
                    keyboardview_ll.setVisibility(View.GONE);
                }
            }
        });
        mobileEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardview_ll.setVisibility(View.VISIBLE);

            }
        });

        mobileEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mobileEd.getText().length() > 0) {
                    mobile_clear.setVisibility(View.VISIBLE);
                } else {
                    mobile_clear.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mPresenter = new LoginPresenter(this, getContext());
       // passwordEd.setCursorVisible(false);

        mobile_clear.setVisibility(View.INVISIBLE);
        password_clear.setVisibility(View.INVISIBLE);

        try {
            if (!TextUtils.isEmpty(getmCache().getAsString(IntentKey.LOGIN_MOBILE))) {
                mobileEd.setText(getmCache().getAsString(IntentKey.LOGIN_MOBILE));
                mobile_clear.setVisibility(View.VISIBLE);
                keyboardview_ll.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XLog.e("------------linear------------");

                passwordEd.clearFocus();
                mobileEd.clearFocus();
            }
        });

    }


    private void initPop() {
        mCustomKeyView = (CustomNumKeyView) findViewById(R.id.keyboardview);
        mCustomKeyView.setVisibility(View.VISIBLE);
        // 设置回调，并进行文本的插入与删除
        mCustomKeyView.setOnCallBack(this, mobileEd);

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

    @OnClick({R.id.mobile_ed, R.id.mobile_clear, R.id.password_clear, R.id.password_ed, R.id.button_login, R.id.user_register, R.id.forget_password, R.id.wechat_login, R.id.qq_login})
    public void onViewClicked(View view) {
        Bundle data;
        switch (view.getId()) {

            case R.id.button_login:

                String password = passwordEd.getText().toString().trim();
                if ((mobileEd.getText().toString().trim()).length() != 11) {
                    XToast.normal("手机号需要填写11位");
                    return;
                } else if (password.length() < 6 || password.length() > 20) {
                    XToast.normal("密码长度位6~20位");
                    return;
                }
                passwordEd.clearFocus();
                mobileEd.clearFocus();
                mPresenter.userLogin(new UserLoginNetBean(mobileEd.getText().toString().trim(), password));
                break;

            case R.id.password_ed:
                passwordEd.requestFocus();
                //passwordEd.setCursorVisible(true);
                //showSoftKeyV(passwordEd);
                keyboardview_ll.setVisibility(View.GONE);
                // mPop.dismiss();
                passwordEd.requestFocus();
                break;
            case R.id.user_register:
                // XToast.normal("注册");
                data = new Bundle();
                data.putInt(IntentKey.REGISTER_TYPE, 0); //0是注册 1是找回密码
                toActivity(RegisterActivity.class, data);
                break;
            case R.id.forget_password:
                data = new Bundle();
                data.putInt(IntentKey.REGISTER_TYPE, 1);
                toActivity(RegisterActivity.class, data);
                break;
            case R.id.wechat_login:
                showLoadding(null);
                wxLogin();
                break;
            case R.id.qq_login:
                //  XToast.normal("QQ登录");
                onClickLogin();
                break;

            case R.id.mobile_clear:
                mobileEd.setText("");
                break;
            case R.id.password_clear:
                passwordEd.setText("");
                break;
        }
    }

    @Override
    public void clickNum(String num) {
        XLog.e(num + "=----TextUtils.isEmpty(num)------------" + TextUtils.isEmpty(num));
        if (TextUtils.isEmpty(num) || " ".equals(num)) {
            return;
        }
        if (mobileEd.getText().length() < 11) {
            mobileEd.append(num);
            //文本长度为6时隐藏键盘
            if (mobileEd.getText().length() >= 11) {
                // mCustomKeyView.
                sendEmptyUiMessageDelayed(1, 200);

            }
        }
    }

    @Override
    public void deleteNum() {
        String mobile = mobileEd.getText().toString();
        int last = mobileEd.getText().length();
        if (last > 0) {
            mobile = mobile.substring(0, mobile.length() - 1);
            //删除最后一位
            //  mobileEd.getText().delete(last - 1, last);
            mobileEd.setText(mobile);

        }
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

    @Subscribe
    public void onEventMainThread(RefreshDataBean info) {
        Bundle data = new Bundle();
        finish();

    }

    @Subscribe
    public void onEventMainThread(HideLoadingBean info) {
        XLog.e("--------onEventMainThread--------1--");
        hideLoadding();

    }

    @Subscribe
    public void onEventMainThread(FinishRegisterActivityBean info) {
        Bundle data = new Bundle();
        finish();

    }


    /**
     * QQ登录
     *///////////////////////////////////////////////////////
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        XLog.e("----------requestCode-----------" + requestCode);
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void onClickLogin() {
        showLoadding(null);
        mTencent.login(this, "all", loginListener);
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            hideLoadding();
            //  Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            QQbean info = new ResponseAnalyze<QQbean>().analyze(values.toString(), QQbean.class);
            XLog.e(info.getAccess_token() + "-------QQbean---------" + values.toString());
            if (!TextUtils.isEmpty(info.getAccess_token()) && !TextUtils.isEmpty(info.getOpenid())) {
                //发送请求
                String token = "";
                if (isLogin()) {
                    token = getUserCache().getToken();
                }
                mPresenter.loginInThird(new LoginThirdNetBean(2, info.getOpenid(), info.getAccess_token(), token));
            }

        }
    };

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            hideLoadding();
            XLog.e("------------QQ登录信息--11-" + response);
            if (null == response) {
                XToast.normal("登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                XToast.normal("登录失败");
                return;
            }
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {
            //getQQuserInfo();

        }


        @Override
        public void onError(UiError e) {
            hideLoadding();
            XToast.normal("登录失败");
        }

        @Override
        public void onCancel() {
            hideLoadding();
            XToast.normal("取消登录");
        }
    }

    public void getQQuserInfo() {
        mInfo = new UserInfo(this, mTencent.getQQToken());
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * QQ登录
     * *///////////////////////////////////////////////////////

    /********************微信登录**********************/

    //微信登录
    public void wxLogin() {
        if (!mApp.mWxApi.isWXAppInstalled()) {
            XToast.normal("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        mApp.mWxApi.sendReq(req);


    }


    /************************************************/


}
