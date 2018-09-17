package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class DealRecordModel extends BaseNetModel{
    private Context mContext;

    public DealRecordModel(Context context){
        mContext=context;
    }





    //获取交易记录
    public  void  get_transactions(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_transactions(mContext, data,  callback);
    }

}
