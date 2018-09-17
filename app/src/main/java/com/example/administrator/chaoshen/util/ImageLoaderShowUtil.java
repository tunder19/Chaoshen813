package com.example.administrator.chaoshen.util;

import android.content.Context;
import android.widget.ImageView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageLoaderShowUtil extends ImageLoaderUtil {
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOpt;

    public ImageLoaderShowUtil(ImageLoader imageLoader, DisplayImageOptions option) {
        mImageLoader=imageLoader;
        mOpt=option;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

      //  mImageLoader.displayImage(path, imageView, mOpt, AvatorLoadingListener.getInstance());

        imageView.post(new DownloadImageRunnable(imageView, (String) path, R.drawable.lunbo_nodata, mImageLoader, mOpt));
    }
}
