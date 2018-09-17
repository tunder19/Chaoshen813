package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class WinLoseShopModel extends BaseNetModel{
    private Context mContext;

    public WinLoseShopModel(Context context){
        mContext=context;
    }


   /* public  void  winlose_bet(BaseNetBean data, TextHttpResponseHandler responseHandler){
        ApiRequestClient.winlose_bet(mContext,data,responseHandler);
    }*/

    public  void  winlose_bet(BaseNetBean data, Callback callback){
        ApiOkHttpClient.winlose_bet(mContext,data,callback);
    }

    //支付订单
    public  void  pay_order(BaseNetBean data, Callback callback){
        ApiOkHttpClient.pay_order(mContext,data,callback);
    }


    //购物车可用红包明细
    public  void  get_ordertRedPackets(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_ordertRedPackets(mContext, data,  callback);
    }


    //取消订单
    public  void  delOrder(BaseNetBean data, Callback callback){
        ApiOkHttpClient.delOrder(mContext, data,  callback);
    }


    //获取彩种投注金额限制
    public  void  lotteryLimit(BaseNetBean data, Callback callback){
        ApiOkHttpClient.lotteryLimit(mContext, data,  callback);
    }

    //充值查询
    public  void  checkRecharge(BaseNetBean data, Callback callback){
        ApiOkHttpClient.checkRecharge(mContext, data,  callback);
    }

    //充值
    public  void  rechargeMoney(BaseNetBean data, Callback callback){
        ApiOkHttpClient.rechargeMoney(mContext, data,  callback);
    }

}
