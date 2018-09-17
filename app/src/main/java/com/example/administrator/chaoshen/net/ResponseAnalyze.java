package com.example.administrator.chaoshen.net;

import android.util.Log;

import com.google.gson.Gson;
import com.youth.xframe.utils.log.XLog;

/**
 * Created by Change on 2015/4/24.
 */
public class ResponseAnalyze<T> {
    public  T analyze(String responseString,Class clazz){
       // AppLog.i(responseString + "");
        XLog.i("ResponseAnalyze",responseString+"");
        try{
            Gson gson = new Gson();
            return (T)gson.fromJson(responseString,clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public  T analyze(String responseString,Class clazz,boolean needPrintLog){
        if(needPrintLog){
            Log.i("ApiRequestClient", responseString + "");
        }
        try{
            Gson gson = new Gson();
            return (T)gson.fromJson(responseString,clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
