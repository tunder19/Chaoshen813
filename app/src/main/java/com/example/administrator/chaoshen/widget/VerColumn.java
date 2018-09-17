package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.chaoshen.R;
import com.youth.xframe.utils.log.XLog;


public class VerColumn extends RelativeLayout {
    private RelativeLayout head_view;
    private LinearLayout zhu;
    private CountDownTimer mTimer;
    private LayoutParams params;
    private float mMaxHeight;
    private long start;
    private long animaltionSecond = 1 * 1000; //动画一秒
    private boolean showAnimation = true;
    private View headView;

    public VerColumn(Context context) {
        super(context);
    }

    public VerColumn(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_ver, this);
        zhu = (LinearLayout) findViewById(R.id.zhu);
        head_view = (RelativeLayout) findViewById(R.id.head_view);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.VerColumn);

        final int count = a.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.VerColumn_v_maxHigh) {
                mMaxHeight = a.getDimension(attr, 0);
            }
        }
        a.recycle();
    }

    public VerColumn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void measureView(double high, String color, View headView) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) zhu.getLayoutParams();
        params.width = getWidth();
        params.height = 0;
        zhu.setLayoutParams(params);
        zhu.setBackgroundColor(Color.parseColor(color));
        if (headView != null) {
            head_view.removeAllViews();
            head_view.addView(headView);
        }
        if (high == 0) {
            return;
        }
        if (showAnimation) {
            startAnmin(params, high);
        } else {
            params.height = (int) high;
            zhu.setLayoutParams(params);
        }
    }


    public void setShowAnimation(boolean showAnimation) {
        this.showAnimation = showAnimation;
    }

    public float getBarMaxHigh() {
        return mMaxHeight;
    }


    public void startAnmin(RelativeLayout.LayoutParams params, double high) {


        double sencondHigh = (high / animaltionSecond);//每一毫秒的高度

        mTimer = new CountDownTimer(animaltionSecond, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                params.height = (int) ((animaltionSecond - millisUntilFinished) * sencondHigh);
                zhu.setLayoutParams(params);
            }

            @Override
            public void onFinish() {
            }
        };
        mTimer.start();


    }


}

