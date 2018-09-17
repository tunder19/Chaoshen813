package com.example.administrator.chaoshen.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;


public class UIHelper {
    public static final float WIDGET_SCALE = 0.6f;//图片压缩成控件长宽的比例

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * @param
     * @return
     * @throws
     * @Title: requestScreenHeight
     * @Description:
     */
    public static int requestScreenHeight(final Context ctx) {
        if (ctx == null) {
            return 0;
        }
        WindowManager manage = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manage.getDefaultDisplay();
        return display.getHeight();
    }


    /**
     * @param
     * @return
     * @throws
     * @Title: requestScreenWidth
     * @Description:
     */
    public static int requestScreenWidth(final Context ctx) {
        if (ctx == null) {
            return 0;
        }
        WindowManager manage = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manage.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * @param @param root
     * @param @param res
     * @param @param strRes
     * @return void
     * @throws
     * @Title: setText
     * @Description: 设置文字
     */
    public static void setText(View v, String str) {
        ((TextView) v).setText(str);
    }

    /**
     * @param @param root
     * @param @param res
     * @param @param text
     * @return void
     * @throws
     * @Title: setText
     * @Description: 设置文字
     */
    public static void setText(View root, int res, String text) {
        ((TextView) root.findViewById(res)).setText(text);
    }

    /**
     * @param @param v
     * @param @param colorRes
     * @return void
     * @throws
     * @Title: setBackground
     * @Description: 设置背景色
     */
    public static void setBackground(View v, int colorRes) {
        v.setBackgroundResource(colorRes);
    }

    /**
     * @param @param root
     * @param @param res
     * @param @param drawableRes
     * @return void
     * @throws
     * @Title: setImage
     * @Description: 设置图片
     */
    public static void setImage(View img, int drawableRes) {
        ((ImageView) img).setImageResource(drawableRes);
    }

    /**
     * 跳转到相应的fragment
     *
     * @param context
     * @param tag     fragment 类名
     */
    public static void toActivity(Context context, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.KEY_TAG, tag);
        toActivity(context, BaseActivity.class, bundle);
    }

    /**
     * activity跳转
     *
     * @param from
     * @param clazz
     * @param data
     */
    public static void toActivity(Context from, Class<?> clazz, Bundle data) {
        Intent to = new Intent(from, clazz);
        if (null != data) {
            to.putExtras(data);
        }
        from.startActivity(to);
    }

    public static void toActivityForResult(Activity from, Class<?> clazz, Bundle data, int requestCode) {
        Intent to = new Intent(from, clazz);
        if (null != data) {
            to.putExtras(data);
        }
        from.startActivityForResult(to, requestCode);
    }

    /**
     * @param context
     */
    public static void call(final Context context, final String tel) {
        PackageManager pm = context.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                    + tel));
            context.startActivity(intent);
        } else {
//				Toast.makeText(context, R.string.user_device_cannot_call_text,
//						Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint({"NewApi", "SetJavaScriptEnabled", "JavascriptInterface"})
    public static void loadMap(WebView wv, final Object js2java, final WebViewClient client) {
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        if (android.os.Build.VERSION.SDK_INT >= 11)
            webSettings.setDisplayZoomControls(false);
        wv.setWebViewClient(client);
        if (null != js2java) {
            wv.addJavascriptInterface(js2java, "js2java");
        }
        wv.loadUrl("file:///android_asset/map.html");
    }

    public static int REQUEST_CODE = 0x1111;

 /*   public static File doPhoto(Activity ctx) {
        String state = Environment.getExternalStorageState();
        File file = null;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String saveDir = Environment.getExternalStorageDirectory()
                    + AppConstants.PIC_TEMP_PATH;
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(saveDir, "temp" + System.currentTimeMillis() + ".jpg");
            file.delete();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx,
                            R.string.photo_file_isnull, Toast.LENGTH_LONG)
                            .show();
                    return file;
                }
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            ctx.startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(ctx,
                    R.string.common_msg_nosdcard, Toast.LENGTH_LONG).show();
        }
        return file;
    }*/


    public static final int PHOTO_REQUEST_CUT = 0x2222;

    /*
     * 剪切图片
     */
   /* public static File crop(Activity ctx, Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//	        intent.putExtra("return-data", true);

        String state = Environment.getExternalStorageState();
        File file = null;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String saveDir = Environment.getExternalStorageDirectory()
                    + AppConstants.PIC_TEMP_PATH;
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(saveDir, "temp" + System.currentTimeMillis() + ".jpg");
            file.delete();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx,
                            R.string.photo_file_isnull, Toast.LENGTH_LONG)
                            .show();
                    return file;
                }
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
            ctx.startActivityForResult(intent, PHOTO_REQUEST_CUT);
        } else {
            Toast.makeText(ctx,
                    R.string.common_msg_nosdcard, Toast.LENGTH_LONG).show();
        }
        return file;
    }
*/

   /* public static File setFilePath(Activity ctx) {
        String state = Environment.getExternalStorageState();
        File file = null;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String saveDir = Environment.getExternalStorageDirectory()
                    + AppConstants.PIC_TEMP_PATH;
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(saveDir, "temp" + System.currentTimeMillis() + ".jpg");
            file.delete();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx,
                            R.string.photo_file_isnull, Toast.LENGTH_LONG)
                            .show();
                    return file;
                }
            }
        } else {
            Toast.makeText(ctx,
                    R.string.common_msg_nosdcard, Toast.LENGTH_LONG).show();
        }
        return file;
    }
*/

    public static File saveCroppedImage(Bitmap bmp) {
        File file = new File("/sdcard/myFolder");
        if (!file.exists())
            file.mkdir();

        file = new File("/sdcard/temp.jpg".trim());
        String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));

        // /sdcard/myFolder/temp_cropped.jpg
        String newFilePath = "/sdcard/myFolder" + "/" + mName + "_cropped" + sName;
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


//    public static void sendMessage(String content, String phone) {
//        SmsManager smsManager = SmsManager.getDefault();
//        /** 切分短信，每七十个汉字切一个，不足七十就只有一个：返回的是字符串的List集合*/
//        List<String> texts = smsManager.divideMessage(content);
//        //发送之前检查短信内容是否为空
//        for (int i = 0; i < texts.size(); i++) {
//            String text = texts.get(i);
//            smsManager.sendTextMessage(phone, null, text, null, null);
//        }
//    }

    public static void sendMessage(Context context, String number, String message) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        sendIntent.putExtra("sms_body", message);
        context.startActivity(sendIntent);
    }


    public static int[] getImageWidthHeight(String imgUrl) {
        int[] result = new int[2];
        try {
            if (!TextUtils.isEmpty(imgUrl)) {
                String[] f = imgUrl.split("!");
                if (f.length == 2) {
                    String[] s = f[1].split("x");
                    if (s.length == 2) {
                        result[0] = Integer.valueOf(s[0]);
                        result[1] = Integer.valueOf(s[1].replace(".jpg", "").replace(".jpeg", "").replace(".png", ""));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据控件宽和高获取新的图片路径
     *
     * @param oldImgUrl
     * @param widgetWidth
     * @param widgetHeight
     * @return
     */
    public static String getImageCompressUrl(String oldImgUrl, int widgetWidth, int widgetHeight) {
        String compressUrl = oldImgUrl;
        try {
            int[] result = new int[2];
            if (!TextUtils.isEmpty(oldImgUrl) && widgetHeight > 0 && widgetWidth > 0) {
                String[] f = oldImgUrl.split("!");
                if (f.length == 2) {
                    String[] s = f[1].split("x");
                    if (s.length == 2) {
                        result[0] = Integer.valueOf(s[0]);
                        result[1] = Integer.valueOf(s[1].replace(".jpg", "").replace(".jpeg", "").replace(".png", ""));
                        if (result[0] > 0 && result[1] > 0) {
                            float widthScale = result[0] / (widgetWidth * WIDGET_SCALE);
                            float heightScale = result[1] / (widgetHeight * WIDGET_SCALE);
                            float compressScale = widthScale;
                            if (widthScale <= 1f || heightScale <= 1f) {
                                return compressUrl;
                            } else {
                                if (widthScale > heightScale) {
                                    compressScale = heightScale;
                                }
                                //得到新路径
                                int newWidth = (int) Math.ceil(result[0] / compressScale);
                                int newHeight = (int) Math.ceil(result[1] / compressScale);
                                String suffix = f[1].substring(f[1].lastIndexOf(".") + 1);
                                StringBuilder newUrlBuilder = new StringBuilder();
                                newUrlBuilder.append(f[0]).append("!").append(newWidth).append("x").append(newHeight).append(".").append(suffix);
                                compressUrl = newUrlBuilder.toString();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compressUrl;
    }


    /**
     * 根据控件宽和高获取新的图片路径
     *
     * @param oldImgUrl
     * @param widgetWidth
     * @param widgetHeight
     * @return
     */
    public static String getImageCompressUrl(String oldImgUrl, int widgetWidth, int widgetHeight, float scale) {
        String compressUrl = oldImgUrl;
        try {
            int[] result = new int[2];
            if (!TextUtils.isEmpty(oldImgUrl) && widgetHeight > 0 && widgetWidth > 0) {
                String[] f = oldImgUrl.split("!");
                if (f.length == 2) {
                    String[] s = f[1].split("x");
                    if (s.length == 2) {
                        result[0] = Integer.valueOf(s[0]);
                        result[1] = Integer.valueOf(s[1].replace(".jpg", "").replace(".jpeg", "").replace(".png", ""));
                        if (result[0] > 0 && result[1] > 0) {
                            float widthScale = result[0] / (widgetWidth * scale);
                            float heightScale = result[1] / (widgetHeight * scale);
                            float compressScale = widthScale;
                            if (widthScale <= 1f || heightScale <= 1f) {
                                return compressUrl;
                            } else {
                                if (widthScale > heightScale) {
                                    compressScale = heightScale;
                                }
                                //得到新路径
                                int newWidth = (int) Math.ceil(result[0] / compressScale);
                                int newHeight = (int) Math.ceil(result[1] / compressScale);
                                String suffix = f[1].substring(f[1].lastIndexOf(".") + 1);
                                StringBuilder newUrlBuilder = new StringBuilder();
                                newUrlBuilder.append(f[0]).append("!").append(newWidth).append("x").append(newHeight).append(".").append(suffix);
                                compressUrl = newUrlBuilder.toString();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compressUrl;
    }

    public static int getImageViewHeight(String oldImgUrl, int imageViewWidth) {
        int height = 0;
        try {
            int[] result = new int[2];
            if (!TextUtils.isEmpty(oldImgUrl) && imageViewWidth > 0) {
                String[] f = oldImgUrl.split("!");
                if (f.length == 2) {
                    String[] s = f[1].split("x");
                    if (s.length == 2) {
                        result[0] = Integer.valueOf(s[0]);
                        result[1] = Integer.valueOf(s[1].replace(".jpg", "").replace(".jpeg", "").replace(".png", ""));
                        if (result[0] > 0 && result[1] > 0) {
                            height = imageViewWidth * result[1] / result[0];
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public static int getStatusHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }


    public static boolean isNavigationVisible(Context context) {
        Resources resources = context.getResources();
        int rid = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean isShow = false;
        int height = 0;
        if (rid > 0) {
            isShow = resources.getBoolean(rid);
        }
        return isShow;
    }

    public static int getNavigationHeight(Context context) {
        Resources resources = context.getResources();
        int rid = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean isShow = false;
        int height = 0;
        if (rid > 0) {
            isShow = resources.getBoolean(rid);
        }
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && isShow) {
            height = resources.getDimensionPixelSize(resourceId);
        }
        return height;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        if (bm == null)
            return bm;
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
//        if (bm != returnBm) {
//            bm.recycle();
//        }
        return returnBm;
    }

    public static Bitmap getBmpFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static int getRealStatusHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (sbar <= 0) {
            sbar = dip2px(context, 25);
        }
        return sbar;
    }

    public static String getImageThumbnailUrl(String oldImgUrl, int imgWidth, int imgHeight) {
        String compressUrl = oldImgUrl;
        try {
            int[] result = new int[2];
            if (!TextUtils.isEmpty(oldImgUrl)) {
                String[] f = oldImgUrl.split("!");
                if (f.length == 2) {
                    String[] s = f[1].split("x");
                    if (s.length == 2) {
                        result[0] = Integer.valueOf(s[0]);
                        result[1] = Integer.valueOf(s[1].replace(".jpg", "").replace(".jpeg", "").replace(".png", ""));
                        if (result[0] > imgWidth || result[1] > imgHeight) {
                            //得到新路径
                            String suffix = f[1].substring(f[1].lastIndexOf(".") + 1);
                            StringBuilder newUrlBuilder = new StringBuilder();
                            newUrlBuilder.append(f[0]).append("!").append(imgWidth).append("x").append(imgHeight).append(".").append(suffix);
                            compressUrl = newUrlBuilder.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compressUrl;
    }

    /**
     * 下划线
     */
    public static void addUnderLine(TextView tvTest) {
        tvTest.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvTest.getPaint().setAntiAlias(true);//抗锯齿
    }


    /**拼接图片*/

    /**
     * 以第一个图为准
     * 优化算法  1.图片不需要铺满，只需要以统一合适的宽度。然后让imageview自己去铺满，不然长图合成长图会崩溃，这里以第一张图为例
     * 2.只缩放不相等宽度的图片。已经缩放过的不需要再次缩放
     *
     * @param bit1
     * @param bit2
     * @return
     */
    public static Bitmap newBitmap(Bitmap bit1, Bitmap bit2) {
        Bitmap newBit = null;
        int width = bit1.getWidth();
        if (bit2.getWidth() != width) {
            int h2 = bit2.getHeight() * width / bit2.getWidth();
            newBit = Bitmap.createBitmap(width, bit1.getHeight() + h2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBit);
            Bitmap newSizeBitmap2 = getNewSizeBitmap(bit2, width, h2);
            canvas.drawBitmap(bit1, 0, 0, null);
            canvas.drawBitmap(newSizeBitmap2, 0, bit1.getHeight(), null);
        } else {
            newBit = Bitmap.createBitmap(width, bit1.getHeight() + bit2.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBit);
            canvas.drawBitmap(bit1, 0, 0, null);
            canvas.drawBitmap(bit2, 0, bit1.getHeight(), null);
        }
        return newBit;
    }


    public static Bitmap getNewSizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        float scaleWidth = ((float) newWidth) / bitmap.getWidth();
        float scaleHeight = ((float) newHeight) / bitmap.getHeight();
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap bit1Scale = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
        return bit1Scale;
    }


    /**
     * 将view变bitmap
     */
    public static Bitmap getViewBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();


        return bitmap;

    }


    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Context mContext, Bitmap bmp, String picName) {

        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//通知相册更新
        MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);

        ToastUtil.showNormalToast(mContext, "图片保存成功");

    }


    public static Bitmap loadBitmapFromView(View v) {
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


    /**
     * 保存图片到图库
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "desheng");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            ToastUtil.showNormalToast(context, "保存失败");
            e.printStackTrace();
        } catch (IOException e) {
            ToastUtil.showNormalToast(context, "保存失败");
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            ToastUtil.showNormalToast(context, "保存成功");
        } catch (FileNotFoundException e) {
            ToastUtil.showNormalToast(context, "保存失败");
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
    }


    public static boolean saveImageToGallery2(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Webview长截图
     */
    public static Bitmap saveBitmapWebview(Context context, WebView mWebView) {
        mWebView.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mWebView.layout(0, 0, mWebView.getMeasuredWidth(), mWebView.getMeasuredHeight());
        mWebView.setDrawingCacheEnabled(true);
        mWebView.buildDrawingCache();
        Bitmap longImage = Bitmap.createBitmap(mWebView.getMeasuredWidth(),
                mWebView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(longImage);  // 画布的宽高和 WebView 的网页保持一致
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, mWebView.getMeasuredHeight(), paint);
        mWebView.draw(canvas);
        return longImage;
    }



    public static  Bitmap captureWebView(WebView webView,float newScale){
        float scale = newScale;
        int width = webView.getWidth();
        int height = (int) (webView.getHeight() * scale);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);
        return bitmap;
    }


    public static Bitmap viewShot(final View view){
        if (view == null)
            return null;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);

        if (view.getMeasuredWidth()<=0 || view.getMeasuredHeight()<=0) {
            L.e("ImageUtils.viewShot size error");
            return null;
        }
        Bitmap bm;
        try {
            bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        }catch (OutOfMemoryError e){
            System.gc();
            try {
                bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            }catch (OutOfMemoryError ee){
                L.e("ImageUtils.viewShot error", ee);
                return null;
            }
        }
        Canvas bigCanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigCanvas.drawBitmap(bm, 0, iHeight, paint);
        view.draw(bigCanvas);
        return bm;
    }









}
