package com.example.administrator.chaoshen.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Change on 2015/6/9.
 */
public interface BaseView {
    public String getStringFromResoure(int res);
    public String getString(int res, Object... obj);
    public void showMsg(String message);
    public void showLoadding(String message);
    public void hideLoadding();
    public void finish();
    public Context getContext();
    public Activity getActivity();
    public void showNetErr(String message);
    public void toActivity(Class clazz, Bundle data);
    public void toActivityForResult(Class clazz, Bundle data, int requestCode);
    public void startActivityForResult(Intent intent, int requestCode);
    public void runOnUiThread(Runnable runnable);
}
