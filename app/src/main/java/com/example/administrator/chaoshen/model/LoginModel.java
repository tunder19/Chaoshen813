package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;
import com.example.administrator.chaoshen.net.bean.UserLoginNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class LoginModel extends BaseNetModel{
    private Context mContext;

    public LoginModel(Context context){
        mContext=context;
    }


    //登录
    public  void  userlogin(BaseNetBean data, Callback callback){
        ApiOkHttpClient.userlogin(mContext, data,  callback);
    }

    //第三方登录
    public  void  userloginInThird(BaseNetBean data, Callback callback){
        ApiOkHttpClient.userloginInThird(mContext, data,  callback);
    }

}
