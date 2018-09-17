package com.example.administrator.chaoshen.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.MessageDataBean;
import com.example.administrator.chaoshen.bean.Notice;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/28.
 */
public class MarqueeTextView extends LinearLayout {

    private Context mContext;
    private ViewFlipper viewFlipper;
    private View marqueeTextView;
    private String[] textArrays;
    private MarqueeTextViewClickListener marqueeTextViewClickListener;

    public MarqueeTextView(Context context) {
        super(context);
        mContext = context;
        initBasicView();
    }


    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initBasicView();
    }

    public void setTextArraysAndClickListener(List<MessageDataBean.InformationBroadcastListBean> notice, ImageLoader imageLoader, DisplayImageOptions mOpt,
                                              MarqueeTextViewClickListener marqueeTextViewClickListener) {
        this.textArrays = textArrays;
        this.marqueeTextViewClickListener = marqueeTextViewClickListener;
        initMarqueeTextView(notice, imageLoader, mOpt, marqueeTextViewClickListener);
    }

    public void initBasicView() {
        marqueeTextView = LayoutInflater.from(mContext).inflate(R.layout.marquee_textview_layout, null);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(marqueeTextView, layoutParams);
        viewFlipper = (ViewFlipper) marqueeTextView.findViewById(R.id.viewFlipper);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
        viewFlipper.startFlipping();
    }

    public void initMarqueeTextView(List<MessageDataBean.InformationBroadcastListBean> notice, ImageLoader imageLoader, DisplayImageOptions mOpt, MarqueeTextViewClickListener marqueeTextViewClickListener) {

        if (notice==null||notice.size() == 0) {
            return;
        }

        int i = 0;
        viewFlipper.removeAllViews();
        while (i < notice.size()) {
            View notice1 = View.inflate(getContext(), R.layout.layout_notice, null);
            TextView textView = notice1.findViewById(R.id.text_notice);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setText(notice.get(i).getContent()+"");
            ImageView horn = notice1.findViewById(R.id.horn_icon);
            // horn.post()
           /* if (!TextUtils.isEmpty(notice.get(i).getImage())) {
                horn.post(new DownloadImageRunnable(horn, notice.get(i).getImage()(), R.drawable.horn_icon, imageLoader, mOpt));
            }else {
                horn.setImageResource(R.drawable.horn_icon);
            }*/

            horn.setImageResource(R.drawable.horn_icon);
            // TextView textView = new TextView(mContext);
            //  textView.setText(textArrays[i]);
            if (marqueeTextViewClickListener != null) {
                textView.setOnClickListener(marqueeTextViewClickListener);
            }
          //  LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            viewFlipper.addView(notice1);
            i++;
        }
    }

    public void releaseResources() {
        if (marqueeTextView != null) {
            if (viewFlipper != null) {
                viewFlipper.stopFlipping();
                viewFlipper.removeAllViews();
                viewFlipper = null;
            }
            marqueeTextView = null;
        }
    }

}