package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.LuckyMoneyFragment;
import com.example.administrator.chaoshen.info.LuckyMoneyInfo;
import com.example.administrator.chaoshen.model.LuckyMoneyModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.LuckyMoneyNetBean;
import com.youth.xframe.utils.log.XLog;

public class LuckyMoneyPresenter {
    private LuckyMoneyModel mModel;
    private LuckyMoneyFragment mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data=false;


    public LuckyMoneyPresenter(LuckyMoneyFragment mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new LuckyMoneyModel(mContext);
    }


    public void get_luckymoney(LuckyMoneyNetBean data, int formHead) {  //0就刷新 1就加载更多
        if (formHead==0){
            data.setSize(20);
            no_data =false;
        }else {
            data.setSize(10);
            if (no_data){
                mVew.stopLoading();
                return;
            }
        }
        if (isLoading) return;
        isLoading = true;
       // mVew.showLoadding(null);

        mModel.get_luckymoney(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                        mVew.stopLoading();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("--------response----" + response);
                LuckyMoneyInfo info = new ResponseAnalyze<LuckyMoneyInfo>().analyze(response, LuckyMoneyInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {

                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info.getResults().getReds().size() == 0) {
                                if (formHead == 0) {
                                    mVew.setNo_lucky_money();
                                } else {
                                    mVew.showMsg("没有更多数据");
                                    no_data=true;
                                }
                                return;
                            }
                            mVew.setTitle(info.getResults().getCanUseds());
                            mVew.getData(info.getResults().getReds(), formHead);
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
                        mVew.setno_net_word();
                    }
                });
            }
        });
    }
}
