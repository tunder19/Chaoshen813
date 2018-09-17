package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.BetDetailActivity;
import com.example.administrator.chaoshen.activtiy.DealRecordActivity;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.BaseInfo;
import com.example.administrator.chaoshen.info.BetDeatilInfo;
import com.example.administrator.chaoshen.info.HuggBetDeatilInfo;
import com.example.administrator.chaoshen.model.BetDeatilModel;
import com.example.administrator.chaoshen.model.DealRecordModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.BetDealNetBean;
import com.example.administrator.chaoshen.net.bean.GetDealNetBean;
import com.youth.xframe.utils.log.XLog;

public class BetDeatilPresenter {
    private BetDeatilModel mModel;
    private BetDetailActivity mVew;
    private Context mContext;
    private boolean isLoading = false;


    public BetDeatilPresenter(BetDetailActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new BetDeatilModel(mContext);
    }


    public void getDealRocrod(BetDealNetBean data, String lotterType) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);

        mModel.betDetail(data, new MyCallback() {
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
                XLog.e("---------response---------" + response);
                    BetDeatilInfo info = new ResponseAnalyze<BetDeatilInfo>().analyze(response, BetDeatilInfo.class);

                    if (mModel.isNetSucceed(mVew, info)) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.getDataSuccessWinLose(info.getResults());
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
                 /*else if ("jingcai".equals(lotterType)) {
                    HuggBetDeatilInfo info = new ResponseAnalyze<HuggBetDeatilInfo>().analyze(response, HuggBetDeatilInfo.class);
                    if (mModel.isNetSucceed(mVew, info)) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.getDataSuccessJingCai(info.getResults());
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

                }*/



            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.showNetErr(response);
                        mVew.setNo_net_word();
                    }
                });
            }
        });
    }
}
