package com.example.administrator.chaoshen.activtiy;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.RechargeistAdapter;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.RechargeFailed;
import com.example.administrator.chaoshen.bean.RechargeH5Bean;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.bean.RechargeSuccessBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.CheckRechargeNetBean;
import com.example.administrator.chaoshen.net.bean.QueryPayChannelsBean;
import com.example.administrator.chaoshen.net.bean.RechargeMoneyNetBean;
import com.example.administrator.chaoshen.presenter.RechargePresenter;
import com.example.administrator.chaoshen.util.SoundUtils;
import com.example.administrator.chaoshen.util.UIHelper;
import com.example.administrator.chaoshen.widget.AppAlertDialog;
import com.example.administrator.chaoshen.widget.ChaoshenAlertDialog;
import com.example.administrator.chaoshen.widget.CustomNumKeyView;
import com.example.administrator.chaoshen.widget.KeyBoardPopupwindow;
import com.youth.xframe.utils.log.XLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class RechargeActivity extends BaseActivity implements View.OnClickListener, CustomNumKeyView.CallBack {
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_money)
    TextView userMoney;
    @Bind(R.id.item_1)
    TextView item1;
    @Bind(R.id.item_2)
    TextView item2;
    @Bind(R.id.item_3)
    TextView item3;
    @Bind(R.id.item_4)
    TextView item4;
    @Bind(R.id.item_5)
    TextView item5;
    @Bind(R.id.item_6)
    TextView item6;
    @Bind(R.id.pay_method)
    ListView payMethod;
    @Bind(R.id.pay_bt)
    Button pay_bt;

    private RechargePresenter mPresenter;
    private int selectPosition = 0;
    private RechargeistAdapter adapter;
    private ArrayList<View> list;
    private String selevtMoney;
    private View mPopView;
    private CustomNumKeyView mCustomKeyView;
    private PopupWindow mPop;
    private ScrollView father;
    private List<RechargeListBean> data;
    private KeyBoardPopupwindow myPop;
    private boolean checkFinish = false;
    private long payID;
    private int requestMoney;
    private boolean reCharegeFailed = true;
    private CheckBox checkbox;
    private TextView server_layout;

    @Override
    public int setLayoutId() {
        return R.layout.activity_rechage;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        // item2.setCl
        if (getIntent() != null) {
            requestMoney = (int) Math.ceil(getIntent().getDoubleExtra(IntentKey.REQUEST_MONEY, 0));
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
    public void initView() {
        list = new ArrayList();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);
        list.add(item6);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOnClickListener(this);
        }
        father = (ScrollView) findViewById(R.id.father1);
        checkbox= (CheckBox) findViewById(R.id.checkbox);
        server_layout= (TextView) findViewById(R.id.server_layout);
        server_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url =getBannerCache().getHelpUrl()+"/rechargeProtocol.html";
                    Bundle data = new Bundle();
                    data.putString(IntentKey.WEB_VIEW_URL,url);//url
                    data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    data.putString(IntentKey.ACTION_BAR_TITLE, "充值协议");
                    data.putInt(IntentKey.OPEN_TYPE, 9);
                    toActivity(WebActivity.class, data);
                }catch (Exception e){

                }

            }
        });
        setActionBarTitle("充值");
        mPresenter = new RechargePresenter(this, this);
        mPresenter.getPayWay(new QueryPayChannelsBean());
        selevtMoney = item1.getText().toString().trim();
        initMyPop();

       // SoundUtils.playSound(R.raw.coin2);
        /*GifImageView gifImageView=findViewById(R.id.loading_gif);
        try {
            GifDrawable gifFromResource = new GifDrawable(getContext().getResources(), R.drawable.coin);
            gifFromResource.setSpeed(0.5f);
            gifFromResource.setLoopCount(3);
            gifImageView.setImageDrawable(gifFromResource);

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }


    public void setPayWay(List<RechargeListBean> bankList) {
        data = bankList;
        if (isLogin()) {
            userName.setText("当前用户: " + getUserCache().getNickname());
            userMoney.setText("当前余额: " + getUserCache().getCurrency() + "元");
        }
        //initPop();

        adapter = new RechargeistAdapter(this, getContext(), bankList);
        payMethod.setAdapter(adapter);
        payMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                XLog.e("------onItemClick----------" + i);
                adapter.clearCheckBox();
                CheckBox checkBox = view.findViewById(R.id.checkbox);
                checkBox.setChecked(true);
                selectPosition = i;
            }
        });
        setListViewHeightBasedOnChildren(payMethod);
        if (bankList.size() != 0) {
            pay_bt.setEnabled(true);
        }

        pay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(selevtMoney.toString().trim())) {
                    showMsg("请输入金额");
                    return;
                }
                if (!checkbox.isChecked()){
                    showMsg("需要同意充值协议");
                    return;
                }


                Double min=10.0;
                try {
                    XLog.e("-------------获取出错"+getmCache().getAsString(IntentKey.MIN_RECHARGE_LIMIT));
                    String minMoney = getmCache().getAsString(IntentKey.MIN_RECHARGE_LIMIT);
                    min  = Double.valueOf(minMoney);
                }catch (Exception e){
                    XLog.e("-------------获取出错"+e);
                }

                if (Double.valueOf(selevtMoney.toString().trim())<min){
                   // requestMoney=min;
                    showMsg("最少充值金额为"+min+"元");
                    return;
                }

                mPresenter.recharge_money(new RechargeMoneyNetBean(getUserCache().getToken(),
                        Double.valueOf(selevtMoney.toString().trim()),
                        data.get(selectPosition).getBankId(), data.get(selectPosition).getMerchantId()));

            }
        });


    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int len = listAdapter.getCount();
        for (int i = 0; i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            if (null != listItem) {
                listItem.measure(0, 0); // 计算子项View 的宽高
                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.item_1:
                selevtMoney = item1.getText().toString().trim();
                setBg();
                item1.setBackgroundResource(R.drawable.recharge_red);
                //   item1.setFocusable(true);
                break;
            case R.id.item_2:
                selevtMoney = item2.getText().toString().trim();
                setBg();
                item2.setBackgroundResource(R.drawable.recharge_red);
                //  item2.setFocusable(true);
                break;
            case R.id.item_3:
                selevtMoney = item3.getText().toString().trim();
                setBg();
                item3.setBackgroundResource(R.drawable.recharge_red);
                //  item3.setFocusable(true);
                break;
            case R.id.item_4:
                selevtMoney = item4.getText().toString().trim();
                setBg();
                item4.setBackgroundResource(R.drawable.recharge_red);
                //  item4.setFocusable(true);
                break;
            case R.id.item_5:
                selevtMoney = item5.getText().toString().trim();
                setBg();
                item5.setBackgroundResource(R.drawable.recharge_red);
                //  item5.setFocusable(true);
                break;
            case R.id.item_6:
                selevtMoney = item6.getText().toString().trim();
                setBg();
                myPop.showAtLocation(father, Gravity.BOTTOM, 0, 0);
                //  mPop.showAtLocation(father, Gravity.BOTTOM, 0, 0);
                item6.setBackgroundResource(R.drawable.recharge_red);
                //   item6.setFocusable(true);

                break;

        }
    }


    public void setBg() {
        XLog.e("----------setBg--------");
        for (int i = 0; i < list.size(); i++) {
            ((View) list.get(i)).setBackgroundResource(R.drawable.recharge_gray);
            //((View)list.get(i)).setFocusable(false);
        }
    }

    private void initMyPop() {
        myPop = new KeyBoardPopupwindow(getContext(), 5);

        // 设置回调，并进行文本的插入与删除
        myPop.setmCallBack(new KeyBoardPopupwindow.CallBack() {
            @Override
            public void number(String number) {
                XLog.e("-------number------==" + number);
                item6.setText(number);
                selevtMoney = number.trim();
            }
        });
    }

  /*  private void initPop() {
        mPop = new PopupWindow();
//        mCustomKeyView=new CustomNumKeyView(this);
        mPopView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.custom_keyboardview, null);
        mPop.setContentView(mPopView);
        mPop.setTouchable(true);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new ColorDrawable());
        mPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mCustomKeyView = (CustomNumKeyView) mPopView.findViewById(R.id.keyboardview);
        mCustomKeyView.setHave_X(true);
        // 设置回调，并进行文本的插入与删除
        mCustomKeyView.setOnCallBack(this);
    }*/

    @Override
    public void clickNum(String num) {
        if (TextUtils.isEmpty(num) || " ".equals(num)) {
            return;
        }
        if (item6.getText().length() < 6) {
            item6.append(num);
            //文本长度为6时隐藏键盘
            if (item6.getText().length() >= 6) {
                // mCustomKeyView.
                sendEmptyUiMessageDelayed(1, 500);

            }
        }
    }

    @Override
    public void deleteNum() {
        int last = item6.getText().toString().length();
        XLog.e("------------item6.getText().toString()=" + item6.getText().toString());
        if (last > 0) {
            //删除最后一位
            String temp;
            temp = (item6.getText().toString()).substring(0, (item6.getText().toString()).length() - 1);
            // mobileEd.getText().delete(last - 1, last);
            item6.setText(temp);
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(RechargeSuccessBean info) {
        showMsg("充值成功");
        //finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.RECHARGE_SUCCESS) {
            String orderId = "";
            if (data != null) {
                orderId = data.getStringExtra(IntentKey.ORDER_ID);
            }
            XLog.e("-------------orderId---=" + orderId);
            //   showPatTips(orderId);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

/*
    public void showPatTips(String orderId) {
        final ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setMessage("您是否完成了支付?", 14);
        dialog.setSureButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
        dialog.setCancelButton("支付完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(orderId)) {
                  //  showMsg("支付出错");
                } else {
                    try {
                        mPresenter.checkRechargeStatus(new CheckRechargeNetBean(getUserCache().getToken(), Long.valueOf(orderId)));
                    }catch (Exception e){
                     //   showMsg("订单出错");
                    }
                }
                dialog.cancel();
            }
        });
        dialog.show();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        //来到界面开始查询是否完成操作
        if (checkFinish) {
            showLoadding("正在查询");
            sendEmptyUiMessageDelayed(1, 5000);
            mPresenter.checkRechargeStatus(new CheckRechargeNetBean(getUserCache().getToken(), payID));
        }
        if (requestMoney != 0) {

            item6.setText(requestMoney + "");
            //item6.performClick();
            sendEmptyUiMessage(2);
            myPop.setNumber(requestMoney + "");
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == 1) {
            hideLoadding();
        } else if (msg.what == 2) {
            selevtMoney = item6.getText().toString().trim();
            setBg();
          //  myPop.showAtLocation(father, Gravity.BOTTOM, 0, 0);
            //  mPop.showAtLocation(father, Gravity.BOTTOM, 0, 0);
            item6.setBackgroundResource(R.drawable.recharge_red);
        }
    }


    public void setRechargeFailed(boolean failed) {
        reCharegeFailed = failed;
    }

    @Override
    public void finish() {
        if (reCharegeFailed == true) {
            EventBus.getDefault().post(new RechargeFailed());
        }
        super.finish();
    }
}

