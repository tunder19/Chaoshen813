package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.view.menu.MenuView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

import com.example.administrator.chaoshen.Fragment.BetWinLoseFragment;
import com.example.administrator.chaoshen.Fragment.WinLoseFragment;
import com.example.administrator.chaoshen.Fragment.WinLoseShopingFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BuyWinLostSuccess;
import com.example.administrator.chaoshen.bean.LuckyMoneyListInfo;
import com.example.administrator.chaoshen.bean.PayOrderSuccess;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.bean.WinloseMatchesBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.CheckRechargeInfo;
import com.example.administrator.chaoshen.info.LimitInfo;
import com.example.administrator.chaoshen.info.LuckyMoneyInfo;
import com.example.administrator.chaoshen.info.RechargeH5Info;
import com.example.administrator.chaoshen.info.WinloseBetInfo;
import com.example.administrator.chaoshen.info.WinloseInfo;
import com.example.administrator.chaoshen.model.WinLoseModel;
import com.example.administrator.chaoshen.model.WinLoseShopModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.CheckRechargeNetBean;
import com.example.administrator.chaoshen.net.bean.LotteryLimitNetBean;
import com.example.administrator.chaoshen.net.bean.OrdertRedPacketsNetBean;
import com.example.administrator.chaoshen.net.bean.PayOrderNetBean;
import com.example.administrator.chaoshen.net.bean.RechargeMoneyNetBean;
import com.example.administrator.chaoshen.net.bean.WinLoseBetNetBean;
import com.example.administrator.chaoshen.net.bean.WinLoseNetBean;
import com.example.administrator.chaoshen.util.UIHelper;
import com.example.administrator.chaoshen.widget.AppAlertDialog;
import com.youth.xframe.utils.log.XLog;

import org.apache.http.Header;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Change on 2016/5/23.
 */
public class WinLoseShopPresenter {
    private WinLoseShopModel mModel;
    private WinLoseShopingFragment mVew;
    private Context mContext;
    private boolean isLoading = false;
    private WinLoseBetNetBean mData;
    private boolean isPaying = false;
    private int loadTime = 0;
    private boolean loadMore = false;
    private double money;


    public WinLoseShopPresenter(Context context, WinLoseShopingFragment activity) {
        mContext = context;
        mVew = activity;
        mModel = new WinLoseShopModel(mContext);
    }


    public void getwinlose_bet(WinLoseBetNetBean data) {
        mData = data;
        if (isLoading) return;

        isLoading = true;
        if (mVew.isAdded()) {
            mVew.showLoadding(null);
        }
        mModel.winlose_bet(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mVew.hideLoadding();
                        }
                    });
                }

            }

            @Override
            public void onSuccess(String response) {
                XLog.e(response + "--------responseString------------");
                WinloseBetInfo info = new ResponseAnalyze<WinloseBetInfo>().analyze(response, WinloseBetInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    //支付成功
                    PayOrderNetBean order = new PayOrderNetBean(mData.getToken(), info.getResults().getId());
                    if (mVew.isAdded()) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mVew.isAdded()) {
                                    mVew.moneyEnoughTopay(info.getResults());
                                    //  get_ordertRedPackets(new OrdertRedPacketsNetBean(mVew.getUserCache().getToken(),  order.getOrderId()),info);
                                    // mVew.showPopup(order,info.getResults().getAmount());
                                }
                            }
                        });
                    }
                   /* PayOrderNetBean order=new PayOrderNetBean(mData.getToken(),info.getResults().getId(),0);
                    pay_order(order);*/
                } else {
                    if (mVew.isAdded()) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mVew.isAdded()) {
                                    if (info != null) {
                                        mVew.showMsg(info.getHead().getErrorMsg() + "");
                                    } else {
                                        mVew.showNetErr(null);
                                    }
                                }

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
                            mVew.showNetErr(null);
                        }
                    });
                }
            }
        });


    }

    public void pay_order(PayOrderNetBean data) {
        XLog.e("-----------------pay_order=winlose");
        if (isPaying) return;
        isPaying = true;
        mVew.showLoadding(null);
        mModel.pay_order(data, new MyCallback() {
            @Override
            public void onFinish() {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isPaying = false;
                        mVew.hideLoadding();
                    }
                });

            }

            @Override
            public void onSuccess(String response) {
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mModel.isNetSucceed(mVew, info)) {
                            RefreshDataBean data1=    new RefreshDataBean();
                            data1.setPayOrderSuccess(true);
                            EventBus.getDefault().post(data1);
                            EventBus.getDefault().post(new BuyWinLostSuccess(data.getOrderId()));

                            mVew.finish();
                            //  mVew.showLoginTips();


                        } else {
                            if (mVew.isAdded()) {
                                if (info != null) {
                                    mVew.showMsg(info.getHead().getErrorMsg() + "");
                                } else {
                                    mVew.showNetErr(null);
                                }
                            }
                        }
                    }
                });

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


    public void get_ordertRedPackets(OrdertRedPacketsNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);

        mModel.get_ordertRedPackets(data, new MyCallback() {
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
                XLog.e("--------response----" + response);
                LuckyMoneyListInfo info = new ResponseAnalyze<LuckyMoneyListInfo>().analyze(response, LuckyMoneyListInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    //  PayOrderNetBean order = new PayOrderNetBean(mData.getToken(), minfo.getResults().getId());
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // XLog.e("--------getAmount----"+minfo.getResults().getAmount());
                            //mVew.showPopup(info.getResults());
                            mVew.payDialog(info.getResults());
                            //mVew.getData(info.getResults(),formHead);
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


    public void getLotteryLimit(LotteryLimitNetBean data) {
        if (isLoading) return;
        isLoading = false;
        mVew.showLoadding(null);
        mModel.lotteryLimit(data, new MyCallback() {
            @Override
            public void onFinish() {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                LimitInfo info = new ResponseAnalyze<LimitInfo>().analyze(response, LimitInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.setLimti(info.getResults());
                        }
                    });


                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(response);
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
                               // mVew.showMsg("充值成功");
                                mVew.hideLoadding();
                                UserBean user=mVew.getUserCache();
                                user.setCurrency( user.getCurrency()+money);
                                mVew.getmCache().put(IntentKey.USER, user);
                                EventBus.getDefault().post(new RefreshDataBean());
                                mVew.payOrder();
                            }
                        });

                    } else if (info.getResults().getStatus() == 2) {
                        //充值失败
                        loadMore = false;
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.setRechargeFailed(true);
                              //  mVew.showMsg("支付失败");
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
                                   // mVew.showMsg("暂无充值结果，请稍后查询账户余额");
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
    }


}
