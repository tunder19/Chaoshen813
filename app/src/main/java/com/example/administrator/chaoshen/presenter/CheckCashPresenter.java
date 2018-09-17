package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.CashActivity;
import com.example.administrator.chaoshen.activtiy.CheckCashActivity;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.CheckCashInfo;
import com.example.administrator.chaoshen.model.CashModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.youth.xframe.utils.log.XLog;

public class CheckCashPresenter {
    private CashModel mModel;
    private CheckCashActivity mVew;
    private Context mContext;
    private boolean isLoading = false;


    public CheckCashPresenter(CheckCashActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new CashModel(mContext);
    }

    public void checkMode() {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.getCashModeSettingList(new TokenNetBean(""), new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("-------------response------------------" + response);
                CheckCashInfo info = new ResponseAnalyze<CheckCashInfo>().analyze(response, CheckCashInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.setData(info.getResults());
                        }
                    });

                } else {
                    if (info != null) {
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
