package com.example.administrator.chaoshen.majia.model;

import android.content.Context;

import com.example.administrator.chaoshen.model.BaseModel;
import com.example.administrator.chaoshen.model.BaseNetModel;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

public class M_MineFragmentModel extends BaseNetModel {
    private Context mContext;

    public M_MineFragmentModel(Context context){
        mContext=context;
    }


    //竞彩足球即时比分
    public  void  getJinCaiMatchesScore(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getJinCaiMatchesScore(mContext, data,  callback);

    }
}
