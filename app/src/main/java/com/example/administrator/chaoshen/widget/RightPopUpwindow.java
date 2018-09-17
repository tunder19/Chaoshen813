package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.activtiy.WinLoseActivity;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.ToKaijiangFragment;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.youth.xframe.widget.XToast;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class RightPopUpwindow extends PopupWindow implements View.OnClickListener {

    TextView kajiangActivity;
    TextView wanfaActivity;
    private View mShow;
    private BaseActivity activity;
    private String mlottery;

    public RightPopUpwindow(BaseActivity ac, Context context, View showView, String lottery) {
        super(context);
        View content = View.inflate(context, R.layout.popupwindow_right, null);
        setContentView(content);
        kajiangActivity = content.findViewById(R.id.kajiang_activity);
        wanfaActivity = content.findViewById(R.id.wanfa_activity);
        kajiangActivity.setOnClickListener(this);
        wanfaActivity.setOnClickListener(this);
        mlottery = lottery;
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        setClippingEnabled(false);
        mShow = showView;
        activity = ac;
        setTouchable(true);
        setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x10000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = ac.getWindow().getAttributes();
        lp.alpha = 0.25f;
        ac.getWindow().setAttributes(lp);
        //
        /*if (Build.VERSION.SDK_INT < 24) {
            showAsDropDown(mShow);
        } else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            popupWindow.showAtLocation(mLine, Gravity.NO_GRAVITY, location[0], location[1] + 2);
        }*/

        //  showAsDropDown(mShow,getWidth()/2,0);
        //  showAtLocation(mShow,Gravity.NO_GRAVITY, location[0]-getM(),location[1]-getHeight()/2);

    }

    @Override
    public void dismiss() {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        activity.getWindow().setAttributes(lp);
        super.dismiss();
    }

    public RightPopUpwindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RightPopUpwindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kajiang_activity:
                // XToast.success("点击开奖");
                /*dismiss();
                EventBus.getDefault().post(new ToKaijiangFragment());
                activity.finish();*/
                BannerBean bannerBean = (BannerBean) activity.mCache.getAsObject(IntentKey.BANNER_BEAN);
                String url = bannerBean.getH5Url() + "/" + IntentKey.WIN_LOSE + "/prizes";
                Bundle data = new Bundle();
                data.putString(IntentKey.WEB_VIEW_URL, url);
                data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data.putInt(IntentKey.OPEN_TYPE, 0);
                activity.toActivity(WebActivity.class, data);

                dismiss();

                break;
            case R.id.wanfa_activity:
                BannerBean bannerBean1 = (BannerBean) activity.mCache.getAsObject(IntentKey.BANNER_BEAN);
                String url2 = bannerBean1.getHelpUrl() + "/" + mlottery + ".html";
                Bundle data2 = new Bundle();
                data2.putString(IntentKey.WEB_VIEW_URL, url2);
                data2.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data2.putInt(IntentKey.OPEN_TYPE, 0);
                activity.toActivity(WebActivity.class, data2);

                dismiss();
                break;

        }
    }


}
