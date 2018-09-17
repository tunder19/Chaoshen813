package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class MineModel extends BaseNetModel{
    private Context mContext;

    public MineModel(Context context){
        mContext=context;
    }


    //用户信息
    public  void  getUserInfo(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getUserInfo(mContext, data,  callback);
    }

    //获取未读消息数量
    public  void  getUnreadMsgCount(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getUnreadMsgCount(mContext, data,  callback);
    }

}
