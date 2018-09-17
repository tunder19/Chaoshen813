package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class LotteryCountModel extends BaseNetModel{
    private Context mContext;

    public LotteryCountModel(Context context){
        mContext=context;
    }





    //高频彩倒计时
    public  void  countDown(BaseNetBean data, Callback callback){
        ApiOkHttpClient.countDown(mContext, data,  callback);
    }

}
