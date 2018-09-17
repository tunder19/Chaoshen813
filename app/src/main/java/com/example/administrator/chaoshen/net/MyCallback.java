package com.example.administrator.chaoshen.net;


import android.telecom.Call;

import com.youth.xframe.utils.log.XLog;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public abstract class MyCallback implements Callback {
    @Override
    public void onFailure(okhttp3.Call call, IOException e) {
        XLog.e("-----------------------call=----"+e);
        if (e.toString().contains("closed")) {
            //如果是主动取消的情况下
            XLog.e("__________用户手动取消网络");
        } else {
            onFailureNo200("网络访问错误");

        }
        onFinish();
    }

    @Override
    public void onResponse(okhttp3.Call call, Response response) throws IOException {

        if (response.code() == 200) {
            //如果是200说明正常，调用MyCallBack的成功方法，传入数据
            String res = response.body().string();
            onSuccess(res);
            XLog.e("OKHTTP_Success--------Post---" + res);
        } else {
            //如果不是200说明异常，调用MyCallBack的失败方法将响应码传入
            onFailureNo200(response.code() + "");
            XLog.e("OKHTTP_Success--------Post-----" + response.code());
        }
        onFinish();


    }

    public abstract void onFinish();

    public abstract void onSuccess(String response);

    public abstract void onFailureNo200(String response);
}
