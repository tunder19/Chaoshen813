package com.example.administrator.chaoshen.majia.model;

import android.content.Context;

import com.example.administrator.chaoshen.model.BaseNetModel;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

public class M_LeaguesModel extends BaseNetModel {
    private Context mContext;

    public M_LeaguesModel(Context context){
       super(context);
        mContext=context;
    }

    //查询足球赛事信息
    public  void  getFootBallScores(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getFootBallScores(mContext, data,  callback);

    }



    //查询联赛射手榜
    public  void  getFootBallplayers(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getFootBallplayers(mContext, data,  callback);

    }

    //查询赛程
    public  void  getFootBallMatches(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getFootBallMatches(mContext, data,  callback);

    }




}
