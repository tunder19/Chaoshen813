package com.example.administrator.chaoshen.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.SelectPayWay;
import com.example.administrator.chaoshen.activtiy.UseLuckyMoneyActivity;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.bean.SettingBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.PayOrderNetBean;
import com.example.administrator.chaoshen.net.bean.RechargeMoneyNetBean;
import com.example.administrator.chaoshen.net.bean.WinLoseBetNetBean;
import com.example.administrator.chaoshen.presenter.PayDialogPresenter;
import com.example.administrator.chaoshen.util.MathUtil;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.widget.XToast;

import java.io.Serializable;
import java.util.List;

public class PayDialog extends PopupWindow {
    private Context mContext;
    private RelativeLayout pay_way_ll,no_enough_money_ll;
    private static TextView pay_way,how_much_money;

    private final TranslateAnimation animation;
    private final View content;
    private int rechargeMode = 0;
    private BaseActivity mActivity;
    private BaseFragment mFragment;
    public static int selectPosition;
    private static List<RechargeListBean> rechargeListBeans;

    @SuppressLint("WrongConstant")
    public PayDialog(BaseActivity baseActivity, BaseFragment baseFragment, Context context, int money, View suerButton, RefershZhuListener refershZhuListener, List<LuckyMoneyBean> redList,
                     RedMoneyListener redMoneyListener, PayOrderListener payOrderListener, GetViewListener getViewListener,
                     DirectPayOrderListener directPayOrderListener) {
        super(context);
        mActivity = baseActivity;
        mFragment = baseFragment;
        mContext = context;
        mRefershZhuListener = refershZhuListener;
        mRedMoneyListener = redMoneyListener;
        mPayOrderListener = payOrderListener;
        mGetViewListener = getViewListener;
        mDirectPayOrder = directPayOrderListener;

        content = View.inflate(context, R.layout.item_change_icon, null);
        pay_way_ll = content.findViewById(R.id.pay_way_ll);
        pay_way = content.findViewById(R.id.pay_way);


        if (getBannerCache() != null && getBannerCache().getSettings() != null) {
            SettingBean settingBean = getBannerCache().getSettings();
            rechargeMode = settingBean.getRechargeMode();
            if (rechargeMode == 1) {
                pay_way_ll.setVisibility(View.VISIBLE);
                rechargeListBeans = getBannerCache().getRechargeListBeans();
                if (rechargeListBeans.size() == 1) {
                    pay_way.setText(rechargeListBeans.get(0).getBankName() + "支付");
                } else if (rechargeListBeans.size() > 1) {
                    pay_way.setText(rechargeListBeans.get(0).getBankName() + "支付");
                    pay_way_ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent to = new Intent(context, SelectPayWay.class);
                            to.putExtra(IntentKey.PAY_WAY_SELECTION,selectPosition);
                            context.startActivity(to);

                        }
                    });
                } else {
                    pay_way_ll.setVisibility(View.GONE);
                }

            } else {
                pay_way_ll.setVisibility(View.GONE);
            }

        }


        // 参数2,3：指明popupwindow的宽度和高度
        setContentView(content);

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mGetViewListener.ongetView(content);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        // 设置背景图片， 必须设置，不然动画没作用
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);

        // 设置点击popupwindow外屏幕其它地方消失
        setOutsideTouchable(true);

        // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
        animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(200);


        ((TextView) content.findViewById(R.id.yu_e_money)).setText("余额抵扣  (余额为" + MathUtil.big2(getUserCache().getCurrency()) + " 元)");

        ((TextView) content.findViewById(R.id.pay_money)).setText(MathUtil.big2(money) + "元");

        mRefershZhuListener.refresh();
       /* oldpayMoney = money;
        refreshMoney(redList);*/

        if (redList == null || redList.size() == 0) {
            content.findViewById(R.id.arrow_falge).setVisibility(View.GONE);
        } else {
            content.findViewById(R.id.arrow_falge).setVisibility(View.VISIBLE);
        }

        content.findViewById(R.id.hongbao_money_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRedMoneyListener.selectHongbao();
               /* if (redList == null || redList.size() == 0) {

                    showMsg("暂无可用红包");
                    return;
                }
                content.findViewById(R.id.arrow_falge).setVisibility(View.VISIBLE);
                Bundle data = new Bundle();
                data.putLong(IntentKey.RED_ID, redId);//redId
                data.putSerializable(IntentKey.ORDER_LIST, (Serializable) redList);
                toActivityForResult(UseLuckyMoneyActivity.class, data, Constants.SELECT_LUCKY_MONEY);*/
            }
        });
        content.findViewById(R.id.pay_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rechargeMode == 0) {
                    mPayOrderListener.onClickPayOrder();
                } else {
                    directPayOrderListener.onClickDirectPay(selectPosition );
                }

               /* WinLoseBetnetBean data = new WinLoseBetnetBean(getUserCache().getToken(), lotteryNo, rule, bet, times);
                mPresenter.getwinlose_bet(data);*/
            }
        });


        content.findViewById(R.id.dismiss_money).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 在点击之后设置popupwindow的销毁
        if (isShowing()) {
            dismiss();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        showAtLocation(suerButton, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        content.startAnimation(animation);


    }

    public View get1View() {
        return content;
    }


    public UserBean getUserCache() {
        XCache cache = XCache.get(mContext);
        if (cache.getAsObject(IntentKey.USER) == null) {
            UserBean userBean = new UserBean();
            return null;
        } else {
            return (UserBean) cache.getAsObject(IntentKey.USER);
        }
    }


    public BannerBean getBannerCache() {
        XCache cache = XCache.get(mContext);
        if (cache.getAsObject(IntentKey.BANNER_BEAN) == null) {
            BannerBean userBean = new BannerBean();
            return null;
        } else {
            return (BannerBean) cache.getAsObject(IntentKey.BANNER_BEAN);
        }
    }


    public void refresh() {
        mRefershZhuListener.refresh();
    }

    public void redMoneyListener() {
        mRedMoneyListener.selectHongbao();
    }

    public void onClickPayOrder() {
        mPayOrderListener.onClickPayOrder();
    }


    public interface RefershZhuListener {
        public void refresh();
    }

    private RefershZhuListener mRefershZhuListener;


    public interface RedMoneyListener {
        public void selectHongbao();
    }

    private RedMoneyListener mRedMoneyListener;


    public interface PayOrderListener {
        public void onClickPayOrder();
    }

    private PayOrderListener mPayOrderListener;


    public interface GetViewListener {
        public void ongetView(View view);
    }

    private GetViewListener mGetViewListener;


    public interface DirectPayOrderListener {
        public void onClickDirectPay(int selection);
    }

    private DirectPayOrderListener mDirectPayOrder;






    public int getSelection(){
        return  selectPosition;
    }

    public static void setPayWay(){
        pay_way.setText(rechargeListBeans.get(selectPosition).getBankName() + "支付");
    }

    @Override
    public void dismiss() {
        selectPosition=0;
        super.dismiss();

    }

    public void  setNo_Enough_moneyVisible(double money){
        no_enough_money_ll=content.findViewById(R.id.no_enough_money_ll);
        how_much_money=content.findViewById(R.id.how_much_money);
        no_enough_money_ll.setVisibility(View.VISIBLE);
        how_much_money.setText(money+"元");

    }

    public void  setPay_way_llGone(){
        pay_way_ll.setVisibility(View.GONE);
    }
}
