package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class BetRocordModel extends BaseNetModel{
    private Context mContext;

    public BetRocordModel(Context context){
        mContext=context;
    }


    //获取用户投注记录
    public  void  get_bets(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_bets(mContext, data,  callback);
    }

}
