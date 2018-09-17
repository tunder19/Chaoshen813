package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.app.ActivityManager;
import com.example.administrator.chaoshen.bean.ActivityHomeBean;
import com.example.administrator.chaoshen.bean.ReceiverBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.util.UIHelper;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.youth.xframe.utils.log.XLog;

public class ActivityHomeDialog extends Dialog {

    private View mVContent, mLine;
    private Context mContext;
    private TextView canlce_right, canlce_bottom;
    private ImageView picture;
    private ActivityHomeBean mData;
    protected DisplayImageOptions mOpt;
    ActivityManager appmanager = ActivityManager.getInstance();


    public ActivityHomeDialog(Context context, ActivityHomeBean bean, DismissListener listener) {
        super(context, R.style.dialog);
        callListener = listener;
        mVContent = View.inflate(context, R.layout.activity_dialog_alert, null);
        setContentView(mVContent);
        mContext = context;
        mData = bean;
        //  setCanceledOnTouchOutside(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); //全屏
        // setCancelable(false);
        mContext = context;
        initImageLoader();
        initViews();
        // show();
    }

    private void initViews() {
        picture = findViewById(R.id.get_activity_im);
        canlce_right = findViewById(R.id.canlce_right);
        canlce_bottom = findViewById(R.id.canlce_bottom);
        XLog.e("-----------------run-------MyDownLoad");


        MyDownloadImageRunnable r = new MyDownloadImageRunnable(picture, mData.getPopupPicUrl(), R.drawable.dialog_default, ImageLoader.getInstance(), mOpt);
        Handler handler = new Handler();
        handler.post(r);


       // picture.post();
        XLog.e("-----------------run-------downloadImageRunnable");


        if (TextUtils.isEmpty(mData.getPopupLink())) {
            canlce_bottom.setVisibility(View.VISIBLE);
        } else {
            canlce_right.setVisibility(View.VISIBLE);
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent to = new Intent(mContext, WebActivity.class);
                    Bundle help = new Bundle();
                    help.putString(IntentKey.WEB_VIEW_URL, mData.getPopupLink());//url
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
                    help.putInt(IntentKey.OPEN_TYPE, 6); //大乐透
                    if (null != help) {
                        to.putExtras(help);
                    }
                    if (appmanager.currentActivity() instanceof WebActivity) {
                        appmanager.currentActivity().finish();
                    }
                    mContext.startActivity(to);
                    dismiss();
                }
            });
        }
        canlce_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        canlce_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }


    public interface DismissListener {
        public void finishCall();
    }

    DismissListener callListener;


    @Override
    public void dismiss() {
        if (callListener != null) {
            callListener.finishCall();
        }
        super.dismiss();
    }

    protected void initImageLoader() {
        mOpt = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.get_price_icon)
                .showImageOnFail(R.drawable.get_price_icon)
//                .showImageOnLoading(R.drawable.default_icon)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }


    private class MyDownLoad extends DownloadImageRunnable {

        public MyDownLoad(ImageView picImg, String oldUrl, int rid, ImageLoader imageLoader, DisplayImageOptions opt) {
            super(picImg, oldUrl, rid, imageLoader, opt);
        }

        @Override
        public void onComplete() {
            super.onComplete();
            XLog.e("---------------------onComplete()----");
            show();
        }

        @Override
        public void onCompleteOrFial() {
            super.onCompleteOrFial();
            XLog.e("--show-------------------onComplete()----");
            show();
        }
    }


    public class MyDownloadImageRunnable implements Runnable {
        private ImageView picImg;
        private LinearLayout picLine;
        private String oldUrl;
        private int imageRid;//占位图片资源id(为0表示不需要占位符)
        private ImageLoader mImageLoader;
        private DisplayImageOptions mOpt;

        public MyDownloadImageRunnable(ImageView picImg, String oldUrl, int rid,
                                       ImageLoader imageLoader, DisplayImageOptions opt) {
            this.picImg = picImg;
            this.oldUrl = oldUrl;
            this.imageRid = rid;
            this.mImageLoader = imageLoader;
            this.mOpt = opt;
        }


        @Override
        public void run() {
            XLog.e("-----------------run----111---");
            String thumbnailUrl = UIHelper.getImageThumbnailUrl(oldUrl, 10, 10);
            mImageLoader.displayImage(thumbnailUrl, picImg, mOpt, new MyDownloadImageRunnable.ThumbnailImageLoaderLister());
        }

        private void setDefaultPicture(View view) {
            if (imageRid > 0) {
                if (view instanceof RoundedImageView) {
                    ((RoundedImageView) view).setImageResource(imageRid);
                } else if (view instanceof ImageView) {
                    ((ImageView) view).setImageResource(imageRid);
                } else if (view instanceof View) {
                    view.setBackgroundResource(imageRid);
                }
            }
        }

        private class ThumbnailImageLoaderLister implements ImageLoadingListener {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                setDefaultPicture(view);
                onStart();
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                setDefaultPicture(view);
                String compressUrl = UIHelper.getImageCompressUrl(oldUrl, picImg.getWidth(), picImg.getHeight(), 1.0f);
                mImageLoader.displayImage(compressUrl, picImg, mOpt, new MyDownloadImageRunnable.LargeImageLoaderLister());
                onCompleteOrFial();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//            final String compressUrl = UIHelper.getImageCompressUrl(oldUrl, picImg.getWidth(), picImg.getHeight(), 1.0f);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mImageLoader.displayImage(compressUrl, picImg, mOpt, new LargeImageLoaderLister());
//                }
//            },3000);
                String compressUrl = UIHelper.getImageCompressUrl(oldUrl, picImg.getWidth(), picImg.getHeight(), 1.0f);
                mImageLoader.displayImage(compressUrl, picImg, mOpt, new MyDownloadImageRunnable.LargeImageLoaderLister());
                onCompleteOrFial();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        }

        private class LargeImageLoaderLister implements ImageLoadingListener {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                setDefaultPicture(view);
                onComplete();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage == null) {
                    setDefaultPicture(view);
                }
                onComplete();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                onComplete();
            }
        }


        public void onStart() {
        }

        ;

        public void onCompleteOrFial() {
            show();
        }

        ;

        public void onComplete() {
            show();
        }

        ;

    }


}
