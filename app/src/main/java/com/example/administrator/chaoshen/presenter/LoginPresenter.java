package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.administrator.chaoshen.Fragment.Any9Fragment;
import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.activtiy.AdvertisementActivity;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.activtiy.RegisterActivity;
import com.example.administrator.chaoshen.bean.LoginSuccess;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.UserInfo;
import com.example.administrator.chaoshen.model.LoginModel;
import com.example.administrator.chaoshen.model.WinLoseModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.LoginThirdNetBean;
import com.example.administrator.chaoshen.net.bean.UserLoginNetBean;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.youth.xframe.cache.XCache;

import java.util.Arrays;

import de.greenrobot.event.EventBus;

public class LoginPresenter  {
    public static final String TO_BIND_PHONE ="128" ;
    private LoginModel mModel;
    private LoginActivity mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean loginingThird=false;

    public LoginPresenter(LoginActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel=new LoginModel(mContext);
    }


    public void userLogin(UserLoginNetBean data){
        if (isLoading)return;

        mVew.showLoading15(null);
        isLoading=true;
        mModel.userlogin(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading=false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                UserInfo info = new ResponseAnalyze<UserInfo>().analyze(response, UserInfo.class);
                if (mModel.isNetSucceed(mVew,info)) {
                    XCache mCache = XCache.get(mContext);
                    mCache.put(IntentKey.USER, (UserBean) info.getResults());
                    EventBus.getDefault().post(new RefreshDataBean());
                    EventBus.getDefault().post(new LoginSuccess());
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showSuccessToast(mContext,info.getHead().getErrorMsg());
                            mVew.getmCache().put(IntentKey.LOGIN_MOBILE,data.getMobile());
                            mVew.finish();
                        }
                    });

                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                           // Toast.makeText(mContext, info.getHead().getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       mVew.showNetErr(response+"");
                    }
                });
            }
        });

    }


    public  void loginInThird(LoginThirdNetBean data){
        if (loginingThird)return;
        loginingThird=true;
        mVew.showLoading15(null);

        mModel.userloginInThird(data, new MyCallback() {
            @Override
            public void onFinish() {
                loginingThird=false;
                mVew.hideLoadding();
            }

            @Override
            public void onSuccess(String response) {
                UserInfo info = new ResponseAnalyze<UserInfo>().analyze(response, UserInfo.class);
                if (TO_BIND_PHONE.equals(info.getHead().getCode())){
                    //要绑定手机号
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data=new Bundle();
                            data.putInt(IntentKey.REGISTER_TYPE,2);
                            data.putLong(IntentKey.THIRD_ID,info.getResults().getThirdId());
                            mVew.toActivity(RegisterActivity.class,data);
                        }
                    });
                }else if (mModel.isNetSucceed(mVew, info)){
                    XCache mCache = XCache.get(mContext);
                    mCache.put(IntentKey.USER, (UserBean) info.getResults());
                    EventBus.getDefault().post(new RefreshDataBean());
                    EventBus.getDefault().post(new LoginSuccess());
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showSuccessToast(mContext,info.getHead().getErrorMsg());
                            mVew.finish();
                        }
                    });
                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                        }
                    });
                }
            }

                @Override
                public void onFailureNo200 (String response){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(response);
                        }
                    });
                }

        });
    }
}
