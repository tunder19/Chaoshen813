package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.BindCardActiivty;
import com.example.administrator.chaoshen.activtiy.KeFuActivity;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.BetDeatilInfo;
import com.example.administrator.chaoshen.info.GetBankNameInfo;
import com.example.administrator.chaoshen.model.BindModel;
import com.example.administrator.chaoshen.model.KefuModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.BindBankNetBean;
import com.example.administrator.chaoshen.net.bean.FeedBackNetBean;
import com.example.administrator.chaoshen.net.bean.GetBankNameNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.youth.xframe.utils.log.XLog;

public class KefuPresenter {
    private KefuModel mModel;
    private KeFuActivity mVew;
    private Context mContext;
    private boolean isLoading = false;


    public KefuPresenter(KeFuActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new KefuModel(mContext);
    }

    public void feedback(FeedBackNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.feedback(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading=false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
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
                            mVew.showMsg("感谢你的意见和建议");
                            mVew.finish();
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
