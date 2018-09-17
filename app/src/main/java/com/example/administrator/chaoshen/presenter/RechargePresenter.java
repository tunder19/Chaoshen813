package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.text.TextUtils;

import com.example.administrator.chaoshen.Fragment.BetFragment1;
import com.example.administrator.chaoshen.activtiy.RechargeActivity;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.CheckRechargeInfo;
import com.example.administrator.chaoshen.info.RechargeH5Info;
import com.example.administrator.chaoshen.info.RechargeListInfo;
import com.example.administrator.chaoshen.model.BetRocordModel;
import com.example.administrator.chaoshen.model.RechargeModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.CheckRechargeNetBean;
import com.example.administrator.chaoshen.net.bean.QueryPayChannelsBean;
import com.example.administrator.chaoshen.net.bean.RechargeMoneyNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

import de.greenrobot.event.EventBus;

public class RechargePresenter {

    private RechargeModel mModel;
    private RechargeActivity mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean loadMore = false;
    private int loadTime = 0;
    private double money;

    public RechargePresenter(RechargeActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new RechargeModel(mContext);
    }

    public void getPayWay(QueryPayChannelsBean data) {
        if (isLoading == true) return;
        isLoading = true;
        mVew.showLoadding(null);

        mModel.queryPayChannels(data, new MyCallback() {
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
                XLog.e("-----------response-----------" + response);
                RechargeListInfo info = new ResponseAnalyze<RechargeListInfo>().analyze(response, RechargeListInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.setPayWay((List<RechargeListBean>) info.getResults());
                        }
                    });

                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info != null) {
                                mVew.showMsg(info.getHead().getErrorMsg());
                            } else {
                                mVew.showNetErr(null);
                            }
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


    public void recharge_money(RechargeMoneyNetBean bean) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.rechargeMoney(bean, new MyCallback() {
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
                RechargeH5Info info = new ResponseAnalyze<RechargeH5Info>().analyze(response, RechargeH5Info.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            money=bean.getMoney();
                            mVew.showH5(info.getResults());
                        }
                    });
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(null);
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(response)) {
                            mVew.showNetErr(response);
                        } else {
                            mVew.showNetErr(null);
                        }

                    }
                });
            }
        });
    }

    public void checkRechargeStatus(CheckRechargeNetBean ben) {
/*
        if (isLoading) return;
        isLoading = true;*/

        mModel.checkRecharge(ben, new MyCallback() {
            @Override
            public void onFinish() {
                loadTime++;
                if (!loadMore) {
                    isLoading = false;
                    loadTime = 0;
                }

            }

            @Override
            public void onSuccess(String response) {
                CheckRechargeInfo info = new ResponseAnalyze<CheckRechargeInfo>().analyze(response, CheckRechargeInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    XLog.e("--------ben------" + info.getResults().getStatus());
                    if (info.getResults().getStatus() == 1) {
                        //充值成功
                        loadMore = false;
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.setRechargeFailed(false);
                                mVew.showMsg("充值成功");
                                mVew.hideLoadding();
                                UserBean user=mVew.getUserCache();
                                user.setCurrency( user.getCurrency()+money);
                                mVew.getmCache().put(IntentKey.USER, user);
                                EventBus.getDefault().post(new RefreshDataBean());
                                mVew.finish();
                            }
                        });

                    } else if (info.getResults().getStatus() == 2) {
                        //充值失败
                        loadMore = false;
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.setRechargeFailed(true);
                                mVew.showMsg("充值失败");
                                mVew.hideLoadding();
                                EventBus.getDefault().post(new RefreshDataBean());
                            }
                        });
                    } else {
                        if (loadTime >= 4) {
                            loadTime = 0;
                            loadMore = false;
                            mVew.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mVew.setRechargeFailed(true);
                                    mVew.showMsg("暂无充值结果，请稍后查询账户余额");
                                    mVew.hideLoadding();
                                }
                            });
                        } else {
                            loadMore = true;
                            isLoading = false;
                            checkRechargeStatus(ben);
                        }
                    }
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg("" + info.getHead().getErrorMsg());
                        }
                    });
                }

            }

            @Override
            public void onFailureNo200(String response) {
                loadMore = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.showNetErr("网络错误" + response);
                        mVew.hideLoadding();
                    }
                });
            }
        });
    }
}
