package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;

import okhttp3.Callback;

/**
 * Created by Change on 2016/5/23.
 */
public class TicketModel extends BaseNetModel{
    private Context mContext;

    public TicketModel(Context context){
        mContext=context;
    }


    //出票明细
    public  void  get_ticket_list(BaseNetBean data, Callback callback){
        ApiOkHttpClient.get_tickets(mContext, data,  callback);
    }


}
