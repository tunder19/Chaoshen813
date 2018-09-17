package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class RechargeModel extends BaseNetModel{
    private Context mContext;

    public RechargeModel(Context context){
        mContext=context;
    }


    //绑定银行卡
    public  void  queryPayChannels(BaseNetBean data, Callback callback){
        ApiOkHttpClient.queryPayChannels(mContext, data,  callback);
    }
    //充值
    public  void  rechargeMoney(BaseNetBean data, Callback callback){
        ApiOkHttpClient.rechargeMoney(mContext, data,  callback);
    }

    //充值查询
    public  void  checkRecharge(BaseNetBean data, Callback callback){
        ApiOkHttpClient.checkRecharge(mContext, data,  callback);
    }

}
