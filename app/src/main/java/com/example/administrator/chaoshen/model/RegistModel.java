package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class RegistModel extends BaseNetModel{
    private Context mContext;

    public RegistModel(Context context){
        mContext=context;
    }


    //登录
    public  void  registUser(BaseNetBean data, Callback callback){
        ApiOkHttpClient.registUser(mContext, data,  callback);
    }

    //验证码
    public  void  get_verifycode(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_verifycode(mContext, data,  callback);
    }
    //忘记密码
    public  void  forget_password(BaseNetBean data, Callback callback){
        ApiOkHttpClient.forget_password(mContext, data,  callback);
    }

    //忘记密码
    public  void  editPassword(BaseNetBean data, Callback callback){
        ApiOkHttpClient.editPassword(mContext, data,  callback);
    }


}
