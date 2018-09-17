package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.BetFragment1;
import com.example.administrator.chaoshen.activtiy.TicketDeatilActivity;
import com.example.administrator.chaoshen.bean.BetRecordBean;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.BetRecordInfo;
import com.example.administrator.chaoshen.info.TickDeatilInfo;
import com.example.administrator.chaoshen.model.BetRocordModel;
import com.example.administrator.chaoshen.model.TicketModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.GetBetsNetBean;
import com.example.administrator.chaoshen.net.bean.TicketNetBean;

import java.util.List;

public class TicketPresenter {
    private TicketModel mModel;
    private TicketDeatilActivity mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data=false;


    public TicketPresenter(TicketDeatilActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new TicketModel(mContext);
    }

    public void getTickets(TicketNetBean data,boolean fromWhere) {
        if (fromWhere){
            data.setSize(20);
            no_data=false;
        }else {
            data.setSize(10);
            if (no_data){
                mVew.stopRefresh();
                return;
            }
        }
        if (isLoading) return;
        mVew.showLoadding(null);
        isLoading = true;
        mModel.get_ticket_list(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                        mVew.stopRefresh();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                TickDeatilInfo info = new ResponseAnalyze<TickDeatilInfo>().analyze(response, TickDeatilInfo.class);
                if (mModel.isNetSucceed(mVew,info)){

                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info.getResults().size()==0){
                                mVew.showMsg("没有更多数据");
                                no_data=true;
                                return;
                            }
                            mVew.getData(info.getResults(),fromWhere);
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
}
