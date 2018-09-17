package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.CashActivity;
import com.example.administrator.chaoshen.activtiy.CashAliActivity;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.bean.BankCardsBean;
import com.example.administrator.chaoshen.bean.BankCardsFBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.UserInfo;
import com.example.administrator.chaoshen.model.CashModel;
import com.example.administrator.chaoshen.model.LoginModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.ApplyCashNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class CashPresenter {
    private CashModel mModel;
    private CashActivity mVew;
    private Context mContext;
    private boolean isLoading = false;
    private CashAliActivity mVew2;


    public CashPresenter(CashActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new CashModel(mContext);
    }

    public CashPresenter(CashAliActivity mVew, Context mContext) {
        this.mVew2 = mVew;
        this.mContext = mContext;
        mModel = new CashModel(mContext);
    }

    //获取绑定银行卡信息
    public void get_bind_card(TokenNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.get_bind_card(data, new MyCallback() {
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
                XLog.e("---------response-------" + response);
                BankCardsInfo info = new ResponseAnalyze<BankCardsInfo>().analyze(response, BankCardsInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    List<BankCardsBean> data = info.getResults();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showDefaultCard(data);
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




    public void apply_money(ApplyCashNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.apply_cash(data, new MyCallback() {
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
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  mVew.showMsg(info.getHead().getErrorMsg());
                            ToastUtil.showSuccessToast(mContext,info.getHead().getErrorMsg());
                            EventBus.getDefault().post(new RefreshDataBean());
                            mVew.finish();
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





    public void apply_money2(ApplyCashNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew2.showLoadding(null);
        mModel.apply_cash(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew2.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew2.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew2, info)) {
                    mVew2.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew2.showMsg("提现成功");

                            EventBus.getDefault().post(new RefreshDataBean());
                            mVew2.finish();
                        }
                    });
                } else {
                    mVew2.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew2.showNetErr(info.getHead().getErrorMsg());
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew2.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew2.showNetErr(response);
                    }
                });
            }
        });
    }


}
