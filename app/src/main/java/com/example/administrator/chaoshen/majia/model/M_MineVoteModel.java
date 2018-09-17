package com.example.administrator.chaoshen.majia.model;

import android.content.Context;

import com.example.administrator.chaoshen.model.BaseNetModel;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

public class M_MineVoteModel extends BaseNetModel {
    private Context mContext;

    public M_MineVoteModel(Context context){
       super(context);
        mContext=context;
    }


    //查询足球赛事信息
    public  void  getVotes(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getVotes(mContext, data,  callback);

    }


    //获取球队投票列表
    public  void  getTeams(BaseNetBean data, Callback callback){
        ApiOkHttpClient.getTeams(mContext, data,  callback);

    }
}
