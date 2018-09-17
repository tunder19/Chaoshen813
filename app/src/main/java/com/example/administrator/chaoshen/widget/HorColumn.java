package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.chaoshen.R;


public class HorColumn extends RelativeLayout {
    private LinearLayout zhu;
    private CountDownTimer mTimer;
    private LayoutParams params;
    private float v_maxWidth;
    private long start;
    private long animaltionSecond = 1 * 1000; //动画一秒
    private boolean showAnimation = true;
    private View headView;

    public HorColumn(Context context) {
        super(context);
    }

    public HorColumn(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_hor, this);
        zhu = (LinearLayout) findViewById(R.id.zhu);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.HorColumn);

        final int count = a.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.HorColumn_v_maxWidth) {
                v_maxWidth = a.getDimension(attr, 0);
            }
        }
        a.recycle();
    }

    public HorColumn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void measureView(double width, String color, View headView) {
        LayoutParams params = (LayoutParams) zhu.getLayoutParams();
        params.width = 0;
        params.height = getHeight();
        zhu.setLayoutParams(params);
        zhu.setBackgroundColor(Color.parseColor(color));
        if (width == 0) {
            return;
        }
        if (showAnimation) {
            startAnmin(params, width);
        } else {
            params.width = (int) width;
            zhu.setLayoutParams(params);
        }
    }


    public void setShowAnimation(boolean showAnimation) {
        this.showAnimation = showAnimation;
    }

    public float getBarMaxWidth() {
        return v_maxWidth;
    }


    public void startAnmin(LayoutParams params, double width) {


        double sencondHigh = (width / animaltionSecond);//每一毫秒的宽度

        mTimer = new CountDownTimer(animaltionSecond, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                params.width = (int) ((animaltionSecond - millisUntilFinished) * sencondHigh);
                zhu.setLayoutParams(params);
            }

            @Override
            public void onFinish() {
            }
        };
        mTimer.start();


    }


}

