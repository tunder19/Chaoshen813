package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class KefuModel extends BaseNetModel{
    private Context mContext;

    public KefuModel(Context context){
        mContext=context;
    }


    //意见反馈
    public  void  feedback(BaseNetBean data, Callback callback){
        ApiOkHttpClient.feedback(mContext, data,  callback);
    }


    //意见反馈
    public  void  getMode(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getMode(mContext, data,  callback);
    }

}
