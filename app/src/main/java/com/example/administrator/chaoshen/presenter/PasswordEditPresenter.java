package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.RegisterFragemnt0;
import com.example.administrator.chaoshen.Fragment.RegisterFragemnt1;
import com.example.administrator.chaoshen.activtiy.PasswordEditActivity;
import com.example.administrator.chaoshen.activtiy.UserActivity;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.UserInfo;
import com.example.administrator.chaoshen.model.RegistModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.ChangePasswordNetBean;
import com.example.administrator.chaoshen.net.bean.GetVerifyCodeNetBean;
import com.example.administrator.chaoshen.net.bean.RegistNetBean;
import com.youth.xframe.cache.XCache;

import de.greenrobot.event.EventBus;

public class PasswordEditPresenter {

    private RegistModel mModel;
    private PasswordEditActivity mVew;
    private Context mContext;
    private boolean getvercode = false;

    public PasswordEditPresenter(PasswordEditActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new RegistModel(mContext);
    }


    public void get_vercode(GetVerifyCodeNetBean data) {
        if (getvercode) return;
        mVew.showLoadding(null);
        getvercode = true;
        mModel.get_verifycode(data, new MyCallback() {
            @Override
            public void onFinish() {
                getvercode = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {


                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.startTime();
                            mVew. showPassWord();
                        }
                    });
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                        }
                    });

                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.showNetErr(response);
                    }
                });
            }
        });

    }


    public void editPass(ChangePasswordNetBean data) {
        if (getvercode) return;
        mVew.showLoadding(null);
        getvercode = true;

        mModel.editPassword(data, new MyCallback() {
            @Override
            public void onFinish() {
                getvercode=false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg("修改成功");
                            Intent data=new Intent();
                            mVew.setResult(UserActivity.REQUESTCODE,data);
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
            public void onFailureNo200(String response) {
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
