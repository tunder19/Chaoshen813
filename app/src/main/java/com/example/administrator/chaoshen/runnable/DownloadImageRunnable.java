package com.example.administrator.chaoshen.runnable;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.chaoshen.util.UIHelper;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.youth.xframe.utils.log.XLog;

/**
 * Created by czg on 2016/3/28.
 */
public class DownloadImageRunnable implements Runnable {
    private ImageView picImg;
    private LinearLayout picLine;
    private String oldUrl;
    private int imageRid;//占位图片资源id(为0表示不需要占位符)
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOpt;

    public DownloadImageRunnable(ImageView picImg, String oldUrl, int rid,
                                 ImageLoader imageLoader, DisplayImageOptions opt) {
        this.picImg = picImg;
        this.oldUrl = oldUrl;
        this.imageRid = rid;
        this.mImageLoader = imageLoader;
        this.mOpt = opt;
    }



    @Override
    public void run() {
        String thumbnailUrl = UIHelper.getImageThumbnailUrl(oldUrl, 10, 10);
        mImageLoader.displayImage(thumbnailUrl, picImg, mOpt, new ThumbnailImageLoaderLister());
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
            mImageLoader.displayImage(compressUrl, picImg, mOpt, new LargeImageLoaderLister());
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
            mImageLoader.displayImage(compressUrl, picImg, mOpt, new LargeImageLoaderLister());
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


    public  void onStart(){};
    public  void onCompleteOrFial(){};
    public  void onComplete(){};

}
