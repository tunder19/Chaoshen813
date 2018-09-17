package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class RefreshListView extends ListView {
    public RefreshListView(Context context) {
        super(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //判断是否滑动到顶部了
        if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() == 0) {//到顶部了
            //返回触摸事件
            getParent().requestDisallowInterceptTouchEvent(false);
        } else {//没有到头部
            //拦截触摸事件
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev);
    }
}
