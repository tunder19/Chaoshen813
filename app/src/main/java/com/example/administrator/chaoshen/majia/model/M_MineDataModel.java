package com.example.administrator.chaoshen.majia.model;

import android.content.Context;

import com.example.administrator.chaoshen.model.BaseModel;
import com.example.administrator.chaoshen.model.BaseNetModel;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

public class M_MineDataModel extends BaseNetModel {
    private Context mContext;

    public M_MineDataModel(Context context){
       super(context);
        mContext=context;
    }


    //查询足球赛事信息
    public  void  getFootBallDeatil(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getFootBallDeatil(mContext, data,  callback);

    }
}
