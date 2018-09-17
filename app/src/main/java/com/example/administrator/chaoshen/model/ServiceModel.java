package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

public class ServiceModel extends BaseNetModel {
    private Context mContext;

    public ServiceModel(Context context){
        mContext=context;
    }


    //获取轮播图

    public  void  get_AdvertList(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_AdvertList(mContext, data,  callback);
    }


    //获取红包

    public  void  checkRedPacket(BaseNetBean data, Callback callback){
        ApiOkHttpClient.checkRedPacket(mContext, data,  callback);
    }




    //获取彩种列表

    public  void  get_lotterys(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getlotterys(mContext, data,  callback);
    }



    //信息播报
    public void getMessageAv(BaseNetBean data, Callback callback){//getInformationBroadcastList
        ApiOkHttpClient.getMessageAv(mContext, data,  callback);
    }

    //检查更新
    public void checkUpdate(BaseNetBean data, Callback callback){
        ApiOkHttpClient.checkUpdate(mContext, data,  callback);
    }


    //活动弹窗
    public void popupActivity(BaseNetBean data, Callback callback){
        ApiOkHttpClient.popupActivity(mContext, data,  callback);
    }



    //查询最少充值金额
    public void checkRechageMoney(BaseNetBean data, Callback callback){
        ApiOkHttpClient.checkRechageMoney(mContext, data,  callback);
    }

    //高频彩倒计时
    public  void  countDown(BaseNetBean data, Callback callback){
        ApiOkHttpClient.countDown(mContext, data,  callback);
    }


    //绑定银行卡
    public  void  queryPayChannels(BaseNetBean data, Callback callback){
        ApiOkHttpClient.queryPayChannels(mContext, data,  callback);
    }

}
