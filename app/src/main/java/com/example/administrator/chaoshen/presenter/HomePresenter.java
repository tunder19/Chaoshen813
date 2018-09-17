package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.ServiceFragment;
import com.example.administrator.chaoshen.activtiy.HomeActivity;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.BannerInfo;
import com.example.administrator.chaoshen.info.RechargeListInfo;
import com.example.administrator.chaoshen.model.ServiceModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.AdvertListNetBean;
import com.example.administrator.chaoshen.net.bean.QueryPayChannelsBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class HomePresenter  {

    private ServiceModel mModel;
    private HomeActivity mVew;
    private Context mContext;
    private boolean isLoading = false;


    public HomePresenter(HomeActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new ServiceModel(mContext);
    }


    public void getAdv() {
        mModel.get_AdvertList(new AdvertListNetBean("1"), new MyCallback() {

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                BannerInfo info = new ResponseAnalyze<BannerInfo>().analyze(response, BannerInfo.class);
                XLog.e("-----getAdv--------------"+response);
                if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info != null) {
                                mVew.intiBanber(info.getResults());


                                if (mVew.getService().getIsInit()==1){
                                mVew.getService().setBannerData();
                                mVew.getService().popActivity();

                                }

                                getPayWay(new QueryPayChannelsBean());
                            }

                        }
                    });

                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.bannerNoData();
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.bannerNoData();
                    }
                });
            }
        });
    }




    public void getPayWay(QueryPayChannelsBean data) {
        XLog.e("-----------getPayWay--");

        mModel.queryPayChannels(data, new MyCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("-----------response------银行卡-----" + response);
                RechargeListInfo info = new ResponseAnalyze<RechargeListInfo>().analyze(response, RechargeListInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BannerBean data = (BannerBean) mVew.mCache.getAsObject(IntentKey.BANNER_BEAN);
                            data.setRechargeListBeans(info.getResults());
                            mVew.mCache.put(IntentKey.BANNER_BEAN,data);
                           // mVew.setPayWay((List<RechargeListBean>) info.getResults());
                        }
                    });

                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info != null) {
                                mVew.showMsg(info.getHead().getErrorMsg());
                            } else {
                                mVew.showNetErr(null);
                            }
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
