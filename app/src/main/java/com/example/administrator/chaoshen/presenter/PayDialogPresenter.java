package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.BetWinLoseFragment;
import com.example.administrator.chaoshen.Fragment.WinLoseShopingFragment;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.RechargeActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.CheckRechargeInfo;
import com.example.administrator.chaoshen.info.RechargeH5Info;
import com.example.administrator.chaoshen.model.RechargeModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.CheckRechargeNetBean;
import com.example.administrator.chaoshen.net.bean.RechargeMoneyNetBean;
import com.youth.xframe.utils.log.XLog;

import de.greenrobot.event.EventBus;

public class PayDialogPresenter {

    private RechargeModel mModel;
    private BaseActivity mActivty;
    private Context mContext;
    private boolean isLoading = false;
    private boolean loadMore = false;
    private int loadTime = 0;
    private double money;
    private BaseFragment mFragment;

    public PayDialogPresenter(BaseActivity mVew, Context mContext) {
        this.mActivty = mVew;
        this.mContext = mContext;
        mModel = new RechargeModel(mContext);
    }

    public PayDialogPresenter(BaseFragment mVew, Context mContext) {
        this.mFragment = mVew;
        this.mContext = mContext;
        mModel = new RechargeModel(mContext);
    }


    public void recharge_money(RechargeMoneyNetBean bean) {
        if (isLoading) return;
        isLoading = true;
        if (mActivty != null) {
            mActivty.showLoadding(null);
        } else {
            mFragment.showLoadding(null);
        }

        mModel.rechargeMoney(bean, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                if (mActivty != null) {
                    mActivty.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivty.hideLoadding();
                        }
                    });
                } else {
                    mFragment.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mFragment.hideLoadding();
                        }
                    });
                }

            }

            @Override
            public void onSuccess(String response) {
                RechargeH5Info info = new ResponseAnalyze<RechargeH5Info>().analyze(response, RechargeH5Info.class);
                if (mActivty != null) {
                    if (mModel.isNetSucceed(mActivty, info)) {
                        mActivty.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                money = bean.getMoney();
                                if (mActivty instanceof WebActivity) {
                                    ((WebActivity) mActivty).showH5(info.getResults());
                                }
                            }
                        });
                    } else {
                        mActivty.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivty.showNetErr(null);
                            }
                        });
                    }
                } else {
                    if (mModel.isNetSucceed(mFragment, info)) {
                        mFragment.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                money = bean.getMoney();
                                if (mFragment instanceof WinLoseShopingFragment) {
                                    ((WinLoseShopingFragment) mFragment).showH5(info.getResults());
                                }else if (mFragment instanceof BetWinLoseFragment){
                                    ((BetWinLoseFragment) mFragment).showH5(info.getResults());
                                }
                            }
                        });
                    } else {
                        mFragment.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mFragment.showNetErr(null);
                            }
                        });
                    }
                }


            }

            @Override
            public void onFailureNo200(String response) {
                if (mActivty != null) {
                    mActivty.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(response)) {
                                mActivty.showNetErr(response);
                            } else {
                                mActivty.showNetErr(null);
                            }

                        }
                    });
                } else {
                    mFragment.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(response)) {
                                mFragment.showNetErr(response);
                            } else {
                                mFragment.showNetErr(null);
                            }

                        }
                    });
                }

            }
        });
    }

   /* public void checkRechargeStatus(CheckRechargeNetBean ben) {


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
                                UserBean user = mVew.getUserCache();
                                user.setCurrency(user.getCurrency() + money);
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
                        mVew.showMsg("网络错误" + response);
                        mVew.hideLoadding();
                    }
                });
            }
        });
    }*/


}
