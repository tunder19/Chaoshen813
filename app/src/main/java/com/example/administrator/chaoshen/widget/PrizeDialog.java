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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BetDetailActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.app.ActivityManager;
import com.example.administrator.chaoshen.bean.ReceiverBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.Urls;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.youth.xframe.utils.log.XLog;

public class PrizeDialog extends Dialog {

    private View mVContent, mLine;
    private Context mContext;
    private TextView get_price_title, get_price_money;
    private ImageView picture;
    private Button get_price_close, get_price_share;
    private ReceiverBean mData;
    protected DisplayImageOptions mOpt;
    ActivityManager appmanager=ActivityManager.getInstance();


    public PrizeDialog(Context context, ReceiverBean bean,DismissListener listener) {
        super(context, R.style.dialog);
        XLog.e(appmanager.getCount()+"------------弹窗="+appmanager.currentActivity());
        callListener=listener;
        mVContent = View.inflate(context, R.layout.getprice_dialog_alert, null);
        setContentView(mVContent);
        mContext = context;
        mData = bean;
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); //全屏
        setCancelable(false);
        initImageLoader();
        initViews();
        show();
    }

    private void initViews() {
        picture = findViewById(R.id.get_price_im);
        get_price_title = findViewById(R.id.get_price_title);
        get_price_money = findViewById(R.id.get_price_money);
        get_price_close = findViewById(R.id.get_price_close);
        get_price_share = findViewById(R.id.get_price_share);
        picture.post(new DownloadImageRunnable(picture, mData.getContent(), R.drawable.get_price_icon, ImageLoader.getInstance(), mOpt));
        get_price_title.setText(mData.getTitle());
        get_price_money.setText(Html.fromHtml("<small>中奖</small>" + mData.getBonus() + "元"));

        get_price_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        get_price_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("winlose".equals(mData.getLotteryType()) || "jingcai".equals(mData.getLotteryType())) {
                    Intent to = new Intent(mContext, BetDetailActivity.class);
                    Bundle data = new Bundle();
                    data.putLong(IntentKey.ORDER_ID, mData.getOrderId());
                    data.putString(IntentKey.LOTTERY_RULE, "");
                    data.putString(IntentKey.LOTTERY_TYPE, mData.getLotteryType());
                    if (appmanager.currentActivity() instanceof BetDetailActivity){
                        appmanager.currentActivity().finish();
                    }
                    to.putExtras(data);
                    mContext.startActivity(to);
                    // toActivity(BetDetailActivity.class, data);

                }else {
                    if (!TextUtils.isEmpty(mData.getLink())) {
                        Intent to = new Intent(mContext, WebActivity.class);
                        Bundle help = new Bundle();
                        help.putString(IntentKey.WEB_VIEW_URL, mData.getLink());//url
                        help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                        // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
                        help.putInt(IntentKey.OPEN_TYPE, 2); //大乐透
                        if (null != help) {
                            to.putExtras(help);
                        }
                        if (appmanager.currentActivity() instanceof WebActivity){
                            appmanager.currentActivity().finish();
                        }
                        mContext.startActivity(to);
                    }
                }











               /* if ("hubei11c5".equals(mData.getLotteryType()) || "daletou".equals(mData.getLotteryType())) {
                    if (!TextUtils.isEmpty(mData.getLink())) {
                        Intent to = new Intent(mContext, WebActivity.class);
                        Bundle help = new Bundle();
                        help.putString(IntentKey.WEB_VIEW_URL, mData.getLink());//url
                        help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                        // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
                        help.putInt(IntentKey.OPEN_TYPE, 5); //大乐透
                        if (null != help) {
                            to.putExtras(help);
                        }
                        if (appmanager.currentActivity() instanceof WebActivity){
                            appmanager.currentActivity().finish();
                        }
                        mContext.startActivity(to);
                    }

                } else {
                    if ("winlose".equals(mData.getLotteryType()) || "jingcai".equals(mData.getLotteryType())) {
                        Intent to = new Intent(mContext, BetDetailActivity.class);
                        Bundle data = new Bundle();
                        data.putLong(IntentKey.ORDER_ID, mData.getOrderId());
                        data.putString(IntentKey.LOTTERY_RULE, "");
                        data.putString(IntentKey.LOTTERY_TYPE, mData.getLotteryType());
                        if (appmanager.currentActivity() instanceof BetDetailActivity){
                            appmanager.currentActivity().finish();
                        }
                        to.putExtras(data);
                        mContext.startActivity(to);
                        // toActivity(BetDetailActivity.class, data);

                    }
                }*/
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
