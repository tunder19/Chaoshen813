package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class MessageCenterModel extends BaseNetModel{
    private Context mContext;

    public MessageCenterModel(Context context){
        super(context);
        mContext=context;
    }





    //获取我的消息
    public  void  getMessages(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getMessages(mContext, data,  callback);
    }

}
