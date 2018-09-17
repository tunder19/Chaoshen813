package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class CashModel extends BaseNetModel{
    private Context mContext;

    public CashModel(Context context){
        mContext=context;
    }


    //获取银行列表
    public  void  get_bind_card(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_bind_card(mContext, data,  callback);
    }


    //申请提现
    public  void  apply_cash(BaseNetBean data, Callback callback){
        ApiOkHttpClient.apply_cash(mContext, data,  callback);
    }


    //查询提现去到
    public  void  getCashModeSettingList(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getCashModeSettingList(mContext, data,  callback);
    }
}
