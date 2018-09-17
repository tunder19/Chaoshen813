package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class BindModel extends BaseNetModel{
    private Context mContext;

    public BindModel(Context context){
        mContext=context;
    }


    //绑定银行卡
    public  void  get_addbank(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_addbank(mContext, data,  callback);
    }


    //获取银行名字
    public  void  get_BankName(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_BankName(mContext, data,  callback);
    }

    public void cancelReq(){
        ApiOkHttpClient.cancelAllReq();
    }
}
