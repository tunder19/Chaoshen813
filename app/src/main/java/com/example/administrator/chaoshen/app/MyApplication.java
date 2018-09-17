package com.example.administrator.chaoshen.app;

import android.content.Context;
import android.graphics.Typeface;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.youth.xframe.XFrame;
import com.youth.xframe.base.XApplication;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends XApplication {
    private static MyApplication sInstance;
    public static IWXAPI mWxApi;
    public static String WEIXIN_APP_ID = "wx77a551d786b3f53e";
    public static Tencent mTencent;
    public static Typeface typeFace;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "717b25d4cb", true);
        XFrame.init(this);
        mTencent = Tencent.createInstance("1106958091", this.getApplicationContext());
        initLog();
        sInstance = this;
        initImageLoader(getApplicationContext()); // 初始化图片加载类
        registerToWX();
    }


    private void initLog() {
        XFrame.initXLog()//初始化XLog
                .setTag("Test")//设置全局tag
                .setDebug(true);//是否显示日志，默认true，发布时最好关闭
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        Set<String> set = new HashSet<>();
        set.add("test");//名字任意，可多添加几个
        JPushInterface.setTags(this, set, null);//设置标签
    }


    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(960, 1600)
                .diskCacheExtraOptions(960, 1600, null)
                .memoryCache(new LruMemoryCache(8 * 1024 * 1024))
                .memoryCacheSize(8 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }


    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(WEIXIN_APP_ID);

    }

}
