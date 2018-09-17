package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.util.ScreenManager;
import com.example.administrator.chaoshen.util.SystemUtil;
import com.youth.xframe.utils.log.XLog;

/**1像素Activity
 *
 * Created by jianddongguo on 2017/7/8.
 */


public class SinglePixelActivity extends AppCompatActivity {
    private static final String TAG = "SinglePixelActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if(Contants.DEBUG)
            XLog.e(TAG,"onCreate--->启动1像素保活");
        // 获得activity的Window对象，设置其属性
        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attrParams = mWindow.getAttributes();
        attrParams.x = 0;
        attrParams.y = 0;
        attrParams.height = 1;
        attrParams.width = 1;
        mWindow.setAttributes(attrParams);
        // 绑定SinglePixelActivity到ScreenManager
        ScreenManager.getScreenManagerInstance(this).setSingleActivity(this);
    }


    @Override
    protected void onDestroy() {
        //if(Contants.DEBUG)
        XLog.e(TAG,"onDestroy--->1像素保活被终止");
        if(! SystemUtil.isProessRunning(this)){
            Intent intentAlive = new Intent(this, AdvertisementActivity.class);
            intentAlive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentAlive);
            XLog.e(TAG,"SinglePixelActivity---->APP被干掉了，我要重启它");
        }
        super.onDestroy();
    }
}
