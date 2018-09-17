package com.example.administrator.chaoshen.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.Fragment.BetWinLoseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.app.MyApplication;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.example.administrator.chaoshen.util.Util;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.utils.permission.XPermission;
import com.youth.xframe.widget.XToast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class SharePopupWindow extends PopupWindow implements View.OnClickListener {
    private View popupView, showView;
    private LinearLayout share_wechat, share_cricle, share_qq;
    private TextView cancel;
    private Context mContext;
    private MyApplication mApplicaiton;
    private BetWinLoseFragment mview;
    private int THUMB_SIZE = 150;
    private int shareWhat = 0;//0分享图片  1分享url
    private int res_scaleImage;
    private String url, title, desctiption;
    private File file;
    private String picName = "qq_share";
    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";
    private String path;
    private Activity activity;
    private String iconUrl;
    private WXMediaMessage msg;
    private SendMessageToWX.Req req;
    private ImageView icon;
    private View codeView;
    private static Bitmap iconBitmap;
    private WebActivity mWebView;
    private boolean webSharePicture = false;
    private WXMediaMessage msg1;
    private int delayMillis=200;

    public SharePopupWindow(BetWinLoseFragment view, MyApplication mApplicaiton, View headFataher, View codeView) {
        super(view.getContext());
        mContext = view.getContext();
        mview = view;
        shareWhat = 0;
        webSharePicture = false;
        this.codeView = codeView;
        this.mApplicaiton = mApplicaiton;
        popupView = View.inflate(mContext, R.layout.share_popup_window, null);
        setContentView(popupView);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        setBackgroundDrawable(null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        showView = mview.getShareView();
        // 设置点击popupwindow外屏幕其它地方消失
        setOutsideTouchable(true);
        initView();
        /*if (Build.VERSION.SDK_INT < 24) {
            showAsDropDown(headFataher);
        } else {*/
        showAtLocation(headFataher, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public SharePopupWindow(WebActivity view, MyApplication mApplicaiton, View headFataher) {
        super(view.getContext());
        mContext = view.getContext();
        mWebView = view;
        shareWhat = 0;
        webSharePicture = true;
        this.mApplicaiton = mApplicaiton;
        popupView = View.inflate(mContext, R.layout.share_popup_window, null);

        setContentView(popupView);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        setBackgroundDrawable(null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        // showView = mview.getShareView();
        // 设置点击popupwindow外屏幕其它地方消失
        setOutsideTouchable(true);
        initView();
      //  msg1 = getWxMediaMessage();

        /*if (Build.VERSION.SDK_INT < 24) {
            showAsDropDown(headFataher);
        } else {*/

        showAtLocation(headFataher, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    public SharePopupWindow(Activity activity, Context context, MyApplication mApplicaiton, View headFataher, int res_scaleImage,
                            String url, String title, String desctiption, String imageUrl, ImageView imageView) {
        super(context);
        mContext = context;
        this.res_scaleImage = res_scaleImage;
        this.url = url;
        icon = imageView;
        this.title = title;
        this.desctiption = desctiption;
        this.mApplicaiton = mApplicaiton;
        iconUrl = imageUrl;
        this.activity = activity;
        shareWhat = 1;
        popupView = View.inflate(mContext, R.layout.share_popup_window, null);
        setContentView(popupView);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setClippingEnabled(false);
        setFocusable(true);
        setBackgroundDrawable(null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置点击popupwindow外屏幕其它地方消失
        setOutsideTouchable(true);
        initView();
       /* if (Build.VERSION.SDK_INT < 24) {
            showAsDropDown(headFataher);
        } else {*/

        showAtLocation(headFataher, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void initView() {
        share_wechat = popupView.findViewById(R.id.share_wechat);
        share_cricle = popupView.findViewById(R.id.share_cricle);
        share_qq = popupView.findViewById(R.id.share_qq);
        cancel = popupView.findViewById(R.id.cancel);

        share_wechat.setOnClickListener(this);
        share_cricle.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_wechat:
                if (!isWeixinAvilible(mContext)){
                    ToastUtil.showNormalToast(mContext,"请先安装微信客户端");
                    return;
                }
                if (shareWhat == 0) {
                    if (webSharePicture) {
                       // mWebView.toggleShotScreen();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //do event
                                shareInitWeb(SendMessageToWX.Req.WXSceneSession);
                            }
                        }, 200);

                    } else {
                        shareInit(SendMessageToWX.Req.WXSceneSession);
                    }
                } else if (shareWhat == 1) {
                    shareUrl(SendMessageToWX.Req.WXSceneSession);
                }

                break;
            case R.id.share_cricle:
                if (!isWeixinAvilible(mContext)){
                    ToastUtil.showNormalToast(mContext,"请先安装微信客户端");
                    return;
                }
                if (shareWhat == 0) {
                    if (webSharePicture) {
                       // mWebView.toggleShotScreen();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //do event
                                shareInitWeb(SendMessageToWX.Req.WXSceneTimeline);
                            }
                        }, 300);
                    } else {
                        shareInit(SendMessageToWX.Req.WXSceneTimeline);
                    }

                } else if (shareWhat == 1) {
                    shareUrl(SendMessageToWX.Req.WXSceneTimeline);
                }
                break;
            case R.id.share_qq:
                if (!isQQClientAvailable(mContext)){
                    ToastUtil.showNormalToast(mContext,"请先安装QQ客户端");
                    return;
                }
                if (shareWhat == 0) {
                    final Bitmap[] bmp = new Bitmap[1];
                    if (webSharePicture) {
                        //mWebView.toggleShotScreen();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //do event
                                showView=mWebView.getWebView();
                                bmp[0] = loadBitmapFromView(showView);
                            }
                        }, 300);


                    } else {
                        showView = mview.getShareView();
                         bmp[0] = loadBitmapFromView(showView);
                    }

                    //  Bitmap bmp =   loadView(showView);

                    XPermission.requestPermissions(mContext, 100, new String[]{Manifest.permission
                            .WRITE_EXTERNAL_STORAGE, Manifest.permission
                            .READ_EXTERNAL_STORAGE}, new XPermission.OnPermissionListener() {
                        //权限申请成功时调用
                        @Override
                        public void onPermissionGranted() {
                            XLog.e("---onPermissionGranted---------");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //do event
                                    path = saveBitmap(mContext, bmp[0]);
                                   // mWebView.toggleShotScreen();
                                    onClickShareToQQ();
                                }
                            }, 300);

                        }

                        //权限被用户禁止时调用
                        @Override
                        public void onPermissionDenied() {
                            XLog.e("---onPermissionGranted-----onPermissionDenied----");
                            //给出友好提示，并且提示启动当前应用设置页面打开权限
                            //   XPermission.showTipsDialog(XPermissionDemoActivity.this);
                            XToast.normal("QQ分享需要权限");
                            toSelfSetting(mContext);
                        }
                    });


                } else if (shareWhat == 1) {
                    onClickShareToQQUrl();
                }

                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        if (mWebView!=null){
            mWebView.toggleShotScreen();
        }
        super.dismiss();

    }

    private void shareInit(int type) {
        showView = mview.getShareView();

        // Bitmap bmp =   loadView(showView);
        Bitmap bmp = loadBitmapFromView(showView);
        WXImageObject imageObject = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        mview.shareRecycle();
        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = type;   //SendMessageToWX.Req.WXSceneSession;   SendMessageToWX.Req.WXSceneTimeline;    朋友圈的
        mApplicaiton.mWxApi.sendReq(req);
        dismiss();
    }

    /**
     * Webview分享图片
     */
    private void shareInitWeb(int type) {
       // mWebView.toggleShotScreen();
        msg1= getWxMediaMessage();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg1;
        req.scene = type;   //SendMessageToWX.Req.WXSceneSession;   SendMessageToWX.Req.WXSceneTimeline;    朋友圈的
        mApplicaiton.mWxApi.sendReq(req);
        dismiss();
       // mWebView.toggleShotScreen();

    }


    @NonNull
    private WXMediaMessage getWxMediaMessage() {


        showView = mWebView.getWebView();

        // Bitmap bmp =   loadView(showView);
        Bitmap bmp = loadBitmapFromView(showView);
        WXImageObject imageObject = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();


        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);
        return msg;
    }


    public void shareUrl(int type) {
       /* Bitmap bmp = loadBitmapFromView(icon);
        if (bmp == null) {
            bmp = BitmapFactory.decodeResource(mContext.getResources(), res_scaleImage);
        }*/
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desctiption;
        //Bitmap bmp;
        //iconUrl="";
        if (!TextUtils.isEmpty(iconUrl)) {
            getBitmap(type, iconUrl);
            //  bmp = Bitmap.createScaledBitmap(getBitmap(iconUrl), 200, 200, true);
        } else {
            // bmp = BitmapFactory.decodeResource(mContext.getResources(), res_scaleImage);
            useLocal(type);
        }


    }

    public void useLocal(int type) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), res_scaleImage);
        weixinShareUrl(type, bitmap);
    }


    private void weixinShareUrl(int type, Bitmap bmp) {
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        XLog.e("-------------iconUrl---------" + iconUrl);
        // msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        msg.thumbData = bmpToByteArray(thumbBmp, 32);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = type;
        mApplicaiton.mWxApi.sendReq(req);
        //dismiss();
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);


    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                onInit();
            }
            super.handleMessage(msg);
        }
    };

    private void onInit() {
        dismiss();
    }


    public void getBitmap(int type, String imageUrl2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .build();
                iconBitmap = ImageLoader.getInstance().loadImageSync(imageUrl2, options);
                weixinShareUrl(type, iconBitmap);
            }
        }).start();

    }


    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param maxkb
     * @return
     */
    public static byte[] bmpToByteArray(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }


    public Bitmap loadView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }



   /* //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
    private void layoutView(View v, int width, int height) {

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        View view = LayoutInflater.from(this).inflate(R.layout.view_photo, null, false);
        layoutView(view, width, height);//去到指定view大小的函数




        // 指定整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        */

    /**
     * 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
     * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
     *//*
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }
*/
    private void onClickShareToQQ() {
        Bundle shareParams = new Bundle();
        shareParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        shareParams.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
        shareParams.putString(QQShare.SHARE_TO_QQ_APP_NAME, mApplicaiton.getResources().getString(R.string.app_name));
        shareParams.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        doShareToQQ(shareParams);
        if (mview != null) {
            mview.shareRecycle();
        }
        dismiss();
    }

    private void onClickShareToQQUrl() {
        Bundle shareParams = new Bundle();
        shareParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        shareParams.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        shareParams.putString(QQShare.SHARE_TO_QQ_APP_NAME, mApplicaiton.getResources().getString(R.string.app_name));
        shareParams.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        shareParams.putString(QQShare.SHARE_TO_QQ_SUMMARY, desctiption);// 摘要
        shareParams.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, iconUrl);
        shareParams.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        doShareToQQ(shareParams);
        if (mview != null) {
            mview.shareRecycle();
        }
        dismiss();
    }


    private void doShareToQQ(Bundle params) {
        if (mview != null) {
            mApplicaiton.mTencent.shareToQQ(mview.getActivity(), params, new BaseUiListener() {
                protected void doComplete(JSONObject values) {
                    XLog.e("分享成功");

                }

                @Override
                public void onError(UiError e) {
                    XLog.e("分享失败");
                }

                @Override
                public void onCancel() {
                    XLog.e("取消分享");
                }
            });
        } else if (activity!=null){
            mApplicaiton.mTencent.shareToQQ(activity, params, new BaseUiListener() {
                protected void doComplete(JSONObject values) {
                    XLog.e("分享成功");
                }

                @Override
                public void onError(UiError e) {
                    XLog.e("分享失败");
                }

                @Override
                public void onCancel() {
                    XLog.e("取消分享");
                }
            });
        }else if (mWebView!=null){
            mApplicaiton.mTencent.shareToQQ(mWebView, params, new BaseUiListener() {
                protected void doComplete(JSONObject values) {
                    XLog.e("分享成功");
                }

                @Override
                public void onError(UiError e) {
                    XLog.e("分享失败");
                }

                @Override
                public void onCancel() {
                    XLog.e("取消分享");
                }
            });
        }
    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onCancel() {
            XLog.e("取消分享");
        }

        @Override
        public void onComplete(Object arg0) {
            XLog.e("分享成功");

        }

        @Override
        public void onError(UiError arg0) {
            XLog.e("分享失败");

        }

    }


    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }


    public static void toSelfSetting(Context context) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }










    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }





    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


}
