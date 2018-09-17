package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.CashActivity;
import com.example.administrator.chaoshen.activtiy.SettingActivity;
import com.example.administrator.chaoshen.bean.BankCardsBean;
import com.example.administrator.chaoshen.bean.CheckVersionBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.BetDeatilInfo;
import com.example.administrator.chaoshen.info.CheckVersionInfo;
import com.example.administrator.chaoshen.model.CashModel;
import com.example.administrator.chaoshen.model.SettingModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.ApplyCashNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

import de.greenrobot.event.EventBus;

public class SettingPresenter {
    private SettingModel mModel;
    private SettingActivity mVew;
    private Context mContext;
    private boolean isLoading = false;


    public SettingPresenter(SettingActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new SettingModel(mContext);
    }


    public void loginOut(TokenNetBean data){
        if (isLoading)return;
        mVew.showLoadding(null);
        isLoading=true;
        mModel.userLogout(data, new MyCallback() {
            @Override
            public void onFinish() {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                        isLoading=false;
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
                            ToastUtil.showSuccessToast(mContext,info.getHead().getErrorMsg());
                            mVew.logOut();
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
                        mVew.showMsg("退出失败");
                    }
                });
            }
        });
    }



    public void checkUpdate(){
        if (isLoading)return;
        mVew.showLoadding(null);
        isLoading=true;
        mModel.checkUpdate(new TokenNetBean(""), new MyCallback() {
            @Override
            public void onFinish() {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                        isLoading=false;
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                CheckVersionInfo info = new ResponseAnalyze<CheckVersionInfo>().analyze(response, CheckVersionInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CheckVersionBean.ClientInfoBean data=info.getResults().getClientInfo();
                            mVew.updateApp(data);
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


    public void checkUpdateFirst(){
        if (isLoading)return;
        mVew.showLoadding(null);
        isLoading=true;
        mModel.checkUpdate(new TokenNetBean(""), new MyCallback() {
            @Override
            public void onFinish() {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                        isLoading=false;
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                CheckVersionInfo info = new ResponseAnalyze<CheckVersionInfo>().analyze(response, CheckVersionInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CheckVersionBean.ClientInfoBean data=info.getResults().getClientInfo();
                            mVew.updateAppFirst(data);
                        }
                    });
                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.upadteVersionFail();
                         //   mVew.showNetErr(info.getHead().getErrorMsg());
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.upadteVersionFail();
                      //  mVew.showNetErr(response);
                    }
                });
            }
        });
    }

}
