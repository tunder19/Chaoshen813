package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.DealRecordActivity;
import com.example.administrator.chaoshen.activtiy.GetHeadListActivity;
import com.example.administrator.chaoshen.bean.DealDeatilBean;
import com.example.administrator.chaoshen.bean.DealDetilBean;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.DealDeatilInfo;
import com.example.administrator.chaoshen.info.GetHeadListInfo;
import com.example.administrator.chaoshen.model.DealRecordModel;
import com.example.administrator.chaoshen.model.GetHeadListModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;
import com.example.administrator.chaoshen.net.bean.GetBetsNetBean;
import com.example.administrator.chaoshen.net.bean.GetDealNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class DealRecordPresenter {
    private DealRecordModel mModel;
    private DealRecordActivity mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data=false;

    public DealRecordPresenter(DealRecordActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new DealRecordModel(mContext);
    }


    public void getDealRocrod(GetDealNetBean data, int formHead) {  //0就刷新 1就加载更多
        if (formHead==0){
            data.setSize(20);
            no_data=false;
        }else {
            data.setSize(10);
            if (no_data){
                mVew.stopLoading();
                return;
            }
        }
        if (isLoading) return;
        isLoading = true;
      //  mVew.showLoadding(null);


        mModel.get_transactions(data, new MyCallback() {

            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     //   mVew.hideLoadding();
                        mVew.stopLoading();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                DealDeatilInfo info = new ResponseAnalyze<DealDeatilInfo>().analyze(response, DealDeatilInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {

                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info.getResults().size() == 0) {
                                if (formHead == 0) {
                                    mVew.setNo_data();
                                } else {
                                    mVew.showMsg("没有更多数据");
                                    no_data=true;
                                }
                                return;
                            }
                            mVew.getData(info.getResults(), formHead);
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
                XLog.e(response + "-------info---------" + info);
                /*if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.getData(info,formHead);
                        }
                    });
                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                        }
                    });
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
