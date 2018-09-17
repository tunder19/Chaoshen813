package com.example.administrator.chaoshen.activtiy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.CheckVersionBean;
import com.example.administrator.chaoshen.bean.ClearCache;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.SettingPresenter;
import com.example.administrator.chaoshen.util.CleanMessageUtil;
import com.example.administrator.chaoshen.util.MathUtil;
import com.example.administrator.chaoshen.util.NotificationsUtils;
import com.example.administrator.chaoshen.widget.VersionUpdateDialog;
import com.youth.xframe.utils.XAppUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SettingActivity extends BaseActivity {
    @Bind(R.id.notice_text)
    TextView noticeText;
    @Bind(R.id.notice_rl)
    RelativeLayout noticeRl;
    @Bind(R.id.version_text)
    TextView versionText;
    @Bind(R.id.version_rl)
    RelativeLayout versionRl;
    @Bind(R.id.about_rl)
    RelativeLayout aboutRl;
    @Bind(R.id.clear_temp_size)
    TextView clearTempSize;
    @Bind(R.id.clear_temp_rl)
    RelativeLayout clearTempRl;
    @Bind(R.id.button_loginout)
    Button buttonLoginout;
    @Bind(R.id.notice_arrow)
    ImageView noticeArrow;
    @Bind(R.id.version_arrow)
    ImageView versionArrow;
    private DownloadManager mDownloadManager;
    private Dialog mDialog1;
    private ProgressBar mProgressBar;
    private TextView mPrecent;
    private TextView mComplete;
    private long mId;
    private SettingPresenter mPresenter;
    private String time;

    @Override
    public int setLayoutId() {
        return R.layout.activty_setting;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        mPresenter = new SettingPresenter(this, getContext());
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
    public void initView() {
        setActionBarText("设置");
        try {
            clearTempSize.setText(CleanMessageUtil.getTotalCacheSize(getContext()) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isLogin()) {
            buttonLoginout.setEnabled(true);
        } else {
            buttonLoginout.setEnabled(false);
        }
        //versionText.setText(XAppUtils.getVersionName(getContext()) + "");
        mPresenter.checkUpdateFirst();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NotificationsUtils.isNotificationEnabled(getContext())) {
            noticeText.setText("已开启");
            noticeArrow.setVisibility(View.GONE);
        } else {
            noticeText.setText("未开启");
            noticeArrow.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.notice_rl, R.id.version_rl, R.id.about_rl, R.id.clear_temp_rl, R.id.button_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notice_rl:
                if (!NotificationsUtils.isNotificationEnabled(getContext())) {
                    NotificationsUtils.toNotifySetting(this);
                }

                break;
            case R.id.version_rl:
                checkVersion();
                break;
            case R.id.about_rl:
                toActivity(AboutActivity.class, null);
                break;
            case R.id.clear_temp_rl:
                clearCache();

                break;
            case R.id.button_loginout:
                //logOut();
                if (isLogin()) {
                    mPresenter.loginOut(new TokenNetBean(getUserCache().getToken()));
                }

                break;
        }
    }

    public void logOut() {
        if (isLogin()) {

            mCache.remove(IntentKey.USER);
            //mCache.clear();
            EventBus.getDefault().post(new ClearCache());
            showMsg("退出成功");
            finish();
        }
    }

    private void clearCache() {
        if (isLogin()) {
            UserBean user = (UserBean) getUserCache().clone();
            BannerBean data = (BannerBean) ((BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN)).clone();
            //   String serviceUrl = mCache.getAsString(IntentKey.SERVICE_LIST);
            //  String help_center = mCache.getAsString(IntentKey.HELP_CENTER);
            // String service_url = mCache.getAsString(IntentKey.SERVICE_URL);
            // String h5_url = mCache.getAsString(IntentKey.H5_URL);


            //  CleanMessageUtil.clearAllCache(getContext());
            mCache.clear();
            mCache.put(IntentKey.USER, user);
            mCache.put(IntentKey.BANNER_BEAN, data);


            // mCache.put(IntentKey.SERVICE_LIST, serviceUrl);
            //  mCache.put(IntentKey.HELP_CENTER, help_center);
            // mCache.put(IntentKey.SERVICE_URL, service_url);
            //  mCache.put(IntentKey.H5_URL, h5_url);
            showMsg("清理成功");
            clearTempSize.setText("");
        }
    }

    public void updateApp(CheckVersionBean.ClientInfoBean data) {
        if (data.getVersionCode() <= MathUtil.StringtoLong(XAppUtils.getVersionName(getContext()))) {
            Toast.makeText(getContext(), "当前是最新版本", Toast.LENGTH_LONG).show();
            return;
        }
       /* int loacalVersion = XAppUtils.getVersionCode(getContext());
        if (Integer.parseInt(data.getBackVersion())<=loacalVersion){
            Toast.makeText(getContext(),"当前是最新版本",Toast.LENGTH_LONG).show();
            return;
        }*/

        VersionUpdateDialog dialog = new VersionUpdateDialog(getContext(), data);
    }

    private void checkVersion() {
        mPresenter.checkUpdate();
        //   VersionUpdateDialog dialog=new VersionUpdateDialog(getContext(),0);

    }


    @SuppressLint("ResourceType")
    public void updateAppFirst(CheckVersionBean.ClientInfoBean data) {
        if (data.getVersionCode() <= MathUtil.StringtoLong(XAppUtils.getVersionName(getContext()))) {
            versionText.setText("已是最新版本" + XAppUtils.getVersionName(getContext()));
            versionText.setTextColor(Color.parseColor(getResources().getString(R.color.text_colorin9)));
            versionArrow.setVisibility(View.GONE);

        } else {
            versionText.setText("发现新版本 V" + data.getVersion());
        }


    }


    @SuppressLint("ResourceType")
    public void upadteVersionFail() {
        versionText.setTextColor(Color.parseColor(getResources().getString(R.color.text_colorin9)));
        versionText.setText("已是最新版本 V" + XAppUtils.getVersionName(getContext()));
        versionArrow.setVisibility(View.GONE);
    }

}

