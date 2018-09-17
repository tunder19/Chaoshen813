package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyNewGridView extends GridView {
    public boolean isOnMeasure;
    public MyNewGridView(Context context) {
        super(context);
    }

    public MyNewGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MyNewGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        isOnMeasure = true;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }

}
