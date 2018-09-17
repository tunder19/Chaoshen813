package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class SettingModel extends BaseNetModel{
    private Context mContext;

    public SettingModel(Context context){
        mContext=context;
    }


    //获取银行列表
    public  void  userLogout(BaseNetBean data, Callback callback){
        ApiOkHttpClient.userLogout(mContext, data,  callback);
    }
    //检查更新
    public void checkUpdate(BaseNetBean data, Callback callback){
        ApiOkHttpClient.checkUpdate(mContext, data,  callback);
    }


}
