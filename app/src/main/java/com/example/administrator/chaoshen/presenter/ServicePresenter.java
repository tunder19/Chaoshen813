package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.chaoshen.Fragment.ServiceFragment;
import com.example.administrator.chaoshen.bean.CheckRedBean;
import com.example.administrator.chaoshen.bean.CheckVersionBean;
import com.example.administrator.chaoshen.bean.LotteryBean;
import com.example.administrator.chaoshen.bean.RechargeLimitBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.ActivityHomeInf;
import com.example.administrator.chaoshen.info.BannerInfo;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.CheckRedInfo;
import com.example.administrator.chaoshen.info.CheckVersionInfo;
import com.example.administrator.chaoshen.info.LotteryInfo;
import com.example.administrator.chaoshen.info.MessageDataInfo;
import com.example.administrator.chaoshen.info.RechargeLimitInfo;
import com.example.administrator.chaoshen.model.ServiceModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.AdvertListNetBean;
import com.example.administrator.chaoshen.net.bean.GetDealNetBean;
import com.example.administrator.chaoshen.net.bean.LotteryCountNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicePresenter {
    private ServiceModel mModel;
    private ServiceFragment mVew;
    private Context mContext;
    private boolean isLoading = false;


    public ServicePresenter(ServiceFragment mVew, Context mContext) {
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

               if (mModel.isNetSucceed(mVew,info)){
                   mVew.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           if (info != null) {
                               mVew.intiBanber(info.getResults());
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


    public void checkRedPacket(TokenNetBean data) {
        mModel.checkRedPacket(data, new MyCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(String response) {
                CheckRedInfo info = new ResponseAnalyze<CheckRedInfo>().analyze(response, CheckRedInfo.class);
               /* CheckRedBean c1=new CheckRedBean();
                c1.setId("122s222");c1.setNum(3);c1.setType(1);
                CheckRedBean c2=new CheckRedBean();
                c2.setId("2222s22");c2.setNum(2);c2.setType(2);
                List list=new ArrayList();
                list.add(c1);list.add(c2);
                info.setResults(list);*/
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (info != null) {
                            mVew.showRedDialog(info.getResults());
                        }

                    }
                });


            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }


    public void get_lotterys() {
        if (isLoading) return;
        isLoading = true;
        mModel.get_lotterys(new TokenNetBean(""), new MyCallback() {
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
                LotteryInfo info = new ResponseAnalyze<LotteryInfo>().analyze(response, LotteryInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {

                    List<LotteryBean> data = info.getResults();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.setLotterys(data);
                        }
                    });

                } else {
                    if (info == null || info.getHead() == null) return;
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                            mVew.setNoNetForLottery();
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
                        mVew.setNoNetForLottery();
                    }
                });
            }
        });
    }


    /**
     * 信息播报
     */

    public void getMessageAv() {

        mModel.getMessageAv(new GetDealNetBean("", 1), new MyCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                MessageDataInfo info = new ResponseAnalyze<MessageDataInfo>().analyze(response, MessageDataInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.initNotice(info.getResults().getInformationBroadcastList());
                        }
                    });

                }


            }

            @Override
            public void onFailureNo200(String response) {

            }
        });


    }


    public void checkUpdate() {
        mModel.checkUpdate(new TokenNetBean(""), new MyCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("----------response-------" + response);
                CheckVersionInfo info = new ResponseAnalyze<CheckVersionInfo>().analyze(response, CheckVersionInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            CheckVersionBean.ClientInfoBean data = info.getResults().getClientInfo();

                            mVew.updateApp(data);
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }

    public void popupActivity() {
        mModel.popupActivity(new TokenNetBean(""), new MyCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("--popupActivity----------response-----"+response);
                ActivityHomeInf info = new ResponseAnalyze<ActivityHomeInf>().analyze(response, ActivityHomeInf.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showActivtiyDialog(info.getResults());
                        }
                    });
                }


            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }


    public void checkRechageMoney() {
        mModel.checkRechageMoney(new TokenNetBean(""), new MyCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("----------response----rechage--------"+response);
                RechargeLimitInfo info = new ResponseAnalyze<RechargeLimitInfo>().analyze(response, RechargeLimitInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    RechargeLimitBean data=   info.getResults();
                    if (data.getModel()!=null){
                        mVew.mCache.put(IntentKey.MIN_RECHARGE_LIMIT,data.getModel().getRechargeMinimumAmount()+"");
                    }
                }


            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }


    public void getLotteryCount( LotteryCountNetBean data) {
        mModel.countDown(data, new MyCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (info!=null&&info.getHead()!=null&&"1".equals(info.getHead().getCode())){

                    JsonObject jsonObject = (JsonObject) new JsonParser().parse(response);
                    JsonObject mapData=jsonObject.get("data").getAsJsonObject();

                    if (TextUtils.isEmpty(mapData.toString()))return;
                    Gson gson=new Gson();
                    Map<String , Double> map = gson.fromJson(mapData,Map.class);
                    for(Map.Entry<String, Double> vo : map.entrySet()){
                        vo.setValue(vo.getValue()/1000);
                    }
                   // DaiJia.useGson("");

                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           /* int seconds=info.getResults().getHubei11select5()/1000;
                            XLog.e("-----------------------请求秒数="+seconds);*/

                            mVew.setDataForTime(map);
                        }
                    });

                }




            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }


}
