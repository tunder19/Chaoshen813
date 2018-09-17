package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class NoticeModel extends BaseNetModel{
    private Context mContext;

    public NoticeModel(Context context){
        mContext=context;
    }


    //公告
    public  void  getNoticeList(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getNoticeList(mContext, data,  callback);
    }

    //活动
    public  void  getActivityList(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getActivityList(mContext, data,  callback);
    }
}
