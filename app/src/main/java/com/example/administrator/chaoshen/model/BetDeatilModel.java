package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class BetDeatilModel extends BaseNetModel{
    private Context mContext;

    public BetDeatilModel(Context context){
        mContext=context;
    }





    //获取交易记录
    public  void  betDetail(BaseNetBean data, Callback callback){
        ApiOkHttpClient.betDetail(mContext, data,  callback);
    }

}
