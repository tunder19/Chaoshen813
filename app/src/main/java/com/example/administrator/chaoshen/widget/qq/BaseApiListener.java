package com.example.administrator.chaoshen.widget.qq;

import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

public class BaseApiListener implements IRequestListener {
    @Override
    public void onComplete(JSONObject jsonObject) {

    }

    @Override
    public void onIOException(IOException e) {

    }

    @Override
    public void onMalformedURLException(MalformedURLException e) {

    }

    @Override
    public void onJSONException(JSONException e) {

    }

    @Override
    public void onConnectTimeoutException(ConnectTimeoutException e) {

    }

    @Override
    public void onSocketTimeoutException(SocketTimeoutException e) {

    }

    @Override
    public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {
        // 当前网络不可用时触发此异常

    }

    @Override
    public void onHttpStatusException(HttpUtils.HttpStatusException e) {
        // http请求返回码非200时触发此异常

    }

    @Override
    public void onUnknowException(Exception e) {
        // 出现未知错误时会触发此异常

    }
}
