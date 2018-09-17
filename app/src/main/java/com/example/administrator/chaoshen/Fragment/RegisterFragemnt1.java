package com.example.administrator.chaoshen.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.RegisterActivity;
import com.example.administrator.chaoshen.bean.SendCodeSuccess;
import com.example.administrator.chaoshen.net.bean.GetVerifyCodeNetBean;
import com.example.administrator.chaoshen.net.bean.RegistNetBean;
import com.example.administrator.chaoshen.presenter.RegisterPresenter;
import com.example.administrator.chaoshen.widget.CustomNumKeyView;
import com.youth.xframe.utils.XKeyboardUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class RegisterFragemnt1 extends BaseFragment implements CustomNumKeyView.CallBack {

    @Bind(R.id.notice)
    TextView notice;
    @Bind(R.id.mobile_ed)
    EditText mobileEd;
    @Bind(R.id.send_code_bt)
    Button sendCodeBt;
    @Bind(R.id.button_login)
    Button buttonLogin;
    @Bind(R.id.cancel_bt)
    TextView cancel_bt;
    @Bind(R.id.finish_bt)
    TextView finish_bt;
    @Bind(R.id.keyboardview)
    CustomNumKeyView mCustomKeyView;
    @Bind(R.id.keyboardview_ll)
    LinearLayout keyboardview_ll;
    private CheckBox checkbox;
    private RegisterPresenter mPresenter;
    private boolean waitingCode = false;
    private int count = 60;

    public static RegisterFragemnt1 newInstance() {
        XLog.e("-----------RegisterFragemnt1--------");
        Bundle args = new Bundle();

        RegisterFragemnt1 fragment = new RegisterFragemnt1();
        fragment.setArguments(args);
        return fragment;
    }


    public void showPassWord() {
        mobileEd.performClick();
        XKeyboardUtils.openKeyboard(getContext(), mobileEd);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            return;
        } else {  // 在最前端显示 相当于调用了onResume();
            //网络数据刷新
            setNotice();
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        super.handlerMessage(msg);
        if (msg.what == 1) {
            if (count <= 0) {
                waitingCode = false;
                if (isAdded()) {
                    sendCodeBt.setTextColor(Color.parseColor("#3F8BC5"));
                    sendCodeBt.setText("重发短信");
                }
                count = 60;
                removeMeaage();

            } else {
                if (isAdded()) {
                    sendCodeBt.setTextColor(Color.parseColor("#999999"));
                    sendCodeBt.setText(count + "秒");
                }
                count--;
                removeMeaage();
                timeMaker();
            }
        }
    }

    private void timeMaker() {
        sendEmptyUiMessageDelayed(1, 1000);
    }

    private void removeMeaage() {
        mUiHandler.removeMessages(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragemtn_register1;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        mPresenter = new RegisterPresenter(this, getContext());

        checkbox = root.findViewById(R.id.checkbox);

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
                if (length >= 1) {
                    buttonLogin.setEnabled(true);
                } else {
                    buttonLogin.setEnabled(false);
                }
            }
        });
        mobileEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    keyboardview_ll.setVisibility(View.VISIBLE);
                }else {
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

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length = mobileEd.getText().toString().trim().length();
                if (length < 1) {
                    XToast.normal("验证码不能为空");
                } else {
                    if (!checkbox.isChecked()) {
                        XToast.normal("需要同意服务协议");
                    } else {
                        ((RegisterActivity) getActivity()).setVerCode(mobileEd.getText().toString().trim());

                        //判断数据
                        int type = ((RegisterActivity) getActivity()).getType();
                        if (type == 2) { //绑定手机直接走接口
                            mPresenter.registUser(new RegistNetBean(((RegisterActivity) getActivity()).getMobile(),
                                    "", ((RegisterActivity) getActivity()).getVerCode(), ((RegisterActivity) getActivity()).getInviteCode(),
                                    ((RegisterActivity) getActivity()).getThirdId()));
                        } else {
                            // ((RegisterActivity) getActivity()).next();
                            mPresenter.checkCode(new RegistNetBean(((RegisterActivity) getActivity()).getMobile(), "",
                                    ((RegisterActivity) getActivity()).getVerCode(), ((RegisterActivity) getActivity()).getInviteCode(),
                                    ((RegisterActivity) getActivity()).getThirdId()));


                        }


                    }
                }

            }
        });


        sendCodeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waitingCode) {
                    return;
                } else {
                    sendVerCode();
                    sendCodeSuccess();
                }

            }
        });
        int type = ((RegisterActivity) getActivity()).getType();
        if (type != 0 && type != 1 && type != 2) {
            sendCodeBt.performClick();
        }
        timeMaker();

        setNotice();
        //showPassWord();
        initPop();
    }


    private void initPop() {
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




    private void setNotice() {
        int type = ((RegisterActivity) getActivity()).getType();
        if (type == 0) {
            notice.setText("发送验证码至" + ((RegisterActivity) getActivity()).getMobile());
            ((RegisterActivity) getActivity()).setLeftText("上一步");
            buttonLogin.setText("下一步");
        } else if (type == 1) {
            notice.setText("发送验证码至" + ((RegisterActivity) getActivity()).getMobile());
            ((RegisterActivity) getActivity()).setLeftText("上一步");
            buttonLogin.setText("下一步");
        } else if (type == 2) {
            notice.setText("发送验证码至" + ((RegisterActivity) getActivity()).getMobile());
            ((RegisterActivity) getActivity()).setLeftText("上一步");
            buttonLogin.setText("立即绑定");
        }
    }

    public void sendCodeSuccess() {
        mobileEd.setText("");
        sendCodeBt.setTextColor(Color.parseColor("#999999"));
        count = 60;
        sendCodeBt.setText("发送短信");
        removeMeaage();
        timeMaker();
        waitingCode = true;
        setNotice();
    }


    public void setWaingtingCode(boolean b) {
        waitingCode = b;
    }

    private void sendVerCode() {
        String mobile = ((RegisterActivity) getActivity()).getMobile();
        //0注册，1找回密码，2绑定手机号码，3变更手机
        mPresenter.get_vercode(new GetVerifyCodeNetBean(mobile, null, ((RegisterActivity) getActivity()).getType()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(SendCodeSuccess info) {
        sendCodeSuccess();

    }

    @Override
    public void clickNum(String num) {
        if (TextUtils.isEmpty(num) || " ".equals(num)) {
            return;
        }
        if (mobileEd.getText().length() < 11) {
            mobileEd.append(num);
            //文本长度为6时隐藏键盘
            if (mobileEd.getText().length() >= 6) {
                // mCustomKeyView.
               // sendEmptyUiMessageDelayed(1, 200);
                keyboardview_ll.setVisibility(View.GONE);

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
}
