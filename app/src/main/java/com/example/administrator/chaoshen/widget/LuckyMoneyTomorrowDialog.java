package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.LuckyMoneyActivity;
import com.example.administrator.chaoshen.app.ActivityManager;
import com.example.administrator.chaoshen.bean.CheckRedBean;
import com.example.administrator.chaoshen.bean.ReceiverBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.youth.xframe.cache.XCache;

public class LuckyMoneyTomorrowDialog extends Dialog {
    private View mVContent;
    private Context mContext;
    private TextView how_much_red;
    private ImageView iamge_ikonw;
    private CheckRedBean mData;
    protected DisplayImageOptions mOpt;
    ActivityManager appmanager = ActivityManager.getInstance();


    public LuckyMoneyTomorrowDialog(Context context, CheckRedBean bean, DismissListener listener) {
        super(context, R.style.dialog);
        callListener = listener;
        mVContent = View.inflate(context, R.layout.lucky_today_dialog, null);
        setContentView(mVContent);
        mContext = context;
        mData = bean;
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); //全屏
        setCancelable(false);
        mContext = context;
        initImageLoader();
        initViews();
        show();
    }

    private void initViews() {
        how_much_red = findViewById(R.id.how_much_red);
        iamge_ikonw = findViewById(R.id.iamge_ikonw);
        how_much_red.setText(Html.fromHtml("明天您将有<font><big>" + mData.getNum() + "</big></font>个红包生效啦"));
        iamge_ikonw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent to = new Intent(mContext, LuckyMoneyActivity.class);
                Bundle help = new Bundle();
                //   help.putString(IntentKey.WEB_VIEW_URL, mData.getLink());//url
                help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
                help.putInt(IntentKey.OPEN_TYPE, 6); //大乐透
                if (null != help) {
                    to.putExtras(help);
                }
                if (appmanager.currentActivity() instanceof LuckyMoneyActivity) {
                    appmanager.currentActivity().finish();
                }
                dismiss();
                mContext.startActivity(to);

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


}
