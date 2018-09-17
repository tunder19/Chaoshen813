package com.example.administrator.chaoshen.activtiy;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.app.ActivityManager;
import com.example.administrator.chaoshen.app.AppUtil;
import com.example.administrator.chaoshen.app.MyApplication;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.AppStatusConstant;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.util.AppStatusManager;
import com.example.administrator.chaoshen.util.BaseNetView;
import com.example.administrator.chaoshen.util.SystemUtil;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.example.administrator.chaoshen.widget.AppProgressDialog;
import com.example.administrator.chaoshen.widget.TopMiddlePopup;
import com.example.administrator.chaoshen.widget.refreshlayout.Engine;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.youth.xframe.base.XActivity;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public abstract class BaseActivity extends MyXactivity implements View.OnClickListener, BaseNetView {
    private TextView mTitleTextView, popup_title;
    public LinearLayout mBackwardbButton;
    private Button mForwardButton;
    private FrameLayout mContentLayout;
    private LinearLayout title_top, title_top0;
    protected AppProgressDialog mProgressDialog;
    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions mOpt;
    protected Handler mUiHandler;
    protected Engine mEngine;
    protected MyApplication mApp;
    private LinearLayout all_title_bar;
    protected TopMiddlePopup topPopupwindow;
    private boolean showPopupwindow = showPopUpBar();
    private ArrayList<String> list;
    private ImageView text_title_bg, right_img;
    public XCache mCache;
    private LinearLayout soft_key;
    private ImageView popup_title_im;
    private boolean up = false;
    private ObjectAnimator icon_anim;
    private boolean isFirstAnim = true;
    private RelativeLayout biaoti;
    public ActivityManager appmanager = ActivityManager.getInstance();
    private LinearLayout web_top_change1, title_top_forweb;
    public SwipeBackLayout mSwipeBackLayout;

    @Override
    public int getLayoutId() {
        if (AppUtil.isTransparent) {
            setTransparent(this); //设置透明方法
        }

        hideActionBar();
        initImageLoader();
        super.setContentView(R.layout.activity_title);

        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
        showSoftKey();
        setupViews();
        mCache = XCache.get(getContext());
        mApp = (MyApplication) MyApplication.getInstance();
        mProgressDialog = new AppProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(true);
        mUiHandler = new UIHandler(this);
        finishThisView();
        setLeftScrrow();

        return setLayoutId();
    }

    /**设置向右滑动退出界面*/
    private void setLeftScrrow() {
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeSize(50);//默认不知道多少 ,200就是放大范围，滑动删除的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
    }


    public void setMajiaTitle(String title){
        if (TextUtils.isEmpty( APP_Contants.getColor())){

        }else {
            web_top_change1.setBackgroundColor(Color.parseColor(APP_Contants.getColor()));
        }
        setActionBarText(title);

    }


    public void finishThisView(){
        if (this instanceof AdvertisementActivity){

        }else {
            if (Constants.NORMAL_STATE==0){//没有从广告页打开
                toActivity(AdvertisementActivity.class,null);
                finish();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appmanager.pushActivity(this);
    }

    protected void protectApp() {
        Intent intent = new Intent(this, AdvertisementActivity.class);
        intent.putExtra(AppStatusConstant.KEY_HOME_ACTION, AppStatusConstant.ACTION_RESTART_APP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        appmanager.popActivity(this);
        // appmanager.popActivity(getClass());
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initDataNew(savedInstanceState);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {

    }

    public abstract int setLayoutId();

    public abstract void initDataNew(Bundle savedInstanceState);

    public abstract boolean showActionBar();

    public abstract boolean showPopUpBar();


    //虚拟键盘问题
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void highApiEffects() {
        getWindow().getDecorView().setFitsSystemWindows(true);
        //透明状态栏 @顶部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏 @底部    这一句不要加，目的是防止沉浸式状态栏和部分底部自带虚拟按键的手机（比如华为）发生冲突，注释掉就好了
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }


    protected void initImageLoader() {
        mOpt = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.default_icon)
                .showImageOnFail(R.drawable.default_icon)
//                .showImageOnLoading(R.drawable.default_icon)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    /**
     * 设置弹窗
     */
    public void setPopup(AdapterView.OnItemClickListener onItemClickListener, ArrayList<String> list) {
        this.list = list;
        topPopupwindow = new TopMiddlePopup(this, this, XDensityUtils.getScreenWidth(), XDensityUtils.getScreenHeight(),
                onItemClickListener, this.list, 0);
    }


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
            //  activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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


    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    //设置自定义标题栏
    private void setupViews() {

       /* int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        int statusHigh=result;*/


        int statusHigh = XDensityUtils.getStatusBarHeight();
        title_top = (LinearLayout) findViewById(R.id.title_top);
        biaoti = (RelativeLayout) findViewById(R.id.biaoti);
        web_top_change1 = (LinearLayout) findViewById(R.id.web_top_change1);
        title_top_forweb = (LinearLayout) findViewById(R.id.title_top_forweb);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top.getLayoutParams();
        //  params.height = px2dp(getContext(),statusHigh);
        params.height = statusHigh;
        XLog.e("---------statusHigh------------" + statusHigh);
        title_top.setLayoutParams(params);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        text_title_bg = (ImageView) findViewById(R.id.text_title_bg);
        text_title_bg.setOnClickListener(this);
        all_title_bar = (LinearLayout) findViewById(R.id.all_title_bar);
        mBackwardbButton = (LinearLayout) findViewById(R.id.button_backward);
        mForwardButton = (Button) findViewById(R.id.button_forward);
        right_img = (ImageView) findViewById(R.id.right_img);
        mBackwardbButton.setOnClickListener(this);
        if (showActionBar()) {
            all_title_bar.setVisibility(View.VISIBLE);
            // all_title_bar.getBackground().setAlpha(255);
        } else {
            all_title_bar.setVisibility(View.GONE);
        }

        if (showPopupwindow) {
            XLog.e(all_title_bar.getHeight() + "------------111----标题栏高度");
            popup_title = (TextView) findViewById(R.id.popup_title);
            popup_title.setVisibility(View.VISIBLE);
            mTitleTextView.setVisibility(View.INVISIBLE);
            popup_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    topPopupwindow.show(all_title_bar);
                    // setShowPopUp();
                }
            });
        }

    }

    public void setShowPopUp() {
        if (popup_title_im == null) {
            popup_title_im = (ImageView) findViewById(R.id.popup_title_im);
        }
        if (up) {

            popup_title_im.setVisibility(View.VISIBLE);
            popup_title_im.setImageResource(R.drawable.up_arrow);
        } else {
            popup_title_im.setVisibility(View.VISIBLE);
            popup_title_im.setImageResource(R.drawable.down_arrow);
        }
        up = !up;

    }

    public View getBiaoTi(){
        return biaoti;
    }

    public void closeActionBar() {
        //all_title_bar.setVisibility(View.GONE);

        web_top_change1.setVisibility(View.GONE);
        int statusHigh = XDensityUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top_forweb.getLayoutParams();
        //  params.height = px2dp(getContext(),statusHigh);
        params.height = statusHigh;
        XLog.e("---------statusHigh------------" + statusHigh);
        title_top_forweb.setLayoutParams(params);
        String color=APP_Contants.getColor();
        if (TextUtils.isEmpty(getmCache().getAsString(APP_Contants.CHANGE_MODE))||"1".equals(getmCache().getAsString(APP_Contants.CHANGE_MODE))){
            color=APP_Contants.getColor();
        }else {
            color=APP_Contants.getYueHongColor();
        }
        title_top_forweb.setBackgroundColor(Color.parseColor(color));
    }

    public void changeBarColor(){
        web_top_change1.setBackgroundColor(Color.parseColor(APP_Contants.getColor()));
    }



    public void restActionBar() {
        //all_title_bar.setVisibility(View.GONE);

        web_top_change1.setVisibility(View.VISIBLE);
        int statusHigh = XDensityUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top_forweb.getLayoutParams();
        //  params.height = px2dp(getContext(),statusHigh);
        params.height = statusHigh;
        title_top_forweb.setLayoutParams(params);
        title_top_forweb.setBackgroundColor(Color.parseColor("#C8152D"));
    }



    public void closeAllBar() {

        web_top_change1.setVisibility(View.GONE);
        /*int statusHigh = XDensityUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top_forweb.getLayoutParams();
        params.height = statusHigh;
        title_top_forweb.setLayoutParams(params);*/
        title_top_forweb.setBackgroundColor(Color.parseColor("#C8152D"));
    }


    public void setShowPopdown() {

       /* XLog.e("----setShowPopUp----------setShowPopdown-");
        popup_title = (TextView) findViewById(R.id.popup_title);
        popup_title.setVisibility(View.VISIBLE);*/
    }

    public LinearLayout getAcionbarRl() {
        return all_title_bar;
    }

    /****
     * 显示标题排除返回键
     */
    public void showActionBarNoBack(String text) {
        all_title_bar.setVisibility(View.VISIBLE);
        mBackwardbButton.setVisibility(View.INVISIBLE);
        mTitleTextView.setText(text);
    }


    /**
     * 设置pop标题
     */
    public void setpopBarTitle(String a) {
        popup_title.setText(a);
    }

    /**
     * 设置标题
     */
    public void setActionBarTitle(String a) {
        mTitleTextView.setText(a);
    }

    public ImageView getActionBarTitle() {
        return text_title_bg;
    }

    public void setActionBarText(String a) {
        setActionBarTitle(a);
    }

    public void showActonBarBg(int Vis) {
        text_title_bg.setVisibility(Vis);
       /* if (Vis == View.VISIBLE) {
            setShowPopdown();
        } else {
            setShowPopUp();
        }*/
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
    }

    public ImageView getRightImage() {
        return right_img;
    }

    public LinearLayout getBackLeft() {
        return mBackwardbButton;
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
    protected void showForwardView(String forwardResId, boolean show) {
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
     * 提供是否显示提交按钮
     *
     * @param
     * @param show true则显示
     */
    protected void setRightIconDrawle(int background, boolean show) {
        if (mForwardButton != null) {
            if (show) {
                mForwardButton.setVisibility(View.VISIBLE);
                mForwardButton.setText("");
                mForwardButton.setBackgroundResource(background);
            } else {
                mForwardButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    public Button getRightIcon() {
        return mForwardButton;
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
                XLog.e("---------------点击了返回");
                onBackward(v);
                break;

            case R.id.button_forward:
                onForward(v);
                break;
            case R.id.text_title_bg:
                // setShowPopUp();
                break;

            default:
                break;
        }
    }


    @Override
    public void showMsg(String msg) {
        /*Toast mToast = Toast.makeText(getActivity(), null, Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.show();*/
        ToastUtil.showNormalToast(getApplicationContext(),msg);
        //  Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
            mProgressDialog.setMessage(loaddingMessage);
        }
    }



    public void showLoading15(String loaddingMessage) {
        if (null != mProgressDialog) {
        } else {
            XLog.e("mProgressDialog is null! did you super initViews?");
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (!mProgressDialog.isShowing() && getActivity() != null && !getActivity().isFinishing()) {
            mProgressDialog.show();
            mProgressDialog.setMessage(loaddingMessage);
        }
        endDialogCancel();
    }

    public void endDialogCancel() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                if (mProgressDialog != null) {
                    mProgressDialog.setCanceledOnTouchOutside(true);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, Constants.DIALOG_SECONDS);//3秒后执行TimeTask的run方法
    }








    @Override
    public void hideLoadding() {
        if (null != mProgressDialog && mProgressDialog.isShowing() && getActivity() != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.dismiss();
                }
            });

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
       /* String msg = getStringFromResoure(R.string.network_err);
        if (!TextUtils.isEmpty(message)) {
            msg = message;
        }
        showMsg(msg);*/
        ToastUtil.showNetErrorToast(getActivity());
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
        private SoftReference<BaseActivity> mRe;
        private BaseActivity mActivity;

        public UIHandler(BaseActivity activity) {
            mRe = new SoftReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            mActivity = mRe.get();
            if (mActivity != null) {
                mActivity.handlerMessage(msg);
            }
        }
    }

    public UserBean getUserCache() {

        return (UserBean) mCache.getAsObject(IntentKey.USER);
    }


    public BannerBean getBannerCache() {

        return (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
    }


    public void clearUserCache() {
        // mCache.clear();
        mCache.remove(IntentKey.USER);
    }


    public boolean isLogin() {
        if (mCache.getAsObject(IntentKey.USER) == null) {
            return false;
        }
        String token = ((UserBean) mCache.getAsObject(IntentKey.USER)).getToken();
        return !TextUtils.isEmpty(token);
    }

    public XCache getmCache() {
        return mCache;
    }

    public void setmCache(XCache mCache) {
        this.mCache = mCache;
    }


    //获取虚拟键盘高度

    public static int getNavigationBarHeight(Context context) {
       /* int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
             return result;
        }*/

        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        XLog.e("Navi height:" + height);
        return height;


    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public void showSoftKey() {
        /*if (hasNavBar(getContext())) {
            soft_key = (LinearLayout) findViewById(R.id.soft_key);
            int keyHigh = getNavigationBarHeight(getContext());

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) soft_key.getLayoutParams();
            params.height = keyHigh;
            soft_key.setLayoutParams(params);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏，解决虚拟键遮挡问题
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //  getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

           /* View decorView =getWindow().getDecorView();
            //View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
*/
        }
    }

    /**
     * 关闭软键盘
     */
    public void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void closeKey2(){
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }




    public void showSoftKeyV(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//隐藏软键盘 //
      //  imm.hideSoftInputFromWindow(et_edit.getWindowToken(), 0);
//显示软键盘
        imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
        //切换软键盘的显示与隐藏
      //  imm.toggleSoftInputFromWindow(tv.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);


    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (null == imm)
            return;

        if (isShow) {
            if (getCurrentFocus() != null) {
                //有焦点打开
                imm.showSoftInput(getCurrentFocus(), 0);
            } else {
                //无焦点打开
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                //有焦点关闭
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                //无焦点关闭
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        }
    }


    public String getImei(){
        XCache mcahe = XCache.get(getContext());
        String imei = "";
        if (!TextUtils.isEmpty(mcahe.getAsString(IntentKey.IMEI))) {
            //  mcahe.put(IntentKey.IMEI, mcahe.getAsString(IntentKey.IMEI));
            imei = mcahe.getAsString(IntentKey.IMEI);
            XLog.e("-------缓存获取------E1----" + mcahe.getAsString(IntentKey.IMEI));
        } else {
            try {
                imei = SystemUtil.getIMEI(getContext());
                mcahe.put(IntentKey.IMEI, imei);
                XLog.e("------IMEI码-------E1----" + mcahe.getAsString(IntentKey.IMEI));
            } catch (Exception e) {

                imei = SystemUtil.getUniquePsuedoID();
                mcahe.put(IntentKey.IMEI, imei);
                XLog.e("------虚拟ID-------E1----" + mcahe.getAsString(IntentKey.IMEI));
            }
        }
        return imei;
    }


}



