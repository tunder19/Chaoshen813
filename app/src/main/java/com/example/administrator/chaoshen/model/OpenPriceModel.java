package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class OpenPriceModel extends BaseNetModel{
    private Context mContext;

    public OpenPriceModel(Context context){
        mContext=context;
    }





    //获取开奖列表
    public  void  openPrice(BaseNetBean data, Callback callback){
        ApiOkHttpClient.openPrice(mContext, data,  callback);

    }



    //竞彩足球即时比分
    public  void  getJinCaiMatchesScore(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getJinCaiMatchesScore(mContext, data,  callback);

    }

}
