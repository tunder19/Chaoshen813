package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.BetDetailActivity;
import com.example.administrator.chaoshen.activtiy.MessageCenterActivity;
import com.example.administrator.chaoshen.info.BetDeatilInfo;
import com.example.administrator.chaoshen.info.MessageCenterInfo;
import com.example.administrator.chaoshen.model.BetDeatilModel;
import com.example.administrator.chaoshen.model.MessageCenterModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.BetDealNetBean;
import com.example.administrator.chaoshen.net.bean.GetDealNetBean;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;

public class MessageCenterPresenter {
    private MessageCenterModel mModel;
    private MessageCenterActivity mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data=false;


    public MessageCenterPresenter(MessageCenterActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel=new MessageCenterModel(mContext);
    }


    public void getMessages(GetDealNetBean data, int formHead){
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
        if (isLoading)return;
        isLoading=true;
      //  mVew.showLoadding(null);

        mModel.getMessages(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading=false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // mVew.hideLoadding();
                        mVew.stopLoading();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("-------------response-------"+response);
                MessageCenterInfo info = new ResponseAnalyze<MessageCenterInfo>().analyze(response, MessageCenterInfo.class);

                if (mModel.isNetSucceed(mVew,info)){
                   mVew.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           if (info.getResults().size()==0){
                               if (formHead == 0) {
                                   mVew.setNo_data();
                               } else {
                                   mVew.showMsg("没有更多数据");
                                   no_data=true;
                               }
                               return;
                           }
                           mVew.getData(info.getResults(),formHead);

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
}
