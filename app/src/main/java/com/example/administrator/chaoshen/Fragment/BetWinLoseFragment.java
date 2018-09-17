package com.example.administrator.chaoshen.Fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BetDetailActivity;
import com.example.administrator.chaoshen.activtiy.RechargeActivity;
import com.example.administrator.chaoshen.activtiy.TicketDeatilActivity;
import com.example.administrator.chaoshen.activtiy.UseLuckyMoneyActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.activtiy.WinLoseActivity;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.adapter.BetwinLoseAdapter;
import com.example.administrator.chaoshen.adapter.HuggAdapter;
import com.example.administrator.chaoshen.app.MyApplication;
import com.example.administrator.chaoshen.bean.BetDeatilBean;
import com.example.administrator.chaoshen.bean.ContinuePaySuccessBean;
import com.example.administrator.chaoshen.bean.FinishActivity;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;
import com.example.administrator.chaoshen.bean.RechargeH5Bean;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.CheckRechargeNetBean;
import com.example.administrator.chaoshen.net.bean.DealOrderNetBean;
import com.example.administrator.chaoshen.net.bean.OrdertRedPacketsNetBean;
import com.example.administrator.chaoshen.net.bean.PayOrderNetBean;
import com.example.administrator.chaoshen.net.bean.RechargeMoneyNetBean;
import com.example.administrator.chaoshen.presenter.BetWinLosePresenter;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.util.MathUtil;
import com.example.administrator.chaoshen.util.SoundUtils;
import com.example.administrator.chaoshen.widget.ChaoshenAlertDialog;
import com.example.administrator.chaoshen.widget.PayDialog;
import com.example.administrator.chaoshen.widget.SharePopupWindow;
import com.youth.xframe.utils.log.XLog;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class BetWinLoseFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.lottery_icon)
    ImageView lotteryIcon;
    @Bind(R.id.lottery_name)
    TextView lotteryName;
    @Bind(R.id.lottery_way)
    TextView lotteryWay;
    @Bind(R.id.lottery_status)
    TextView lotteryStatus;
    @Bind(R.id.lottery_status_des)
    TextView lotteryStatusDes;
    @Bind(R.id.lottery_right_ic)
    GifImageView lotteryRightIc;
    @Bind(R.id.head_fataher)
    RelativeLayout headFataher;
    @Bind(R.id.head_fataher_line)
    View headFataherLine;
    @Bind(R.id.open_price_time)
    TextView openPriceTime;
    @Bind(R.id.open_price_time_title)
    TextView open_price_time_title;
    @Bind(R.id.open_price_time_title2)
    TextView open_price_time_title2;
    @Bind(R.id.head_flag)
    LinearLayout headFlag;
    @Bind(R.id.zhu_and_time)
    TextView zhuAndTime;
    @Bind(R.id.list_heaf_flage)
    LinearLayout listHeafFlage;
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.no_chupiao)
    TextView noChupiao;
    @Bind(R.id.has_chupiao)
    TextView hasChupiao;
    @Bind(R.id.flage3)
    RelativeLayout flage3;
    @Bind(R.id.order_id_number)
    TextView orderIdNumber;
    @Bind(R.id.create_time)
    TextView createTime;
    @Bind(R.id.dianzhu_get_time)
    TextView dianzhuGetTime;
    @Bind(R.id.dianzhu_chupiao)
    TextView dianzhuChupiao;
    @Bind(R.id.pay_message)
    TextView payMessage;
    @Bind(R.id.warm_tips)
    TextView warmTips;
    @Bind(R.id.cancel_order)
    Button cancelOrder;
    @Bind(R.id.pay_order)
    Button payOrder;
    @Bind(R.id.no_pay_order_rl)
    RelativeLayout noPayOrderRl;
    @Bind(R.id.continue_buy)
    Button continueBuy;
    @Bind(R.id.layout_recor_jingcai_head)
    LinearLayout layout_recor_jingcai_head;
    @Bind(R.id.redod_list_head)
    LinearLayout redod_list_head;

    @Bind(R.id.forecast_price_ll)
    LinearLayout forecast_price_ll;
    @Bind(R.id.forecast_price)
    TextView forecast_price;
    @Bind(R.id.list_refunds_ll)
    LinearLayout list_refunds_ll;
    @Bind(R.id.list_refunds)
    TextView list_refunds;


    private BetDeatilBean data;
    private static BetWinLoseFragment mfragment;
    private PopupWindow popupWindow;
    private TranslateAnimation animation;
    private RelativeLayout show_scrollview;
    private LinearLayout code_view;
    // 声明PopupWindow对应的视图
    private View popupView;
    private BetWinLosePresenter mPresenter;
    private double oldpayMoney;
    private long redId = 0;
    private double payMoney;
    private AppBaseAdapter adapter;
    private ImageView er_weicode;
    private PayDialog dialog;
    private boolean checkFinish;
    private long payID;
    private PayOrderNetBean orderBean;

    public static BetWinLoseFragment newInstance(BetDeatilBean data) {

        Bundle args = new Bundle();
        args.putSerializable(IntentKey.BET_DEATIL, data);
        BetWinLoseFragment fragment = new BetWinLoseFragment();
        fragment.setArguments(args);
        mfragment = fragment;
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bet_deatil;
    }

    @Override
    public void initData() {
        data = (BetDeatilBean) getArguments().getSerializable(IntentKey.BET_DEATIL);
        //判断是否完成支付
        mPresenter = new BetWinLosePresenter(getContext(), this);
        if ("winlose".equals(data.getLotteryType())) {
            adapter = new BetwinLoseAdapter(mfragment, getContext());
        } else if ("jingcai".equals(data.getLotteryType())) {
            adapter = new HuggAdapter(mfragment, getContext());
        }

    }

    public BetDeatilBean getViewData(){
        return data;
    }

    @Override
    public void handlerMessage(Message msg) {
        super.handlerMessage(msg);
        if (msg.what == 1) {
            laodListview();
        } else if (msg.what == 2) {
            laodJingCaiListview();
        }else if (msg.what==3){
            startAnimation(msg.arg1);
        }
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        showLoadding(null);
        if (data.getStatus() == 0) {  //未支付状态
            noPayOrder();
        } else {
            hasPayOrder();
        }
        Message message=new Message();
        message.what=3;
        show_scrollview = root.findViewById(R.id.show_scrollview);
        code_view = root.findViewById(R.id.code_view);
        if (data.getStatus() == 0 || data.getStatus() == -1 || data.getStatus() == 1
                || data.getStatus() == 2 || data.getStatus() == 3 || data.getStatus() == 6) {
            lotteryRightIc.setVisibility(View.GONE);
        } else if (data.getStatus() == 4 || data.getStatus() == 5 || data.getStatus() == 7) {
            lotteryRightIc.setVisibility(View.VISIBLE);
           // startAnimation(R.drawable.waiting_word);
            message.arg1=R.drawable.waiting_word;
            sendUiMessageDelayed(message,1000);
            //lotteryRightIc.setImageResource(R.drawable.waiting_word);
        } else if (data.getStatus() == 8) {
            lotteryRightIc.setVisibility(View.VISIBLE);
            //startAnimation(R.drawable.no_word);
            message.arg1=R.drawable.no_word;
            sendUiMessageDelayed(message,1000);
        } else if (data.getStatus() == 9 || data.getStatus() == 10 || data.getStatus() == 11 || data.getStatus() == 12 || data.getStatus() == 13) {
            lotteryRightIc.setVisibility(View.VISIBLE);
           // lotteryRightIc.setImageResource(R.drawable.has_word);
           // startAnimation(R.drawable.has_word);
            message.arg1=R.drawable.has_word;
            sendUiMessageDelayed(message,1000);

        }


        lotteryIcon.post((new DownloadImageRunnable(lotteryIcon, data.getIcon(),
                R.drawable.default_bg, mImageLoader, mOpt)));
        open_price_time_title2.setVisibility(View.GONE);
        if ("winlose".equals(data.getLotteryType())) {
            lotteryName.setText(data.getRuleName() + "");
            lotteryWay.setText(data.getLotteryNo() + "期");
            zhuAndTime.setText("" + data.getBets() + "注" + data.getTimes() + "倍,方案金额" + data.getAmount() + "元");
            noChupiao.setVisibility(View.GONE);
            open_price_time_title.setText("预计开奖");
            openPriceTime.setText(data.getPrizeTime());

        } else if ("jingcai".equals(data.getLotteryType())) {
            lotteryName.setText(data.getLotteryName() + "");
            lotteryWay.setText(data.getRuleName() + "");
            zhuAndTime.setText(data.getMatchNum() + "场," + data.getPassType() + "," + data.getBets() + "注" + data.getTimes() + "倍,方案金额" + data.getAmount() + "元");
            noChupiao.setVisibility(View.VISIBLE);
            open_price_time_title.setText("全部比赛");
            open_price_time_title2.setText("预计结束");
            open_price_time_title2.setVisibility(View.VISIBLE);
            openPriceTime.setText(data.getFinishTime());

            if (!TextUtils.isEmpty(data.getMinEstimateBonus()) && !TextUtils.isEmpty(data.getMaxEstimateBonus())) {
                forecast_price_ll.setVisibility(View.VISIBLE);
                forecast_price.setText(Html.fromHtml("<font color=#F14941>" + "若中奖，预测奖金:" + data.getMinEstimateBonus()
                        + "~" + data.getMaxEstimateBonus() + "</font>"));
            }

        }


        /*else if (){

        }*/  //足彩的

        lotteryStatus.setText(data.getStatusText());
        if (!TextUtils.isEmpty(data.getStatusTips())) {
            lotteryStatusDes.setText("(" + data.getStatusTips() + ")");
        }
        ArrayList list = new ArrayList();
        list.add(11);
        list.add(12);
        list.add(13);
        if (list.contains(data.getStatus())) {
            lotteryStatus.setText(Html.fromHtml("<font color=#F14941>" + "奖金" + "<big>" + data.getTaxedBonus() + "</big></font>"));
        }

        orderIdNumber.setText("订单编号    " + data.getOrderNo());
        createTime.setText("下单时间    " + data.getCreateTime());
        dianzhuGetTime.setText("店主接单    " + data.getSendTime());
        dianzhuChupiao.setText("店主出票    " + data.getTicketTime());
        payMessage.setText("支付信息    " + data.getPayDetail());
        warmTips.setText("温馨提示    " + data.getReminder());

        if (data.getStatus() == 0) {
            cancelOrder.setOnClickListener(this);
            payOrder.setOnClickListener(this);
        } else {
            continueBuy.setOnClickListener(this);
        }

        if (data.getStatus() == 4 || data.getStatus() == 7 || data.getStatus() == 8 || data.getStatus() == 9 || data.getStatus() == 10 || data.getStatus() == 11 || data.getStatus() == 12 || data.getStatus() == 13) {
            hasChupiao.setVisibility(View.VISIBLE);
            hasChupiao.setText("出票成功，出票明细>");
        } else if (data.getStatus() == 5) {
            hasChupiao.setVisibility(View.VISIBLE);
            hasChupiao.setText("部分出票成功，出票明细>");
        } else {
            hasChupiao.setVisibility(View.GONE);
        }
        if (data.getStatus() == 6 || data.getStatus() == 8 || data.getStatus() == 9 ||
                data.getStatus() == 10 || data.getStatus() == 11 ||
                data.getStatus() == 12 || data.getStatus() == 13 || data.getStatus() == -1) {
            headFlag.setVisibility(View.GONE);
        } else {
            headFlag.setVisibility(View.VISIBLE);
        }
        list_refunds_ll.setVisibility(View.GONE);
        if (data.getStatus() == 5 || data.getStatus() == 6 || data.getStatus() == -1) {
            list_refunds_ll.setVisibility(View.VISIBLE);
            list_refunds.setText(data.getRefunds());
        }


        hasChupiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showMsg("出票明细");
                Bundle bundle = new Bundle();
                bundle.putLong(IntentKey.ORDER_ID, data.getOrderId());
                bundle.putString(IntentKey.LOTTERY_TYPE, data.getLotteryType());
                toActivity(TicketDeatilActivity.class, bundle);
            }
        });

        listView.setAdapter(adapter);

        if ("winlose".equals(data.getLotteryType())) {
            sendEmptyUiMessageDelayed(1, 500);
            //  laodListview();

        } else if ("jingcai".equals(data.getLotteryType())) {
            sendEmptyUiMessageDelayed(2, 500);
            //laodJingCaiListview();
        }
        er_weicode = root.findViewById(R.id.er_weicode);
        er_weicode.post(new DownloadImageRunnable(er_weicode, data.getInivitQrcode(), R.drawable.default_bg, mImageLoader, mOpt));

        initShare();
    }



    public void startAnimation(int resId){
        GifDrawable gifFromResource = null;
        try {
            gifFromResource = new GifDrawable(getResources(), resId);
            //gifFromResource.setLoopCount(1);
            //  gifFromResource.setSpeed(0.7f);
            lotteryRightIc.setImageDrawable(gifFromResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*listView.post(new Runnable() {
            @Override
            public void run() {

            }
        });*/

    }


    private void laodJingCaiListview() {

        List<BetDeatilBean.BetContentBean> list_data = data.getBetContent();
        ((HuggAdapter) adapter).setmData(list_data);
        setListV();
        layout_recor_jingcai_head.setVisibility(View.VISIBLE);

    }

    public void setListV() {
        setListViewHeightBasedOnChildren(listView);
    }


    private void laodListview() {

        List<BetDeatilBean.BetContentBean> list_data = data.getBetContent();
        List<BetDeatilBean.BetContentBean> inde_list = new ArrayList<>();
        for (int i = 0; i < list_data.size(); i++) {
            if (!TextUtils.isEmpty(list_data.get(i).getContent())) {
                inde_list.add(list_data.get(i));
            }
        }
        ((BetwinLoseAdapter) adapter).setmData(list_data);
        setListV();
        redod_list_head.setVisibility(View.VISIBLE);
    }

    public void setList(int high) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = high;
        XLog.e("-high-----------i=" + high);
        listView.setLayoutParams(params);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView==null){
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            XLog.e("-params.height------------i=" + listItem.getMeasuredHeight());
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    private void noPayOrder() {
        lotteryStatusDes.setVisibility(View.GONE);
        lotteryRightIc.setVisibility(View.GONE);
        headFlag.setVisibility(View.GONE);
        hasChupiao.setVisibility(View.GONE);
        dianzhuGetTime.setVisibility(View.GONE);
        dianzhuChupiao.setVisibility(View.GONE);
        continueBuy.setVisibility(View.GONE);
    }


    private void hasPayOrder() {
        lotteryStatusDes.setVisibility(View.VISIBLE);
        lotteryRightIc.setVisibility(View.VISIBLE);
        headFlag.setVisibility(View.VISIBLE);
        hasChupiao.setVisibility(View.VISIBLE);
        dianzhuGetTime.setVisibility(View.VISIBLE);
        dianzhuChupiao.setVisibility(View.VISIBLE);
        continueBuy.setVisibility(View.VISIBLE);
        noPayOrderRl.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cancel_order:
                mPresenter.delOrder(new DealOrderNetBean(data.getOrderId()));
                break;
            case R.id.pay_order:
                OrdertRedPacketsNetBean data1 = new OrdertRedPacketsNetBean(getUserCache().getToken(), data.getLotteryType(), data.getAmount() + "");
                data1.setOrderId(data.getOrderId());
                mPresenter.get_ordertRedPackets(data1);
                // showPopup(new PayOrderNetBean(getUserCache().getToken(), data.getOrderId()));

                break;
            case R.id.continue_buy:
                if ("r9".equals(data.getRule())) {
                    Bundle data = new Bundle();
                    data.putInt("lotter_type", 1); //0是胜负彩  1是任9
                    toActivity(WinLoseActivity.class, data);
                } else if ("sfc".equals(data.getRule())) {
                    Bundle data = new Bundle();
                    data.putInt("lotter_type", 0); //0是胜负彩  1是任9
                    toActivity(WinLoseActivity.class, data);
                }
                getActivity().finish();
                EventBus.getDefault().post(new FinishActivity()); //关闭前面的界面
                break;
        }
    }




    /**
     * 新弹窗
     */
    public void payDialog(final PayOrderNetBean order, List<LuckyMoneyBean> redList) {
        orderBean = order;
        dialog = new PayDialog(null, this, getContext(), (int) data.getAmount(), headFataher, new PayDialog.RefershZhuListener() {
            @Override
            public void refresh() {
                oldpayMoney = data.getAmount();
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
                data.putLong(IntentKey.ORDER_ID, order.getOrderId());
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
                        order.setRedId(redId);
                    }
                    mPresenter.pay_order(order);
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
                        order.setRedId(redId);
                    }
                    List<RechargeListBean> rechargeListBeans = getBannerCache().getRechargeListBeans();
                    mPresenter.recharge_money(new RechargeMoneyNetBean(getUserCache().getToken(),
                            MathUtil.doubleTwo(Double.valueOf(payMoney - getUserCache().getCurrency())),
                            rechargeListBeans.get(dialog.getSelection()).getBankId(), rechargeListBeans.get(dialog.getSelection()).getMerchantId()));
                    dialog.dismiss();
                } else {
                    if (redId != 0) {
                        XLog.e("------redId------2-----" + redId);
                        order.setRedId(redId);
                    }
                    mPresenter.pay_order(order);
                    dialog.dismiss();
                }


            }
        });
        if (payMoney <= getUserCache().getCurrency()) {
            dialog.setPay_way_llGone();
        }else {
            dialog.setNo_Enough_moneyVisible(MathUtil.doubleTwo(Double.valueOf(payMoney - getUserCache().getCurrency())));
        }

        popupWindow = dialog;

    }

    public void payOrder(){
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
            // toActivity(WebActivity.class, data);
            toActivityForResult(WebActivity.class, data, Constants.RECHARGE_SUCCESS);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (checkFinish) {
            showLoadding("正在查询");
          //  sendEmptyUiMessageDelayed(3, 5000);
            mPresenter.checkRechargeStatus(new CheckRechargeNetBean(getUserCache().getToken(), payID));
        }
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


    public void showLoginTips() {
        showMsg("支付成功");
        EventBus.getDefault().post(new ContinuePaySuccessBean());
    }

    public void initShare() {
        View codeView = View.inflate(getContext(), R.layout.code_view, null);
        ((ImageView) codeView.findViewById(R.id.er_weicode)).post(new DownloadImageRunnable(er_weicode, data.getInivitQrcode(),
                R.drawable.default_bg, mImageLoader, mOpt));
        // er_weicode.post(new DownloadImageRunnable(er_weicode,data.getInivitQrcode(),R.drawable.default_bg,mImageLoader,mOpt));
        ((BetDetailActivity) getActivity()).getRightImage().setVisibility(View.VISIBLE);
        ((BetDetailActivity) getActivity()).getRightImage().setImageResource(R.drawable.share_icon);
        ((BetDetailActivity) getActivity()).getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] list = new int[]{R.id.code_view};
                new SharePopupWindow(mfragment, (MyApplication) getActivity().getApplication(), show_scrollview, codeView);

            }
        });
    }

    public View getShareView() {
        code_view.setVisibility(View.VISIBLE);
        XLog.e("----------code_view-----");
        return show_scrollview;
    }

    public void shareRecycle() {
        // code_view.setVisibility(View.GONE);
    }


    private void refreshMoney(List<LuckyMoneyBean> redList) {
        payMoney = oldpayMoney;

        if (redList != null && redList.size() != 0) {
            if (redList.get(0).getId() == 0) {
                ((TextView) popupView.findViewById(R.id.hongbao_money)).setText("暂无可用红包");
                payMoney = payMoney - 0;
            } else {
                redId = redList.get(0).getId();
                ((TextView) popupView.findViewById(R.id.hongbao_money)).setText("-" + MathUtil.big2(redList.get(0).getRedPacketAmount()) + "元");
                payMoney = payMoney - redList.get(0).getRedPacketAmount();
            }
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


    public void setRechargeFailed(boolean failed) {
        checkFinish = false;
        if (failed) {//充值失败
            showMsg("支付失败");
        } else {//充值成功

        }
    }
}
