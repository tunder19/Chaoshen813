package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class GetHeadListModel extends BaseNetModel{
    private Context mContext;

    public GetHeadListModel(Context context){
        mContext=context;
    }





    //获取头像列表
    public  void  getUserHeadIcos(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getUserHeadIcos(mContext, data,  callback);
    }

}
