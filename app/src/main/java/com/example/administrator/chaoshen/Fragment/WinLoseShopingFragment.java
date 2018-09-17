package com.example.administrator.chaoshen.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.activtiy.RechargeActivity;
import com.example.administrator.chaoshen.activtiy.UseLuckyMoneyActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.adapter.WinLoseShopAdapter;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.LimitBean;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;
import com.example.administrator.chaoshen.bean.PayOrderSuccess;
import com.example.administrator.chaoshen.bean.RechargeFailed;
import com.example.administrator.chaoshen.bean.RechargeH5Bean;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.SettingBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.bean.WinLoseBetnetBean;
import com.example.administrator.chaoshen.bean.WinLoseDataBean;
import com.example.administrator.chaoshen.bean.WinloseMatchesBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.CheckRechargeNetBean;
import com.example.administrator.chaoshen.net.bean.LotteryLimitNetBean;
import com.example.administrator.chaoshen.net.bean.OrdertRedPacketsNetBean;
import com.example.administrator.chaoshen.net.bean.PayOrderNetBean;
import com.example.administrator.chaoshen.net.bean.RechargeMoneyNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.net.bean.WinLoseBetNetBean;
import com.example.administrator.chaoshen.presenter.WinLoseShopPresenter;
import com.example.administrator.chaoshen.util.MathJava.WinloseUtil;
import com.example.administrator.chaoshen.util.MathUtil;
import com.example.administrator.chaoshen.util.SortUtil;
import com.example.administrator.chaoshen.util.UIHelper;
import com.example.administrator.chaoshen.widget.AppAlertDialog;
import com.example.administrator.chaoshen.widget.BeishuKeyBroad;
import com.example.administrator.chaoshen.widget.ChaoshenAlertDialog;
import com.example.administrator.chaoshen.widget.LastInputEditText;
import com.example.administrator.chaoshen.widget.PayDialog;
import com.example.administrator.chaoshen.widget.SoftKeyBoardListener;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.XKeyboardUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class WinLoseShopingFragment extends BaseFragment implements View.OnClickListener {
    CheckBox falge1;
    TextView clearList;
    private WinloseMatchesBean data = new WinloseMatchesBean();
    private int selPosition;
    private WinLoseShopPresenter mPresenter;
    private LastInputEditText times_edit;


    @Bind(R.id.edite_button)
    TextView editeButton;
    @Bind(R.id.deatil_text)
    TextView deatilText;
    @Bind(R.id.list_item)
    ListView listItem;
    @Bind(R.id.selcet_zhu_num)
    TextView selcetZhuNum;
    @Bind(R.id.has_select)
    TextView hasSelect;
    @Bind(R.id.sure_buy)
    TextView sureBuy;
    @Bind(R.id.adfsdf)
    RelativeLayout adfsdf;


    private int mSelectnum;
    private boolean check = true;
    private WinLoseShopAdapter adapter;
    private int atLeastNum = 0;
    //Activity最外层的Layout视图
    private View activityRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    // 声明PopupWindow
    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;

    // 声明平移动画
    private TranslateAnimation animation;
    private boolean isFirst;
    private long redId = 0;
    private double payMoney;
    private double oldpayMoney;
    private int money;
    private String rule;
    private String lotteryNo;
    private String bet;
    private int times;
    private TextView money_left;
    private int minTimes = 1; //最小投注倍数
    private int maxTimes = 9999;//最大投注倍数
    private int maxAmout = 99999999;//最大投注金额
    private TextView flage3;
    private PayOrderNetBean orderBean;
    private boolean checkFinish = false;
    private long payID;
    private boolean reCharegeFailed;
    private PayDialog dialog;
    private BeishuKeyBroad beishuKeyBroad;
    private RelativeLayout bottom_shoping;


    public static WinLoseShopingFragment newInstance(Bundle data2) {

        WinLoseShopingFragment fragment = new WinLoseShopingFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_winlose_shoping;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLogin()) {
            money_left.setText(MathUtil.big2(getUserCache().getCurrency()) + "元");
        } else {
            money_left.setText("未登录");
        }

        if (checkFinish) {
            showLoadding("正在查询");
            //sendEmptyUiMessageDelayed(1, 5000);
            mPresenter.checkRechargeStatus(new CheckRechargeNetBean(getUserCache().getToken(), payID));
        }
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        editeButton.setOnClickListener(this);
        sureBuy.setOnClickListener(this);
        activityRootView = root.findViewById(R.id.root_layout);
        data = (WinloseMatchesBean) getArguments().getSerializable("data");
        selPosition = (int) getArguments().getInt("selectLotteyNum");
        atLeastNum = (int) getArguments().getInt("atLeastNum");
        adapter = new WinLoseShopAdapter(this, getContext(), data.getMatches());
        listItem.setAdapter(adapter);
        View footer = View.inflate(getContext(), R.layout.layout_service_xieyi, null);
        falge1 = footer.findViewById(R.id.falge1);
        flage3 = footer.findViewById(R.id.flage3);
        clearList = footer.findViewById(R.id.clear_list);
        listItem.addFooterView(footer);
        clearList.setOnClickListener(this);
        money_left = root.findViewById(R.id.money_left);
        beishuKeyBroad=root.findViewById(R.id.beishuKeyBroad);
        bottom_shoping=root.findViewById(R.id.bottom_shoping);
        falge1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                check = b;
            }
        });
        deatilText.setText(data.getLotteryNo() + "期,截止时间" + data.getEndTime().substring(0, 16));
        mPresenter = new WinLoseShopPresenter(getContext(), this);
        if (atLeastNum == 9) {
            mPresenter.getLotteryLimit(new LotteryLimitNetBean("r9"));
        } else {
            mPresenter.getLotteryLimit(new LotteryLimitNetBean("sfc"));
        }
        times_edit = (LastInputEditText) root.findViewById(R.id.times_edit);

        times_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        //获取屏幕高度
        screenHeight = XDensityUtils.getScreenHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        setSelectSum();
        /*SoftKeyBoardListener listener = new SoftKeyBoardListener(getActivity());
        listener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                setTimes();


            }
        });*/
        if (mCache.getAsObject(IntentKey.BANNER_BEAN) != null) {
            flage3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle data = new Bundle();
                    BannerBean banner = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
                    data.putString(IntentKey.WEB_VIEW_URL, banner.getServiceUrl());//url
                    data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    data.putString(IntentKey.ACTION_BAR_TITLE, "服务协议");
                    data.putInt(IntentKey.OPEN_TYPE, 9);
                    toActivity(WebActivity.class, data);
                }
            });
        }


        adfsdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showSoftKeyV(times_edit);
                //XKeyboardUtils.openKeyboard(getContext(), times_edit);
                times_edit.requestFocus();
            }
        });


        setListenerForKey();
        times_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    beishuKeyBroad.setVisibility(View.VISIBLE);
                    bottom_shoping.setVisibility(View.GONE);
                }else {
                    beishuKeyBroad.setVisibility(View.GONE);
                    bottom_shoping.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void setListenerForKey(){
        beishuKeyBroad.disableShowInput(times_edit);
        beishuKeyBroad.setNumberCallBack(new BeishuKeyBroad.NumberCallBack() {
            @Override
            public void clickNumber(String number) {
                String oldText=times_edit.getText().toString();
                if (oldText.length()>=8){
                    return;
                }
                times_edit.setText(oldText+number);
            }

            @Override
            public void deleteNumber() {
                String mobile = times_edit.getText().toString();
                int last = times_edit.getText().length();
                if (last > 0) {
                    mobile = mobile.substring(0, mobile.length() - 1);
                    //删除最后一位
                    //  mobileEd.getText().delete(last - 1, last);
                    times_edit.setText(mobile);

                }
            }

            @Override
            public void sureButton() {
                times_edit.clearFocus();
                setTimes();
            }

            @Override
            public void seleterBeishu(String number) {
                times_edit.setText(number);
                times_edit.clearFocus();
                setTimes();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.edite_button:
                EventBus.getDefault().post(new WinLoseDataBean(selPosition, data));
                finish();
                break;
            case R.id.sure_buy:

                if (!check) {
                    XToast.normal("请同意服务协议");
                } else {
                    if (mSelectnum < atLeastNum) {
                        XToast.normal("请选择至少" + atLeastNum + "赛事");
                    } else {
                        XCache cache = XCache.get(getContext());
                        UserBean user = (UserBean) cache.getAsObject(IntentKey.USER);
                        if (user == null || user.getToken() == null) {
                            XToast.error("请先登录");
                            toActivity(LoginActivity.class, null);
                        } else {
                            if (Integer.parseInt(times_edit.getText().toString()) < minTimes) {
                                times_edit.setText(minTimes + "");
                                showMsg("最小" + minTimes + "倍数起投");
                                times_edit.setText(minTimes + "");
                                return;
                            } else if (Integer.parseInt(times_edit.getText().toString()) > maxTimes) {
                                times_edit.setText(maxTimes + "");
                                showMsg("最大" + maxTimes + "倍");
                                times_edit.setText(maxTimes + "");
                                return;
                            }
                            if (money > maxAmout) {
                                ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
                                dialog.setOne_button("投注金额不能超过" + maxAmout + "元", "确定");
                                dialog.show();
                                return;
                            }
                            if (TextUtils.isEmpty(times_edit.getText().toString().trim()) || "0".equals(times_edit.getText().toString().trim())) {
                                showMsg("最小" + minTimes + "倍数起投");
                                times_edit.setText(minTimes + "");
                            } else {
                                lotteryNo = data.getLotteryNo();
                                bet = "";
                                for (int i = 0; i < data.getMatches().size(); i++) {

                                    for (int j = 0; j < (data.getMatches().get(i)).getSelectItem().size(); j++) {
                                        SortUtil.sortList((data.getMatches().get(i)).getSelectItem());

                                        if (j == (data.getMatches().get(i)).getSelectItem().size() - 1) {
                                            bet = bet + (data.getMatches().get(i)).getSelectItem().get(j) + "";
                                        } else {
                                            bet = bet + (data.getMatches().get(i)).getSelectItem().get(j) + "";
                                        }

                                    }
                                    if (i == data.getMatches().size() - 1) {
                                        bet = bet + "";
                                    } else {
                                        bet = bet + "|";
                                    }
                                }
                                try {
                                    times = Integer.valueOf(times_edit.getText().toString().trim());
                                    rule = "";
                                    if (atLeastNum == 14) {
                                        rule = "sfc";
                                    } else if (atLeastNum == 9) {
                                        rule = "r9";
                                    }
                                    if (atLeastNum == 0) {
                                        showMsg("选投倍数有误");
                                        return;
                                    }
                                    XLog.e("--------bet-------------" + bet);

                                    XLog.e("-------------bet=" + bet);
                                    //showPopup();
                                    mPresenter.get_ordertRedPackets(new OrdertRedPacketsNetBean(getUserCache().getToken(), "winlose", money + ""));
                                    //   WinLoseBetnetBean data = new WinLoseBetnetBean(user.getToken(), lotteryNo, rule, bet, times);
                                    //   mPresenter.getwinlose_bet(data);
                                } catch (Exception e) {
                                    showMsg("选投倍数有误");
                                }

                            }
                        }
                    }
                }

                break;

            case R.id.clear_list:

                for (int i = 0; i < data.getMatches().size(); i++) {
                    data.getMatches().get(i).getSelectItem().clear();
                    XLog.e("-----123------" + data.getMatches().get(i).getSelectItem().size());
                }


                mSelectnum = 0;
                adapter.notifyDataSetChanged();
                refreshZhu();

                break;

        }
    }


    public void setSelectData(int selectData) {
        mSelectnum = selectData;
        hasSelect.setText("已选" + mSelectnum + "场(请选择" + atLeastNum + "场赛事)");

    }


    //计算一进页面的已选号码和刷新注数
    private void setSelectSum() {
        int selectNum = 0;
        for (int i = 0; i < data.getMatches().size(); i++) {
            if (data.getMatches().get(i).getSelectItem().size() != 0) {
                selectNum++;
            }
        }
        mSelectnum = selectNum;
        refreshZhu();
    }


    public void refreshZhu() {
        XLog.e(mSelectnum + "----数据---isFirst=" + atLeastNum);
        if (mSelectnum >= atLeastNum) {
            WinloseMatchesBean selctData = data;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < selctData.getMatches().size(); i++) {

                for (int j = 0; j < selctData.getMatches().get(i).getSelectItem().size(); j++) {
                    SortUtil.sortList(selctData.getMatches().get(i).getSelectItem());
                    stringBuilder.append(selctData.getMatches().get(i).getSelectItem().get(j) + "");
                }
                stringBuilder.append("|");

            }
            String[] bets = stringBuilder.toString().split(WinloseUtil.separator, -1);
            XLog.e("----数据---=" + stringBuilder.toString());
            int zhushu;
            if (atLeastNum == 14) {
                zhushu = WinloseUtil.bets(bets, false);
            } else {
                zhushu = WinloseUtil.bets(bets, true);
            }
            int times;
            try {
                if (TextUtils.isEmpty(times_edit.getText().toString().trim())) {
                    times = 0;
                    // times_edit.setText("0");
                } else {
                    times = Integer.valueOf(times_edit.getText().toString().trim());
                }
            } catch (Exception e) {
                times_edit.setText(minTimes);
                times = minTimes;
            }


            selcetZhuNum.setText(Html.fromHtml("" + "<font color='#666666'>" + zhushu
                    + "" + "注</font>  " + "<font color='#C8152D'>" + 2 * times * zhushu + "元</font>" + ""));

            money = 2 * times * zhushu;

            XLog.e(money + "----money---=" + maxAmout);

            // hasSelect.setText("预测奖金"+minxMoney+"~~"+maxMoney+"元");

           /* //先得到投注赔率的集合
            ArrayList<ArrayList<Double>> oddsAlldata = new ArrayList<>();
            for (int i = 0; i < selctData.getMatches().size(); i++) {

                ArrayList data = selctData.getMatches().get(i).getSelectItem();
                ArrayList<Double> oddsList = new ArrayList<>();
                for (int j = 0; j < data.size(); j++) {
                    double odds = 0;
                    if ("3".equals(data.get(j).toString())) {
                        odds = selctData.getMatches().get(i).getHostOdds();

                    } else if ("1".equals(data.get(j).toString())) {
                        odds = selctData.getMatches().get(i).getEvenOdds();
                    } else if ("0".equals(data.get(j).toString())) {
                        odds = selctData.getMatches().get(i).getGuestOdds();
                    }
                    oddsList.add(odds);
                    XLog.e(data.get(j) + "------selctData-----odds---" + odds);
                }
                oddsAlldata.add(oddsList);
            }

            double maxMoney = 1;
            double minxMoney = 1;


            for (int i = 0; i < oddsAlldata.size(); i++) {
                SortUtil.sortLisDouble(oddsAlldata.get(i));
                for (int j = 0; j < oddsAlldata.get(i).size(); j++) {
                    if (oddsAlldata.get(i).get(j) != (Double) 0.00) {
                        XLog.e("------maxMoney-----1---" + maxMoney);
                        if (j == 0) {
                            maxMoney = maxMoney * oddsAlldata.get(i).get(0);
                        }
                    }

                }
            }

            for (int i = 0; i < oddsAlldata.size(); i++) {
                SortUtil.sortLisDouble(oddsAlldata.get(i));
                for (int j = 0; j < oddsAlldata.get(i).size(); j++) {
                    XLog.e("------maxMoney------2--" + minxMoney);
                    if (j == oddsAlldata.get(i).size() - 1) {
                        minxMoney = minxMoney * oddsAlldata.get(i).get(j);
                    }

                }
            }
            hasSelect.setText("预测奖金" + MathUtil.big2(minxMoney) + "~~" +  MathUtil.big2(maxMoney) + "元");*/

        } else {
            selcetZhuNum.setText("" + 0 + "注" + 0 + "元");
            hasSelect.setText("已选" + mSelectnum + "场（请至少选" + atLeastNum + "场赛事）");
        }
    }

    public void moneyEnoughTopay(WinLoseBetnetBean info) {
        orderBean = new PayOrderNetBean(getUserCache().getToken(), info.getId());
        SettingBean settingBean = getBannerCache().getSettings();
        int rechargeMode = settingBean.getRechargeMode();
        if (rechargeMode == 1) {

            if (getUserCache().getCurrency() < payMoney) {
                //showMsg("余额不足，请充值");
                if (redId != 0) {
                    orderBean.setRedId(redId);
                }
                XLog.e(payMoney+"--------payMoney-------------"+ Double.valueOf(payMoney - getUserCache().getCurrency()));
                List<RechargeListBean> rechargeListBeans = getBannerCache().getRechargeListBeans();
                mPresenter.recharge_money(new RechargeMoneyNetBean(getUserCache().getToken(),
                        MathUtil.doubleTwo(Double.valueOf(payMoney - getUserCache().getCurrency())),
                        rechargeListBeans.get(dialog.getSelection()).getBankId(), rechargeListBeans.get(dialog.getSelection()).getMerchantId()));
                popupWindow.dismiss();
            } else {
                if (redId != 0) {
                    XLog.e("------redId------2-----" + redId);
                    orderBean.setRedId(redId);
                }
                mPresenter.pay_order(orderBean);
                popupWindow.dismiss();
            }


        } else {  //预充值
            if (getUserCache().getCurrency() < payMoney) {
                //showMsg("余额不足，请充值");
                showNoEnoughMoney();
                popupWindow.dismiss();
            } else {
                if (redId != 0) {
                    XLog.e("------redId------2-----" + redId);
                    orderBean.setRedId(redId);
                }
                mPresenter.pay_order(orderBean);
                popupWindow.dismiss();
            }
        }
    }

    public void payOrder() {
        if (redId != 0) {
            XLog.e("------redId------2-----" + redId);
            orderBean.setRedId(redId);
        }
        mPresenter.pay_order(orderBean);
    }


    private void showNoEnoughMoney() {
        ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
        dialog.setMessage("余额不足支付本订单，请充值");
        dialog.setSureButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMsg("支付失败，请到“订单记录”处继续支付");
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


    /**
     *
     */
   /* @SuppressLint("WrongConstant")
    public void showPopup(List<LuckyMoneyBean> redList) {
        //  mPresenter.get_ordertRedPackets(new OrdertRedPacketsNetBean(getUserCache().getToken(),  order.getOrderId()));
        popupView = View.inflate(getContext(), R.layout.item_change_icon, null);
        // 参数2,3：指明popupwindow的宽度和高度
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        // 设置背景图片， 必须设置，不然动画没作用
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        // 设置点击popupwindow外屏幕其它地方消失
        popupWindow.setOutsideTouchable(true);

        // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
        animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(200);

        ((TextView) popupView.findViewById(R.id.yu_e_money)).setText("余额抵扣  (余额为" + MathUtil.big2(getUserCache().getCurrency()) + " 元)");

        ((TextView) popupView.findViewById(R.id.pay_money)).setText(MathUtil.big2(money) + "元");

        oldpayMoney = money;
        refreshMoney(redList);

        if (redList == null || redList.size() == 0) {
            popupView.findViewById(R.id.arrow_falge).setVisibility(View.GONE);
        } else {
            popupView.findViewById(R.id.arrow_falge).setVisibility(View.VISIBLE);
        }

        popupView.findViewById(R.id.hongbao_money_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redList == null || redList.size() == 0) {

                    showMsg("暂无可用红包");
                    return;
                }
                popupView.findViewById(R.id.arrow_falge).setVisibility(View.VISIBLE);
                // XToast.normal("使用红包");
                Bundle data = new Bundle();
                //   data.putLong(IntentKey.ORDER_ID, order.getOrderId());
                data.putLong(IntentKey.RED_ID, redId);//redId
                data.putSerializable(IntentKey.ORDER_LIST, (Serializable) redList);
                toActivityForResult(UseLuckyMoneyActivity.class, data, Constants.SELECT_LUCKY_MONEY);
                // order.setRedId(0);
            }
        });
        popupView.findViewById(R.id.pay_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinLoseBetNetBean data = new WinLoseBetNetBean(getUserCache().getToken(), lotteryNo, rule, bet, times);
                mPresenter.getwinlose_bet(data);
               *//* if (getUserCache().getCurrency() < payMoney) {
                    showMsg("余额不足，请充值");
                    toActivity(RechargeActivity.class, null);
                    popupWindow.dismiss();
                } else {
                    if (redId != 0) {
                        XLog.e("------redId------2-----" + redId);
                        order.setRedId(redId);
                    }
                    mPresenter.pay_order(order);
                    popupWindow.dismiss();
                }*//*
            }
        });


        popupView.findViewById(R.id.dismiss_money).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // 在点击之后设置popupwindow的销毁
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        popupWindow.showAtLocation(sureBuy, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
    }*/


    /**
     * 新弹窗
     */
    public void payDialog(List<LuckyMoneyBean> redList) {
        // XToast.normal("使用红包");
//   data.putLong(IntentKey.ORDER_ID, order.getOrderId());
//redId
        dialog = new PayDialog(null, this, getContext(), money, sureBuy, new PayDialog.RefershZhuListener() {
            @Override
            public void refresh() {
                oldpayMoney = money;
                refreshMoney(redList);
            }
        }, redList, new PayDialog.RedMoneyListener() {
            @Override
            public void selectHongbao() {
                if (redList == null || redList.size() == 0) {

                    showMsg("暂无可用红包");
                    return;
                }
                popupView.findViewById(R.id.arrow_falge).setVisibility(View.VISIBLE);
                // XToast.normal("使用红包");
                Bundle data = new Bundle();
                //   data.putLong(IntentKey.ORDER_ID, order.getOrderId());
                data.putLong(IntentKey.RED_ID, redId);//redId
                data.putSerializable(IntentKey.ORDER_LIST, (Serializable) redList);
                toActivityForResult(UseLuckyMoneyActivity.class, data, Constants.SELECT_LUCKY_MONEY);

            }
        }, new PayDialog.PayOrderListener() {
            @Override
            public void onClickPayOrder() {
                WinLoseBetNetBean data = new WinLoseBetNetBean(getUserCache().getToken(), lotteryNo, rule, bet, times);
                mPresenter.getwinlose_bet(data);
            }
        }, new PayDialog.GetViewListener() {
            @Override
            public void ongetView(View view) {
                popupView = view;
            }
        }, new PayDialog.DirectPayOrderListener() {
            @Override
            public void onClickDirectPay(int selection) {
                WinLoseBetNetBean data = new WinLoseBetNetBean(getUserCache().getToken(), lotteryNo, rule, bet, times);
                mPresenter.getwinlose_bet(data);
            }
        });

        if (payMoney <= getUserCache().getCurrency()) {

            dialog.setPay_way_llGone();
        }else {
            dialog.setNo_Enough_moneyVisible(MathUtil.doubleTwo(Double.valueOf(payMoney - getUserCache().getCurrency())));
        }

        popupWindow = dialog;

    }


    public void showH5(RechargeH5Bean mdata) {
        checkFinish = true;
        payID = mdata.getPayId();
        if (mdata.getPayType() == 0) { //H5支付
            Bundle data = new Bundle();
            data.putString(IntentKey.WEB_VIEW_URL, mdata.getParams());
            data.putBoolean(IntentKey.IS_PAY_ORDER, true);
            // toActivity(WebActivity.class, data);
            toActivityForResult(WebActivity.class, data, Constants.RECHARGE_SUCCESS);
        }
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
        XLog.e("------redId------1-----" + redId);
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

    public void showLoginTips() {
        final AppAlertDialog dialog = new AppAlertDialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setWindowWidth(UIHelper.dip2px(getContext(), 265));
        dialog.setMessage("支付成功");
        dialog.setSureButton("查看订单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                XToast.normal("查看订单");
            }
        });
        dialog.setCancelButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                EventBus.getDefault().post(new PayOrderSuccess());
                EventBus.getDefault().post(new RefreshDataBean());
            }
        });
        dialog.show();
    }


    public void setLimti(LimitBean data) {
        try {
            minTimes = Integer.parseInt(data.getMinTimes());
            XLog.e("--------------------minTimes-" + minTimes);
            times_edit.setText(minTimes + "");
        } catch (Exception e) {
        }

        try {
            maxTimes = Integer.parseInt(data.getMaxTimes());
        } catch (Exception e) {
        }
        try {
            maxAmout = Integer.parseInt(data.getMaxAmout());
        } catch (Exception e) {
        }


    }


    private void setTimes() {
        if (TextUtils.isEmpty(times_edit.getText().toString())) {
            times_edit.setText(minTimes + "");
            showMsg("最小" + minTimes + "倍数起投");
        } else {
            //在这里调用服务器的接口，获取数据
            if (Integer.parseInt(times_edit.getText().toString()) < minTimes) {
                times_edit.setText(minTimes + "");
                showMsg("最小" + minTimes + "倍数起投");
                // times_edit.setText(minTimes + "");
            } else if (Integer.parseInt(times_edit.getText().toString()) > maxTimes) {
                times_edit.setText(maxTimes + "");
                showMsg("最大可投" + maxTimes + "倍");
                //times_edit.setText(maxTimes + "");
            }
        }
        refreshZhu();
    }

    @Subscribe
    public void onEventMainThread(RefreshDataBean info) {

        if (isLogin()) {
            money_left.setText(MathUtil.big2(getUserCache().getCurrency()) + "元");
            if (!info.isPayOrderSuccess()) {
                setCheckMoney();
            }
        } else {
            money_left.setText("未登录");
        }

    }


    @Subscribe
    public void onEventMainThread(RechargeFailed info) {
        showMsg("支付失败，请到“订单记录”处继续支付");

    }


    public void setRechargeFailed(boolean failed) {
        checkFinish = false;
        if (failed) {
            showMsg("支付失败，请到“订单记录”处继续支付");
        } else {//充值成功

        }

    }


    public void setCheckMoney() {
        if (getUserCache().getCurrency() < payMoney) {
            //showMsg("余额不足，请充值");
            showNoEnoughMoney();

        } else {
           /* WinLoseBetnetBean data = new WinLoseBetnetBean(getUserCache().getToken(), lotteryNo, rule, bet, times);
            mPresenter.getwinlose_bet(data);*/
            mPresenter.pay_order(orderBean);
        }
    }

}
