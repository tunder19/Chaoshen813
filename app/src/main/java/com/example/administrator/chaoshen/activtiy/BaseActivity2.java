package com.example.administrator.chaoshen.activtiy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.AppUtil;
import com.example.administrator.chaoshen.app.MyApplication;
import com.example.administrator.chaoshen.util.BaseNetView;
import com.example.administrator.chaoshen.widget.AppProgressDialog;
import com.example.administrator.chaoshen.widget.TopMiddlePopup;
import com.example.administrator.chaoshen.widget.refreshlayout.Engine;
import com.youth.xframe.base.XActivity;
import com.youth.xframe.base.XApplication;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

public abstract class BaseActivity2 extends XActivity implements View.OnClickListener, BaseNetView {
    private TextView mTitleTextView,popup_title;
    private LinearLayout mBackwardbButton;
    private Button mForwardButton;
    private FrameLayout mContentLayout;
    private LinearLayout title_top,title_top0;
    protected AppProgressDialog mProgressDialog;
    protected Handler mUiHandler;
    protected Engine mEngine;
    protected XApplication mApp;
    private RelativeLayout all_title_bar;
    protected TopMiddlePopup topPopupwindow;
    private  boolean showPopupwindow=showPopUpBar();
    private ArrayList<String> list;

    @Override
    public int getLayoutId() {
        if (AppUtil.isTransparent) {
            setTransparent(this); //设置透明方法
        }
        hideActionBar();
        super.setContentView(R.layout.activity_title);
        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
        setupViews();
        mApp = MyApplication.getInstance();
        mProgressDialog = new AppProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mUiHandler = new UIHandler(this);


        return setLayoutId();
    }





    @Override
    public void initData(Bundle savedInstanceState) {

        initDataNew(savedInstanceState);

    }

    public abstract int setLayoutId();

    public abstract void initDataNew(Bundle savedInstanceState);

    public abstract boolean showActionBar();

    public abstract boolean showPopUpBar();








    /**
     * 设置隐藏标题栏
     */
    private void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
        setRootView(activity);
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }


    //设置自定义标题栏
    private void setupViews() {

        int statusHigh = XDensityUtils.getStatusBarHeight();
        title_top = (LinearLayout) findViewById(R.id.title_top);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top.getLayoutParams();
        params.height = statusHigh;
        title_top.setLayoutParams(params);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        all_title_bar=(RelativeLayout) findViewById(R.id.all_title_bar);
        mBackwardbButton = (LinearLayout) findViewById(R.id.button_backward);
        mForwardButton = (Button) findViewById(R.id.button_forward);
        mBackwardbButton.setOnClickListener(this);
        if (showActionBar()){
            all_title_bar.setVisibility(View.VISIBLE);
        }else {
            all_title_bar.setVisibility(View.GONE);
        }

        if (showPopupwindow){
            popup_title= (TextView) findViewById(R.id.popup_title);
            popup_title.setVisibility(View.VISIBLE);
            mTitleTextView.setVisibility(View.INVISIBLE);
            popup_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    topPopupwindow.show(all_title_bar);
                }
            });
        }
    }
    /**设置pop标题*/
    public void setpopBarTitle(String a){
        popup_title.setText(a);
    }

    /**设置标题*/
    public void setActionBarTitle(String a){
        mTitleTextView.setText(a);
    }

    /**
     * 是否显示返回按钮
     *
     * @param backwardResid 文字
     * @param show          true则显示
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (mBackwardbButton != null) {
            if (show) {
                //   mBackwardbButton.setText(backwardResid);
                mBackwardbButton.setVisibility(View.VISIBLE);
            } else {
                mBackwardbButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param forwardResId 文字
     * @param show         true则显示
     */
    protected void showForwardView(int forwardResId, boolean show) {
        if (mForwardButton != null) {
            if (show) {
                mForwardButton.setVisibility(View.VISIBLE);
                mForwardButton.setText(forwardResId);
            } else {
                mForwardButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 返回按钮点击后触发
     *
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
        finish();
    }

    /**
     * 提交按钮点击后触发
     *
     * @param forwardView
     */
    protected void onForward(View forwardView) {
        Toast.makeText(this, "我是提交按钮", Toast.LENGTH_SHORT).show();
    }

    //设置标题内容
    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    //设置标题文字颜色
    @Override
    public void setTitleColor(int textColor) {
        mTitleTextView.setTextColor(textColor);
    }

    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * 按钮点击调用的方法
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_backward:
                onBackward(v);
                break;

            case R.id.button_forward:
                onForward(v);
                break;


            default:
                break;
        }
    }


    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToNext() {

    }

    /**
     * 显示加载弹框
     *
     * @param loaddingMessage
     */
    @Override
    public void showLoadding(String loaddingMessage) {
        if (null != mProgressDialog) {
//            mProgressDialog.setMessage(loaddingMessage);
        } else {
            XLog.e("mProgressDialog is null! did you super initViews?");
        }
        if (!mProgressDialog.isShowing() && getActivity() != null && !getActivity().isFinishing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoadding() {
        if (null != mProgressDialog && mProgressDialog.isShowing() && getActivity() != null) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public String getStringFromResoure(int res) {
        return getString(res);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showNetErr(String message) {
        String msg = getStringFromResoure(R.string.network_err);
        if (!TextUtils.isEmpty(message)) {
            msg = message;
        }
        showMsg(msg);
    }

    @Override
    public void toActivity(Class clazz, Bundle data) {
        Intent to = new Intent(this, clazz);
        if (null != data) {
            to.putExtras(data);
        }
        startActivity(to);
    }

    @Override
    public void toActivityForResult(Class clazz, Bundle data, int requestCode) {
        Intent to = new Intent(this, clazz);
        if (null != data) {
            to.putExtras(data);
        }
        startActivityForResult(to, requestCode);
    }


    /**
     * 发送空消息到主线程消息队列
     *
     * @param what
     */
    protected void sendEmptyUiMessage(int what) {
        mUiHandler.sendEmptyMessage(what);
    }

    /**
     * 发送消息到主线程消息队列
     */
    protected void sendUiMessage(Message msg) {
        mUiHandler.sendMessage(msg);
    }

    /**
     * 延时发送空消息到主线程消息队列
     *
     * @param what
     */
    protected void sendEmptyUiMessageDelayed(int what, long delayMillis) {
        mUiHandler.sendEmptyMessageDelayed(what, delayMillis);
    }

    /**
     * 延时往Ui线程发送消息
     *
     * @param msg
     * @param delayMillis
     */
    protected void sendUiMessageDelayed(Message msg, long delayMillis) {
        mUiHandler.sendMessageDelayed(msg, delayMillis);
    }

    /**
     * 处理消息
     *
     * @param msg
     */
    public void handlerMessage(Message msg) {
    }


    public static class UIHandler extends Handler {
        private SoftReference<BaseActivity2> mRe;
        private BaseActivity2 mActivity;

        public UIHandler(BaseActivity2 activity) {
            mRe = new SoftReference<BaseActivity2>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            mActivity = mRe.get();
            if (mActivity != null) {
                mActivity.handlerMessage(msg);
            }
        }
    }


}
