package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class LuckyMoneyModel extends BaseNetModel{
    private Context mContext;

    public LuckyMoneyModel(Context context){
        mContext=context;
    }





    //红包明细
    public  void  get_luckymoney(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_luckymoney(mContext, data,  callback);
    }

   /* //购物车可用红包明细
    public  void  get_ordertRedPackets(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_ordertRedPackets(mContext, data,  callback);
    }
*/
}
