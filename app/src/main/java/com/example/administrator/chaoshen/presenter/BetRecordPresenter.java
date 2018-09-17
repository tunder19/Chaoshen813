package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.BetFragment1;
import com.example.administrator.chaoshen.activtiy.CashActivity;
import com.example.administrator.chaoshen.bean.BankCardsBean;
import com.example.administrator.chaoshen.bean.BetRecordBean;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.BetRecordInfo;
import com.example.administrator.chaoshen.model.BetRocordModel;
import com.example.administrator.chaoshen.model.CashModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.GetBetsNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class BetRecordPresenter {
    private BetRocordModel mModel;
    private BetFragment1 mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data=false;


    public BetRecordPresenter(BetFragment1 mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new BetRocordModel(mContext);
    }

    public void getBets(GetBetsNetBean data, int formHead) { //type=0 等于刷新头部  1等于加载更多
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
        mModel.get_bets(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.stopLoading();
                         //   mVew.hideLoadding();
                        }
                    });
                }
            }

            @Override
            public void onSuccess(String response) {
                BetRecordInfo info = new ResponseAnalyze<BetRecordInfo>().analyze(response, BetRecordInfo.class);
                if (mModel.isNetSucceed(mVew, info))
                    if (mVew.isAdded()) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                XLog.e(info.getResults().size()+"----info.getResults().size()-------------"+formHead);
                                if (info.getResults().size() == 0) {

                                    if (formHead == 0) {
                                        mVew.setNo_data();
                                    } else {
                                        mVew.showMsg("没有更多数据");
                                        no_data=true;
                                    }
                                    return;
                                }
                                mVew.getDataSuccess((List<BetRecordBean>) info.getResults(), formHead);
                            }
                        });
                    }
            }

            @Override
            public void onFailureNo200(String response) {
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(response);
                            mVew.setNo_net();

                        }
                    });
                }
            }
        });
    }
}
