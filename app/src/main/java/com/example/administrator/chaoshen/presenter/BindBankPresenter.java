package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.BindCardActiivty;
import com.example.administrator.chaoshen.activtiy.CashActivity;
import com.example.administrator.chaoshen.bean.BankCardsBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.GetBankNameInfo;
import com.example.administrator.chaoshen.info.UserInfo;
import com.example.administrator.chaoshen.model.BindModel;
import com.example.administrator.chaoshen.model.CashModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.BindBankNetBean;
import com.example.administrator.chaoshen.net.bean.GetBankNameNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class BindBankPresenter {
    private BindModel mModel;
    private BindCardActiivty mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean isGetting = false;


    public BindBankPresenter(BindCardActiivty mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new BindModel(mContext);
    }

    public void bind_bank(BindBankNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.get_addbank(data, new MyCallback() {
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
                XLog.e("---------绑定银行卡-------" + response);
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew,info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.setResult(Constants.TO_BIND_AC);
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

    public void getBankName(GetBankNameNetBean data) {
        if (isGetting) return;
        isGetting = true;
        cancelReq();
        mModel.get_BankName(data, new MyCallback() {
            @Override
            public void onFinish() {
                isGetting = false;
            }

            @Override
            public void onSuccess(String response) {
                GetBankNameInfo info = new ResponseAnalyze<GetBankNameInfo>().analyze(response, GetBankNameInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.setBankName(info.getResults());
                        }
                    });
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.clearBankName();
                            mVew.showMsg(info.getHead().getErrorMsg());
                        }
                    });
                }

            }

            @Override
            public void onFailureNo200(String response) {
                //其他情况下
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.showNetErr(response);
                    }
                });
            }
        });
    }


    public void cancelReq() {
        mModel.cancelReq();
    }

}
