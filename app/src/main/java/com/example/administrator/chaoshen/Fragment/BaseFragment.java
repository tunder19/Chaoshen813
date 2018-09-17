package com.example.administrator.chaoshen.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.adapter.PopRecycleAdapter;
import com.example.administrator.chaoshen.app.MyApplication;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.BaseInfo;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.util.BaseNetView;
import com.example.administrator.chaoshen.util.SystemUtil;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.example.administrator.chaoshen.widget.AppProgressDialog;
import com.example.administrator.chaoshen.widget.TopMiddlePopup;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.youth.xframe.base.XApplication;
import com.youth.xframe.base.XFragment;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;


/**
 * fragment基类，包含公有行为和属性
 */
public abstract class BaseFragment extends XFragment implements BaseNetView, Handler.Callback {
    protected AppProgressDialog mProgressDialog;
    public static final String INDEX = "index";
    protected Handler mUiHandler;
    private String title;
    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions mOpt;
    protected int mScreenWidth, mScreenHeight;
    protected boolean isVisible = false;
    protected boolean isPrepared = false;
    protected boolean mIsLoaded = false;
    protected XApplication mApp;
    protected TopMiddlePopup topPopupwindow;
    private List<String> list;
    public XCache mCache;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initData();
        initImageLoader();
        if (null != getActivity()) {
            WindowManager wm = (WindowManager) getActivity()
                    .getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = wm.getDefaultDisplay().getWidth();
            mScreenHeight = wm.getDefaultDisplay().getHeight();
        }

        View rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        initViews(rootView);
        isPrepared = true;
        onVisible();

        return rootView;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }


    @Override
    public void onDestroyView() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mApp = MyApplication.getInstance();
        mCache = ((BaseActivity) getActivity()).getmCache();
        super.onCreate(savedInstanceState);

        mUiHandler = new Handler(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public abstract int getLayoutId();

    public abstract void initData();


    @Override
    public void initView() {

    }

    /**
     * 在子类中复写初始化界面
     * 必须super调用父类
     */
    protected void initViews(View root) {
        mProgressDialog = new AppProgressDialog(getActivity());
        mProgressDialog.setCanceledOnTouchOutside(true);
    }


    /**
     * 布局文件中需要含有id为card_container的Linearlayout
     *
     * @param msg
     */
    public void showMsg(String msg) {
       /* if (getActivity() == null || !isAdded())
            return;
        Toast  mToast=Toast.makeText(getActivity(),null,Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.show();*/
        ToastUtil.showNormalToast(getContext(), msg);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            //相当于Fragment的onResume
            isVisible = true;
            onVisible();
        } else {
            //相当于Fragment的onPause
            isVisible = false;
            onInVisible();
        }
    }


    public void onVisible() {
        if (mIsLoaded || !isPrepared || !isVisible) {
            return;
        }
        XLog.e("onVisible" + this.getClass().getSimpleName());
        onLayzeLoad();
        mIsLoaded = true;
    }


    public void onInVisible() {
    }

    /**
     * 懒加载
     */
    public void onLayzeLoad() {
    }

    /**
     * 获得字符串
     *
     * @param res
     * @return
     */

    public String getStringFromResoure(int res) {
        if (!isAdded())
            return null;
        try {
            return getString(res);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
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
            mProgressDialog.dismiss();
        }
    }

    /**
     * 跳转到activity
     *
     * @param clazz
     * @param data
     */
    public void toActivity(Class clazz, Bundle data) {
        if (null == getActivity()) {
            return;
        }
        Intent to = new Intent(getActivity(), clazz);
        if (null != data) {
            to.putExtras(data);
        }
        startActivity(to);
    }

    /**
     * 跳转到相应的 Fragment
     *
     * @param tag Faragment 类名
     */
    public void toActivity(String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.KEY_TAG, tag);
        toActivity(BaseActivity.class, bundle);
    }

    /**
     * 跳转到相应的 Fragment
     *
     * @param tag Faragment 类名
     */
    public void toActivity(String tag, Bundle data) {
        if (null != data) {
            data.putString(IntentKey.KEY_TAG, tag);
        }
        toActivity(BaseActivity.class, data);
    }

    /**
     * 跳转到activity返回结果
     *
     * @param clazz
     * @param data
     */
    public void toActivityForResult(Class clazz, Bundle data, int requestCode) {
        if (null == getActivity()) {
            return;
        }
        Intent to = new Intent(getActivity(), clazz);
        if (null != data) {
            to.putExtras(data);
        }
        startActivityForResult(to, requestCode);
    }

    /**
     * 网络错误提示
     */
    public void showNetErr(String message) {
        /*String msg = getStringFromResoure(R.string.network_err);
        if (!TextUtils.isEmpty(message)) {
            msg = message;
        }
        showMsg(msg);*/
        ToastUtil.showNetErrorToast(getActivity());
    }

    /**
     * 网络错误提示
     */
   /* public void showNetErr(BaseInfo info) {
       if (info.getHead()!=null){
           showMsg(info.getHead().getErrorMsg());
       }else {
           showMsg("网络错误");
       }
    }*/

    /**
     * 数据传输异常
     */
    public void showDataWrong() {
        showMsg(getStringFromResoure(R.string.data_wrong));
    }


    protected void sendEmptyUiMessage(int what) {
        mUiHandler.sendEmptyMessage(what);
    }

    protected void sendUiMessage(Message msg) {
        mUiHandler.sendMessage(msg);
    }

    protected void sendEmptyUiMessageDelayed(int what, long delayMillis) {
        mUiHandler.sendEmptyMessageDelayed(what, delayMillis);
    }

    protected void sendUiMessageDelayed(Message msg, long delayMillis) {
        mUiHandler.sendMessageDelayed(msg, delayMillis);
    }

    public void handlerMessage(Message msg) {
    }

    @Override
    public boolean handleMessage(Message msg) {
        handlerMessage(msg);
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public BannerBean getBannerCache() {

        return (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
    }


    /**
     * 刷新页面,子类重写
     */
    public void refresh() {
    }

    /**
     * 标题栏右按钮1背景，子类重写
     */
    public int getRightBtn1Res() {
        return 0;
    }

    /**
     * 标题栏右按钮2背景，子类重写
     *
     * @return
     */
    public int getRightBtn2Res() {
        return 0;
    }

    /**
     * 子类重写，标题左侧icon
     *
     * @return
     */
    public int getTitleDrawble() {
        return 0;
    }

    /**
     * 独立标题，如果子类重写了，将会显示在标题位置
     *
     * @return
     */
    public String getIndependentTitle() {
        return null;
    }

    /**
     * 在主线程执行
     *
     * @param runnable
     */
    public void runOnUiThread(Runnable runnable) {
        if (null == getActivity())
            return;
        getActivity().runOnUiThread(runnable);
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


    public void finish() {
        if (null != getActivity())
            getActivity().finish();
    }

    public Context getContext() {
        return getActivity();
    }

    public void scrollToTop() {
    }

    /**
     * 弹出键盘
     *
     * @param editText
     */
    public void showInput(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }


    /**
     * 设置弹窗
     */
    public void setPopup(AdapterView.OnItemClickListener onItemClickListener, List<String> list, PopRecycleAdapter.OnRecyclerViewItemClickListener rc) {
        this.list = list;
        XLog.e("---------list-------" + list);
        topPopupwindow = new TopMiddlePopup(this, getContext(), XDensityUtils.getScreenWidth(), XDensityUtils.getScreenHeight(),
                onItemClickListener, this.list, 0, rc);
    }

    public UserBean getUserCache() {
        if (mCache.getAsObject(IntentKey.USER) == null) {
            UserBean userBean = new UserBean();
            return null;
        } else {
            return (UserBean) mCache.getAsObject(IntentKey.USER);
        }
    }

    public void clearUserCache() {
        mCache.put(IntentKey.USER, "");

    }

    public boolean isLogin() {
        if (getUserCache() != null) {
            String token = getUserCache().getToken();
            if (TextUtils.isEmpty(token)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public XCache getmCache() {
        return mCache;
    }

/*public void setmPagerListener(MultiPagerFragment.PagerListener listener) {
    }*/

    /**
     * 关闭软键盘
     */
    public void closeKeyboard() {
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
