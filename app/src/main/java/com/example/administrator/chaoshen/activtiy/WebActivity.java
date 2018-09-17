package com.example.administrator.chaoshen.activtiy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.ServiceFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.MyApplication;
import com.example.administrator.chaoshen.app.UrlManager;
import com.example.administrator.chaoshen.bean.ArticleListBean;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.BuyWinLostSuccess;
import com.example.administrator.chaoshen.bean.HomeToMineBean;
import com.example.administrator.chaoshen.bean.LotteryBean;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;
import com.example.administrator.chaoshen.bean.RechargeH5Bean;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.bean.RechargeSuccessBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.ToAriticle;
import com.example.administrator.chaoshen.bean.ToKaijiangFragment;
import com.example.administrator.chaoshen.bean.ToServiceBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.bean.WebLotteryBean;
import com.example.administrator.chaoshen.bean.WebViewRefreshBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.contans.PermissionCode;
import com.example.administrator.chaoshen.net.Urls;
import com.example.administrator.chaoshen.net.bean.CheckRechargeNetBean;
import com.example.administrator.chaoshen.net.bean.M_SaveArticle;
import com.example.administrator.chaoshen.net.bean.OrdertRedPacketsNetBean;
import com.example.administrator.chaoshen.net.bean.PayOrderNetBean;
import com.example.administrator.chaoshen.net.bean.RechargeMoneyNetBean;
import com.example.administrator.chaoshen.presenter.WebViewPresenter;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.util.MathUtil;
import com.example.administrator.chaoshen.util.ShakeListenerUtils;
import com.example.administrator.chaoshen.util.SystemUtil;
import com.example.administrator.chaoshen.util.UIHelper;
import com.example.administrator.chaoshen.web.JsInteration;
import com.example.administrator.chaoshen.widget.ChaoshenAlertDialog;
import com.example.administrator.chaoshen.widget.PayDialog;
import com.example.administrator.chaoshen.widget.SharePopupWindow;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XAppUtils;
import com.youth.xframe.utils.XNetworkUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.utils.permission.XPermission;
import com.youth.xframe.widget.XToast;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.Cookie;
import retrofit2.http.Url;


public class WebActivity extends BaseActivity {
    private Button mForwardButton;
    private WebView mWebview;
    private String url;
    private boolean isPayOrder = false;
    private String addS = "LotteryApp";//自定义H5识别
    private String signature = "signature";
    private String subject = "subject";//
    private boolean isWeChat = false;
    // 声明PopupWindow
    private PopupWindow popupWindow;
    private TranslateAnimation animation;
    // 声明PopupWindow对应的视图
    private View popupView;
    private boolean openWchat = false;
    private int open_type = 0;//0是帮助中心  1是轮播图 2 是11选5   3是大乐透  4资讯详情  5 十一选5 和大乐透的中奖界面  6活动弹窗 7开奖详情  8双色球 9其他彩种
    // 10马甲资讯正文  11好友推荐 12数据分析

    private WebLotteryBean getFromNetData;
    private WebViewPresenter mPresenter;
    private double oldpayMoney;
    private double payMoney;
    private int redId;
    private String loadingUrl;  //加载的URL
    private ImageView imageView;
    private UrlManager urlManager = UrlManager.getInstance();
    private boolean firstTemp = true;
    private String firstUrl = "";
    private boolean needClearHistory;
    private String shareImageUrl; //分享的图标
    private String description;
    private String login;
    private String shareTitle;
    private String shareLink;
    private PayOrderNetBean orderBean;
    private PayDialog dialog;
    private boolean checkFinish = false;
    private long payID;
    private String jsonSignature;
    private boolean refresh = false;
    private ShakeListenerUtils shakeUtils;
    private SensorManager mSensorManager;
    private boolean shakeOpen = true;
    private Vibrator vibrator;
    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;
    private boolean openShake = false;
    private ProgressBar progressBar1;
    private RelativeLayout no_net_work;
    private boolean loadError = false;
    private String openUrl;
    private String oldUrl;
    private int firstOpen = 0;
    private float scale;
    private boolean showActionBar = false;
    private boolean change_color = false;
    private String type = "";

    @Override
    public int setLayoutId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
        return R.layout.activity_webview;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        if (getIntent() != null) {
            url = getIntent().getStringExtra(IntentKey.WEB_VIEW_URL);
            isPayOrder = getIntent().getBooleanExtra(IntentKey.IS_PAY_ORDER, false);
            if (isPayOrder == false) {
                open_type = getIntent().getIntExtra(IntentKey.OPEN_TYPE, 0);
            }
            showActionBar = getIntent().getBooleanExtra(IntentKey.SHOW_ACTIONBAR, false);
            change_color = getIntent().getBooleanExtra(IntentKey.CHANGE_COLOR, false);
            type = getIntent().getStringExtra(IntentKey.ACTION_BAR_TYPE);
        }
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (openShake) {
            registerShake();
        }

        if (openWchat) {
            openWchat = false;
            finish();
        }

        if (checkFinish) {
            showLoadding("正在查询");
            // sendEmptyUiMessageDelayed(1, 5000);
            mPresenter.checkRechargeStatus(new CheckRechargeNetBean(getUserCache().getToken(), payID));
        }
    }

    private void registerShake() {
        if (shakeUtils != null) {
            mSensorManager = (SensorManager) this
                    .getSystemService(Service.SENSOR_SERVICE);
            //加速度传感器
            mSensorManager.registerListener(shakeUtils,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
                    //根据不同应用，需要的反应速率不同，具体根据实际情况设定
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        unRegiterShake();
        super.onPause();
    }

    private void unRegiterShake() {
        if (shakeUtils != null && mSensorManager != null) {
            mSensorManager.unregisterListener(shakeUtils);
        }
    }


    public void closeActionBarAction() {
        XLog.e("-------------closeActionBarAction--------");

        if (isPayOrder) {
            setActionBarText("充值");
            showForwardView("关闭", true);
        } else {
            if (open_type == 0) {
                //  setActionBarText("帮助中心");
                setFinfishBt();
            } else if (open_type == 1) {
                setActionBarText("");
                setFinfishBt();
            } else if (open_type == 2 || open_type == 3) {  //11选5  和大乐透
                closeActionBar();
            } else if (open_type == 4) { //资讯详情
                //  setActionBarText(getIntent().getStringExtra(IntentKey.ACTION_BAR_TITLE));
                getRightImage().setVisibility(View.VISIBLE);
                getRightImage().setImageResource(R.drawable.share_icon);
                String imageUrl = getIntent().getStringExtra(IntentKey.WEB_URL);
                imageView.post(new DownloadImageRunnable(imageView, imageUrl, R.drawable.information_list,
                        mImageLoader, mOpt));
                getRightImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isLogin()) {
                            toActivity(LoginActivity.class, null);
                            return;
                        }

                        // finish();
                        shareUrl();
                    }
                });
            } else if (open_type == 5) {
                if (!isLogin()) {
                    toActivity(LoginActivity.class, null);
                    return;
                }

                //  setActionBarText("订单详情");
                getRightImage().setVisibility(View.VISIBLE);
                getRightImage().setImageResource(R.drawable.share_icon);
                String title = getIntent().getStringExtra(IntentKey.WEB_TITLE);
                String imageUrl = getIntent().getStringExtra(IntentKey.WEB_URL);
                imageView.post(new DownloadImageRunnable(imageView, imageUrl, R.drawable.information_list,
                        mImageLoader, mOpt));
                getRightImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // finish();
                        XLog.e("-------imageUrl------------" + imageUrl);
                        shareUrl();
                    }
                });
            } else if (open_type == 6) {
                setFinfishBt();
            } else if (open_type == 7) {
                if (!TextUtils.isEmpty(getIntent().getStringExtra(IntentKey.ACTION_BAR_TITLE))) {
                    setActionBarText(getIntent().getStringExtra(IntentKey.ACTION_BAR_TITLE));
                } else {
                    // setActionBarText("开奖详情");
                }
                setFinfishBt();
            } else if (open_type == 8) {//双色球
                closeActionBar();
                if (!TextUtils.isEmpty(getIntent().getStringExtra(IntentKey.ACTION_BAR_TITLE))) {
                    setActionBarText(getIntent().getStringExtra(IntentKey.ACTION_BAR_TITLE));
                } else {
                    //  setActionBarText("双色球");
                }
                getRightImage().setImageResource(R.drawable.close_web_icon);
                getRightImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            } else if (open_type == 9) {
                //   setActionBarText("服务协议");
                setFinfishBt();
            } else if (open_type == 10) { //资讯详情  和 好友推荐
                setMajiaTitle(getIntent().getStringExtra(IntentKey.ACTION_BAR_TITLE));
                // setActionBarText(getIntent().getStringExtra(IntentKey.ACTION_BAR_TITLE));
                getRightImage().setVisibility(View.VISIBLE);
                getRightImage().setImageResource(R.drawable.start_icon);
                String title = getIntent().getStringExtra(IntentKey.WEB_TITLE);
                String imageUrl = getIntent().getStringExtra(IntentKey.WEB_URL);
                imageView.post(new DownloadImageRunnable(imageView, imageUrl, R.drawable.information_list,
                        mImageLoader, mOpt));
                getRightImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        XToast.success("收藏提示");
                    }
                });
            } else if (open_type == 11) {//好友推荐
                setFinfishBt();
            } else if (open_type == 12) {//比赛详情
                closeAllBar();
            } else if (open_type == 13) {//马家包资讯详情
                getRightImage().setVisibility(View.VISIBLE);

               /* String imageUrl = getIntent().getStringExtra(IntentKey.WEB_URL);
                imageView.post(new DownloadImageRunnable(imageView, imageUrl, R.drawable.information_list,
                        mImageLoader, mOpt));*/
                ArticleListBean article = (ArticleListBean) getIntent().getSerializableExtra(IntentKey.ARTICLE_LIST_BEAN);
                M_SaveArticle saveArticle = (M_SaveArticle) getmCache().getAsObject(IntentKey.SaveArticle);

                getRightImage().setImageResource(R.drawable.favourite_start_icon);
                save_article(article);

                if (saveArticle != null) {//有记录

                    for (int i = 0; i < saveArticle.getList().size(); i++) {
                        if (saveArticle.getList().get(i).getId() == article.getId()) {
                            XLog.e(saveArticle.getList().get(i).getId() + "-------文章----------" + article.getId());
                            getRightImage().setImageResource(R.drawable.save_frivaoutir);
                            break;
                           /* getRightImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                  //  showMsg("你已经订阅该文章");
                                    save_article(article);
                                }
                            });*/

                        } /*else {
                            getRightImage().setImageResource(R.drawable.favourite_start_icon);
                            save_article(article);
                        }*/

                    }


                } /*else {
                    getRightImage().setImageResource(R.drawable.favourite_start_icon);
                    save_article(article);
                }*/


            }

        }
        if (getIntent().getBooleanExtra(IntentKey.CLOSE_ACTION_BAR, false)) {
            closeActionBar();
        }
    }


    private void save_article(ArticleListBean article) {
        getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasRead = false;
                M_SaveArticle saveArticle2 = (M_SaveArticle) getmCache().getAsObject(IntentKey.SaveArticle);
                if (saveArticle2 == null) {
                    setRed(article);
                    return;
                }

                for (int i = 0; i < saveArticle2.getList().size(); i++) {
                    XLog.e(saveArticle2.getList().get(i).getId()+"-----------article.getId()------"+article.getId());
                    if (saveArticle2.getList().get(i).getId() == article.getId()) {
                        hasRead = true;
                        break;
                    }

                }
                XLog.e("-----------article.getId()----hasRead--"+hasRead);
                if (hasRead) {
                    for (int i = 0; i < saveArticle2.getList().size(); i++) {
                        if (saveArticle2.getList().get(i).getId() == article.getId()) {
                            XLog.e("-------saveArticle2.getList()-----" + saveArticle2.getList().size());
                            saveArticle2.getList().remove(saveArticle2.getList().get(i));
                            XLog.e("-------saveArticle2.getList()2-----" + saveArticle2.getList().size());
                            getmCache().put(IntentKey.SaveArticle, saveArticle2);
                            getRightImage().setImageResource(R.drawable.favourite_start_icon);
                            showMsg("取消订阅成功");
                            return;
                        }
                    }

                } else {//订阅
                    setRed(article);
                }


            }
        });
    }

    public void setRed(ArticleListBean article) {
        if (getIntent().getSerializableExtra(IntentKey.ARTICLE_LIST_BEAN) != null) {
            XLog.e(getmCache().getAsObject(IntentKey.SaveArticle) + "-------文章-2---------" + article.getId());
            M_SaveArticle saveArticle;
            if (getmCache().getAsObject(IntentKey.SaveArticle) != null) {
                saveArticle = (M_SaveArticle) getmCache().getAsObject(IntentKey.SaveArticle);
                List<ArticleListBean> articleList = saveArticle.getList();
                articleList.add(0, article);
                saveArticle.setList(articleList);
            } else {

                saveArticle = new M_SaveArticle();
                List<ArticleListBean> data = new ArrayList<>();
                data.add(article);
                saveArticle.setList(data);

            }
            getmCache().put(IntentKey.SaveArticle, saveArticle);

        }
        getRightImage().setImageResource(R.drawable.save_frivaoutir);
        showMsg("订阅成功");
    }


    @Override
    public void initView() {
        if (change_color) {
            changeBarColor();
        }
        setActionBarText("");
        imageView = (ImageView) findViewById(R.id.share_icon);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        try {
            vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
            shakeUtils = new ShakeListenerUtils(this, vibrator);//开启摇动
            XLog.e("-----------------------shakeUtils=" + shakeUtils);
        } catch (Exception e) {
            shakeUtils = null;
        }

        getBackLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                } else {
                    finish();
                }
            }
        });
        if (isPayOrder) {
            setActionBarText("充值");
            showForwardView("关闭", true);
        }


        mPresenter = new WebViewPresenter(getContext(), this);
        mForwardButton = getRightIcon();
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWebview = (WebView) findViewById(R.id.webview);
        no_net_work = (RelativeLayout) findViewById(R.id.no_net_work);
        setWebview();


        XLog.e("-------------------加载的url=" + url);
        setSignatureCookie(getContext());
        mWebview.loadUrl(url);
        loadingUrl = url;
    }

    private void setWebview() {
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js

        String ua = webSettings.getUserAgentString();

        webSettings.setUserAgentString(ua + addS);
        //1.首先在WebView初始化时添加如下代码
        if (Build.VERSION.SDK_INT >= 19) {
            /*对系统API在19以上的版本作了兼容。因为4.4以上系统在onPageFinished时再恢复图片加载时,如果存在多张图片引用的是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载。*/
            mWebview.getSettings().setLoadsImagesAutomatically(true);
        } else {
            mWebview.getSettings().setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAppCacheEnabled(true);
        mWebview.requestFocus();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不适用缓存
        webSettings.setDatabaseEnabled(true);// 设置支持本地存储
        //取得缓存路径
        String path = getActivity().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        //设置路径
        webSettings.setDatabasePath(path);
        // 使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);  // 开启 DOM storage 功能
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getContext().getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);    // 可以读取文件缓存
        webSettings.setAppCacheEnabled(true);    //开启H5(APPCache)缓存功能


        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //使把图片加载放在最后来加载渲染
        //   webSettings.setBlockNetworkImage(true);

        webSettings.setGeolocationEnabled(true);

        mWebview.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");

        //注入js
        //addInterface();

        mWebview.setWebViewClient(
                new WebViewClient() {

                    final String[] prev = {"", "", ""};


                    @Override
                    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                        super.doUpdateVisitedHistory(view, url, isReload);
                        oldUrl = view.getUrl();
                        openUrl = url;
                        XLog.e("--------------------打印日志:oldUrl=" + oldUrl);
                        XLog.e("--------------------打印日志:openUrl=" + openUrl);
                        if (open_type == 2 || open_type == 3) {
                            if (needClearHistory) {
                                needClearHistory = false;
                                mWebview.clearHistory();//清除历史记录
                            }
                        }


                    }


                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String next) {
                        unRegiterShake();
                        XLog.e("------------------支付url---==" + next);

                        try {
                            //  if (next.startsWith("https://wx.tenpay.com")) {
                            if (next.startsWith("weixin://wap/pay?")) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(next));
                                startActivity(intent);
                                isWeChat = true;
                                // finish();
                                openWchat = true;
                                return true;
                            }
                        } catch (Exception e) {
                            showMsg("启动微信App失败");
                        }
                        try {

                            if (next.startsWith("alipays://platformapi/startApp?")) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(next));
                                startActivity(intent);
                                return true;
                            }
                        } catch (Exception e) {
                            showMsg("启动支付宝App失败");
                        }
                        if (controlByH5(next, view)) {
                            return true;
                        }
                        loadingUrl = next;
                        if (WebActivity.this.url.startsWith("http:") || WebActivity.this.url.startsWith("https:")) {
                            return false;
                        }
                        return super.shouldOverrideUrlLoading(view, WebActivity.this.url);
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        no_net_work.setVisibility(View.GONE);
                        loadError = false;

                        // showLoadding(null);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        view.loadUrl("javascript:window.local_obj.showSource('<head>'+" + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                        XLog.e("-onPageFinished--------------onProgressChanged");
                        if (!loadError) {
                            closeActionBarAction();
                            String title = getIntent().getStringExtra(IntentKey.ACTION_BAR_TITLE);
                            if (showActionBar || !TextUtils.isEmpty(title)) {
                                setActionBarText(title);
                            } else {
                                setActionBarText(view.getTitle());
                            }
                            mWebview.setVisibility(View.VISIBLE);
                            firstOpen++;
                        }

                        // hideLoadding();
                        if (!mWebview.getSettings().getLoadsImagesAutomatically()) {
                            mWebview.getSettings().setLoadsImagesAutomatically(true);
                        }

                        Object tag = view.getTag(R.id.tag_first);
                        if (tag != null) {
                            // hideLoadding();
                            return;
                        }


                        isH5showBar(view.getUrl());
                        if (!TextUtils.isEmpty(type)) {
                            controlBar(view, type);
                        }

                    }

                    @Override
                    public void onScaleChanged(WebView view, float oldScale, float newScale) {
                        super.onScaleChanged(view, oldScale, newScale);
                        scale = newScale;

                    }

                    @Override
                    public void onPageCommitVisible(WebView view, String url) {
                        super.onPageCommitVisible(view, url);
                        hideLoadding();
                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        super.onReceivedError(view, request, error);
                        //  showMsg("网页加载错误");
                        loadError = true;
                        firstOpen = 0;
                        showLoadError();
                        hideLoadding();
                        //restActionBar();
                        XLog.e("-onReceivedError--------------onProgressChanged");
                    }

                    //处理https请求
                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        //  super.onReceivedSslError(view, handler, error);
                        handler.proceed();
                    }


                }
        );

        mWebview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                XLog.e("---------------onProgressChanged");
                if ((firstOpen == 0)) {
                    if (newProgress == 100) {

                        progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                    } else {
                        progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                        progressBar1.setProgress(newProgress);//设置进度值
                    }
                }
            }
        });
    }

    private void showLoadError() {
        no_net_work.setVisibility(View.VISIBLE);
        mWebview.setVisibility(View.INVISIBLE);
        no_net_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mWebview.reload();
                XLog.e(oldUrl + "--------------------打印日志:要跳转的=" + openUrl);
                if (mWebview != null) {
                    if (openUrl.indexOf("invoke://") != -1) {
                        mWebview.loadUrl(oldUrl);
                    } else {
                        mWebview.reload();
                    }
                    //  mWebview.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void isH5showBar(String url) {
        if (getBannerCache() == null) {
            return;
        }
        String h5Url = getBannerCache().getH5Url();
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(h5Url) || loadError || showActionBar) {
            return;
        }
        XLog.e(url + "--------h5Url---------" + h5Url);
        if (url.indexOf(h5Url) != -1) {
            closeActionBar();
        }
    }


    private void shareUrl() {
        if ("true".equals(login)) {
            shareLink = shareLink + getUserCache().getUserId();
        }

        new SharePopupWindow(getActivity(), getContext(), (MyApplication) getActivity().getApplication(),
                mWebview, R.mipmap.app_icon, shareLink, shareTitle, description, shareImageUrl, imageView);
    }

    private void sharePicture() {
        if ("true".equals(login)) {
            shareLink = shareLink + getUserCache().getUserId();
        }
        toggleShotScreen();

        new SharePopupWindow(this, (MyApplication) getActivity().getApplication(), mWebview);
    }

    public void toggleShotScreen() {
        runJs("window.location.hash='toggle=screenshot'");
    }

    public void shakeWeb() {
        if (shakeOpen) {
            shakeOpen = false;
            runJs("window.location.hash='shake'");
            sendEmptyUiMessageDelayed(2, 800);
            vibrator.vibrate(200);
        }
    }


    public WebView getWebView() {
        return mWebview;
    }


    private void setFinfishBt() {
        getRightImage().setVisibility(View.VISIBLE);
        getRightImage().setImageResource(R.drawable.close_web_icon);
        getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void refreshH5() {
        refresh = false;
        XLog.e("-----------refreshH5-----");
        //runJs("window.location.hash=''");
        runJs("window.location.hash='refresh'");
    }


    private void runJs(String js) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mWebview.evaluateJavascript(js, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        } else {
            mWebview.loadUrl("javascript:" + js);
        }
    }

    /**
     * 格式化时间
     *
     * @param milliseconds
     * @param format       yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getFormatTime(long milliseconds, String format) {
        String dateString = "";
        Date date = new Date(milliseconds);
        //date.setTime(milliseconds);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            dateString = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //其中webView.canGoBack()在webView含有一个可后退的浏览记录时返回true

        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        if (open_type == 2 || open_type == 3) {
            // mWebview.reload();
        }
        unRegiterShake();
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(RechargeSuccessBean info) {
        // showMsg("充值成功");
        finish();

    }

    public boolean controlByH5(String url, WebView view) {
        XLog.e("-----------url路径===" + url);
        String contansString = "pay-success://";
        if (url.startsWith(contansString)) {
           /* // EventBus.getDefault().post(new RechargeSuccessBean());
            String[] arrr = contansString.split("=");
            if (arrr.length >=2) {

                Intent data = new Intent();
                data.putExtra(IntentKey.ORDER_ID, arrr[1]);
                setResult(Constants.RECHARGE_SUCCESS, data);
            } else {
                XLog.e("-----------url路径===" + url);
                showMsg("支付出错");
                setResult(Constants.RECHARGE_SUCCESS);
            }*/
            finish();
            return true;
        }
        String showPaydialog = "checkout://orderId=";  //调用支付框
        if (url.startsWith(showPaydialog)) {
            String[] urls = url.split("checkout://");
            String[] canshu = urls[1].split("&");
            String orderId = "";
            String amonut = "";
            String lotteyType = "";
            for (int i = 0; i < canshu.length; i++) {
                String[] arrays;
                arrays = canshu[i].split("=");
                if (i == 0) {
                    orderId = arrays[1];
                } else if (i == 1) {
                    amonut = arrays[1];
                } else if (i == 2) {
                    lotteyType = arrays[1];
                }
            }
            // String [] temps=urls[1].split("=");
            if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(amonut) || TextUtils.isEmpty(lotteyType)) {
                showMsg("参数不足");
                return true;
            }
            getFromNetData = new WebLotteryBean(orderId, amonut, lotteyType);
            // finish();
            if (!isLogin()) {
                toActivity(LoginActivity.class, null);
                showMsg("请先登录");
                return true;
            }
            mPresenter.getLuckyList(new OrdertRedPacketsNetBean(getUserCache().getToken(), getFromNetData.getLotteryType(),
                    getFromNetData.getAmount() + ""));


            return true;

        } else if (url.indexOf("redirect://login") != -1) { //登录
            toActivity(LoginActivity.class, null);
            return true;
        }
        return toOtherActivity(url, view);

        //return false;
    }

    public boolean toOtherActivity(String url, WebView view) {

        String service = "service"; //服务大厅
        String article = "article"; //资讯
        String prize = "prize"; //开奖
        String personal = "personal"; //我的
        String home = "index"; //我的
        String back = "back"; //返回上一页
        String share = "share";//分享
        String screenshot = "screenshot";//截屏分享
        String recharge = "recharge";//充值
        String shakeOn = "shake?on";//开启摇一摇
        String shakeOff = "shake?off";//关闭摇一摇
        String openWeixin = "weixin://";//打开微信
        String openAlis = "alipays://";//打开支付宝
        String savePicture = "screencapture";//保存截图到本地
        String close = "close";//关闭
        String openUrl = "webview";

        Bundle data = new Bundle();

        XLog.e("--refresh-----url---------" + url);
        if (url.indexOf(addString(service)) != -1 || url.indexOf(addString(home)) != -1) {//
            EventBus.getDefault().post(new ToServiceBean());
            appmanager.popAllActivityUntillOne(HomeActivity.class);
            return true;
        } else if (url.indexOf(addString(article)) != -1) {
            EventBus.getDefault().post(new ToAriticle());
            appmanager.popAllActivityUntillOne(HomeActivity.class);
            return true;
        } else if (url.indexOf(addString(prize)) != -1) {
            EventBus.getDefault().post(new ToKaijiangFragment());
            appmanager.popAllActivityUntillOne(HomeActivity.class);
            return true;
        } else if (url.indexOf(addString(personal)) != -1) {
            EventBus.getDefault().post(new HomeToMineBean());
            appmanager.popAllActivityUntillOne(HomeActivity.class);
            return true;
        } else if (url.indexOf(addString(IntentKey.DALETOU)) != -1) {

            return continueBuyLottery(IntentKey.DALETOU);

        } else if (url.indexOf(addString(IntentKey.HUBEI11C5)) != -1) {
            return continueBuyLottery(IntentKey.HUBEI11C5);

        } else if (url.indexOf(addString(IntentKey.SSQ)) != -1) {
            return continueBuyLottery(IntentKey.SSQ);

        } else if (url.indexOf(addString(IntentKey.WIN_LOSE) + "?rule=" + IntentKey.SFC) != -1) {

            data.putInt("lotter_type", 0); //0是胜负彩  1是任9
            toActivity(WinLoseActivity.class, data);
            finish();
            return true;
        } else if (url.indexOf(addString(IntentKey.WIN_LOSE) + "?rule=" + IntentKey.R9) != -1) {
            data.putInt("lotter_type", 1); //0是胜负彩  1是任9
            toActivity(WinLoseActivity.class, data);
            finish();

            return true;
        } else if (url.indexOf(addString(IntentKey.JINGCAI)) != -1) {
            showMsg("竞彩足球");

            return true;
        } else if (url.indexOf(addString(back)) != -1) {
            // onBackPressed();
            //mWebview.goBack();
            if (mWebview.canGoBack()) {
                mWebview.goBack();
            } else {
                finish();
            }
            return true;
        } else if (url.indexOf(addInvokeString(share)) != -1) {
            if (!isLogin()) {
                toActivity(LoginActivity.class, null);
                return true;
            }
            shareUrl();

            return true;
        } else if (url.indexOf(addInvokeString(screenshot)) != -1) {
            if (!isLogin()) {
                toActivity(LoginActivity.class, null);
                return true;
            }
            sharePicture();

            return true;
        } else if (url.indexOf(addInvokeString(recharge)) != -1) {
            Double money = 0.0;
            if (isLogin()) {
                try {
                    String[] arr = url.split("=");
                    money = Double.valueOf(arr[1]);
                } catch (Exception e) {

                }
                if (money != 0) {
                    data = new Bundle();
                    data.putDouble(IntentKey.REQUEST_MONEY, money);
                    toActivity(RechargeActivity.class, data);
                } else {
                    toActivity(RechargeActivity.class, null);
                }

                return true;
            } else {
                toActivity(LoginActivity.class, null);
                return true;
            }

        } else if (url.indexOf(addInvokeString(shakeOn)) != -1) {
            //开启摇一摇
            openShake = true;
            registerShake();
            return true;
        } else if (url.indexOf(addInvokeString(shakeOff)) != -1) {
            openShake = false;
            unRegiterShake();
            return true;
        } else if (url.indexOf(openWeixin) != -1) {
            if (SharePopupWindow.isWeixinAvilible(getContext())) {
                XAppUtils.startApp("com.tencent.mm");
            } else {
                showMsg("请先下载微信客户端");
            }
            return true;
        } else if (url.indexOf(openAlis) != -1) {
            if (isAliAvilible(getContext())) {
                XAppUtils.startApp("com.eg.android.AlipayGphone");
            } else {
                showMsg("请先下载支付宝客户端");
            }
            return true;
        } else if (url.indexOf(addInvokeString(savePicture)) != -1) { //截图保存到本地
            savePicture();

            return true;
        } else if (url.indexOf(addInvokeString(close)) != -1) { //关闭
            finish();

            return true;
        } else if (url.indexOf(addString(openUrl)) != -1) { //控制头布局
            try {
                String[] array = url.split(openUrl);
                String typeParams = array[1];
                String[] array2 = typeParams.split("&");
                String webUrl2 = array2[0].split("url=")[1];
                String webUrl = URLDecoder.decode(webUrl2);
                XLog.e(typeParams + "--------------------webUrl=" + webUrl);
                //String type = "type"; //页面布局类型 0原生标题，1用webview标题, 2需要隐藏标题，3，完全隐藏
                String type = array2[1].replace("type=", "");
                data = new Bundle();
                data.putString(IntentKey.WEB_VIEW_URL, webUrl);
                data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data.putString(IntentKey.ACTION_BAR_TYPE, type);
                toActivity(WebActivity.class, data);

            } catch (Exception e) {

            }
            return true;
        }


        return false;
    }

    public void controlBar(WebView view, String type) {
        switch (type) {

            case "0":
                break;
            case "1":
                setActionBarText(view.getTitle());
                break;
            case "2":
                closeActionBar();
                break;
            case "3":
                //closeActionBarAction();
                closeAllBar();
                break;
        }
    }


    private void savePicture() {
        try {

            XPermission.requestPermissions(getContext(), PermissionCode.RED_WRITE, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, Manifest.permission
                    .READ_EXTERNAL_STORAGE}, new XPermission.OnPermissionListener() {
                //权限申请成功时调用
                @Override
                public void onPermissionGranted() {
                    XLog.e("---onPermissionGranted---------");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //do event
                            //Bitmap bmp = UIHelper.loadBitmapFromView(mWebview);
                            Bitmap bmp = UIHelper.viewShot(mWebview);
                            if (bmp == null) {
                                showMsg("截图失败");
                            } else {
                                boolean success = UIHelper.saveImageToGallery2(getContext(), bmp);
                                if (success) {
                                    showMsg("保存成功");
                                } else {
                                    showMsg("保存失败");
                                }
                            }

                        }
                    }, 300);

                }

                //权限被用户禁止时调用
                @Override
                public void onPermissionDenied() {
                    XLog.e("---onPermissionGranted-----onPermissionDenied----");
                    //给出友好提示，并且提示启动当前应用设置页面打开权限
                    //   XPermission.showTipsDialog(XPermissionDemoActivity.this);
                    XToast.normal("保存截图到相册需要权限");
                }
            });


        } catch (Exception e) {
            showMsg("保存失败");
        }
    }


    /**
     * 拼接
     */
    public String addString(String content) {
        String redirect = "redirect://";
        return redirect + content;
    }


    /**
     * 拼接
     */
    public String addInvokeString(String content) {
        String redirect = "invoke://";
        return redirect + content;
    }


    /**
     * 判断 用户是否安装支付宝客户端
     */
    public static boolean isAliAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.eg.android.AlipayGphone")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 继续购买彩种
     */
    public boolean continueBuyLottery(String lotteryType) {
        List<LotteryBean> data = ServiceFragment.lotteryData;
        for (int i = 0; i < data.size(); i++) {
            if (lotteryType.equals(data.get(i).getLotteryType())) {

                mWebview.loadUrl(data.get(i).getUrl());
                // mWebview.clearHistory();
                needClearHistory = true;
                closeActionBar();
                return true;
            }

        }
        return false;


    }


    /**
     * 新弹窗
     */
    public void payDialog(List<LuckyMoneyBean> redList) {

        dialog = new PayDialog(this, null, getContext(), Integer.valueOf(getFromNetData.getAmount()), mWebview, new PayDialog.RefershZhuListener() {
            @Override
            public void refresh() {
                oldpayMoney = Double.parseDouble(getFromNetData.getAmount());
                orderBean = new PayOrderNetBean(getUserCache().getToken(), Long.parseLong(getFromNetData.getOrderId()));
                refreshMoney(redList);
            }
        }, redList, new PayDialog.RedMoneyListener() {
            @Override
            public void selectHongbao() {
                if (redList == null || redList.size() == 0) {

                    showMsg("暂无可用红包");
                    return;
                }
                // XToast.normal("使用红包");
                Bundle data = new Bundle();
                //   data.putLong(IntentKey.ORDER_ID, order.getOrderId());
                data.putSerializable(IntentKey.ORDER_LIST, (Serializable) redList);
                data.putLong(IntentKey.RED_ID, redId);
                toActivityForResult(UseLuckyMoneyActivity.class, data, Constants.SELECT_LUCKY_MONEY);

            }
        }, new PayDialog.PayOrderListener() {
            @Override
            public void onClickPayOrder() {
                if (getUserCache().getCurrency() < payMoney) {
                   /* showMsg("余额不足，请充值");
                    Bundle data = new Bundle();
                    double rechageMoney = payMoney - getUserCache().getCurrency();
                    data.putDouble(IntentKey.REQUEST_MONEY, rechageMoney);
                    toActivity(RechargeActivity.class, data);*/
                    showNoEnoughMoney();
                    dialog.dismiss();
                } else {
                    if (redId != 0) {
                        XLog.e("------redId------2-----" + redId);
                        orderBean.setRedId(redId);
                    }
                    mPresenter.pay_order(orderBean);
                    dialog.dismiss();
                }
            }
        }, new PayDialog.GetViewListener() {
            @Override
            public void ongetView(View view) {
                popupView = view;
            }
        }, new PayDialog.DirectPayOrderListener() {
            @Override
            public void onClickDirectPay(int selection) {
                if (getUserCache().getCurrency() < payMoney) {
                    //跳转到H5直接支付
                    if (redId != 0) {
                        orderBean.setRedId(redId);
                    }
                    List<RechargeListBean> rechargeListBeans = getBannerCache().getRechargeListBeans();
                    mPresenter.recharge_money(new RechargeMoneyNetBean(getUserCache().getToken(),
                            MathUtil.doubleTwo(Double.valueOf(payMoney - getUserCache().getCurrency())),
                            rechargeListBeans.get(dialog.getSelection()).getBankId(), rechargeListBeans.get(dialog.getSelection()).getMerchantId()));
                    dialog.dismiss();
                } else {
                    if (redId != 0) {
                        XLog.e("------redId------2-----" + redId);
                        orderBean.setRedId(redId);
                    }
                    mPresenter.pay_order(orderBean);
                    dialog.dismiss();
                }


            }
        });
        if (payMoney <= getUserCache().getCurrency()) {
            dialog.setPay_way_llGone();
        } else {
            dialog.setNo_Enough_moneyVisible(MathUtil.doubleTwo(Double.valueOf(payMoney - getUserCache().getCurrency())));
        }

        popupWindow = dialog;

    }


    public void payOrder() {
        if (redId != 0) {
            orderBean.setRedId(redId);
        }
        mPresenter.pay_order(orderBean);
    }


    public void showH5(RechargeH5Bean mdata) {
        checkFinish = true;
        payID = mdata.getPayId();
        if (mdata.getPayType() == 0) { //H5支付
            Bundle data = new Bundle();
            data.putString(IntentKey.WEB_VIEW_URL, mdata.getParams());
            data.putBoolean(IntentKey.IS_PAY_ORDER, true);
            toActivityForResult(WebActivity.class, data, Constants.RECHARGE_SUCCESS);
        }
       /* isPayOrder=true;
        mWebview.loadUrl(mdata.getParams());
        // mWebview.clearHistory();
        needClearHistory = true;
        //setActionBarText("支付");
       // closeActionBar();*/

    }


    private void showNoEnoughMoney() {
        ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
        dialog.setMessage("余额不足支付本订单，请充值");
        dialog.setSureButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelButton("去充值", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRecharge(payMoney);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void toRecharge(double payMoney1) {
        Bundle data = new Bundle();
        double rechageMoney = payMoney1 - getUserCache().getCurrency();
        data.putDouble(IntentKey.REQUEST_MONEY, rechageMoney);
        toActivity(RechargeActivity.class, data);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SELECT_LUCKY_MONEY) {
            TextView money = popupView.findViewById(R.id.hongbao_money);
            if (data != null) {
                List<LuckyMoneyBean> redList = new ArrayList<>();
                LuckyMoneyBean bean = (LuckyMoneyBean) data.getSerializableExtra(IntentKey.GET_LUCKY_MONEYID);
                redList.add(bean);
                refreshMoney(redList);
              /*
                money.setText("-" + bean.getRedPacketAmount() + "元");
                XLog.e("------redId------1-----" + redId);
                redId = bean.getId();*/
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshMoney(List<LuckyMoneyBean> redList) {
        payMoney = oldpayMoney;

        if (redList != null && redList.size() != 0) {
            redId = redList.get(0).getId();
            ((TextView) popupView.findViewById(R.id.hongbao_money)).setText("-" + MathUtil.big2(redList.get(0).getRedPacketAmount()) + "元");
            payMoney = payMoney - redList.get(0).getRedPacketAmount();
        } else {
            ((TextView) popupView.findViewById(R.id.hongbao_money)).setText("暂无可用红包");
            payMoney = payMoney - 0;
        }

        if (getUserCache().getCurrency() > payMoney) {
            ((TextView) popupView.findViewById(R.id.yue_money)).setText("-" + MathUtil.big2(payMoney) + "元");
        } else {
            ((TextView) popupView.findViewById(R.id.yue_money)).setText("-" + MathUtil.big2(getUserCache().getCurrency()) + "元");
        }
    }

    public void showLoginTips(BuyWinLostSuccess info) {
        ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        // dialog.setWindowWidth(UIHelper.dip2px(getContext(), 265));
        dialog.setMessage("支付成功");
        dialog.setSureButton("查看订单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("winlose".equals(getFromNetData.getLotteryType()) || "jingcai".equals(getFromNetData.getLotteryType())) {
                    Bundle data = new Bundle();

                    data.putLong(IntentKey.ORDER_ID, Long.parseLong(getFromNetData.getOrderId()));
                    data.putString(IntentKey.LOTTERY_TYPE, getFromNetData.getLotteryType());
                    toActivity(BetDetailActivity.class, data);
                } else {
                    BannerBean banner = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
                    String orderUrl = banner.getH5Url() + "/" + getFromNetData.getLotteryType() + "/order?id=" + getFromNetData.getOrderId();
                    hideLoadding();
                    mWebview.loadUrl(orderUrl);
                }
                dialog.cancel();
            }
        });
        dialog.setCancelButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                mWebview.goBack();
               /* EventBus.getDefault().post(new PayOrderSuccess());
                EventBus.getDefault().post(new RefreshDataBean());*/
            }
        });
        dialog.show();
    }

    @Subscribe
    public void onEventMainThread(BuyWinLostSuccess info) {
        // 购买彩种成功
        // finish();
        showLoginTips(info);
    }

    @Subscribe
    public void onEventMainThread(RefreshDataBean info) {
        //登录成功或者充值成功等
        //  mWebview.loadUrl(url+"_relod");
        //mWebview.reload();
        // showLoadding(null);

        // sendEmptyUiMessage(1);
        //  showload();
        setSignatureCookie(getContext());

        syncCookie(getContext());
        refresh = true;

    }

    public void showload() {
        showLoadding(null);
    }


    @Subscribe
    public void onEventMainThread(WebViewRefreshBean info) {

        // showLoadding(null);
        /*try {

          //  hideLoadding();


            showMsg("弹窗");
          //  sendEmptyUiMessage(1);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sendEmptyUiMessage(1);
                }
            });
        }catch (Exception e){
            XLog.e("--------Exception--------");
        }*/
        hideLoadding();
        sendMessage();
    }

    public void sendMessage() {
        sendEmptyUiMessage(1);

    }

    @Override
    public void handlerMessage(Message msg) {
        //  super.handlerMessage(msg);
        if (msg.what == 1) {
            XLog.e(isLogin() + "------------goBack()--------");
            //   writeData(true);
            refreshH5();
        } else if (msg.what == 2) {
            mUiHandler.removeMessages(2);
            shakeOpen = true;
        }

    }

    /**
     * js接口
     */
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            getHtmlContent(html);
        }
    }

    /**
     * 获取内容
     *
     * @param html
     */
    private void getHtmlContent(final String html) {
        //    XLog.e("LOGCAT+网页内容:" + html);
        Document document = Jsoup.parse(html);
        //通过类名获取到一组Elements，获取一组中第一个element并设置其html
//                Elements elements = document.getElementsByClass("loadDesc");
//                elements.get(0).html("<p>test</p>");

        //通过ID获取到element并设置其src属性
//                Element element = document.getElementById("imageView");
//                element.attr("src","file:///test/dragon.jpg");

        //通过meta标签的name获得其内容
        shareImageUrl = document.select("meta[name=image]").get(0).attr("content");
        XLog.e("-----------------shareImageUrl=" + shareImageUrl);
        description = document.select("meta[name=description]").get(0).attr("content");
        login = document.select("meta[name=login]").get(0).attr("content");
        shareTitle = document.select("meta[name=title]").get(0).attr("content");
        shareLink = document.select("meta[name=link]").get(0).attr("content");

    }


    public void setRechargeFailed(boolean failed) {
        checkFinish = false;
        if (failed) {//充值失败
            showMsg("支付失败");
        } else {//充值成功

        }
    }


    public void addInterface() {
        mWebview.addJavascriptInterface(new JsInteration() {
            //定义要调用的方法
            //msg由js调用的时候传递
            @JavascriptInterface
            public String getSignature() {
                return jsonSignature;
            }
        }, "LotteryApp");
    }


    public void syncCookie(Context context) {
        Cookie cookie = signatureCookie();
        XLog.e("document.cookie=" + cookie.toString());
        runJs("document.cookie=" + cookie.toString());
    }

    Cookie signatureCookie() {
        String domain = getDomain(url);

        return new Cookie.Builder()
                .name("signature")
                .value(URLEncoder.encode(getSignuater()))
                .domain(domain)
                .secure()
                .build();
    }

    public void setSignatureCookie(Context context) {
        Cookie cookie = signatureCookie();
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        cookieManager.setCookie(cookie.domain(), cookie.toString());
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 获取URL的域名
     */
    private static String getDomain(String url) {
        url = url.replace("http://", "").replace("https://", "");
        if (url.contains("/")) {
            url = url.substring(0, url.indexOf('/'));
        }
        return url;
    }


    public String getSignuater() {
        JSONObject o = new JSONObject();
        String key = signature;
        try {
            o.put("channelId", "A100001");
            o.put("timestamp", getFormatTime(System.currentTimeMillis(), "yyyyMMddHHmmss"));
            o.put("platform", 1);
            o.put("version", XAppUtils.getVersionName(this) + "");

            if (XNetworkUtils.isWifiConnected()) {
                o.put("network", "wifi");
            } else if (XNetworkUtils.is4G()) {
                o.put("network", "4g");
            } else {
                o.put("network", "其他联网方式");
            }
            o.put("device", SystemUtil.getDeviceBrand() + SystemUtil.getSystemModel() + "");
            o.put("os", SystemUtil.getSystemVersion());
            XCache mcahe = XCache.get(getContext());
            String imei = "";
            if (!TextUtils.isEmpty(mcahe.getAsString(IntentKey.IMEI))) {
                //  mcahe.put(IntentKey.IMEI, mcahe.getAsString(IntentKey.IMEI));
                imei = mcahe.getAsString(IntentKey.IMEI);
                XLog.e("-------缓存获取------E1----" + mcahe.getAsString(IntentKey.IMEI));
            } else {
                try {
                    imei = SystemUtil.getIMEI(this);
                    mcahe.put(IntentKey.IMEI, imei);
                    XLog.e("------IMEI码-------E1----" + mcahe.getAsString(IntentKey.IMEI));
                } catch (Exception e) {
                    imei = SystemUtil.getUniquePsuedoID();
                    mcahe.put(IntentKey.IMEI, imei);
                    XLog.e("------虚拟ID-------E1----" + mcahe.getAsString(IntentKey.IMEI));
                }
            }
            o.put("sid", imei);
            //经纬度注入
            XCache mCache = XCache.get(this);
            if (isLogin()) {
                if (mCache.getAsObject(IntentKey.USER) != null &&
                        !TextUtils.isEmpty(((UserBean) mCache.getAsObject(IntentKey.USER)).getToken())) {
                    o.put("token", ((UserBean) mCache.getAsObject(IntentKey.USER)).getToken());
                    XLog.e(((UserBean) mCache.getAsObject(IntentKey.USER)).getToken() + "-----token()----");
                }
            }
        } catch (Exception e) {
            XLog.e("-----------------local strog错误---------");
            showMsg("网络错误");
            finish();
        }
        jsonSignature = o.toString();
        return jsonSignature;
    }
}
