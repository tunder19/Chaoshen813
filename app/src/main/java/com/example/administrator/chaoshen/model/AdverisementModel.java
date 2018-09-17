package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

public class AdverisementModel extends BaseModel {
    public AdverisementModel(Context context){
        mContext=context;
    }


    //获取用户投注记录
    public  void  get_bets(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_bets(mContext, data,  callback);
    }
}
