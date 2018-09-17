package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BetDetailActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.app.ActivityManager;
import com.example.administrator.chaoshen.bean.ReceiverBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ActivityDialog extends Dialog {

    private View mVContent, mLine;
    private Context mContext;
    private TextView canlce_right, canlce_bottom;
    private ImageView picture;
    private ReceiverBean mData;
    protected DisplayImageOptions mOpt;
    ActivityManager appmanager=ActivityManager.getInstance();


    public ActivityDialog(Context context, ReceiverBean bean, DismissListener listener) {
        super(context, R.style.dialog);
        callListener=listener;
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
        show();
    }

    private void initViews() {
        picture = findViewById(R.id.get_activity_im);
        canlce_right = findViewById(R.id.canlce_right);
        canlce_bottom = findViewById(R.id.canlce_bottom);
        picture.post(new DownloadImageRunnable(picture, mData.getContent(), R.drawable.activity_im, ImageLoader.getInstance(), mOpt));
        if (TextUtils.isEmpty(mData.getLink())){
            canlce_bottom.setVisibility(View.VISIBLE);
        }else {
            canlce_right.setVisibility(View.VISIBLE);
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent to = new Intent(mContext, WebActivity.class);
                    Bundle help = new Bundle();
                    help.putString(IntentKey.WEB_VIEW_URL, mData.getLink());//url
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
                    help.putInt(IntentKey.OPEN_TYPE, 6); //大乐透
                    if (null != help) {
                        to.putExtras(help);
                    }
                    if (appmanager.currentActivity() instanceof WebActivity){
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
        callListener.finishCall();
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


}
