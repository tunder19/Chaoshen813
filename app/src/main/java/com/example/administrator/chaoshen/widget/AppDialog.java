package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.chaoshen.R;

/**
 * Created by Change on 2015/4/29.
 */
public class AppDialog extends Dialog {

    private Window window = null;
    private int mWindowWidth;

    public AppDialog(Context context) {
        super(context, R.style.dialog);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mWindowWidth = wm.getDefaultDisplay().getWidth();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    public void setWindowWidth(int width){
        mWindowWidth=width;
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.width = width; // 宽度
        getWindow().setAttributes(lp);
    }


 /*   public void showDialog(int x, int y,int height) {
        windowDeploy(x, y,mWindowWidth- UIHelper.dip2px(getContext(), 20f),height);
        setCanceledOnTouchOutside(true);
        show();
    }*/

    public void showDialog(int x, int y,int width,int height) {
        if(width==-2)
            width = mWindowWidth;
        windowDeploy(x, y,width,height);
        setCanceledOnTouchOutside(true);
        show();
    }

    /**
     *
     *设置窗口显示
     */
    public void windowDeploy(int x, int y,int width,int height) {
        window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
//        window.setBackgroundDrawableResource(android.R.color.transparent); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
        wl.gravity = Gravity.BOTTOM;

        wl.width = width;
        wl.height = height;
        window.setAttributes(wl);
    }
}