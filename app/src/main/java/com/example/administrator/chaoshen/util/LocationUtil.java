package com.example.administrator.chaoshen.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.administrator.chaoshen.bean.GetLocationBean;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.MyCallback;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.utils.permission.XPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class LocationUtil {


    private static Context mContext;
    private static LocationUtil mInstance;
    public static final String TAG = "LocationUtil";
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                XLog.e("-------showLocation-------11----");
                startApp2();
            }
        }
    };

    private LocationUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static LocationUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LocationUtil.class) {
                if (mInstance == null) {
                    mInstance = new LocationUtil(context);
                    mContext = context;

                }
            }
            initLocation();
        }
        return mInstance;
    }

    static LocationManager locationManager;
    static Location location;
    static String provider;

    public static void initLocation() {

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        /*if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            provider = LocationManager.GPS_PROVIDER;
        } else */
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
           /* Toast.LENGTH_SHORT).show();
            LogUtil.d(TAG,"No location provider to use");*/
            XLog.e("-------showLocation---------2--");
            mHandler.sendEmptyMessageDelayed(1, 10 * 1000);
            return;
        }
        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        location = locationManager.getLastKnownLocation(provider);
    /*if (location != null) {
        // 显示当前设备的位置信息
        showLocation(location);
    }*/
        locationManager.requestLocationUpdates(provider, 60 * 1000, 100, locationListener); //60秒 100米才变换一次
        mHandler.sendEmptyMessageDelayed(1, 10 * 1000);
        XLog.e("-------showLocation-----------");
    }

    /**
     * 获取位置信息
     *
     * @return
     */
    public static Map<String, String> getLocation() {
        return showLocation(location);
    }


    public static Map<String, String> showLocation(Location location) {
        if (location == null) return null;
        Map<String, String> map = new HashMap<>();
        map.put("longitude", String.valueOf(location.getLongitude()));
        map.put("latitude", String.valueOf(location.getLatitude()));
        return map;
    }

    public static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            //   showLocation(location);
            ApiOkHttpClient.setmLocation(location);
            //  EventBus.getDefault().post(new GetLocationBean());
            startApp2();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void remove() {
        if (locationManager != null) {
            // 关闭程序时将监听器移除
            locationManager.removeUpdates(locationListener);
        }
    }

    private static void startApp2() {
        mHandler.removeMessages(1);
        ApiOkHttpClient.testJiekou2(mContext, new MyCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailureNo200(String response) {

            }


            @Override
            public void onFinish() {

            }
        });

    }

}
