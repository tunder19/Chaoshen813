package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class WinLoseModel extends BaseNetModel{
    private Context mContext;

    public WinLoseModel(Context context){
        mContext=context;
    }


    //获取历史收益
    public  void  getWinLoseLottery(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getWinLose(mContext,data,  callback);
    }

}
