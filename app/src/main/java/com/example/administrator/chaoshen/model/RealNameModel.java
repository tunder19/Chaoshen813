package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;
import com.example.administrator.chaoshen.net.bean.EditPasswordNetBean;
import com.example.administrator.chaoshen.net.bean.EditUserNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class RealNameModel extends BaseNetModel{
    private Context mContext;

    public RealNameModel(Context context){
        mContext=context;
    }


    //编辑用户信息
    public  void  edit_UserInfo(EditUserNetBean data, Callback callback){
        ApiOkHttpClient.edit_UserInfo(mContext, data,  callback);
    }

    //编辑用户信息
    public  void  test_info(TokenNetBean data, Callback callback){
        ApiOkHttpClient.test_info(mContext, data,  callback);
    }

    //修改密码
    public  void  editPassword(EditPasswordNetBean data, Callback callback){
        ApiOkHttpClient.editPassword(mContext, data,  callback);
    }


}
