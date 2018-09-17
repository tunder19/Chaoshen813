package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class KaijiangModel extends BaseNetModel{
    private Context mContext;

    public KaijiangModel(Context context){
        mContext=context;
    }


    //
    public  void  getArticleClassify(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getArticleClassify(mContext, data,  callback);
    }



    public  void  getArticleList(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getArticleList(mContext, data,  callback);
    }


    public  void  getPrizePush(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getPrizePush(mContext, data,  callback);
    }


    public void getMaJiaAdv(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getArticleAdvertList(mContext, data,  callback);
    }
}
