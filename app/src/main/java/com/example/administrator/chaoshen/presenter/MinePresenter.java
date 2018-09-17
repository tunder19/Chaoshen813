package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.MineFragment;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.MessageCountInfo;
import com.example.administrator.chaoshen.info.UserInfo;
import com.example.administrator.chaoshen.model.LoginModel;
import com.example.administrator.chaoshen.model.MineModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.util.MathUtil;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.log.XLog;

public class MinePresenter {
    private MineModel mModel;
    private MineFragment mVew;
    private Context mContext;
    private boolean isLoading = false;

    public MinePresenter(MineFragment mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new MineModel(mContext);
    }

    public void getUserInfo(TokenNetBean token) {
        if (isLoading) {
            return;
        }
        isLoading = true;

        mModel.getUserInfo(token, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.stopRefreishing();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("获取用户数据---------" + response);
                UserInfo info = new ResponseAnalyze<UserInfo>().analyze(response, UserInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    XCache mCache = XCache.get(mContext);
                    mCache.put(IntentKey.USER, (UserBean) info.getResults());
                    if (mVew.isAdded()) {

                        mVew.setUsetData(info.getResults());
                        getMessageCount(token);
                    }

                } else {
                    if (mVew.isAdded()) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showMsg(info.getHead().getErrorMsg());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailureNo200(String response) {
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mVew.isAdded()) {
                                mVew.showNetErr(response);
                            }
                        }
                    });
                }
            }
        });
    }


    public void getMessageCount(TokenNetBean tokenNetBean) {
        mModel.getUnreadMsgCount(tokenNetBean, new MyCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                MessageCountInfo info = new ResponseAnalyze<MessageCountInfo>().analyze(response, MessageCountInfo.class);

                if (mModel.isNetSucceed(mVew, info)) {
                    if (info.getResults().getCount() > 0) {
                        if (mVew.isAdded()) {
                            mVew.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mVew.setMessageCount(info.getResults().getCount());
                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }

}
