package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.MineFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BankCardsBean;
import com.example.administrator.chaoshen.bean.BankCardsFBean;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.ClearCache;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.ApplyCashNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.CashPresenter;
import com.example.administrator.chaoshen.util.MathUtil;
import com.example.administrator.chaoshen.util.UIHelper;
import com.example.administrator.chaoshen.widget.AppAlertDialog;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class CashAliActivity extends BaseActivity implements TextWatcher {


    @Bind(R.id.zhifubao_name)
    TextView zhifubaoName;
    @Bind(R.id.choose_bank)
    RelativeLayout chooseBank;
    @Bind(R.id.bank_name)
    EditText bankName;
    @Bind(R.id.can_cash_money)
    EditText canCashMoney;
    @Bind(R.id.button_login)
    Button buttonLogin;
    @Bind(R.id.message_notice)
    TextView messageNotice;
    private CashPresenter mPresenter;
    private List<BankCardsBean> mBankCardLis;
    private RelativeLayout choose_bank;
    public static String getBankList = "GetBankList";
    private int mPosition;
    private String text;

    @Override
    public int setLayoutId() {
        return R.layout.activity_cash_zhifubao;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
            if (getIntent()!=null){
                text = getIntent().getStringExtra(IntentKey.CASH_MEHTORD);
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
        mPresenter = new CashPresenter(this, this);
        setActionBarTitle("提现");
        choose_bank = (RelativeLayout) findViewById(R.id.choose_bank);
       // messageNotice.setText("1、提现金额最低10元起；\n2、每人每日最多提现2次；\n3、到账时间视银行处理速度而定，一般不超过2小时；");
        messageNotice.setText(text+"");
        getRightImage().setImageResource(R.drawable.cash_incon);
        //checkRealName();
        getRightImage().setVisibility(View.VISIBLE);
        getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data= new Bundle();
                BannerBean banner1 = (BannerBean) mCache.getAsObject(IntentKey.BANNER_BEAN);
                data.putString(IntentKey.WEB_VIEW_URL, banner1.getHelpUrl());
                // data.putString(IntentKey.WEB_VIEW_URL, mCache.getAsString(IntentKey.HELP_CENTER));//url
                data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data.putString(IntentKey.ACTION_BAR_TITLE, "帮助中心");
                data.putInt(IntentKey.OPEN_TYPE, 0);
                toActivity(WebActivity.class, data);
            }
        });
        XLog.e("-------------getRightIcon--");
        UserBean userBean = getUserCache();
        if (isLogin()) {
            if (userBean != null) {
                double money = userBean.getCurrency() - userBean.getNonPayableAmount();
                canCashMoney.setHint("可提取" + MathUtil.big2(money) + "元");
                zhifubaoName.setText(userBean.getUserName());
                /*if (1 == userBean.getIsRealName()) { //已经实名过了
                    mPresenter.get_bind_card(new TokenNetBean(getUserCache().getToken()));
                }*/
            }
        }
        canCashMoney.addTextChangedListener(this);
        bankName.addTextChangedListener(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Double.valueOf(canCashMoney.getText().toString().trim())>(userBean.getCurrency() - userBean.getNonPayableAmount())){
                    showMsg("提取金额超过可提现金额");
                    return;
                }

               // String name=zhifubaoName.getText().toString();
                String ali_number=bankName.getText().toString().trim();
               // String count=canCashMoney.getText().toString().trim();
                mPresenter.apply_money2(new ApplyCashNetBean(getUserCache().getToken(),
                        Double.valueOf(canCashMoney.getText().toString().trim()),ali_number, 0));
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkRealName();
    }

    private void checkRealName() {
        if (isLogin()) {
            if (getUserCache() == null) {
                XToast.normal("请先登录");
                toActivity(LoginActivity.class, null);
            } else {
                int isRealName = getUserCache().getIsRealName();
                if (isRealName == 0) { //没有实名认证
                    showLoginTips();
                } else if (isRealName == 1) {
                    zhifubaoName.setText(getUserCache().getUserName());
                    //去绑定银行卡的界面
                  //  toActivityForResult(BindCardActiivty.class, null, Constants.TO_BIND_AC);
                }
            }

        } else {
            XToast.normal("请先登录");
            toActivity(LoginActivity.class, null);
            // finish();
        }
    }


  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.TO_BIND_AC) {
            // setData();
            mPresenter.get_bind_card(new TokenNetBean(getUserCache().getToken()));
        } else if (requestCode == Constants.RETURN_SELECT_BANK) {
            int position = data.getIntExtra(getBankList, mPosition);
            if (position != -1) {
                notifyDefaultBank(position);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/


    public void setData() {

    }

    public void showLoginTips() {
        final AppAlertDialog dialog = new AppAlertDialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setWindowWidth(UIHelper.dip2px(getContext(), 265));
        dialog.setMessage("提现之前，请先完成实名认证", 14);
        dialog.setSureButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                //  XToast.normal("查看订单");
            }
        });
        dialog.setCancelButton("去实名认证", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                toActivity(RealNameActivity.class, null);
                //  finish();
            }
        });
        dialog.show();
    }


   /* public void showDefaultCard(List<BankCardsBean> data) {

        mBankCardLis = data;
        for (int i = 0; i < mBankCardLis.size(); i++) {
            if (data.get(i).getIsDefault() == 1) {
                mPosition = i;
                bankName.setText(data.get(i).getBankName() + "");
                bankNumber.setText(data.get(i).getCardNum() + "");
            }
        }
        choose_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bankList = new Bundle();
                BankCardsFBean data = new BankCardsFBean();
                data.setList(mBankCardLis);
                bankList.putSerializable(getBankList, data);
                toActivityForResult(BankListActivity.class, bankList, Constants.RETURN_SELECT_BANK);
            }
        });
    }*/


   /* public void notifyDefaultBank(int position) {
      *//*  mBankCardLis.get(mPosition).setIsDefault(0);
        mBankCardLis.get(position).setIsDefault(1);*//*

        // showDefaultCard(mBankCardLis);
        mPosition = position;
        bankName.setText(mBankCardLis.get(mPosition).getBankName() + "");
        bankNumber.setText(mBankCardLis.get(mPosition).getCardNum() + "");
    }*/

    @Subscribe
    public void onEventMainThread(RefreshDataBean info) {
       // cleraUser();
        UserBean userBean = getUserCache();
        if (isLogin()) {
            if (userBean != null) {
                double money = userBean.getCurrency() - userBean.getNonPayableAmount();
                canCashMoney.setHint("可提取" + MathUtil.big2(money) + "元");
                zhifubaoName.setText(userBean.getUserName());
                /*if (1 == userBean.getIsRealName()) { //已经实名过了
                    mPresenter.get_bind_card(new TokenNetBean(getUserCache().getToken()));
                }*/
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String name=zhifubaoName.getText().toString();
        String ali_number=bankName.getText().toString().trim();
        String count=canCashMoney.getText().toString().trim();
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(ali_number)&&!TextUtils.isEmpty(count)){
            buttonLogin.setEnabled(true);
        }else {
            buttonLogin.setEnabled(false);
        }
        /*canCashMoney.addTextChangedListener(this);
        bankName.*/
    }



}
