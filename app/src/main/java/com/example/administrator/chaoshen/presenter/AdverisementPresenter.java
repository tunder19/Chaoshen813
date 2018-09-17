package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.chaoshen.activtiy.AdvertisementActivity;
import com.example.administrator.chaoshen.activtiy.KeFuActivity;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.info.InformationInfo;
import com.example.administrator.chaoshen.info.ModeInfo;
import com.example.administrator.chaoshen.model.KefuModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.log.XLog;

public class AdverisementPresenter {
    private KefuModel mModel;
    private AdvertisementActivity mVew;
    private Context mContext;
    private boolean isLoading = false;
    private final XCache xCache;


    public AdverisementPresenter(AdvertisementActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new KefuModel(mContext);
        xCache = XCache.get(mContext);
    }

    public void getMode() {
        mModel.getMode(null, new MyCallback() {
            @Override
            public void onFinish() {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.openApp();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("--------------getMode=" + response);
                ModeInfo info = new ResponseAnalyze<ModeInfo>().analyze(response, ModeInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    int type = 1;//1是马甲 2是粤红
                    if (info.getResults().getModel() == 0 || info.getResults().getModel() == 1) {
                        type = 1;
                    } else {
                        type = 2;
                    }
                    if (type == 1) {
                        xCache.put(APP_Contants.CHANGE_MODE, "1");
                    } else if (type == 2) {
                        xCache.put(APP_Contants.CHANGE_MODE, "0");
                    }
                }


            }

            @Override
            public void onFailureNo200(String response) {

                if (TextUtils.isEmpty(xCache.getAsString(APP_Contants.CHANGE_MODE))) {
                    xCache.put(APP_Contants.CHANGE_MODE, "1");
                }
            }
        });
    }


}
