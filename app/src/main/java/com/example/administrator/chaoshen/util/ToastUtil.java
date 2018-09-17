package com.example.administrator.chaoshen.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;


/**
 * 作者：nicksong
 * 创建时间：2016/11/21
 * 功能描述:自定义toast样式、显示时长
 */

public class ToastUtil {
    private Toast mToast;
    private TextView mTextView;
    private TimeCount timeCount;
    private String message;
    private Handler mHandler = new Handler();
    private boolean canceled = true;
    private final View view;

    public ToastUtil(Context context, int layoutId, String msg) {
        message = msg;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        view = inflater.inflate(layoutId, null);
        //自定义toast文本
        RelativeLayout relativeLayout=view.findViewById(R.id.toast_father);
        relativeLayout.setAlpha(0.7f);
        mTextView = (TextView) view.findViewById(R.id.toast_msg);
        mTextView.setAlpha(0.7f);
        mTextView.setText(msg);
        Log.i("ToastUtil", "Toast start...");
        if (mToast == null) {
            mToast = new Toast(context);
            Log.i("ToastUtil", "Toast create...");
        }
        //设置toast居中显示
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(view);
    }

    public void setGravity(int gravity) {
        if (gravity == Gravity.CENTER) {
            mToast.setGravity(gravity, 0, 0);
        } else {
            mToast.setGravity(gravity, 0, 250);
        }
    }

    public View getView() {
        return view;
    }


    public static void showNormalToast(Context context, String message) {
        ToastUtil toastUtil = new ToastUtil(context.getApplicationContext(), R.layout.toast_center, message);
        toastUtil.setGravity(Gravity.BOTTOM);
        toastUtil.show(2000);
    }


    public static void showNormalToastWithTime(Context context, String message, int seconds) {
        ToastUtil toastUtil = new ToastUtil(context, R.layout.toast_center, message);
        toastUtil.setGravity(Gravity.BOTTOM);
        toastUtil.show(seconds * 1000);
    }


    public static void showSuccessToast(Context context, String message) {
        ToastUtil toastUtil = new ToastUtil(context.getApplicationContext(), R.layout.layout_toast_with_icon, message);
        toastUtil.setGravity(Gravity.CENTER);
        ImageView icon = (ImageView) toastUtil.getView().findViewById(R.id.staus_icon);
        icon.setImageResource(R.drawable.show_success);
        toastUtil.show(2000);
    }

    public static void showFialureToast(Context context, String message) {
        ToastUtil toastUtil = new ToastUtil(context.getApplicationContext(), R.layout.layout_toast_with_icon, message);
        toastUtil.setGravity(Gravity.CENTER);
        ImageView icon = (ImageView) toastUtil.getView().findViewById(R.id.staus_icon);
        icon.setImageResource(R.drawable.show_failure);
        toastUtil.show(2000);
    }


    public static void showBusyToast(Context context) {
        ToastUtil toastUtil = new ToastUtil(context.getApplicationContext(), R.layout.layout_toast_with_icon, "系统繁忙");
        toastUtil.setGravity(Gravity.CENTER);
        ImageView icon = (ImageView) toastUtil.getView().findViewById(R.id.staus_icon);
        icon.setImageResource(R.drawable.show_bussy);
        toastUtil.show(2000);
    }

    public static void showNetErrorToast(Context context) {
        ToastUtil toastUtil = new ToastUtil(context.getApplicationContext(), R.layout.layout_toast_with_icon, "网络异常");
        toastUtil.setGravity(Gravity.CENTER);
        ImageView icon = (ImageView) toastUtil.getView().findViewById(R.id.staus_icon);
        icon.setImageResource(R.drawable.show_net_error);
        toastUtil.show(2000);
    }


    /**
     * 自定义居中显示toast
     */
    public void show() {
        mToast.show();
        Log.i("ToastUtil", "Toast show...");
    }

    /**
     * 自定义时长、居中显示toast
     *
     * @param duration
     */
    public void show(int duration) {
        timeCount = new TimeCount(duration, 1000);
        Log.i("ToastUtil", "Toast show...");
        if (canceled) {
            timeCount.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏toast
     */
    private void hide() {
        if (mToast != null) {
            mToast.cancel();
        }
        canceled = true;
        Log.i("ToastUtil", "Toast that customed duration hide...");
    }

    private void showUntilCancel() {
        if (canceled) { //如果已经取消显示，就直接return
            return;
        }
        mToast.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("ToastUtil", "Toast showUntilCancel...");
                showUntilCancel();
            }
        }, Toast.LENGTH_LONG);
    }

    /**
     * 自定义计时器
     */
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //millisInFuture总计时长，countDownInterval时间间隔(一般为1000ms)
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText(message);
        }

        @Override
        public void onFinish() {
            hide();
        }
    }
}
